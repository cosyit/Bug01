package com.cosyit.lmbbs.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.cosyit.lmbbs.dao.base.RowMapper;
import com.cosyit.lmbbs.dao.interfaces.SystemprivilegeDao;
import com.cosyit.lmbbs.entity.Systemprivilege;
import com.cosyit.lmbbs.entity.Topic;
import com.cosyit.lmbbs.service.interfaces.SystemprivilegeService;

public class SystemprivilegeServiceImpl implements SystemprivilegeService{
	private SystemprivilegeDao sd;
	public void setSd(SystemprivilegeDao sd) {
		this.sd = sd;
	}
	//查询某个权限的id查对应的权限
	@Override
	public Systemprivilege findById(String id) {
		// id是个UUID，不影响吗？
		return sd.findById(id, "select * from lm_systemprivilege where id=?" , new SystemprivilegeRowMapper());
	}
	
	// 查询所有的权限
	@Override
	public List findAll() {
		return sd.findAll("select * from lm_systemprivilege", new SystemprivilegeRowMapper());
	}
	
	//写一个包装对象的内部类
	private class SystemprivilegeRowMapper implements RowMapper<Systemprivilege>{
		@Override
		public Systemprivilege readToObjFromDBRow(ResultSet rs) throws SQLException {
			Systemprivilege s=new Systemprivilege();
			s.setId(rs.getString("id"));
			s.setName(rs.getString("name"));
			s.setResource(rs.getString("resource"));
			s.setAction(rs.getString("action"));
			return s;
		}
	}
}
