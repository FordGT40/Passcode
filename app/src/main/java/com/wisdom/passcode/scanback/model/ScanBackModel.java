package com.wisdom.passcode.scanback.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author HanXueFeng
 * @ProjectName project： Passcode
 * @class package：com.wisdom.passcode.scanback.model
 * @class describe：
 * @time 2020/6/9 0009 16:32
 * @change
 */
public class ScanBackModel implements Serializable {

    /**
     * black : false
     * agree : true
     * auth : false
     * pushData : {"visitorsUserId":205,"visitorsUserPhone":"wglwkTFXEqxbw35IAsJqyw==","userName":"9372","type":"1","userId":205,"placeCodeId":12,"pushType":1,"adminName":"9372","visitorsUserDept":"自新部","placeCodeName":"大发市场正门","visitorsUserName":"翟峰","logId":29,"remarks":"测试进门"}
     */

    private String black;
    private String agree;
    private String auth;
    private PushDataBean pushData;
    private List<PlaceInfomationBean> placeInfomation;
    private List<PlaceInfomationUserBean> placeInfomationUser;

    public String getBlack() {
        return black;
    }

    public void setBlack(String black) {
        this.black = black;
    }

    public String getAgree() {
        return agree;
    }

    public void setAgree(String agree) {
        this.agree = agree;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public PushDataBean getPushData() {
        return pushData;
    }

    public void setPushData(PushDataBean pushData) {
        this.pushData = pushData;
    }

    public List<PlaceInfomationBean> getPlaceInfomation() {
        return placeInfomation;
    }

    public void setPlaceInfomation(List<PlaceInfomationBean> placeInfomation) {
        this.placeInfomation = placeInfomation;
    }

    public List<PlaceInfomationUserBean> getPlaceInfomationUser() {
        return placeInfomationUser;
    }

    public void setPlaceInfomationUser(List<PlaceInfomationUserBean> placeInfomationUser) {
        this.placeInfomationUser = placeInfomationUser;
    }

    public static class PushDataBean implements Serializable {
        /**
         * visitorsUserId : 205
         * visitorsUserPhone : wglwkTFXEqxbw35IAsJqyw==
         * userName : 9372
         * type : 1
         * userId : 205
         * placeCodeId : 12
         * pushType : 1
         * adminName : 9372
         * visitorsUserDept : 自新部
         * placeCodeName : 大发市场正门
         * visitorsUserName : 翟峰
         * logId : 29
         * remarks : 测试进门
         */

        private String visitorsUserId;
        private String visitorsUserPhone;
        private String userName;
        private String type;
        private String userId;
        private String placeCodeId;
        private String pushType;
        private String adminName;
        private String visitorsUserDept;
        private String placeCodeName;
        private String visitorsUserName;
        private String logId;
        private String remarks;
        private String agree;//被拜访人是否同意 1：同意 2：不同意
        private String rejectReason;//拒绝原因
        private String scanDate;//扫码时间（毫秒时间戳）
        private String userPhone;//用户手机号（传输加密）

        public String getScanDate() {
            return scanDate;
        }

        public void setScanDate(String scanDate) {
            this.scanDate = scanDate;
        }

        public String getUserPhone() {
            return userPhone;
        }

        public void setUserPhone(String userPhone) {
            this.userPhone = userPhone;
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

        public String getPushType() {
            return pushType;
        }

        public void setPushType(String pushType) {
            this.pushType = pushType;
        }

        public String getAdminName() {
            return adminName;
        }

        public void setAdminName(String adminName) {
            this.adminName = adminName;
        }

        public String getVisitorsUserDept() {
            return visitorsUserDept;
        }

        public void setVisitorsUserDept(String visitorsUserDept) {
            this.visitorsUserDept = visitorsUserDept;
        }

        public String getPlaceCodeName() {
            return placeCodeName;
        }

        public void setPlaceCodeName(String placeCodeName) {
            this.placeCodeName = placeCodeName;
        }

        public String getVisitorsUserName() {
            return visitorsUserName;
        }

        public void setVisitorsUserName(String visitorsUserName) {
            this.visitorsUserName = visitorsUserName;
        }

        public String getLogId() {
            return logId;
        }

        public void setLogId(String logId) {
            this.logId = logId;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }
    }

    public static class PlaceInfomationBean implements Serializable {
        /**
         * title : 场所信息1
         * content : 产股搜信息内容1
         * reportUserName : 9372
         */

        private String title;
        private String content;
        private String reportUserName;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getReportUserName() {
            return reportUserName;
        }

        public void setReportUserName(String reportUserName) {
            this.reportUserName = reportUserName;
        }
    }

    public static class PlaceInfomationUserBean implements Serializable {
        /**
         * title : 人员信息1
         * content : 人员信息内容1
         * reportUserName : 9372
         */

        private String title;
        private String content;
        private String reportUserName;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getReportUserName() {
            return reportUserName;
        }

        public void setReportUserName(String reportUserName) {
            this.reportUserName = reportUserName;
        }
    }
}
