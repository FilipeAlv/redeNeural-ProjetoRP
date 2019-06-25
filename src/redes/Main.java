package redes;

import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

public class Main {
	static double[][] entradas;
	static double[] saidas;

	static double[][] entradasTeste;
	static double[] saidasTeste;


	public static void main(String[] args) {
		Dados.pegarDados();
		pegarEntradasSaidas();			
		
		double entradasT[][] = {{0,0},{0,1},{1,0},{1,1}};
		double saidasT[] = {0,1,1,0};
		
		
		System.out.println("============Treinando==========");
		RedeNeural rn = new RedeNeural(entradas, 1000,1, 4);
		rn.treinar(saidas);

		System.out.println("============Teste==========");
		rn.teste(entradasTeste, saidasTeste);

	}

	private static void pegarEntradasSaidas() {
		int percentEntradas = Dados.ROWS * 80 / 100;
		int percentEntradasTeste = Dados.ROWS-percentEntradas;

		entradas = new double[percentEntradas][Dados.COLS];
		saidas = new double[percentEntradas];

		entradasTeste = new double[percentEntradasTeste][Dados.COLS];
		saidasTeste = new double[percentEntradasTeste];

		gerarDados(aleatórios(percentEntradas));

	}

	private static void gerarDados(Set<Integer> aleatórios) {
		int posEntradas = 0;
		int posEntradasTeste = 0;
		boolean sorteado = false;
		for (int i = 0; i < Dados.ROWS; i++) {
			for (Integer integer : aleatórios) {
				sorteado=false;
				if(i==integer) {
					entradas[posEntradas] = Dados.ENTRADAS[i];
					saidas[posEntradas] = Dados.SAIDAS[i];
					posEntradas++;
					sorteado = true;
					break;
				}
			}
			if(!sorteado){
				//System.out.println(i);
				entradasTeste[posEntradasTeste] = Dados.ENTRADAS[i];
				saidasTeste[posEntradasTeste] = Dados.SAIDAS[i];
				posEntradasTeste++;

			}
		}

	}

	private static Set<Integer> aleatórios(int quantidade) {
		Set<Integer> numeros = new TreeSet<Integer>();
		Random rand = new Random();

		while (numeros.size() < quantidade) {
			numeros.add(rand.nextInt(Dados.ROWS));
		}
		//System.out.println(numeros);
		return numeros;
	}
}
