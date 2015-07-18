package com.wms.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;
/**
 * 订单信息
 */

@SuppressWarnings("serial")
@Entity
@Table(name="orderinfo")
public class Orderinfo implements Serializable {
    
    private Long oId;
    private String orderCode;
    private Long customerCode;
    private String whAdd;
    private String transpCostType;
    private String receiveName;
    private String receiveTel;
    private String receiveAdd;
    private Long userId;
    private String userName;
    private Long operatorId;
    
    private String documentDate;
    private Double amountTotal;
    private Double amountDiscount;
    private Double amountPayable;

    private Integer status;
    private Long osId;
    private String remark;
    private String insertDt;
    private String updateTime;
    
    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }
    public String getReceiveName() {
        return receiveName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserName() {
        return userName;
    }
    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }
    public Long getOperatorId() {
        return operatorId;
    }
    public void setOsId(Long osId) {
        this.osId = osId;
    }
    public Long getOsId() {
        return osId;
    }
    public Long getoId() {
        return oId;
    }
    public void setoId(Long oId) {
        this.oId = oId;
    }
    public String getOrderCode() {
        return orderCode;
    }
    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }
    public Long getCustomerCode() {
        return customerCode;
    }
    public void setCustomerCode(Long customerCode) {
        this.customerCode = customerCode;
    }
    public String getWhAdd() {
        return whAdd;
    }
    public void setWhAdd(String whAdd) {
        this.whAdd = whAdd;
    }
    public String getTranspCostType() {
        return transpCostType;
    }
    public void setTranspCostType(String transpCostType) {
        this.transpCostType = transpCostType;
    }
    public String getReceiveTel() {
        return receiveTel;
    }
    public void setReceiveTel(String receiveTel) {
        this.receiveTel = receiveTel;
    }
    public String getReceiveAdd() {
        return receiveAdd;
    }
    public void setReceiveAdd(String receiveAdd) {
        this.receiveAdd = receiveAdd;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public Long getUserId() {
        return userId;
    }
    public String getDocumentDate() {
        return documentDate;
    }
    public void setDocumentDate(String documentDate) {
        this.documentDate = documentDate;
    }
    public Double getAmountTotal() {
        if (amountTotal == null) {
            return (double) 0;
        }
        return amountTotal;
    }
    public void setAmountTotal(Double amountTotal) {
        this.amountTotal = amountTotal;
    }
    public Double getAmountDiscount() {
        if (amountDiscount == null) {
            return (double) 0;
        }
        return amountDiscount;
    }
    public void setAmountDiscount(Double amountDiscount) {
        this.amountDiscount = amountDiscount;
    }
    public Double getAmountPayable() {
        if (amountPayable == null) {
            return (double) 0;
        }
        return amountPayable;
    }
    public void setAmountPayable(Double amountPayable) {
        this.amountPayable = amountPayable;
    }
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public String getRemark() {
        return remark;
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
        return "Orderinfo [oId=" + oId + ", orderCode=" + orderCode
                + ", customerCode=" + customerCode + ", whAdd=" + whAdd
                + ", transpCostType=" + transpCostType + ", receiveTel="
                + receiveTel + ", receiveAdd=" + receiveAdd + ", userId="
                + userId + ", documentDate=" + documentDate + ", amountTotal="
                + amountTotal + ", amountDiscount=" + amountDiscount
                + ", amountPayable=" + amountPayable + ", status=" + status
                + ", osId=" + osId
                + ", remark=" + remark + ", insertDt=" + insertDt
                + ", updateTime=" + updateTime + "]";
    }
    
}
