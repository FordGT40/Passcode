package com.wisdom.passcode.mine.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author HanXueFeng
 * @ProjectName project： Passcode
 * @class package：com.wisdom.passcode.mine.model
 * @class describe：
 * @time 2020/5/20 0020 14:13
 * @change
 */
public class PersonalInfoModel implements Serializable {


    /**
     * phonenumber : QMmw8ZL6Gjlt6El+6KJLVw==
     * loginDate : 1589788301000
     * channelType : 1
     * placeList : [{"deptName":"市场部","placeId":24,"placeName":"大发市场","userId":207,"scannerType":"2"}]
     * source : 1
     * delFlag : 0
     * type : 1
     * score : 1
     * password : $2a$10$3hgwzJ1nI.zz8ov0hGtvbuVK/3Dzd7hIRQ.Zsfkt1JU9DTENhSNhO
     * userIdEncryption : NTQ5MjFmMGJiM2EwNTdjZmUzNDk3OTk3MDQxZWI2OGJkNDZlMGE5NQ==
     * updateBy :
     * loginIp :
     * channelId : 3709272911446827591
     * email :
     * authState : 1
     * nickName : 韩雪峰
     * phoneLastFour : 1111
     * sex : 0
     * updateTime : 1589788301000
     * avatar :
     * isAdmin : false
     * userName : QMmw8ZL6Gjlt6El+6KJLVw==
     * userId : 207
     * createBy :
     * createTime : 1589788301000
     * userType : 00
     * status : 0
     */

    private String phonenumber;
    private String loginDate;
    private String channelType;
    private String source;
    private String delFlag;
    private String type;
    private String score;
    private String password;
    private String userIdEncryption;
    private String updateBy;
    private String loginIp;
    private String channelId;
    private String email;
    private String authState;
    private String nickName;
    private String phoneLastFour;
    private String sex;
    private String updateTime;
    private String avatar;
    private String isAdmin;
    private String userName;
    private String userId;
    private String createBy;
    private String createTime;
    private String userType;
    private String status;
    private List<PlaceListBean> placeList;

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(String loginDate) {
        this.loginDate = loginDate;
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserIdEncryption() {
        return userIdEncryption;
    }

    public void setUserIdEncryption(String userIdEncryption) {
        this.userIdEncryption = userIdEncryption;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAuthState() {
        return authState;
    }

    public void setAuthState(String authState) {
        this.authState = authState;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhoneLastFour() {
        return phoneLastFour;
    }

    public void setPhoneLastFour(String phoneLastFour) {
        this.phoneLastFour = phoneLastFour;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(String isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<PlaceListBean> getPlaceList() {
        return placeList;
    }

    public void setPlaceList(List<PlaceListBean> placeList) {
        this.placeList = placeList;
    }

    public static class PlaceListBean implements Serializable {
        /**
         * deptName : 市场部
         * placeId : 24
         * placeName : 大发市场
         * userId : 207
         * scannerType : 2
         */

        private String deptName;
        private String placeId;
        private String placeName;
        private String userId;
        private String scannerType;
        private String postName;
        private List<PlaceCodeBean> placeCode;

        public List<PlaceCodeBean> getPlaceCode() {
            return placeCode;
        }

        public void setPlaceCode(List<PlaceCodeBean> placeCode) {
            this.placeCode = placeCode;
        }

        public String getPostName() {
            return postName;
        }

        public void setPostName(String postName) {
            this.postName = postName;
        }

        public String getDeptName() {
            return deptName;
        }

        public void setDeptName(String deptName) {
            this.deptName = deptName;
        }

        public String getPlaceId() {
            return placeId;
        }

        public void setPlaceId(String placeId) {
            this.placeId = placeId;
        }

        public String getPlaceName() {
            return placeName;
        }

        public void setPlaceName(String placeName) {
            this.placeName = placeName;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getScannerType() {
            return scannerType;
        }

        public void setScannerType(String scannerType) {
            this.scannerType = scannerType;
        }
    }


    public static class PlaceCodeBean implements Serializable {
        private String id;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
