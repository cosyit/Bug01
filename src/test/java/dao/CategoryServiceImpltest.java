package dao;
import java.util.Collection;
import java.util.Set;

import org.junit.Test;

import com.cosyit.lmbbs.entity.Category;
import com.cosyit.lmbbs.entity.Forum;
import com.cosyit.lmbbs.factory.ServiceFactory;
import com.cosyit.lmbbs.service.interfaces.CategoryService;

public class CategoryServiceImpltest {

	@Test
	public void testFindById() {
		CategoryService cs=ServiceFactory.getCategoryService();
		Category category=cs.findById("0a7a23f7-988c-4d04-9f74-751bc578b905");
		System.out.println(category.getName());
		//下面有问题根据板块查询子板块，应该有BUG
		Set<Forum> forums=category.getForums();
		for (Forum forum : forums) {
			System.out.println(forum.getName());
		}
	}
	
	//引用外键查询。
	@Test
	public void testQueryListByForeignKey() {
		//1.创建业务对象。用业务对象做业务。
		CategoryService categoryService=ServiceFactory.getCategoryService();
		// 根据一个外键id，来查找该id下对象下，所有的对象集合。
		Collection<Forum> forumes =categoryService.findForums("0a7a23f7-988c-4d04-9f74-751bc578b905");
		for(Forum forum:forumes) {
			System.out.println(forum.getId());
		}
	}
}
