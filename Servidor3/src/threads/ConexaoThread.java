package threads;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;


import bd.daos.Usuarios;
import bd.dbos.Usuario;
import mensagem.Mensagem;
import Jogo.Jogador;
import Jogo.Partida;
import Main.Main;

public class ConexaoThread extends Thread {
	private Socket conexao;
	
    public ConexaoThread(Socket clientSocket) {
        this.conexao = clientSocket;
    }
    
    /**
     * Thread que trata a conexao e retorna erro ou sucesso
     */
    public void run() {
    	
    	ObjectInputStream receptor;
    	ObjectOutputStream transmissor;
    	Temporizador contador;
    	Mensagem mensagem;
    	Object obj;
    	
    	
		try {
			receptor = new ObjectInputStream(conexao.getInputStream());
			transmissor = new ObjectOutputStream(conexao.getOutputStream());
			
    		obj = receptor.readObject();
    	
    		mensagem = (mensagem.Mensagem) obj;
	    	Usuarios usuariosDAO = new Usuarios();
	    	
	    	/**
	    	 * Consulta Partidas em excução
	    	 */
	    	if	(mensagem.getCom().equals("PAR")) {
	    		int i = 0;
	    		String nome_sala = "";
	    		mensagem.setCom("PAR");
	    		while(Main.listaglobal.getQtd() >=  i) 
	    		{			    				
	    		    nome_sala += ((Partida)Main.listaglobal.getItem(i)).getNome_Sala()+",";
	    				
	    			i++;
	    		}
	    		mensagem.setNomeSala(nome_sala);
	    	}
	    	/**
	    	 * Cadastra Jogador
	    	 */
	    	else if(mensagem.getCom().equals("CAD"))
    		{
    			if(!usuariosDAO.cadastrado(mensagem.getEmail()))
        		{
        			if(usuariosDAO.incluir(new Usuario(mensagem.getSenha(), mensagem.getNome(), mensagem.getEmail(), mensagem.getMoedas())))
					{
        				mensagem.setCom("SUC");
					}
        			else
        			{
        				mensagem.setCom("ERR");
        			}
        		}
    			else
    			{
    				mensagem.setCom("ERR");
    			}
    		}
    		/**
    		 * Loga o jogador e instancia novo jogador
    		 */
    		else if(mensagem.getCom().equals("LOG"))
    		{
    			if(usuariosDAO.logar(mensagem.getEmail(), mensagem.getSenha()))
        		{	
    				mensagem.setMoedas(usuariosDAO.getEmail(mensagem.getEmail()).getMoedas());
        			mensagem.setCom("SUC");
        		}
    			else
    			{
    				mensagem.setCom("ERR");
    			}
    		}
	    	
    		else if(mensagem.getCom().equals("ENT")){
    			
    				for(int i=0;i <= Main.listaglobal.getQtd();i++) {
    					if(((Partida)Main.listaglobal.getItem(i)).getNome_Sala().equals(mensagem.getNomeSala())) 
    						{
    							Jogador j = new Jogador(mensagem.getEmail(), usuariosDAO.getEmail(mensagem.getEmail()).getMoedas());
    				
    							mensagem.setMoedas(usuariosDAO.getEmail(mensagem.getEmail()).getMoedas());
    							mensagem.setNomeSala(((Partida)Main.listaglobal.getItem(i)).getNome_Sala());
	    						((Partida)Main.listaglobal.getItem(i)).entrar_partida(j);
    							mensagem.setCom("SUC");
	    						
    						}
    					}
    			
    		}
	    	
    		else if(mensagem.getCom().equals("APO")){
    	
    			if(mensagem.getMoedas() > usuariosDAO.getEmail(mensagem.getEmail()).getMoedas()) {
    				mensagem.setCom("ERR");
    			}
    			
    				for(int i=0;i <= Main.listaglobal.getQtd();i++) {
    					if(((Partida)Main.listaglobal.getItem(i)).getNome_Sala().equals(mensagem.getNomeSala())) 
    						{
    						if (!((Partida)Main.listaglobal.getItem(i)).isStatus()) {
    							mensagem.setCom("ERR");
    							break;
    						}
    						
    						((Partida)Main.listaglobal.getItem(i)).aposta(mensagem.getMoedas(), mensagem.getEmail());
    						System.out.print(usuariosDAO.getEmail(mensagem.getEmail()).getMoedas());
    						mensagem.setCarta(((Partida)Main.listaglobal.getItem(i)).sacar(mensagem.getEmail()).toString()+"-"+((Partida)Main.listaglobal.getItem(i)).sacar(mensagem.getEmail()).toString());
    						mensagem.setCom("SUC");
    						}
    				
    				
    			}
    		}
    		else if(mensagem.getCom().equals("COM"))
    		{		
    				for(int i=0;i <= Main.listaglobal.getQtd();i++) {
    					if(((Partida)Main.listaglobal.getItem(i)).getNome_Sala().equals(mensagem.getNomeSala())) 
    						{
    						
    						mensagem.setCarta(((Partida)Main.listaglobal.getItem(i)).sacar(mensagem.getEmail()).toString());
    						mensagem.setCom("CAR");
    						mensagem.setPontos(((Partida)Main.listaglobal.getItem(i)).verPontos(mensagem.getEmail()));
    						
    						}
    				}
    				
    		}
    		else if(mensagem.getCom().equals("EOC"))
    		{		
				for(int i=0;i <= Main.listaglobal.getQtd();i++) 
				{
					if(((Partida)Main.listaglobal.getItem(i)).getNome_Sala().equals(mensagem.getNomeSala())) 
						{
						
						try 
						{
							int pontos = ((Partida)Main.listaglobal.getItem(i)).verPontos(mensagem.getEmail());
							((Partida)Main.listaglobal.getItem(i)).setPontos(pontos , mensagem.getEmail());
							((Partida)Main.listaglobal.getItem(i)).JogadorParou();
							while (true) 
							{   
					            if(((Partida)Main.listaglobal.getItem(i)).getQtdParou()==((Partida)Main.listaglobal.getItem(i)).getQtd_jogadores())
					            {	
					            	
					            	String com= "";
					            	com = (((Partida)Main.listaglobal.getItem(i)).vencedorWin(mensagem.getEmail()));
					            	
					            	mensagem.setMoedas(usuariosDAO.getEmail(mensagem.getEmail()).getMoedas());
					            	mensagem.setCom(com);
					            	
					            	if(usuariosDAO.getEmail(mensagem.getEmail()).getMoedas() <= 0) {
					            		((Partida)Main.listaglobal.getItem(i)).jogadorSai(mensagem.getEmail());
					            		contador = new Temporizador(mensagem.getEmail());
					            		new Thread(contador).start();
					            	}
					            	break;
					            }
					        }
						}
						catch(Exception e) 
							{
							System.err.println("Erro ao estabelecer conexão: " + e.getMessage());
							}
						}
				
				}
		 }
    		
    		else if(mensagem.getCom().equals("INI")){
    			for(int i =0; i<= Main.listaglobal.getQtd(); i++)
    			{
    				if(((Partida)Main.listaglobal.getItem(i)).getNome_Sala().equals(mensagem.getNomeSala()) )
    				{
    					if(((Partida)Main.listaglobal.getItem(i)).getQtd_jogadores() < 3)
						{
    						mensagem.setCom("ERR");
							break;
						}
    					if(((Partida)Main.listaglobal.getItem(i)).isStatus())
						{
    						mensagem.setCom("ERR");
							break;
						}
    					if(((Partida)Main.listaglobal.getItem(i)).comeca_partida())
    					mensagem.setCom("SUC");
    					
    				}
    				else
    					mensagem.setCom("ERR");
    			}
    		}
    				
    		else if(mensagem.getCom().equals("CRI"))
    		{	
   
    				if(Main.listaglobal.vazia()) {
    				Partida p = new Partida(mensagem.getNomeSala());
    				mensagem.setCom("SUC"); 
    				mensagem.setNomeSala(mensagem.getNomeSala());
    				Main.listaglobal.guarde(p);
    				}
    				else if(mensagem.getCom().equals("CRI"))
    				{	
    					for(int i=0;i<=Main.listaglobal.getQtd();i++) 
    					{
    						if (((Partida)Main.listaglobal.getItem(i)).getNome_Sala().equals(mensagem.getNomeSala()))
    						{
    							mensagem.setCom("ERR");
    							break;
    						}
    					}
    					if(mensagem.getCom()!=("ERR")) 
        				{
       					 Partida p = new Partida(mensagem.getNomeSala());
       	   				 
       	   				 mensagem.setCom("SUC"); 
       	   				 mensagem.setNomeSala(mensagem.getNomeSala());
       	   				 Main.listaglobal.guarde(p); 
       					}
    				}
    		}	
    		else if(mensagem.getCom().equals("SAI"))
    		{	for(int i =0; i<= Main.listaglobal.getQtd(); i++)
				{
					if(((Partida)Main.listaglobal.getItem(i)).getNome_Sala().equals(mensagem.getNomeSala()) )
					{
						((Partida)Main.listaglobal.getItem(i)).jogadorSai(mensagem.getEmail());
						mensagem.setCom("SAI");
						break;
					}
				}	
    		}
    		else 
    		{ 	
    			mensagem.setCom("ERR");
    		}
    		
    		transmissor.writeObject(mensagem);
			transmissor.flush();
			
			transmissor.close();
			receptor.close();
	    	conexao.close();
		} catch (Exception e) {
			System.err.println("Erro ao tratar a conexão: " + e.getMessage());
		}     		
		
    }
}
