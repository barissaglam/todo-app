package barissaglam.todo.model.event;

public class ListSortEvent {
    private int sortType;

    public ListSortEvent(int sortType) {
        this.sortType = sortType;
    }

    public int getSortType() {
        return sortType;
    }

    public void setSortType(int sortType) {
        this.sortType = sortType;
    }
}
