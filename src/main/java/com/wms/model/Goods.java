package com.wms.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import net.sf.json.JSONObject;



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
    
    private String model;
    
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
    private Long operatorId;
    private String standards;
    
    private String lrack1;
    private String lrack2;
    private String lrack3;
    
    private String mrack1;
    private String mrack2;
    private String mrack3;
    
    
    @Column(updatable=false)
    private String insertDt;
    private String updateTime;
    
    public String getLrack1() {
        return lrack1;
    }
    public void setLrack1(String lrack1) {
        this.lrack1 = lrack1;
    }
    public String getLrack2() {
        return lrack2;
    }
    public void setLrack2(String lrack2) {
        this.lrack2 = lrack2;
    }
    public String getLrack3() {
        return lrack3;
    }
    public void setLrack3(String lrack3) {
        this.lrack3 = lrack3;
    }
    public String getMrack1() {
        return mrack1;
    }
    public void setMrack1(String mrack1) {
        this.mrack1 = mrack1;
    }
    public String getMrack2() {
        return mrack2;
    }
    public void setMrack2(String mrack2) {
        this.mrack2 = mrack2;
    }
    public String getMrack3() {
        return mrack3;
    }
    public void setMrack3(String mrack3) {
        this.mrack3 = mrack3;
    }
    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public JSONObject getLrack() {
        JSONObject json = new JSONObject();
        json.put("lrack1", this.lrack1);
        json.put("lrack2", this.lrack2);
        json.put("lrack3", this.lrack3);
        
        return json;
    }
    public void setLrack(JSONObject lrack) {
        this.lrack1 = (String) lrack.get("lrack1");
        this.lrack2 = (String) lrack.get("lrack2");
        this.lrack3 = (String) lrack.get("lrack3");
    }
    public JSONObject getMrack() {
        
        JSONObject json = new JSONObject();
        json.put("mrack1", this.mrack1);
        json.put("mrack2", this.mrack2);
        json.put("mrack3", this.mrack3);
        
        return json;
    }
    public void setMrack(JSONObject mrack) {
        this.mrack1 = (String) mrack.get("mrack1");
        this.mrack2 = (String) mrack.get("mrack2");
        this.mrack3 = (String) mrack.get("mrack3");
    }
    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }
    public Long getOperatorId() {
        return operatorId;
    }
    public void setStandards(String standards) {
        this.standards = standards;
    }
    public String getStandards() {
        return standards;
    }
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
                + ", userId=" + userId + ", standards=" + standards
                + ", insertDt=" + insertDt + ", updateTime=" + updateTime + "]";
    }
}
