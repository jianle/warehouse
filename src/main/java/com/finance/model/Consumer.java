package com.finance.model;

public class Consumer {
	
	private Long conId;
	private String conName;
	private String remark;
	private String updateTime;
	
	public Long getConId() {
		return conId;
	}
	public void setConId(Long conId) {
		this.conId = conId;
	}
	public String getConName() {
		return conName;
	}
	public void setConName(String conName) {
		this.conName = conName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	
	@Override
	public String toString() {
		return "Consumer [conId=" + conId + ", conName=" + conName
				+ ", remark=" + remark + ", updateTime=" + updateTime + "]";
	}
	
}
