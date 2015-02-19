package toDeOlho.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class FabricaConexao {
	private static String MySQLDriver;
    private static String URL;
    private static String NOME;
    private static String SENHA;
    
    public static void bancoLocal(){
    	MySQLDriver = "com.mysql.jdbc.Driver";
    	URL = "jdbc:mysql://localhost/denuncias2";
    	NOME = "root";
    	SENHA = "admin";
    }
    
    public static void bancoRemoto(){
    	MySQLDriver = "com.mysql.jdbc.Driver";
    	URL = "jdbc:mysql://mysql.leiros.eti.br:3306/leiros01";
    	NOME = "leiros01";
    	SENHA = "ninocafe";
    }

    public static Connection getConexao() throws ClassNotFoundException, SQLException { 
    		bancoLocal();
            Class.forName(MySQLDriver);
            return DriverManager.getConnection(URL, NOME, SENHA);  
    }  
}
