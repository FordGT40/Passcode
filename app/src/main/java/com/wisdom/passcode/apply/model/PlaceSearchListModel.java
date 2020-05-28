package com.wisdom.passcode.apply.model;

import java.io.Serializable;

/**
 * @author HanXueFeng
 * @ProjectName project： Passcode
 * @class package：com.wisdom.passcode.apply.model
 * @class describe：
 * @time 2020/5/28 0028 13:17
 * @change
 */
public class PlaceSearchListModel implements Serializable {


    /**
     * name : 春天幼儿园
     * placeIdEncryption : ZmQ3ZWI4YjNjYjBhYmZlZjg2MjY0NGY5MDRkMThhZmY1YmEwZDJiNw==
     */

    private String name;
    private String placeIdEncryption;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlaceIdEncryption() {
        return placeIdEncryption;
    }

    public void setPlaceIdEncryption(String placeIdEncryption) {
        this.placeIdEncryption = placeIdEncryption;
    }
}
