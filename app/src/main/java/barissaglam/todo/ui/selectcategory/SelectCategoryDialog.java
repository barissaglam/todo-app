package barissaglam.todo.ui.selectcategory;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

import javax.inject.Inject;

import barissaglam.todo.R;
import barissaglam.todo.databinding.DialogSelectCategoryBinding;
import barissaglam.todo.model.entities.CategoryEntity;
import barissaglam.todo.ui.taskdetail.TaskDetailViewModel;


public class SelectCategoryDialog extends DialogFragment implements SelectCategoryAdapter.SelectCategoryItemCallback {

    public SelectCategoryDialog() {

    }

    // @Inject Valuables
    @Inject
    ViewModelProvider.Factory viewModelFactory;

    // Other Valuables
    private DialogSelectCategoryBinding databinding;
    private TaskDetailViewModel mViewModel;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        databinding = DataBindingUtil.inflate(getActivity().getLayoutInflater(), R.layout.dialog_select_category, null, false);

        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getContext()), R.style.MyAlertDialogStyle);
        builder.setTitle("Choose List");
        builder.setView(databinding.getRoot());

        builder.setPositiveButton("Cancel", null);

        return builder.create();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setupViewModel();
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        databinding.dscRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        databinding.dscRecyclerView.setAdapter(new SelectCategoryAdapter(mViewModel.getCategoryEntities(), this, mViewModel.getTaskResult().getCategoryID() - 1));
    }

    private void setupViewModel() {
        mViewModel = ViewModelProviders.of(getParentFragment(), viewModelFactory).get(TaskDetailViewModel.class);
    }

    // ******************** SelectCategoryItemCallback functions**/

    @Override
    public void onClickSelectedCategoryItem(CategoryEntity categoryEntity, int itemPos) {
        mViewModel.updateListField(itemPos);
        dismiss();
    }

}
