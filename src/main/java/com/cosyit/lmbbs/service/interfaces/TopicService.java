package com.cosyit.lmbbs.service.interfaces;

import java.util.List;

import com.cosyit.lmbbs.entity.Topic;

public interface TopicService {
	/**
	 * 加帖子
	 * @param topic
	 * @param forumId
	 * @param authorId
	 */
	public void addTopic(Topic topic, String forumId, String authorId);
	
	/**
	 * 根据传入的forumId 查该id下所有的topic
	 * @param forumId
	 * @return  该id下所有的topic
	 */
	public List<Topic> getAllTopicis(String forumId);
	
	/**
	 * 返回当前forumId下。
	 * @param forumId
	 * @return
	 */
	public Topic lastTopic(String forumId);
	
	/**
	 * 根据帖子id查询帖子对象
	 * @param topicId
	 * @return
	 */
	
	/**
	 * 根据Id查到该id对应的对象。
	 * @param topicId
	 * @return
	 */
	public Topic findById(String topicId);
}
