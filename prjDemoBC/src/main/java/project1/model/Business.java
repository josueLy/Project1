package project1.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



@Document(collection = "business")
@Data
public class Business{
    @Id
    private Integer idBusiness;
    //private Client client;
    private String ruc;

    public Business(Integer idBusiness, Client client, String ruc) {
        this.idBusiness = idBusiness;
        //this.client = client;
        this.ruc = ruc;
    }
}
