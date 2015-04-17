package com.wms.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;
/**
 * 订单信息
 */

@Entity
@Table(name="orderinfo")
public class Orderinfo implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Long oId;
    private String documentCode;
    private String orderCode;
    private String orderType;
    private String customerType;
    private String customerName;
    private String customerCode;
    private String whAdd;
    private String transpCostType;
    private String receiveTel;
    private String receiveAdd;
    private String userName;
    
    private Date documentDate;
    private Date saleDate;
    
    private String company;
    private Double amountTotal;
    private String mtMch;
    private Double accountBalance;
    private Double retailPrice;
    private Double amountDiscount;
    private Double amountPayable;
    private Double amountNet;
    private Integer status;
    private Timestamp insertDt;
    private Timestamp updateTime;
    public Long getoId() {
        return oId;
    }
    public void setoId(Long oId) {
        this.oId = oId;
    }
    public String getDocumentCode() {
        return documentCode;
    }
    public void setDocumentCode(String documentCode) {
        this.documentCode = documentCode;
    }
    public String getOrderCode() {
        return orderCode;
    }
    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }
    public String getOrderType() {
        return orderType;
    }
    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }
    public String getCustomerType() {
        return customerType;
    }
    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }
    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public String getCustomerCode() {
        return customerCode;
    }
    public void setCustomerCode(String customerCode) {
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
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public Date getDocumentDate() {
        return documentDate;
    }
    public void setDocumentDate(Date documentDate) {
        this.documentDate = documentDate;
    }
    public Date getSaleDate() {
        return saleDate;
    }
    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }
    public String getCompany() {
        return company;
    }
    public void setCompany(String company) {
        this.company = company;
    }
    public Double getAmountTotal() {
        return amountTotal;
    }
    public void setAmountTotal(Double amountTotal) {
        this.amountTotal = amountTotal;
    }
    public String getMtMch() {
        return mtMch;
    }
    public void setMtMch(String mtMch) {
        this.mtMch = mtMch;
    }
    public Double getAccountBalance() {
        return accountBalance;
    }
    public void setAccountBalance(Double accountBalance) {
        this.accountBalance = accountBalance;
    }
    public Double getRetailPrice() {
        return retailPrice;
    }
    public void setRetailPrice(Double retailPrice) {
        this.retailPrice = retailPrice;
    }
    public Double getAmountDiscount() {
        return amountDiscount;
    }
    public void setAmountDiscount(Double amountDiscount) {
        this.amountDiscount = amountDiscount;
    }
    public Double getAmountPayable() {
        return amountPayable;
    }
    public void setAmountPayable(Double amountPayable) {
        this.amountPayable = amountPayable;
    }
    public Double getAmountNet() {
        return amountNet;
    }
    public void setAmountNet(Double amountNet) {
        this.amountNet = amountNet;
    }
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
    public Timestamp getInsertDt() {
        return insertDt;
    }
    public void setInsertDt(Timestamp insertDt) {
        if (insertDt == null) {
            insertDt = new Timestamp(System.currentTimeMillis());
        }
        this.insertDt = insertDt;
    }
    public Timestamp getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(Timestamp updateTime) {
        if (updateTime == null) {
            updateTime = new Timestamp(System.currentTimeMillis());
        }
        this.updateTime = updateTime;
    }
    
    @Override
    public String toString() {
        return "Orderinfo [oId=" + oId + ", documentCode=" + documentCode
                + ", orderCode=" + orderCode + ", orderType=" + orderType
                + ", customerType=" + customerType + ", customerName="
                + customerName + ", customerCode=" + customerCode + ", whAdd="
                + whAdd + ", transpCostType=" + transpCostType
                + ", receiveTel=" + receiveTel + ", receiveAdd=" + receiveAdd
                + ", userName=" + userName + ", documentDate=" + documentDate
                + ", saleDate=" + saleDate + ", company=" + company
                + ", amountTotal=" + amountTotal + ", mtMch=" + mtMch
                + ", accountBalance=" + accountBalance + ", retailPrice="
                + retailPrice + ", amountDiscount=" + amountDiscount
                + ", amountPayable=" + amountPayable + ", amountNet="
                + amountNet + ", status=" + status + ", insertDt=" + insertDt
                + ", updateTime=" + updateTime + "]";
    }
    
}
