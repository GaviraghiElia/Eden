package com.unimib.eden.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.unimib.eden.database.ProductDao;
import com.unimib.eden.database.ProductRoomDatabase;
import com.unimib.eden.model.Prodotto;
import com.unimib.eden.utils.Constants;
import com.unimib.eden.utils.ServiceLocator;

import java.util.List;
import java.util.Map;

/**
 * Repository class for managing Product entities, providing data access operations and synchronization with Firestore.
 */
public class ProductRepository implements IProductRepository {
    private static final String TAG = "ProductRepository";
    private final ProductDao mProductDao;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final LiveData<List<Prodotto>> allProducts;

    /**
     * Constructs an instance of ProductRepository.
     *
     * @param application the application context.
     */
    public ProductRepository(Application application) {
        ProductRoomDatabase productRoomDatabase = ServiceLocator.getInstance().getProductDao(application);
        this.mProductDao = productRoomDatabase.productDao();
        allProducts = mProductDao.getAll();
    }

    /**
     * Retrieves all Product objects.
     *
     * @return a list of all Product objects.
     */
    @Override
    public LiveData<List<Prodotto>> getAllProducts() {
        return allProducts;
    }

    /**
     * Deletes a Product object.
     *
     * @param product the Product object to delete.
     */
    @Override
    public void deleteProduct(Prodotto product) {
        new DeleteProductAsyncTask(mProductDao).execute(product);
    }

    /**
     * Inserts a new Product object.
     *
     * @param product the new Product object to insert.
     */
    @Override
    public void insert(Prodotto product) {
        new InsertProductAsyncTask(mProductDao).execute(product);
    }

    /**
     * Adds a new product to Firestore and the local database.
     *
     * @param productMap map containing the data of the product to add.
     */
    public void addProduct(Map<String, Object> productMap) {
        db.collection(Constants.FIRESTORE_COLLECTION_PRODUCTS)
                .add(productMap)
                .addOnSuccessListener(documentReference -> {
                    String productId = documentReference.getId();
                    // Add the ID to the productMap
                    productMap.put(Constants.PRODUCT_ID, productId);
                    Prodotto product = new Prodotto(productMap);
                    insert(product);
                });
    }

    /**
     * Updates the local database with products from Firestore.
     * If the local database is empty, downloads products from Firestore.
     */
    public void updateLocalDB(String currentUserId) {
        db.collection(Constants.FIRESTORE_COLLECTION_PRODUCTS)
                .whereEqualTo(Constants.PRODUCT_SELLER, currentUserId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for(QueryDocumentSnapshot document: task.getResult()) {
                                List<Prodotto> tempProduct = allProducts.getValue();
                                boolean isProductPresent = false;
                                boolean isProductChanged = false;
                                Prodotto oldProduct = null;
                                Prodotto newProduct = null;
                                if (tempProduct != null) {
                                    for (Prodotto p : tempProduct) {
                                        if (p.getId().equals(document.getId())) {
                                            isProductPresent = true;
                                        }
                                        if (p.getId().equals(document.getId())) {
                                            oldProduct = p;
                                            isProductChanged = true;
                                        }

                                        boolean productFireBaseDBNotPresent = false;
                                        for (QueryDocumentSnapshot d : task.getResult()) {
                                            if (p.getId().equals(d.getId())) {
                                                productFireBaseDBNotPresent = true;
                                            }
                                        }
                                        if (!productFireBaseDBNotPresent) {
                                            deleteProduct(p);
                                        }
                                    }
                                    if (!isProductPresent) {
                                        newProduct = new Prodotto(document);
                                        insert(newProduct);
                                    }
                                    if (isProductPresent) {
                                        deleteProduct(oldProduct);
                                        newProduct = new Prodotto(document);
                                        insert(newProduct);

                                    }
                                } else { // empty local db
                                    newProduct = new Prodotto(document);
                                    insert(newProduct);
                                }
                            }
                        }
                    }
                });
    }

    // AsyncTask intern classes
    /**
     * AsyncTask subclass for deleting Product objects in the background.
     */
    private static class DeleteProductAsyncTask extends AsyncTask<Prodotto, Void, Void> {
        private final ProductDao productDao;

        /**
         * Constructs an instance of DeleteProductAsyncTask with the specified ProductDao.
         *
         * @param productDao the ProductDao to use for database operations.
         */
        private DeleteProductAsyncTask(ProductDao productDao) {
            this.productDao = productDao;
        }

        /**
         * Performs the deletion of Product objects in the background.
         *
         * @param products the Product objects to delete.
         * @return null.
         */
        @Override
        protected Void doInBackground(Prodotto... products) {
            productDao.delete(products[0]);
            return null;
        }
    }

    /**
     * AsyncTask subclass for inserting Product objects in the background.
     */
    private static class InsertProductAsyncTask extends AsyncTask<Prodotto, Void, Void> {
        private final ProductDao mProductsDao;

        /**
         * Constructs an instance of InsertProductAsyncTask with the specified ProductDao.
         *
         * @param productsDao the ProductDao to use for database operations.
         */
        private InsertProductAsyncTask(ProductDao productsDao) {
            this.mProductsDao = productsDao;
        }

        /**
         * Performs the insertion of Product objects in the background.
         *
         * @param products the Product objects to insert.
         * @return null.
         */
        @Override
        protected Void doInBackground(Prodotto... products) {
            mProductsDao.insert(products[0]);
            return null;
        }
    }
}
