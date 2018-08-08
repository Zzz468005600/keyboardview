package com.stone.keyboardview.model;

import java.util.List;

/**
 * Created by zhulei
 * 2018/8/7
 */
public class Keyboard {

    private int line;//行数
    private int column;//列数
    private float verticalMargin;//键之间的垂直距离
    private float horizontalMargin;//键之间的水平距离

    private List<Key> keys;//键

    public void setLine(int line) {
        this.line = line;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public void setVerticalMargin(float verticalMargin) {
        this.verticalMargin = verticalMargin;
    }

    public void setHorizontalMargin(float horizontalMargin) {
        this.horizontalMargin = horizontalMargin;
    }

    public void setKeys(List<Key> keys) {
        this.keys = keys;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    public float getVerticalMargin() {
        return verticalMargin;
    }

    public float getHorizontalMargin() {
        return horizontalMargin;
    }

    public List<Key> getKeys() {
        return keys;
    }
}
