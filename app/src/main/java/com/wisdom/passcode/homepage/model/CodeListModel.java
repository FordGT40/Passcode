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


    /**
     * codeTypeName : 长期
     * advanceTime : 37
     * placeId : 24
     * codeTypeDataType : 2
     * codeTypeLable : 一年
     * codeTypeTimeRange : 07:00~24:00
     * carNumber : 黑A789456
     * expireTime : null
     * currentPlace : 1
     * auditStatus : 1
     * id : 1
     * placeName : 大发市场
     * logoApp : null
     */

    private String codeTypeName;
    private String advanceTime;
    private String placeId;
    private String codeTypeDataType;
    private String codeTypeLable;
    private String codeTypeTimeRange;
    private String carNumber;
    private String expireTime;
    private String currentPlace;
    private String auditStatus;
    private String id;
    private String placeName;
    private String logoApp;

    public String getCodeTypeName() {
        return codeTypeName;
    }

    public void setCodeTypeName(String codeTypeName) {
        this.codeTypeName = codeTypeName;
    }

    public String getAdvanceTime() {
        return advanceTime;
    }

    public void setAdvanceTime(String advanceTime) {
        this.advanceTime = advanceTime;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getCodeTypeDataType() {
        return codeTypeDataType;
    }

    public void setCodeTypeDataType(String codeTypeDataType) {
        this.codeTypeDataType = codeTypeDataType;
    }

    public String getCodeTypeLable() {
        return codeTypeLable;
    }

    public void setCodeTypeLable(String codeTypeLable) {
        this.codeTypeLable = codeTypeLable;
    }

    public String getCodeTypeTimeRange() {
        return codeTypeTimeRange;
    }

    public void setCodeTypeTimeRange(String codeTypeTimeRange) {
        this.codeTypeTimeRange = codeTypeTimeRange;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    public String getCurrentPlace() {
        return currentPlace;
    }

    public void setCurrentPlace(String currentPlace) {
        this.currentPlace = currentPlace;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getLogoApp() {
        return logoApp;
    }

    public void setLogoApp(String logoApp) {
        this.logoApp = logoApp;
    }
}
