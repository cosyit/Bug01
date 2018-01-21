package com.cosyit.lmbbs.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.cosyit.lmbbs.dao.base.DaoSupport;
import com.cosyit.lmbbs.dao.interfaces.TopicDao;
import com.cosyit.lmbbs.util.jdbc.JDBCTools;
import com.cosyit.lmbbs.entity.Topic;

public class TopicDaoImpl extends DaoSupport<Topic> implements TopicDao{

	@Override
	public int getMaxFloor(String forumId) {
		// TODO Auto-generated method stub
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		int maxFloor=0;
		try {
			con= JDBCTools.getConn();
			ps=con.prepareStatement("select max(nextFloor) from lm_topic where forumId=?");
			ps.setString(1, forumId);
			rs=ps.executeQuery();
			if(rs.next()){
				maxFloor=rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			JDBCTools.close(rs,ps, con);
		}
		return 0;
	}

}
