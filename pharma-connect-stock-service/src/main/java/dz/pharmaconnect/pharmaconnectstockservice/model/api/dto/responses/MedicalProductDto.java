package dz.pharmaconnect.pharmaconnectstockservice.model.api.dto.responses;

import dz.pharmaconnect.pharmaconnectstockservice.model.schema.entities.MedicalProduct;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Builder
@Data
public class MedicalProductDto {


    private Integer id;

    private String name;

    private String description;

    private Double price;

    private boolean prescriptionMedication = false;

    private String picture;

    private boolean para = false;


    private Set<ProductTagDto> tags;


    public static MedicalProductDto map(MedicalProduct product) {
        if (product == null) return null;

        return map(product, true);

    }

    public static MedicalProductDto map(MedicalProduct product, boolean mapTags) {
        if (product == null) return null;

        var prod = MedicalProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .para(product.isPara())
                .picture(product.getPicture())
                .prescriptionMedication(product.isPrescriptionMedication())

                .build();
        if (mapTags) {
            prod.setTags(ProductTagDto.map(product.getTags()));
        }
        return prod;

    }

    public static List<MedicalProductDto> mapAll(List<MedicalProduct> prods) {
        return MedicalProductDto.mapAll(prods, true);
    }

    public static List<MedicalProductDto> mapAll(List<MedicalProduct> prods, boolean mapTags) {
        if (prods == null) return null;

        return prods.stream().map(t -> MedicalProductDto.map(t, mapTags)).collect(Collectors.toList());
    }
}
