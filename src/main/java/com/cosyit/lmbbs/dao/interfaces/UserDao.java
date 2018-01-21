package com.cosyit.lmbbs.dao.interfaces;
//既然设计一个UserDao,为什么还设计UserService呢？
import java.util.List;

import com.cosyit.lmbbs.dao.base.Dao;
import com.cosyit.lmbbs.entity.User;

//除了增删改查等通用的sql操作外的一些，特殊的业务操作用户特有的操作。比如：我要查看用户所拥有的操作。
public interface UserDao extends Dao<User>{   
//	设计思考：
	
	//为什么不继承DaoSupport呢？---大概这里是处理user表涉及的特殊业务吧。
//  public List<Role> getRoles(String userId);//如果涉及到多个表的查询的时候，应该写到业务里面。
//比如说我是不是用这样的需要，根据某个用户查询该用户所拥有的角色。那么就要查用户和角色的关联表。业务里面就会多一张表那么这张表的DAO写在哪里呢？
//	那么就有一个操作查的就是集合，根据什么来查？--根据用户的ID，那么我们就想一下》
//	public List<String> getRoles(String userId);//这个集合里放的是什么根据用的ID，查到的角色的ID。
	//其实我们已经实现了：   //全部查询感觉也方便，只是不想做分页的接口。
//在Dao中  List<T> findAll(String sql,RowMapper<T> rm,Object...obj);可变参数传进来的时候我们可以是有条件的。
	//只不过我在分装结果集的时候还要分装对象吗？？？？？？？？？？？可能就不需要分装对象了。因为我拿到只是一个ID。按说查询的时候都要封装对象，但是我
	//我查询的只是某一列。举例：我传的sql语句是：select roleId_ from wyzc_role_user where userId_=?。
//继续想：修改密码要不要自己去写？修改密码的是不是savaOrUpdate(String sql....)  在这些地方主要思考的是有没有特殊的操作。有的就写出来，
	//没有特殊的操作，在继承的父类就已经解决了的话，这里就不要费劲再去写了。
	//就不要再管了。将来有特殊的操作的时候再写进来就可以了。想不到后面有没有，先放着，真用不上再删除掉即可。

}