package com.stone.keyboardview.model;

import android.graphics.RectF;
import android.support.annotation.DrawableRes;

/**
 * Created by zhulei
 * 2018/8/6
 */
public class Key {

    //位置信息
    private RectF rectF;

    //样式信息
    private int defaultBgColor;
    private int pressBgColor;
    private float cornerRadius;

    //文字图片信息及其外形
    private String text;
    private float textSize;
    private int textColor;
    private int drawable;

    //键类型
    private Action action;

    private boolean pressed;

    /**
     * 用来表示键的类型
     * 目前又三种：普通按键，全部清除按键，删除一个字符按键
     */
    public enum Action {
        //普通按键
        NORMAL,
        //全部清除按键
        CLEAR,
        //删除一个字符按键
        DELETE
    }

    public RectF getRectF() {
        return rectF;
    }

    public void setRectF(RectF rectF) {
        this.rectF = rectF;
    }

    public int getDefaultBgColor() {
        return defaultBgColor;
    }

    public void setDefaultBgColor(int defaultBgColor) {
        this.defaultBgColor = defaultBgColor;
    }

    public int getPressBgColor() {
        return pressBgColor;
    }

    public void setPressBgColor(int pressBgColor) {
        this.pressBgColor = pressBgColor;
    }

    public float getCornerRadius() {
        return cornerRadius;
    }

    public void setCornerRadius(float cornerRadius) {
        this.cornerRadius = cornerRadius;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public boolean isPressed() {
        return pressed;
    }

    public void setPressed(boolean pressed) {
        this.pressed = pressed;
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(@DrawableRes int drawable) {
        this.drawable = drawable;
    }
}
