package toDeOlho.mbeans;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.SQLException;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.UploadedFile;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import toDeOlho.dao.DenunciaDAO;
import toDeOlho.dao.LocalizacaoDAO;
import toDeOlho.entidades.Categoria;
import toDeOlho.entidades.Denuncia;
import toDeOlho.entidades.Localizacao;
import toDeOlho.entidades.Usuario;

@ManagedBean(name="denuncia")
@RequestScoped
public class DenunciaMB implements Serializable{
	private static final long serialVersionUID = 1L;

	private MapModel emptyModel;

	private Denuncia denuncia;

	private UploadedFile upfile;

	public DenunciaMB(){
		super();
		denuncia = new Denuncia();
	}

	@PostConstruct
	public void init() {
		emptyModel = new DefaultMapModel();
	}

	/*
	 * #################Objetos beans externos################
	 */

	/**
	 * Metodo que acessa um Bean externo UsuarioMB, que retorna o usu‡rio da sess‹o. 
	 */

	@ManagedProperty(value = "#{usuario}")
	private UsuarioMB usuarioMB = new UsuarioMB();

	/**
	 * Metodo setters necess‡rio para manipula�‹o do bean UsuarioMB.
	 * @param usuarioMB.
	 */

	public void setUsuarioMB(UsuarioMB usuarioMB) {
		this.usuarioMB = usuarioMB;
	}

	/**
	 * Metodo que acessa um Bean extreno CategoriaMB, que retorna uma categoria.
	 */

	@ManagedProperty(value = "#{categoria}")
	private CategoriaMB categoriaMB = new CategoriaMB();

	/**
	 * Metodo setters necess‡rio para manipula�‹o do bean CategoriaMB.
	 * @param categoriaMB
	 */

	public void setCategoriaMB(CategoriaMB categoriaMB){
		this.categoriaMB = categoriaMB;
	}

	/**
	 *  Metodo que acessa um Bean extreno LocalizacaoMB, que retorna uma Localizacao.
	 */

	@ManagedProperty(value = "#{localizacao}")
	private LocalizacaoMB localizacaoMB = new LocalizacaoMB();

	/**
	 * Metodo setters necess‡rio para manipula�‹o do bean LocalizacaoMB.
	 * @param localizacaoMB
	 */

	public void setLocalizacaoMB(LocalizacaoMB localizacaoMB){
		this.localizacaoMB = localizacaoMB;
	}

	/**
	 * Metodo que retorna um mapa vazio.
	 * @return emptyModel
	 */

	public MapModel getEmptyModel() {
		return emptyModel;
	}

	/**
	 * Metodo respons‡vel por criar um marcador no mapa de inser�‹o de Localiza�‹o 
	 */

	public void addMarker() {
		Marker marker = new Marker(new LatLng(denuncia.getLocalizacao().getLatitude(),
				denuncia.getLocalizacao().getLongitude()), 
				new String());
		emptyModel.addOverlay(marker);

	}

	/**
	 * Metodo respons‡vel por fazer o controller instanciando as objetos DAO(LocalizcaoDAO, DenunciaDAO)
	 * que salvaram as informac›es no banco de dados.
	 * @return Redireciona para a p‡gina principal.
	 */

	public void salvar(){


		//Upload do arquivo

		//###########################################################//

		//System.out.println("=======================================");
//		System.out.println("Localiza�‹o dentro do bean: ");
//		System.out.println("Latitude: "+localizacao.getLatitude());
//		System.out.println("Longitude: "+localizacao.getLongitude());
//		System.out.println("ID localizcao: "+localizacao.getId());
//		System.out.println("Categoria dentro do bean: ");
//		System.out.println("ID categoria: "+categoria.getId());
//		System.out.println("Usuario dentro do bean: ");
//		System.out.println("ID usuario: "+usuario.getId());
//		System.out.println("Midia: "+this.denuncia.getMidia());
//		System.out.println("=======================================");


		//localizacaoMB.salvar();
		try {
			
			if(this.upfile != null) {

				denuncia.setMidia(this.upfile.getFileName());
				try {  
					System.out.println("Nome do arquivo: "+this.denuncia.getMidia());

					InputStream in = new BufferedInputStream(this.upfile.getInputstream());  

					//File file = new File("/webapps/ciospapp/resources/images/" + upfile.getFileName());
					File file = new File("/C:/Users/Nayara/workspace/PdsImplementacao/WebContent/resources/images/" + upfile.getFileName()); 

					String caminho = file.getAbsolutePath();
					System.out.println("Caminho absoluto: "+caminho);

					FileOutputStream fout = new FileOutputStream(file);  
					while (in.available() != 0) {  
						fout.write(in.read());  
					}  
					fout.close();

					FacesMessage msg = new FacesMessage("O Arquivo ", file.getName() + " salvo.");  
					FacesContext.getCurrentInstance().addMessage("msgUpdate", msg);  
				} catch (Exception ex) {  
					ex.getMessage();  
				} 
			}
			else{
				System.out.println("Arquivo nulo.");
			}
			
			//Instancia DenunciaDAO para persistir os dados
			DenunciaDAO denunciaDAO = new DenunciaDAO();
			Usuario usuario = usuarioMB.getUsuario();
			Categoria categoria = categoriaMB.getCat();
			Localizacao localizacao = localizacaoMB.getLocalizacao();
			
			//Salva a localiza�‹o
			LocalizacaoDAO localizacaoDAO = new LocalizacaoDAO();
			localizacaoDAO.salvar(localizacao);
			localizacao = localizacaoDAO.getLocalizacao(localizacao);
			
			
			this.denuncia.setUsuario(usuario);
			this.denuncia.setCategoria(categoria);
			this.denuncia.setLocalizacao(localizacao);

			denunciaDAO.salvar(this.denuncia);
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Sucesso!", "Denuncia Salva."));
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Metodo respons‡vel por retornar um objeto denuncia.
	 * @return denuncia
	 */
	public Denuncia getDenuncia() {
		return denuncia;
	}

	/**
	 * Metodo responsavel por setar um objeto denuncia.
	 * @param Recebe por parametro um objeto denuncia.
	 */
	public void setDenuncia(Denuncia denuncia) {
		this.denuncia = denuncia;
	}

	/**
	 * @return the upfile
	 */
	public UploadedFile getUpfile() {
		return upfile;
	}

	/**
	 * @param upfile the upfile to set
	 */
	public void setUpfile(UploadedFile upfile) {
		this.upfile = upfile;
	}
}
