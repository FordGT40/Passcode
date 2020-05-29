package com.wisdom.passcode.mine.model;

import java.io.Serializable;

/**
 * @author HanXueFeng
 * @ProjectName project： Passcode
 * @class package：com.wisdom.passcode.mine.model
 * @class describe：
 * @time 2020/5/29 0029 17:23
 * @change
 */
public class RealNameInfoDetailModel implements Serializable {


    /**
     * id : 205
     * idCard : 6xGXrYHiZaEhNJc0M46uMSSScLs9e5EB
     * idCardLastFour : 0012
     * realName : 翟峰
     * sex : 1
     * bornDate : 783964800000
     * administrativeDivision : 230826
     * address : 黑龙江省佳木斯市桦川县
     */

    private String id;
    private String idCard;
    private String idCardLastFour;
    private String realName;
    private String sex;
    private String bornDate;
    private String administrativeDivision;
    private String address;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getIdCardLastFour() {
        return idCardLastFour;
    }

    public void setIdCardLastFour(String idCardLastFour) {
        this.idCardLastFour = idCardLastFour;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBornDate() {
        return bornDate;
    }

    public void setBornDate(String bornDate) {
        this.bornDate = bornDate;
    }

    public String getAdministrativeDivision() {
        return administrativeDivision;
    }

    public void setAdministrativeDivision(String administrativeDivision) {
        this.administrativeDivision = administrativeDivision;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
