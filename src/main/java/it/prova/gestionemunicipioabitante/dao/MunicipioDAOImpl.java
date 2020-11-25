package it.prova.gestionemunicipioabitante.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Component;

import it.prova.gestionemunicipioabitante.model.Municipio;

@Component
public class MunicipioDAOImpl implements MunicipioDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Municipio> list() {
		// dopo la from bisogna specificare il nome dell'oggetto (lettera
		// maiuscola) e non la tabella
		return entityManager.createQuery("from Municipio",Municipio.class).getResultList();
	}

	@Override
	public Municipio get(Long id) {
		return entityManager.find(Municipio.class, id);
	}

	@Override
	public Municipio findEagerFetch(long idMunicipio) {
		Query q = entityManager.createQuery("SELECT m FROM Municipio m JOIN FETCH m.abitanti a WHERE m.id = :id");
		q.setParameter("id", idMunicipio);
		return (Municipio) q.getSingleResult();
	}

	@Override
	public void update(Municipio municipioInstance) {
		municipioInstance = entityManager.merge(municipioInstance);
	}

	@Override
	public void insert(Municipio municipioInstance) {
		entityManager.persist(municipioInstance);
	}

	@Override
	public void delete(Municipio municipioInstance) {
		entityManager.remove(entityManager.merge(municipioInstance));
	}

	@Override
	public List<Municipio> findByExample(Municipio municipioInstance) {
		//prendere spunto da quello di abitantedao
		return null;
	}

	@Override
	public Long countByAbitantiMinorenni() {
		Query q = entityManager.createQuery("SELECT count(m) from Municipio m where m in ( select distinct m from Municipio m join m.abitanti a where a.eta < 18 ) ");
		Object result =  q.getSingleResult();
		return (Long)result;
	}
	
}
