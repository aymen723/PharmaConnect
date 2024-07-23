package dz.pharmaconnect.pharmaconnectstockservice.services.database;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dz.pharmaconnect.pharmaconnectstockservice.clients.AuthClient;
import dz.pharmaconnect.pharmaconnectstockservice.model.data.PharmacyInserter;
import dz.pharmaconnect.pharmaconnectstockservice.model.data.ProductInserter;
import dz.pharmaconnect.pharmaconnectstockservice.model.data.TagInserter;
import dz.pharmaconnect.pharmaconnectstockservice.model.data.UpTimeInserterGroup;
import dz.pharmaconnect.pharmaconnectstockservice.model.api.dto.clients.auth.requests.CreatePharmacyAccountRequest;
import dz.pharmaconnect.pharmaconnectstockservice.model.schema.entities.*;
import dz.pharmaconnect.pharmaconnectstockservice.model.schema.enums.TagTypeName;
import dz.pharmaconnect.pharmaconnectstockservice.repositories.database.UpTimeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class DummyDataFactory {


    private final int PHARMACY_NUMBER = 10;
    private final int PRODUCT_NUMBER = 10;


    private final MedicalProductService medicalProductService;
    private final PharmacyService pharmacyService;
    private final StockService stockService;
    private final ProductTagService productTagService;
    private final TagTypeService tagTypeService;
    private final LocationService locationService;
    private final AuthClient authClient;
    private final UpTimeRepository upTimeRepository;

    private final DataSource dataSource;
    private final ObjectMapper objectMapper;

    private List<PharmacyInserter> parsePharmacies() throws IOException {
        // Read JSON file and map it to a list of MyObject
        ClassPathResource resource = new ClassPathResource("dummy-data/pharmacies.json");


        List<PharmacyInserter> pharmacies = objectMapper.readValue(resource.getFile(), new TypeReference<List<PharmacyInserter>>() {
        });

        return pharmacies;
    }

    private List<ProductInserter> parseProducts() throws IOException {
        // Read JSON file and map it to a list of MyObject
        ClassPathResource resource = new ClassPathResource("dummy-data/products.json");


        List<ProductInserter> prods = objectMapper.readValue(resource.getFile(), new TypeReference<List<ProductInserter>>() {
        });

        return prods;
    }

    private List<TagInserter> parseTags() throws IOException {
        // Read JSON file and map it to a list of MyObject
        ClassPathResource resource = new ClassPathResource("dummy-data/tags.json");


        List<TagInserter> tags = objectMapper.readValue(resource.getFile(), new TypeReference<List<TagInserter>>() {
        });

        return tags;
    }

    private List<UpTimeInserterGroup> parseUpTimes() throws IOException {
        // Read JSON file and map it to a list of MyObject
        ClassPathResource resource = new ClassPathResource("dummy-data/upTime.json");


        List<UpTimeInserterGroup> upTimes = objectMapper.readValue(resource.getFile(), new TypeReference<List<UpTimeInserterGroup>>() {
        });

        return upTimes;
    }


    public void createDummyData() {


        try {
            log.info("INSERTING DUMMY DATA");
            parseProducts();
            parseTags();

            var createdProducts = this.createProducts();
            var createdPharmacies = this.createPharmacies();


            this.createStock(createdPharmacies, createdProducts);
            log.info("Dummy Data Inserted");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


    private List<ProductTag> createTags() throws IOException {
        TagType typeTagType = tagTypeService.save(TagType.builder().name(TagTypeName.TYPE).build());
        TagType cosumptionTagType = tagTypeService.save(TagType.builder().name(TagTypeName.CONSUMPTION).build());
        TagType dosageTagType = tagTypeService.save(TagType.builder().name(TagTypeName.DOSAGE).build());
        List<TagType> tagTypeList = List.of(typeTagType, cosumptionTagType, dosageTagType);

        List<ProductTag> tags = parseTags().stream().map((ti) -> ti.getTag(tagTypeList)).collect(Collectors.toList());


        tags = productTagService.saveAll(tags);
        return tags;
    }

    private List<MedicalProduct> createProducts() throws IOException {
        var tags = createTags();
        var products = parseProducts().stream().map(prod -> prod.getProduct(tags)).collect(Collectors.toList());
        ;
        System.out.println("product that will be inserted :: " + products);


        products = medicalProductService.saveAll(products);
        return products;
    }

    private List<Pharmacy> createPharmacies() throws IOException {
        final var upTimes = parseUpTimes();
        System.out.println("uptimes : " + upTimes);
        List<Pharmacy> pharmacies = parsePharmacies().stream().map(ph -> {
            var pharma = ph.getPharmacy();
            if (ph.getUpTimeId() != null) {
                var phUpTimes = upTimes.stream().filter(uptime -> uptime.getId().equals(ph.getUpTimeId())).findFirst();

                phUpTimes.ifPresent(upTimeInserter -> pharma.setUpTimes(upTimeInserter.getValues().stream().map(uptime -> uptime.getUpTime()).collect(Collectors.toSet())));


            }
            return pharma;
        }).collect(Collectors.toList());

        for (var ph : pharmacies) {

            ph.setLocation(locationService.save(ph.getLocation()));
        }


        pharmacies = pharmacies.stream().map(ph -> {
            var uptimes = ph.getUpTimes();
            ph.setUpTimes(null);
            var pharmacyAccount = authClient.createPharmacyAccount(CreatePharmacyAccountRequest.builder()
                    .username(ph.getName())
                    .name(ph.getName())
                    .email(ph.getName().toLowerCase().replace(" ", "") + "@gmail.com")
                    .password("123456789")
                    .picture(ph.getPicture())

                    .build());
            ph.setAccountId(pharmacyAccount.getId());
            var pharma = pharmacyService.save(ph);
            if (uptimes != null) {
                uptimes = new HashSet<>(upTimeRepository.saveAll(uptimes.stream().map(uptime -> {
                    uptime.setPharmacy(pharma);
                    return uptime;
                }).toList()));


                return pharma;
            }

            return pharma;
        }).collect(Collectors.toList());

        return pharmacies;
    }


    private List<Stock> createStock(final List<Pharmacy> pharmacies, final List<MedicalProduct> products) {
        List<Stock> createdStock = new ArrayList<>();
        pharmacies.forEach(pharmacy -> {
            products.forEach(product -> {

                Stock stock = Stock.builder()
                        .id(new Stock.StockId(product.getId(), pharmacy.getId()))
                        .available(true)
                        .overridden(false)
                        .maxPurchaseCount(5)
                        .selling(true)
                        .price(product.getPrice())
                        .medicalproduct(product)
                        .pharmacy(pharmacy)
                        .overriddenAvailability(false)
                        .build();

                createdStock.add(stock);


            });
        });
        return stockService.saveAll(createdStock);
    }


}

