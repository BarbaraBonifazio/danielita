package it.prova.gestionemunicipioabitante.dao;

import it.prova.gestionemunicipioabitante.model.Municipio;

public interface MunicipioDAO extends IBaseDAO<Municipio> {
	public Municipio findEagerFetch(long idMunicipio);
	public Long countByAbitantiMinorenni();
}
