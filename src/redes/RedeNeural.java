package redes;

import java.util.Random;

public class RedeNeural {

	private double[] pesos;
	private double bias;

	double erro=0;

	private double[][] entradas;
	private int interacoes;
	private int quantCamadas;
	private int quantNeuronios;
	int momento;

	double somaY[][];

	public RedeNeural(double[][] entradas, int interacoes, int quantCamadas, int quantNeuronios) {
		this.momento = 1;
		this.entradas = entradas;
		this.interacoes = interacoes;
		this.pesos = new double[(entradas[0].length*quantNeuronios*quantCamadas)+quantNeuronios];
		this.quantCamadas = quantCamadas;
		this.quantNeuronios = quantNeuronios;
		this.somaY = new double[entradas.length][quantNeuronios];

		iniciarPesos();

	}

	//definindo valores aleatorios para os pesos e bias
	private void iniciarPesos() {
		Random r = new Random();
		for(int i = 0; i < pesos.length; i++) {
			pesos[i] = -1 + (1 - (-1)) * r.nextDouble();
		}
		bias = r.nextDouble()*0.001;
		
	}


	public double sigmoid(double x) {
		return 1 / (1 + Math.exp(-x));
	}

	public double derivadaSigmoid(double sig) {
		return sig * (1 - sig);
	}


	public double rodar(double[] entradas, int idEntrada) {
		int p = sigmoidSaida(entradas, idEntrada);
		double soma = somaSigmoidSaida(idEntrada, p);
		
		return sigmoid(soma);
	}


	private double somaSigmoidSaida(int idEntrada, int quantP) {
		int p = quantP;
		double soma = 0;
		for (int i = 0; i < somaY[idEntrada].length; i++) {
			soma += (somaY[idEntrada][i] * pesos[p]);
			p++;
		}
		return soma;
	}

	private int sigmoidSaida(double[] entradas, int idEntrada) {
		int p = 0;
		for(int i = 0; i < quantNeuronios; i++) {
			for (int j = 0; j < entradas.length; j++) {
				somaY[idEntrada][i] += (entradas[j] * pesos[p]);
				p++;
			}
			
			somaY[idEntrada][i] = sigmoid(somaY[idEntrada][i]);
		}
		
		return p;
		
	}


	public boolean treinar(double[] saidas) {
		int count = 0;
		double derivadasSigmoid[] = new double[saidas.length];
		double deltaSaida;
		int p;
		double pesosNovo[] = new double[quantNeuronios];
		double deltaOculta[][] = new double[entradas.length][quantNeuronios];


		do {
			
			double erros[] = new double[saidas.length];
			//System.out.println("Epoca " + (count + 1));
			for(int i = 0; i < entradas.length; i++) {
				double y = rodar(entradas[i], i);
				//System.out.printf("Esperado: %.2f --> Resultado: %.5f\n",saidas[i],y);
				p = pesos.length-1;

				erros[i] = saidas[i] - y;
				
				derivadasSigmoid[i] = derivadaSigmoid(y);
				deltaSaida = erros[i] * derivadasSigmoid[i];

				for (int j = quantNeuronios-1; j >= 0; j--) {
					deltaOculta[i][j] = deltaSaida*pesos[p]*derivadaSigmoid(somaY[i][j]);
					pesosNovo[j] += somaY[i][j]*deltaSaida;
					p--;

				}

			}
			double x=0;
			for (int i = 0; i < erros.length; i++) {
				x += Math.abs(erros[i]);
			}
			System.out.println("Erro medio: "+ x/erros.length);
			p = pesos.length-1;

			double  pesoNovoEntrada[][] = new double[quantNeuronios][entradas[0].length];
			for (int i = quantNeuronios-1; i >=0 ; i--) {
				pesos[p] = (pesos[p]*momento) + pesosNovo[i]*bias;
				p--;
				for (int j = 0; j < entradas[0].length; j++) {
					for (int k = 0; k < entradas.length; k++) {
						pesoNovoEntrada[i][j] += entradas[k][j] * deltaOculta[k][i];
					}
				}
			}
			
			p = pesos.length-quantNeuronios-1;
			for (int i = pesoNovoEntrada.length-1; i >=0; i--) {
				for (int j = pesoNovoEntrada[0].length-1; j >=0 ; j--) {
					pesos[p] = (pesos[p]*momento) + pesoNovoEntrada[i][j]*bias;
					p--;
				} 
			}
			
			count++;
		} while(count < this.interacoes);

		return true;

	}

	public void teste(double[][] entradaTeste, double[] saidasTeste) {
		double erro = 0;
		for (int i = 0; i < entradaTeste.length; i++) {
			double r = rodar(entradaTeste[i],0);
			System.out.printf("Esperado = %5f  -> Resultado = %5f\n",saidasTeste[i],r);
			erro +=  Math.abs(r-saidasTeste[i]);
		
		}
		double porErro = erro*100/saidasTeste.length;
		
		System.out.println("\nErro total:" +erro);
		System.out.println("\nErro Médio:" +(erro/entradaTeste.length));
		System.out.println("\nPorcentagem de Acerto: "+(100-porErro)+"%");
		System.out.println("Porcentagem de Erro: "+porErro+"%");

	}

}
