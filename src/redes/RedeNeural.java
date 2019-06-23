package redes;

import java.util.Random;

public class RedeNeural {

	private double[] pesos;
	private double bias;

	double erro=0;

	private double[][] entradas;
	private int interacoes;

	public RedeNeural(double[][] entradas, int interacoes) {
		this.entradas = entradas;
		this.interacoes = interacoes;
		this.pesos = new double[entradas[0].length];
		
		iniciarPesos();

	}

	//definindo valores aleatorios para os pesos e bias
	private void iniciarPesos() {
		Random r = new Random();
		for(int i = 0; i < pesos.length; i++) {
			pesos[i] = -1 + (1 - (-1)) * r.nextDouble();

		}
		bias = -1 + (1 - (-1)) * r.nextDouble();;
	}


	public double sigmoid(double x) {
		return 1.0 / (1.0 + Math.exp(-x));
	}

	public double rodar(double[] entradas) {
		double soma = 0.0;
		for(int i = 0; i < entradas.length; i++) {
			soma += (entradas[i] * pesos[i]) + bias;
		}
//		System.out.println(pesos[0]);
		return sigmoid(soma);
	}
	
	

	public boolean treinar(double[] saidas) {
		int count = 0;
		do {
			//System.out.println("Epoca " + (count + 1));
		
			for(int i = 0; i < entradas.length; i++) {
				double y = rodar(entradas[i]);
				for(int j = 0; j < pesos.length; j++) {
					pesos[j] += (saidas[i] - y) * entradas[i][j];
				}
				
				double er = erro(y,saidas[i]);
				//System.out.printf("Y: %5f -> Esperado: %5f -> Erro: %.5f\n", y, saidas[i], er);
				erro+=er;
			}
			//System.out.println("Erro Total= "+ erro);
			erro=0;
			count++;
		} while(count < this.interacoes);
		
		return true;

	}

	public double erro(double ativacao, double saida) {
		double erro = saida-ativacao;
		if(erro<0)
			erro *= -1;
		return erro;
	}

	public void teste(double[][] entradaTeste, double[] saidasTeste) {
		int erro = 0;
		for (int i = 0; i < entradaTeste.length; i++) {
			double r = rodar(entradaTeste[i]);
			System.out.printf("Resultado = %5f  -> Esperado = %5f\n",r,saidasTeste[i]);
			erro+=erro(r,saidasTeste[i]);
		}
		int porErro = erro*100/saidasTeste.length;
		System.out.println("\nPorcentagem de Acerto: "+(100-porErro)+"%");
		System.out.println("Porcentagem de Erro: "+porErro+"%");
	
	}

}
