package dz.pharmaconnect.pharmaconnectauth.model.api.client;

import lombok.Data;

@Data
public class Pharmacy {

    private Integer id;

    private String name;

    private boolean enabled;

    private Long accountId;

    private String phoneNumber;
    
    private String picture;
}
