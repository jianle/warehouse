package com.wms.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="enter")
public class Enter implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    private Long eId;
    private Long gId;
    private Long sId;
    private String gName;
    private Integer chests;
    private Integer boxes;
    private Long userId;
    private Long operatorId;
    private String userName;
    
    private Integer amount;
    private String remarks;
    private Timestamp insertDt;
    private Timestamp updateTime;
    
    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }
    public Long getOperatorId() {
        return operatorId;
    }
    public Long geteId() {
        return eId;
    }
    public void seteId(Long eId) {
        this.eId = eId;
    }
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
    public Integer getChests() {
        return chests;
    }
    public void setChests(Integer chests) {
        this.chests = chests;
    }
    public Integer getBoxes() {
        return boxes;
    }
    public void setBoxes(Integer boxes) {
        this.boxes = boxes;
    }
    public Integer getAmount() {
        return amount;
    }
    public void setAmount(Integer amount) {
        this.amount = amount;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
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
    
    @Override
    public String toString() {
        return "Enter [eId=" + eId + ", gId=" + gId + ", sId=" + sId
                + ", gName=" + gName + ", chests=" + chests + ", boxes="
                + boxes + ", userId=" + userId + ", userName=" + userName
                + ", amount=" + amount + ", remarks=" + remarks + ", insertDt="
                + insertDt + ", updateTime=" + updateTime + "]";
    }
    
}
