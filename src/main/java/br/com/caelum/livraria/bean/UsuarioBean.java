package br.com.caelum.livraria.bean;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.caelum.livraria.dao.UsuarioDao;
import br.com.caelum.livraria.modelo.Usuario;
import br.com.caelum.livraria.tx.Transacional;

@Named
@ViewScoped //javax.faces.view.ViewScoped
public class UsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Usuario usuario = new Usuario();

	@Inject
	private UsuarioDao usuarioDao;

	public Usuario getUsuario() {
		return usuario;
	}

	@Transacional
	public String gravar() {
		System.out.println("Gravando User: " + this.usuario.getEmail());
		System.out.println("Gravando ID: " + this.usuario.getId());

		this.usuarioDao.adiciona(this.usuario);

		this.usuario = new Usuario();
		
		return "index?face-redirect=true";

	}
	
}
