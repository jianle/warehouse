package com.wms.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 */

@Entity
@Table(name = "supplier")
public class Supplier implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id @GeneratedValue
    private Long sId;
    private String name;
    private String shortname;
    private String address;
    private String contactName;
    private String contactTel;
    private String isDisabled;
    
    @Column(updatable=false)
    private Timestamp insertDt;
    private Timestamp updateTime;
    public Long getsId() {
        return sId;
    }
    public void setsId(Long sId) {
        this.sId = sId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getShortname() {
        return shortname;
    }
    public void setShortname(String shortname) {
        this.shortname = shortname;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getContactName() {
        return contactName;
    }
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }
    public String getContactTel() {
        return contactTel;
    }
    public void setContactTel(String contactTel) {
        this.contactTel = contactTel;
    }
    public String getIsDisabled() {
        return isDisabled;
    }
    public void setIsDisabled(String isDisabled) {
        this.isDisabled = isDisabled;
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
        return "Supplier [sId=" + sId + ", name=" + name + ", shortname="
                + shortname + ", address=" + address + ", contactName="
                + contactName + ", contactTel=" + contactTel + ", isDisabled="
                + isDisabled + ", insertDt=" + insertDt + ", updateTime="
                + updateTime + "]";
    }
    
}
