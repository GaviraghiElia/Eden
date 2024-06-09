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
public class ProdottoDaoTest {
    private static final String TAG = "ProdottoDaoTest";
    private ProductDao prodottoDao;
    private ProductRoomDatabase prodottoRoomDatabase;
    private ArrayList<String> offerte = new ArrayList<String>();

    private Product prodotto1 = new Product(
            "6ofIp6Ca4utFGT67ysxz",
            "eccedenza",
            "s.erba9@campus.unimib.it",
            16.35,
            "pomodoro",
            offerte,
            8,
            "mtqfP931yhNDQzFRN8RU",
            "dovrebbe salvarla come eccedenza",
            true
    );
    private Product prodotto2 = new Product(
            "mqpnEeGfbHcQqyMXBRQP",
            "eccedenza",
            "s.erba9@campus.unimib.it",
            14.33,
            "aglio",
            offerte,
            3,
            "QhhUcsLVFpDppyxen9tM",
            "pesce d'aprile!",
            true
    );

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        prodottoRoomDatabase = Room.inMemoryDatabaseBuilder(context, ProductRoomDatabase.class)
                .allowMainThreadQueries()
                .build();
        prodottoDao = prodottoRoomDatabase.productDao();
    }

    @After
    public void closeDb() {
        prodottoRoomDatabase.close();
    }

    private void addOneProdottoToDb() {
        offerte.add("IfjXQLn98rnUDu1rax8h");
        prodotto1.setOffers(offerte);
        prodottoDao.insert(prodotto1);
    }

    private void addTwoProdottiToDb() {
        offerte.add("IfjXQLn98rnUDu1rax8h");
        prodotto1.setOffers(offerte);
        prodotto2.setOffers(offerte);
        prodottoDao.insert(prodotto1);
        prodottoDao.insert(prodotto2);
    }

    @Test
    public void daoInsert_insertsProdottoIntoDb() {
        addOneProdottoToDb();
        List<Product> allProdotti = prodottoDao.getAllTest();
        assertEquals(allProdotti.get(0), prodotto1);
    }

    @Test
    public void daoGetAllProdotti_returnAllProdottiFromDb() {
        addTwoProdottiToDb();
        List<Product> allProdotti = prodottoDao.getAllTest();
        assertEquals(allProdotti.get(0), prodotto1);
        assertEquals(allProdotti.get(1), prodotto2);
    }

    @Test
    public void daoDeleteProdotti_deleteAllProdottiFromDb() {
        addTwoProdottiToDb();
        prodottoDao.delete(prodotto1);
        prodottoDao.delete(prodotto2);
        List<Product> allProdotti = prodottoDao.getAllTest();
        assertTrue(allProdotti.isEmpty());
    }
}