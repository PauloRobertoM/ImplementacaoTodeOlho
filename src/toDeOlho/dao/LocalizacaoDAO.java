package toDeOlho.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import toDeOlho.entidades.Localizacao;

public class LocalizacaoDAO {
	
	private Connection con;  
	private Statement comando;

	public LocalizacaoDAO() {
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
	
	public void salvar(Localizacao localizacao) throws SQLException{
		conectar();
        try {
            String sql = "insert into Localizacao (latitude,longitude)"
                    +"values(?,?)";
            
            System.out.println("metodo salvar de Localizacao SQL: "+sql);
            System.out.println("Parametros de Localizacao: "+
            					localizacao.getLatitude()+
            					localizacao.getLongitude());
            
            PreparedStatement stmt = (PreparedStatement) con.prepareStatement(sql);
          
            stmt.setDouble(1, localizacao.getLatitude());
            stmt.setDouble(2, localizacao.getLongitude());
            
            
            stmt.execute();
            
        } catch (SQLException e) {
        	throw new SQLException(e.getMessage()); 
        }
    }
	
	public Localizacao getLocalizacao(Localizacao loc) throws SQLException{
		conectar();
		ResultSet rs;
		Localizacao localizacao = null;
		try {
			
			String sql = "select * from Localizacao where latitude = "+loc.getLatitude()+
															" and longitude = "+loc.getLongitude();
			
			System.out.println("metodo getLocalizacao sql: "+sql);
			
			rs = comando.executeQuery(sql);
			while (rs.next()) {  
				// pega todos os atributos os comentarios
				localizacao = new Localizacao();
				localizacao.setId(rs.getInt("idLocalizacao"));
				localizacao.setLatitude(rs.getDouble("latitude"));
				localizacao.setLongitude(rs.getDouble("longitude")); 
			}
			return localizacao;
		} catch (SQLException e) {
			throw new SQLException(e.getMessage()); 
		}
	}
	
	public List<Localizacao> getLocalizacoes() throws SQLException{
		conectar();
		ResultSet rs;
		Localizacao localizacao;
		List<Localizacao> localizacoes = new ArrayList<Localizacao>();
		try {
			
			String sql = "select b.idLocalizacao, b.latitude, b.longitude from Denuncia a "
					+ "inner join Localizacao b on b.idLocalizacao = a.Localizacao_id";
			
			System.out.println("metodo getLocalizacao sql: "+sql);
			
			rs = comando.executeQuery(sql);
			while (rs.next()) {  
				// pega todos os atributos os comentarios
				localizacao = new Localizacao();
				localizacao.setId(rs.getInt("idLocalizacao"));
				localizacao.setLatitude(rs.getDouble("latitude"));
				localizacao.setLongitude(rs.getDouble("longitude"));
				localizacoes.add(localizacao);
			}
			return localizacoes;
		} catch (SQLException e) {
			throw new SQLException(e.getMessage()); 
		}
	}
}