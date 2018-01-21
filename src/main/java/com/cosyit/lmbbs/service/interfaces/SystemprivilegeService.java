package com.cosyit.lmbbs.service.interfaces;

import java.util.List;

import com.cosyit.lmbbs.entity.Systemprivilege;

public interface SystemprivilegeService {
	/**
	 * 根据传入的权限id查询 对象
	 * @param id
	 * @return 一个权限对象。
	 */
	Systemprivilege findById(String id);
	
	/**
	 * 查询系统中所有权限
	 * @return
	 */
	List<Systemprivilege> findAll();
}
