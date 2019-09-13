package barissaglam.todo.utils;

import android.annotation.SuppressLint;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {


    @SuppressLint("SimpleDateFormat")
    public static String parseCurrentDate() {
        return new SimpleDateFormat("dd MMMM EEEE").format(new Date().getTime());
    }

    public static String getDateWithFormat(String dateTime){

        @SuppressLint("SimpleDateFormat") SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMMM EEEE");

        Date date = null;
        try {
            date = inputFormat.parse(dateTime);
            return outputFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static String getHourFromDateTime(String inputPattern, String dateTime){

        @SuppressLint("SimpleDateFormat") SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat outputFormat = new SimpleDateFormat("HH");

        Date date = null;
        try {
            date = inputFormat.parse(dateTime);
            return outputFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getDateFromDateTime(String inputPattern, String dateTime){

        @SuppressLint("SimpleDateFormat") SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");

        Date date = null;
        try {
            date = inputFormat.parse(dateTime);
            return outputFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



    public static String getMinuteFromDateTime(String inputPattern, String dateTime){

        @SuppressLint("SimpleDateFormat") SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat outputFormat = new SimpleDateFormat("mm");

        Date date = null;
        try {
            date = inputFormat.parse(dateTime);
            return outputFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
