package barissaglam.todo.ui.main;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.bottomappbar.BottomAppBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

import barissaglam.todo.R;
import barissaglam.todo.database.repository.CategoryRepository;
import barissaglam.todo.databinding.ActivityMainBinding;
import barissaglam.todo.manager.LocalDataManager;
import barissaglam.todo.model.entities.CategoryEntity;
import barissaglam.todo.model.entities.PriorityEntity;
import barissaglam.todo.model.event.BottomAppBarEvent;
import barissaglam.todo.database.repository.LocalDataRepository;
import barissaglam.todo.ui.base.BaseActivity;
import barissaglam.todo.ui.common.NavigationController;
import barissaglam.todo.ui.drawer.DrawerFragment;
import barissaglam.todo.ui.menu.MenuFragment;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class MainActivity extends BaseActivity implements HasSupportFragmentInjector {
    //Inject Variables
    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    // @Inject Valuables
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    @Inject
    LocalDataRepository localDataRepository;
    @Inject
    CategoryRepository categoryRepository;
    @Inject
    LocalDataManager localDataManager;
    @Inject
    NavigationController navigationController;

    // Normal Variables
    private MainViewModel mViewModel;
    private DrawerFragment drawerFragment;
    private MenuFragment menuFragment;


    // View Variables


    public ActivityMainBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(binding.bottomAppBar);

        setupViewModel();
        setupClickListeners();

        if (savedInstanceState == null)
            navigationController.navigateToHome();

        if (localDataManager.isFirst()) {
            localDataRepository.insertPriorityData(new PriorityEntity("Düşük", "Low"));
            localDataRepository.insertPriorityData(new PriorityEntity("Orta", "Medium"));
            localDataRepository.insertPriorityData(new PriorityEntity("Yüksek", "High"));

            categoryRepository.createNewCategory(new CategoryEntity("My Tasks", "#616161"));
        }

        if (mViewModel.anyFragmentOpened.get()) {
            binding.fab.hide();
            binding.bottomAppBar.setVisibility(View.GONE);
        }

    }


    private void setupViewModel() {
        mViewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel.class);
    }

    private void setupClickListeners() {
        binding.fab.setOnClickListener(v -> {
            navigationController.navigateToCreateTask();
            updateVisibleStatusOfBottomAppBar();
        });

        binding.bottomAppBar.setNavigationOnClickListener(v -> {
            if (drawerFragment == null)
                drawerFragment = DrawerFragment.newInstance();
            drawerFragment.show(getSupportFragmentManager(), drawerFragment.getTag());

        });
    }

    private void updateVisibleStatusOfBottomAppBar() {
        if (binding.fab.isShown()) {
            binding.fab.hide();
            binding.bottomAppBar.getBehavior().slideDown(binding.bottomAppBar);
            mViewModel.anyFragmentOpened.set(true);
        } else {
            if (binding.bottomAppBar.getVisibility() == View.GONE) {
                Animation slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up);
                binding.bottomAppBar.setVisibility(View.VISIBLE);
                binding.bottomAppBar.startAnimation(slideUp);
            }
            mViewModel.anyFragmentOpened.set(false);
            binding.fab.show();
            binding.bottomAppBar.getBehavior().slideUp(binding.bottomAppBar);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu){
            if (menuFragment == null)
                menuFragment = MenuFragment.newInstance();
            menuFragment.show(getSupportFragmentManager(), menuFragment.getTag());
        }
        return true;
    }

    // ******************** EventBus functions **/

    @Subscribe(sticky = true)
    public void showOrHideBottomAppbarEvent(BottomAppBarEvent bottomAppBarEvent) {
        updateVisibleStatusOfBottomAppBar();
    }


    // ******************** Lifecycle functions **/
    @Override
    public void onStart() {

        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);

        BottomAppBarEvent event = EventBus.getDefault().getStickyEvent(BottomAppBarEvent.class);
        if (event != null) {
            EventBus.getDefault().removeStickyEvent(event);
        }
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            updateVisibleStatusOfBottomAppBar();
            mViewModel.anyFragmentOpened.set(false);
            fm.popBackStack();
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }

}
