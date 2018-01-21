package com.cosyit.lmbbs.dao.base;

import java.sql.ResultSet;
import java.sql.SQLException;
//以后让实现这个接口的子类，去自己包装他这个表的一些数据。
public interface RowMapper<T> {
	//通过结果集来获取数据库的行row，对应的应该是一个对象,我们在这里包装对象的
	/*子类不能抛出比父类更多的异常*/
	public T readToObjFromDBRow(ResultSet rs) throws SQLException;
}


//这个类里将来就是包装对象，进行的就是这些操作
//t.setId(rs.getString("id_"));
//t.setNickName_(rs.getString("nickname_"));
//t.setPassword_(rs.getString("password_"));
//t.setEmail(rs.getString("email_"));
//t.setLoginName_(rs.getString("loginName_"));
//return t;（←t泛指的未来需要的T类的实例对象。）