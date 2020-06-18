package br.com.caelum.livraria.dao;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.com.caelum.livraria.modelo.Usuario;

@Named
@ViewScoped
public class UsuarioDao implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager em;
	
	private DAO<Usuario> dao;
	
	@PostConstruct
	void init() {
		this.dao = new DAO<Usuario>(this.em, Usuario.class);
	}
	
	public boolean existe(Usuario usuario) {
		
		TypedQuery<Usuario> query = em.createQuery(
				"select u from Usuario u"
		   	  + " where u.email = :pEmail and u.senha = :pSenha", Usuario.class);
				
		query.setParameter("pEmail", usuario.getEmail());
		query.setParameter("pSenha", usuario.getSenha());
		try {
			query.getSingleResult();
		} catch(NoResultException ex) {
			return false;
		}
		
		return true;
	}
	
	public void adiciona(Usuario usuario) {
		this.dao.adiciona(usuario);
	}

}
