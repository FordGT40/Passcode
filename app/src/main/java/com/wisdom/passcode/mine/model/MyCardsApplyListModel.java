package com.wisdom.passcode.mine.model;

import java.io.Serializable;

/**
 * @author HanXueFeng
 * @ProjectName project： Passcode
 * @class package：com.wisdom.passcode.mine.model
 * @class describe：
 * @time 2020/6/2 0002 15:29
 * @change
 */
public class MyCardsApplyListModel implements Serializable {


    /**
     * reason : 我要进去，谁敢管劳资
     * codeTypeName : 长期
     * advanceTime : 37
     * placeId : 24
     * currentPlace : 1
     * auditStatus : 2
     * codeTypeDataType : 2
     * id : 2
     * codeTypeLable : 一年
     * codeTypeTimeRange : 07:00~24:00
     * placeName : 大发市场
     * createDate : 1590716949000
     */

    private String reason;
    private String rejectReason;
    private String codeTypeName;
    private int advanceTime;
    private int placeId;
    private String currentPlace;
    private String auditStatus;
    private String codeTypeDataType;
    private int id;
    private String codeTypeLable;
    private String codeTypeTimeRange;
    private String placeName;
    private long createDate;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getCodeTypeName() {
        return codeTypeName;
    }

    public void setCodeTypeName(String codeTypeName) {
        this.codeTypeName = codeTypeName;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public int getAdvanceTime() {
        return advanceTime;
    }

    public void setAdvanceTime(int advanceTime) {
        this.advanceTime = advanceTime;
    }

    public int getPlaceId() {
        return placeId;
    }

    public void setPlaceId(int placeId) {
        this.placeId = placeId;
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

    public String getCodeTypeDataType() {
        return codeTypeDataType;
    }

    public void setCodeTypeDataType(String codeTypeDataType) {
        this.codeTypeDataType = codeTypeDataType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }
}
