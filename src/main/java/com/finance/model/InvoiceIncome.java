package com.finance.model;

import java.text.DecimalFormat;


/*
 * Table -> invoice_income
 */


public class InvoiceIncome implements Cloneable {
    
    private Long invId;
    private Long proId;
    private Double valoremTax;
    private Double amount;
    private Double amountTax;
    private Double rateTax;
    private String invDate;
    private Integer invType;
    private Long conId;
    private String remark;
    private Double rateRebate;
    private Integer isDeleted;
    private String updateTime;
    private Integer number;
    
    private DecimalFormat df=new DecimalFormat("#0.00"); 
    
    public String getRebateAmount() {
        return df.format(this.amountTax * (this.rateRebate/100));
    }
    
    public Integer getNumber() {
        return number;
    }
    public void setNumber(Integer number) {
        this.number = number;
    }
    public Long getInvId() {
        return invId;
    }
    public void setInvId(Long invId) {
        this.invId = invId;
    }
    public void setProId(Long proId) {
        this.proId = proId;
    }
    public Long getProId() {
        return proId;
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
    public void setConId(Long conId) {
        this.conId = conId;
    }
    public Long getConId() {
        return conId;
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
    public Object clone() throws CloneNotSupportedException {  
        return super.clone();  
    }
    
    @Override
    public String toString() {
        return "InvoiceIncome [invId=" + invId + ", proId=" + proId
                + ", valoremTax=" + valoremTax + ", amount=" + amount
                + ", amountTax=" + amountTax + ", rateTax=" + rateTax
                + ", invDate=" + invDate + ", invType=" + invType
                + ", conId=" + conId + ", remark=" + remark
                + ", rateRebate=" + rateRebate + ", isDeleted=" + isDeleted
                + ", updateTime=" + updateTime + "]";
    }
    
}
