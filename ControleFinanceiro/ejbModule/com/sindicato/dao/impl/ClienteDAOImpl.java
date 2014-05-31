package com.sindicato.dao.impl;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.swing.SortOrder;

import com.sindicato.dao.ClienteDAO;
import com.sindicato.entity.Cliente;
import com.sindicato.entity.InformacaoSocio;
import com.sindicato.entity.Enum.StatusDebitoEnum;
import com.sindicato.result.InformacaoMensalidade;
import com.sindicato.result.MensalidadePaga;

@Stateless
public class ClienteDAOImpl extends DAOImpl<Cliente, Integer> implements
		ClienteDAO {

	private void inseriRegistroSocio(Cliente cliente) {
		InformacaoSocio infSocio = new InformacaoSocio();
		infSocio.setCliente(cliente);
		infSocio.setDataEvento(Calendar.getInstance());
		infSocio.setSocio(cliente.isSocio());
		em.persist(infSocio);
	}

	@Override
	public Cliente searchByID(Integer id) {
		Cliente cliente = em.find(Cliente.class, id);
		cliente.setSocio(this.isSocio(cliente));
		return cliente;
	}

	@Override
	public List<Cliente> getAll() {

		String subQuerySocio = "select socio from InformacaoSocio s "
				+ "Where s.id = (select MAX(s2.id) from InformacaoSocio s2 Where s2.cliente.id = c.id) ";

		String strQuery = "Select c, (" + subQuerySocio + ") from Cliente c ";
		TypedQuery<Object[]> query = null;
		query = em.createQuery(strQuery, Object[].class);

		List<Cliente> clientes = new ArrayList<Cliente>();
		try {
			List<Object[]> objects = query.getResultList();
			for (Object[] o : objects) {
				Cliente cliente = (Cliente) o[0];
				if (o[1] != null) {
					cliente.setSocio((Boolean) o[1]);
				}
				clientes.add(cliente);
			}
		} catch (NoResultException e) {
		}

		return clientes;
	}

	@Override
	public void update(Cliente cliente) {
		try {
			em.getTransaction().begin();
			em.merge(cliente);
			if (this.isSocio(cliente) != cliente.isSocio()) {
				inseriRegistroSocio(cliente);
			}
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		}
	}

	@Override
	public void insert(Cliente cliente) {
		try {
			em.getTransaction().begin();
			em.persist(cliente);
			if (cliente.isSocio()) {
				inseriRegistroSocio(cliente);
			}
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			e.printStackTrace();
		}
	}

	@Override
	public boolean isSocio(Cliente cliente) {
		String strQuery = "select socio from InformacaoSocio s "
				+ "Where s.id = (select MAX(s2.id) from InformacaoSocio s2 Where s2.cliente = :cliente) ";
		Query query = em.createQuery(strQuery);
		query.setParameter("cliente", cliente);
		boolean socio = false;
		try {
			socio = (Boolean) query.getSingleResult();
		} catch (NoResultException e) {
		}
		return socio;
	}

	@Override
	public List<InformacaoSocio> getInformacoesSocio(Cliente cliente) {
		try {
			String strQuery = " select i from InformacaoSocio i where i.cliente = :cliente "
					+ " order by i.id asc ";
			TypedQuery<InformacaoSocio> query = em.createQuery(strQuery,
					InformacaoSocio.class);
			query.setParameter("cliente", cliente);
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public int calculaQuantosMesesOClienteESocio(Cliente cliente){
		List<InformacaoSocio> informacoesSocio = this.getInformacoesSocio(cliente);
		if(informacoesSocio.size() == 0){
			return 0;
		}
		int i = 1;
		boolean calcula = false;
		LocalDate dataVirouSocio = null;
		LocalDate dataDeixouDeSerSocio = null;
		int qtdMesComoSocio = 0;
		
		for (InformacaoSocio informacaoSocio : informacoesSocio) {
		
			// indice impar é data inicial e id par é data final, caso nao tenha id par, sera data atual
			// primeiro registro sempre sera socio = true, segundo sempre quando deixa de ser sócio
			if(i % 2 > 0){
				dataVirouSocio = LocalDate.of(
						informacaoSocio.getDataEvento().get(Calendar.YEAR), 
						informacaoSocio.getDataEvento().get(Calendar.MONTH), 
						informacaoSocio.getDataEvento().get(Calendar.DAY_OF_MONTH));
			}else{
				dataDeixouDeSerSocio = LocalDate.of(
						informacaoSocio.getDataEvento().get(Calendar.YEAR), 
						informacaoSocio.getDataEvento().get(Calendar.MONTH), 
						informacaoSocio.getDataEvento().get(Calendar.DAY_OF_MONTH)); 
				calcula = true;
			}
			
			if(calcula){
				qtdMesComoSocio += ChronoUnit.MONTHS.between(dataVirouSocio, dataDeixouDeSerSocio); 
				dataVirouSocio = null;
				dataDeixouDeSerSocio = null;
				calcula = false;
			}
			
			i++;
		}
		
		// se existir apenas registro de sócio = true, calcula com data atual
		if((i % 2 == 0) && dataDeixouDeSerSocio == null){
			qtdMesComoSocio += ChronoUnit.MONTHS.between(dataVirouSocio, LocalDate.now());
		}
		return qtdMesComoSocio;
	}

	@Override
	public int calculaQuantasMensalidadeForamPagas(Cliente cliente) {

		try {
			String strQuery = "select SUM(ds.servico.quantosMesesVale) from DebitoServico ds "
					+ " where ds.debito.cliente = :cliente "
					+ " and ds.debito.status in (:status) "
					+ " and ds.servico.mensalidade = :mensalidade";

			List<StatusDebitoEnum> statusPermitido = new ArrayList<StatusDebitoEnum>();
			statusPermitido.add(StatusDebitoEnum.RECEBIDO);
			statusPermitido.add(StatusDebitoEnum.RECOLHIDO);

			TypedQuery<Long> query = em.createQuery(strQuery, Long.class);
			query.setParameter("cliente", cliente);
			query.setParameter("status", statusPermitido);
			query.setParameter("mensalidade", true);

			return Integer.parseInt(query.getSingleResult().toString());
		} catch (NoResultException e) {
			return 0;
		} catch (Exception e) {
			return 0;
		}
	}

	@Override
	public InformacaoMensalidade estaEmDiaComAsMensalidades(Cliente cliente) {
		InformacaoMensalidade retorno = new InformacaoMensalidade();

		retorno.setMesesComoSocio(this
				.calculaQuantosMesesOClienteESocio(cliente));
		retorno.setMensalidadesPagas(this
				.calculaQuantasMensalidadeForamPagas(cliente));

		if (retorno.getMesesComoSocio() == 0) {
			if (cliente.isSocio()) {
				retorno.setMensagem("Cliente ainda não completou 1 mês como sócio");
			} else {
				retorno.setMensagem("Cliente nunca foi sócio");
			}
			retorno.setAtrasado(false);
		} else if (retorno.getMesesComoSocio() > retorno.getMensalidadesPagas()) {
			int diferenca = retorno.getMesesComoSocio()
					- retorno.getMensalidadesPagas();
			retorno.setMensagem(diferenca + " mensalidades atrasadas");
			retorno.setAtrasado(true);
		} else {
			int diferenca = retorno.getMensalidadesPagas()
					- retorno.getMesesComoSocio();
			retorno.setMensagem("Cliente possui " + diferenca
					+ " mensalidades de crédito");
			retorno.setAtrasado(false);
		}
		return retorno;
	}

	@Override
	public List<MensalidadePaga> getInformacoesMensalidade(Cliente cliente) {

		String strQuery = " select "
				+ " ds.servico.descricao, ds.debito.dataEmissaoNotaCobranca "
				+ " from DebitoServico ds "
				+ " where ds.debito.cliente = :cliente "
				+ " and ds.servico.mensalidade = :mensalidade"
				+ " and ds.debito.status in (:status)";

		List<StatusDebitoEnum> statusPermitido = new ArrayList<StatusDebitoEnum>();
		statusPermitido.add(StatusDebitoEnum.NOTACOBRANCAGERADA);
		statusPermitido.add(StatusDebitoEnum.RECEBIDO);
		statusPermitido.add(StatusDebitoEnum.RECOLHIDO);

		TypedQuery<Object[]> query = em.createQuery(strQuery, Object[].class);
		query.setParameter("cliente", cliente);
		query.setParameter("mensalidade", true);
		query.setParameter("status", statusPermitido);

		List<Object[]> result = query.getResultList();
		List<MensalidadePaga> mensalidadesPagas = new ArrayList<MensalidadePaga>();
		for (Object[] objects : result) {
			MensalidadePaga inf = new MensalidadePaga();
			inf.setDataPagamento((Calendar) objects[1]);
			inf.setDescricaoMensalidade(objects[0].toString());

			mensalidadesPagas.add(inf);
		}

		return mensalidadesPagas;
	}

	@Override
	public int count(Map<String, String> filters) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<Cliente> myObj = cq.from(Cliente.class);
		cq.where(this.getFilterCondition(cb, myObj, filters));
		cq.select(cb.count(myObj));
		return em.createQuery(cq).getSingleResult().intValue();
	}

	private Predicate getFilterCondition(CriteriaBuilder cb,
			Root<Cliente> myObj, Map<String, String> filters) {
		Predicate filterCondition = cb.conjunction();
		for (Map.Entry<String, String> filter : filters.entrySet()) {

			if (!filter.getValue().equals("")) {
				javax.persistence.criteria.Path<String> path = myObj.get(filter
						.getKey());
				myObj.get(filter.getKey()).getJavaType();
				try {
					String columnType = myObj.get(filter.getKey())
							.getJavaType().toString();
					if (columnType.contains("String")) {
						String value = "%" + filter.getValue().toUpperCase()
								+ "%";
						filterCondition = cb.and(filterCondition,
								cb.like(cb.upper(path), value));
					} else {
						filterCondition = cb.and(filterCondition,
								cb.equal(path, filter.getValue()));
					}
				} catch (Exception e) {
					filterCondition = cb.and(filterCondition,
							cb.equal(path, filter.getValue()));
				}
			}
		}
		return filterCondition;
	}

	@Override
	public List<Cliente> getResultListFiltered(int first, int pageSize,
			String sortField, SortOrder sortOrder, Map<String, String> filters) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Cliente> cq = cb.createQuery(Cliente.class);
		Root<Cliente> myObj = cq.from(Cliente.class);
		cq.where(this.getFilterCondition(cb, myObj, filters));
		if (sortField != null) {
			if (sortOrder == SortOrder.ASCENDING) {
				cq.orderBy(cb.asc(myObj.get(sortField)));
			} else if (sortOrder == SortOrder.DESCENDING) {
				cq.orderBy(cb.desc(myObj.get(sortField)));
			}
		}

		List<Cliente> clientes = em.createQuery(cq).setFirstResult(first)
				.setMaxResults(pageSize).getResultList();
		for (Cliente cliente : clientes) {
			if (cliente != null) {
				cliente.setSocio(this.isSocio(cliente));
			}
		}
		return clientes;
	}

}