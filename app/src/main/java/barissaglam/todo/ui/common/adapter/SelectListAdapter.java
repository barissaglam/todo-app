package barissaglam.todo.ui.common.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.List;

import barissaglam.todo.R;
import barissaglam.todo.model.entities.CategoryEntity;

public class SelectListAdapter extends BaseAdapter {
    private List<CategoryEntity> data;
    private Context context;

    public SelectListAdapter(List<CategoryEntity> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.item_dialog_select_category, null);
        }


        TextView tvListName = convertView.findViewById(R.id.tvCategoryName);
        ImageView imgListColor = convertView.findViewById(R.id.imgCategoryColor);
        RadioButton radioButton = convertView.findViewById(R.id.radioButton);

        tvListName.setText(data.get(position).getCategoryName());
        imgListColor.setImageTintList(ColorStateList.valueOf(Color.parseColor(data.get(position).getCategoryColor())));
        return convertView;
    }
}
