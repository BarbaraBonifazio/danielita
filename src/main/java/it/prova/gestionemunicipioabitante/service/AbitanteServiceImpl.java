package it.prova.gestionemunicipioabitante.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.gestionemunicipioabitante.dao.AbitanteDAO;
import it.prova.gestionemunicipioabitante.model.Abitante;
import it.prova.gestionemunicipioabitante.model.Municipio;

@Service
public class AbitanteServiceImpl implements AbitanteService {

	@Autowired
	private AbitanteDAO abitanteDAO;

	@Transactional(readOnly = true)
	public List<Abitante> listAllAbitanti() {
		return abitanteDAO.list();
	}

	@Transactional(readOnly = true)
	public Abitante caricaSingoloAbitante(Long id) {
		return abitanteDAO.get(id);
	}

	@Transactional
	public void aggiorna(Abitante abitanteInstance) {
		abitanteDAO.update(abitanteInstance);
	}

	@Transactional
	public void inserisciNuovo(Abitante abitanteInstance) {
		abitanteDAO.insert(abitanteInstance);
	}

	@Transactional
	public void rimuovi(Abitante abitanteInstance) {
		abitanteDAO.delete(abitanteInstance);
	}

	@Transactional(readOnly = true)
	public List<Abitante> findByExample(Abitante example) {
		return abitanteDAO.findByExample(example);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Abitante> cercaAbitantiInMunicipio(Municipio input) {
		return abitanteDAO.findAllByMunicipio(input);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Abitante> cercaAbitantiInMunicipioConUbicazioneContiene(
			String ubicazioneToken) {
		return abitanteDAO.findAllByUbicazioneMunicipioContiene(ubicazioneToken);
	}

}
