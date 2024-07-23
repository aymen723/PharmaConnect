//package dz.pharmaconnect.pharmaconnectstockservice.model.schema.entities;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.io.Serializable;
//
//@Table(name = "tag_associations")
//@Entity
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
//public class TagAssociation {
//
//    @EmbeddedId
//    private TagAssociationId id;
//
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @MapsId("tag_id")
//    @JoinColumn(name = "tag_id", referencedColumnName = "tag_id")
//    private ProductTag pharmacy;
//
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @MapsId("product_id")
//    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
//    private MedicalProduct product;
//
//    @Embeddable
//    @Data
//    @AllArgsConstructor
//    @NoArgsConstructor
//    public static class TagAssociationId implements Serializable {
//        @Column(name = "product_id")
//        private Integer productId;
//        @Column(name = "tag_id")
//        private Integer tagId;
//    }
//}
