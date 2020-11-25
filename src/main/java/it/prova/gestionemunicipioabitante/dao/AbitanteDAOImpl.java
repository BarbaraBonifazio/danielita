package it.prova.gestionemunicipioabitante.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import it.prova.gestionemunicipioabitante.model.Abitante;
import it.prova.gestionemunicipioabitante.model.Municipio;

@Component
public class AbitanteDAOImpl implements AbitanteDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Abitante> list() {
		return entityManager.createQuery("from Abitante",Abitante.class).getResultList();
	}

	@Override
	public Abitante get(Long id) {
		return entityManager.find(Abitante.class, id);
	}

	@Override
	public void update(Abitante abitanteInstance) {
		abitanteInstance = entityManager.merge(abitanteInstance);
	}

	@Override
	public void insert(Abitante abitanteInstance) {
		entityManager.persist(abitanteInstance);
	}

	@Override
	public void delete(Abitante abitanteInstance) {
		entityManager.remove(entityManager.merge(abitanteInstance));
	}

	@Override
	public List<Abitante> findByExample(Abitante abitanteInstance) {
		Session session = (Session) entityManager.getDelegate();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Abitante> criteria = builder.createQuery(Abitante.class);
		Root<Abitante> root = criteria.from(Abitante.class);
		criteria.select(root);
        
        List<Predicate> predicates = new ArrayList<Predicate>();
        if(StringUtils.isNotBlank(abitanteInstance.getCognome()) )
        	predicates.add(builder.like(root.get("cognome"), abitanteInstance.getCognome()));
        if(StringUtils.isNotBlank(abitanteInstance.getNome()) )
        	predicates.add(builder.like(root.get("nome"), abitanteInstance.getNome()));
        if(StringUtils.isNotBlank(abitanteInstance.getResidenza()) )
        	predicates.add(builder.like(root.get("residenza"), abitanteInstance.getResidenza()));
        if(abitanteInstance.getEta() != null && abitanteInstance.getEta() > 0)
        	predicates.add(builder.equal(root.get("eta"), abitanteInstance.getEta()));
        
        criteria.select(root).where(predicates.toArray(new Predicate[predicates.size()]));
        
        
        return session.createQuery(criteria).getResultList();
	}

	@Override
	public List<Abitante> findAllByMunicipio(Municipio input) {
		TypedQuery<Abitante> query = entityManager.createQuery(
				"select u FROM Abitante u JOIN FETCH u.municipio where u.municipio =:municipioInput", Abitante.class);
		return query.setParameter("municipioInput", input).getResultList();
	}

	@Override
	public List<Abitante> findAllByUbicazioneMunicipioContiene(String ubicazioneToken) {
		TypedQuery<Abitante> query = entityManager.createQuery(
				"select u FROM Abitante u JOIN FETCH u.municipio m where m.ubicazione like :ubicazioneInput",
				Abitante.class);
		return (List<Abitante>) query.setParameter("ubicazioneInput", '%' + ubicazioneToken + '%').getResultList();
	}

}
