package com.sindicato.result;

public class InformacaoMensalidade {

	private boolean atrasado;
	private int mensalidadesPagas;
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
}
