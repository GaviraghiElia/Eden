package com.unimib.eden.database;

        import androidx.room.Dao;
        import androidx.room.Delete;
        import androidx.room.Insert;
        import androidx.room.Query;

        import com.unimib.eden.model.Pianta;

        import java.util.List;

@Dao
public interface PiantaDao {
    @Query("SELECT * FROM 'pianta'")
    List<Pianta> getAll();

    @Query("SELECT * FROM 'pianta' WHERE id = :piantaId")
    Pianta getById(String piantaId);

    @Query("SELECT * FROM 'pianta' WHERE nome LIKE '%' || :query || '%'")
    List<Pianta> searchMatches(String query);

    @Delete
    void delete(Pianta... pianta);

    @Insert
    void insert(Pianta pianta);
}

