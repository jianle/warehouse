package com.finance.model;

/*
 * Table -> debtor
 */

public class Debtor {
    
    private Long deId;
    private String monId;
    private String consumer;
    private String invCompany;
    private Double amount;
    private String remark;
    private String updateTime;
    
    public Debtor() { }
    
    public Debtor(Long deId, String monId, String consumer,String invCompany
            , Double amount, String remark, String updateTime) {
        this.deId =deId;
        this.monId = monId;
        this.consumer = consumer;
        this.invCompany = invCompany;
        this.amount = amount;
        this.remark = remark;
        this.updateTime = updateTime;
    }
    
    public Long getDeId() {
        return deId;
    }

    public void setDeId(Long deId) {
        this.deId = deId;
    }

    public String getMonId() {
        return monId;
    }

    public void setMonId(String monId) {
        this.monId = monId;
    }

    public String getConsumer() {
        return consumer;
    }

    public void setConsumer(String consumer) {
        this.consumer = consumer;
    }

    public String getInvCompany() {
        return invCompany;
    }

    public void setInvCompany(String invCompany) {
        this.invCompany = invCompany;
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

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "Debtor [deId=" + deId + ", monId=" + monId + ", consumer="
                + consumer + ", invCompany=" + invCompany + ", amount="
                + amount + ", remark=" + remark + ", updateTime=" + updateTime
                + "]";
    }
    
}
