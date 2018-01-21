package com.cosyit.lmbbs.entity;

import java.io.Serializable;

//权限类
public class Systemprivilege implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String action;
	private String resource;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getResource() {
		return resource;
	}
	public void setResource(String resource) {
		this.resource = resource;
	}
	@Override
	public String toString() {
		return "Systemprivilege [id=" + id + ", name=" + name + ", action=" + action + ", resource=" + resource + "]";
	}
	
	
	
}
