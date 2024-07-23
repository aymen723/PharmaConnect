package dz.pharmaconnect.pharmaconnectstockservice.model.api.dto.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dz.pharmaconnect.pharmaconnectstockservice.model.schema.entities.Bookmark;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Builder
@Data
public class BookmarkDto {


    private Long id;


    private Long accountId;


    private String name;


    private Instant createDate;


    @JsonIgnoreProperties({"tags"})
    private MedicalProductDto registeredProduct;

    @JsonIgnoreProperties({"tags"})
    private List<MedicalProductDto> products;

    public static BookmarkDto map(Bookmark bookmark) {
        if (bookmark == null) return null;
        return BookmarkDto.builder()
                .id(bookmark.getId())
                .accountId(bookmark.getAccountId())
                .name(bookmark.getName())
                .createDate(bookmark.getCreateDate())
                .registeredProduct(MedicalProductDto.map(bookmark.getRegisteredProduct(), false))
                .products(MedicalProductDto.mapAll(bookmark.getProducts(), false))
                .build();

    }

}
