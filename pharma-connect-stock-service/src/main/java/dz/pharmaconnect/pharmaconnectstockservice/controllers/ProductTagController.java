package dz.pharmaconnect.pharmaconnectstockservice.controllers;

import java.util.List;

import dz.pharmaconnect.pharmaconnectstockservice.model.api.dto.responses.ProductTagDto;
import dz.pharmaconnect.pharmaconnectstockservice.model.api.filters.TagSearchFilter;
import dz.pharmaconnect.pharmaconnectstockservice.services.database.ProductTagService;
import dz.pharmaconnect.pharmaconnectstockservice.utils.SearchParamsValidator;
import jakarta.annotation.security.PermitAll;
import org.hibernate.FetchNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import dz.pharmaconnect.pharmaconnectstockservice.model.schema.entities.ProductTag;
import lombok.RequiredArgsConstructor;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/product-tags")
@RequiredArgsConstructor
public class ProductTagController {

    private final ProductTagService productTagService;

    @GetMapping("/{tagId}")
    @PermitAll
    public ResponseEntity<ProductTagDto> fetchTagById(@PathVariable Integer tagId) {
        var tag = productTagService.fetch(tagId).orElseThrow(() -> new FetchNotFoundException("productTag", tagId));
        return ResponseEntity.ok(ProductTagDto.map(tag));
    }

    @GetMapping()
    @PermitAll
    public ResponseEntity<Page<ProductTagDto>> fetchTagsByFilter(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int pageSize,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) List<Integer> ids
    ) {
        var filter = TagSearchFilter.builder()
                .name(name)
                .tags(ids)
                .build();
        var pageable = SearchParamsValidator.paginate(page, pageSize);
        var tags = productTagService.getAllFiltered(pageable, filter);
        return ResponseEntity.ok(tags.map(ProductTagDto::map));
    }


}
