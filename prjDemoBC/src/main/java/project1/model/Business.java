package project1.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



@Document(collection = "business")
@Data
public class Business{
    @Id
    private String businessId;
    //private Client client;
    private String ruc;

    public Business(String businessId, Client client, String ruc) {
        this.businessId = businessId;
        //this.client = client;
        this.ruc = ruc;
    }
}
