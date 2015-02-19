package toDeOlho.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import toDeOlho.entidades.Categoria;

public class CategoriaDAO {
	
	private Connection con;  
	private Statement comando;

	public CategoriaDAO() {
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
	
	public List<Categoria> getCategorias() throws SQLException{
		conectar();
		ResultSet rs;
		Categoria cat = null;
		List<Categoria> categorias = new ArrayList<Categoria>();
		try {
			
			String sql = "select * from Categoria";
			
			System.out.println("Entrou no getCategorias sql:"+sql); 
			
			PreparedStatement ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {  
				//pega todos os atributos da postagem
				cat = new Categoria();
				cat.setId(rs.getInt("idCategoria"));
				cat.setCategoria(rs.getString("nomeCategoria"));
				categorias.add(cat);
			}
			return categorias;
		} catch (SQLException e) {  
			throw new SQLException(e.getMessage());  
		}
	}
	
	public Categoria getCategoria(Integer codigo) throws SQLException{
		conectar();
		ResultSet rs;
		Categoria categoria = null;
		try {  
			rs = comando.executeQuery("select * from Categoria where idLocalizacao = "+codigo);
			while (rs.next()) {  
				// pega todos os atributos os comentarios
				categoria = new Categoria();
				categoria.setId(rs.getInt("id"));
				categoria.setCategoria(rs.getString("nomeCategoria")); 
			}
			return categoria;
		} catch (SQLException e) {
			throw new SQLException(e.getMessage()); 
		}
	}
}