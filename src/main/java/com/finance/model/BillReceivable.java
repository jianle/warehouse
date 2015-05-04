package com.finance.model;

/*
 * 应收账单
 */
public class BillReceivable {
    
    private Long brId;
    private String customerCompany;
    private String brDate;
    private Double amount;
    private String remark;
    
    public Long getBrId() {
        return brId;
    }
    public void setBrId(Long brId) {
        this.brId = brId;
    }
    public String getCustomerCompany() {
        return customerCompany;
    }
    public void setCustomerCompany(String customerCompany) {
        this.customerCompany = customerCompany;
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
        return "BillReceivable [brId=" + brId + ", customerCompany="
                + customerCompany + ", brDate=" + brDate + ", amount=" + amount
                + ", remark=" + remark + "]";
    }
    
}
