package com.unimib.eden.utils;

public class Constants {

    public static final String PIANTA_NOME = "nome";
    public static final String PIANTA_DESCRIZIONE = "descrizione";
    public static final String PIANTA_FAMIGLIA_BOTANICA = "famiglia_botanica";
    public static final String PIANTA_FASE = "fasi";
    public static final String PIANTA_FREQUENZA_INNAFFIAMENTO = "frequenza_innaffiamento";
    public static final String PIANTA_INIZIO_SEMINA = "inizio_semina";
    public static final String PIANTA_FINE_SEMINA = "fine_semina";
    public static final String PIANTA_SPAZIO_NECESSARIO = "spazio_necessario";
    public static final String PIANTA_ALTEZZA_MAX_PREVISTA = "altezza_max_prevista";
    public static final String PIANTA_TIPO_TERRENO = "tipo_terreno";
    public static final String PIANTA_MIN_TEMPERATURA = "min_temperatura";
    public static final String PIANTA_MAX_TEMPERATURA = "max_temperatura";
    public static final String PIANTA_ESPOSIZIONE_SOLE = "esposizione_sole";

    public static final String FASE_NOME_FASE = "nome_fase";
    public static final String FASE_INIZIO_FASE = "inizio_fase";
    public static final String FASE_DURATA_FASE = "durata_fase";
    public static final String FASE_DESCRIZIONE = "descrizione";
    public static final String FASE_IMMAGINE = "immagine";
    public static final int VERSIONE_DATABASE = 2;

    public static final String NOME_DATABASE_ORTO = "pianta";

    public static final String FIRESTORE_COLLECTION_PIANTE = "piante";

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
}
