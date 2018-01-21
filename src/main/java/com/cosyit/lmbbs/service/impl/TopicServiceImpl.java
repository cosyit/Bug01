package com.cosyit.lmbbs.service.impl;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import com.cosyit.lmbbs.dao.base.RowMapper;
import com.cosyit.lmbbs.dao.interfaces.TopicDao;
import com.cosyit.lmbbs.entity.Topic;
import com.cosyit.lmbbs.factory.ServiceFactory;
import com.cosyit.lmbbs.service.interfaces.ForumService;
import com.cosyit.lmbbs.service.interfaces.TopicService;
import com.cosyit.lmbbs.service.interfaces.UserService;

public class TopicServiceImpl implements TopicService{
private ForumService fs=ServiceFactory.getForumService();
private UserService us=ServiceFactory.getUserService();
private TopicDao td;
public void setTd(TopicDao td) {
	this.td = td;
}
	@Override
	public void addTopic(Topic topic,String forumId,String authorId) {
		topic.setId(UUID.randomUUID().toString());
		int maxFloor=td.getMaxFloor(forumId);//利用接口调用dao完美实现指令类的方法
		topic.setFloor(maxFloor+1);
		String sql="insert into lm_topic(id,title,authorId,content,type,forumId,nextFloor)values(?,?,?,?,?,?,?)";
		td.saveOrUpdate(sql, topic.getId(),topic.getTitle(),authorId,topic.getContent(),topic.getType(),forumId,topic.getFloor());	
	}
	@Override
	public List<Topic> getAllTopicis(String forumId) {
		// TODO Auto-generated method stub
		return td.findAll("select * from lm_topic  where forumId=? order by nextFloor", new TopicRowMapper(),forumId);
	}
	
	private class TopicRowMapper implements RowMapper<Topic>{

		@Override
		public Topic readToObjFromDBRow(ResultSet rs) throws SQLException {
			Topic topic=new Topic();
			topic.setId(rs.getString("id"));
			topic.setContent(rs.getString("content"));
			topic.setTitle(rs.getString("title"));
			topic.setFloor(rs.getInt("nextFloor"));
			topic.setType(rs.getString("type"));
			topic.setForum(fs.findById(rs.getString("forumId")));
			topic.setUser(us.findById(rs.getString("authorId")));
			return topic;
		}
		
	}

	@Override
	public Topic lastTopic(String forumId) {
		// TODO Auto-generated method stub
		return getAllTopicis( forumId).get(0);//list.get(0)获取第一个
	}
	@Override
	public Topic findById(String topicId) {
		// TODO Auto-generated method stub
		return td.findById(topicId, "select * from lm_topic where id=?", new TopicRowMapper());
	}
}
