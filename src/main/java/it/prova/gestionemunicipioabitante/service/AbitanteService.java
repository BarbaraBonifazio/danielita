package it.prova.gestionemunicipioabitante.service;


import java.util.List;

import it.prova.gestionemunicipioabitante.model.Abitante;
import it.prova.gestionemunicipioabitante.model.Municipio;

public interface AbitanteService {

	public List<Abitante> listAllAbitanti();

	public Abitante caricaSingoloAbitante(Long id);

	public void aggiorna(Abitante abitanteInstance);

	public void inserisciNuovo(Abitante abitanteInstance);

	public void rimuovi(Abitante abitanteInstance);

	public List<Abitante> findByExample(Abitante example);
	
	public List<Abitante> cercaAbitantiInMunicipio(Municipio input);
	
	public List<Abitante> cercaAbitantiInMunicipioConUbicazioneContiene(String ubicazioneToken);

}
