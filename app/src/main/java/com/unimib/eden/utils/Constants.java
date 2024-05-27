package com.unimib.eden.utils;

/**
 * Classe Constants contenente tutte le costanti utilizzate all'interno dell'applicazione.
 *
 * @author Gaia Colombo
 * @author Sandro Erba
 * @author Alice Hoa Galli
 * @author Elia Gaviraghi
 */
public class Constants {

    public static final String PIANTA_NOME = "nome";
    public static final String PIANTA_DESCRIZIONE = "descrizione";
    public static final String PIANTA_FAMIGLIA_BOTANICA = "famiglia_botanica";
    public static final String PIANTA_FASE = "fasi";
    public static final String PIANTA_INIZIO_SEMINA = "inizio_semina";
    public static final String PIANTA_FINE_SEMINA = "fine_semina";
    public static final String PIANTA_SPAZIO_NECESSARIO = "spazio_necessario";
    public static final String PIANTA_ALTEZZA_MAX_PREVISTA = "altezza_max_prevista";
    public static final String PIANTA_TIPO_TERRENO = "tipo_terreno";
    public static final String PIANTA_MIN_TEMPERATURA = "min_temperatura";
    public static final String PIANTA_MAX_TEMPERATURA = "max_temperatura";
    public static final String PIANTA_ESPOSIZIONE_SOLE = "esposizione_sole";


    public static final String COLTURA_PIANTA = "pianta";
    public static final String COLTURA_PROPRIETARIO = "proprietario";
    public static final String COLTURA_QUANTITA = "quantita";
    public static final String COLTURA_NOTE = "note";
    public static final String COLTURA_DATA_INSERIMENTO = "data_inserimento";
    public static final String COLTURA_FASE_ATTUALE = "fase_attuale";
    public static final String COLTURA_FREQUENZA_INNAFFIAMENTO_ATTUALE = "frequenza_innaffiamento_attuale";
    public static final String COLTURA_ULTIMO_INNAFFIAMENTO = "ultimo_innaffiamento";

    public static final String COLTURA_FREQUENZA_INNAFFIAMENTO = "frequenza_innaffiamento";


    public static final String FASE_NOME_FASE = "nome_fase";
    public static final String FASE_INIZIO_FASE = "inizio_fase";
    public static final String FASE_DURATA_FASE = "durata_fase";
    public static final String FASE_DESCRIZIONE = "descrizione";
    public static final String FASE_IMMAGINE = "immagine";
    public static final String FASE_FREQUENZA_INNAFFIAMENTO = "frequenza_innaffiamento";
    public static final int VERSIONE_DATABASE = 18;
    public static final int VERSIONE_DATABASE_PIANTA = 13;
    public static final int VERSIONE_DATABASE_FASE = 12;

    public static final String NOME_DATABASE_ORTO = "orto";
    public static final String NOME_DATABASE_COLTURA = "coltura";

    public static final String FIRESTORE_COLLECTION_PIANTE = "piante";
    public static final String FIRESTORE_COLLECTION_COLTURE = "colture";

    public static final String SHARED_PREFERENCES_FILE_NAME = "com.unimib.eden.preferences";

    public static final String FIREBASE_DATABASE_URL = "https://ginlemons-6adb3-default-rtdb.europe-west1.firebasedatabase.app/";
    public static final String ITEM_ID_PRESSED_KEY = "ItemIdPressedKey";
    public static final String ITEM_LEVEL_PRESSED_KEY = "ItemLevelPressedKey";
    public static final String FRAGMENTFORTRANSITION = "FragmentIntent";
    public static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[!@#$%^&*+=?-]).{6,15}$";
    public static final String EMAIL_PATTERN = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    public static final String AUTHENTICATION_TOKEN = "AUTHENTICATION_TOKEN";
    public static final String USER_ID = "USER_ID";
    public static final int DATABASE_VERSION = 1;
    public static final String NEWS_DATABASE_NAME = "recipes_db";
    public static final long FRESH_TIMEOUT = 60*1000;
    public static final String LAST_UPDATE = "last_update";



    public static final String FIRESTORE_COLLECTION_FASI = "fasi";

    public static final int API_SEARCH_DELAY = 500;
    public static final int SEARCH_PIANTA_OPERATION_CODE = 1;
    public static final int CREATE_COLTURA_OPERATION_CODE = 2;
    public static final int CREATE_PRODOTTO_OPERATION_CODE = 3;
    public static final int PIANTA_DETAILS_OPERATION_CODE = 4;

    public static final String PRODOTTO_ID = "id";
    public static final String PRODOTTO_TIPO = "tipo";
    public static final String PRODOTTO_VENDITORE = "venditore";
    public static final String PRODOTTO_PREZZO = "prezzo";
    public static final String PRODOTTO_OFFERTE = "offerte";
    public static final String PRODOTTO_ALTRE_INFORMAZIONI = "altre_informazioni";
    public static final String PRODOTTO_QUANTITA = "quantita";
    public static final String PRODOTTO_PIANTA = "pianta";
    public static final String PRODOTTO_FASE_ATTUALE = "fase_attuale";
    public static final String PRODOTTO_SCAMBIO_DISPONIBILE = "scambio_disponibile";

    public static final String OFFERTA_ACQUIRENTE = "acquirente";
    public static final String OFFERTA_PREZZO = "offerta";
    public static final String OFFERTA_STATO = "stato";
    public static final String FIRESTORE_COLLECTION_PRODOTTI = "prodotti";

    public static final String NOME_DATABASE_PRODOTTO = "prodotto";
    public static final String DATE_STAMP_FORMAT = "MMM dd, yyyy HH:mm";
}
