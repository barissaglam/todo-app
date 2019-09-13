package barissaglam.todo.ui.selectdatetime;

import android.annotation.SuppressLint;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

import java.util.Calendar;

import javax.inject.Inject;

import barissaglam.todo.model.event.DateSelectedEvent;
import barissaglam.todo.utils.Enums;

@SuppressLint("DefaultLocale")
public class DatePickerViewModel extends ViewModel {

    private DateSelectedEvent dateSelectedEvent = new DateSelectedEvent();

    public ObservableField<String> selectedDate = new ObservableField<>(String.format("%02d", Calendar.getInstance().get(Calendar.DAY_OF_MONTH)) + "/" +
            String.format("%02d", Calendar.getInstance().get(Calendar.MONTH) + 1) + "/" + Calendar.getInstance().get(Calendar.YEAR));

    @Inject
    public DatePickerViewModel() {
    }

    public DateSelectedEvent getDateSelectedEvent() {
        return dateSelectedEvent;
    }

    public void setDateSelectedEvent(Enums.DatePickerAction datePickerAction, DateSelectedEvent dateSelectedEvent) {
        this.dateSelectedEvent = dateSelectedEvent;

        if (datePickerAction == Enums.DatePickerAction.SELECT_TASK_DATE && dateSelectedEvent != null && dateSelectedEvent.getSelectedTaskDate() != null && !dateSelectedEvent.getSelectedTaskDate().isEmpty()) {
            selectedDate.set(dateSelectedEvent.getSelectedTaskDate());
        }

        if (datePickerAction == Enums.DatePickerAction.SELECT_TASK_REMINDER_DATE && dateSelectedEvent != null && dateSelectedEvent.getSelectedReminderDate() != null && !dateSelectedEvent.getSelectedReminderDate().isEmpty()) {
            selectedDate.set(dateSelectedEvent.getSelectedReminderDate());
        }

    }
}
