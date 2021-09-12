package asw.sfingegram.enigmiseguiti.domain;

import javax.persistence.*; 
import lombok.*; 

// Entità per la ricerca rapida di tutti gli enigmi
// seguiti da un certo utente.
@Entity
@Table(name = "enigmiseguiti")
@Data @NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@IdClass(EnigmaSeguitoKey.class)
public class EnigmaSeguito {
    
    @Id
    @EqualsAndHashCode.Include
    private String utente;

    @Id
    @EqualsAndHashCode.Include
    private Long idEnigma;

    private String autoreEnigma;
    private String tipoEnigma;
    private String titoloEnigma;
    private String[] testoEnigma;
}
