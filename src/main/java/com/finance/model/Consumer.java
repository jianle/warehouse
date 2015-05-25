package com.finance.model;

public class Consumer {
    
    private Long conId;
    private String conName;
    private String accountCompany;
    private String account;
    private String taxpayerId;
    
    private String contactAddr;
    private String contactName;
    private String contactTel;
    
    private String remark;
    private String insertDt;
    private String updateTime;
    public Long getConId() {
        return conId;
    }
    public void setConId(Long conId) {
        this.conId = conId;
    }
    public String getConName() {
        return conName;
    }
    public void setConName(String conName) {
        this.conName = conName;
    }
    public String getAccountCompany() {
        return accountCompany;
    }
    public void setAccountCompany(String accountCompany) {
        this.accountCompany = accountCompany;
    }
    public String getAccount() {
        return account;
    }
    public void setAccount(String account) {
        this.account = account;
    }
    public String getTaxpayerId() {
        return taxpayerId;
    }
    public void setTaxpayerId(String taxpayerId) {
        this.taxpayerId = taxpayerId;
    }
    public String getContactAddr() {
        return contactAddr;
    }
    public void setContactAddr(String contactAddr) {
        this.contactAddr = contactAddr;
    }
    public String getContactName() {
        return contactName;
    }
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }
    public String getContactTel() {
        return contactTel;
    }
    public void setContactTel(String contactTel) {
        this.contactTel = contactTel;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public String getInsertDt() {
        return insertDt;
    }
    public void setInsertDt(String insertDt) {
        this.insertDt = insertDt;
    }
    public String getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
    
    @Override
    public String toString() {
        return "Consumer [conId=" + conId + ", conName=" + conName
                + ", accountCompany=" + accountCompany + ", account=" + account
                + ", taxpayerId=" + taxpayerId + ", contactAddr=" + contactAddr
                + ", contactName=" + contactName + ", contactTel=" + contactTel
                + ", remark=" + remark + ", insertDt=" + insertDt
                + ", updateTime=" + updateTime + "]";
    }
    
}
