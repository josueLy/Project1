package project1.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "client")
@Data
public class Client {

    @Id
    private String idClient;
    private String dni;
    private String name;
    private String phoneNumber;
    private String emailAddress;

    public Client(String dni, String name, String phoneNumber, String emailAddress) {
        this.dni=dni;
        this.name=name;
        this.phoneNumber=phoneNumber;
        this.emailAddress=emailAddress;
    }
}
