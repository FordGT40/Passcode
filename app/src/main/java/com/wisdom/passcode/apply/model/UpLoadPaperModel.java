package com.wisdom.passcode.apply.model;

import java.io.Serializable;

/**
 * @author HanXueFeng
 * @ProjectName project： Passcode
 * @class package：com.wisdom.passcode.apply.model
 * @class describe：
 * @time 2020/5/25 0025 14:18
 * @change
 */
public class UpLoadPaperModel implements Serializable {
    private String  type;//场所类型 1：政府2：企业3：其他组织
    private String  name;//场所名称
    private String  uscc;//统一信用代码
    private String  province;//所在省 （地市接口ID）
    private String  city;//所在城市（地市接口ID）
    private String  prefecture;//所在区县（地市接口ID）
    private String  detailedAddress;//详细地址
    private String  legal;//法人姓名（企业必传）
    private String  legalIdCard;//法人身份证号（企业必传）
    private String  accountSliceType;//证件类型1：多证合一营业执照2：普通营业执照3：事业单位法人证书或统一社会信用代码证书4：普通组织机构代码证
    private String  licenseImgFile;//营业执照图片文件(不参与签名算法)
    private String  applyLetterFile;//申请公函图片文件(不参与签名算法)

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUscc() {
        return uscc;
    }

    public void setUscc(String uscc) {
        this.uscc = uscc;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPrefecture() {
        return prefecture;
    }

    public void setPrefecture(String prefecture) {
        this.prefecture = prefecture;
    }

    public String getDetailedAddress() {
        return detailedAddress;
    }

    public void setDetailedAddress(String detailedAddress) {
        this.detailedAddress = detailedAddress;
    }

    public String getLegal() {
        return legal;
    }

    public void setLegal(String legal) {
        this.legal = legal;
    }

    public String getLegalIdCard() {
        return legalIdCard;
    }

    public void setLegalIdCard(String legalIdCard) {
        this.legalIdCard = legalIdCard;
    }

    public String getAccountSliceType() {
        return accountSliceType;
    }

    public void setAccountSliceType(String accountSliceType) {
        this.accountSliceType = accountSliceType;
    }

    public String getLicenseImgFile() {
        return licenseImgFile;
    }

    public void setLicenseImgFile(String licenseImgFile) {
        this.licenseImgFile = licenseImgFile;
    }

    public String getApplyLetterFile() {
        return applyLetterFile;
    }

    public void setApplyLetterFile(String applyLetterFile) {
        this.applyLetterFile = applyLetterFile;
    }
}
