package barissaglam.todo.manager;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;

import barissaglam.todo.utils.Constants;
import barissaglam.todo.utils.Keys;

public class LocalDataManager {
    private SharedPreferences mSharedPreferences;

    @Inject
    public LocalDataManager(Application baseApp) {
        mSharedPreferences = baseApp.getSharedPreferences("LocalData", Context.MODE_PRIVATE);
    }


    public boolean isFirst() {
        boolean isFirst = mSharedPreferences.getBoolean(Keys.KEY_IS_FIRST, true);
        if (isFirst) {
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            editor.putBoolean(Keys.KEY_IS_FIRST, false);
            editor.apply();
        }
        return isFirst;
    }

    // *********************************************
    public void setCurrentCategoryID(int listID){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putInt(Keys.KEY_CATEGORY_ID, listID);
        editor.apply();
    }

    public int getCurrentCategoryID(){
        return mSharedPreferences.getInt(Keys.KEY_CATEGORY_ID,1);
    }
    // *********************************************

    // *********************************************
    public void setListSortType(int sortID){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putInt(Keys.KEY_SORT_TYPE, sortID);
        editor.apply();
    }

    public int getListSortType(){
        return mSharedPreferences.getInt(Keys.KEY_SORT_TYPE, Constants.SORT_LIST_CUSTOM);
    }
    // *********************************************



}