package toDeOlho.mbeans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import toDeOlho.entidades.Perfil;

@ManagedBean(name="perfil")
@SessionScoped
public class PerfilMB {
	
	private Perfil perfil;
	
	public PerfilMB() {
		super();
		perfil = new Perfil();	
	}

	/**
	 * @return the perfil
	 */
	public Perfil getPerfil() {
		return perfil;
	}

	/**
	 * @param perfil the perfil to set
	 */
	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}
	
}