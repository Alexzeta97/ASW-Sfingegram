package asw.sfingegram.enigmiseguiti.domain;

import java.util.*;
import java.util.stream.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.logging.Logger;

@Service
public class ConnessioniService {

	@Autowired
	private ConnessioniConAutoriRepository connessioniConAutoriRepository;

    @Autowired
	private ConnessioniConTipiRepository connessioniConTipiRepository;

	@Autowired
	private EnigmiSeguitiService enigmiSeguitiService;

	private final Logger logger = Logger.getLogger(ConnessioniService.class.toString());
	
	
	// Crea una connessione utente-autore.
	public ConnessioneConAutore createConnessioneConAutore(Long id, String utente, String autore) {
		ConnessioneConAutore connessione = new ConnessioneConAutore(id, utente, autore);
		connessione = connessioniConAutoriRepository.save(connessione);
		logger.info("CREATED CONNESSIONE UTENTE-AUTORE: " + connessione);
		enigmiSeguitiService.updateAfterCreatedConnAutore(connessione.getUtente(), connessione.getAutore());
		return connessione;
	}

	// Crea una connessione utente-tipo.
	public ConnessioneConTipo createConnessioneConTipo(Long id, String utente, String tipo) {
		ConnessioneConTipo connessione = new ConnessioneConTipo(id, utente, tipo);
		connessione = connessioniConTipiRepository.save(connessione);
		logger.info("CREATED CONNESSIONE UTENTE-TIPO: " + connessione);
		enigmiSeguitiService.updateAfterCreatedConnTipo(connessione.getUtente(), connessione.getTipo());
		return connessione;
	}


	// Recupera la lista di utenti che seguono un certo autore.
	public Collection<String> getUtentiConnessiConAutore(String autore) {

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
	public Collection<String> getUtentiConnessiConTipo(String tipo) {

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
