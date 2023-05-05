package main.java.jogo;

import main.java.controller.Jogo;

public class Application {

	public static void main(String[] args) {
		init();
	}

	public static void init() {
		Jogo.iniciaPratosEAtributos();
		Jogo.iniciaJogo();
	}

}
