package com.rahul.instaimgmap.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rahul.instaimgmap.R;


/**
 * Created by rahul on 25/11/17
 */

public class LabelTextView extends LinearLayout {

    private Context mContext;
    private TextView tvLabel, tvValue;

    public LabelTextView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public LabelTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init(mContext, attrs);
    }

    void init() {
        tvLabel = getTextView(false, "");
        tvValue = getTextView(false, "");
        LinearLayout view = getContainer(false, tvLabel, tvValue, false);
        addView(view);
    }

    void init(Context context, AttributeSet attrs) {
        if (attrs == null) return;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LabelTextView);
        boolean isVertical = typedArray.getBoolean(R.styleable.LabelTextView_isVertical, false);
        String label = typedArray.getString(R.styleable.LabelTextView_label);
        String value = typedArray.getString(R.styleable.LabelTextView_value);
        boolean isLabelBold = typedArray.getBoolean(R.styleable.LabelTextView_isLabelBold, false);
        boolean isValueBold = typedArray.getBoolean(R.styleable.LabelTextView_isValueBold, false);
        boolean isGravityCenter = typedArray.getBoolean(R.styleable.LabelTextView_isGravityCenter, true);
        typedArray.recycle();


        tvLabel = getTextView(isLabelBold, label);
        setTvLabel(tvLabel);
        tvValue = getTextView(isValueBold, value);
        setTvValue(tvValue);
        LinearLayout view = getContainer(isVertical, tvLabel, tvValue, isGravityCenter);
        addView(view);
    }

    private LinearLayout getContainer(boolean isVertical, TextView label, TextView value, boolean isGravityCenter) {
        LinearLayout parent = new LinearLayout(mContext);
        parent.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        parent.setOrientation(isVertical ? VERTICAL : HORIZONTAL);
        parent.addView(label);
        parent.addView(value);
        if (isGravityCenter) parent.setGravity(Gravity.CENTER);
        return parent;
    }

    private TextView getTextView(boolean isBold, String text) {
        TextView textView = new TextView(mContext);
        textView.setText(text);
        if (isBold) textView.setTypeface(null, Typeface.BOLD);

        textView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        return textView;
    }

    public TextView getTvLabel() {
        return tvLabel;
    }

    public void setTvLabel(TextView tvLabel) {
        this.tvLabel = tvLabel;
    }

    public TextView getTvValue() {
        return tvValue;
    }

    public void setTvValue(TextView tvValue) {
        this.tvValue = tvValue;
    }
}
