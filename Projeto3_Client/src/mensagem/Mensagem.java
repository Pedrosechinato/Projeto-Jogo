package mensagem;

//import bd.dbos.*;
import java.io.Serializable;


public class Mensagem implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String com, email, nome, senha, nomeSala, carta;
	private boolean status;
	private float moedas;
	private int pontos;
	
	/**
	 * Construtor para o cadastro, contendo todos os parametros
	 * @param com
	 * @param email
	 * @param senha
	 * @param nome
	 */
	public Mensagem(String com, String email, String senha, String nome, float moedas) {
		this.com = com;
		this.email = email;
		this.nome = nome;
		this.senha = senha;
		this.setMoedas(moedas);
	}
	
	/**
	 * Construtor para o login, contendo somente email e senha
	 * @param com
	 * @param email
	 * @param senha
	 */
	public Mensagem(String com, String email, String senha) {
		this.com = com;
		this.email = email;
		this.senha = senha;
	}
	/**
	 * Construtor para criar sala, contendo somente o nome da sala
	 * @param com
	 * @param nomeSala
	 */
	public Mensagem(String com, String nomeSala) {
		this.com = com;
		this.setNomeSala(nomeSala);
	}
	/**
	 * Construtor para consulta das salas
	 * @param com
	 * @param nome_Sala
	 * @param status
	 */
	public Mensagem(String com, String nome_Sala, boolean status) {
		this.com = com;
		this.setNomeSala(nome_Sala);
		this.setStatus(status);
	}
	public Mensagem(String com) {
		this.com = com;
	}

	/**
	 * Método obrigatório hashCode
	 */

	 public String toString ()
	    {
	        String ret="email:"+this.email+"senha:"+this.senha+"nome:"+this.nome+"moedas:"+this.moedas+"status:"+this.status+"Com:"+this.com+"Nome Sala:"+this.nomeSala;
	  

	        return ret;
	    }

	    public int hashCode ()
	    {
	        int ret=666;

	        ret = 7*ret + new Integer(this.nome).hashCode ();
	        ret = 7*ret + new Integer(this.senha   ).hashCode ();
	        ret = 7*ret + new Integer(this.email   ).hashCode ();
	    	ret = 7*ret + new Integer((int) this.moedas  ).hashCode ();
	    	ret = 7*ret + new Boolean(this.status  ).hashCode ();
	    	ret = 7*ret + new Integer(this.com     ).hashCode ();
	    	ret = 7*ret + new Integer(this.nomeSala).hashCode ();

	        return ret;
	    }
	/**
	 * Método obrigatório equals
	 * @return
	 */
	public boolean equals (Object obj)
    {
        if (this==obj)
            return true;

        if (obj==null)
            return false;

        if (this.getClass() != obj.getClass())
            return false;

        Mensagem msg = (Mensagem)obj;

        if (this.email != msg.email)
            return false;
        if (this.nome != msg.nome)
        	return false;
        if (this.senha != msg.senha)
        	return false;
        if(this.moedas != msg.moedas)
        	return false;
        if(this.status != msg.status)
        	return false;
        if(this.com != msg.com)
        	return false;
        if(this.nomeSala != msg.nomeSala)
        	return false;

        return true;
    }
	
	
	public String getCom() {
		return com;
	}

	public void setCom(String com) {
		this.com = com;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getNomeSala() {
		return nomeSala;
	}

	public void setNomeSala(String nomeSala) {
		this.nomeSala = nomeSala;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public float getMoedas() {
		return moedas;
	}

	public void setMoedas(float moedas) {
		this.moedas = moedas;
	}

	public String getCarta() {
		return carta;
	}

	public void setCarta(String carta) {
		this.carta = carta;
	}

	public int getPontos() {
		return pontos;
	}

	public void setPontos(int pontos) {
		this.pontos = pontos;
	}
}
