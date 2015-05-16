package com.finance.model;

/*
 * table -> invoice
 */

public class Invoice implements Cloneable {
    
    private Long brId;
    private Long invId;
    private Long conId;
    private Double valoremTax;
    private Double amount;
    private Double amountTax;
    private Double rateTax;
    
    private String invDate;
    private String remark;
    private String incDate;
    private Long  proId;
    private Double verification;
    private Integer isDeleted;
    private String updateTime;
    
    private Integer number;
    
    public Long getConId() {
		return conId;
	}

	public void setConId(Long conId) {
		this.conId = conId;
	}

	public Long getProId() {
		return proId;
	}

	public void setProId(Long proId) {
		this.proId = proId;
	}

	public void setNumber(Integer number) {
        this.number = number;
    }
    
    public Integer getNumber() {
        return number;
    }
    
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

    public Double getVerification() {
        return verification;
    }

    public void setVerification(Double verification) {
        this.verification = verification;
    }
    
    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }
    
    public Integer getIsDeleted() {
        return isDeleted;
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
		return "Invoice [brId=" + brId + ", invId=" + invId + ", conId="
				+ conId + ", valoremTax=" + valoremTax + ", amount=" + amount
				+ ", amountTax=" + amountTax + ", rateTax=" + rateTax
				+ ", invDate=" + invDate + ", remark=" + remark + ", incDate="
				+ incDate + ", proId=" + proId + ", verification="
				+ verification + ", isDeleted=" + isDeleted + ", updateTime="
				+ updateTime + ", number=" + number + "]";
	}
    
}
