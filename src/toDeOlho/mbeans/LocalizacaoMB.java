package toDeOlho.mbeans;

import java.io.Serializable;
import java.sql.SQLException;


import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;


import toDeOlho.dao.LocalizacaoDAO;
import toDeOlho.entidades.Localizacao;
@ManagedBean(name="localizacao")
@RequestScoped
public class LocalizacaoMB implements Serializable {
	
	
	private static final long serialVersionUID = -3778720335664023997L;
	private MapModel emptyModel;
	private Localizacao localizacao;
	
	public LocalizacaoMB(){
		this.localizacao = new Localizacao();
	}
	
	@PostConstruct
    public void init() {
        emptyModel = new DefaultMapModel();
    }
	
	public MapModel getEmptyModel() {
        return emptyModel;
    }
	
	public void addMarker() {
        Marker marker = new Marker(new LatLng(localizacao.getLatitude(),
        										localizacao.getLongitude()), 
        											new String());
        emptyModel.addOverlay(marker);
          
    }
	
	
	
	
	public void salvar(){
		
		try {
			LocalizacaoDAO localizacaoDAO = new LocalizacaoDAO();
			localizacaoDAO.salvar(localizacao);
			this.localizacao = localizacaoDAO.getLocalizacao(this.localizacao); 
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Sucesso!", "Localiza�‹o Salva."));
		} catch (SQLException e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Erro!", e.getMessage()));
		}
	}

	public Localizacao getLocalizacao() {
		return localizacao;
	}

	public void setLocalizacao(Localizacao localizacao) {
		this.localizacao = localizacao;
	}
	
	
}
