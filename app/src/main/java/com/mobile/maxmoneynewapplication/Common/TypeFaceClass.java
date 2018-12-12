package com.mobile.maxmoneynewapplication.Common;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class TypeFaceClass {

    public static void setTypeFaceEditText(EditText editText, Context context){
        editText.setTypeface(Typeface.createFromAsset(context.getAssets(), "Avenir-BookOblique-02.ttf"));
    }

    public static void setTypeFaceTextView(TextView textView, Context context){
        textView.setTypeface(Typeface.createFromAsset(context.getAssets(), "Avenir-BookOblique-02.ttf"));
    }
    public static void setTypeFaceButton(Button button, Context context){
        button.setTypeface(Typeface.createFromAsset(context.getAssets(), "Avenir-BookOblique-02.ttf"));
    }
}
