package toDeOlho.mbeans;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import toDeOlho.dao.DenunciaDAO;
import toDeOlho.entidades.Denuncia;

@ManagedBean(name="editarDenuncia")
@RequestScoped
public class EditarDenunciaMB implements Serializable{

	public int getCodigoDenuncia() {
		return codigoDenuncia;
	}

	public void setCodigoDenuncia(int codigoDenuncia) {
		this.codigoDenuncia = codigoDenuncia;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Denuncia denuncia = new Denuncia();
	private int codigoDenuncia;
	
	FacesContext fc = FacesContext.getCurrentInstance();
	
	
	
	public Denuncia getDenuncia() {
		return denuncia;
	}

	public void setDenuncia(Denuncia denuncia) {
		this.denuncia = denuncia;
	}

	public String editAction() {

		Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
		codigoDenuncia = Integer.parseInt(params.get("param"));
		System.out.println("##########################################"
				+ "esse é o param" + codigoDenuncia);

		DenunciaDAO denunciaDAO = new DenunciaDAO();
		try {
		this.denuncia = denunciaDAO.getDenuncia(codigoDenuncia);
		System.out.println("codigo da denuncia a editar "+codigoDenuncia);
		} catch (SQLException e) {
		FacesContext context = FacesContext.getCurrentInstance();
			System.out.println("erro "+ e.getMessage());
		}

		return "editarDenuncia";

		}
	
	@ManagedProperty(value = "#{localizacao}")
	private LocalizacaoMB localizacaoMB = new LocalizacaoMB();

	/**
	* Metodo setters necessário para manipulação do bean LocalizacaoMB.
	* @param localizacaoMB
	*/

	public void setLocalizacaoMB(LocalizacaoMB localizacaoMB){
	this.localizacaoMB = localizacaoMB;
	}
	
	public String alterar(){
		/*
		Localizacao localizacao = localizacaoMB.getLocalizacao();

		*LocalizacaoDAO localizacaoDAO = new LocalizacaoDAO();
		try {
			localizacaoDAO.salvar(localizacao);
			localizacao = localizacaoDAO.getLocalizacao(localizacao);
		} catch (SQLException e1) {
			e1.getMessage();
		}
		
		System.out.println("=======================================");
		System.out.println("Localização dentro do bean: ");
		System.out.println("Latitude: "+localizacao.getLatitude());
		System.out.println("Longitude: "+localizacao.getLongitude());
		System.out.println("ID localizcao: "+localizacao.getId());
		System.out.println("Categoria dentro do bean: ");
		System.out.println("=======================================");
		
		*/
		Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
		codigoDenuncia = Integer.parseInt(params.get("param"));
		System.out.println("o id que vai pro dao "+codigoDenuncia);
		System.out.println("editando nova descrição");
		System.out.println(this.denuncia.getDescricao());
		System.out.println("codigo da denuncia editada"+this.denuncia.getId());
		DenunciaDAO denunciaDao = new DenunciaDAO();
		try {
			//this.denuncia.setLocalizacao(localizacao);
			denunciaDao.alterar(this.denuncia, codigoDenuncia);
			return "gerenciarDenuncia";
		} catch (SQLException e) {
			e.getMessage();
		}
		return null;
	}
}
