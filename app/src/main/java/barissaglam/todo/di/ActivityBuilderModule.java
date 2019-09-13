package barissaglam.todo.di;
import barissaglam.todo.di.module.HomeFragmentBuilderModule;
import barissaglam.todo.ui.main.MainActivity;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;


@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector(modules = HomeFragmentBuilderModule.class)
    abstract MainActivity contributeMainActivity();

}
