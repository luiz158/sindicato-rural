package com.sindicato.dao.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.sindicato.dao.ClienteDAO;
import com.sindicato.entity.Cliente;
import com.sindicato.entity.InformacaoSocio;
import com.sindicato.entity.Enum.StatusDebitoEnum;
import com.sindicato.result.InformacaoMensalidade;
import com.sindicato.result.MensalidadePaga;
import com.sindicato.result.ResultOperation;

@Stateless
public class ClienteDAOImpl extends DAOImpl<Cliente, Integer> implements
		ClienteDAO {

	@Override
	public List<Cliente> getAll(){
		String strQuery = "Select c from Cliente c "
				+ " Where c.ativo = true "
				+ " order by c.nome ";
		TypedQuery<Cliente> query = null;
		query = em.createQuery(strQuery, Cliente.class);
		return query.getResultList();
	}

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
		return cliente;
	}

	@Override
	public void desativarCliente(Cliente cliente){
		try {
			cliente.setAtivo(false);
			em.merge(cliente);
			if (cliente.isSocio()) {
				cliente.setSocio(false);
				inseriRegistroSocio(cliente);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void update(Cliente cliente) {
		try {
			boolean socio_old = em.find(Cliente.class, cliente.getId()).isSocio();
			em.merge(cliente);
			if (socio_old != cliente.isSocio()) {
				inseriRegistroSocio(cliente);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void insert(Cliente cliente) {
		try {
			em.persist(cliente);
			if (cliente.isSocio()) {
				inseriRegistroSocio(cliente);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
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
						informacaoSocio.getDataEvento().get(Calendar.MONTH) + 1, 
						informacaoSocio.getDataEvento().get(Calendar.DAY_OF_MONTH));
			}else{
				dataDeixouDeSerSocio = LocalDate.of(
						informacaoSocio.getDataEvento().get(Calendar.YEAR), 
						informacaoSocio.getDataEvento().get(Calendar.MONTH) + 1, 
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
			retorno.setMensagem(diferenca + " mensalidade(s) atrasada(s)");
			retorno.setAtrasado(true);
		} else {
			int diferenca = retorno.getMensalidadesPagas()
					- retorno.getMesesComoSocio();
			retorno.setMensagem(diferenca
					+ " mensalidade(s) de crédito");
			retorno.setAtrasado(false);
		}
		return retorno;
	}

	@Override
	public List<MensalidadePaga> getInformacoesMensalidade(Cliente cliente) {

		String strQuery = " select "
				+ " ds.servico.descricao, ds.debito.dataBase, ds.valor "
				+ " from DebitoServico ds "
				+ " where ds.debito.cliente = :cliente "
				+ " and ds.servico.mensalidade = :mensalidade"
				+ " and ds.debito.status in (:status)";

		List<StatusDebitoEnum> statusPermitido = new ArrayList<StatusDebitoEnum>();
		//statusPermitido.add(StatusDebitoEnum.NOTACOBRANCAGERADA);
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
			inf.setValor((BigDecimal) objects[2]);
			
			mensalidadesPagas.add(inf);
		}

		return mensalidadesPagas;
	}

	@Override
	public int count(Map<String, Object> filters) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<Cliente> myObj = cq.from(Cliente.class);
		cq.where(this.getFilterCondition(cb, myObj, filters));
		cq.select(cb.count(myObj));
		return em.createQuery(cq).getSingleResult().intValue();
	}

	private Predicate getFilterCondition(CriteriaBuilder cb,
			Root<Cliente> myObj, Map<String, Object> filters) {
		Predicate filterCondition = cb.conjunction();

		// ignora clientes desativados
		filters.put("ativo", Boolean.TRUE);
		
		for (Map.Entry<String, Object> filter : filters.entrySet()) {

			if (!filter.getValue().equals("")) {
				javax.persistence.criteria.Path<String> path = myObj.get(filter
						.getKey());
				myObj.get(filter.getKey()).getJavaType();
				try {
					String columnType = myObj.get(filter.getKey())
							.getJavaType().toString();
					if (columnType.contains("String")) {
						String value = "%" + filter.getValue().toString().toUpperCase()
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
			String sortField, String sortOrder, Map<String, Object> filters) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Cliente> cq = cb.createQuery(Cliente.class);
		Root<Cliente> myObj = cq.from(Cliente.class);
		cq.where(this.getFilterCondition(cb, myObj, filters));
		if (sortField != null) {
			if (sortOrder == "ASCENDING") {
				cq.orderBy(cb.asc(myObj.get(sortField)));
			} else if (sortOrder == "DESCENDING") {
				cq.orderBy(cb.desc(myObj.get(sortField)));
			}
		}

		List<Cliente> clientes = em.createQuery(cq).setFirstResult(first)
				.setMaxResults(pageSize).getResultList();
		return clientes;
	}

	
	@Override
	public ResultOperation salvar(Cliente cliente) {
		// TODO Auto-generated method stub
		return null;
	}

}