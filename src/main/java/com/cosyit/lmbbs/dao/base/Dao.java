package com.cosyit.lmbbs.dao.base;

import java.util.Collection;
import java.util.List;


/**
 接口是一组需求的描述。
 	DAO接口一般都是基础的crud操作：佟哥的代码逻辑还要加入con对象以方便做事务。
		c	void addObject(String sql ,Object[] args);
		r	void queryObject();
		u	void updateObject();
		d	·void deleteObject(Integer id);
			·void deleteObject(Object obj);

 RowMapper对象中常用的操作：把DB的每一个row携带的信息，映射方式为领域bean对象进行初始化。
		 beanInstance.setProperty(rs.getObject("DB_field");
*/
public interface Dao<T> {

	/**
	 * id,sql,以及他所对应的行映射的包装对对象---->来直接获取所有的对象。
	 * 
	 * @param id
	 *            根据id来查询
	 * @param sql
	 *            查询语句
	 * @param rm
	 *            具体包装的对象
	 * @return
	 */
	T findById(String id, String sql, RowMapper<T> rm);

	/**
	 * 分页查询 Object...obj表示可变参数。Object参数数组由问号的所对应的value组成的
	 * 
	 * @param PageSize
	 *            按多少页来做查询
	 * @param page
	 *            当前页
	 * @param rm
	 *            具体包装的对象
	 * @param obj
	 *            有没有条件，如果有条件，我们应该在这里给值
	 * @return
	 */
	List<T> findByPage(int PageSize, int page, String sql, RowMapper<T> rm, Object... obj);

	/**
	 * @param obj
	 * @return
	 */
	int saveOrUpdate(String sql, Object... obj);// 返回int如果int类型返回值>0的话就代表操作成功。
	
	/**
	 * 根据id删除一个对象
	 * @param id
	 * @param sql
	 * @return
	 */
	int deleteById(String id, String sql);
	
	
	/**
	 * 萌萌的查询所有
	 * @param sql
	 * @param rm
	 * @param obj
	 * @return
	 */
	List<T> findAll(String sql, RowMapper<T> rm, Object... obj);

	/**
	 * 根据一个外键的id,查询另外的id集合。
	 * @param id
	 * @param sql
	 * @return
	 */
	public Collection<String> findForeignId(String id, String sql);

	// 查询总记录数
	int getCount(String sql);
}
