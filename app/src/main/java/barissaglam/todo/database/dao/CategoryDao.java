package barissaglam.todo.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import barissaglam.todo.model.entities.CategoryEntity;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface CategoryDao {

    @Query("SELECT * FROM Categories")
    List<CategoryEntity> getAllCategories();

    @Insert(onConflict = REPLACE)
    void createCategory(CategoryEntity categoryEntity);
}
