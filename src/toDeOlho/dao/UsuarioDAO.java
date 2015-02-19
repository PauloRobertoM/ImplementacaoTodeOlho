package toDeOlho.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import toDeOlho.entidades.Usuario;

public class UsuarioDAO {
	
	private Connection con;  
	private Statement comando;

	public UsuarioDAO() {
		super();
	}

	private void conectar() {
		if (con == null) {
			try {  
				con = FabricaConexao.getConexao();  
				comando = con.createStatement();  
				System.out.println("Conectado!");  
			} catch (ClassNotFoundException e) {  
				imprimeErro("Erro ao carregar o driver", e.getMessage());  
			} catch (SQLException e) {  
				imprimeErro("Erro ao conectar", e.getMessage());  
			}  
		}
	} 

	public void fechar() {  
		try {  
			comando.close();  
			con.close();  
			System.out.println("Conex‹o Fechada");  
		} catch (SQLException e) {  
			imprimeErro("Erro ao fechar conex‹o", e.getMessage());  
		}  
	}  

	private void imprimeErro(String msg, String msgErro) {   
		System.err.println(msg);  
		System.out.println(msgErro);  
	}
	
	public boolean salvar(Usuario usuario) throws SQLException{
		conectar();
        try {
            String sql = "insert into Usuario (login,senha)"
                    +"values(?,?)";
            
            PreparedStatement stmt = (PreparedStatement) con.prepareStatement(sql);
          
            stmt.setString(1,usuario.getLogin());
            stmt.setString(2,usuario.getSenha());
            
            stmt.execute();
        
            return true;
            
        } catch (SQLException e) {
        	throw new SQLException(e.getMessage()); 
        }
    }

	public Usuario autenticar(String login, String senha) {  
		conectar();
		ResultSet rs;
		Usuario usr = null;
		try {
			PreparedStatement ps = con.prepareStatement("SELECT * FROM Usuario WHERE login=? and senha=?");
			ps.setString(1, login);
			ps.setString(2, senha);
			rs = ps.executeQuery();
			while (rs.next()) {  
				// pega todos os atributos da postagem
				usr = new Usuario();
				usr.setId(rs.getInt("idUsuario"));
				usr.setLogin(rs.getString("login"));
				usr.setSenha(rs.getString("senha"));
			}
		} catch (SQLException e) {  
			imprimeErro("Erro ao buscar usuario", e.getMessage());  
		}
		return usr;
	} 
}