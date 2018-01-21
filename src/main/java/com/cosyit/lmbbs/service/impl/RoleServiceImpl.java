package com.cosyit.lmbbs.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.cosyit.lmbbs.dao.base.RowMapper;
import com.cosyit.lmbbs.dao.interfaces.RoleDao;
import com.cosyit.lmbbs.entity.Role;
import com.cosyit.lmbbs.entity.Systemprivilege;
import com.cosyit.lmbbs.service.interfaces.RoleService;
import com.cosyit.lmbbs.service.interfaces.SystemprivilegeService;

public class RoleServiceImpl implements RoleService {
	private RoleDao rd;
	private SystemprivilegeService ss;

	public void setSs(SystemprivilegeService ss) {
		this.ss = ss;
	}

	public void setRd(RoleDao rd) {
		this.rd = rd;
	}

	// 查询所有的角色（页面显示需要这个业务）
	@Override
	public List<Role> findAllRoles() {

		return rd.findAll("select * from lm_role", new RoleRowMapper());
	}

	//
	@Override
	public Role findById(String id) {
		// TODO Auto-generated method stub
		return rd.findById(id, "select * from lm_role where id=?", new RoleRowMapper());
	}

	// 写一个内部类，我们希望拿到这个角色的时候，权限都已经包装进去了。
	public class RoleRowMapper implements RowMapper<Role> {
		@Override
		public Role readToObjFromDBRow(ResultSet rs) throws SQLException {
			Role role = new Role();
			role.setId(rs.getString("id"));
			role.setDescription(rs.getString("description"));
			role.setName(rs.getString("name"));
			role.setDefaultForNewUser(rs.getBoolean("defaultForNewUser"));
			
			// 临时中间表：根据角色的id查权限的id：该角色拥有哪些权限
			Collection<String> ids = rd.findForeignId(role.getId(),
					"select SystemprivilegeId from lm_role_Systemprivileges where roleId=?");
			// 包装角色所拥有的权限。role里面的属性内存是set->private Set<Systemprivilege> systemprivileges;
			Set<Systemprivilege> sps = new HashSet<Systemprivilege>();
			for (String string : ids) {
				Systemprivilege s = ss.findById(string);
				sps.add(s);
			}
			//把这个集合设置为它的属性值。这样就包装进去了。
			role.setSystemprivileges(sps);
			return role;
		}

	}

	//查询某个id角色，是否拥有某权限id
	@Override
	public boolean contains(Role role, String id) {
		// 获得这个角色拥有的所有权限,用一个set集合内存空间来接收。
		Set<Systemprivilege> set = role.getSystemprivileges();
		// 遍历这个set集合。
		for (Systemprivilege systemprivilege : set) {
			// 将每一个集合的元素，拿到它的id参数的id对比。
			if (systemprivilege.getId().equals(id)) {
				return true;
			}
		}
		return false;
	}

	//添加权限
	@Override
	public int addRole_Sys(String roleId, String sysId) {

		return rd.saveOrUpdate("insert into lm_role_systemprivileges(roleId,systemPrivilegeId)values(?,?)",
				roleId,sysId);
	}
	
	//删除权限
	@Override
	public int delRole_Sys(String roleId) {
		// 一下子全部删掉了。删掉的不止一条记录。（逻辑粗暴，性能较差）
		return rd.deleteById(roleId, "delete from lm_role_systemprivileges where roleId=?");
	}

	//添加角色并给角色分配权限，将来需要做事务处理。
	@Override
	public void addRoleAndSys(Role role,String[] sysIds) {
		//添加的时候，我们自己就把这个角色的id默认设置了。给角色加的id用UUID这样子来保证唯一。
		role.setId(UUID.randomUUID().toString());
		role.setDefaultForNewUser(false);//经过这种处理的，都不是默认用户了。
		rd.saveOrUpdate("insert into lm_role(id,name,description,defaultForNewUser)values(?,?,?,?)", role.getId(),role.getName(),role.getDescription(),role.isDefaultForNewUser());
		//接下来我们要分role（角色）分配权限。
//		Set<Systemprivilege> systemprivileges=role.getSystemprivileges();//我们调用业务的时候，肯定会给他包装好了，所以我们默认这里有权限。
		for (String sysId : sysIds) {
			rd.saveOrUpdate("insert into lm_role_systemprivileges(roleId,systemprivilegeId)values(?,?)",role.getId(),sysId);
		}
	}

	@Override
	public void updateRoleAndSys(Role role, String[] sysIds) {
		rd.saveOrUpdate("update lm_role set name=?,description=?,where id=?", role.getName(),role.getDescription(),role.getId());
		//只要是这个id的我就全部删掉。
		this.delRole_Sys(role.getId());
		//通过循环来添加---我的知识点扩展：for,if是不存在作用域的，在本类中，上面的方法都是可以调用的。所以addRole_Sys(role.getId(), ssid)前面没有加任何this.
		for(String ssid:sysIds){
			addRole_Sys(role.getId(), ssid);//角色id,权限id添加到中间表里。
		}
	}

	@Override
	public void delRole(String roleId) {
		//正确的流程应该是：我们必须先要删除role的权限的信息，再去删除role。因为在关联的中间临时表里面记录role的信息。把role删了，权限还存在，逻辑是不通的。
		int n=rd.deleteById(roleId,
	"delete from lm_role_systemprivileges where roleId=?");//delRole_sys(roleId)干的事情一样，上面已经写了这步操作了。
		//删除了中间的关联信息之后，删除role
		int n1=rd.deleteById(roleId, "delete from lm_role where id=?");
		
	}

	@Override
	public int setDefault(boolean flag,String roleId) {
		// TODO Auto-generated method stub
		return rd.saveOrUpdate("update lm_role set defaultForNewUser=? where id=?", flag,roleId);
	}



}
