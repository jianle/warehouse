package com.wms.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 订单明细
 */
@Entity
@Table(name="order_detail")
public class OrderDetail implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Long odId;
    private Long oId;
    private Long gId;
    private String gName;
    private Double retailPrice;
    private Double unitPrice;
    private Double amountNet;
    private Integer amount;
    private Double amountAmt;
    private String code;
    private String remarks;
    
    @Column(updatable=false)
    private String insertDt;
    private String updateTime;
    
    public Long getOdId() {
        return odId;
    }
    public void setOdId(Long odId) {
        this.odId = odId;
    }
    public Long getoId() {
        return oId;
    }
    public void setoId(Long oId) {
        this.oId = oId;
    }
    public Long getgId() {
        return gId;
    }
    public void setgId(Long gId) {
        this.gId = gId;
    }
    public String getgName() {
        return gName;
    }
    public void setgName(String gName) {
        this.gName = gName;
    }
    public Double getRetailPrice() {
        if (retailPrice==null) {
            retailPrice = (double) 0;
        }
        return retailPrice;
    }
    public void setRetailPrice(Double retailPrice) {
        this.retailPrice = retailPrice;
    }
    public Double getUnitPrice() {
        if (unitPrice==null) {
            unitPrice = (double) 0;
        }
        return unitPrice;
    }
    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }
    public Double getAmountNet() {
        if (amountNet==null) {
            amountNet = (double) 0;
        }
        return amountNet;
    }
    public void setAmountNet(Double amountNet) {
        this.amountNet = amountNet;
    }
    public Integer getAmount() {
        if (amount==null) {
            amount = 0;
        }
        return amount;
    }
    public void setAmount(Integer amount) {
        this.amount = amount;
    }
    public Double getAmountAmt() {
        if (amountAmt==null) {
            amountAmt = (double) 0;
        }
        return amountAmt;
    }
    public void setAmountAmt(Double amountAmt) {
        this.amountAmt = amountAmt;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getRemarks() {
        return remarks;
    }
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    public String getInsertDt() {
        return insertDt;
    }
    public void setInsertDt(String insertDt) {
        if (insertDt == null) {
            insertDt = String.valueOf(new Timestamp(System.currentTimeMillis()));
        }
        this.insertDt = insertDt;
    }
    public String getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(String updateTime) {
        if (updateTime == null) {
            updateTime = String.valueOf(new Timestamp(System.currentTimeMillis()));
        }
        this.updateTime = updateTime;
    }
    
    @Override
    public String toString() {
        return "OrderDetail [odId=" + odId + ", oId=" + oId + ", gId=" + gId
                + ", gName=" + gName + ", retailPrice=" + retailPrice
                + ", unitPrice=" + unitPrice + ", amountNet=" + amountNet
                + ", amount=" + amount + ", amountAmt=" + amountAmt + ", code="
                + code + ", remarks=" + remarks + ", insertDt=" + insertDt
                + ", updateTime=" + updateTime + "]";
    }
    
}
