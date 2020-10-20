package bd.daos;

import java.sql.*;

import bd.*;
import bd.core.MeuResultSet;
import bd.dbos.Usuario;

public class Usuarios
{
	/**
	 * Busca se um usuario existe pelo email
	 * @param Email
	 * @return
	 * @throws Exception
	 */
    public boolean logar (String email, String senha) throws Exception
    {
        boolean retorno = false;
        
        try
        {
        	
            String sql;

            sql = "SELECT * " +
                  "FROM usuarios " +
                  "WHERE email = ? " +
    			  "AND senha = ?";

            BD.COMANDO.prepareStatement (sql);
    	
            BD.COMANDO.setString (1, email);
            BD.COMANDO.setString (2, senha);            
			
			
            MeuResultSet resultado = (MeuResultSet) BD.COMANDO.executeQuery ();

            retorno = resultado.first(); 
            
        }
        catch (SQLException erro)
        {
            throw new Exception ("Erro ao procurar usuario");
        }

        return retorno;
    }
    
	/**
	 * Busca se um usuario existe pelo email
	 * @param Email
	 * @return
	 * @throws Exception
	 */
    public boolean cadastrado (String email) throws Exception
    {
        boolean retorno = false;

        try
        {
            String sql;

            sql = "SELECT * " +
                  "FROM usuarios " +
                  "WHERE email = ?";

            BD.COMANDO.prepareStatement (sql);

            BD.COMANDO.setString (1, email);

            MeuResultSet resultado = (MeuResultSet) BD.COMANDO.executeQuery ();

            retorno = resultado.first(); // pode-se usar resultado.last() ou resultado.next() ou resultado.previous() ou resultado.absotule(numeroDaLinha)

        }
        catch (SQLException erro)
        {
            throw new Exception ("Erro ao procurar usuario");
        }

        return retorno;
    }
    
    /**
     * Insere um usuario no banco
     * @param usuario
     * @throws Exception
     */
    public boolean incluir (Usuario usuario) throws Exception
    {
        if (usuario==null) 
        {
        	throw new Exception ("usuario nao fornecido");
        }
        
        try
        {
            String sql;
            

            sql = " INSERT INTO usuarios " +
                  " (nome,email,senha,moedas) " +
                  " VALUES " +
                  " (?,?,?,?) ";

            BD.COMANDO.prepareStatement (sql);

            
            
            BD.COMANDO.setString(1, usuario.getNome());
            BD.COMANDO.setString(2, usuario.getEmail());
            BD.COMANDO.setString(3, usuario.getSenha());
	        BD.COMANDO.setFloat(4,  usuario.getPrim());	
	        
            BD.COMANDO.executeUpdate();
            BD.COMANDO.commit();
            return true;
        }
        catch (SQLException erro)
        {
            throw new Exception ("Erro ao inserir Usuario");
        }
    }
    /**
     * Exclui um usuario do banco
     * @param Email
     * @throws Exception
     */
    public void excluir (String email) throws Exception
    {
        if (!cadastrado (email))
            throw new Exception ("Nao cadastrado");

        try
        {
            String sql;

            sql = "DELETE FROM usuarios " +
                  "WHERE email=?";

            BD.COMANDO.prepareStatement(sql);

            BD.COMANDO.setString(1, email);

            BD.COMANDO.executeUpdate();
            BD.COMANDO.commit();        
        }
        catch (SQLException erro)
        {
            throw new Exception ("Erro ao excluir Usuario");
        }
    }

    /**
     * Recebe um objeto usuario e altera no banco o usuario correspondente
     * @param usuario
     * @throws Exception
     */
    public void alterar (Usuario usuario) throws Exception
    {
        if (usuario==null)
            throw new Exception ("Usuario nao fornecido");

        if (!cadastrado (usuario.getEmail()))
            throw new Exception ("Nao cadastrado");

        try
        {
            String sql;

            sql = "UPDATE usuarios " +
                  "SET nome=? " +
                  "SET email=? " +
                  "SET moedas=? " +
                  "WHERE senha = ?";

            BD.COMANDO.prepareStatement (sql);

            BD.COMANDO.setString  (1, usuario.getNome());
            BD.COMANDO.setString  (2, usuario.getEmail());
            BD.COMANDO.setString  (3, usuario.getSenha());
            BD.COMANDO.setFloat   (4,usuario.getMoedas());

            BD.COMANDO.executeUpdate();
            BD.COMANDO.commit();
        }
        catch (SQLException erro)
        {
            throw new Exception ("Erro ao atualizar dados do usuario");
        }
    }
    
    synchronized public void upadateMoedas (String email, float moedas) throws Exception   
    {
        if (email==null)
            throw new Exception ("Usuario nao fornecido");

        if (!cadastrado (email))
            throw new Exception ("Nao cadastrado");

        try
        {
            String sql;

            sql = "UPDATE usuarios " +
                  "SET moedas=? " +
                  "WHERE email =?";

            BD.COMANDO.prepareStatement (sql);

            BD.COMANDO.setFloat  (1, moedas);
            BD.COMANDO.setString  (2,email);

            BD.COMANDO.executeUpdate();
            BD.COMANDO.commit();
            
        }
        catch (SQLException erro)
        {
            throw new Exception ("Erro ao atualizar dados do usuario");
        }
      
    }

    /**
     * Obtem um usuario pelo email
     * @param Email
     * @return
     * @throws Exception
     */
    public Usuario getEmail (String email) throws Exception
    {
        Usuario usuario = null;

        try
        {
            String sql;

            sql = "SELECT * " +
                  "FROM usuarios " +
                  "WHERE email = ?";

            BD.COMANDO.prepareStatement (sql);

            BD.COMANDO.setString (1, email);

            MeuResultSet resultado = (MeuResultSet)BD.COMANDO.executeQuery ();

            if (!resultado.first())
                throw new Exception ("Nao cadastrado");

            usuario = new Usuario (resultado.getString("email"),
                               resultado.getString("nome"),
                               resultado.getString ("senha"),
                               resultado.getFloat("moedas"));
        }
        catch (SQLException erro)
        {
            throw new Exception ("Erro ao recuperar Usuario pelo email");
        }

        return usuario;
    }

    /**
     * Obtem todos os usuarios
     * @return
     * @throws Exception
     */
    public MeuResultSet getUsuarios() throws Exception
    {
        MeuResultSet resultado = null;

        try
        {
            String sql;

            sql = "SELECT * " +
                  "FROM usuarios";

            BD.COMANDO.prepareStatement (sql);

            resultado = (MeuResultSet)BD.COMANDO.executeQuery ();
        }
        catch (SQLException erro)
        {
            throw new Exception ("Erro ao recuperar usuarios");
        }

        return resultado;
    }
}