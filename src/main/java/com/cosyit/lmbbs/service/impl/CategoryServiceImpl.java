package com.cosyit.lmbbs.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import com.cosyit.lmbbs.dao.base.RowMapper;
import com.cosyit.lmbbs.dao.interfaces.CategoryDao;
import com.cosyit.lmbbs.dao.interfaces.ForumDao;
import com.cosyit.lmbbs.entity.Category;
import com.cosyit.lmbbs.entity.Forum;
import com.cosyit.lmbbs.factory.ServiceFactory;
import com.cosyit.lmbbs.service.interfaces.CategoryService;
import com.cosyit.lmbbs.service.interfaces.ForumService;

public class CategoryServiceImpl implements CategoryService {
	
	//这个地方用的是接口引用，而不是实例引用哦！这样方便解耦。
	private CategoryDao categoryDao;
	private ForumDao forumDao;
	
	//你用的时候，需要初始化该类对象的实例，那么你这个时候，传入的categoryDao就是CategoryDaoImpl的实例。
	public void setcategoryDao(CategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}
	
	public void setForumDao(ForumDao forumDao) {
		this.forumDao = forumDao;
	}
	
	//它业务层的那些具体实现类作为他的属性。来进行注入。ForumServiceImpl作为他的属性的类       
	private ForumService forumService=ServiceFactory.getForumService();
	
	@Override
	public int addCategory(Category c) {
		c.setId(UUID.randomUUID().toString());
		c.setOrder(categoryDao.getMaxOrder() + 1);
		/*INSERT INTO table_name ( field1, field2,...fieldN )
            VALUES
            ( value1, value2,...valueN );
*/
		String sql = "insert into lm_category values(?,?,?)";
		int n = categoryDao.saveOrUpdate(sql, c.getId(), c.getName(), c.getOrder());
		return n;

	}

	@Override
	public Category findById(String id) { 
		//根据板块的id查找返回到的category对象。
		Category category= categoryDao.findById(id, "select * from lm_category where id=?", new CategoryRowMapper());
		return category;
	}
	
	
	@Override
	public List<Forum> findForums(String CategoryId) {
		Collection<String > forumIds = forumDao.findForeignId(CategoryId, "select * from lm_forum where categoryId = ?");
		List<Forum> list = new ArrayList<Forum>();
		for(String forumid:forumIds) {
			/**
			 * 思路很重要!拿到id组，进行循环。在每个小循环中，根据id查询所有信息，来封装对象。
			 */
			Forum forum = forumDao.findById(forumid, "select * from lm_forum where id = ?", new  ForumServiceImpl.ForumRowMapper());
			list.add(forum);
		}
		return list;
	}

	@Override
	public List<Category> findAll() {
		return categoryDao.findAll("select * from lm_category", new CategoryRowMapper());
	}
	
	@Override
	public int updateCategory(String id, String name) {
		return categoryDao.saveOrUpdate("update lm_category set name=?where id=?", name,id);
	}
	
	private class CategoryRowMapper implements RowMapper<Category> {
		@Override
		public Category readToObjFromDBRow(ResultSet rs) throws SQLException {
			//数据库模型。空对象。
			Category c = new Category();
			c.setId(rs.getString("id"));
			c.setName(rs.getString("name"));
			c.setOrder(rs.getInt("order"));
			
			//根据categoryId查询出一组ForumId，通过ForumId来获取
			Collection<String> forumIds=categoryDao.findForeignId(rs.getString("id"), "select id from lm_forum where categoryId=?");//调用接口的方法，就会调用子类的方法吗？
			//并且声明一个集合，用来存放子版块的对象。存在HashSet集合里面。
			HashSet<Forum> forums=new HashSet<Forum>();
			for(String string:forumIds){
				//根据子版id获取子版对象。
				Forum forum=forumService.findById(string);
				//把forum的Category属性赋值。get,set中的set赋值操作而已。
				forum.setCategory(c);
				//把本次循环查到的“子版”对象加到子版集合里。
				forums.add(forum);
			}
			//把子版面的集合设置为版面对象的属性
			c.setForums(forums);
			//END!---意味着我们在获取板块category对象的时候就已经把forums子版块包装进去了。
			return c;
		}

	}
}
