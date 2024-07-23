package dz.pharmaconnect.pharmaconnectstockservice.controllers;

import dz.pharmaconnect.pharmaconnectstockservice.model.api.filters.BookmarkSearchFilter;
import dz.pharmaconnect.pharmaconnectstockservice.model.api.filters.ProductSearchFilter;
import dz.pharmaconnect.pharmaconnectstockservice.model.api.requests.CreateBookmarkRequest;
import dz.pharmaconnect.pharmaconnectstockservice.model.schema.entities.Bookmark;
import dz.pharmaconnect.pharmaconnectstockservice.services.database.BookMarkService;
import dz.pharmaconnect.pharmaconnectstockservice.services.database.MedicalProductService;
import dz.pharmaconnect.pharmaconnectstockservice.utils.SearchParamsValidator;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.ForbiddenException;
import lombok.RequiredArgsConstructor;
import org.hibernate.FetchNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("api/v1/bookmarks")
@RequiredArgsConstructor
public class BookmarkController {
    private final BookMarkService bookMarkService;
    private final MedicalProductService productService;

    @GetMapping("/all")
    public ResponseEntity<List<Bookmark>> getAll() {
        return ResponseEntity.ok(bookMarkService.getAll());
    }


    @GetMapping
    @PreAuthorize("hasAnyAuthority('CLIENT', 'ADMIN')")
    public ResponseEntity<Page<Bookmark>> getBookMarks(@RequestAttribute Long userAccountId,
                                                       @RequestParam(required = false) Long accountId,
                                                       @RequestParam(required = false) String name,
                                                       @RequestParam(required = false) Integer page,
                                                       @RequestParam(required = false) Integer pageSize,
                                                       @RequestParam(required = false) Integer registeredProd

    ) {

        var filter = BookmarkSearchFilter.builder().accountId(accountId).name(name).registeredProd(registeredProd).build();
        var authority = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().toList().get(0).getAuthority();
        if (authority.equals("CLIENT")) {
            filter.setAccountId(userAccountId);
        }

        System.out.println("bookmark filter : " + filter);
        var pageable = SearchParamsValidator.paginate(page, pageSize);

        var bookmarks = bookMarkService.getBookMarksByFilter(pageable, filter);

        return ResponseEntity.ok(bookmarks);
    }


    @PostMapping()
    @PreAuthorize("hasAuthority('CLIENT')")
    public ResponseEntity<Bookmark> createBookmark(@RequestBody CreateBookmarkRequest request, @RequestAttribute Long userAccountId) {

        var buildr = Bookmark.builder()
                .accountId(userAccountId)
                .products(List.of())
                .name(request.getName())
                .createDate(Instant.now());


        if (request.getRegisteredProd() != null) {
            var product = productService.get(request.getRegisteredProd()).orElseThrow(() -> new FetchNotFoundException("product", request.getRegisteredProd()));
            var prodBookmark = bookMarkService.getBookMarksByFilter(PageRequest.of(0, 10), BookmarkSearchFilter.builder().accountId(userAccountId).registeredProd(product.getId()).build());
            if (!prodBookmark.isEmpty()) return ResponseEntity.ok(prodBookmark.getContent().get(0));
            buildr.registeredProduct(product);
            buildr.name(product.getName());

        } else if (request.getProducts() != null && !request.getProducts().isEmpty()) {
            var products = productService.getByFilter(PageRequest.of(0, request.getProducts().size()), ProductSearchFilter.builder().products(request.getProducts()).build()).getContent();
            if (products.isEmpty()) throw new IllegalStateException();
            buildr.products(products);
        } else {
            throw new BadRequestException();
        }

        var bookmark = bookMarkService.save(buildr.build());

        return ResponseEntity.status(HttpStatus.CREATED).body(bookmark);


    }

    @DeleteMapping("/{bookmarkId}")
    @PreAuthorize("hasAnyAuthority('CLIENT')")
    public ResponseEntity<Void> deleteBookmark(@PathVariable Long bookmarkId, @RequestAttribute Long userAccountId) {
        var bookmark = bookMarkService.get(bookmarkId).orElseThrow(() -> new FetchNotFoundException("bookmark", bookmarkId));
        if (!bookmark.getAccountId().equals(userAccountId)) {
            throw new ForbiddenException();
        }
        bookMarkService.delete(bookmarkId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
