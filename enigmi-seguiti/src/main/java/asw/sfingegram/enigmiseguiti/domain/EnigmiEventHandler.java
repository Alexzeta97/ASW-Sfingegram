package asw.sfingegram.enigmiseguiti.domain;

import asw.sfingegram.common.api.event.DomainEvent;
import asw.sfingegram.enigmi.api.event.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.logging.Logger;

import java.util.*; 
import java.util.stream.*; 

@Service
public class EnigmiEventHandler {

	@Autowired
	private EnigmiRepository enigmiRepository;

	@Autowired
	private ConnessioniConAutoriRepository connessioniConAutoriRepository;

    @Autowired
	private ConnessioniConTipiRepository connessioniConTipiRepository;

	@Autowired
	private EnigmiSeguitiRepository enigmiSeguitiRepository;

	private final Logger logger = Logger.getLogger(EnigmiEventHandler.class.toString());

	public void onEvent(DomainEvent event) {
		
		// Enigma creato.
        if (event.getClass().equals(EnigmaCreatedEvent.class)) {
			EnigmaCreatedEvent ece = (EnigmaCreatedEvent) event;
			enigmaCreated(ece); 
		}
        
        else {
			logger.info("UNKNOWN EVENT: " + event);			
		}
	}
	
	private Enigma enigmaCreated(EnigmaCreatedEvent event) {
		Enigma enigma = new Enigma(
			event.getId(),
			event.getAutore(),
			event.getTipo(),
			event.getTitolo(),
			event.getTesto()
		);
		enigma = enigmiRepository.save(enigma);
		logger.info("CREATED ENIGMA: " + enigma);
		updateEnigmiSeguiti(enigma);
		return enigma;
	}


	// Aggiorna la collezione di entità EnigmaSeguito in seguito all'aggiunta
	// di un Enigma.
	private void updateEnigmiSeguiti(Enigma enigma) {

		// Aggiungi un'istanza di EnigmaSeguito alla collezione per ogni utente
		// interessato all'enigma.
		for (String utente : getUtentiInteressatiAdEnigma(enigma)) {

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
		}
	}


	// Recupera la lista di tutti gli utenti interessati ad un enigma.
	private Collection<String> getUtentiInteressatiAdEnigma(Enigma enigma) {

		// Recupera la lista di utenti connessi con l'autore dell'enigma.
		Collection<String> utentiConnessiConAutore = getUtentiConnessiConAutore(enigma.getAutore());

		// Recupera la lista di utenti connessi con il tipo dell'enigma.
		Collection<String> utentiConnessiConTipo = getUtentiConnessiConTipo(enigma.getTipo());

		// Costruisci la lista di tutti gli utenti interessati all'enigma.
		Collection<String> utenti = new TreeSet<>();
		utenti.addAll(utentiConnessiConAutore);
		utenti.addAll(utentiConnessiConTipo);

		return utenti;
	}


	// Recupera la lista di utenti che seguono un certo autore.
	private Collection<String> getUtentiConnessiConAutore(String autore) {

		// Recupera la lista di connessioni con l'autore.
		Collection<ConnessioneConAutore> connessioniConAutore = connessioniConAutoriRepository.findByAutore(autore);

		// Estrai l'insieme di utenti da quello delle connessioni con l'autore.
		Collection<String> utenti = 
			connessioniConAutore
				.stream()
				.map(c -> c.getUtente())
				.collect(Collectors.toSet());

		return utenti;
	}


	// Recupera la lista di utenti che seguono un certo tipo di enigmi.
	private Collection<String> getUtentiConnessiConTipo(String tipo) {

		// Recupera la lista di connessioni con il tipo.
		Collection<ConnessioneConTipo> connessioniConTipo = connessioniConTipiRepository.findByTipo(tipo);

		// Estrai l'insieme di utenti da quello delle connessioni con il tipo.
		Collection<String> utenti = 
			connessioniConTipo
				.stream()
				.map(c -> c.getUtente())
				.collect(Collectors.toSet());

		return utenti;
	}
}
