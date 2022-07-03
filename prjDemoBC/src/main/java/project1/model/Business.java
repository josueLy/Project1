package project1.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



@Document(collection = "business")
@Data
public class Business extends Client{
    @Id
    private String idBusiness;
    private String ruc;

    public Business(String idBusiness, String ruc) {
        this.idBusiness = idBusiness;

        this.ruc = ruc;
    }
}