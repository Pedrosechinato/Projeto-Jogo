package Jogo;
import Baralho.*;


public class Jogador extends Thread{
	private float moedas;
	private String email_Jogador;
	private Baralho baralhoMao;
	private int pontos;
	
	public void guardaCarta(Carta carta)throws Exception {
		if (carta == null)
				throw new Exception("sem carta para entrar na mao ");
		
		baralhoMao.addCarta(carta);
	}
	public float aposta_jogador(float moedas)throws Exception {
		if(this.moedas < moedas)
				throw new Exception("Sem saldo para aposta");
		
		this.moedas = this.moedas-moedas;
		return moedas ;
		
	}
	synchronized public float atualizaMoedasWin(float pote) {
		moedas = moedas + pote;
		return moedas;
		
	}
	
	public Jogador(String email, float moedas) {
		setEmail_Jogador(email);
		setMoedas(moedas);
		baralhoMao = new Baralho();
	}
	
	public float getMoedas() {
		return moedas;
	}

	public void setMoedas(float moedas) {
		this.moedas = moedas;
	}

	public String getEmail_Jogador() {
		return email_Jogador;
	}

	public void setEmail_Jogador(String nome_Jogador) {
		this.email_Jogador = nome_Jogador;
	}

	public Baralho getBaralhoMao() {
		return baralhoMao;
	}

	public void setBaralhoMao(Baralho baralhoMao) {
		
		this.baralhoMao = baralhoMao;
	}
	public int getPontos() {
		return pontos;
	}
	public void setPontos(int pontos) {
		this.pontos = pontos;
	}
	

}
