package asw.sfingegram.enigmiseguiti.domain;

import javax.persistence.*; 
import lombok.*; 

/* Enigma (in formato breve, senza soluzione). */ 
@Entity
@Data @NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Enigma implements Comparable<Enigma> {

	@Id
	@EqualsAndHashCode.Include
	private Long id; 
	private String autore; 
	private String tipo; 
	private String titolo; 
	private String[] testo; 

	@Override
	public int compareTo(Enigma other) {
		return this.id.compareTo(other.id); 
	}
}
