package redes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;

public class Dados {
	public static int ROWS, COLS;
	public static String RELATION;
	public static double[][] ENTRADAS;
	public static double[] SAIDAS;
	public static ArrayList<String> ATTRIBUTES;

	private static boolean is_data;

	public static void pegarDados() {
		String nome="/media/filipe/00844E3F844E3784/Windows.old/Users/FilipeAlves/Desktop/redes/src/redes/cancer.txt";
		ATTRIBUTES = new ArrayList<String>();

		try {
			FileReader arq = new FileReader(nome);
			BufferedReader lerArq = new BufferedReader(arq);
			String linha;
			int row =0;
			while((linha = lerArq.readLine())!=null) {
				String[] split = linha.split(" ");
				if(!is_data) {
					validarAnotacao(split);
				}else {
					buscarDados(linha, row);
					row++;
				}

			}
			
			arq.close();
			
		} catch (IOException e) {
			System.err.printf("Erro na abertura do arquivo: %s.\n",
					e.getMessage());
		}
	}



	private static void buscarDados(String linha, int row) {
		int quantVirgulas = 0;
		int point = 0;
		linha=linha+",";
		for (int i = 0; i < linha.length(); i++) {
			if (linha.charAt(i)==',') {
				if(quantVirgulas==0) {
					point=i;
				}else if(quantVirgulas==1) {
					String letra = linha.substring(point+1, i);
					SAIDAS[row]= (letra.equals("B")?0:1);
				}else {
					String valor = linha.substring(point+1, i);
					ENTRADAS[row][quantVirgulas-2] = Double.parseDouble(valor);
				}
				quantVirgulas++;
				point=i;
			}
		}
		
	}



	private static void validarAnotacao(String[] split) {

		switch (split[0]) {
		case "@relation":
			RELATION = split[1];
			break;

		case "@cols":
			COLS = Integer.parseInt(split[1])-2;
			break;

		case "@rows":
			ROWS = Integer.parseInt(split[1]);
			break;

		case "@attribute":
			ATTRIBUTES.add(split[1].toUpperCase());
			break;

		case "@data":
			is_data = true;
			ENTRADAS = new double[ROWS][COLS];
			SAIDAS = new double[ROWS];

		default:
			break;
		}


	}



	public static void dadosTeste() {
		String nome="C:\\Users\\FilipeAlves\\Desktop\\redes\\src\\redes\\conjunto_de_teste.txt";
		try {
			FileReader arq = new FileReader(nome);
			BufferedReader lerArq = new BufferedReader(arq);
			String linha = lerArq.readLine();



			ENTRADAS = new double[ROWS][COLS];
			SAIDAS = new double[ROWS];

			for (int i = 0; i < ROWS; i++) {
				int quantVirgulas = 0;
				int point = 0;
				linha = lerArq.readLine();
				if(linha!=null) {
					for (int j = 0; j < linha.length(); j++) {
						if (linha.charAt(j)==',') {
							String valor = linha.substring(point+1, j);
							ENTRADAS[i][quantVirgulas] = Double.parseDouble(valor);
							quantVirgulas++;
							point=j;
						}
					}
				}
			}

			arq.close();
		} catch (IOException e) {
			System.err.printf("Erro na abertura do arquivo: %s.\n",
					e.getMessage());
		}
	}
}
