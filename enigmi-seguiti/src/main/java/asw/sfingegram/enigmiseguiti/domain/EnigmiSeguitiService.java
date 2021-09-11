package asw.sfingegram.enigmiseguiti.domain;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*; 
import java.util.stream.*; 

@Service 
public class EnigmiSeguitiService {

	@Autowired 
	private EnigmiRepository enigmiRepository;

	@Autowired
	private ConnessioniConAutoriRepository connessioniConAutoriRepository;

	@Autowired 
	private ConnessioniConTipiRepository connessioniConTipiRepository;

	/* Trova gli enigmi (in formato breve) degli utenti seguiti da utente. */ 
	public Collection<Enigma> getEnigmiSeguiti(String utente) {
		Collection<Enigma> enigmi = new TreeSet<>(); 		
		Collection<Enigma> enigmiDiAutoriSeguiti = getEnigmiDiAutoriSeguiti(utente);
		Collection<Enigma> enigmiDiTipiSeguiti = getEnigmiDiTipiSeguiti(utente); 
		enigmi.addAll(enigmiDiAutoriSeguiti); 
		enigmi.addAll(enigmiDiTipiSeguiti); 
		return enigmi; 
	}

	private Collection<Enigma> getEnigmiDiAutoriSeguiti(String utente) {
		Collection<Enigma> enigmi = new TreeSet<>(); 
		Collection<ConnessioneConAutore> connessioniConAutori = connessioniConAutoriRepository.findByUtente(utente);
		Collection<String> autoriSeguiti = 
			connessioniConAutori
				.stream()
				.map(c -> c.getAutore())
				.collect(Collectors.toSet()); 
		if (autoriSeguiti.size()>0) {
			Collection<Enigma> enigmiDiAutoriSeguiti = enigmiRepository.findByAutoreIn(autoriSeguiti);
			enigmi.addAll(enigmiDiAutoriSeguiti); 
		}
		return enigmi; 
	}

	private Collection<Enigma> getEnigmiDiTipiSeguiti(String utente) {
		Collection<Enigma> enigmi = new TreeSet<>(); 
		Collection<ConnessioneConTipo> connessioniConTipi = connessioniConTipiRepository.findByUtente(utente);
		Collection<String> tipiSeguiti = 
			connessioniConTipi
				.stream()
				.map(c -> c.getTipo())
				.collect(Collectors.toSet()); 
		if (tipiSeguiti.size()>0) {
			Collection<Enigma> enigmiDiTipiSeguiti = enigmiRepository.findByTipoIn(tipiSeguiti);
			enigmi.addAll(enigmiDiTipiSeguiti); 
		}
		return enigmi; 
	}
}
