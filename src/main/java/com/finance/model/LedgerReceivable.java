package com.finance.model;

/*
 * Table -> ledger_receivable
 */

public class LedgerReceivable {
    
    private Long lrId;
    private Long conId;
    private String monthId;
    private Double amount;
    private String payDate;
    private Double verification;
    private String remark;
    private String updateTime;
    public void setMonthId(String monthId) {
        this.monthId = monthId;
    }
    public String getMonthId() {
        return monthId;
    }
    public Long getLrId() {
        return lrId;
    }
    public void setLrId(Long lrId) {
        this.lrId = lrId;
    }
    public void setConId(Long conId) {
        this.conId = conId;
    }
    public Long getConId() {
        return conId;
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
        return "LedgerReceivable [lrId=" + lrId + ", conId="
                + conId + ", amount=" + amount + ", payDate=" + payDate
                + ", verification=" + verification + ", remark=" + remark
                + ", updateTime=" + updateTime + "]";
    }
    
}
