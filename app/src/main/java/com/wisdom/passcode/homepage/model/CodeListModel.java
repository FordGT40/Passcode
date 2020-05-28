package com.wisdom.passcode.homepage.model;

import java.io.Serializable;

/**
 * @author HanXueFeng
 * @ProjectName project： Passcode
 * @class package：com.wisdom.passcode.homepage.model
 * @class describe：
 * @time 2020/5/9 0009 10:05
 * @change
 */
public class CodeListModel implements Serializable {
    public String getIsDateOf() {
        return isDateOf;
    }

    public void setIsDateOf(String isDateOf) {
        this.isDateOf = isDateOf;
    }

    private String isDateOf;

}
