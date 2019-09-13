package barissaglam.todo.ui.selectdatetime;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.snackbar.Snackbar;

import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;

import javax.inject.Inject;

import barissaglam.todo.R;
import barissaglam.todo.databinding.FragmentDatePickerBinding;
import barissaglam.todo.model.event.DateSelectedEvent;
import barissaglam.todo.ui.createtask.CreateTaskFragment;
import barissaglam.todo.utils.AutoClearedValue;
import barissaglam.todo.utils.Enums;

public class FragmentDatePicker extends DialogFragment {

    public FragmentDatePicker(Enums.DatePickerAction datePickerAction, DateSelectedEvent dateSelectedEvent) {
        this.dateSelectedEvent = dateSelectedEvent;
        this.datePickerAction = datePickerAction;
    }

    public FragmentDatePicker() {

    }

    // Other Variables
    private AutoClearedValue<FragmentDatePickerBinding> binding;
    private DatePickerViewModel mViewModel;
    private DateSelectedEvent dateSelectedEvent;
    private Enums.DatePickerAction datePickerAction;
    // Normal Variables

    // View Variables
    private CalendarView calendarView;
    private MaterialButton btnCancel, btnDone;
    private Chip chipSelectTime;

    // @Inject Valuables
    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }

        FragmentDatePickerBinding dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_date_picker, container, false);
        binding = new AutoClearedValue<>(this, dataBinding);
        return dataBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupViews();
        setupViewModel(savedInstanceState);
        setupCalendarView();
        setupClickListeners();

    }

    private void setupViewModel(Bundle savedInstance) {
        mViewModel = ViewModelProviders.of(this, viewModelFactory).get(DatePickerViewModel.class);
        binding.get().setViewModel(mViewModel);

        if (savedInstance == null) {
            mViewModel.setDateSelectedEvent(datePickerAction, dateSelectedEvent);
        }
    }

    private void setupViews() {
        calendarView = binding.get().fdpCalendarView;
        chipSelectTime = binding.get().fdpChipSelectTaskTime;
        btnCancel = binding.get().fdpBtnCancel;
        btnDone = binding.get().fdpBtnDone;

        if (datePickerAction == Enums.DatePickerAction.SELECT_TASK_DATE)
            chipSelectTime.setVisibility(View.GONE);

    }

    @SuppressLint("DefaultLocale")
    private void setupCalendarView() {
        calendarView.setMinDate(Calendar.getInstance().getTimeInMillis());

        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            mViewModel.selectedDate.set(String.format("%02d", dayOfMonth) + "/" + String.format("%02d", month + 1) + "/" + year);
        });

        calendarView.setDate(mViewModel.getDateSelectedEvent().getSelectedDateLong(datePickerAction));

    }

    private void setupClickListeners() {
        btnCancel.setOnClickListener(v -> dismiss());

        btnDone.setOnClickListener(v -> {
            if (datePickerAction == Enums.DatePickerAction.SELECT_TASK_DATE) {
                mViewModel.getDateSelectedEvent().setSelectedTaskDate(mViewModel.selectedDate.get());
            } else {
                if (datePickerAction == Enums.DatePickerAction.SELECT_TASK_REMINDER_DATE &&
                        (mViewModel.getDateSelectedEvent().getSelectedReminderHour() == null ||
                                mViewModel.getDateSelectedEvent().getSelectedReminderHour().isEmpty())) {
                    Snackbar snackbar = Snackbar.make(getView(), "Please select the reminder hour.", Snackbar.LENGTH_LONG);
                    snackbar.setBackgroundTint(getResources().getColor(R.color.colorWarning));
                    snackbar.show();

                    return;
                }

                mViewModel.getDateSelectedEvent().setSelectedReminderDate(mViewModel.selectedDate.get());
            }

            mViewModel.getDateSelectedEvent().setDatePickerAction(datePickerAction);
            EventBus.getDefault().post(mViewModel.getDateSelectedEvent());
            dismiss();
        });

        chipSelectTime.setOnClickListener(v -> {
            FragmentTimePicker fragmentTimePicker = new FragmentTimePicker(mViewModel.getDateSelectedEvent(), getFragmentDatePicker());
            fragmentTimePicker.show(getChildFragmentManager(), CreateTaskFragment.TAG);
        });

    }


    private FragmentDatePicker getFragmentDatePicker() {
        return this;
    }

}
