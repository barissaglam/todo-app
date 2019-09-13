package barissaglam.todo.ui.main;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import javax.inject.Inject;

import barissaglam.todo.ui.base.BaseViewModel;

public class MainViewModel extends BaseViewModel {

    ObservableBoolean anyFragmentOpened = new ObservableBoolean(false);

    @Inject
    public MainViewModel() {

    }
}
