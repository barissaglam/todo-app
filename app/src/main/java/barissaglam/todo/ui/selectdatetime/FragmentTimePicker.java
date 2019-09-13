package barissaglam.todo.ui.selectdatetime;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.button.MaterialButton;

import javax.inject.Inject;

import barissaglam.todo.R;
import barissaglam.todo.databinding.FragmentTimePickerBinding;
import barissaglam.todo.model.event.DateSelectedEvent;
import barissaglam.todo.utils.AutoClearedValue;
import timber.log.Timber;

public class FragmentTimePicker extends DialogFragment {

    public FragmentTimePicker( DateSelectedEvent dateSelectedEvent, FragmentDatePicker fragmentDatePicker) {
        this.dateSelectedEvent = dateSelectedEvent;
        this.fragmentDatePicker = fragmentDatePicker;
    }

    // @Inject Valuables
    @Inject
    ViewModelProvider.Factory viewModelFactory;

    // Other Variables
    private AutoClearedValue<FragmentTimePickerBinding> binding;
    private TimePickerViewModel mViewModel;
    private FragmentDatePicker fragmentDatePicker;
    private DateSelectedEvent dateSelectedEvent;

    // Normal Variables


    // View Variables
    private MaterialButton btnCancel, btnDone;
    private TimePicker timePicker;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            getDialog().setCancelable(false);
        }


        FragmentTimePickerBinding dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_time_picker, container, false);
        binding = new AutoClearedValue<>(this, dataBinding);
        return dataBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupViewModel(savedInstanceState);
        setupViews();
        setupTimePicker();
        setupClickListeners();
    }

    private void setupViewModel(Bundle savedInstance) {
        mViewModel = ViewModelProviders.of(this, viewModelFactory).get(TimePickerViewModel.class);

        if (savedInstance == null) {
            mViewModel.setDateSelectedEvent(dateSelectedEvent);
        }

    }

    private void setupViews() {
        btnCancel = binding.get().ftpBtnCancel;
        btnDone = binding.get().ftpBtnDone;
        timePicker = binding.get().ftpTimePicker;
    }

    @SuppressLint("DefaultLocale")
    private void setupTimePicker() {
        timePicker.setIs24HourView(true);

        timePicker.setCurrentHour(Integer.valueOf(mViewModel.selectedReminderHour.get()));
        timePicker.setCurrentMinute(Integer.valueOf(mViewModel.selectedReminderMinute.get()));


        timePicker.setOnTimeChangedListener((view, hourOfDay, minute) -> {
            mViewModel.selectedReminderHour.set(String.format("%02d", hourOfDay));
            mViewModel.selectedReminderMinute.set(String.format("%02d", minute));


        });

    }

    @SuppressLint("DefaultLocale")
    private void setupClickListeners() {
        btnCancel.setOnClickListener(v -> dismiss());

        btnDone.setOnClickListener(v -> {
            mViewModel.getDateSelectedEvent().setSelectedReminderHour(mViewModel.selectedReminderHour.get());
            mViewModel.getDateSelectedEvent().setSelectedReminderMinute(mViewModel.selectedReminderMinute.get());

            Timber.i("DATEKONTROL  %s", mViewModel.selectedReminderHour.get());
            dismiss();
        });
    }


    /*
    private void setupReminderDaySpinner() {
        if (getContext() == null)
            return;

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.MyAlertDialogStyle);
        builder.setTitle("Select day");
        builder.setSingleChoiceItems(getResources().getStringArray(R.array.reminderDayList), -1, new DialogInterface.OnClickListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onClick(DialogInterface dialog, int which) {

                DateTime reminderDateTime = new DateTime(
                        Integer.valueOf(dateSelectedEvent.getSelectedYear()),
                        Integer.valueOf(dateSelectedEvent.getSelectedMonth()),
                        Integer.valueOf(dateSelectedEvent.getSelectedDay()),
                        Integer.valueOf(mViewModel.selectedReminderHour.get()),
                        Integer.valueOf(mViewModel.selectedReminderMinute.get()),
                        Calendar.SECOND).minusDays(which);

                dialog.dismiss();


                if ((reminderDateTime.getMillis() - DateTime.now().getMillis()) < 0) {
                    Snackbar.make(getView(), "Hatırlatma tarihi, şu anki tarihten daha önce olamaz. Lütfen daha ileri saat/gün seçin", Snackbar.LENGTH_LONG).show();
                    return;
                }


                mViewModel.getDateSelectedEvent().setSelectedReminderEarlyDay(String.valueOf(which));
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

*/
}

