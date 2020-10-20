package bd.dbos;

public class Usuario
{
    private String  email;
    private String  nome;
    private String  senha;
    private float   moedas;
 
    
    public void setSenha (String senha) throws Exception
    {
        if (senha==null || senha.equals(""))
            throw new Exception ("Senha invalida");

        this.senha = senha;
    }   

    public void setNome (String nome) throws Exception
    {
        if (nome==null || nome.equals(""))
            throw new Exception ("Nome nao fornecido");

        this.nome = nome;
    }

    public void setEmail (String email) throws Exception
    {
        if (email==null || email.equals(""))
            throw new Exception ("Email invalido");

        this.email = email;
    }
    
    public void setMoedas (float moedas) throws Exception
    {
    	if(moedas == 0)
    		throw new Exception("Sem moedas");
    	
    	this.moedas = moedas;
    }
    
    public void setPrim(float moedas) {
    	this.moedas = 1000;
    }
    
    public String getSenha ()
    {
        return this.senha;
    }

    public String getNome ()
    {
        return this.nome;
    }

    public String getEmail ()
    {
        return this.email;
    }
    
    public float getMoedas()
    {
    	return this.moedas;
    }
    
    public float getPrim()
    {
    	return this.moedas;
    }

    public Usuario (String Senha, String nome, String Email, float moedas) throws Exception
    {
        this.setSenha  (Senha);
        this.setNome   (nome);
        this.setEmail  (Email);
        this.setMoedas (moedas);
    }

    /**
     * Compara
     */
    public boolean equals (Object obj)
    {
        if (this==obj)
            return true;

        if (obj==null)
            return false;

        if (this.getClass() != obj.getClass())
            return false;

        Usuario usu = (Usuario)obj;

        if (this.email != usu.email)
            return false;
        if (this.nome != usu.nome)
        	return false;
        if (this.senha != usu.senha)
        	return false;
        if(this.moedas != usu.moedas)
        	return false;

        return true;
    }

    public String toString ()
    {
        String ret="email:"+this.email+"senha:"+this.senha+"nome:"+this.nome+"moedas:"+this.moedas;


        return ret;
    }
    public Object clone() {
 	   Usuario ret = null;
 	   try {
 		   ret = new Usuario (this.senha, this.nome, this.email,  this.moedas) ;
 	   }
 	   catch(Exception erro) {}
 	   return ret;
    }

    public int hashCode ()
    {
        int ret=666; //so nao vale zero

        ret = 7*ret + new Integer(this.nome).hashCode ();
        ret = 7*ret + new Integer(this.senha  ).hashCode ();
        ret = 7*ret + new Integer(this.email   ).hashCode ();
        ret = 7*ret + new Integer((int) this.moedas   ).hashCode ();
        

        return ret;
    }
}