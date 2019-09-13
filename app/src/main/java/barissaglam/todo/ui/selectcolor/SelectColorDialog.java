package barissaglam.todo.ui.selectcolor;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

import java.util.Objects;

import javax.inject.Inject;

import barissaglam.todo.R;
import barissaglam.todo.databinding.DialogSelectColorBinding;
import barissaglam.todo.ui.createcategory.CreateCategoryFragment;
import barissaglam.todo.ui.createcategory.CreateCategoryViewModel;

public class SelectColorDialog extends DialogFragment implements SelectColorAdapter.SelectColorItemCallback {
    public SelectColorDialog() {

    }

    // @Inject Valuables
    @Inject
    ViewModelProvider.Factory viewModelFactory;

    // Other Valuables
    private CreateCategoryViewModel mViewModel;
    private DialogSelectColorBinding dataBinding;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        dataBinding = DataBindingUtil.inflate(getActivity().getLayoutInflater(), R.layout.dialog_select_color, null, false);

        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getContext()), R.style.MyAlertDialogStyle);
        builder.setTitle("Choose Color");
        builder.setView(dataBinding.getRoot());

        builder.setPositiveButton("Cancel", null);

        return builder.create();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setupViewModel(savedInstanceState);
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        String[] colorList = getResources().getStringArray(R.array.listColors);
        dataBinding.dscColorsRecycler.setLayoutManager(new GridLayoutManager(getContext(), 6));
        dataBinding.dscColorsRecycler.setAdapter(new SelectColorAdapter(colorList, this, mViewModel.selectedColorIndex.get()));
    }

    private void setupViewModel(Bundle savedInstance) {
        if (getParentFragment() != null && getParentFragment().getTag() != null && getParentFragment().getTag().equals(CreateCategoryFragment.TAG))
            mViewModel = ViewModelProviders.of(getParentFragment(), viewModelFactory).get(CreateCategoryViewModel.class);

    }

    @Override
    public void onSelectedColor(String colorStr, int itemPos) {
        mViewModel.getSelectedColor().set(colorStr);
        mViewModel.selectedColorIndex.set(itemPos);

        dismiss();
    }

}
