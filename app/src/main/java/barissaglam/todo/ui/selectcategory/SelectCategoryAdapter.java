package barissaglam.todo.ui.selectcategory;

import java.util.List;

import barissaglam.todo.R;
import barissaglam.todo.databinding.ItemDialogSelectCategoryBinding;
import barissaglam.todo.model.entities.CategoryEntity;
import barissaglam.todo.ui.base.adapter.DataBoundAdapter;
import barissaglam.todo.ui.base.adapter.DataBoundViewHolder;

public class SelectCategoryAdapter extends DataBoundAdapter<ItemDialogSelectCategoryBinding> {

    private List<CategoryEntity> categoryEntities;
    private SelectCategoryItemCallback callback;
    private int selectedItemPos;

    public SelectCategoryAdapter(List<CategoryEntity> categoryEntities, SelectCategoryItemCallback callback, int selectedItemPos) {
        super(R.layout.item_dialog_select_category);
        this.categoryEntities = categoryEntities;
        this.callback = callback;
        this.selectedItemPos = selectedItemPos;
    }

    @Override
    protected void bindItem(DataBoundViewHolder<ItemDialogSelectCategoryBinding> holder, int position, List<Object> payloads) {
        holder.binding.setData(categoryEntities.get(position));
        holder.binding.setCallback(callback);
        holder.binding.setItemPos(holder.getAdapterPosition());
        holder.binding.setSelectedItemPos(selectedItemPos);
    }

    @Override
    public int getItemCount() {
        return categoryEntities != null ? categoryEntities.size() : 0;
    }

    public interface SelectCategoryItemCallback{
        void onClickSelectedCategoryItem(CategoryEntity categoryEntity, int itemPos);
    }
}
