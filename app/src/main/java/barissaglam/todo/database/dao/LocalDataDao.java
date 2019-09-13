package barissaglam.todo.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;

import barissaglam.todo.model.entities.PriorityEntity;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface LocalDataDao {

    @Insert(onConflict = REPLACE)
    void insertPriority(PriorityEntity priorityEntity);

}
