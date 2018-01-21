package com.cosyit.lmbbs.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Category implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private int myorder;
	private Set<Forum> forums=new HashSet<Forum>();
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
	public int getMyorder() {
		return myorder;
	}
	public void setMyorder(int myorder) {
		this.myorder = myorder;
	}
	public Set<Forum> getForums() {
		return forums;
	}
	public void setForums(Set<Forum> forums) {
		this.forums = forums;
	}
	@Override
	public String toString() {
		return "Category [id=" + id + ", name=" + name + ", myorder=" + myorder + ", forums=" + forums + "]";
	}

	
}
