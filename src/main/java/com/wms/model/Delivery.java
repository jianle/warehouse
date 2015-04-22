package com.wms.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="delivery")
public class Delivery {
    
    private Long dId;
    private Long oId;
    private String expressId;
    private String expressName;
    
    private Integer weight;
    private Integer length;
    private Integer wide;
    private Integer high;
    
    private String remarks;
    private Integer status;
    private String insertDt;
    private String updateTime;
    
    public Long getdId() {
        return dId;
    }
    public void setdId(Long dId) {
        this.dId = dId;
    }
    public Long getoId() {
        return oId;
    }
    public void setoId(Long oId) {
        this.oId = oId;
    }
    public String getExpressId() {
        return expressId;
    }
    public void setExpressId(String expressId) {
        this.expressId = expressId;
    }
    public String getExpressName() {
        return expressName;
    }
    public void setExpressName(String expressName) {
        this.expressName = expressName;
    }
    public Integer getWeight() {
        return weight;
    }
    public void setWeight(Integer weight) {
        this.weight = weight;
    }
    public Integer getLength() {
        return length;
    }
    public void setLength(Integer length) {
        this.length = length;
    }
    public Integer getWide() {
        return wide;
    }
    public void setWide(Integer wide) {
        this.wide = wide;
    }
    public Integer getHigh() {
        return high;
    }
    public void setHigh(Integer high) {
        this.high = high;
    }
    public String getRemarks() {
        return remarks;
    }
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
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
        return "Delivery [dId=" + dId + ", oId=" + oId + ", expressId="
                + expressId + ", expressName=" + expressName + ", weight="
                + weight + ", length=" + length + ", wide=" + wide + ", high="
                + high + ", remarks=" + remarks + ", status=" + status
                + ", insertDt=" + insertDt + ", updateTime=" + updateTime + "]";
    }
    
}
