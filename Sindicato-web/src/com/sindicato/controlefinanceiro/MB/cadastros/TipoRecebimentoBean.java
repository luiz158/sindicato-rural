package com.sindicato.controlefinanceiro.MB.cadastros;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.sindicato.MB.util.UtilBean;
import com.sindicato.controlefinanceiro.dao.TipoRecebimentoDAO;
import com.sindicato.controlefinanceiro.entity.TipoRecebimento;

@ManagedBean
@ViewScoped
public class TipoRecebimentoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB private TipoRecebimentoDAO tipoDAO;

	private TipoRecebimento tipoSelecionado;
	private List<TipoRecebimento> tipos;

	private int indexTab;

	public void alterTab(int newTab) {
		indexTab = newTab;
	}

	public void reset() {
		tipoSelecionado = new TipoRecebimento();
	}

	public void salvar() {
		try {
			if (tipoSelecionado.getId() == 0) {
				tipoDAO.insert(tipoSelecionado);
			} else {
				tipoDAO.update(tipoSelecionado);
			}
			this.reset();
			alterTab(0);
			UtilBean.addMessageAndRemoveOthers(FacesMessage.SEVERITY_INFO,
					"Sucesso", "Tipo de recebimento salvo com sucesso");
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
	
	public void excluir(TipoRecebimento tipo) {
		try {
			if(tipo.getId() == 0){ return; }
			
			tipoDAO.removeById(tipo.getId());
			this.reset();
			alterTab(0);
			UtilBean.addMessageAndRemoveOthers(FacesMessage.SEVERITY_INFO,
					"Sucesso", "Tipo de recebimento excluído com sucesso");
		} catch (Exception e) {
			e.printStackTrace();
			UtilBean.addMessageAndRemoveOthers(FacesMessage.SEVERITY_ERROR,
					"Erro", "Contate o administrador do sistema");
		}
	}
	public TipoRecebimento getTipoSelecionado() {
		if (tipoSelecionado == null) {
			tipoSelecionado = new TipoRecebimento();
		}
		return tipoSelecionado;
	}
	public List<TipoRecebimento> getTipos() {
		tipos = tipoDAO.getAll();
		return tipos;
	}
	public int getIndexTab() {
		return indexTab;
	}
	public void setIndexTab(int indexTab) {
		this.indexTab = indexTab;
	}
	public void setTipoSelecionado(TipoRecebimento tipoSelecionado) {
		this.tipoSelecionado = tipoSelecionado;
	}
	public void setTipos(List<TipoRecebimento> tipos) {
		this.tipos = tipos;
	}

}
