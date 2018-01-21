package dao;

import com.cosyit.lmbbs.entity.User;
import com.cosyit.lmbbs.factory.ServiceFactory;
import com.cosyit.lmbbs.service.interfaces.UserService;
import com.cosyit.lmbbs.util.des.DESUtils;
import com.cosyit.lmbbs.util.query.PageView;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

public class UserServiceTest {
	
	UserService us=ServiceFactory.getUserService();
	
	@Test
	public void testM1() {
		User davi=new User();
		davi.setLoginName("superman");
		davi.setEmail("610495444@qq.com");
		davi.setGender("男");
		davi.setId("0001");
		davi.setNickName("大为");
		davi.setPassword("123456");
		us.registerUserResult(davi);
	}
	
	
	@Test
	public void testM2() {
		User user=new User();
		user.setId(UUID.randomUUID().toString());
		user.setLoginName("初心");
		//md5加密
		user.setPassword(DESUtils.encode("davi"));
		user.setEmail("http://qun.qzone.qq.com/group#!/477903591/home");
		user.setNickName("破壳期");
		user.setLocked(false);
		user.setGender("无");
		int n=us.registerUserResult(user);
		System.out.println(n);
	}
	
	//查询
	@Test
	public void testM3() {
		String loginName="汪大为";
		String password="666666";
		us.findByLoginNameAndPassword(loginName, password);
		boolean result=us.checkLoginNameIsAvailable(loginName);
		System.out.println(result);
	}
	
	
	@Test //查询所有
	public void testM4() {
			List<User> users= us.findAll(null,null,null,null);
			System.out.println(users.size());
			for(User user:users){
				System.out.println(user.getLoginName()+","+user.getNickName());
			}
			System.out.println("=============================================");
			PageView pageView=us.findAll(null, null, null,null, 1, 1);
	}
	
}
