package barissaglam.todo.ui.selectcolor;

import java.util.List;

import barissaglam.todo.R;
import barissaglam.todo.databinding.ItemListColorBinding;
import barissaglam.todo.ui.base.adapter.DataBoundAdapter;
import barissaglam.todo.ui.base.adapter.DataBoundViewHolder;

public class SelectColorAdapter extends DataBoundAdapter<ItemListColorBinding> {
    private final String[] colorList;
    private final SelectColorItemCallback callback;
    private final int selectedColor;

    public SelectColorAdapter(String[] colorList, SelectColorItemCallback callback, int selectedColor) {
        super(R.layout.item_list_color);
        this.colorList = colorList;
        this.callback = callback;
        this.selectedColor = selectedColor;
    }

    @Override
    protected void bindItem(DataBoundViewHolder<ItemListColorBinding> holder, int position, List<Object> payloads) {
        holder.binding.setItemPos(holder.getAdapterPosition());
        holder.binding.setData(colorList[position]);
        holder.binding.setCallback(callback);
        holder.binding.setSelectedItemPos(selectedColor);
    }

    @Override
    public int getItemCount() {
        return colorList != null ? colorList.length : 0;
    }


    public interface SelectColorItemCallback {
        void onSelectedColor(String colorStr, int itemPos);
    }
}
