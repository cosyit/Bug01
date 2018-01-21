package com.cosyit.lmbbs.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Topic extends Article{
	private String type;//置顶帖，精华帖等。。。
	private Forum forum;//帖子属于哪个版块。
	private String summary;//帖子的摘要
	private int viewCount;//帖子的阅览数
	private int replyCount;//帖子的回复数量
//	private 最后一个回复还没写一会补上。
	private Reply lastReply;//最后一个回复。
	private Date lastArticlePostTime;//最后回复的时间
	private Set<Reply> replys=new HashSet<Reply>();
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Forum getForum() {
		return forum;
	}
	public void setForum(Forum forum) {
		this.forum = forum;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public int getViewCount() {
		return viewCount;
	}
	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}
	public int getReplyCount() {
		return replyCount;
	}
	public void setReplyCount(int replyCount) {
		this.replyCount = replyCount;
	}
	public Date getLastArticlePostTime() {
		return lastArticlePostTime;
	}
	public void setLastArticlePostTime(Date lastArticlePostTime) {
		this.lastArticlePostTime = lastArticlePostTime;
	}
	public Reply getLastReply() {
		return lastReply;
	}
	public void setLastReply(Reply lastReply) {
		this.lastReply = lastReply;
	}
	public Set<Reply> getReplys() {
		return replys;
	}
	public void setReplys(Set<Reply> replys) {
		this.replys = replys;
	}
	@Override
	public String toString() {
		return "Topic [type=" + type + ", forum=" + forum + ", summary=" + summary + ", viewCount=" + viewCount
				+ ", replyCount=" + replyCount + ", lastReply=" + lastReply + ", lastArticlePostTime="
				+ lastArticlePostTime + ", replys=" + replys + "]";
	}
	
	
}
