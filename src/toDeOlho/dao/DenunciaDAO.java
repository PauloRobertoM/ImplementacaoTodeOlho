package toDeOlho.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import toDeOlho.entidades.Categoria;
import toDeOlho.entidades.Denuncia;
import toDeOlho.entidades.Localizacao;
import toDeOlho.entidades.Usuario;
import toDeOlho.exception.ClasseNaoFuncionaException;

public class DenunciaDAO {
	
	private Connection con;  
	private Statement comando;

	public DenunciaDAO() {
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
	
	public void salvar(Denuncia denuncia) throws SQLException{
		conectar();
        try {
            String sql = "insert into Denuncia (aberta, midia, descricao, data, Categoria_id, Localizacao_id, Usuario_id)"
                    +"values(?,?,?,?,?,?,?)";
            
            System.out.println("metodo salvar de Denuncia SQL: "+sql);
            System.out.println("Parametros de denuncia: "+"\n"+
            					"ID: "+denuncia.getId()+"\n"+
            					"Midia: "+denuncia.getMidia()+"\n"+
            					"Descricao: "+denuncia.getDescricao()+"\n"+
            					"Data: "+"Ser‡ criada pelo sistema"+"\n"+
            					"Categoria: "+denuncia.getCategoria().getId()+"\n"+
            					"Localizacao: "+denuncia.getLocalizacao().getId()+"\n"+
            					"Usuario: "+denuncia.getUsuario().getId());
            
            PreparedStatement stmt = (PreparedStatement) con.prepareStatement(sql);
          
            stmt.setInt(1, 1);
            stmt.setString(2,denuncia.getMidia());
            stmt.setString(3,denuncia.getDescricao());
            java.util.Date data = new java.util.Date();
			stmt.setDate(4, new Date(data.getTime()));
			stmt.setInt(5, denuncia.getCategoria().getId());
			stmt.setInt(6, denuncia.getLocalizacao().getId());
			stmt.setInt(7, denuncia.getUsuario().getId());
            
            stmt.execute();
            
        } catch (SQLException e) {
        	throw new SQLException(e.getMessage()); 
        }
    }
	
	public List<Denuncia> getDenuncias() throws SQLException{
		conectar();
		ResultSet rs;
		Denuncia denuncia;
		Categoria categoria;
		Localizacao localizacao;
		List<Denuncia> denuncias = new ArrayList<Denuncia>();
		try {
			
			String sql = "select b.idCategoria, b.nomeCategoria, a.idDenuncia, a.aberta, a.midia, "
					+ "a.descricao, a.data, c.idLocalizacao, c.latitude, c.longitude from Denuncia a "
					+ "inner join Categoria b ON b.idCategoria = a.Categoria_id "
					+ "inner join Localizacao c ON c.idLocalizacao = a.Localizacao_id";
			
			System.out.println("metodo getDenuncias sql: "+sql);
			
			rs = comando.executeQuery(sql);
			while (rs.next()) {  
				// pega todos os atributos os comentarios
				denuncia = new Denuncia();
				
				categoria = new Categoria();
				categoria.setId(rs.getInt("idCategoria"));
				categoria.setCategoria(rs.getString("nomeCategoria"));
				
				localizacao =new Localizacao();
				localizacao.setId(rs.getInt("idLocalizacao"));
				localizacao.setLatitude(rs.getDouble("latitude"));
				localizacao.setLongitude(rs.getDouble("longitude"));
				
				denuncia.setId(rs.getInt("idDenuncia"));
				denuncia.setAberta(rs.getBoolean("aberta"));
				denuncia.setMidia(rs.getString("midia"));
				denuncia.setDescricao(rs.getString("descricao"));
				denuncia.setData(rs.getDate("data"));
				denuncia.setCategoria(categoria);
				denuncia.setLocalizacao(localizacao);
				denuncias.add(denuncia);
			}
			return denuncias;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new SQLException(e.getMessage()); 
		}
	}
	
	public void deletar(int codigoDenuncia) throws SQLException {
		conectar();
		try{

			String sql = "DELETE FROM Denuncia WHERE idDenuncia = ?";

			PreparedStatement stmt = (PreparedStatement) con.prepareStatement(sql);

			stmt.setInt(1, codigoDenuncia);

			stmt.execute();
			stmt.close();

		}
		catch(SQLException e){
			throw new SQLException(e.getMessage());
		}
	}
	
	/*
	 * Listar denuncia por usuario
	 * será usado para gerenciar denuncia
	 */
	public List<Denuncia> listarDenuncias(Usuario usuario) throws ClasseNaoFuncionaException, SQLException {  
		conectar();
		ResultSet rs;  
		try {
			String sql = "select a.idDenuncia, a.midia, b.idCategoria, b.nomeCategoria, a.descricao, c.latitude, "
						+ "c.longitude, c.idLocalizacao from Denuncia a inner join "
						+ "Categoria b ON a.Categoria_id = b.idCategoria inner join  "
						+ "Localizacao c ON a.Localizacao_id = c.idLocalizacao "
						+ "where Usuario_id = "+ usuario.getId();
			
			rs = comando.executeQuery(sql);
			List<Denuncia> denuncias = new ArrayList<Denuncia>();
			System.out.println("Listar denuncias SQL: "+sql);
			while (rs.next()) {  
				// pega todos os atributos da denuncia
				Denuncia denuncia = new Denuncia();
				Categoria categoria = new Categoria();
				denuncia.setId(rs.getInt("idDenuncia"));
				denuncia.setMidia(rs.getString("midia"));
				categoria.setId(rs.getInt("idCategoria"));
				categoria.setCategoria(rs.getString("nomeCategoria"));
				denuncia.setCategoria(categoria);
				denuncia.setDescricao(rs.getString("descricao"));
				Localizacao localizacao = new Localizacao();
				localizacao.setId(rs.getInt("idLocalizacao"));
				localizacao.setLatitude(rs.getDouble("latitude"));
				localizacao.setLongitude(rs.getDouble("longitude"));
				denuncia.setLocalizacao(localizacao);
				denuncias.add(denuncia);  
			}
			return denuncias;
		} catch (SQLException e) {
			throw new SQLException(e.getMessage()); 
		}
		
		
	}
	
	/*
	 * retorna um objeto denuncia a partid do codigo
	 * */
	public Denuncia getDenuncia(int codigoDenuncia) throws SQLException {
		conectar();
		ResultSet rs;  
		try {  
			String sql = "select b.idCategoria, b.nomeCategoria, a.idDenuncia, a.aberta, a.midia,"+
					" a.descricao, a.data, c.idLocalizacao, c.latitude, c.longitude from Denuncia a inner"
					+ " join Categoria b ON b.idCategoria = a.Categoria_id inner join Localizacao c ON c.idLocalizacao"
					+ " = a.Localizacao_id and idDenuncia = " + codigoDenuncia;
			rs = comando.executeQuery(sql);
			Denuncia denuncia = new Denuncia();
			Categoria categoria = new Categoria();
			Localizacao localizacao = new Localizacao();
			System.out.println("retornou uma denuncia"+sql);
			while (rs.next()) {  
				// pega todos os atributos da denuncia

				categoria = new Categoria();
				categoria.setId(rs.getInt("idCategoria"));
				categoria.setCategoria(rs.getString("nomeCategoria"));

				localizacao = new Localizacao();
				localizacao.setId(rs.getInt("idLocalizacao"));
				localizacao.setLatitude(rs.getDouble("latitude"));
				localizacao.setLongitude(rs.getDouble("longitude"));

				denuncia.setId(rs.getInt("idDenuncia"));
				denuncia.setAberta(rs.getBoolean("aberta"));
				denuncia.setMidia(rs.getString("midia"));
				denuncia.setDescricao(rs.getString("descricao"));
				denuncia.setData(rs.getDate("data"));

				denuncia.setCategoria(categoria);
				denuncia.setLocalizacao(localizacao);


			}
			return denuncia;
		} catch (SQLException e) {
			throw new SQLException(e.getMessage()); 
		}
	}

	
	public void alterar(Denuncia denuncia, int codigoDenuncia) throws SQLException {
		conectar();
		try{
			
			String sql = "update Denuncia set descricao=? where idDenuncia=?";
			
			System.out.println(sql);

			PreparedStatement stmt = (PreparedStatement) con.prepareStatement(sql);

			stmt.setString(1, denuncia.getDescricao());
			stmt.setInt(2, codigoDenuncia);

			stmt.execute();
			stmt.close();

		}
		catch(SQLException e){
			System.out.println(e.getMessage()); 
		}	}
}