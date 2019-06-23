package redes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class prepararDados {
	public static void main(String[] args) {
		String nome="C:\\\\Users\\\\FilipeAlves\\\\Desktop\\\\redes\\\\src\\\\redes\\\\conjunto_de_teste.txt";

		System.out.printf("\nConteúdo do arquivo texto:\n");
		try {
			FileReader arq = new FileReader(nome);
			BufferedReader lerArq = new BufferedReader(arq);
			String linha = lerArq.readLine();

			while (linha!=null) {
				

				linha = lerArq.readLine(); 
				int l= 0;
				for (int i = 0; i < linha.length(); i++) {
					if (linha.charAt(i)==',') {
						if(l==3) {
							System.out.printf("%s\n", linha.substring(0,i)+",");
							break;
						}
						l++;
					}
				}
				
			}

			arq.close();
		} catch (IOException e) {
			System.err.printf("Erro na abertura do arquivo: %s.\n",
					e.getMessage());
		}

		System.out.println();

	}
}
