package com.cosyit.lmbbs.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
//子版块
public class Forum implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String description;
	private int myorder;
	private Category category;//子版块应该是和主板块是从属关系。java里是一种面向对象的关联关系。
	private int topicCount;
	private int articleCount;
	/*lastTopicId暂时没写，一会写？因为是一个帖子对象。*/
	private Topic lastTopic;//最后一个发布的帖子对象。
	private Date lastArticlePostTime;
	private Set<Topic> topics=new HashSet<Topic>();
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getMyorder() {
		return myorder;
	}
	public void setMyorder(int myorder) {
		this.myorder = myorder;
	}
	public Set<Topic> getTopics() {
		return topics;
	}
	public void setTopics(Set<Topic> topics) {
		this.topics = topics;
	}

	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
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
	public Date getLastArticlePostTime() {
		return lastArticlePostTime;
	}
	public void setLastArticlePostTime(Date lastArticlePostTime) {
		this.lastArticlePostTime = lastArticlePostTime;
	}
	public Topic getLastTopic() {
		return lastTopic;
	}
	public void setLastTopic(Topic lastTopic) {
		this.lastTopic = lastTopic;
	}
	@Override
	public String toString() {
		return "Forum [id=" + id + ", name=" + name + ", description=" + description + ", myorder=" + myorder
				+ ", category=" + category + ", topicCount=" + topicCount + ", articleCount=" + articleCount
				+ ", lastTopic=" + lastTopic + ", lastArticlePostTime=" + lastArticlePostTime + ", topics=" + topics
				+ "]";
	}
	
}
