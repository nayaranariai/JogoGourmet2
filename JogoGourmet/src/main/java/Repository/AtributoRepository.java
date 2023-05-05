package main.java.Repository;

import java.util.ArrayList;

import main.java.model.Atributo;
import main.java.model.Prato;

public class AtributoRepository {

	public static ArrayList<String> buscaNomesTodosAtributos(ArrayList<Prato> pratos) {
		ArrayList<String> atributos = new ArrayList<>();
		for (Prato prato : pratos) {
			if (prato.getAtributos() != null) {
				for (Atributo atributo : prato.getAtributos()) {
					if (!atributos.contains(atributo.getNome())) {
						atributos.add(atributo.getNome());
					}
				}
			}
		}

		return atributos;
	}

	public static ArrayList<Atributo> buscaTodosAtributos(ArrayList<Prato> pratos) {

		ArrayList<Atributo> atributos = new ArrayList<>();

		for (Prato prato : pratos) {
			if (prato.getAtributos() != null) {
				for (Atributo atributo : prato.getAtributos()) {
					if (!atributos.contains(atributo)) {
						atributos.add(atributo);
					}
				}
			}
		}

		return atributos;
	}
}
