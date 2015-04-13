package com.wms.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="storage")
public class Storage implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Long gId;
    private Long sId;
    private String gName;
    private Integer chests;
    private Integer boxes;
    private Integer amount;
    private String remarks;
    
    @Column(updatable=false)
    private Timestamp insertDt;
    private Timestamp updateTime;
    
    public Long getgId() {
        return gId;
    }
    public void setgId(Long gId) {
        this.gId = gId;
    }
    public Long getsId() {
        return sId;
    }
    public void setsId(Long sId) {
        this.sId = sId;
    }
    public String getgName() {
        return gName;
    }
    public void setgName(String gName) {
        this.gName = gName;
    }
    public Integer getBoxes() {
        return boxes;
    }
    public void setBoxes(Integer boxes) {
        this.boxes = boxes;
    }
    public String getRemarks() {
        return remarks;
    }
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    public Timestamp getInsertDt() {
        return insertDt;
    }
    public void setInsertDt(Timestamp insertDt) {
        this.insertDt = insertDt;
    }
    public Timestamp getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }
    public Integer getChests() {
        return chests;
    }
    public void setChests(Integer chests) {
        this.chests = chests;
    }
    public Integer getAmount() {
        return amount;
    }
    public void setAmount(Integer amount) {
        this.amount = amount;
    }
    
    @Override
    public String toString() {
        return "Storage [gId=" + gId + ", gName=" + gName + ", chests="
                + chests + ", boxes=" + boxes + ", amount=" + amount
                + ", remarks=" + remarks + ", insertDt=" + insertDt
                + ", updateTime=" + updateTime + "]";
    }
    
}
