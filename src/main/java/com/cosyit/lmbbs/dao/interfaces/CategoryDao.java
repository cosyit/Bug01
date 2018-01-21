package com.cosyit.lmbbs.dao.interfaces;

import com.cosyit.lmbbs.dao.base.Dao;
import com.cosyit.lmbbs.entity.Category;

//主板块的Dao接口
public interface CategoryDao extends Dao<Category> {
	// 找到最大的顺序值的方法。
	public int getMaxOrder();

}
