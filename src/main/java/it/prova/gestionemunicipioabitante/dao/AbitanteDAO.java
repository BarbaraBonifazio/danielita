package it.prova.gestionemunicipioabitante.dao;

import java.util.List;

import it.prova.gestionemunicipioabitante.model.Abitante;
import it.prova.gestionemunicipioabitante.model.Municipio;

public interface AbitanteDAO extends IBaseDAO<Abitante> {

	public List<Abitante> findAllByMunicipio(Municipio input);

	public List<Abitante> findAllByUbicazioneMunicipioContiene(String ubicazioneToken);

}
