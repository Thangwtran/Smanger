package com.example.dialog_toolbar.utils;

import android.annotation.SuppressLint;
import android.widget.SpinnerAdapter;


import com.example.dialog_toolbar.R;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    public static final String DATE_FORMAT = "dd/MM/yyyy";

    public static String dateToString(Date date){
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        return dateFormat.format(date);
    }

    public static Date stringToDate(String date){
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        try{
            return dateFormat.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static void formatNumber(Object number){
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        decimalFormat.format(number);
    }
    public static int getImageResourceId(String gender){
        if(gender.compareToIgnoreCase("Nam") == 0){
            return R.drawable.ic_man_24;
        } else if (gender.compareToIgnoreCase("Nữ") == 0){
            return R.drawable.ic_woman_24;
        }else{
            return R.drawable.ic_man_24;
        }
    }

    public static int getAdapterPosition(SpinnerAdapter adapter, String value){
        for (int i = 0; i < adapter.getCount(); i++) {
            if(value.compareTo(adapter.getItem(i).toString()) == 0){
                return i;
            }
        }
        return 0;
    }
}
