package project1.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@ToString
@Getter
@Setter
@EqualsAndHashCode


public class Business{

    private Integer idBusiness;
    private Client idCliente;
    private String ruc;


}
