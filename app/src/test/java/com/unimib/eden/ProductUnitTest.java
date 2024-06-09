package com.unimib.eden;

import static com.unimib.eden.utils.Constants.PRODUCT_OTHER_INFORMATION;
import static com.unimib.eden.utils.Constants.PRODUCT_CURRENT_PHASE;
import static com.unimib.eden.utils.Constants.PRODUCT_ID;
import static com.unimib.eden.utils.Constants.PRODUCT_OFFERS;
import static com.unimib.eden.utils.Constants.PRODUCT_PLANT;
import static com.unimib.eden.utils.Constants.PRODUCT_PRICE;
import static com.unimib.eden.utils.Constants.PRODUCT_QUANTITY;
import static com.unimib.eden.utils.Constants.PRODUCT_EXCHANGE_AVAILABLE;
import static com.unimib.eden.utils.Constants.PRODUCT_TYPE;
import static com.unimib.eden.utils.Constants.PRODUCT_SELLER;
import com.unimib.eden.model.Product;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class ProductUnitTest {

    private Product product;

    @Before
    public void setUp() {
        ArrayList<String> offers = new ArrayList<>();
        offers.add("offer1");
        offers.add("offer2");

        product = new Product(
                "1",
                "Fruit",
                "Seller1",
                10.0,
                "Apple",
                offers,
                5,
                "Phase1",
                "Other information",
                true
        );
    }

    @Test
    public void testConstructor() {
        assertNotNull(product);
        assertEquals("1", product.getId());
        assertEquals("Fruit", product.getType());
        assertEquals("Seller1", product.getSeller());
        assertEquals(10.0, product.getPrice(), 0.01);
        assertEquals("Apple", product.getPlant());
        assertEquals(2, product.getOffers().size());
        assertEquals("offer1", product.getOffers().get(0));
        assertEquals(5, product.getQuantity());
        assertEquals("Phase1", product.getCurrentPhase());
        assertEquals("Other information", product.getOtherInformation());
        assertTrue(product.getExchangeAvailable());
    }

    @Test
    public void testGetters() {
        assertEquals("1", product.getId());
        assertEquals("Fruit", product.getType());
        assertEquals("Seller1", product.getSeller());
        assertEquals(10.0, product.getPrice(), 0.01);
        assertEquals("Apple", product.getPlant());
        assertEquals(2, product.getOffers().size());
        assertEquals("offer1", product.getOffers().get(0));
        assertEquals(5, product.getQuantity());
        assertEquals("Phase1", product.getCurrentPhase());
        assertEquals("Other information", product.getOtherInformation());
        assertTrue(product.getExchangeAvailable());
    }

    @Test
    public void testSetters() {
        ArrayList<String> newOffers = new ArrayList<>();
        newOffers.add("offer3");

        product.setId("2");
        product.setType("Vegetable");
        product.setSeller("Seller2");
        product.setPrice(15.0);
        product.setPlant("Carrot");
        product.setOffers(newOffers);
        product.setQuantity(10);
        product.setCurrentPhase("Phase2");
        product.setOtherInformation("New information");
        product.setExchangeAvailable(false);

        assertEquals("2", product.getId());
        assertEquals("Vegetable", product.getType());
        assertEquals("Seller2", product.getSeller());
        assertEquals(15.0, product.getPrice(), 0.01);
        assertEquals("Carrot", product.getPlant());
        assertEquals(1, product.getOffers().size());
        assertEquals("offer3", product.getOffers().get(0));
        assertEquals(10, product.getQuantity());
        assertEquals("Phase2", product.getCurrentPhase());
        assertEquals("New information", product.getOtherInformation());
        assertFalse(product.getExchangeAvailable());
    }

    @Test
    public void testEqualsAndHashCode() {
        ArrayList<String> offers1 = new ArrayList<>();
        offers1.add("offer1");
        offers1.add("offer2");

        Product product1 = new Product(
                "1",
                "Fruit",
                "Seller1",
                10.0,
                "Apple",
                offers1,
                5,
                "Phase1",
                "Other information",
                true
        );

        ArrayList<String> offers2 = new ArrayList<>();
        offers2.add("offer1");
        offers2.add("offer2");

        Product product2 = new Product(
                "1",
                "Fruit",
                "Seller1",
                10.0,
                "Apple",
                offers2,
                5,
                "Phase1",
                "Other information",
                true
        );

        assertEquals(product1, product2);
        assertEquals(product1.hashCode(), product2.hashCode());

        product2.setId("2");
        assertNotEquals(product1, product2);
        assertNotEquals(product1.hashCode(), product2.hashCode());
    }

    @Test
    public void testToString() {
        String expected = "Product{id='1', type='Fruit', seller='Seller1', price=10.0, " +
                "plant='Apple', offers=[offer1, offer2], quantity=5, currentPhase='Phase1', " +
                "otherInformation='Other information', exchangeAvailable=true}";
        assertEquals(expected, product.toString());
    }

    @Test
    public void testInitFromMap() {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put(PRODUCT_TYPE, "Vegetable");
        dataMap.put(PRODUCT_PRICE, 15.0);
        dataMap.put(PRODUCT_PLANT, "Carrot");
        dataMap.put(PRODUCT_QUANTITY, 10);
        dataMap.put(PRODUCT_CURRENT_PHASE, "Phase2");
        dataMap.put(PRODUCT_OTHER_INFORMATION, "New information");
        dataMap.put(PRODUCT_SELLER, "Seller2");
        dataMap.put(PRODUCT_OFFERS, new ArrayList<>());
        dataMap.put(PRODUCT_EXCHANGE_AVAILABLE, false);

        product.initFromMap(dataMap);

        assertEquals("1", product.getId());
        assertEquals("Vegetable", product.getType());
        assertEquals("Seller2", product.getSeller());
        assertEquals(15.0, product.getPrice(), 0.01);
        assertEquals("Carrot", product.getPlant());
        assertEquals(0, product.getOffers().size());
        assertEquals(10, product.getQuantity());
        assertEquals("Phase2", product.getCurrentPhase());
        assertEquals("New information", product.getOtherInformation());
        assertFalse(product.getExchangeAvailable());
    }

    @Test
    public void testProductConstructorFromMap() {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put(PRODUCT_ID, "2");
        dataMap.put(PRODUCT_TYPE, "Vegetable");
        dataMap.put(PRODUCT_PRICE, 15.0);
        dataMap.put(PRODUCT_PLANT, "Carrot");
        dataMap.put(PRODUCT_QUANTITY, 10);
        dataMap.put(PRODUCT_CURRENT_PHASE, "Phase2");
        dataMap.put(PRODUCT_OTHER_INFORMATION, "New information");
        dataMap.put(PRODUCT_SELLER, "Seller2");
        dataMap.put(PRODUCT_OFFERS, new ArrayList<>());
        dataMap.put(PRODUCT_EXCHANGE_AVAILABLE, false);

        Product productFromMap = new Product(dataMap);

        assertEquals("2", productFromMap.getId());
        assertEquals("Vegetable", productFromMap.getType());
        assertEquals("Seller2", productFromMap.getSeller());
        assertEquals(15.0, productFromMap.getPrice(), 0.01);
        assertEquals("Carrot", productFromMap.getPlant());
        assertEquals(0, productFromMap.getOffers().size());
        assertEquals(10, productFromMap.getQuantity());
        assertEquals("Phase2", productFromMap.getCurrentPhase());
        assertEquals("New information", productFromMap.getOtherInformation());
        assertFalse(productFromMap.getExchangeAvailable());
    }
}
