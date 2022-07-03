package project1.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Products {
    @Id
    private String productId;
    private String availableBalance;
    private String numberAccount;
}
