package Baralho;

import java.util.ArrayList;

import java.util.Random;


public class Baralho {

	private ArrayList<Carta> cartas;
	/**
	 * Construtor do Baralho
	 */
	public Baralho(){
		this.cartas = new ArrayList<Carta>();
	}
	/**
	 * Adiciona cartas no baralho
	 */
	public void criarBaralhoCompleto() {
		for(Naipe cartaNaipe :  Naipe.values()) {
			for(Numero cartaNumero : Numero.values()) {
				this.cartas.add(new Carta(cartaNaipe, cartaNumero));
			}
		}
	}
	/**
	 * Embaralha o Baralho
	 */
	public void embaralha() {
		ArrayList<Carta> aux = new ArrayList<Carta>();
		
		Random aleatorio = new Random();
		
		int cartaAleatoria = 0;
		int tam = this.cartas.size();
		int i = 0;
		
		for(;i < tam;i++) {
			
		cartaAleatoria = aleatorio.nextInt((this.cartas.size()-1 - 0) + 1 + 0);
		aux.add(this.cartas.get(cartaAleatoria));
		this.cartas.remove(cartaAleatoria);
		}
		
		this.cartas = aux; 
	}
	
	public void removeCarta(int i) {
		this.cartas.remove(i);
	}
	
	public Carta getCarta(int i) {
		return this.cartas.get(i);
	}
	
	public void addCarta(Carta addCartas) {
		this.cartas.add(addCartas);
	}
	
	public Carta sacar(Baralho baralho) {
		Carta carta;
		carta = baralho.getCarta(0);
		baralho.removeCarta(0);
		
		return carta;
	}
	
	public int tamBaralho() {
		return this.cartas.size();
	}
	/**
	 * Calcula o valor das cartas
	 * @return valor total das cartas
	 */
	public int valorCartas(){
			int valorTotal = 0;
			int aux = 0;
			int as = 0;
			
			for(Carta aCarta : this.cartas) {
				
				switch (aCarta.getNumero()) {
				
				case DOIS:valorTotal += 2;
					 break;
				case TRES:valorTotal += 3;
				 	 break;
				case QUATRO:valorTotal += 4;
				 	 break;
				case CINCO:valorTotal += 5;
				 	 break;
				case SEIS:valorTotal += 6;
				 	 break;
				case SETE:valorTotal += 7;
				 	 break;
				case OITO:valorTotal += 8;
				 	 break;
				case NOVE:valorTotal += 9;
				 	 break;
				case DEZ:valorTotal += 10;
				 	 break;
				case VALETE:valorTotal += 10;
						aux += 1;
				 	 break;
				case DAMA:valorTotal += 10;
						aux += 1;
				 	 break;
				case REI:valorTotal += 10;
						aux += 1;
				 	 break;
				case AS:as += 1;
				 	 break;				
				}
				if(as > 0) {
				for(int i = 0 ;i < as; i++) {
					if(aux > 0) {
						valorTotal += 11;
					}
					else {
						valorTotal += 1;
					}
				}
				}
			}
			
			
		return valorTotal;
	}
	
	
}
