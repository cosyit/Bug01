package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.junit.Test;

import com.cosyit.lmbbs.dao.base.RowMapper;
import com.cosyit.lmbbs.dao.impl.UserDaoImpl;
import com.cosyit.lmbbs.entity.User;

public class UserDaoTest {
	UserDaoImpl userdao=new UserDaoImpl();
	
	//测试插入一条记录
	@Test
	public void testUserDao1() {
		String sql = "insert into lm_user (id,loginName,passWorD,Email)values(?,?,?,?);";
		String id = UUID.randomUUID().toString();
		int n = userdao.saveOrUpdate(sql, id,"mumu","mumu","mumu@163.com");
		System.out.println(n);
	}
	
	//测试根据主键查询
	@Test
	public void testUserDao2() {
		User user = userdao.findById("41203ce3-3946-4aa1-95ee-b09e772e2558", "select * from lm_user where id = ?", new RowMapper<User>() {
			@Override
			public User readToObjFromDBRow(ResultSet rs) throws SQLException {
				User user = new User();
				user.setId(rs.getString("id"));
				user.setNickName(rs.getString("nickNAME"));
				user.setGender(rs.getString("gender"));
				user.setLoginName(rs.getString("loginName"));
				//...不继续设值了。
				return user;
			}
			
		});
		System.out.println(user.getLoginName());
	}
	
	//测试修改密码
	@Test
	public void testPassword() {
		String sql = "update lm_user set password = ? where id = ?";
		int i =userdao.saveOrUpdate(sql, "666666","41203ce3-3946-4aa1-95ee-b09e772e2558");
		System.out.println(i);
	}
	
}
