package toDeOlho.mbeans;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import toDeOlho.dao.PerfilDAO;
import toDeOlho.dao.UsuarioDAO;
import toDeOlho.entidades.Perfil;
import toDeOlho.entidades.Usuario;

@ManagedBean(name="usuario")
@SessionScoped
public class UsuarioMB {
	private String login, senha;
	private Usuario usuario;
	private String mensagem;
	private boolean logado;


	public UsuarioMB() {
		super();
		this.mensagem = "";
		this.logado = false;
		usuario = new Usuario();	
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public boolean isLogado() {
		return logado;
	}
	public boolean isNotLogado() {

		if(this.logado == true)
			return false;
		else
			return this.logado;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuarioB(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	
	public String salvar(){
		
		UsuarioDAO ud = new UsuarioDAO();
		Perfil perfil = new Perfil();
		PerfilDAO perfilDAO = new PerfilDAO();
		try {
			if(ud.salvar(this.usuario)){
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage("Sucesso!",  "Usuário salvo."));
				this.login = usuario.getLogin();
				this.senha = usuario.getSenha();
				this.autentica();

				perfil.setUsuario(usuario);
				perfilDAO.salvar(perfil);
				System.out.println("Salvar dentro de UsuarioDAO: ");
				System.out.println("Id usuario: "+usuario.getId());
				return "index.jsf";
			}
			else{
				return null;
			}
		} catch (SQLException e) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Erro!",  "Mensagem: " + e.getMessage()));
			return null;
		}	
	}

	public String autentica() {
		if (login!=null && !login.isEmpty() && senha!=null && !senha.isEmpty()) {
			UsuarioDAO dao = new UsuarioDAO();
			Usuario usr = dao.autenticar(login, senha);
			if (usr != null) {
				this.usuario = usr;
				this.logado = true;
				System.out.println("Usuário: "+usuario.getLogin()+". Conectado às "+Calendar.getInstance().getTime());
				return "index.jsf?faces-redirect=true";
			} else {
				this.mensagem = "Login e senha não correspondem a um usuário válido!";
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage("Erro!",  "Mensagem: " + mensagem));
			}
		} else {
			this.mensagem = "Parâmetros inválidos!";
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Erro!",  "Mensagem: " + mensagem));
		}
		return "cadastrarUsuario.jsf";
	}
	
	/**
	 * Metodo responsavel por fazer a verificação seu um usuario está logado no sistema.
	 */
	public void verificaSessao(){
		System.out.println("Sessão verificada. Usuário logado: "+this.logado);
		if (this.logado == false){
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("login.jsf");
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}	
	}

	public String logout() {
		this.usuario = new Usuario();
		this.logado = false;
		return "index.jsf?faces-redirect=true";
	}

}
