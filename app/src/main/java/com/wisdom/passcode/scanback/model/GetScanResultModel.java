package com.wisdom.passcode.scanback.model;

import java.io.Serializable;

/**
 * @author HanXueFeng
 * @ProjectName project： Passcode
 * @class package：com.wisdom.passcode.scanback.model
 * @class describe：
 * @time 2020/6/1 0001 14:08
 * @change
 */
public class GetScanResultModel implements Serializable {

    private String type;//扫码类型
    //    1：人扫墙
// 2：管理员扫人码
// 3：管理员扫人通行证
// 4：管理员扫车通行证
// 5：管理员扫车牌号

    private String placeCodeEncryption;//场所id 标识
    private String userIdEncryption;//用户id 标识（除type=5 都传）
    private String passCodeId;//通行证id(人或车) （type=3或type=4时必传）
    private String carNumber;//车牌号 （5时必传）

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getPassCodeId() {
        return passCodeId;
    }

    public void setPassCodeId(String passCodeId) {
        this.passCodeId = passCodeId;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }
}
