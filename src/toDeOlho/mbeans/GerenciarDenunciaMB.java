package toDeOlho.mbeans;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import toDeOlho.dao.DenunciaDAO;
import toDeOlho.entidades.Denuncia;
import toDeOlho.entidades.Usuario;
import toDeOlho.exception.ClasseNaoFuncionaException;

@ManagedBean(name="gerenciarDenuncia")
@ViewScoped
public class GerenciarDenunciaMB implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int codigoDenuncia;
	List<Denuncia> denuncias = new ArrayList<Denuncia>();
	FacesContext context = FacesContext.getCurrentInstance();
	FacesMessage facesMessage;
	
	@ManagedProperty(value = "#{usuario}")
    private UsuarioMB usuarioMB = new UsuarioMB();
	
	public void setUsuarioMB(UsuarioMB usuarioMB) {
        this.usuarioMB = usuarioMB;
	}
	
	@PostConstruct
	public void init() {
		try {
			DenunciaDAO denunciaDAO = new DenunciaDAO();
			Usuario usuario = usuarioMB.getUsuario();
			System.out.println("usuario: "+usuario.getLogin());
			denuncias = denunciaDAO.listarDenuncias(usuario);
		} catch (ClasseNaoFuncionaException e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Erro!", e.getMessage()));
		} catch (SQLException e) {
			
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Erro!", e.getMessage()));
		}
	}
	
	public List<Denuncia> getDenuncias() {
		return denuncias;
	}

	public void setDenuncias(List<Denuncia> denuncias) {
		this.denuncias = denuncias;
	}
	
	/**
	 * Método para excluir a denúncia
	 * 	irá pegar o id passado como parametro e chamar o daoDenuncia
	 * 
	 */

	public String excluirDenuncia() throws ClasseNaoFuncionaException {
		System.out.println("metodo excluir..");
//			Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
//			codigoDenuncia = Integer.parseInt(params.get("param"));
		codigoDenuncia =  Integer.parseInt(FacesContext.getCurrentInstance().  
				getExternalContext().getRequestParameterMap().get("id"));  
		System.out.println("id: " + codigoDenuncia);

		DenunciaDAO denunciaDAO = new DenunciaDAO();
		try {
			denunciaDAO.deletar(codigoDenuncia);
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Sucesso!", "Denúncia Deletada."));
			return "gerenciarDenuncia.jsf?faces-redirect=true";	
		} catch (SQLException e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Erro!", e.getMessage()));
			return null;
		}

	}

	public int getCodigoDenuncia() {
		return codigoDenuncia;
	}

	public void setCodigoDenuncia(int codigoDenuncia) {
		this.codigoDenuncia = codigoDenuncia;
	}	
	
	public void editarDenuncia(){
		String numero = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
		codigoDenuncia =  Integer.parseInt(numero);   

	}
}
