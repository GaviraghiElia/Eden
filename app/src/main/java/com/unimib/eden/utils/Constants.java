package com.unimib.eden.utils;

/**
 * Constants class containing all the constants used within the application.
 */
public class Constants {

    public static final String FIRESTORE_COLLECTION_PLANTS = "piante";
    public static final String PLANT_NAME = "nome";
    public static final String PLANT_DESCRIPTION = "descrizione";
    public static final String PLANT_BOTANICAL_FAMILY = "famiglia_botanica";
    public static final String PLANT_PHASE = "fasi";
    public static final String PLANT_SOWING_START = "inizio_semina";
    public static final String PLANT_SOWING_END = "fine_semina";
    public static final String PLANT_REQUIRED_SPACE = "spazio_necessario";
    public static final String PLANT_MAX_EXPECTED_HEIGHT = "altezza_max_prevista";
    public static final String PLANT_SOIL_TYPE = "tipo_terreno";
    public static final String PLANT_MIN_TEMPERATURE = "min_temperatura";
    public static final String PLANT_MAX_TEMPERATURE = "max_temperatura";
    public static final String PLANT_SUN_EXPOSURE = "esposizione_sole";



    public static final String FIRESTORE_COLLECTION_CROPS = "colture";
    public static final String CROPS_ID = "id";
    public static final String CROPS_PLANT = "pianta";
    public static final String CROPS_OWNER = "proprietario";
    public static final String CROPS_QUANTITY = "quantita";
    public static final String CROPS_NOTES = "note";
    public static final String CROPS_INSERTION_DATE = "data_inserimento";
    public static final String CROPS_CURRENT_PHASE = "fase_attuale";
    public static final String CROPS_CURRENT_WATERING_FREQUENCY = "frequenza_innaffiamento_attuale";
    public static final String CROPS_LAST_WATERING = "ultimo_innaffiamento";
    public static final String CROPS_WATERING_FREQUENCY = "frequenza_innaffiamento";


    public static final String FIRESTORE_COLLECTION_FASI = "fasi";
    public static final String PHASE_NAME = "nome_fase";
    public static final String PHASE_START = "inizio_fase";
    public static final String PHASE_DURATION = "durata_fase";
    public static final String PHASE_DESCRIPTION = "descrizione";
    public static final String PHASE_IMAGE = "immagine";
    public static final String PHASE_WATERING_FREQUENCY = "frequenza_innaffiamento";


    public static final int DATABASE_VERSION = 19;
    public static final int DATABASE_VERSION_PLANT = 14;
    public static final int DATABASE_VERSION_PHASE = 13;


    public static final String SHARED_PREFERENCES_FILE_NAME = "com.unimib.eden.preferences";
    public static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[!@#$%^&*+=?-]).{6,15}$";
    public static final String EMAIL_PATTERN = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    public static final String AUTHENTICATION_TOKEN = "AUTHENTICATION_TOKEN";
    public static final String USER_ID = "USER_ID";
    public static final String LAST_UPDATE = "last_update";


    public static final int API_SEARCH_DELAY = 500;
    public static final int SEARCH_PIANTA_OPERATION_CODE = 1;
    public static final int CREATE_COLTURA_OPERATION_CODE = 2;
    public static final int CREATE_PRODOTTO_OPERATION_CODE = 3;
    public static final int PIANTA_DETAILS_OPERATION_CODE = 4;

    public static final String PRODUCT_ID = "id";
    public static final String PRODUCT_TYPE = "tipo";
    public static final String PRODUCT_SELLER = "venditore";
    public static final String PRODUCT_PRICE = "prezzo";
    public static final String PRODUCT_OFFERS = "offerte";
    public static final String PRODUCT_OTHER_INFORMATION = "altre_informazioni";
    public static final String PRODUCT_QUANTITY = "quantita";
    public static final String PRODUCT_PLANT = "pianta";
    public static final String PRODUCT_CURRENT_PHASE = "fase_attuale";
    public static final String PRODUCT_EXCHANGE_AVAILABLE = "scambio_disponibile";


    public static final String OFFER_BUYER = "acquirente";
    public static final String OFFER_PRICE = "offerta";
    public static final String OFFER_STATUS = "stato";


    public static final String FIRESTORE_COLLECTION_PRODOTTI = "prodotti";
    public static final String NOME_DATABASE_PRODOTTO = "prodotto";
    public static final String CROP_DATABASE_NAME = "coltura";
    public static final String GARDEN_DATABASE_NAME = "orto";

    // API
    public static final String apiKey = "27a201b9171e48f5bc8130304240705";
    public static final String BASE_URL = "https://api.weatherapi.com/v1/";
    public static final String LOCATION = "Agrate Brianza";
}
