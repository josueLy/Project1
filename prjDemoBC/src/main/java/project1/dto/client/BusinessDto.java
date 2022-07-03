package project1.dto.client;

import lombok.Data;
import lombok.NoArgsConstructor;
import project1.model.Client;

@Data
@NoArgsConstructor
public class BusinessDto{

    private String businessId;
    private String dni;
    private String name;
    private String phoneNumber;
    private String emailAddress;
    private String ruc;

}
