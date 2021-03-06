package com.wms.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "user")
public class User implements Serializable {

    private static final long serialVersionUID = 1025380931554330409L;

    public static final int ROLE_ADMIN = 1;
    public static final int ROLE_BOSS = 2;
    public static final int ROLE_DIRECTOR = 3;
    public static final int ROLE_NORMAL = 4;
    public static final int ROLE_GUEST = 8;


    @Id @GeneratedValue
    private Long id;
    private Long parentId;
    private String username;
    @Column(updatable = false)
    private String password;
    
    private String truename;
    private String email;
    private Integer role;
    private String userIds;
    
    @Column(updatable = false)
    private Date created;
    
    public void setUserIds(String userIds) {
        this.userIds = userIds;
    }
    public String getUserIds() {
        return userIds;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getTruename() {
        return truename;
    }
    public void setTruename(String truename) {
        this.truename = truename;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public Integer getRole() {
        return role;
    }
    public void setRole(Integer role) {
        this.role = role;
    }
    public Date getCreated() {
        return created;
    }
    public void setCreated(Date created) {
        this.created = created;
    }
    @Override
    public String toString() {
        return "User [id=" + id + ", parentId=" + parentId + ", username="
                + username + ", password=" + password + ", truename="
                + truename + ", email=" + email + ", role=" + role
                + ", created=" + created + "]";
    }
    
}
