package barissaglam.todo.model.event;

public class TimeSelectedEvent {
    private String selectedHour;

    public TimeSelectedEvent(String selectedHour) {
        this.selectedHour = selectedHour;
    }

    public String getSelectedHour() {
        return selectedHour;
    }
}
