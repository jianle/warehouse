package com.finance.model;

/*
 * Table -> ledger_receivable
 */

public class LedgerReceivable {
    
    private Long lrId;
    private String payCompany;
    private Double amount;
    private String payDate;
    private Double verification;
    private String remark;
    private String updateTime;
    public Long getLrId() {
        return lrId;
    }
    public void setLrId(Long lrId) {
        this.lrId = lrId;
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
        return "LedgerReceivable [lrId=" + lrId + ", payCompany="
                + payCompany + ", amount=" + amount + ", payDate=" + payDate
                + ", verification=" + verification + ", remark=" + remark
                + ", updateTime=" + updateTime + "]";
    }
    
}
