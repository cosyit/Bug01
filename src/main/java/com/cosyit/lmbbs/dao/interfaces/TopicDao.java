package com.cosyit.lmbbs.dao.interfaces;

import com.cosyit.lmbbs.dao.base.Dao;
import com.cosyit.lmbbs.entity.Topic;
//topicDao---topic的完美指令集类
public interface TopicDao extends Dao<Topic>{
	public int getMaxFloor(String forumId);
}
