package dz.pharmaconnect.pharmaconnectstockservice.controllers;

import dz.pharmaconnect.pharmaconnectstockservice.model.api.filters.ProductSearchFilter;
import dz.pharmaconnect.pharmaconnectstockservice.model.schema.entities.ProductTag;
import dz.pharmaconnect.pharmaconnectstockservice.services.database.MedicalProductService;
import dz.pharmaconnect.pharmaconnectstockservice.services.database.ProductTagService;
import dz.pharmaconnect.pharmaconnectstockservice.utils.SearchParamsValidator;
import lombok.extern.log4j.Log4j2;
import org.hibernate.FetchNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import dz.pharmaconnect.pharmaconnectstockservice.model.schema.entities.MedicalProduct;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/medical-products")
@RequiredArgsConstructor
@Log4j2
public class MedicalProductController {

    private final MedicalProductService medicalProductService;
    private final ProductTagService productTagService;

    @GetMapping("/{productId}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<MedicalProduct> fetchProductById(@PathVariable Integer productId) {
        var product = medicalProductService.fetch(productId).orElseThrow(() -> new FetchNotFoundException("product", productId));

        return ResponseEntity.ok(product);
    }


    @GetMapping()
    @PreAuthorize("permitAll()")
    public ResponseEntity<Page<MedicalProduct>> fetchProducts(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int pageSize,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Set<Integer> tags,
            @RequestParam(required = false) Set<Integer> ids
    ) {

        var pageable = SearchParamsValidator.paginate(page, pageSize);
        var filter = ProductSearchFilter.builder().products(ids).search(search).tags(tags).build();
        var product = medicalProductService.getByFilter(pageable, filter);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(product);
    }


    @GetMapping("/{productId}/tags")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Set<ProductTag>> fetchProductTags(
            @PathVariable Integer productId
    ) {
        var tags = medicalProductService.fetch(productId).orElseThrow(() -> new FetchNotFoundException("product", productId)).getTags();

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(tags);
    }

}
