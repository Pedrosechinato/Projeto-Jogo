package threads;

import bd.BD;


public class Temporizador implements Runnable {
	private String email;
	private int tempo = 30;
	
	public Temporizador (String email) {
		setEmail(email);
		
	}
	@Override
	public void run() {
		try {
			while (tempo >= 0) {
				ContaTempo();
			}
		} catch (Exception erro) {
			erro.getMessage();
		}
	}
		
	
	public synchronized void ContaTempo() {
		try {
			if (tempo == 0) 
				{
				BD.USUARIOS.upadateMoedas(this.email, 200);
				}
			
			if (tempo >= 0) {
				int minutos = tempo / 60;
				int segundos = (tempo - (minutos * 60));
				tempo--;
				Thread.sleep(1000);
			}
		} catch (Exception e) {
			e.getMessage();
		}
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
}
