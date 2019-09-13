package barissaglam.todo.database;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import barissaglam.todo.database.dao.CategoryDao;
import barissaglam.todo.database.dao.TaskDao;
import barissaglam.todo.database.dao.LocalDataDao;
import barissaglam.todo.model.entities.CategoryEntity;
import barissaglam.todo.model.entities.PriorityEntity;
import barissaglam.todo.model.entities.TaskEntity;
import barissaglam.todo.model.entities.TaskStepEntity;

@Database(entities = {
        CategoryEntity.class,
        TaskEntity.class,
        PriorityEntity.class,
        TaskStepEntity.class
}, version = 1, exportSchema = false)

public abstract class AppDatabase extends RoomDatabase {

    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    @Override
    public void clearAllTables() {

    }

    public abstract CategoryDao taskListDao();
    public abstract TaskDao taskDao();
    public abstract LocalDataDao localDao();
}
