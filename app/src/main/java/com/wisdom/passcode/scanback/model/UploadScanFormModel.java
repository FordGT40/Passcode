package com.wisdom.passcode.scanback.model;

import java.io.Serializable;

/**
 * @author HanXueFeng
 * @ProjectName project： Passcode
 * @class package：com.wisdom.passcode.scanback.model
 * @class describe：
 * @time 2020/6/1 0001 15:12
 * @change
 */
public class UploadScanFormModel implements Serializable {
    private String type;//出入类型  1：进入  // 2：离开
    private String scanType;//扫码类型   1：扫码    2：通行证
    private String placeCodeEncryption;//场所id 标识
    private String userIdEncryption;//用户id 标识
    private String adminEncryption;//扫码人员加密标识
    private String remarks;//扫码原因
    private String visitorsUserName;//访问人姓名
    private String visitorsUserPhone;//访问人手机号
    private String visitorsUserDept;//访问人部门名称
    private String carNumber;//车牌号


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getScanType() {
        return scanType;
    }

    public void setScanType(String scanType) {
        this.scanType = scanType;
    }

    public String getPlaceCodeEncryption() {
        return placeCodeEncryption;
    }

    public void setPlaceCodeEncryption(String placeCodeEncryption) {
        this.placeCodeEncryption = placeCodeEncryption;
    }

    public String getUserIdEncryption() {
        return userIdEncryption;
    }

    public void setUserIdEncryption(String userIdEncryption) {
        this.userIdEncryption = userIdEncryption;
    }

    public String getAdminEncryption() {
        return adminEncryption;
    }

    public void setAdminEncryption(String adminEncryption) {
        this.adminEncryption = adminEncryption;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }
}
