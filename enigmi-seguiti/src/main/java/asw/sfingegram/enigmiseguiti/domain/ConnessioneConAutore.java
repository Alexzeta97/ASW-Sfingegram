package asw.sfingegram.enigmiseguiti.domain;

import javax.persistence.*; 
import lombok.*; 

@Entity 
@Data @NoArgsConstructor @AllArgsConstructor
public class ConnessioneConAutore {

	@Id 
	private Long id; 
	private String utente; 
	private String autore; 
}
