package br.com.caelum.livraria.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import br.com.caelum.livraria.modelo.Autor;
import br.com.caelum.livraria.modelo.Livro;

@SuppressWarnings("serial")
public class DAO<T> implements Serializable {

	private final Class<T> classe;
	private EntityManager em;

	public DAO(EntityManager manager, Class<T> classe) {
		this.em = manager;
		this.classe = classe;
	}

	public int quantidadeDeElementos() {
        long result = (Long) em.createQuery("select count(n) from " + classe.getSimpleName() + " n")
                .getSingleResult();

        return (int) result;
    }
	
	public void adiciona(T t) {
		em.persist(t);

	}

	public void remove(T t) {

		em.remove(em.merge(t));

	}

	public void atualiza(T t) {

		em.merge(t);

	}

	public List<T> listaTodos() {
		CriteriaQuery<T> query = em.getCriteriaBuilder().createQuery(classe);
		query.select(query.from(classe));

		List<T> lista = em.createQuery(query).getResultList();

		return lista;
	}
	
	public T buscaPorId(Integer id) {
		T instancia = em.find(classe, id);
		return instancia;
	}


	public int contaTodos() {
		long result = (Long) em.createQuery("select count(n) from livro n")
				.getSingleResult();

		return (int) result;
	}

	public Integer qtdLivrosPorAutor(Autor autor) {
		
		TypedQuery<Livro> query = em.createQuery(
				"select l from Livro l join fetch l.autores a where a.id = :pAutor", Livro.class);

		query.setParameter("pAutor", autor.getId());
		
		Integer resultado;
		
		try {
			resultado = query.getResultList().size();
		} catch(NoResultException ex) {
			return 0;
		}
		
		
		return resultado;
		
	}

	public List<T> listaTodosPaginada(int firstResult, int maxResults) {
		CriteriaQuery<T> query = em.getCriteriaBuilder().createQuery(classe);
		query.select(query.from(classe));

		List<T> lista = em.createQuery(query).setFirstResult(firstResult)
				.setMaxResults(maxResults).getResultList();

		return lista;
	}

}
