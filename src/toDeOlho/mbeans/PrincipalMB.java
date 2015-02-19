package toDeOlho.mbeans;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import toDeOlho.dao.DenunciaDAO;
import toDeOlho.dao.LocalizacaoDAO;
import toDeOlho.entidades.Denuncia;
import toDeOlho.entidades.Localizacao;


@ManagedBean(name="principal")
@ViewScoped
public class PrincipalMB implements Serializable {

	private static final long serialVersionUID = 6282984833168478406L;
	private MapModel simpleModel;
	private List<Denuncia> denuncias;
	private Denuncia selectDenuncia;
	

	
	@PostConstruct
    public void init() {
        this.simpleModel = new DefaultMapModel();
        this.denuncias = new ArrayList<Denuncia>();
     
        LocalizacaoDAO localizacaoDAO = new LocalizacaoDAO();
        try {
			List<Localizacao> localizacoes = localizacaoDAO.getLocalizacoes();
			DenunciaDAO denunciaDAO = new DenunciaDAO();
			this.denuncias = denunciaDAO.getDenuncias();
			
			for(Localizacao localizacao : localizacoes){
				System.out.println("localizacao lat: "+localizacao.getLatitude());
				LatLng coord1 = new LatLng(localizacao.getLatitude(), localizacao.getLongitude());
				simpleModel.addOverlay(new Marker(coord1, new String()));
				
			}
		} catch (SQLException e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Erro!", e.getMessage()));
		}
       
    }
	
	

	
	
	
	
	
	public MapModel getSimpleModel() {
        return simpleModel;
    }

	/**
	 * @return the denuncias
	 */
	public List<Denuncia> getDenuncias() {
		return denuncias;
	}

	/**
	 * @param denuncias the denuncias to set
	 */
	public void setDenuncias(List<Denuncia> denuncias) {
		this.denuncias = denuncias;
	}

	/**
	 * @return the selectDenuncia
	 */
	public Denuncia getSelectDenuncia() {
		return selectDenuncia;
	}

	/**
	 * @param selectDenuncia the selectDenuncia to set
	 */
	public void setSelectDenuncia(Denuncia selectDenuncia) {
		this.selectDenuncia = selectDenuncia;
	}
	
}