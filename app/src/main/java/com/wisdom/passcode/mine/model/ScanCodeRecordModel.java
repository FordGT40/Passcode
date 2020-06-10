package com.wisdom.passcode.mine.model;

import java.io.Serializable;

/**
 * @author HanXueFeng
 * @ProjectName project： Passcode
 * @class package：com.wisdom.passcode.mine.model
 * @class describe：
 * @time 2020/6/10 0010 11:02
 * @change
 */
public class ScanCodeRecordModel implements Serializable {
    private String actionType;//操作类型 1：身为管理员扫他人码 2：身为用户扫场所码

    private String recoedType;//记录类型 1：人员 2：车辆

    private String scanDate;//扫码日期

    private String placeCodeId;//场所码id

    private String placeName;//场所码名称

    private String type;//类型1：入内2：外出3：经过

    private String adminId;//管理员id（没有则null）
//    // （当actionType=1时，userId为被你扫码得用户id，adminId为你的用户id）
//            （当actionType=2时，userId为你的用户id）

    private String adminName;//管理员昵称

    private String userId;//用户id
//（当actionType=1时，userId为被你扫码得用户id，adminId为你的用户id）
//            （当actionType=2时，userId为你的用户id）

    private String remarks;//扫码事由

    private String visitorsUserId;//拜访人id

    private String visitorsUserName;//拜访人姓名

    private String visitorsUserPhone;//拜访人手机号

    private String visitorsUserDept;//拜访人部门

    private String scanType;//扫码类型1：扫码  2 ：通行证

    private String agree;//拜访人是否同意1：同意 2：不同意

    private String rejectReason;//拒绝原因

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getRecoedType() {
        return recoedType;
    }

    public void setRecoedType(String recoedType) {
        this.recoedType = recoedType;
    }

    public String getScanDate() {
        return scanDate;
    }

    public void setScanDate(String scanDate) {
        this.scanDate = scanDate;
    }

    public String getPlaceCodeId() {
        return placeCodeId;
    }

    public void setPlaceCodeId(String placeCodeId) {
        this.placeCodeId = placeCodeId;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getVisitorsUserId() {
        return visitorsUserId;
    }

    public void setVisitorsUserId(String visitorsUserId) {
        this.visitorsUserId = visitorsUserId;
    }

    public String getVisitorsUserName() {
        return visitorsUserName;
    }

    public void setVisitorsUserName(String visitorsUserName) {
        this.visitorsUserName = visitorsUserName;
    }

    public String getVisitorsUserPhone() {
        return visitorsUserPhone;
    }

    public void setVisitorsUserPhone(String visitorsUserPhone) {
        this.visitorsUserPhone = visitorsUserPhone;
    }

    public String getVisitorsUserDept() {
        return visitorsUserDept;
    }

    public void setVisitorsUserDept(String visitorsUserDept) {
        this.visitorsUserDept = visitorsUserDept;
    }

    public String getScanType() {
        return scanType;
    }

    public void setScanType(String scanType) {
        this.scanType = scanType;
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
}
