package Baralho;

public class Carta {
		private Naipe naipe;
		private Numero numero;
		
	public Carta(Naipe naipe, Numero numero) {
		this.naipe = naipe;
		this.numero = numero;
	}
	
	public String toString() {
		return this.numero.toString() + "-" + this.naipe.toString();
	}
	
	public Numero getNumero(){
		return this.numero;
	}
}
