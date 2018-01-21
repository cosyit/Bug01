package com.cosyit.lmbbs.entity;

public class Reply extends Article{
	private Topic topic;//该回复属于哪个帖子

	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}

	@Override
	public String toString() {
		return "Reply [topic=" + topic + "]";
	}
	
}
