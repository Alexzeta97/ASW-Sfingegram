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

	private final Logger logger = Logger.getLogger(EnigmiSeguitiService.class.toString());


	/* Trova gli enigmi (in formato breve) degli utenti seguiti da utente. */ 
	public Collection<EnigmaSeguito> getEnigmiSeguiti(String utente) {

		return enigmiSeguitiRepository.findByUtente(utente);
	}


	// Crea un nuovo EnigmaSeguito.
	public EnigmaSeguito createEnigmaSeguito(
		String utente, Long idEnigma, String autoreEnigma,
		String tipoEnigma, String titoloEnigma, String[] testoEnigma
	) {
		EnigmaSeguito enigmaSeguito = new EnigmaSeguito(
			utente,
			idEnigma,
			autoreEnigma,
			tipoEnigma,
			titoloEnigma,
			testoEnigma
		);
		enigmiSeguitiRepository.save(enigmaSeguito);
		logger.info("CREATED ENIGMA SEGUITO: " + enigmaSeguito);
		return enigmaSeguito;
	}


	// Aggiorna la collezione di entità EnigmaSeguito in seguito all'aggiunta
	// di un Enigma.
	public void updateAfterCreatedEnigma(Enigma enigma) {

		// Aggiungi un'istanza di EnigmaSeguito alla collezione per ogni utente
		// interessato all'enigma aggiunto.
		for (String utente : enigmiService.getUtentiInteressatiAdEnigma(enigma)) {
			createEnigmaSeguito(
				utente,
				enigma.getId(),
				enigma.getAutore(),
				enigma.getTipo(),
				enigma.getTitolo(),
				enigma.getTesto()
			);
		}
	}


	// Aggiorna la collezione di entità EnigmaSeguito in seguito all'aggiunta di
	// una nuova connessione utente-autore.
	public void updateAfterCreatedConnAutore(String utente, String autore) {
		addEnigmiSeguitiUtente(utente, enigmiService.findByAutore(autore));
	}


	// Aggiorna la collezione di entità EnigmaSeguito in seguito all'aggiunta di
	// una nuova connessione utente-tipo.
	public void updateAfterCreatedConnTipo(String utente, String tipo) {
		addEnigmiSeguitiUtente(utente, enigmiService.findByTipo(tipo));
	}


	// Aggiunge nella base di dati un'entità EnigmaSeguito per ogni enigma a cui
	// un certo utente è interessato.
	private void addEnigmiSeguitiUtente(String utente, Collection<Enigma> enigmi) {
		for (Enigma enigma : enigmi) {
			createEnigmaSeguito(
				utente,
				enigma.getId(),
				enigma.getAutore(),
				enigma.getTipo(),
				enigma.getTitolo(),
				enigma.getTesto()
			);
		}
	}
}
