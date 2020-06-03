package com.wisdom.passcode.mine.model;

import java.io.Serializable;

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
     * nickName : 9372
     * sex : 0
     * avatar :
     * authState : 1
     * channelId : null
     * phonenumber : wglwkTFXEqxbw35IAsJqyw==
     * userName : wglwkTFXEqxbw35IAsJqyw==
     */

    private String nickName;
    private String sex;
    private String avatar;
    private String authState;
    private Object channelId;
    private String phonenumber;
    private String userName;
    private String isAdmin;
    private String passCodeSort;
    private String score;

    public String getPassCodeSort() {
        return passCodeSort;
    }

    public void setPassCodeSort(String passCodeSort) {
        this.passCodeSort = passCodeSort;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(String isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAuthState() {
        return authState;
    }

    public void setAuthState(String authState) {
        this.authState = authState;
    }

    public Object getChannelId() {
        return channelId;
    }

    public void setChannelId(Object channelId) {
        this.channelId = channelId;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
