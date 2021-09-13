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
		enigmiSeguitiService.updateEnigmiSeguiti(connessione);
		return connessione;
	}

	// Crea una connessione utente-tipo.
	public ConnessioneConTipo createConnessioneConTipo(Long id, String utente, String tipo) {
		ConnessioneConTipo connessione = new ConnessioneConTipo(id, utente, tipo);
		connessione = connessioniConTipiRepository.save(connessione);
		logger.info("CREATED CONNESSIONE UTENTE-TIPO: " + connessione);
		enigmiSeguitiService.updateEnigmiSeguiti(connessione);
		return connessione;
	}

	// Recupera la lista di tutte le connessioni relative ad un certo autore.
	public Collection<ConnessioneConAutore> getConnessioniConAutore(String autore) {
		return connessioniConAutoriRepository.findByAutore(autore);
	}

	// Recupera la lista di tutte le connessioni relative ad un certo tipo di enigma.
	public Collection<ConnessioneConTipo> getConnessioniConTipo(String tipo) {
		return connessioniConTipiRepository.findByTipo(tipo);
	}
}
