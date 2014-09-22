package com.sindicato.result;

import java.io.Serializable;

public class InformacaoMensalidade implements Serializable {

	private static final long serialVersionUID = 1L;
	private boolean atrasado;
	private int mensalidadesPagas;
	private int mesesComoSocio;
	private String mensagem;
	
	public boolean isAtrasado() {
		return atrasado;
	}
	public void setAtrasado(boolean atrasado) {
		this.atrasado = atrasado;
	}
	public int getMensalidadesPagas() {
		return mensalidadesPagas;
	}
	public void setMensalidadesPagas(int mensalidadesPagas) {
		this.mensalidadesPagas = mensalidadesPagas;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	public int getMesesComoSocio() {
		return mesesComoSocio;
	}
	public void setMesesComoSocio(int mesesComoSocio) {
		this.mesesComoSocio = mesesComoSocio;
	}
}
