package barissaglam.todo.ui.base;

import androidx.lifecycle.ViewModel;

import java.lang.ref.WeakReference;

public class BaseViewModel extends ViewModel {


}

/*

public abstract class BaseViewModel<N> extends ViewModel {
    private WeakReference<N> mNavigator;

    @Override
    protected void onCleared() {
        super.onCleared();
    }

    public N getNavigator() {
        return mNavigator.get();
    }

    public void setNavigator(N navigator) {
        this.mNavigator = new WeakReference<>(navigator);
    }

}
*/
