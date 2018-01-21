package com.cosyit.lmbbs.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import com.cosyit.lmbbs.dao.base.RowMapper;
import com.cosyit.lmbbs.dao.interfaces.ForumDao;
import com.cosyit.lmbbs.entity.Forum;
import com.cosyit.lmbbs.entity.Systemprivilege;
import com.cosyit.lmbbs.entity.Topic;
import com.cosyit.lmbbs.service.interfaces.ForumService;

public class ForumServiceImpl implements ForumService {
	
	//加入需要用到的Dao对象进来。用和谁有关系的业务就加入谁的dao对象。
	private ForumDao fd;
	//提供一个方法，未来该类的对象可以把ForumDao作为
	public void setFd(ForumDao fd) {//ForumDao接口比DaoSupport多一个，获得最大值的方法。
		this.fd = fd;
	}
	@Override
	public List<Topic> getTopics(String forumId) {
		Collection<String> topicIds = fd.findForeignId(forumId, "" + "select id from lm_topic where forumId=?");
		List<Topic> topics = new ArrayList<Topic>();
		return topics;
	}

	@Override
	public int addForum(Forum forum,String categoryId) {
		//id和maxOrder的设定到对象中
		forum.setId(UUID.randomUUID().toString());
		forum.setMyorder(fd.getMaxOrder()+1);
		int n=fd.saveOrUpdate("insert into lm_forum(id,name,description,myorder,categoryId)values(?,?,?,?,?)", forum.getId(),
				forum.getName(),forum.getDescription(),forum.getMyorder(),categoryId);
		return n;
	}
	
	@Override
	public Forum findById(String id) {
	
		return fd.findById(id, "select * from lm_forum where id=?", new ForumRowMapper());
	}
	
	@Override
	public int delete(String forumId) {
		return fd.deleteById(forumId, "delete from lm_forum where id=?");
	}

	public static class ForumRowMapper implements RowMapper<Forum>{
		@Override
		public Forum readToObjFromDBRow(ResultSet rs) throws SQLException {
			Forum forum=new Forum();
			forum.setId(rs.getString("id"));//把结果集里拿到的id字段对应的查询结果拿出来设置到对象的id属性中。
			forum.setName(rs.getString("name"));//同理
			forum.setDescription(rs.getString("description"));
			forum.setMyorder(rs.getInt("myorder"));
			return forum;
		}
		
	}
}
