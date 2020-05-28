package com.wisdom.passcode.mine.model;

/**
 * @author HanXueFeng
 * @ProjectName project： Passcode
 * @class package：com.wisdom.passcode.mine
 * @class describe：
 * @time 2020/5/7 0007 17:01
 * @change
 */
public class ApplyRecordModel {
    private String name;
    private String date;
    private String state;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
