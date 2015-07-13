package com.wms.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 
 */

@Entity
@Table(name = "goods")
public class Goods implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id @GeneratedValue
    private Long gId;
    
    private Long sId;
    private String name;
    private Integer length;
    private Integer width;
    private Integer height;
    private Integer weight;
    
    private String gIdSupplier;
    private String scode;
    private Integer boxes;
    private Integer amount;
    private String isDisabled;
    private Long userId;
    
    @Column(updatable=false)
    private String insertDt;
    private String updateTime;
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public Long getUserId() {
        return userId;
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
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getLength() {
        return length;
    }
    public void setLength(Integer length) {
        this.length = length;
    }
    public Integer getWidth() {
        return width;
    }
    public void setWidth(Integer width) {
        this.width = width;
    }
    public Integer getHeight() {
        return height;
    }
    public void setHeight(Integer height) {
        this.height = height;
    }
    public Integer getWeight() {
        return weight;
    }
    public void setWeight(Integer weight) {
        this.weight = weight;
    }
    public String getgIdSupplier() {
        return gIdSupplier;
    }
    public void setgIdSupplier(String gIdSupplier) {
        this.gIdSupplier = gIdSupplier;
    }
    public String getScode() {
        return scode;
    }
    public void setScode(String scode) {
        this.scode = scode;
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
    public String getIsDisabled() {
        return isDisabled;
    }
    public void setIsDisabled(String isDisabled) {
        this.isDisabled = isDisabled;
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
        return "Goods [gId=" + gId + ", sId=" + sId + ", name=" + name
                + ", length=" + length + ", width=" + width + ", height="
                + height + ", weight=" + weight + ", gIdSupplier="
                + gIdSupplier + ", scode=" + scode + ", boxes=" + boxes
                + ", amount=" + amount + ", isDisabled=" + isDisabled
                + ", insertDt=" + insertDt + ", updateTime=" + updateTime + "]";
    }
}
