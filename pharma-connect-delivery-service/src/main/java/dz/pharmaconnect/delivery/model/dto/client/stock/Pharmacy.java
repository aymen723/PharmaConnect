package dz.pharmaconnect.delivery.model.dto.client.stock;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Pharmacy {
    private Integer id;


    private String name;


    private boolean supportPayment = false;


    private Boolean enabled;


    private String picture;


    private String phoneNumber;


    private Long accountId;
}
