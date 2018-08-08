package com.stone.keyboardview.model;

import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;

/**
 * 对外给用户
 * Created by zhulei
 * 2018/8/7
 */
public class KeyModel {

    private String text;//键上的文字
    private int drawable;//图片资源
    private Key.Action action;//行为

    public KeyModel(@Nullable String text, Key.Action action) {
        this.action = action;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Key.Action getAction() {
        return action;
    }

    public void setAction(Key.Action action) {
        this.action = action;
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(@DrawableRes int drawable) {
        this.drawable = drawable;
    }
}
