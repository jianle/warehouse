package com.finance.model;

/*
 * Table -> ledger_receivable
 */

public class LedgerReceivable {
    
    private Long lr_id;
    private String payCompany;
    private Double amount;
    private String payDate;
    private Double verification;
    private String remark;
    private String updateTime;
    public Long getLr_id() {
        return lr_id;
    }
    public void setLr_id(Long lr_id) {
        this.lr_id = lr_id;
    }
    public String getPayCompany() {
        return payCompany;
    }
    public void setPayCompany(String payCompany) {
        this.payCompany = payCompany;
    }
    public Double getAmount() {
        return amount;
    }
    public void setAmount(Double amount) {
        this.amount = amount;
    }
    public String getPayDate() {
        return payDate;
    }
    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }
    public Double getVerification() {
        return verification;
    }
    public void setVerification(Double verification) {
        this.verification = verification;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public String getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
    @Override
    public String toString() {
        return "LedgerReceivable [lr_id=" + lr_id + ", payCompany="
                + payCompany + ", amount=" + amount + ", payDate=" + payDate
                + ", verification=" + verification + ", remark=" + remark
                + ", updateTime=" + updateTime + "]";
    }
    
}
