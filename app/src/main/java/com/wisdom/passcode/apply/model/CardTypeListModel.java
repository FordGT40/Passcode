package com.wisdom.passcode.apply.model;

import java.io.Serializable;

/**
 * @author HanXueFeng
 * @ProjectName project： Passcode
 * @class package：com.wisdom.passcode.apply.model
 * @class describe：
 * @time 2020/5/28 0028 15:00
 * @change
 */
public class CardTypeListModel implements Serializable {

    /**
     * dataType : 2
     * placeId : 19
     * name : 长期
     * lable : 一年
     * id : 7
     * type : 1
     * timeRange : 07:00~24:00
     */

    private String dataType;
    private String placeId;
    private String name;
    private String lable;
    private String id;
    private String type;
    private String timeRange;

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLable() {
        return lable;
    }

    public void setLable(String lable) {
        this.lable = lable;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTimeRange() {
        return timeRange;
    }

    public void setTimeRange(String timeRange) {
        this.timeRange = timeRange;
    }
}
