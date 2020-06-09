package com.wisdom.passcode.scanback.model;

import java.io.Serializable;

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

    public static class PushDataBean {
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
}
