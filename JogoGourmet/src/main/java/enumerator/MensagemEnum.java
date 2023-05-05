package main.java.enumerator;

public enum MensagemEnum {
	
	INICIO_JOGO("Pense em um prato que gosta"),
	PERGUNTA("O prato que você pensou é %s ?"),
	ACERTO("Acertei de novo!"),
	ERRO("Qual prato você pensou?"),
	COMPLETE("%s é __________ mas %s não.");
	

	public String stringMensagem;
	
	MensagemEnum(String mensagem) {
		stringMensagem = mensagem;
	}
	
}
