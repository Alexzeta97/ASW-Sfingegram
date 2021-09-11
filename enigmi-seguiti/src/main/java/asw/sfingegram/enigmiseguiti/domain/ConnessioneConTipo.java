package asw.sfingegram.enigmiseguiti.domain;

import javax.persistence.*; 
import lombok.*; 

@Entity 
@Data @NoArgsConstructor @AllArgsConstructor
public class ConnessioneConTipo {

	@Id 
	private Long id; 
	private String utente; 
	private String tipo; 	
}
