package com.unimib.eden;

import android.content.Context;

import com.unimib.eden.model.Product;

import androidx.room.Room;
import static org.junit.Assert.*;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.unimib.eden.database.ProductDao;
import com.unimib.eden.database.ProductRoomDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class ProductDaoTest {
    private static final String TAG = "ProductDaoTest";
    private ProductDao productDao;
    private ProductRoomDatabase productRoomDatabase;
    private final ArrayList<String> offers = new ArrayList<>();

    private final Product product1 = new Product(
            "6ofIp6Ca4utFGT67ysxz",
            "surplus",
            "s.erba9@campus.unimib.it",
            16.35,
            "tomato",
            offers,
            8,
            "mtqfP931yhNDQzFRN8RU",
            "should save it as surplus",
            true
    );
    private final Product product2 = new Product(
            "mqpnEeGfbHcQqyMXBRQP",
            "surplus",
            "s.erba9@campus.unimib.it",
            14.33,
            "garlic",
            offers,
            3,
            "QhhUcsLVFpDppyxen9tM",
            "April Fools'!",
            true
    );

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        productRoomDatabase = Room.inMemoryDatabaseBuilder(context, ProductRoomDatabase.class)
                .allowMainThreadQueries()
                .build();
        productDao = productRoomDatabase.productDao();
    }

    @After
    public void closeDb() {
        productRoomDatabase.close();
    }

    private void addOneProductToDb() {
        offers.add("IfjXQLn98rnUDu1rax8h");
        product1.setOffers(offers);
        productDao.insert(product1);
    }

    private void addTwoProductsToDb() {
        offers.add("IfjXQLn98rnUDu1rax8h");
        product1.setOffers(offers);
        product2.setOffers(offers);
        productDao.insert(product1);
        productDao.insert(product2);
    }

    @Test
    public void daoInsert_insertsProductIntoDb() {
        addOneProductToDb();
        List<Product> allProducts = productDao.getAllTest();
        assertEquals(allProducts.get(0), product1);
    }

    @Test
    public void daoGetAllProducts_returnAllProductsFromDb() {
        addTwoProductsToDb();
        List<Product> allProducts = productDao.getAllTest();
        assertEquals(allProducts.get(0), product1);
        assertEquals(allProducts.get(1), product2);
    }

    @Test
    public void daoDeleteProducts_deleteAllProductsFromDb() {
        addTwoProductsToDb();
        productDao.delete(product1);
        productDao.delete(product2);
        List<Product> allProducts = productDao.getAllTest();
        assertTrue(allProducts.isEmpty());
    }
}
