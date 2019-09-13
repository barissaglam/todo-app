package barissaglam.todo.ui.base.adapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import barissaglam.todo.BR;
import timber.log.Timber;

abstract public class MultiTypeDataBoundAdapter extends BaseDataBoundAdapter {
    protected List<Object> mItems = new ArrayList<>();

    public MultiTypeDataBoundAdapter(Object... items) {
        Collections.addAll(mItems, items);
    }

    @Override
    protected void bindItem(DataBoundViewHolder holder, int position, List payloads) {
        holder.binding.setVariable(BR.data, mItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public Object getItem(int position) {
        return mItems.get(position);
    }

    public void addItem(Object item) {
        mItems.add(item);
        notifyItemInserted(mItems.size() - 1);
        //notifyDataSetChanged();
    }

    public void addItem(int position, Object item) {
        mItems.add(position, item);
       // notifyItemInserted(position);
       // notifyItemRangeChanged(position, mItems.size());
        notifyDataSetChanged();
    }
    public void removeItem(int position) {
        Timber.i("ItemPost : %s",position);
        mItems.remove(position);
        //notifyItemRemoved(position);
        // notifyItemRangeChanged(position, mItems.size());
        notifyDataSetChanged();
    }

}