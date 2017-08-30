package com.yyk.retrofitrxandroid.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;

import com.yyk.retrofitrxandroid.R;

/**
 * Created by YYK on 2017/8/18.
 */

public class MaterialProgress extends android.support.v7.widget.AppCompatImageView {

    private MaterialProgressDrawable progress;
    private Paint mPaint;
    private static final int CIRCLE_DIAMETER = 40;
    private int color = 0xFF00DDFF;
    private DisplayMetrics metrics = getResources().getDisplayMetrics();

    public MaterialProgress(Context context) {
        super(context);
        initView(context);
    }

    public MaterialProgress(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public MaterialProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        setBackgroundResource(R.drawable.progress_bg);
        mPaint = new Paint();
        progress = new MaterialProgressDrawable(context, this);
        setImageDrawable(progress);
        progress.updateSizes(MaterialProgressDrawable.DEFAULT);
        progress.setColorSchemeColors(color);
        progress.setProgressRotation(0);
        progress.setAlpha(255);
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void startProgress() {
        progress.start();
    }

    public void stopProgress() {
        progress.stop();
    }
}
