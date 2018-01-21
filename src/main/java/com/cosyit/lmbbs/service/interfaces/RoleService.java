package com.cosyit.lmbbs.service.interfaces;

import java.util.List;

import com.cosyit.lmbbs.entity.Role;

public interface RoleService {
	/**
	 * 查看所有的角色
	 * @return
	 */
	public List<Role> findAllRoles();
	
	/**
	 * 该角色拥有的所有权限
	 * @param id
	 * @return
	 */
	public Role findById(String id);
	
	/**
	 * 查询某个id角色，是否拥有某权限id
	 * @param role
	 * @param id
	 * @return
	 */
	public boolean contains(Role role, String id);
	
	/**
	 * 给角色加加权限。
	 * @param roleId 角色id
	 * @param sysId 权限id
	 * @return
	 */
	public int addRole_Sys(String roleId, String sysId);
	
	/**
	 * 根据角色的id删除 
	 * @param roleId
	 * @return
	 */
	public int delRole_Sys(String roleId);
	
	/**
	 * 添加角色并分配权限的操作---其实这种东西，将来都要加事务的。
	 * @param role
	 * @param sysIds 分配的权限id 组。
	 */
	public void addRoleAndSys(Role role, String[] sysIds);
	
	/**
	 * role(角色)的权限重新配置，能改名字，改描述。重新定义角色！
	 * @param role
	 * @param sysIds
	 */
	public void updateRoleAndSys(Role role, String[] sysIds);
	
	/**
	 * 根据角色的Id来删除角色
	 * @param roleId
	 */
	public void delRole(String roleId);
	
	/**
	 * 设置默认
	 * @param flag
	 * @param roleId
	 * @return
	 */
	public int setDefault(boolean flag, String roleId);
}
