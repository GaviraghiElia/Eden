package com.unimib.eden.database;

        import androidx.room.Dao;
        import androidx.room.Delete;
        import androidx.room.Insert;
        import androidx.room.Query;

        import com.unimib.eden.model.Pianta;

        import java.util.List;

/**
 * Interfaccia Dao per Pianta.
 * Questa interfaccia definisce le operazioni di accesso ai dati per la tabella Pianta nel database Room.
 *
 * @author Alice Hoa Galli
 */
@Dao
public interface PiantaDao {

    /**
     * Ottiene tutte le piante presenti nel database.
     *
     * @return Una lista di tutte le piante presenti nel database.
     */
    @Query("SELECT * FROM 'pianta'")
    List<Pianta> getAll();

    /**
     * Ottiene una lista di tutte le piante nel cui nome è presente come sottostringa la stringa passata in input.
     *
     * @param query Il nome della pianta che si vuole cercare.
     * @return  La lista delle piante nel cui nome è presente come sottostringa la stringa passata in input.
     */
    @Query("SELECT * FROM 'pianta' WHERE nome LIKE '%' || :query || '%'")
    List<Pianta> searchPiante(String query);

    /**
     * Ottiene una lista di tutte le piante presenti nel database nel cui nome è presente come sottostringa la stringa passata in input e soddisfa tutti i parametri passati in input.
     *
     * @param query Il nome della pianta che si vuole cercare.
     * @param frequenzaInnaffiamento    La frequenza di innaffiamento maggiore o uguale che le piante devono avere.
     * @param esposizioneSole   L'esposizione solare che le piante devono avere.
     * @param inizioSemina  Il mese di inizio semina maggiore o ugale che le piante devono avere.
     * @param fineSemina    Il mese di fine semina minore o uguale che le piante devono avere.
     * @return  La lista di tutte le piante nel cui nome è presente come sottostringa la stringa passata in input e soddisfa tutti i parametri passati in input.
     */
    @Query("SELECT * FROM 'pianta' WHERE nome LIKE '%' || :query || '%' AND frequenza_innaffiamento >= :frequenzaInnaffiamento AND esposizione_sole = :esposizioneSole AND inizio_semina >= :inizioSemina AND fine_semina <= :fineSemina")
    List<Pianta> searchPianteFiltriAll(String query, int frequenzaInnaffiamento, String esposizioneSole, int inizioSemina, int fineSemina);

    /**
     * Ottiene una lista di tutte le piante presenti nel database nel cui nome è presente come sottostringa la stringa passata in input e soddisfa tutti i parametri passati in input.
     *
     * @param query Il nome della pianta che si vuole cercare.
     * @param frequenzaInnaffiamento    La frequenza di innaffiamento maggiore o uguale che le piante devono avere.
     * @param inizioSemina  Il mese di inizio semina maggiore o ugale che le piante devono avere.
     * @param fineSemina    Il mese di fine semina minore o uguale che le piante devono avere.
     * @return  La lista di tutte le piante nel cui nome è presente come sottostringa la stringa passata in input e soddisfa tutti i parametri passati in input.
     */
    @Query("SELECT * FROM 'pianta' WHERE nome LIKE '%' || :query || '%'AND frequenza_innaffiamento >= :frequenzaInnaffiamento AND inizio_semina >= :inizioSemina AND fine_semina <= :fineSemina")
    List<Pianta> searchPianteFiltri(String query, int frequenzaInnaffiamento, int inizioSemina, int fineSemina);

    /**
     * Ottiene la pianta presente nel database con l'id.
     *
     * @param piantaId  L'id della pianta da cercare all'interno del database.
     */
    @Query("SELECT * FROM 'pianta' WHERE id = :piantaId")
    Pianta getById(String piantaId);

    /**
     * Elimina la pianta in input dal database.
     *
     * @param pianta  La pianta da eliminare all'interno del database.
     */
    @Delete
    void delete(Pianta... pianta);

    /**
     * Inserisce la pianta in input nel database.
     *
     * @param pianta  La pianta da inserire all'interno del database.
     */
    @Insert
    void insert(Pianta pianta);
}
