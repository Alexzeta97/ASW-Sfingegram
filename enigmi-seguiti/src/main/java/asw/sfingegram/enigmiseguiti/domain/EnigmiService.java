package asw.sfingegram.enigmiseguiti.domain;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service; 
import java.util.logging.Logger;

@Service
public class EnigmiService {

	@Autowired
	private EnigmiRepository enigmiRepository;

	@Autowired
	private ConnessioniService connessioniService;

	@Autowired
	private EnigmiSeguitiService enigmiSeguitiService;

	private final Logger logger = Logger.getLogger(EnigmiService.class.toString());


	// Crea un nuovo enigma.
	public Enigma createEnigma(Long id, String autore, String tipo, String titolo, String[] testo) {
		Enigma enigma = new Enigma(id, autore, tipo, titolo, testo);
		enigma = enigmiRepository.save(enigma);
		logger.info("CREATED ENIGMA: " + enigma);
		updateEnigmiSeguiti(enigma);
		return enigma;
	}

	
	// Trova tutti gli enigmi scritti da un certo autore.
	public Collection<Enigma> findByAutore(String autore) {
		return enigmiRepository.findByAutore(autore);
	}

	// Trova tutti gli enigmi di un certo tipo.
	public Collection<Enigma> findByTipo(String tipo) {
		return enigmiRepository.findByTipo(tipo);
	}


	// Aggiorna la collezione di entità EnigmaSeguito in seguito all'aggiunta
	// di un Enigma.
	private void updateEnigmiSeguiti(Enigma enigma) {

		// Aggiungi un'istanza di EnigmaSeguito alla collezione per ogni utente
		// interessato all'enigma.
		for (String utente : getUtentiInteressatiAdEnigma(enigma)) {
			enigmiSeguitiService.createEnigmaSeguito(
				utente,
				enigma.getId(),
				enigma.getAutore(),
				enigma.getTipo(),
				enigma.getTitolo(),
				enigma.getTesto()
			);
		}
	}


	// Recupera la lista di tutti gli utenti interessati ad un enigma.
	private Collection<String> getUtentiInteressatiAdEnigma(Enigma enigma) {

		// Recupera la lista di utenti connessi con l'autore dell'enigma.
		Collection<String> utentiConnessiConAutore = connessioniService.getUtentiConnessiConAutore(enigma.getAutore());

		// Recupera la lista di utenti connessi con il tipo dell'enigma.
		Collection<String> utentiConnessiConTipo = connessioniService.getUtentiConnessiConTipo(enigma.getTipo());

		// Costruisci la lista di tutti gli utenti interessati all'enigma.
		Collection<String> utenti = new TreeSet<>();
		utenti.addAll(utentiConnessiConAutore);
		utenti.addAll(utentiConnessiConTipo);

		return utenti;
	}
}
