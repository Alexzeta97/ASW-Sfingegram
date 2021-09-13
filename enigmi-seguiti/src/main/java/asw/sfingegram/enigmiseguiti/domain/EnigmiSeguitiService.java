package asw.sfingegram.enigmiseguiti.domain;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.logging.Logger;

import java.util.*; 

@Service 
public class EnigmiSeguitiService {

	@Autowired 
	private EnigmiSeguitiRepository enigmiSeguitiRepository;

	@Autowired
	private EnigmiService enigmiService;

	@Autowired
	private ConnessioniService connessioniService;

	private final Logger logger = Logger.getLogger(EnigmiSeguitiService.class.toString());


	/* Trova gli enigmi (in formato breve) degli utenti seguiti da utente. */ 
	public Collection<EnigmaSeguito> getEnigmiSeguiti(String utente) {

		return enigmiSeguitiRepository.findByUtente(utente);
	}


	// Aggiorna la collezione di entità EnigmaSeguito in seguito all'aggiunta
	// di un Enigma.
	public void updateEnigmiSeguiti(Enigma enigma) {

		// Aggiungi un'istanza di EnigmaSeguito alla collezione per ogni utente
		// connesso all'autore dell'enigma.
		for (ConnessioneConAutore connAutore: connessioniService.getConnessioniConAutore(enigma.getAutore())) {
			createEnigmaSeguito(connAutore.getUtente(), enigma);
		}

		// Aggiungi un'istanza di EnigmaSeguito alla collezione per ogni utente
		// connesso al tipo dell'enigma.
		for (ConnessioneConTipo connTipo: connessioniService.getConnessioniConTipo(enigma.getTipo())) {
			createEnigmaSeguito(connTipo.getUtente(), enigma);
		}
	}


	// Aggiorna la collezione di entità EnigmaSeguito in seguito all'aggiunta di
	// una nuova connessione utente-autore.
	public void updateEnigmiSeguiti(ConnessioneConAutore connAutore) {
		createEnigmiSeguiti(connAutore.getUtente(), enigmiService.findByAutore(connAutore.getAutore()));
	}


	// Aggiorna la collezione di entità EnigmaSeguito in seguito all'aggiunta di
	// una nuova connessione utente-tipo.
	public void updateEnigmiSeguiti(ConnessioneConTipo connTipo) {
		createEnigmiSeguiti(connTipo.getUtente(), enigmiService.findByTipoStartingWith(connTipo.getTipo()));
	}


	// Crea un nuovo EnigmaSeguito.
	private EnigmaSeguito createEnigmaSeguito(String utente, Enigma enigma) {
		EnigmaSeguito enigmaSeguito = new EnigmaSeguito(
			utente,
			enigma.getId(),
			enigma.getAutore(),
			enigma.getTipo(),
			enigma.getTitolo(),
			enigma.getTesto()
		);
		enigmiSeguitiRepository.save(enigmaSeguito);
		logger.info("CREATED ENIGMA SEGUITO: " + enigmaSeguito);
		return enigmaSeguito;
	}

	// Crea nella base di dati più entità EnigmaSeguito, tutte relative ad uno stesso utente.
	private void createEnigmiSeguiti(String utente, Collection<Enigma> enigmi) {
		for (Enigma enigma : enigmi) {
			createEnigmaSeguito(utente, enigma);
		}
	}
}
