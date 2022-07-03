package project1.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Products {
    @Id
    private String idProducts;
    private String availableBalance;
    private String numberAccount;
}
