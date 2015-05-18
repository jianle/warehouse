package com.finance.model;

public class Incomepayment {
	
	private Integer id;
	private Integer parentId;
	private String  type;
	private String  updateTime;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	
	@Override
	public String toString() {
		return "Incomepayment [id=" + id + ", parentId=" + parentId + ", type="
				+ type + ", updateTime=" + updateTime + "]";
	}
	
}
