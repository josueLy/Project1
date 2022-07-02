package project1.dto.client;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClientDto {

    private String dni;
    private String name;
    private String phoneNumber;
    private String emailAddress;
}
