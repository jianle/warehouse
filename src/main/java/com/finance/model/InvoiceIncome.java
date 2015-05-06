package com.finance.model;


/*
 * Table -> invoice_income
 */


public class InvoiceIncome {
    
    private Long invId;
    private String invHead;
    private Double valoremTax;
    private Double amount;
    private Double amountTax;
    private Double rateTax;
    private String invDate;
    private Integer invType;
    private String invToCompany;
    private String remark;
    private Double rateRebate;
    private Integer isDeleted;
    private String updateTime;
    public Long getInvId() {
        return invId;
    }
    public void setInvId(Long invId) {
        this.invId = invId;
    }
    public String getInvHead() {
        return invHead;
    }
    public void setInvHead(String invHead) {
        this.invHead = invHead;
    }
    public Double getValoremTax() {
        return valoremTax;
    }
    public void setValoremTax(Double valoremTax) {
        this.valoremTax = valoremTax;
    }
    public Double getAmount() {
        return amount;
    }
    public void setAmount(Double amount) {
        this.amount = amount;
    }
    public Double getAmountTax() {
        return amountTax;
    }
    public void setAmountTax(Double amountTax) {
        this.amountTax = amountTax;
    }
    public Double getRateTax() {
        return rateTax;
    }
    public void setRateTax(Double rateTax) {
        this.rateTax = rateTax;
    }
    public String getInvDate() {
        return invDate;
    }
    public void setInvDate(String invDate) {
        this.invDate = invDate;
    }
    public Integer getInvType() {
        return invType;
    }
    public void setInvType(Integer invType) {
        this.invType = invType;
    }
    public String getInvToCompany() {
        return invToCompany;
    }
    public void setInvToCompany(String invToCompany) {
        this.invToCompany = invToCompany;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public Double getRateRebate() {
        return rateRebate;
    }
    public void setRateRebate(Double rateRebate) {
        this.rateRebate = rateRebate;
    }
    public Integer getIsDeleted() {
        return isDeleted;
    }
    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }
    public String getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
    @Override
    public String toString() {
        return "InvoiceIncome [invId=" + invId + ", invHead=" + invHead
                + ", valoremTax=" + valoremTax + ", amount=" + amount
                + ", amountTax=" + amountTax + ", rateTax=" + rateTax
                + ", invDate=" + invDate + ", invType=" + invType
                + ", invToCompany=" + invToCompany + ", remark=" + remark
                + ", rateRebate=" + rateRebate + ", isDeleted=" + isDeleted
                + ", updateTime=" + updateTime + "]";
    }
    
}
