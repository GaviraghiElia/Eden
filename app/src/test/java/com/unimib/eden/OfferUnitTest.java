package com.unimib.eden;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.unimib.eden.model.Offer;

import org.junit.Before;
import org.junit.Test;

import com.unimib.eden.utils.Enum;

public class OfferUnitTest {
    private QueryDocumentSnapshot document;
    private Offer offer;

    @Before
    public void setUp() {
        offer = new Offer("offerId", "Sandro", 150, Enum.OfferStatus.ACCEPTED);
    }

    @Test
    public void testConstructor() {
        assertNotNull(offer);
        assertEquals("offerId", offer.getId());
        assertEquals("Sandro", offer.getBuyer());
        assertEquals(150, offer.getPrice(), 0.01);
        assertEquals(Enum.OfferStatus.ACCEPTED, offer.getOfferStatus());
    }

    @Test
    public void testGetters() {
        assertEquals("offerId", offer.getId());
        assertEquals("Sandro", offer.getBuyer());
        assertEquals(150, offer.getPrice(), 0.01);
        assertEquals(Enum.OfferStatus.ACCEPTED, offer.getOfferStatus());
    }

    @Test
    public void testSetters() {
        offer.setId("offerId2");
        offer.setBuyer("Alice");
        offer.setPrice(200);
        offer.setOfferStatus(Enum.OfferStatus.REJECTED);

        assertEquals("offerId2", offer.getId());
        assertEquals("Alice", offer.getBuyer());
        assertEquals(200, offer.getPrice(), 0.01);
        assertEquals(Enum.OfferStatus.REJECTED, offer.getOfferStatus());
    }

    @Test
    public void testEqualsAndHashCode() {
        Offer sameOffer = new Offer("offerId", "Sandro", 150, Enum.OfferStatus.ACCEPTED);
        Offer differentOffer = new Offer("offerId2", "Alice", 200, Enum.OfferStatus.REJECTED);

        Offer newOffer = null;
        assertEquals(offer, sameOffer);
        assertNotEquals(offer, differentOffer);
        assertEquals(offer.hashCode(), sameOffer.hashCode());
        assertNotEquals(offer.hashCode(), differentOffer.hashCode());
        assertNotEquals(offer, newOffer);
    }

    @Test
    public void testToString() {
        String expectedString = "Offer{id='offerId', buyer='Sandro', price=150.0, offerStatus=ACCEPTED}";
        assertEquals(expectedString, offer.toString());
    }
}
