package it.prova.gestionemunicipioabitante.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.gestionemunicipioabitante.dao.MunicipioDAO;
import it.prova.gestionemunicipioabitante.model.Municipio;

@Service
public class MunicipioServiceImpl implements MunicipioService {

	@Autowired
	private MunicipioDAO municipioDAO;

	public List<Municipio> listAllMunicipi() {
		return municipioDAO.list();
	}

	public Municipio caricaSingoloMunicipio(Long id) {
		return municipioDAO.get(id);
	}

	public Municipio caricaSingoloMunicipioEagerAbitanti(Long idMunicipio) {
		return municipioDAO.findEagerFetch(idMunicipio);
	}

	@Transactional
	public void aggiorna(Municipio municipioInstance) {
		municipioDAO.update(municipioInstance);
	}

	@Transactional
	public void inserisciNuovo(Municipio municipioInstance) {
		municipioDAO.insert(municipioInstance);
	}

	@Transactional
	public void rimuovi(Municipio municipioInstance) {
		municipioDAO.delete(municipioInstance);
	}

	public List<Municipio> findByExample(Municipio example) {
		return municipioDAO.findByExample(example);
	}

	@Transactional
	public void removeConEccezione(Municipio municipioInstance) {
		municipioDAO.delete(municipioInstance);
		throw new RuntimeException("Eccezione di prova transazione");
	}

	@Override
	public Long countByAbitantiMinorenni() {
		return municipioDAO.countByAbitantiMinorenni();
	}
}
