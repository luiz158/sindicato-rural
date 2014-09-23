package com.sindicato.MB.cadastros;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.sindicato.MB.util.UtilBean;
import com.sindicato.controlefinanceiro.dao.DestinoRecebimentoDAO;
import com.sindicato.controlefinanceiro.entity.DestinoRecebimento;

@ManagedBean
@ViewScoped
public class DestinoRecebimentoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB private DestinoRecebimentoDAO destinoDAO;

	private DestinoRecebimento destinoSelecionado;
	private List<DestinoRecebimento> destinos;

	private int indexTab;

	public void alterTab(int newTab) {
		indexTab = newTab;
	}

	public void reset() {
		destinoSelecionado = new DestinoRecebimento();
	}

	public void salvar() {
		try {
			if (destinoSelecionado.getId() == 0) {
				destinoDAO.insert(destinoSelecionado);
			} else {
				destinoDAO.update(destinoSelecionado);
			}
			this.reset();
			alterTab(0);
			UtilBean.addMessageAndRemoveOthers(FacesMessage.SEVERITY_INFO,
					"Sucesso", "Destino de recebimento salvo com sucesso");
		} catch (Exception e) {
			e.printStackTrace();
			UtilBean.addMessageAndRemoveOthers(FacesMessage.SEVERITY_ERROR,
					"Erro", "Contate o administrador do sistema");
		}
	}

	public void novo(){
		this.reset();
		alterTab(1);
	}
	
	public void excluir(DestinoRecebimento destino) {
		try {
			if(destino.getId() == 0){ return; }
			
			destinoDAO.removeById(destino.getId());
			this.reset();
			alterTab(0);
			UtilBean.addMessageAndRemoveOthers(FacesMessage.SEVERITY_INFO,
					"Sucesso", "Destino de recebimento excluído com sucesso");
		} catch (Exception e) {
			e.printStackTrace();
			UtilBean.addMessageAndRemoveOthers(FacesMessage.SEVERITY_ERROR,
					"Erro", "Contate o administrador do sistema");
		}
	}
	public DestinoRecebimento getDestinoSelecionado() {
		if (destinoSelecionado == null) {
			destinoSelecionado = new DestinoRecebimento();
		}
		return destinoSelecionado;
	}
	public List<DestinoRecebimento> getDestinos() {
		destinos = destinoDAO.getAll();
		return destinos;
	}
	public int getIndexTab() {
		return indexTab;
	}
	public void setIndexTab(int indexTab) {
		this.indexTab = indexTab;
	}
	public void setDestinoSelecionado(DestinoRecebimento destinoSelecionado) {
		this.destinoSelecionado = destinoSelecionado;
	}
	public void setDestinos(List<DestinoRecebimento> destinos) {
		this.destinos = destinos;
	}

}
