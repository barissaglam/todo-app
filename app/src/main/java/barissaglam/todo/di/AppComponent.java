package barissaglam.todo.di;


import android.app.Application;

import javax.inject.Singleton;

import barissaglam.todo.ToDoApp;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Singleton
@Component(modules = {
        AndroidInjectionModule.class,
        ActivityBuilderModule.class,
        AppModule.class,
        RoomModule.class,
        BroadcastReceiverModule.class
})

public interface AppComponent {

    @Component.Builder
    interface Builder{
        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
    void inject(ToDoApp toDoApp);
}
