package com.unimib.eden;

import android.content.Context;
import android.util.Log;

import com.unimib.eden.model.Prodotto;

import androidx.room.Room;
import static org.junit.Assert.*;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.unimib.eden.database.ProdottoDao;
import com.unimib.eden.database.ProdottoRoomDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class ProdottoDaoTest {
    private static final String TAG = "ProdottoDaoTest";
    private ProdottoDao prodottoDao;
    private ProdottoRoomDatabase prodottoRoomDatabase;
    private ArrayList<String> offerte = new ArrayList<String>();

    private Prodotto prodotto1 = new Prodotto(
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
    private Prodotto prodotto2 = new Prodotto(
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
        prodottoRoomDatabase = Room.inMemoryDatabaseBuilder(context, ProdottoRoomDatabase.class)
                .allowMainThreadQueries()
                .build();
        prodottoDao = prodottoRoomDatabase.prodottoDao();
    }

    @After
    public void closeDb() {
        prodottoRoomDatabase.close();
    }

    private void addOneProdottoToDb() {
        offerte.add("IfjXQLn98rnUDu1rax8h");
        prodotto1.setOfferte(offerte);
        prodottoDao.insert(prodotto1);
    }

    private void addTwoProdottiToDb() {
        offerte.add("IfjXQLn98rnUDu1rax8h");
        prodotto1.setOfferte(offerte);
        prodotto2.setOfferte(offerte);
        prodottoDao.insert(prodotto1);
        prodottoDao.insert(prodotto2);
    }

    @Test
    public void daoInsert_insertsProdottoIntoDb() {
        addOneProdottoToDb();
        List<Prodotto> allProdotti = prodottoDao.getAll();
        assertEquals(allProdotti.get(0), prodotto1);
    }

    @Test
    public void daoGetAllProdotti_returnAllProdottiFromDb() {
        addTwoProdottiToDb();
        List<Prodotto> allProdotti = prodottoDao.getAll();
        assertEquals(allProdotti.get(0), prodotto1);
        assertEquals(allProdotti.get(1), prodotto2);
    }

    @Test
    public void daoDeleteProdotti_deleteAllProdottiFromDb() {
        addTwoProdottiToDb();
        prodottoDao.delete(prodotto1);
        prodottoDao.delete(prodotto2);
        List<Prodotto> allProdotti = prodottoDao.getAll();
        assertTrue(allProdotti.isEmpty());
    }
}