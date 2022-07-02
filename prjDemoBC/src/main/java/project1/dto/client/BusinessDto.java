package project1.dto.client;

import lombok.Data;
import lombok.NoArgsConstructor;
import project1.model.Client;

@Data
@NoArgsConstructor
public class BusinessDto extends  ClientDto{

    private Integer idBusiness;
    private String ruc;

}
