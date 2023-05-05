package main.java.model;

import java.util.ArrayList;

public class Prato {

	String nome;
	ArrayList<Atributo> atributos;

	public Prato(String nome, ArrayList<Atributo> atributos) {
		super();
		this.nome = nome;
		this.atributos = atributos;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public ArrayList<Atributo> getAtributos() {
		return atributos;
	}

	public void addAtributo(Atributo atributo) {
		this.atributos.add(atributo);
	}
}
