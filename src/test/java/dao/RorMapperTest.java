package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.cosyit.lmbbs.dao.base.DaoSupport;
import com.cosyit.lmbbs.dao.base.RowMapper;
import com.cosyit.lmbbs.entity.Systemprivilege;

public class RorMapperTest {
public static void main(String[] args) {
	DaoSupport<Systemprivilege> ds=new DaoSupport<Systemprivilege>();
	List<Systemprivilege> ss=ds.findAll("select * from lm_systemprivilege ", new RowMapper<Systemprivilege>() {
		
		@Override
		public Systemprivilege readToObjFromDBRow(ResultSet rs) throws SQLException {
			//如何包装呢？---思路顺序随意：1.函数名是要做什么事情。
			//2.方法里传递进来的是什么参数，参数就是我们的思路资源，关于参数有哪些操作。
			//3.看返回什么对象。对象如何包装。就给看对象有哪些属性，给属性赋值。
			Systemprivilege s=new Systemprivilege();
			s.setId(rs.getString("id"));
			s.setName(rs.getString("name"));
			s.setResource(rs.getString("resource"));
			s.setAction(rs.getString("action"));
			System.out.println(s);
			return s;//对象包装好了,return。
		}
	});//全部查询不需要第三个参数了。
	
	for(Systemprivilege s:ss){
		System.out.println(s.getName());
	}
}
}
