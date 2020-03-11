package com.khud44.triviaallday.utils;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.GsonBuilder;
import com.khud44.triviaallday.R;
import com.khud44.triviaallday.models.pojo.CategoryList;
import com.khud44.triviaallday.models.pojo.QuestionList;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class Utils {

    private static final String TAG = Utils.class.getSimpleName();

    public static QuestionList loadDefaultQuestions(Context context){
        return (QuestionList) jsonToObject(context, "questions.json", QuestionList.class);
    }

    public static CategoryList loadCategories(Context context){
        return (CategoryList) jsonToObject(context, "categories.json", CategoryList.class);
    }

    private static Object jsonToObject(Context context, String filename, Class objectClass){

        String jsonString = jsonFileToString(context, filename);

        if (jsonString != null) {
            Log.d(TAG, "default questions were loaded");
            return new GsonBuilder().create().fromJson(jsonString, objectClass);
        }

        return null;
    }

    private static String jsonFileToString(Context context, String filename){
        String json;
        try{
            InputStream stream = context.getAssets().open(filename);
            int size = stream.available();
            byte [] bytes = new byte[size];
            stream.read(bytes);
            stream.close();
            json = new String(bytes, "UTF-8");
        }
        catch(IOException e){
            Log.d(TAG, "failed to load default question");
            e.printStackTrace();
            return null;
        }
        return json;
    }

    public static int dpToPx(Context context, int dp){
        Resources r = context.getResources();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
    }

    public static void setTextViewDecodedHtml(TextView textView, String htmlString){
        // in api >=24 fromHtml is deprecated
//        if (Build.VERSION.SDK_INT >= 24) {
//            textView.setText(Html.fromHtml(htmlString , Html.FROM_HTML_MODE_LEGACY));
//        }
//        else {
//            textView.setText(Html.fromHtml(htmlString));
//        }
        textView.setText(decodedHtmlToString(htmlString));

    }

    // API returns string html encoded
    public static String decodedHtmlToString(String htmlString){
        // in api >=24 fromHtml is deprecated
        String result = "";
        if (Build.VERSION.SDK_INT >= 24) {
            result = Html.fromHtml(htmlString , Html.FROM_HTML_MODE_LEGACY).toString();
        }
        else {
            result = Html.fromHtml(htmlString).toString();
        }
        return result;
    }


    public static int getRandomColor(Context context){
        Random rnd = new Random();
        TypedArray colors = context.getResources().obtainTypedArray(R.array.colors);
        int color = colors.getColor(rnd.nextInt(colors.length()), 0);
        colors.recycle();
        return color;
    }

    public static void showMsg(Context context, String msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
    
}
