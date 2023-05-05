package main.java.controller;

import static javax.swing.JOptionPane.CANCEL_OPTION;
import static javax.swing.JOptionPane.CLOSED_OPTION;
import static javax.swing.JOptionPane.NO_OPTION;
import static javax.swing.JOptionPane.YES_NO_OPTION;
import static javax.swing.JOptionPane.YES_OPTION;
import static main.java.enumerator.MensagemEnum.ACERTO;
import static main.java.enumerator.MensagemEnum.COMPLETE;
import static main.java.enumerator.MensagemEnum.ERRO;
import static main.java.enumerator.MensagemEnum.INICIO_JOGO;
import static main.java.enumerator.MensagemEnum.PERGUNTA;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.swing.JOptionPane;

import main.java.Repository.AtributoRepository;
import main.java.model.Atributo;
import main.java.model.Prato;

public class Jogo {

	private static ArrayList<Prato> listaPratos;
	private static HashMap<String, Boolean> respostas;

	public static void iniciaPratosEAtributos() {
		listaPratos = new ArrayList<>();

		ArrayList<Atributo> atributos1 = new ArrayList<>();
		atributos1.add(new Atributo("massa"));

		ArrayList<Atributo> atributos2 = new ArrayList<>();
		atributos2.add(new Atributo("doce"));

		Prato prato1 = new Prato("Lasanha", atributos1);
		Prato prato2 = new Prato("Bolo de Chocolate", atributos2);

		listaPratos.add(prato1);
		listaPratos.add(prato2);
	}

	public static void iniciaJogo() {
		mensagemInicio();
		respostas = new HashMap<String, Boolean>();

		ArrayList<String> listaAtributos = AtributoRepository.buscaNomesTodosAtributos(buscaTodosPratos());

		for (int i = 0; i <= listaAtributos.size() - 1; i++) {
			String atributo = listaAtributos.get(i);

			Integer respostaAtributo = realizaPergunta(atributo);
			ArrayList<Prato> pratosEleitos = retornaPratosEleitos();

			if (pratosEleitos.isEmpty() && i == listaAtributos.size() - 1) {
				processaErro(buscaTodosPratos().get(0));
				break;
			} else if (!pratosEleitos.isEmpty()) {
				processaPratosEleitos(pratosEleitos, atributo, respostaAtributo);
				break;
			} else {
				continue;
			}
		}

		reiniciaJogo();
	}

	public static void processaPratosEleitos(ArrayList<Prato> pratosEleitos, String atributo,
			Integer respostaAtributo) {

		Prato pratoEleito;
		Integer respostaFinal;

		if (!pratosEleitos.isEmpty()) {
			ArrayList<String> listaProximosAtributos = AtributoRepository.buscaNomesTodosAtributos(pratosEleitos);

			for (String atributoPerguntado : respostas.keySet()) {
				listaProximosAtributos = removeAtributo(listaProximosAtributos, atributoPerguntado);
			}

			if (listaProximosAtributos.isEmpty()) {
				pratoEleito = pratosEleitos.get(0);
				respostaFinal = realizaPergunta(pratoEleito.getNome());
				processaResposta(respostaFinal, pratoEleito, atributo, respostaAtributo);
			}

			for (int i = 0; i <= listaProximosAtributos.size() - 1; i++) {
				String proximoAtributo = listaProximosAtributos.get(i);
				Integer respostaProximoAtributo = realizaPergunta(proximoAtributo);
				pratosEleitos = retornaPratosEleitos();

				if (pratosEleitos.size() == 1) {
					pratoEleito = pratosEleitos.get(0);
					respostaFinal = realizaPergunta(pratoEleito.getNome());
					processaResposta(respostaFinal, pratoEleito, proximoAtributo, respostaProximoAtributo);
					break;
				} else if (i == listaProximosAtributos.size() - 1) {
					processaErro(buscaTodosPratos().get(0));
				} else {
					continue;
				}

			}

		}
	}

	public static void reiniciaJogo() {
		iniciaJogo();
	}

	public static void processaResposta(Integer resposta, Prato pratoEleito, String ultimoAtributo,
			Integer respostaAtributo) {
		if (resposta == YES_OPTION) {
			processaAcerto();
		} else if (resposta == NO_OPTION) {
			processaErro(pratoEleito);
		}
	}

	public static void finalizaJogo(Integer resposta) {
		if (resposta == CANCEL_OPTION || resposta == CLOSED_OPTION) {
			System.exit(0);
		}
	}

	public static void mensagemInicio() {
		JOptionPane.showMessageDialog(null, INICIO_JOGO.stringMensagem, "Jogo Gourmet", JOptionPane.PLAIN_MESSAGE);
	}

	public static ArrayList<Prato> buscaTodosPratos() {
		return listaPratos;
	}

	public static Prato buscaPrato(String nome) {
		ArrayList<Prato> pratos = buscaTodosPratos();

		for (Prato prato : pratos) {
			if (Objects.equals(prato.getNome(), nome)) {
				return prato;
			}
		}
		return null;
	}

	public static ArrayList<String> buscaRespostasPositivas() {
		ArrayList<String> respostasPositivas = new ArrayList<>();

		for (Map.Entry<String, Boolean> set : respostas.entrySet()) {
			if (set.getValue() == true) {
				respostasPositivas.add(set.getKey());
			}
		}

		return respostasPositivas;
	}

	public static ArrayList<String> buscaRespostasNegativas() {
		ArrayList<String> respostasNegativas = new ArrayList<>();

		for (Map.Entry<String, Boolean> set : respostas.entrySet()) {
			if (set.getValue() == false) {
				respostasNegativas.add(set.getKey());
			}
		}

		return respostasNegativas;
	}

	public static ArrayList<Prato> retornaPratosEleitos() {
		ArrayList<Prato> pratosARemover = new ArrayList<Prato>();
		ArrayList<Prato> pratos = new ArrayList<>();

		for (Prato prato : listaPratos) {
			pratos.add(prato);
		}

		for (Prato prato : pratos) {
			ArrayList<String> nomesAtributos = new ArrayList<>();

			if (prato.getAtributos() != null) {
				for (Atributo atributo : prato.getAtributos()) {
					nomesAtributos.add(atributo.getNome());
				}
			}

			for (String nomeAtributo : buscaRespostasNegativas()) {
				if (nomesAtributos.contains(nomeAtributo)) {
					if (!pratosARemover.contains(prato)) {
						pratosARemover.add(prato);
					}
				}
			}

			for (String nomeAtributo : buscaRespostasPositivas()) {
				if (!nomesAtributos.contains(nomeAtributo)) {
					if (!pratosARemover.contains(prato)) {
						pratosARemover.add(prato);
					}
				}
			}
		}

		for (Prato prato : pratosARemover) {
			pratos.remove(prato);
		}

		return pratos;
	}

	public static ArrayList<String> removeAtributo(ArrayList<String> listaProximosAtributos, String atributoAnterior) {

		ArrayList<String> listaAtributos = new ArrayList<>();
		for (String atributo : listaProximosAtributos) {
			listaAtributos.add(atributo);
		}

		listaAtributos.remove(atributoAnterior);

		return listaAtributos;
	}

	public static Integer realizaPergunta(String nome) {
		Integer resposta;

		resposta = JOptionPane.showConfirmDialog(null, String.format(PERGUNTA.stringMensagem, nome), "Confirm",
				YES_NO_OPTION);

		finalizaJogo(resposta);

		Boolean res;
		if (resposta == YES_OPTION) {
			res = true;
		} else {
			res = false;
		}
		respostas.put(nome, res);

		return resposta;
	}

	public static void processaErro(Prato prato) {
		String pratoCorreto = perguntaPrato();
		String atributoCorreto = perguntaAtributo(pratoCorreto, prato.getNome());

		ArrayList<Atributo> novosAtributos = new ArrayList<>();

		for (String atributoRespondido : buscaRespostasPositivas()) {
			for (Atributo atributo : AtributoRepository.buscaTodosAtributos(buscaTodosPratos())) {
				if (atributo.getNome() == atributoRespondido) {
					novosAtributos.add(atributo);
				}
			}
		}

		novosAtributos.add(new Atributo(atributoCorreto));

		Prato novoPrato = buscaPrato(pratoCorreto);

		if (novoPrato == null) {
			novoPrato = new Prato(pratoCorreto, novosAtributos);
			listaPratos.add(novoPrato);
		} else {
			for (Atributo atributo : novosAtributos) {
				novoPrato.addAtributo(atributo);
			}
		}
	}

	public static void processaAcerto() {
		JOptionPane.showMessageDialog(null, ACERTO.stringMensagem, "Jogo Gourmet", JOptionPane.INFORMATION_MESSAGE);
	}

	public static String perguntaPrato() {
		return JOptionPane.showInputDialog(null, ERRO.stringMensagem, "Desisto", JOptionPane.QUESTION_MESSAGE);
	}

	public static String perguntaAtributo(String pratoCorreto, String nomePrato) {
		if (nomePrato == null && buscaTodosPratos().get(0) != null) {
			nomePrato = buscaTodosPratos().get(0).getNome();
		}
		return JOptionPane.showInputDialog(null, String.format(COMPLETE.stringMensagem, pratoCorreto, nomePrato),
				"Complete", JOptionPane.QUESTION_MESSAGE);
	}
}
