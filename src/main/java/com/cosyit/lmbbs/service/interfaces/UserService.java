package com.cosyit.lmbbs.service.interfaces;

import java.util.List;

import com.cosyit.lmbbs.entity.Role;
import com.cosyit.lmbbs.entity.User;
import com.cosyit.lmbbs.util.query.PageView;


public interface UserService {
	/**
	 * 返回用户组集合的size是否为0
	 * 	List<User> users = ud.findAll("select * from lm_user where loginName= ? ", new UserRowMapper(), loginName);
		return users.size() == 0;
	 * @param loginName
	 * @return 是否可用的结果。
	 */
	public boolean checkLoginNameIsAvailable(String loginName);

	/**
	 * 保证用户密码不重复
	 * size==0 为真时，说明没有重复。可用。
	 * @param email
	 * @return
	 */
	public boolean checkEmailIsAvailable(String email);

	/**
	 *  根据用户名，密码查询
	 * @param loginName
	 * @param password
	 * @return
	 */
	public User findByLoginNameAndPassword(String loginName, String password);

	/**
	 *  传入用户和密码，改此用户的密码。
	 * @param user 用户
	 * @param newPassword 新密码
	 * @return
	 */
	public User changePassord(User user, String newPassword);

	/**
	 *  注册用户
	 * @param user
	 * @return
	 */
	public int registerUserResult(User user);

	/**
	 * 根据用户的Id查找出其对应的角色
	 * @param userId
	 * @return
	 */
	public List<Role> getRole(String userId);

	/**
	 *  锁用户
	 * @param id
	 */
	public void lock(String id);

	/**
	 *  解用户
	 * @param id
	 */
	public void unlock(String id);
	
	/**
	 * 业务参考：
	 * 检查用户是否被锁，
	 * 查看哪些用户被锁，
	 * 锁多久时间等等的业务。
	 */
	
	/**
	 *  自动登录的 autoLoginAuthKey修改
	 * @param user
	 */
	public void changeAutoLoginAuthKey(User user);

	/**
	 *  根据主键查找用户
	 * @param id
	 * @return
	 */
	public User findById(String id);
	
	/**
	 * 全部查询
	 * @param loginName
	 * @param nickName
	 * @param locked
	 * @param roleId
	 * @return
	 */
//	public List<User> findAll();
	public List<User> findAll(String loginName, String nickName, String locked, String roleId);
	
	/**
	 * 带分页的全部查询（其实可以和上一个方法合并）
	 * @param loginName
	 * @param nickName
	 * @param locked
	 * @param roleId
	 * @param pageSize
	 * @param currentPage
	 * @return
	 */
	public PageView findAll(String loginName, String nickName, String locked, String roleId, int pageSize, int currentPage);
	
	/**
	 * 修改角色
	 * @param userId
	 * @param roleIds
	 */
	public void updateUserRole(String userId, String[] roleIds);

}
