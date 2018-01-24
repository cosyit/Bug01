package com.cosyit.lmbbs.service.interfaces;

import java.util.Collection;
import java.util.List;

import com.cosyit.lmbbs.entity.Category;
import com.cosyit.lmbbs.entity.Forum;

//业务接口
public interface CategoryService {
	/**
	 * 增： 添加category对象
	 * @param C
	 * @return
	 */
	public int addCategory(Category C);

	/**
	 * 查：
	 * 根据主键来查询
	 * @param id
	 * @return
	 */
	public Category findById(String id);

	/**
	 * 查：根据版块id,查询所有子版块集合。
	 * @param CategoryId
	 * @return
	 */
	public Collection<Forum> findForums(String CategoryId);

	/**
	 * 查：查询所有的版面
	 * @return
	 */
	public List<Category> findAll();
	
	/**
	 *	改：修改版面
	 * @param id
	 * @param name
	 * @return
	 */
	public int updateCategory(String id, String name);


	public int deleteCategoryById(String id);
}
