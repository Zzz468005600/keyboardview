package com.stone.keyboardview.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.stone.keyboardview.R;
import com.stone.keyboardview.model.Key;
import com.stone.keyboardview.model.Keyboard;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhulei
 * 2018/8/6
 */
public class KeyboardView extends View {

    public KeyboardView(Context context) {
        super(context);
        init(context, null);
    }

    public KeyboardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mKeyboard = new Keyboard();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        if (attrs == null) {
            return;
        }

        TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.KeyboardView);
        int line = arr.getInt(R.styleable.KeyboardView_kv_line, 4);
        int column = arr.getInt(R.styleable.KeyboardView_kv_column, 3);
        float verticalMargin = arr.getDimension(R.styleable.KeyboardView_kv_vertical_margin, dp2px(5));
        float horizontalMargin = arr.getDimension(R.styleable.KeyboardView_kv_horizontal_margin, dp2px(5));

        CharSequence[] keyTextArr = arr.getTextArray(R.styleable.KeyboardView_kv_keys);
        CharSequence[] keyActions = arr.getTextArray(R.styleable.KeyboardView_kv_actions);
        if (keyTextArr == null || keyTextArr.length == 0 || keyTextArr.length != line * column) {
            line = 4;
            column = 3;
            keyTextArr = DEFAULT_KEYS;
        }
        if (keyActions == null || keyActions.length == 0 || keyActions.length != keyTextArr.length) {
            keyActions = DEFAULT_ACTIONS;
        }
        mKeyboard.setLine(line);
        mKeyboard.setColumn(column);
        mKeyboard.setVerticalMargin(verticalMargin);
        mKeyboard.setHorizontalMargin(horizontalMargin);

        float cornerRaduis = arr.getDimension(R.styleable.KeyboardView_kv_key_corner_radius, dp2px(4));
        int keyDefaultColor = arr.getColor(R.styleable.KeyboardView_kv_key_default_color, getResources().getColor(R.color.key_default_color));
        int keyPressColor = arr.getColor(R.styleable.KeyboardView_kv_key_press_color, getResources().getColor(R.color.key_press_color));
        float keyTextSize = arr.getDimension(R.styleable.KeyboardView_kv_key_text_size, getResources().getDimension(R.dimen.font_text));
        int keyTextColor = arr.getColor(R.styleable.KeyboardView_kv_key_text_color, getResources().getColor(R.color.color_text));
        List<Key> keys = new ArrayList<>();
        for (int i = 0; i < line * column; i++) {
            Key key = new Key();
            RectF rectF = new RectF();
            key.setRectF(rectF);
            key.setCornerRadius(cornerRaduis);
            key.setText((String) keyTextArr[i]);
            key.setTextColor(keyTextColor);
            key.setTextSize(keyTextSize);
            key.setAction(getAction((String) keyActions[i]));
            key.setDefaultBgColor(keyDefaultColor);
            key.setPressBgColor(keyPressColor);
            keys.add(key);
        }
        mKeyboard.setKeys(keys);

        deleteBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_delete);

        arr.recycle();
    }

    private OnKeyClickListener mListener;

    private Paint mPaint;
    private Keyboard mKeyboard;

    private Bitmap deleteBitmap;
    private String[] DEFAULT_KEYS = getResources().getStringArray(R.array.keys);
    private String[] DEFAULT_ACTIONS = getResources().getStringArray(R.array.actions);

    public void setOnKeyClickListener(@Nullable OnKeyClickListener l) {
        this.mListener = l;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int lines = mKeyboard.getLine();//行
        int columns = mKeyboard.getColumn();//列
        float horizontalMargin = mKeyboard.getHorizontalMargin();//水平间隙
        float verticalMargin = mKeyboard.getVerticalMargin();//垂直间隙

        float keyWidth = (getWidth() - horizontalMargin * (columns - 1)) / columns;
        float keyHeight = (getHeight() - verticalMargin * (lines - 1)) / lines;

        List<Key> keys = mKeyboard.getKeys();
        for (int i = 0; i < keys.size(); i++) {
            float left = (i % columns) * (keyWidth + horizontalMargin);
            float top = (keyHeight + verticalMargin) * (i / columns);

            Key key = keys.get(i);
            RectF rectF = key.getRectF();
            rectF.left = left;
            rectF.top = top;
            rectF.right = left + keyWidth;
            rectF.bottom = top + keyHeight;

            //绘图
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setColor(key.isPressed() ? key.getPressBgColor() : key.getDefaultBgColor());
            canvas.drawRoundRect(rectF, key.getCornerRadius(), key.getCornerRadius(), mPaint);

            //画文字
            mPaint.setColor(key.getTextColor());
            mPaint.setTextSize(key.getTextSize());
            mPaint.setTextAlign(Paint.Align.CENTER);
            Paint.FontMetrics fm = mPaint.getFontMetrics();
            float textHeight = fm.bottom + fm.top;
            if (key.getAction() == Key.Action.DELETE) {
                canvas.drawBitmap(deleteBitmap, rectF.centerX() - deleteBitmap.getWidth() / 2, rectF.centerY() - deleteBitmap.getHeight() / 2, mPaint);
            } else {
                canvas.drawText(key.getText(), rectF.centerX(), rectF.centerY() - textHeight / 2, mPaint);
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                Key downKey = getSelectKey(x, y);
                if (downKey != null) {
                    downKey.setPressed(true);
                }
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                for (Key key : mKeyboard.getKeys()) {
                    key.setPressed(false);
                }
                invalidate();

                Key upKey = getSelectKey(x, y);
                if (upKey == null) {
                    return true;
                }
                if (mListener == null) {
                    return true;
                }
                mListener.onKeyClicked(upKey.getText(), upKey.getAction());
                break;
        }
        return true;
    }

    private Key getSelectKey(float x, float y) {
        if (x + getLeft() < getRight() && y + getTop() < getBottom()) {
            for (Key key : mKeyboard.getKeys()) {
                RectF rectF = key.getRectF();
                float left = rectF.left;
                float right = rectF.right;
                float top = rectF.top;
                float bottom = rectF.bottom;
                if (x > left && x < right && y > top && y < bottom) {
                    return key;
                }
            }
        }
        return null;
    }

    private Key.Action getAction(String action) {
        Key.Action resAction = Key.Action.NORMAL;
        switch (action) {
            case "clear":
                resAction = Key.Action.CLEAR;
                break;
            case "delete":
                resAction = Key.Action.DELETE;
                break;
        }
        return resAction;
    }

    private int dp2px(final float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public interface OnKeyClickListener {
        void onKeyClicked(String key, Key.Action action);
    }

}
