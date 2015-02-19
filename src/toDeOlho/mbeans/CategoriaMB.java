package toDeOlho.mbeans;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import toDeOlho.dao.CategoriaDAO;
import toDeOlho.entidades.Categoria;

@ManagedBean(name="categoria")
@RequestScoped
public class CategoriaMB {
	private Categoria cat = new Categoria();
	private List<SelectItem> categoriaItens;
	
	@PostConstruct
	public void init() {
		this.listarCategorias();
	}
	
	/**
	 * Metodo responsavel por listar categorias.
	 */
	public void listarCategorias(){
			
		List<Categoria> categorias = new ArrayList<Categoria>();
		this.categoriaItens = new ArrayList<SelectItem>();
		
		try {
			
			CategoriaDAO categoriaDAO = new CategoriaDAO();
			categorias = categoriaDAO.getCategorias();
			categoriaItens.add(new SelectItem(null, "Selecione"));
			for(Categoria categoria : categorias){
				categoriaItens.add(new SelectItem(categoria.getId(),categoria.getCategoria()));
			}
			
		} catch (SQLException e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Erro!",  "Mensagem: " + e.getMessage()));
		}
	}


	/**
	 * @return the cat
	 */
	public Categoria getCat() {
		return cat;
	}

	/**
	 * @param cat the cat to set
	 */
	public void setCat(Categoria cat) {
		this.cat = cat;
	}

	/**
	 * @return the categoriaItens
	 */
	public List<SelectItem> getCategoriaItens() {
		return categoriaItens;
	}


	/**
	 * @param categoriaItens the categoriaItens to set
	 */
	public void setCategoriaItens(List<SelectItem> categoriaItens) {
		this.categoriaItens = categoriaItens;
	}
	
}
