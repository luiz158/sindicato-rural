package com.sindicato.MB.financeiro;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.primefaces.component.commandbutton.CommandButton;

import com.sindicato.MB.reports.GeradorReports;
import com.sindicato.MB.util.UtilBean;
import com.sindicato.dao.EntityManagerFactorySingleton;
import com.sindicato.dao.FinanceiroDAO;
import com.sindicato.dao.ListasDAO;
import com.sindicato.dao.impl.FinanceiroDAOImpl;
import com.sindicato.dao.impl.ListasDAOImpl;
import com.sindicato.entity.Debito;
import com.sindicato.entity.DebitoServico;
import com.sindicato.entity.Recebimento;
import com.sindicato.entity.Servico;
import com.sindicato.entity.Enum.StatusDebitoEnum;
import com.sindicato.entity.autenticacao.Usuario;
import com.sindicato.result.ResultOperation;
import com.sindicato.util.Extenso;

@ManagedBean
@ViewScoped
public class RecebimentoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private Usuario usuarioLogado = UtilBean.getUsuarioLogado();

	private EntityManager em;
	private ListasDAO listasDAO;
	private FinanceiroDAO financeiroDAO;

	private Recebimento recebimento;
	
	private Debito debitoSelecionado;
	private List<Debito> debitos;

	private int indexTab;

	@PostConstruct
	public void init() {
		em = EntityManagerFactorySingleton.getInstance().createEntityManager();
		listasDAO = new ListasDAOImpl(em);
		financeiroDAO = new FinanceiroDAOImpl(em);
	}

	public void alterTab(int newTab) {
		indexTab = newTab;
	}

	public void reset() {
		debitoSelecionado = new Debito();
	}

	public void salvarRecebimento(){
		recebimento.setDebito(debitoSelecionado);
		debitoSelecionado.getRecebimentos().add(recebimento);
		recebimento = new Recebimento();
	}
	
	public void removerRecebimento(Recebimento recebimento){
		debitoSelecionado.getRecebimentos().remove(recebimento);
	}
	
	public void salvar() {
		try {
			ResultOperation result = financeiroDAO.registrarRecebimento(debitoSelecionado);
			if(result.isSuccess()){
				UtilBean.addMessageAndRemoveOthers(FacesMessage.SEVERITY_INFO,
						"Sucesso", result.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
			UtilBean.addMessageAndRemoveOthers(FacesMessage.SEVERITY_ERROR,
					"Erro", "Contate o administrador do sistema");
		}
	}

	public void imprimirNotaCobranca() throws JRException, IOException{
		GeradorReports gerador = preparaValoresReport();
		
		FacesContext context = UtilBean.getFacesContext();

		HttpServletResponse httpServletResponse = (HttpServletResponse) context
				.getExternalContext().getResponse();

		httpServletResponse.setContentType("application/pdf");

		ServletOutputStream servletOutputStream = httpServletResponse
				.getOutputStream();
		
		JasperExportManager.exportReportToPdfStream(gerador.getJasperPrint(),
				servletOutputStream);
		
		servletOutputStream.flush();  
        servletOutputStream.close();
		
		context.responseComplete();
	}
	
	private GeradorReports preparaValoresReport() throws JRException{
		StringBuilder telefones = new StringBuilder();
		telefones.append(usuarioLogado.getEmpresa().getTelefone());
		telefones.append(" / ");
		telefones.append(usuarioLogado.getEmpresa().getTelefone2());
		
		Extenso extenso = new Extenso();
		extenso.setNumber(debitoSelecionado.getTotalDebitos());
		
		SimpleDateFormat format = new SimpleDateFormat("MMMMM/yyyy");
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("cartaSindical", usuarioLogado.getEmpresa().getCartaSindical());
		parameters.put("cnpj", usuarioLogado.getEmpresa().getCnpj());
		parameters.put("endereco", this.getEnderecoCompletoUsuario(usuarioLogado));
		parameters.put("telefones", telefones.toString());
		parameters.put("fax", usuarioLogado.getEmpresa().getFax());
		parameters.put("email", usuarioLogado.getEmpresa().getEmail());

		parameters.put("clienteId", debitoSelecionado.getCliente().getId());
		parameters.put("clienteNome", debitoSelecionado.getCliente().getNome());
		parameters.put("socio", (debitoSelecionado.getCliente().isSocio()) ? "Sim" : "Não");
		parameters.put("valorPorExtenso", extenso.toString());
		parameters.put("valorNota", debitoSelecionado.getTotalDebitos());
		parameters.put("data", format.format(debitoSelecionado.getDataBase().getTime()));
		parameters.put("usuario", usuarioLogado.getNome());
		parameters.put("numeroNota", debitoSelecionado.getId());
		
		GeradorReports gerador = new GeradorReports("notaCobranca.jasper", parameters, new JRBeanCollectionDataSource(
				debitoSelecionado.getDebitoServicos()));
		return gerador;
	}
	
	private String getEnderecoCompletoUsuario(Usuario usuario){
		StringBuilder endereco = new StringBuilder();
		endereco.append(usuario.getEmpresa().getEndereco());
		endereco.append(" - ");
		endereco.append(usuario.getEmpresa().getBairro());
		endereco.append(" - CEP ");
		endereco.append(usuario.getEmpresa().getCep());
		endereco.append(" - ");
		endereco.append(usuario.getEmpresa().getCidade());
		endereco.append("/");
		endereco.append(usuario.getEmpresa().getEstado());
		return endereco.toString();
	}

	public Debito getDebitoSelecionado() {
		if(debitoSelecionado == null){
			debitoSelecionado = new Debito();
		}
		return debitoSelecionado;
	}

	public List<Debito> getDebitos() {
		debitos = listasDAO.getDebitosNoStatus(StatusDebitoEnum.NOTACOBRANCAGERADA);
		return debitos;
	}
 


}
