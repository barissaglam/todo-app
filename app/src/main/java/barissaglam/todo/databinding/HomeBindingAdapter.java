package barissaglam.todo.databinding;


import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.chip.Chip;

import java.text.SimpleDateFormat;
import java.util.Date;

import barissaglam.todo.R;
import barissaglam.todo.model.entities.CategoryEntity;
import barissaglam.todo.model.other.CompletedTasksModel;
import barissaglam.todo.model.result.TaskResult;

public class HomeBindingAdapter {
/*
    @BindingAdapter(value = "completedTasksData")
    public static void setCompletedTasksRecycler(RecyclerView recyclerView, CompletedTasksModel completedTasksModel) {
        if (completedTasksModel.getTaskResults() != null && completedTasksModel.getTaskResults().size() != 0) {
            completedTasksModel.getHomeAdapter().initList(completedTasksModel.getTaskResults());
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(), RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(completedTasksModel.getHomeAdapter());
        recyclerView.setItemAnimator(new DefaultItemAnimator());

    }*/

    @BindingAdapter(value = "tintOfProgressBar")
    public static void setTintOfProgressBar(ProgressBar progressBar, CategoryEntity categoryEntity) {
        int color = Color.parseColor(categoryEntity.getCategoryColor());
        progressBar.getIndeterminateDrawable().setColorFilter(color, PorterDuff.Mode.SRC_IN);
        progressBar.getProgressDrawable().setColorFilter(color, PorterDuff.Mode.SRC_IN);
    }

    @BindingAdapter(value = "viewBackgroundColor")
    public static void setViewBackgroundColor(View view, String listColor) {
        view.setBackgroundColor(Color.parseColor(listColor));
    }

    @BindingAdapter(value = "textFont")
    public static void setTextFont(TextView textView, Boolean done) {

        if (done)
            textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        else
            textView.setPaintFlags(textView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
    }

    @BindingAdapter(value = "visibleGone")
    public static void setVisibleOrGone(View view, Boolean visible) {
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter(value = "chipTintColor")
    public static void setChipTintColor(Chip chipColor, String colorHex) {
        if (colorHex != null && !colorHex.isEmpty())
            chipColor.setChipIconTint(ColorStateList.valueOf(Color.parseColor("#616161")));
        else
            chipColor.setChipIconTint(ColorStateList.valueOf(chipColor.getContext().getResources().getColor(R.color.colorPrimary)));
    }

    @BindingAdapter(value = "chipIconVisibility")
    public static void setChipIconVisibility(Chip chip, Boolean visible) {
        chip.setCloseIconVisible(!visible);
    }

    @SuppressLint("SimpleDateFormat")
    @BindingAdapter(value = {"setTextDateWithFormat", "setTextIfEmpty"})
    public static void setTextDateWithFormat(TextView textView, String date, String textIfEmpty) {
        if (date != null && !date.isEmpty()) {
            Date date1 = null;
            @SuppressLint("SimpleDateFormat") SimpleDateFormat frmt = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            try {
                date1 = frmt.parse(date);
                textView.setText(new SimpleDateFormat("dd MMM EEE HH:mm").format(date1));
            } catch (Exception e) {
                @SuppressLint("SimpleDateFormat") SimpleDateFormat frmtCatch = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    date1 = frmtCatch.parse(date);
                    textView.setText(new SimpleDateFormat("dd MMM EEE").format(date1));

                } catch (Exception e1) {
                    textView.setText(date);
                }
            }

        } else
            textView.setText(textIfEmpty);
    }

    @BindingAdapter(value = "imageButtonTint")
    public static void setImageButtonTint(ImageButton imageButton, String tintColor) {
        imageButton.setImageTintList(ColorStateList.valueOf(Color.parseColor(tintColor)));
    }

    @BindingAdapter(value = "imageViewTint")
    public static void setImageViewTint(ImageView imageView, String tintColor) {
        imageView.setImageTintList(ColorStateList.valueOf(Color.parseColor(tintColor)));
    }

    @BindingAdapter("changeCheckState")
    public static void setChangeCheckState(View view, Boolean isChecked) {
        if (isChecked)
            ((ImageButton) view).setImageResource(R.drawable.ic_check_circle_colored_24dp);
        else
            ((ImageButton) view).setImageResource(R.drawable.ic_radio_button_unchecked_gray_24dp);
    }
}
