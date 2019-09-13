package barissaglam.todo.di;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Singleton;
import barissaglam.todo.ToDoApp;
import dagger.Module;
import dagger.Provides;


@Module(includes = ViewModelModule.class)
class AppModule {

    @Provides
    @Singleton
    Context provideApplicationContext(ToDoApp app) {
        return app;
    }

    @Singleton
    @Provides
    SharedPreferences sharedPreferences(Application app) {
        return app.getSharedPreferences("LocalData", Context.MODE_PRIVATE);
    }



}
