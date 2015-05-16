package com.finance.model;

public class Producer {
	
	private Long proId;
	private String proName;
	private String remark;
	private String updateTime;
	
	public Long getProId() {
		return proId;
	}
	public void setProId(Long proId) {
		this.proId = proId;
	}
	public String getProName() {
		return proName;
	}
	public void setProName(String proName) {
		this.proName = proName;
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
		return "Producer [proId=" + proId + ", proName=" + proName
				+ ", remark=" + remark + ", updateTime=" + updateTime + "]";
	}
	
}
