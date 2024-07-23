package dz.pharmaconnect.pharmaconnectstockservice;

import dz.pharmaconnect.pharmaconnectstockservice.model.api.dto.responses.BookmarkDto;
import dz.pharmaconnect.pharmaconnectstockservice.model.api.dto.responses.LocationDto;
import dz.pharmaconnect.pharmaconnectstockservice.model.api.dto.responses.MedicalProductDto;
import dz.pharmaconnect.pharmaconnectstockservice.model.api.dto.responses.OrderDto;
import dz.pharmaconnect.pharmaconnectstockservice.model.schema.entities.*;
import dz.pharmaconnect.pharmaconnectstockservice.model.schema.enums.OrderStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class DtoTests {


    @Test
    public void locationMappingTest() {
        var coordinates = new Coordinate(60, 70);
        var pointFactory = new GeometryFactory();
        var location = Location.builder()
                .id(1)
                .point(pointFactory.createPoint(new Coordinate(70, 80)))
                .googleUrl("https://maps.google.com/?q=40.7128,-74.0060")
                .build();

        var locationDto = LocationDto.map(location);

        Assertions.assertNotNull(locationDto);
        Assertions.assertEquals(location.getId(), locationDto.getId());
        Assertions.assertEquals(location.getCoordinates(), locationDto.getCoordinates());
        Assertions.assertEquals(location.getGoogleUrl(), locationDto.getGoogleUrl());

    }

    @Test
    public void locationMappingNullTest() {
        var locationDto = LocationDto.map(null);

        Assertions.assertNull(locationDto);
    }


    @Test
    public void medicalProductMappingTest() {
        var tags = Set.of(
                ProductTag.builder().id(1).name("Tag1").build(),
                ProductTag.builder().id(2).name("Tag2").build()
        );

        var product = MedicalProduct.builder()
                .id(1)
                .name("Aspirin")
                .description("Pain reliever")
                .price(9.99)
                .prescriptionMedication(true)
                .picture("https://example.com/picture.jpg")
                .para(true)
                .tags(tags)
                .build();

        var productDto = MedicalProductDto.map(product);

        Assertions.assertNotNull(productDto);
        Assertions.assertEquals(product.getId(), productDto.getId());
        Assertions.assertEquals(product.getName(), productDto.getName());
        Assertions.assertEquals(product.getDescription(), productDto.getDescription());
        Assertions.assertEquals(product.getPrice(), productDto.getPrice());
        Assertions.assertEquals(product.isPara(), productDto.isPara());
        Assertions.assertEquals(product.getPicture(), productDto.getPicture());
        Assertions.assertEquals(product.isPrescriptionMedication(), productDto.isPrescriptionMedication());
        Assertions.assertNotNull(productDto.getTags());
        Assertions.assertEquals(product.getTags().size(), productDto.getTags().size());
    }

    @Test
    public void medicalProductMappingNullTest() {


        Assertions.assertNull(MedicalProductDto.mapAll(null));
    }

    @Test
    public void medicalProductMappingWithoutTagsTest() {
        var product = MedicalProduct.builder()
                .id(1)
                .name("Aspirin")
                .description("Pain reliever")
                .price(9.99)
                .prescriptionMedication(true)
                .picture("https://example.com/picture.jpg")
                .para(true)
                .build();

        var productDto = MedicalProductDto.map(product, false);

        Assertions.assertNotNull(productDto);
        Assertions.assertEquals(product.getId(), productDto.getId());
        Assertions.assertEquals(product.getName(), productDto.getName());
        Assertions.assertEquals(product.getDescription(), productDto.getDescription());
        Assertions.assertEquals(product.getPrice(), productDto.getPrice());
        Assertions.assertEquals(product.isPara(), productDto.isPara());
        Assertions.assertEquals(product.getPicture(), productDto.getPicture());
        Assertions.assertEquals(product.isPrescriptionMedication(), productDto.isPrescriptionMedication());
        Assertions.assertNull(productDto.getTags());
    }


    @Test
    public void bookmarkMappingTest() {
        var registeredProduct = MedicalProduct.builder()
                .id(1)
                .name("Aspirin")
                .description("Pain reliever")
                .price(9.99)
                .prescriptionMedication(true)
                .picture("https://example.com/picture.jpg")
                .para(true)
                .build();

        var product1 = MedicalProduct.builder()
                .id(2)
                .name("Ibuprofen")
                .description("Anti-inflammatory")
                .price(12.99)
                .prescriptionMedication(false)
                .picture("https://example.com/picture2.jpg")
                .para(false)
                .build();

        var product2 = MedicalProduct.builder()
                .id(3)
                .name("Paracetamol")
                .description("Pain reliever and fever reducer")
                .price(8.99)
                .prescriptionMedication(false)
                .picture("https://example.com/picture3.jpg")
                .para(false)
                .build();

        var bookmark = Bookmark.builder()
                .id(1L)
                .accountId(123L)
                .name("My Bookmark")
                .createDate(Instant.now())
                .registeredProduct(registeredProduct)
                .products(List.of(product1, product2))
                .build();

        var bookmarkDto = BookmarkDto.map(bookmark);

        Assertions.assertNotNull(bookmarkDto);
        Assertions.assertEquals(bookmark.getId(), bookmarkDto.getId());
        Assertions.assertEquals(bookmark.getAccountId(), bookmarkDto.getAccountId());
        Assertions.assertEquals(bookmark.getName(), bookmarkDto.getName());
        Assertions.assertEquals(bookmark.getCreateDate(), bookmarkDto.getCreateDate());

        Assertions.assertNotNull(bookmarkDto.getRegisteredProduct());
        Assertions.assertEquals(bookmark.getRegisteredProduct().getId(), bookmarkDto.getRegisteredProduct().getId());
        Assertions.assertEquals(bookmark.getRegisteredProduct().getName(), bookmarkDto.getRegisteredProduct().getName());
        Assertions.assertEquals(bookmark.getRegisteredProduct().getDescription(), bookmarkDto.getRegisteredProduct().getDescription());

        Assertions.assertNotNull(bookmarkDto.getProducts());
        Assertions.assertEquals(bookmark.getProducts().size(), bookmarkDto.getProducts().size());
        Assertions.assertEquals(bookmark.getProducts().get(0).getId(), bookmarkDto.getProducts().get(0).getId());
        Assertions.assertEquals(bookmark.getProducts().get(1).getId(), bookmarkDto.getProducts().get(1).getId());
    }

    @Test
    public void bookmarkMappingNullTest() {
        var bookmarkDto = BookmarkDto.map(null);

        Assertions.assertNull(bookmarkDto);
    }


    @Test
    public void orderMappingTest() {


        var purchase1 = Purchase.builder()
                .purchaseId(new Purchase.PurchaseId(2, 1L))
                .count(2)
                .productPrice(22.0)
                .build();

        var purchase2 = Purchase.builder()
                .purchaseId(new Purchase.PurchaseId(1, 1L))
                .count(2)
                .productPrice(22.0)
                .build();

        var order = Order.builder()
                .id(1L)
                .secret(UUID.randomUUID())
                .deliveryId(1001L)
                .paymentId(2002L)
                .accountId(3003L)
                .purchases(List.of(purchase1, purchase2))
                .checkoutPrice(35.0)
                .status(OrderStatus.INITIALIZING)
                .date(Instant.now())
                .price(25.0)
                .deliveryPrice(10.0)
                .build();

        var orderDto = OrderDto.map(order);

        Assertions.assertNotNull(orderDto);
        Assertions.assertEquals(order.getId(), orderDto.getId());
        Assertions.assertEquals(order.getSecret(), orderDto.getSecret());
        Assertions.assertEquals(order.getDeliveryId(), orderDto.getDeliveryId());
        Assertions.assertEquals(order.getPaymentId(), orderDto.getPaymentId());
        Assertions.assertEquals(order.getAccountId(), orderDto.getAccountId());

        Assertions.assertNotNull(orderDto.getPurchases());
        Assertions.assertEquals(order.getPurchases().size(), orderDto.getPurchases().size());
        Assertions.assertEquals(order.getCheckoutPrice(), orderDto.getCheckoutPrice());
        Assertions.assertEquals(order.getStatus(), orderDto.getStatus());
        Assertions.assertEquals(order.getDate(), orderDto.getDate());
        Assertions.assertEquals(order.getPrice(), orderDto.getPrice());
        Assertions.assertEquals(order.getDeliveryPrice(), orderDto.getDeliveryPrice());
    }

    @Test
    public void orderMappingNullTest() {
        var orderDto = OrderDto.map(null);

        Assertions.assertNull(orderDto);
    }
}
