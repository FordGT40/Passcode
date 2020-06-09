package com.wisdom.passcode.homepage.model;

import java.io.Serializable;

/**
 * @author HanXueFeng
 * @ProjectName project： Passcode
 * @class package：com.wisdom.passcode.homepage.model
 * @class describe：
 * @time 2020/6/9 0009 16:00
 * @change
 */
public class ShowOrScanCodeRecordModel implements Serializable {


    /**
     * visitorsUserId : 205
     * recoedType : 1
     * visitorsUserPhone : wglwkTFXEqxbw35IAsJqyw==
     * userName : 9372
     * type : 1
     * userId : 205
     * placeCodeId : 12
     * scanDate : 1590742017000
     * visitorsUserDept : 自新部
     * visitorsUserName : 翟峰
     * scanType : 1
     * placeName : 大发市场正门
     * remarks : 测试进门
     */

    private String visitorsUserId;
    private String recoedType;
    private String visitorsUserPhone;
    private String userName;
    private String type;
    private String userId;
    private String placeCodeId;
    private String scanDate;
    private String visitorsUserDept;
    private String visitorsUserName;
    private String scanType;
    private String placeName;
    private String remarks;
    private String adminId;
    private String adminName;
    private String agree;
    private String rejectReason;

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAgree() {
        return agree;
    }

    public void setAgree(String agree) {
        this.agree = agree;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public String getVisitorsUserId() {
        return visitorsUserId;
    }

    public void setVisitorsUserId(String visitorsUserId) {
        this.visitorsUserId = visitorsUserId;
    }

    public String getRecoedType() {
        return recoedType;
    }

    public void setRecoedType(String recoedType) {
        this.recoedType = recoedType;
    }

    public String getVisitorsUserPhone() {
        return visitorsUserPhone;
    }

    public void setVisitorsUserPhone(String visitorsUserPhone) {
        this.visitorsUserPhone = visitorsUserPhone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPlaceCodeId() {
        return placeCodeId;
    }

    public void setPlaceCodeId(String placeCodeId) {
        this.placeCodeId = placeCodeId;
    }

    public String getScanDate() {
        return scanDate;
    }

    public void setScanDate(String scanDate) {
        this.scanDate = scanDate;
    }

    public String getVisitorsUserDept() {
        return visitorsUserDept;
    }

    public void setVisitorsUserDept(String visitorsUserDept) {
        this.visitorsUserDept = visitorsUserDept;
    }

    public String getVisitorsUserName() {
        return visitorsUserName;
    }

    public void setVisitorsUserName(String visitorsUserName) {
        this.visitorsUserName = visitorsUserName;
    }

    public String getScanType() {
        return scanType;
    }

    public void setScanType(String scanType) {
        this.scanType = scanType;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

}
