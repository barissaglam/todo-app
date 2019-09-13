package barissaglam.todo.model.event;

import barissaglam.todo.model.entities.CategoryEntity;

public class ListSelectedEvent {
    private CategoryEntity categoryEntity;
    private int itemPos;

    public ListSelectedEvent(CategoryEntity categoryEntity, int itemPos) {
        this.categoryEntity = categoryEntity;
        this.itemPos = itemPos;
    }

    public CategoryEntity getCategoryEntity() {
        return categoryEntity;
    }

    public void setCategoryEntity(CategoryEntity categoryEntity) {
        this.categoryEntity = categoryEntity;
    }

    public int getItemPos() {
        return itemPos;
    }

    public void setItemPos(int itemPos) {
        this.itemPos = itemPos;
    }
}
