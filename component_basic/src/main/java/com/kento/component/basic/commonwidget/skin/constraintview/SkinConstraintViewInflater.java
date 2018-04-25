package com.kento.component.basic.commonwidget.skin.constraintview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;

import skin.support.app.SkinLayoutInflater;

public class SkinConstraintViewInflater implements SkinLayoutInflater {
    @Override
    public View createView( @NonNull Context context, final String name, @NonNull AttributeSet attrs) {
        View view = null;
        switch (name) {
            case "android.support.constraint.ConstraintLayout":
                view = new SkinCompatConstraintLayout(context, attrs);
                break;
        }
        return view;
    }
}
