package bd;

import bd.core.*;
import bd.daos.*;

public class BD
{
    public static final MeuPreparedStatement COMANDO;
    public static final Usuarios USUARIOS;
    
    static
    {
    	MeuPreparedStatement comando  = null;
    	Usuarios             usuarios = null;
    	
    	try
        {
            comando =
            		new MeuPreparedStatement (
            		"com.microsoft.sqlserver.jdbc.SQLServerDriver",
                    "jdbc:sqlserver://fs5:1433;databasename=poo201825",
                    "poo201825", "Dntlg8");

            usuarios = new Usuarios ();
           
        }
        catch (Exception erro)
        {
            System.err.println ("Problemas de conexao com o BD");
            System.exit(0);
        }
    	
        COMANDO = comando;
        USUARIOS  = usuarios;
    }
}