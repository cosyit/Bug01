package com.cosyit.lmbbs.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.cosyit.lmbbs.dao.base.DaoSupport;
import com.cosyit.lmbbs.dao.interfaces.ForumDao;
import com.cosyit.lmbbs.util.jdbc.JDBCTools;
import com.cosyit.lmbbs.entity.Forum;

public class ForumDaoImpl extends DaoSupport<Forum> implements ForumDao{

	public int getMaxOrder() {
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		int maxOrder=0;
		con= JDBCTools.getConn();
		try {
			//实例：最大：select max(field1) as maxvalue from table1
			ps=con.prepareStatement("select max(`order`) from lm_forum");
			rs=ps.executeQuery();
			if(rs.next()){
				maxOrder=rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			JDBCTools.close(rs,ps,con);
		}
		return maxOrder;
	}
}
