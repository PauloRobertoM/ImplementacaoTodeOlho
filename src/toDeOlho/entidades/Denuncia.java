package toDeOlho.entidades;

import java.io.Serializable;
import java.util.Date;

public class Denuncia implements Serializable {
	
	private static final long serialVersionUID = -841061546645294666L;
	private int id;
	private boolean aberta;
	private String midia;
	private String descricao;
	private Date data;
	private Usuario usuario;
	private Categoria categoria;
	private Localizacao localizacao;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public boolean isAberta() {
		return aberta;
	}
	
	public void setAberta(boolean aberta) {
		this.aberta = aberta;
	}
	
	public String getMidia() {
		return midia;
	}
	
	public void setMidia(String midia) {
		this.midia = midia;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public Date getData() {
		return data;
	}
	
	public void setData(Date data) {
		this.data = data;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public Categoria getCategoria() {
		return categoria;
	}
	
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
	public Localizacao getLocalizacao() {
		return localizacao;
	}
	
	public void setLocalizacao(Localizacao localizacao) {
		this.localizacao = localizacao;
	}
}
