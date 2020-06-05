package br.com.caelum.livraria.dao;

import java.io.Serializable;

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
	private EntityManager manager; //new EntityManager
	
	public boolean existe(Usuario usuario) {
		
		TypedQuery<Usuario> query = manager.createQuery(
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

}
