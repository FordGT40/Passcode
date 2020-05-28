package com.wisdom.passcode.mine.model;

import java.util.List;

/**
 * @author HanXueFeng
 * @ProjectName project： Passcode
 * @class package：com.wisdom.passcode.mine.model
 * @class describe：
 * @time 2020/5/27 0027 11:34
 * @change
 */
public class PlaceCodeRecordModel {

    /**
     * total : 2
     * list : [{"city":"哈尔滨","prefecture":"道里区","legalIdCard":"IedlZAOfXS6uAaim1r+VoQNgsEhMoU2b","uscc":"123456789aa","accountSliceType":"1","type":"1","licenseImg":"http://192.168.1.225:8091/cxm-file-system/profile/upload/2020/05/26/e4d704a85b9c4744a02223477d49513b.png","auditState":"1","applyLetter":"http://192.168.1.225:8091/cxm-file-system/profile/upload/2020/05/26/86fcb1404b5419a9afdce881e70403e3.png","province":"黑龙江","detailedAddress":"哈尔滨道里区红旗大街9999号","legal":"法海","name":"少林寺","id":6,"createDate":1590472238000,"leaderIdCard":"AT135U0gEAYgNDUnglSdpgu2EcpN04FV"}]
     */

    private int total;
    private List<ListBean> list;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * city : 哈尔滨
         * prefecture : 道里区
         * legalIdCard : IedlZAOfXS6uAaim1r+VoQNgsEhMoU2b
         * uscc : 123456789aa
         * accountSliceType : 1
         * type : 1
         * licenseImg : http://192.168.1.225:8091/cxm-file-system/profile/upload/2020/05/26/e4d704a85b9c4744a02223477d49513b.png
         * auditState : 1
         * applyLetter : http://192.168.1.225:8091/cxm-file-system/profile/upload/2020/05/26/86fcb1404b5419a9afdce881e70403e3.png
         * province : 黑龙江
         * detailedAddress : 哈尔滨道里区红旗大街9999号
         * legal : 法海
         * name : 少林寺
         * id : 6
         * createDate : 1590472238000
         * leaderIdCard : AT135U0gEAYgNDUnglSdpgu2EcpN04FV
         */

        private String city;
        private String prefecture;
        private String legalIdCard;
        private String uscc;
        private String accountSliceType;
        private String type;
        private String licenseImg;
        private String auditState;
        private String auditReason;
        private String applyLetter;
        private String province;
        private String detailedAddress;
        private String legal;
        private String name;
        private int id;
        private long createDate;
        private String leaderIdCard;

        public String getAuditReason() {
            return auditReason;
        }

        public void setAuditReason(String auditReason) {
            this.auditReason = auditReason;
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

        public String getLegalIdCard() {
            return legalIdCard;
        }

        public void setLegalIdCard(String legalIdCard) {
            this.legalIdCard = legalIdCard;
        }

        public String getUscc() {
            return uscc;
        }

        public void setUscc(String uscc) {
            this.uscc = uscc;
        }

        public String getAccountSliceType() {
            return accountSliceType;
        }

        public void setAccountSliceType(String accountSliceType) {
            this.accountSliceType = accountSliceType;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getLicenseImg() {
            return licenseImg;
        }

        public void setLicenseImg(String licenseImg) {
            this.licenseImg = licenseImg;
        }

        public String getAuditState() {
            return auditState;
        }

        public void setAuditState(String auditState) {
            this.auditState = auditState;
        }

        public String getApplyLetter() {
            return applyLetter;
        }

        public void setApplyLetter(String applyLetter) {
            this.applyLetter = applyLetter;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public long getCreateDate() {
            return createDate;
        }

        public void setCreateDate(long createDate) {
            this.createDate = createDate;
        }

        public String getLeaderIdCard() {
            return leaderIdCard;
        }

        public void setLeaderIdCard(String leaderIdCard) {
            this.leaderIdCard = leaderIdCard;
        }
    }
}
