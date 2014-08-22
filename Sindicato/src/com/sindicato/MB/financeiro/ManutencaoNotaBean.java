package com.sindicato.MB.financeiro;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.primefaces.component.dialog.Dialog;
import org.primefaces.component.fieldset.Fieldset;
import org.primefaces.component.tabview.TabView;
import org.primefaces.model.LazyDataModel;

import com.sindicato.MB.util.UtilBean;
import com.sindicato.dao.FinanceiroDAO;
import com.sindicato.dao.ListasDAO;
import com.sindicato.dao.ModoPagamentoDAO;
import com.sindicato.dao.ServicoDAO;
import com.sindicato.entity.Debito;
import com.sindicato.entity.DebitoServico;
import com.sindicato.entity.DestinoRecebimento;
import com.sindicato.entity.ModoPagamento;
import com.sindicato.entity.Recebimento;
import com.sindicato.entity.Servico;
import com.sindicato.entity.TipoRecebimento;
import com.sindicato.entity.Enum.StatusDebitoEnum;
import com.sindicato.entity.autenticacao.Usuario;
import com.sindicato.lazyDataModel.LazyDebitoDataModel;
import com.sindicato.reports.GeradorReports;
import com.sindicato.result.ResultOperation;
import com.sindicato.util.Extenso;

@ManagedBean
@ViewScoped
public class ManutencaoNotaBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private Usuario usuarioLogado = UtilBean.getUsuarioLogado();
	@EJB
	private ListasDAO listasDAO;
	@EJB
	private FinanceiroDAO financeiroDAO;
	@EJB
	private ServicoDAO servicoDAO;
	@EJB
	private ModoPagamentoDAO modoPagamentoDAO;
	private List<Servico> servicos;
	private Debito debitoSelecionado;
	private LazyDataModel<Debito> debitos;
	// servicos
	private DebitoServico debitoServico;
	// recebimentos
	private List<TipoRecebimento> tiposRecebimento;
	private Recebimento recebimento;
	private Recebimento recebimentoSelecionado;
	private List<DestinoRecebimento> destinos;
	private Dialog dialogInfRecebimento;
	private Dialog dialogFormRecebimento;
	// recolhimentos
	private List<DebitoServico> servicosComRetencao;
	private List<ModoPagamento> modosPagamento;
	private int indexTab;
	private TabView tabView;
	private CommandButton botaoImprimir;
	private Fieldset fieldSetRecebimentos;
	private Fieldset fieldSetRecolhimentos;

	public void selecionaDebito() {
		tabView.setActiveIndex(1);
		if (debitoSelecionado.getStatus().equals(StatusDebitoEnum.RECOLHIDO)) {
			fieldSetRecebimentos.setRendered(true);
			fieldSetRecolhimentos.setRendered(true);
		} else if (debitoSelecionado.getStatus().equals(StatusDebitoEnum.RECEBIDO)) {
			fieldSetRecebimentos.setRendered(true);
			fieldSetRecolhimentos.setRendered(false);
		} else {
			fieldSetRecebimentos.setRendered(false);
			fieldSetRecolhimentos.setRendered(false);
		}
	}
	public void selecionaRecebimento(Recebimento recebimento) {
		this.recebimentoSelecionado = recebimento;
		this.dialogInfRecebimento.setVisible(true);
	}
	public void editarRecebimento(Recebimento recebimento) {
		this.recebimento = recebimento;
		this.dialogFormRecebimento.setVisible(true);
	}
	public void adicionarRecebimento() {
		this.recebimento = new Recebimento();
		this.dialogFormRecebimento.setVisible(true);
	}
	public void reset() {
		debitoSelecionado = new Debito();
	}
	// SERVICOS
	public void salvarServico() {
		debitoServico.setDebito(debitoSelecionado);
		debitoSelecionado.getDebitoServicos().add(debitoServico);
		debitoServico = new DebitoServico();
		botaoImprimir.setDisabled(true);
	}
	public void removerServico(DebitoServico servico) {
		debitoSelecionado.getDebitoServicos().remove(servico);
		botaoImprimir.setDisabled(true);
	}
	// RECEBIMENTOS
	public void salvarRecebimento() {
		if (recebimento.getId() == 0 && !debitoSelecionado.getRecebimentos().contains(recebimento)) {
			recebimento.setDebito(debitoSelecionado);
			debitoSelecionado.getRecebimentos().add(recebimento);
			recebimento = new Recebimento();
		}
		this.dialogFormRecebimento.setVisible(false);
	}
	public void removerRecebimento(Recebimento recebimento) {
		debitoSelecionado.getRecebimentos().remove(recebimento);
	}
	// RECOLHIMENTOS
	private void mergeServicosComRetencao() {
		for (DebitoServico debito : servicosComRetencao) {
			for (DebitoServico debitoSelecionado : debitoSelecionado.getDebitoServicos()) {
				if (debitoSelecionado.equals(debito)) {
					debitoSelecionado.setRecolhimento(debito.getRecolhimento());
				}
			}
		}
	}
	public void cancelar() {
		try {
			ResultOperation result = financeiroDAO.cancelarNotaDeCobranca(debitoSelecionado);
			if (result.isSuccess()) {
				UtilBean.addMessageAndRemoveOthers(FacesMessage.SEVERITY_INFO, "Sucesso", "Nota de cobrança "
						+ debitoSelecionado.getNumeroNota() + " foi cancelada com sucesso");
				this.reset();
				tabView.setActiveIndex(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			UtilBean.addMessageAndRemoveOthers(FacesMessage.SEVERITY_ERROR, "Erro",
					"Contate o administrador do sistema");
		}
	}
	public void salvar() {
		try {
			if (debitoSelecionado.getStatus().equals(StatusDebitoEnum.RECOLHIDO)) {
				this.mergeServicosComRetencao();
			}
			if (debitoSelecionado.getStatus().equals(StatusDebitoEnum.RECEBIDO)
					|| debitoSelecionado.getStatus().equals(StatusDebitoEnum.RECOLHIDO)) {
				if (!UtilBean.confereValorRecebimentos(debitoSelecionado)) {
					UtilBean.addMessageAndRemoveOthers(FacesMessage.SEVERITY_WARN, "Ooops...",
							"Verifique os valores de recebimento, pois não confere com o valor da Nota");
					return;
				}
			}
			ResultOperation result = financeiroDAO.salvarAlteracaoNotaCobranca(debitoSelecionado);
			if (result.isSuccess()) {
				UtilBean.addMessageAndRemoveOthers(FacesMessage.SEVERITY_INFO, "Sucesso", result.getMessage());
				if (debitoSelecionado.getStatus().equals(StatusDebitoEnum.NOTACOBRANCAGERADA)) {
					botaoImprimir.setDisabled(false);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			UtilBean.addMessageAndRemoveOthers(FacesMessage.SEVERITY_ERROR, "Erro",
					"Contate o administrador do sistema");
		}
	}
	public void imprimirNotaCobranca() throws JRException, IOException {
		GeradorReports gerador = preparaValoresReport();
		FacesContext context = UtilBean.getFacesContext();
		HttpServletResponse httpServletResponse = (HttpServletResponse) context.getExternalContext().getResponse();
		httpServletResponse.setContentType("application/pdf");
		ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
		JasperExportManager.exportReportToPdfStream(gerador.getJasperPrint(), servletOutputStream);
		servletOutputStream.flush();
		servletOutputStream.close();
		context.responseComplete();
	}
	private GeradorReports preparaValoresReport() throws JRException {
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
		parameters.put("clienteId", debitoSelecionado.getCliente().getId());
		parameters.put("clienteNome", debitoSelecionado.getCliente().getNome());
		parameters.put("socio", (debitoSelecionado.getCliente().isSocio()) ? "Sim" : "Não");
		parameters.put("valorPorExtenso", valorNotaExtenso);
		parameters.put("valorNota", debitoSelecionado.getTotalDebitos());
		parameters.put("data", format.format(debitoSelecionado.getDataBase().getTime()));
		parameters.put("dataEmissao", format2.format(debitoSelecionado.getDataEmissaoNotaCobranca().getTime()));
		parameters.put("usuario", usuarioLogado.getNome());
		parameters.put("numeroNota", debitoSelecionado.getNumeroNota());
		GeradorReports gerador = new GeradorReports("notaCobranca.jasper", parameters, new JRBeanCollectionDataSource(
				debitoSelecionado.getDebitoServicos()));
		return gerador;
	}
	private String getEnderecoCompletoUsuario(Usuario usuario) {
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
		if (debitoSelecionado == null) {
			debitoSelecionado = new Debito();
		}
		return debitoSelecionado;
	}
	public LazyDataModel<Debito> getDebitos() {
		if (debitos == null) {
			List<StatusDebitoEnum> statusPermitido = new ArrayList<StatusDebitoEnum>();
			statusPermitido.add(StatusDebitoEnum.NOTACOBRANCAGERADA);
			statusPermitido.add(StatusDebitoEnum.RECEBIDO);
			statusPermitido.add(StatusDebitoEnum.RECOLHIDO);
			debitos = new LazyDebitoDataModel(statusPermitido);
		}
		return debitos;
	}
	public int getIndexTab() {
		return indexTab;
	}
	public DebitoServico getDebitoServico() {
		if (debitoServico == null) {
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
	public List<TipoRecebimento> getTiposRecebimento() {
		if (tiposRecebimento == null) {
			tiposRecebimento = listasDAO.getTodasFormasRecebimento();
		}
		return tiposRecebimento;
	}
	public Recebimento getRecebimento() {
		if (recebimento == null) {
			recebimento = new Recebimento();
		}
		return recebimento;
	}
	public List<DestinoRecebimento> getDestinos() {
		if (destinos == null) {
			destinos = listasDAO.getTodosDestinosRecebimento();
		}
		return destinos;
	}
	public List<DebitoServico> getServicosComRetencao() {
		servicosComRetencao = new ArrayList<DebitoServico>();
		for (DebitoServico debito : debitoSelecionado.getDebitoServicos()) {
			if (debito.getServico().isRetencao()) {
				servicosComRetencao.add(debito);
			}
		}
		return servicosComRetencao;
	}
	public List<ModoPagamento> getModosPagamento() {
		if (modosPagamento == null) {
			modosPagamento = modoPagamentoDAO.getAll();
		}
		return modosPagamento;
	}
	public Fieldset getFieldSetRecebimentos() {
		return fieldSetRecebimentos;
	}
	public Fieldset getFieldSetRecolhimentos() {
		return fieldSetRecolhimentos;
	}
	public TabView getTabView() {
		return tabView;
	}
	public Recebimento getRecebimentoSelecionado() {
		return recebimentoSelecionado;
	}
	public Dialog getDialogInfRecebimento() {
		return dialogInfRecebimento;
	}
	public Dialog getDialogFormRecebimento() {
		return dialogFormRecebimento;
	}
	public void setDialogFormRecebimento(Dialog dialogEditRecebimento) {
		this.dialogFormRecebimento = dialogEditRecebimento;
	}
	public void setDialogInfRecebimento(Dialog dialogInfRecebimento) {
		this.dialogInfRecebimento = dialogInfRecebimento;
	}
	public void setRecebimentoSelecionado(Recebimento recebimentoSelecionado) {
		this.recebimentoSelecionado = recebimentoSelecionado;
	}
	public void setTabView(TabView tabView) {
		this.tabView = tabView;
	}
	public void setFieldSetRecebimentos(Fieldset fieldSetRecebimentos) {
		this.fieldSetRecebimentos = fieldSetRecebimentos;
	}
	public void setFieldSetRecolhimentos(Fieldset fieldSetRecolhimentos) {
		this.fieldSetRecolhimentos = fieldSetRecolhimentos;
	}
	public void setServicosComRetencao(List<DebitoServico> servicosComRetencao) {
		this.servicosComRetencao = servicosComRetencao;
	}
	public void setModosPagamento(List<ModoPagamento> modosPagamento) {
		this.modosPagamento = modosPagamento;
	}
	public void setTiposRecebimento(List<TipoRecebimento> tiposRecebimento) {
		this.tiposRecebimento = tiposRecebimento;
	}
	public void setRecebimento(Recebimento recebimento) {
		this.recebimento = recebimento;
	}
	public void setDestinos(List<DestinoRecebimento> destinos) {
		this.destinos = destinos;
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
	public void setDebitos(LazyDataModel<Debito> debitos) {
		this.debitos = debitos;
	}
	public void setIndexTab(int indexTab) {
		this.indexTab = indexTab;
	}
}
