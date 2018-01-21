package dao;

import com.cosyit.lmbbs.entity.Role;
import com.cosyit.lmbbs.entity.Systemprivilege;
import com.cosyit.lmbbs.factory.ServiceFactory;
import com.cosyit.lmbbs.service.interfaces.RoleService;

public class RoleServiceImplTest {
	public static void main(String[] args) {
		//通过工厂拿的就是初始化好的完美实现类的实例对象。
		RoleService rs=ServiceFactory.getRoleService();

		
		
		//通过这个完美实例，找一个Role对象
		Role role=rs.findById("0001");
		//遍历！查这个角色的的所有的权限。
		for(Systemprivilege s:role.getSystemprivileges()){
			System.out.println(s.getName()+","+s.getResource()+","+s.getAction());
		}
	}
}
