package barissaglam.todo.model.event;

import android.annotation.SuppressLint;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import java.text.SimpleDateFormat;
import java.util.Date;

import barissaglam.todo.BR;
import barissaglam.todo.utils.Enums;

public class DateSelectedEvent extends BaseObservable {
    private String selectedTaskDate;
    private String selectedReminderDate;
    private String selectedReminderHour;
    private String selectedReminderMinute;

    private Enums.DatePickerAction datePickerAction;

    public String getSelectedTaskDate() {
        return selectedTaskDate;
    }

    public void setSelectedTaskDate(String selectedTaskDate) {
        this.selectedTaskDate = selectedTaskDate;
    }

    public String getSelectedReminderDate() {
        return selectedReminderDate;
    }

    public void setSelectedReminderDate(String selectedReminderDate) {
        this.selectedReminderDate = selectedReminderDate;
    }

    @Bindable
    public String getSelectedReminderHour() {
        return selectedReminderHour;
    }

    public void setSelectedReminderHour(String selectedReminderHour) {
        this.selectedReminderHour = selectedReminderHour;
        notifyPropertyChanged(BR.selectedReminderHour);
    }

    @Bindable
    public String getSelectedReminderMinute() {
        return selectedReminderMinute;
    }

    public void setSelectedReminderMinute(String selectedReminderMinute) {
        this.selectedReminderMinute = selectedReminderMinute;
        notifyPropertyChanged(BR.selectedReminderMinute);
    }

    public Enums.DatePickerAction getDatePickerAction() {
        return datePickerAction;
    }

    public void setDatePickerAction(Enums.DatePickerAction datePickerAction) {
        this.datePickerAction = datePickerAction;
    }

    public long getSelectedDateLong(Enums.DatePickerAction datePickerAction) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            if (datePickerAction == Enums.DatePickerAction.SELECT_TASK_DATE)
                date = formatter.parse(selectedTaskDate);
            else
                date = formatter.parse(selectedReminderDate);
            return date.getTime();
        } catch (Exception e) {
            return new Date().getTime();
        }

    }

    public String getFullReminderDate(){
        return getSelectedReminderDate() + " " + getSelectedReminderHour() + ":" + getSelectedReminderMinute();
    }

}
