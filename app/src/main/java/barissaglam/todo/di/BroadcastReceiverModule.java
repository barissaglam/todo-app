package barissaglam.todo.di;

import barissaglam.todo.reminder.ReminderBroadcastManager;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class BroadcastReceiverModule {

    @ContributesAndroidInjector
    abstract ReminderBroadcastManager provideReminderBroadcastManagerFactory();
}