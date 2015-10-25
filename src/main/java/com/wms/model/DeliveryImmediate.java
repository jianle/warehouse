package com.wms.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="delivery_immediate")
public class DeliveryImmediate implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Long gId;
    private String name;
    private String model;
    private String scode;
    private Integer amount;
    private Integer damount;
    private Long operatorId;
    
    @Column(updatable=false)
    private String insertDt;
    private String updateTime;
    public Long getgId() {
        return gId;
    }
    public void setgId(Long gId) {
        this.gId = gId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public String getScode() {
        return scode;
    }
    public void setScode(String scode) {
        this.scode = scode;
    }
    public Integer getAmount() {
        return amount;
    }
    public void setAmount(Integer amount) {
        this.amount = amount;
    }
    public Integer getDamount() {
        return damount;
    }
    public void setDamount(Integer damount) {
        this.damount = damount;
    }
    public Long getOperatorId() {
        return operatorId;
    }
    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }
    public String getInsertDt() {
        return insertDt;
    }
    public void setInsertDt(String insertDt) {
        this.insertDt = insertDt;
    }
    public String getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
    
    @Override
    public String toString() {
        return "DeliveryImmediate [gId=" + gId + ", name=" + name + ", model="
                + model + ", scode=" + scode + ", amount=" + amount
                + ", damount=" + damount + ", operatorId=" + operatorId
                + ", insertDt=" + insertDt + ", updateTime=" + updateTime + "]";
    }
    
}
