package com.sindicato.MB.financeiro;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.primefaces.component.commandbutton.CommandButton;

import com.sindicato.MB.reports.GeradorReports;
import com.sindicato.MB.util.UtilBean;
import com.sindicato.dao.FinanceiroDAO;
import com.sindicato.dao.ListasDAO;
import com.sindicato.dao.ServicoDAO;
import com.sindicato.entity.Debito;
import com.sindicato.entity.DebitoServico;
import com.sindicato.entity.Servico;
import com.sindicato.entity.Enum.StatusDebitoEnum;
import com.sindicato.entity.autenticacao.Usuario;
import com.sindicato.result.ResultOperation;
import com.sindicato.util.Extenso;

@ManagedBean
@ViewScoped
public class ManutencaoNotaBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private Usuario usuarioLogado = UtilBean.getUsuarioLogado();

	@EJB private ListasDAO listasDAO;
	@EJB private FinanceiroDAO financeiroDAO;
	@EJB private ServicoDAO servicoDAO;

	private List<Servico> servicos;

	private Debito debitoSelecionado;
	private List<Debito> debitos;

	private DebitoServico debitoServico;
	
	private int indexTab;

	private CommandButton botaoImprimir;
	
	public void alterTab(int newTab) {
		indexTab = newTab;
	}

	public void reset() {
		debitoSelecionado = new Debito();
	}

	public void salvarServico(){
		debitoServico.setDebito(debitoSelecionado);
		debitoSelecionado.getDebitoServicos().add(debitoServico);
		debitoServico = new DebitoServico();
		botaoImprimir.setDisabled(true);
	}
	
	public void removerServico(DebitoServico servico){
		debitoSelecionado.getDebitoServicos().remove(servico);
		botaoImprimir.setDisabled(true);
	}
	
	public void cancelar() {
		try {
			ResultOperation result = financeiroDAO.cancelarNotaDeCobranca(debitoSelecionado);
			if(result.isSuccess()){
				UtilBean.addMessageAndRemoveOthers(FacesMessage.SEVERITY_INFO,
						"Sucesso", "Nota de cobrança " + debitoSelecionado.getId() + " foi cancelada com sucesso");
				this.reset();
				alterTab(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			UtilBean.addMessageAndRemoveOthers(FacesMessage.SEVERITY_ERROR,
					"Erro", "Contate o administrador do sistema");
		}
	}
	
	public void salvar() {
		try {
			ResultOperation result = financeiroDAO.salvarAlteracaoNotaCobranca(debitoSelecionado);
			if(result.isSuccess()){
				UtilBean.addMessageAndRemoveOthers(FacesMessage.SEVERITY_INFO,
						"Sucesso", result.getMessage());
				botaoImprimir.setDisabled(false);
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
 
	public int getIndexTab() {
		return indexTab;
	}

	public DebitoServico getDebitoServico() {
		if(debitoServico == null){
			debitoServico = new DebitoServico();
		}
		return debitoServico;
	}

	public List<Servico> getServicos() {
		servicos = servicoDAO.getAll();
		return servicos;
	}


	public CommandButton getBotaoImprimir() {
		return botaoImprimir;
	}

	public void setBotaoImprimir(CommandButton botaoImprimir) {
		this.botaoImprimir = botaoImprimir;
	}

	public void setServicos(List<Servico> servicos) {
		this.servicos = servicos;
	}

	public void setDebitoServico(DebitoServico debitoServico) {
		this.debitoServico = debitoServico;
	}

	public void setDebitoSelecionado(Debito debitoSelecionado) {
		this.debitoSelecionado = debitoSelecionado;
	}

	public void setDebitos(List<Debito> debitos) {
		this.debitos = debitos;
	}

	public void setIndexTab(int indexTab) {
		this.indexTab = indexTab;
	}

	

}
