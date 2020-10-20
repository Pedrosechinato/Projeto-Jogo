package Jogo;
import  Baralho.*;
import bd.daos.Usuarios;


public class Partida extends Thread {
	private float Pote = 0;
	private Jogador[] jogador;
	private String nome_Sala;
	private Baralho baralho;
	private int qtdParou= 0;
	private int qtd=0;
	private boolean status = false;
	
	public Partida(String nomeSala)throws Exception {
		if (nomeSala == null)
			throw new Exception("Nome da sala nulo");
		
		jogador = new Jogador[8];
		
		setNome_Sala(nomeSala);
		
		// TODO Auto-generated constructor stub
	}
	
	//VERIFICA SALA ESTA CHEIA
	synchronized public void entrar_partida(Jogador j)throws Exception {
		if(j == null )
			throw new Exception ("Usuario nao logado");
		if (this.qtd>=8) 
			throw new Exception ("sala Cheia");
		if(status)
			throw new Exception ("Jogo ja começou");
		
		qtd++;
		jogador[qtd] = j;
	
	
	}
	synchronized public boolean comeca_partida()throws Exception{
		boolean r = false;
		if (status)
			throw new Exception ("Jogo ja começou");
		if (this.qtd < 3)
			throw new Exception ("Poucos Jogadores");
		
		this.baralho = new Baralho();
		this.baralho.criarBaralhoCompleto();
		if (this.qtd <= 4)
			baralho.criarBaralhoCompleto();
			
		this.baralho.embaralha();
		this.status = true;
		r = true;
		return r;
	}
	synchronized public Carta sacar(String email) throws Exception {
		
		Carta ret=null;
		for(int i = 1; i <= this.qtd; i++) {
			if(this.jogador[i].getEmail_Jogador().equals(email))
			{
				ret =  this.baralho.sacar(this.baralho);
				jogador[i].getBaralhoMao().addCarta(ret);
				jogador[i].getBaralhoMao().valorCartas();
				
				break;
			}	
		}
				
			return ret;
		}
	synchronized public int verPontos(String email) {
		int pontos=0;
		for(int i = 1; i <= this.qtd; i++) {
			if(this.jogador[i].getEmail_Jogador().equals(email))
			{
				
				pontos = jogador[i].getBaralhoMao().valorCartas();
				
				break;
			}	
		}
		
		return pontos;
	}
	synchronized public void aposta(float moedas, String email_jogador)throws Exception{
		Usuarios usuariosDAO = new Usuarios();
		
		for(int i = 1; i <= this.qtd; i++) {
			if(this.jogador[i].getEmail_Jogador().equals(email_jogador))
			{
				
				float aux = jogador[i].aposta_jogador(moedas);
				this.Pote = this.Pote + aux;
				usuariosDAO.upadateMoedas(email_jogador,jogador[i].getMoedas());
				break;
			}
		}
	
	}
	synchronized public String vencedorWin(String email) throws Exception {
		Usuarios usuariosDAO = new Usuarios();
		int maior = 0, pos = 0;
		String ret = "ERR";
		
		maior = jogador[1].getPontos();
		pos = 1;
		for(int i = 2; i <= this.qtd; i++) 
		{
				if(maior < jogador[i].getPontos()) {
					maior = jogador[i].getPontos();
					pos = i;
				}
		}
		if(email.equals(jogador[pos].getEmail_Jogador())) {
			ret = "WIN";
			jogador[pos].atualizaMoedasWin(this.Pote);
			setPote(0);
			usuariosDAO.upadateMoedas(email,jogador[pos].getMoedas());
			limpaMao(email);
		}
		
		
		else
			ret = "ERR";
		
		setQtdParou(0);
		return ret;
		
	}
	public void limpaMao(String email) {
		for(int i = 1; i <= this.qtd; i++) 
		{
			if(this.jogador[i].getEmail_Jogador().equals(email))
			{
				for(int j=0 ; j< jogador[j].getBaralhoMao().tamBaralho();) 
				{
				jogador[j].getBaralhoMao().removeCarta(j);
				}
			
			}
		}
	}
	
	synchronized public void setPontos(int pontos,String email) {
		for(int i = 1; i <= this.qtd; i++) {
			if(this.jogador[i].getEmail_Jogador().equals(email))
			{
				
				jogador[i].setPontos(pontos);
				
				break;
			}
		}
	}
	public void jogadorSai(String email) {
		for(int i = 1; i <= this.qtd; i++) 
		{
			if(this.jogador[i].getEmail_Jogador().equals(email))
			{
				jogador[i]=null;
				this.qtd--;	
			
			}
		}
		
	}
	synchronized public void JogadorParou() {
		qtdParou++;
	}

	synchronized public String getNome_Sala() {
		return nome_Sala;
	}

	public void setNome_Sala(String nome_Sala) {
		this.nome_Sala = nome_Sala;
	}

	public float getPote() {
		return Pote;
	}

	public void setPote(float pote) {
		Pote = pote;
	}
	public int getQtd_jogadores() {
		return qtd;
	}
	public void setQtd_jogadores(int qtd_jogadores) {
		this.qtd = qtd_jogadores;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}

	public void setJogador(Jogador jogador) {
		this.jogador[qtd] = jogador;
	}

	public int getQtdParou() {
		return qtdParou;
	}

	public void setQtdParou(int qtdParou) {
		this.qtdParou = qtdParou;
	}

}
