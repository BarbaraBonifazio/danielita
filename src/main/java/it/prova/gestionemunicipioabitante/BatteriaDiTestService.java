package it.prova.gestionemunicipioabitante;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.prova.gestionemunicipioabitante.model.Abitante;
import it.prova.gestionemunicipioabitante.model.Municipio;
import it.prova.gestionemunicipioabitante.service.AbitanteService;
import it.prova.gestionemunicipioabitante.service.MunicipioService;

@Component
public class BatteriaDiTestService {

	@Autowired
	private MunicipioService municipioService;

	@Autowired
	private AbitanteService abitanteService;

	// casi di test (usare valorizzando la variabile casoDaTestare nel main)
	public static final String INSERISCI_NUOVO_MUNICIPIO = "INSERISCI_NUOVO_MUNICIPIO";
	public static final String INSERISCI_ABITANTI_DATO_UN_MUNICIPIO = "INSERISCI_ABITANTI_DATO_UN_MUNICIPIO";
	public static final String CERCA_ABITANTE_DATO_ID_MUNICIPIO = "CERCA_ABITANTE_DATO_ID_MUNICIPIO";
	public static final String RIMUOVI_MUNICIPIO_E_ABITANTI = "RIMUOVI_MUNICIPIO_E_ABITANTI";
	public static final String ELENCA_TUTTI_I_MUNICIPI = "ELENCA_TUTTI_I_MUNICIPI";
	public static final String FIND_BY_EXAMPLE_BY_VIA = "FIND_BY_EXAMPLE_BY_VIA";
	public static final String UPDATE_ABITANTE_SET_ETA = "UPDATE_ABITANTE_SET_ETA";
	public static final String CARICA_MUNICIPIO_EAGER = "CARICA_MUNICIPIO_EAGER";
	public static final String REMOVE_CON_ECCEZIONE_VA_IN_ROLLBACK = "REMOVE_CON_ECCEZIONE_VA_IN_ROLLBACK";
	public static final String FIND_ALL_ABITANTE_UBICAZIONE_CONTIENE = "FIND_ALL_ABITANTE_UBICAZIONE_CONTIENE";
	public static final String COUNT_MUNICIPI_BY_MINORENNI = "COUNT_MUNICIPI_BY_MINORENNI";
	public static final String FIND_BY_EXAMPLE_BY_COGNOME = "FIND_BY_EXAMPLE_BY_COGNOME";
	public static final String FIND_BY_EXAMPLE_BY_COGNOME_AND_ETA = "FIND_BY_EXAMPLE_BY_COGNOME_AND_ETA";

	public void eseguiBatteriaDiTest(String casoDaTestare) {
		try {
			switch (casoDaTestare) {
			case INSERISCI_NUOVO_MUNICIPIO:
				// creo nuovo municipio
				Municipio nuovoMunicipio = new Municipio("Municipio III", "III", "Via dei Nani");
				// salvo
				municipioService.inserisciNuovo(nuovoMunicipio);
				System.out.println("Municipio appena inserito: " + nuovoMunicipio);
				break;

			case INSERISCI_ABITANTI_DATO_UN_MUNICIPIO:
				// / creo nuovo abitante
				Abitante nuovoAbitante = new Abitante("Pluto", "Plutorum", 77, "Via Lecce");
				nuovoAbitante.setMunicipio(municipioService.caricaSingoloMunicipio(1L));
				// salvo
				abitanteService.inserisciNuovo(nuovoAbitante);
				break;

			case CERCA_ABITANTE_DATO_ID_MUNICIPIO:
				// stampo gli abitanti di un determinato municipio
				System.out.println(
						abitanteService.cercaAbitantiInMunicipio(municipioService.caricaSingoloMunicipio(22L)));
				break;

			case RIMUOVI_MUNICIPIO_E_ABITANTI:
				// per cancellare tutto il municipio
				municipioService.rimuovi(municipioService.caricaSingoloMunicipio(24L));
				break;

			case ELENCA_TUTTI_I_MUNICIPI:
				// elencare i municipi
				System.out.println("Elenco i municipi:");
				for (Municipio municipioItem : municipioService.listAllMunicipi()) {
					System.out.println(municipioItem);
				}
				break;

			case FIND_BY_EXAMPLE_BY_VIA:
				System.out.println("########### EXAMPLE ########################");
				// find by example: voglio ricercare i municipi con
				// ubicazione'Via dei Grandi'
				Municipio municipioExample = new Municipio();
				municipioExample.setUbicazione("Via dei Nani");
				for (Municipio municipioItem : municipioService.findByExample(municipioExample)) {
					System.out.println(municipioItem);
				}
				break;

			case FIND_BY_EXAMPLE_BY_COGNOME:
				System.out.println("########### EXAMPLE ########################");
				// find by example: voglio ricercare le persone di cognome Rossi
				Abitante abitanteExample = new Abitante();
				abitanteExample.setCognome("Rossi");
				for (Abitante abitanteItem : abitanteService.findByExample(abitanteExample)) {
					System.out.println(abitanteItem);
				}
				break;
			case FIND_BY_EXAMPLE_BY_COGNOME_AND_ETA:
				System.out.println("########### EXAMPLE ########################");
				// find by example: voglio ricercare le persone di cognome Rossi
				Abitante abitanteExample2 = new Abitante();
				abitanteExample2.setCognome("Rossi");
				abitanteExample2.setEta(25);
				for (Abitante abitanteItem : abitanteService.findByExample(abitanteExample2)) {
					System.out.println(abitanteItem);
				}
				break;

			case UPDATE_ABITANTE_SET_ETA:
				// carico un abitante e cambio eta
				Abitante abitanteEsistente = abitanteService.caricaSingoloAbitante(14L);
				if (abitanteEsistente != null) {
					abitanteEsistente.setEta(50);
					abitanteService.aggiorna(abitanteEsistente);
				}
				break;

			case CARICA_MUNICIPIO_EAGER:
				// quando carico un Municipio ho già i suoi abitanti
				Municipio municipioACaso = municipioService.caricaSingoloMunicipioEagerAbitanti(23L);
				if (municipioACaso != null) {
					for (Abitante abitanteItem : municipioACaso.getAbitanti()) {
						System.out.println(abitanteItem);
					}
				}
				break;

			case REMOVE_CON_ECCEZIONE_VA_IN_ROLLBACK:
				// Test transaction rollback provando a cancellare l'ultimo
				// inserito
				List<Municipio> allMunicipi = municipioService.listAllMunicipi();
				System.out.println("...size before..." + allMunicipi.size());
				try {
					Municipio ultimoInserito = allMunicipi.get(allMunicipi.size() - 1);

					municipioService.removeConEccezione(ultimoInserito);
				} catch (Exception e) {
					e.printStackTrace();
				}

				allMunicipi = municipioService.listAllMunicipi();
				System.out.println("...size after..." + allMunicipi.size());
				break;

			case FIND_ALL_ABITANTE_UBICAZIONE_CONTIENE:
				System.out.println("########### FIND_ALL_ABITANTE_UBICAZIONE_CONTIENE ########################");

				for (Abitante abitanteItem : abitanteService.cercaAbitantiInMunicipioConUbicazioneContiene("cani")) {
					System.out.println(abitanteItem);
				}
				break;
			case COUNT_MUNICIPI_BY_MINORENNI:
				System.out.println("########### COUNT_MUNICIPI_BY_MINORENNI ########################");
				System.out
						.println("ci sono " + municipioService.countByAbitantiMinorenni() + " municipi con minorenni");
				break;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
