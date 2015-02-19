package toDeOlho.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import toDeOlho.entidades.Perfil;
import toDeOlho.entidades.Usuario;

public class PerfilDAO {
	
	private Connection con;  
	private Statement comando;

	public PerfilDAO() {
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
	
	public void salvar(Perfil perfil) throws SQLException{
		conectar();
        try {
            String sql = "insert into Perfil (tipoPerfil,status,nome,email,senha,cpf,"
            		+ "cnpj,descricao,dataNascimento,imagem,Usuario_id)"
                    +"values(?,?,?,?,?,?,?,?,?,?,?)";
            
            System.out.println("metodo salvar dentro de PerfilDAO sql: "+sql);
            System.out.println("id do usuario: "+perfil.getUsuario().getId());
            
            PreparedStatement stmt = (PreparedStatement) con.prepareStatement(sql);
          
            stmt.setString(1,null);
            stmt.setString(2,null);
            stmt.setString(3,null);
            stmt.setString(4,null);
            stmt.setString(5,null);
            stmt.setString(6,null);
            stmt.setString(7,null);
            stmt.setString(8,null);
            stmt.setString(9,null);
            stmt.setString(10,null);
            stmt.setInt(11,perfil.getUsuario().getId());
            
            
            stmt.execute();
            
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