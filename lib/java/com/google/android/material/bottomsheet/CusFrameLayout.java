package com.google.android.material.bottomsheet;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CusFrameLayout extends FrameLayout {
  public CusFrameLayout(@NonNull Context context) {
    super(context);
  }

  public CusFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public CusFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override
  public void setBackground(Drawable background) {
    super.setBackground(background);
    if (background instanceof ColorDrawable) {
      try {
        throw new RuntimeException("===========调用栈==================");
      } catch (RuntimeException e) {
        e.printStackTrace();
      }
    }
    Log.i("CusFrameLayout", "=============setBackground===================" + background);
  }

  @Override
  public void setBackgroundColor(int color) {
    super.setBackgroundColor(color);
    String hexColor = String.format("#%06X", 0xFFFFFF & color);

    Log.i("CusFrameLayout", "=============setBackgroundColor===================" + hexColor);
  }



  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    Log.i("CusFrameLayout", "=============onMeasure===================");
  }

  @Override
  protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
    super.onLayout(changed, left, top, right, bottom);
    Log.i("CusFrameLayout", "=============onLayout===================");
  }

  @Override
  protected void onDraw(@NonNull Canvas canvas) {
    super.onDraw(canvas);
    Log.i("CusFrameLayout", "=============onDraw===================");
  }
}
