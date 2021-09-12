package asw.sfingegram.enigmiseguiti.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.*; 

public interface ConnessioniConAutoriRepository extends CrudRepository<ConnessioneConAutore, Long> {
	
	public Collection<ConnessioneConAutore> findByAutore(String autore);
}
