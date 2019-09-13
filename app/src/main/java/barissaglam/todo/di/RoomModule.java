package barissaglam.todo.di;

import android.app.Application;

import androidx.room.Room;

import javax.inject.Singleton;

import barissaglam.todo.database.AppDatabase;
import barissaglam.todo.database.dao.CategoryDao;
import barissaglam.todo.database.dao.TaskDao;
import barissaglam.todo.database.dao.LocalDataDao;
import barissaglam.todo.database.repository.CategoryRepository;
import barissaglam.todo.database.repository.LocalDataRepository;
import barissaglam.todo.database.repository.TaskRepository;
import dagger.Module;
import dagger.Provides;

@Module
class RoomModule {

    @Singleton
    @Provides
    AppDatabase provideAppDatabase(Application context) {
        return Room.databaseBuilder(context, AppDatabase.class, "todo_db").allowMainThreadQueries().build();
    }


    // Provides Task List Repository
    @Singleton
    @Provides
    CategoryRepository provideTaskListRepository(CategoryDao categoryDao) {
        return new CategoryRepository(categoryDao);
    }

    @Singleton
    @Provides
    CategoryDao provideTaskListDao(AppDatabase appDatabase) {
        return appDatabase.taskListDao();
    }


    // Provides Task Repository
    @Singleton
    TaskRepository provideTaskRepository(TaskDao taskDao){
        return new TaskRepository(taskDao);
    }

    @Singleton
    @Provides
    TaskDao provideTaskDao(AppDatabase appDatabase){
        return appDatabase.taskDao();
    }

    // Provides LocalData Repository

    @Singleton
    @Provides
    LocalDataDao provideLocalDataDao(AppDatabase appDatabase){
        return appDatabase.localDao();
    }

    @Singleton
    LocalDataRepository provideLocalDataRepository(LocalDataDao localDataDao){
        return new LocalDataRepository(localDataDao);
    }
}
