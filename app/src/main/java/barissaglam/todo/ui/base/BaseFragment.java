package barissaglam.todo.ui.base;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

import barissaglam.todo.R;
import barissaglam.todo.di.Injectable;

public class BaseFragment<T extends ViewDataBinding, V extends BaseViewModel> extends Fragment implements Injectable {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void showWarning(String warningMsg) {
        if (getView() == null)
            return;
        Snackbar snackbar = Snackbar.make(getView(), warningMsg, Snackbar.LENGTH_LONG);
        snackbar.setBackgroundTint(getResources().getColor(R.color.colorWarning));
        snackbar.show();
    }

    protected void showSuccess(String successMsg) {
        if (getView() == null)
            return;
        Snackbar snackbar = Snackbar.make(getView(), successMsg, Snackbar.LENGTH_LONG);
        snackbar.setBackgroundTint(getResources().getColor(R.color.colorSuccess));
        snackbar.show();
    }

    protected void showSnackBarWithAction(String msg, String textActionBtn, View.OnClickListener actionBtnClickListener) {
        if (getView() == null)
            return;
        Snackbar snackbar = Snackbar.make(getView(), msg, Snackbar.LENGTH_LONG);
        snackbar.setAction(textActionBtn, actionBtnClickListener);
        snackbar.show();
    }

    protected void showAlertDialogSingleChoiceItems(String dialogTitle, String[] itemList, DialogInterface.OnClickListener itemClickListener, DialogInterface.OnClickListener positiveBtnClickListener, int checkedItem) {
        if (getContext() == null)
            return;

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.MyAlertDialogStyle);
        builder.setTitle(dialogTitle);
        builder.setSingleChoiceItems(itemList, checkedItem, itemClickListener);
        if (positiveBtnClickListener != null)
            builder.setPositiveButton("OK", positiveBtnClickListener);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
/*
    protected void showAlertDialogList(String[] itemList, DialogInterface.OnClickListener itemClickListener) {
        if (getContext() == null)
            return;

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.MyAlertDialogStyle);
        builder.setTitle("Select One Name:-");
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.select_dialog_singlechoice);
        arrayAdapter.addAll(itemList);

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        builder.setAdapter(arrayAdapter, itemClickListener);
        builder.show();
    }*/
}
