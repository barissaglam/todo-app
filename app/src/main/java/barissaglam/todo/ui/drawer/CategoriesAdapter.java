package barissaglam.todo.ui.drawer;

import android.graphics.Color;
import android.util.TypedValue;

import java.util.List;

import barissaglam.todo.R;
import barissaglam.todo.databinding.ItemCategoryBinding;
import barissaglam.todo.model.entities.CategoryEntity;
import barissaglam.todo.ui.base.adapter.DataBoundAdapter;
import barissaglam.todo.ui.base.adapter.DataBoundViewHolder;

public class CategoriesAdapter extends DataBoundAdapter<ItemCategoryBinding> {

    private List<CategoryEntity> listEntities;
    private CategoryItemCallback callback;
    private int selectedIndex = 0;

    public CategoriesAdapter(List<CategoryEntity> listEntities, CategoryItemCallback callback, int selectedIndex) {
        super(R.layout.item_category);
        this.listEntities = listEntities;
        this.callback = callback;
        this.selectedIndex = selectedIndex;
    }

    @Override
    protected void bindItem(DataBoundViewHolder<ItemCategoryBinding> holder, int position, List<Object> payloads) {
        holder.binding.setData(listEntities.get(position));
        holder.binding.setCallback(callback);

        if (position == selectedIndex) {
            holder.binding.icTvCategoryName.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.colorPrimary));
            holder.binding.icTvCategoryName.setBackgroundColor(Color.parseColor("#30d32f2f"));
        } else {
            TypedValue outValue = new TypedValue();
            holder.itemView.getContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground, outValue, true);
            holder.binding.icTvCategoryName.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.colorTextDarkDis));
            holder.binding.icTvCategoryName.setBackgroundResource(outValue.resourceId);
        }

    }

    @Override
    public int getItemCount() {
        return listEntities != null ? listEntities.size() : 0;
    }

    public interface CategoryItemCallback {
        void onClickCategoryItem(CategoryEntity categoryEntity);
    }
}
