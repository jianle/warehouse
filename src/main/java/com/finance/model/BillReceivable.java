package com.finance.model;

/*
 * 应收账单
 */
public class BillReceivable {
    
    private Long brId;
    private Long conId;
    private String brDate;
    private Double amount;
    private String remark;
    
    public Long getBrId() {
        return brId;
    }
    public void setBrId(Long brId) {
        this.brId = brId;
    }
    public void setConId(Long conId) {
		this.conId = conId;
	}
    public Long getConId() {
		return conId;
	}
    public String getBrDate() {
        return brDate;
    }
    public void setBrDate(String brDate) {
        this.brDate = brDate;
    }
    public Double getAmount() {
        return amount;
    }
    public void setAmount(Double amount) {
        this.amount = amount;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    @Override
    public String toString() {
        return "BillReceivable [brId=" + brId + ", conId="
                + conId + ", brDate=" + brDate + ", amount=" + amount
                + ", remark=" + remark + "]";
    }
    
}
