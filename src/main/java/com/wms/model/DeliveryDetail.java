package com.wms.model;

public class DeliveryDetail {
    
    private Long   ddId;
    private String content;
    private String acceptAddress;
    private String acceptTime;
    private String remark;
    public Long getDdId() {
        return ddId;
    }
    public void setDdId(Long ddId) {
        this.ddId = ddId;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getAcceptAddress() {
        return acceptAddress;
    }
    public void setAcceptAddress(String acceptAddress) {
        this.acceptAddress = acceptAddress;
    }
    public String getAcceptTime() {
        return acceptTime;
    }
    public void setAcceptTime(String acceptTime) {
        this.acceptTime = acceptTime;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    @Override
    public String toString() {
        return "DeliveryDetail [ddId=" + ddId + ", content=" + content
                + ", acceptAddress=" + acceptAddress + ", acceptTime="
                + acceptTime + ", remark=" + remark + "]";
    }
    
}
