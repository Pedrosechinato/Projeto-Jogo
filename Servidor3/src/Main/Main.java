package Main;

import java.net.ServerSocket;
import java.net.Socket;

import Jogo.Partida;
import threads.ConexaoThread;
import Jogo.Lista;

public class Main  {
	
	public static Lista<Partida> listaglobal = new Lista<Partida>();
	public static boolean PARAR = false;
	
	
	public static void main(String[] args) {
		ServerSocket pedido = null;
		
		
		try {
			//ccc
			pedido = new ServerSocket(12340);
			
			Socket conexao = null;	
			
			while (true) {
				
            	conexao = pedido.accept();
            	new ConexaoThread(conexao).start();
	           
	            if(PARAR)
	            {
	            	pedido.close();
	            }
	        }
		}
		catch(Exception e) {
			System.err.println("Erro ao estabelecer conexão: " + e.getMessage());
		}
	}
}
