package com.cosyit.lmbbs.dao.interfaces;

import com.cosyit.lmbbs.dao.base.Dao;
import com.cosyit.lmbbs.entity.Forum;

//子板块的Dao接口
public interface ForumDao extends Dao<Forum>{
    public int getMaxOrder();
}
