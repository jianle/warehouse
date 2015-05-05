package com.finance.model;

/*
 * table -> invoice
 */

public class Invoice {
    
    private Long brId;
    private Long invId;
    private String invHead;
    private Double valoremTax;
    private Double amount;
    private Double amountTax;
    private Double rateTax;
    
    private String invDate;
    private String remark;
    private String incDate;
    private String invToCompany;
    private Double verification;
    private String updateTime;
    
    public Long getBrId() {
        return brId;
    }

    public void setBrId(Long brId) {
        this.brId = brId;
    }

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getIncDate() {
        return incDate;
    }

    public void setIncDate(String incDate) {
        this.incDate = incDate;
    }

    public String getInvToCompany() {
        return invToCompany;
    }

    public void setInvToCompany(String invToCompany) {
        this.invToCompany = invToCompany;
    }

    public Double getVerification() {
        return verification;
    }

    public void setVerification(Double verification) {
        this.verification = verification;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "Invoice [brId=" + brId + ", invId=" + invId + ", invHead="
                + invHead + ", valoremTax=" + valoremTax + ", amount=" + amount
                + ", amountTax=" + amountTax + ", rateTax=" + rateTax
                + ", invDate=" + invDate + ", remark=" + remark + ", incDate="
                + incDate + ", invToCompany=" + invToCompany
                + ", verification=" + verification + ", updateTime="
                + updateTime + "]";
    }
    
}
