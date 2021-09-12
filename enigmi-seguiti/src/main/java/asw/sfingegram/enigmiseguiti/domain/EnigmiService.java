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
	private EnigmiSeguitiService enigmiSeguitiService;

	private final Logger logger = Logger.getLogger(EnigmiService.class.toString());


	// Crea un nuovo enigma.
	public Enigma createEnigma(Long id, String autore, String tipo, String titolo, String[] testo) {
		Enigma enigma = new Enigma(id, autore, tipo, titolo, testo);
		enigma = enigmiRepository.save(enigma);
		logger.info("CREATED ENIGMA: " + enigma);
		enigmiSeguitiService.updateEnigmiSeguiti(enigma);
		return enigma;
	}

	
	// Trova tutti gli enigmi scritti da un certo autore.
	public Collection<Enigma> findByAutore(String autore) {
		return enigmiRepository.findByAutore(autore);
	}

	// Trova tutti gli enigmi il cui tipo inizia con una certa stringa.
	public Collection<Enigma> findByTipoStartingWith(String tipo) {
		return enigmiRepository.findByTipoStartingWith(tipo);
	}
}
