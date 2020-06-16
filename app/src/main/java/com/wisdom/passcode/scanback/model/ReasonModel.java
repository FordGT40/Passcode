package com.wisdom.passcode.scanback.model;

import java.io.Serializable;

/**
 * @author HanXueFeng
 * @ProjectName project： Passcode
 * @class package：com.wisdom.passcode.scanback.model
 * @class describe：
 * @time 2020/6/16 0016 09:23
 * @change
 */
public class ReasonModel implements Serializable {

    /**
     * sort : 21
     * text : 有事找领导
     */

    private int sort;
    private String text;

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
