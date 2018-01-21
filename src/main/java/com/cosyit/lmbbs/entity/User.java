package com.cosyit.lmbbs.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


public class User implements Serializable{
	private static final long serialVersionUID = 1L;//实现序列化接口与数据库对应
	private String id;
	private String loginName;
	private String password;
	private String email;
	private String nickName;
	private String gender;
	private String avatar;
	private String signature;
	private Date registrationTime;//注册时间
	private Date lastVisitTime;//上一次登录访问时间；
	private String lastVisitIpAddr;//最后访问IP地址
	private int topicCount;//发表的帖子数
	private int articleCount;//回复数
	private boolean locked;//是否被锁定；
	private String autoLoginAuthKey;//将来做自动登录的验证
	private Set<Role> roles;//该用户所拥有的角色
	private Set<Systemprivilege> Systemprivileges=new HashSet<Systemprivilege>();
	
	
	//get&set方法
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public Date getRegistrationTime() {
		return registrationTime;
	}
	public void setRegistrationTime(Date registrationTime) {
		this.registrationTime = registrationTime;
	}
	public Date getLastVisitTime() {
		return lastVisitTime;
	}
	public void setLastVisitTime(Date lastVisitTime) {
		this.lastVisitTime = lastVisitTime;
	}
	public String getLastVisitIpAddr() {
		return lastVisitIpAddr;
	}
	public void setLastVisitIpAddr(String lastVisitIpAddr) {
		this.lastVisitIpAddr = lastVisitIpAddr;
	}
	public int getTopicCount() {
		return topicCount;
	}
	public void setTopicCount(int topicCount) {
		this.topicCount = topicCount;
	}
	public int getArticleCount() {
		return articleCount;
	}
	public void setArticleCount(int articleCount) {
		this.articleCount = articleCount;
	}
	public boolean isLocked() {
		return locked;
	}
	public void setLocked(boolean locked) {
		this.locked = locked;
	}
	public String getAutoLoginAuthKey() {
		return autoLoginAuthKey;
	}
	public void setAutoLoginAuthKey(String autoLoginAuthKey) {
		this.autoLoginAuthKey = autoLoginAuthKey;
	}
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	public Set<Systemprivilege> getSystemprivileges() {
		return Systemprivileges;
	}
	public void setSystemprivileges(Set<Systemprivilege> systemprivileges) {
		Systemprivileges = systemprivileges;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", loginName=" + loginName + ", password=" + password + ", email=" + email
				+ ", nickName=" + nickName + ", gender=" + gender + ", avatar=" + avatar + ", signature=" + signature
				+ ", registrationTime=" + registrationTime + ", lastVisitTime=" + lastVisitTime + ", lastVisitIpAddr="
				+ lastVisitIpAddr + ", topicCount=" + topicCount + ", articleCount=" + articleCount + ", locked="
				+ locked + ", autoLoginAuthKey=" + autoLoginAuthKey + ", roles=" + roles + ", Systemprivileges="
				+ Systemprivileges + "]";
	}
}
