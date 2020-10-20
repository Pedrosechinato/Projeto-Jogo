package main;


import interfaces.InterfaceCadastro;
import interfaces.InterfaceLogin;
import interfaces.InterfaceSala;
import interfaces.InterfaceLobby;
import interfaces.InterfaceCriarSala;

public class Main {
	
	public static InterfaceLogin TELA_LOGIN;
	public static InterfaceCadastro TELA_CADASTRO;
	public static InterfaceLobby TELA_LOBBY;
	public static InterfaceCriarSala TELA_CRIAR_SALA;
	public static InterfaceSala TELA_SALA;
	public static final String IP = "172.16.12.75"; //192.168.15.8
	public static final int PORTA = 12340;
	public static String email;
	public static String nome_sala;
	public static float moedas;
	/**
	 * Inicializa e mostra as janelas
	 * @param args
	 */
	public static void main(String[] args) {
		TELA_LOGIN = new InterfaceLogin();
		TELA_LOGIN.setVisible(true);
		TELA_LOBBY = new InterfaceLobby();
		TELA_LOBBY.setVisible(false);
		TELA_CRIAR_SALA = new InterfaceCriarSala();
		TELA_CRIAR_SALA.setVisible(false);
		TELA_CADASTRO = new InterfaceCadastro();
		TELA_CADASTRO.setVisible(false);
		TELA_SALA = new InterfaceSala();
		TELA_SALA.setVisible(false);
		
		
		
	}
}
