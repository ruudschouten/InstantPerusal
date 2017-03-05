package com.blappole.instantperusal;

import android.graphics.Typeface;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;


public class Utils {

    public static SpannableStringBuilder GetStyledString(String text, String textToStyle) {
        return GetStyledString(text, textToStyle, new StyleSpan(Typeface.BOLD));
    }

    public static SpannableStringBuilder GetStyledString(String text, String textToStyle, StyleSpan style) {
        SpannableStringBuilder builder = new SpannableStringBuilder(text);
        int startIndex = text.indexOf(textToStyle);
        int endIndex = startIndex + textToStyle.length();
        builder.setSpan(style, startIndex, endIndex, 0);
        return builder;
    }
}
