package barissaglam.todo.model.event;

public class ColorSelectedEvent {
    private String color;
    private int itemPos;


    public ColorSelectedEvent(String color, int itemPos) {
        this.color = color;
        this.itemPos = itemPos;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getItemPos() {
        return itemPos;
    }

    public void setItemPos(int itemPos) {
        this.itemPos = itemPos;
    }
}
