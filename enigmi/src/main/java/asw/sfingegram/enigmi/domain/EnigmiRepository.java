package asw.sfingegram.enigmi.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.*; 

public interface EnigmiRepository extends CrudRepository<Enigma, Long> {

	public Collection<Enigma> findAll();

	public Collection<Enigma> findByAutore(String autore);

	public Collection<Enigma> findByAutoreIn(Collection<String> autori);

	public Collection<Enigma> findByTipoStartingWith(String tipo);

}

