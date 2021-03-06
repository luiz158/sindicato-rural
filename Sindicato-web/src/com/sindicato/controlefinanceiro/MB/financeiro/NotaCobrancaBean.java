package com.sindicato.controlefinanceiro.MB.financeiro;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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

import com.sindicato.MB.util.UtilBean;
import com.sindicato.controlefinanceiro.dao.DebitoDAO;
import com.sindicato.controlefinanceiro.dao.FinanceiroDAO;
import com.sindicato.controlefinanceiro.dao.ListasCFDAO;
import com.sindicato.controlefinanceiro.entity.Cliente;
import com.sindicato.controlefinanceiro.entity.Debito;
import com.sindicato.controlefinanceiro.entity.Enum.StatusDebitoEnum;
import com.sindicato.controlefinanceiro.reports.GeradorReports;
import com.sindicato.painelcontrole.entity.Usuario;
import com.sindicato.result.ResultOperation;
import com.sindicato.util.Extenso;

@ManagedBean
@ViewScoped
public class NotaCobrancaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Usuario usuarioLogado = UtilBean.getUsuarioLogado();
	
	@EJB private FinanceiroDAO financeiroDAO;
	@EJB private ListasCFDAO listasDAO;
	@EJB private DebitoDAO debitoDAO;

	private List<Cliente> clientes;
	private List<Debito> debitos;
	private Cliente clienteSelecionado;
	private Debito debitoSelecionado;

	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");


	public void selecionaCliente() {
		debitos = listasDAO.getDebitosDoClienteNoStatus(clienteSelecionado,
				StatusDebitoEnum.DEBITOCRIADO);
	}

	public void reset() {
		debitoSelecionado = new Debito();
		clienteSelecionado = new Cliente();
	}


	public void gerarNotaDeCobranca() throws JRException {
		ResultOperation result;
		try {
			result = financeiroDAO.gerarNotaDeCobranca(debitoSelecionado);
			if (result.isSuccess()) {
				debitoSelecionado = debitoDAO.searchByID(debitoSelecionado.getId());
				this.imprimirNotaCobranca();
				this.reset();
				UtilBean.addMessageAndRemoveOthers(FacesMessage.SEVERITY_INFO,
						"Sucesso", "Nota de cobran�a gerada com sucesso");
			} else {
				UtilBean.addMessageAndRemoveOthers(FacesMessage.SEVERITY_WARN,
						"Aten��o", result.getMessage());
			}

		} catch (Exception e) {
			UtilBean.addMessageAndRemoveOthers(FacesMessage.SEVERITY_ERROR,
					"Erro",
					"Nota de cobran�a n�o foi criada, contate o administrador");
			e.printStackTrace();
		}
	}
	
	private void imprimirNotaCobranca() throws JRException, IOException{
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
		
		BigDecimal valorNota = debitoSelecionado.getTotalDebitos();
		String valorNotaExtenso;
		Extenso extenso = new Extenso();
		if(valorNota.compareTo(BigDecimal.ZERO) < 0){
			extenso.setNumber(debitoSelecionado.getTotalDebitos().multiply(BigDecimal.valueOf(-1)));
			valorNotaExtenso = extenso.toString() + " negativo";
		}else{
			extenso.setNumber(debitoSelecionado.getTotalDebitos());
			valorNotaExtenso = extenso.toString();
		}
		
		SimpleDateFormat format = new SimpleDateFormat("MMMMM/yyyy");
		SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yyyy");
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("cartaSindical", usuarioLogado.getEmpresa().getCartaSindical());
		parameters.put("cnpj", usuarioLogado.getEmpresa().getCnpj());
		parameters.put("endereco", this.getEnderecoCompletoUsuario(usuarioLogado));
		parameters.put("telefones", telefones.toString());
		parameters.put("fax", usuarioLogado.getEmpresa().getFax());
		parameters.put("email", usuarioLogado.getEmpresa().getEmail());

		parameters.put("clienteId", clienteSelecionado.getId());
		parameters.put("clienteNome", clienteSelecionado.getNome());
		parameters.put("socio", (clienteSelecionado.isSocio()) ? "Sim" : "N�o");
		parameters.put("valorPorExtenso", valorNotaExtenso);

		NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
		String valorNotaFormatada = numberFormat.format(debitoSelecionado.getTotalDebitos());
		parameters.put("valorNota", valorNotaFormatada);

		parameters.put("data", format.format(debitoSelecionado.getDataBase().getTime()));
		parameters.put("dataEmissao", format2.format(debitoSelecionado.getDataEmissaoNotaCobranca().getTime()));
		parameters.put("usuario", usuarioLogado.getNome());
		parameters.put("numeroNota", debitoSelecionado.getNumeroNota());
		
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
	
	public List<Cliente> getClientes() {
		clientes = listasDAO
				.getClientesComDebitosNoStatus(StatusDebitoEnum.DEBITOCRIADO);
		return clientes;
	}

	public List<Debito> getDebitos() {
		if (debitos == null) {
			debitos = new ArrayList<Debito>();
		}
		return debitos;
	}

	public Cliente getClienteSelecionado() {
		if (clienteSelecionado == null) {
			clienteSelecionado = new Cliente();
		}
		return clienteSelecionado;
	}

	public Debito getDebitoSelecionado() {
		/*
		 * if(debitoSelecionado == null){ debitoSelecionado = new Debito(); }
		 */
		return debitoSelecionado;
	}

	public SimpleDateFormat getSdf() {
		return sdf;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public void setDebitos(List<Debito> debitos) {
		this.debitos = debitos;
	}

	public void setClienteSelecionado(Cliente clienteSelecionado) {
		this.clienteSelecionado = clienteSelecionado;
	}

	public void setDebitoSelecionado(Debito debitoSelecionado) {
		this.debitoSelecionado = debitoSelecionado;
	}

}
