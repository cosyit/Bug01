package com.cosyit.lmbbs.service.interfaces;

import java.util.List;

import com.cosyit.lmbbs.entity.Forum;
import com.cosyit.lmbbs.entity.Topic;

//讲来，他的子类ForumServiceImpl类，讲会把本来的所有方法具体实现。
public interface ForumService {
	
	/**
	 * 通过传入forumId 
	 * @param forumId 传入的id 【forum读音：佛入耳呃么】
	 * @return 获得所有的话题帖子数的List集合
	 */
     public List<Topic> getTopics(String forumId);
     
   /**
    * 将子版对象添加到对应id的板块中
    * @param forum 子版对象
    * @param categoryId 版块id，添加到哪个版块。
    * @return 返回受影响的条数
    */
     public int addForum(Forum forum, String categoryId);
     
     /**
      * 根据forumId 查找 Forum对象。
      * @param id  子版id查找
      * @return 返回查找出来的对象。
      */
     public Forum findById(String id);
     
     /**
      * 删除的业务:根据forumId来删除
      * @param forumId
      * @return 受影响的条数。
      */
     public int delete(String forumId);
}
