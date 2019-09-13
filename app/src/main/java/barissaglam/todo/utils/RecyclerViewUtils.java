package barissaglam.todo.utils;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import barissaglam.todo.helper.ItemTouchHelperAdapter;
import barissaglam.todo.helper.SimpleItemTouchHelperCallback;

public class RecyclerViewUtils {
    public static void setItomTouchHelper(RecyclerView recyclerView, ItemTouchHelperAdapter adapter){
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);
    }
}
