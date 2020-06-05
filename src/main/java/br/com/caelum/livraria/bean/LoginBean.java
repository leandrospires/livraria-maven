package br.com.caelum.livraria.bean;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.caelum.livraria.dao.UsuarioDao;
import br.com.caelum.livraria.modelo.Usuario;

@Named
@javax.faces.view.ViewScoped
public class LoginBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Usuario usuario = new Usuario();
	
	@Inject
	UsuarioDao usuarioDao;
	
	@Inject
	FacesContext context;
	
	public String efetuarLogin() {
		System.out.println("Efetuando Login: " + this.usuario.getEmail());

		boolean existe = usuarioDao.existe(this.usuario);
		
		context.getExternalContext().getFlash().setKeepMessages(true);
		
		if (existe) {
			context.getExternalContext().getSessionMap().put("usuarioLogado", this.usuario);
			return "livro?faces-redirect=true";
		} else {
			
			
			context.addMessage(null, new FacesMessage("Usuário ou senha inválidos!"));
			//"login:email"
			return "login?faces-redirect=true";
		}

	}
	
	public Usuario getUsuario() {
		return usuario;
	}

	public String deslogar() {
		context.getExternalContext().getSessionMap().remove("usuarioLogado");
		
		return "index?faces-redirect=true";
	}
	
	public String usuarioLogado() {
	
		Usuario usuarioLogado = (Usuario) context.getExternalContext().getSessionMap().get("usuarioLogado");
		
		if (usuarioLogado != null) {
			return usuarioLogado.getEmail();
		}
		
		return "Visitante";
	}

}
