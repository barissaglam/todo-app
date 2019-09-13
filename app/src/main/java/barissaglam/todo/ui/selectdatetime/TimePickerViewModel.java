package barissaglam.todo.ui.selectdatetime;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

import java.util.Calendar;

import javax.inject.Inject;

import barissaglam.todo.model.event.DateSelectedEvent;

@SuppressLint("DefaultLocale")
public class TimePickerViewModel extends ViewModel {
    private DateSelectedEvent dateSelectedEvent = new DateSelectedEvent();

    public ObservableField<String> selectedReminderHour = new ObservableField<>();
    public ObservableField<String> selectedReminderMinute = new ObservableField<>();


    @Inject
    public TimePickerViewModel() {

    }

    public DateSelectedEvent getDateSelectedEvent() {
        return dateSelectedEvent;
    }

    public void setDateSelectedEvent(DateSelectedEvent dateSelectedEvent) {
        this.dateSelectedEvent = dateSelectedEvent;

        if (dateSelectedEvent != null && dateSelectedEvent.getSelectedReminderHour() != null && !dateSelectedEvent.getSelectedReminderHour().isEmpty()) {
            selectedReminderHour.set(dateSelectedEvent.getSelectedReminderHour());
            selectedReminderMinute.set(dateSelectedEvent.getSelectedReminderMinute());
        }else {
            selectedReminderHour.set(String.format("%02d", Calendar.getInstance().get(Calendar.HOUR_OF_DAY)));
            selectedReminderMinute.set(String.format("%02d", Calendar.getInstance().get(Calendar.MINUTE)));
        }

    }
}
