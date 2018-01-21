package com.cosyit.lmbbs.dao.base;

import com.cosyit.lmbbs.util.jdbc.JDBCTools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 我觉得DaoSupport中应该加入一个反射做的一个通用的RowMapper
 * @author Administrator
 *
 * @param <T>
 */
public class DaoSupport<T> implements Dao<T>{
	//Dao支持接口，这个里面是一些什么东西呢？
	//接口不能实例化，将来只能是实现接口的对象传进来。
	@Override
	public T findById(String id, String sql, RowMapper<T> rm) {
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		T t=null;//会返回一个对象的哦！
		try {
			con= JDBCTools.getConn();
			ps=con.prepareStatement(sql);
			/*将指定参数设置为给定 Java String 值。
			 * 在将此值发送给数据库时，驱动程序将它转换成一个 SQL VARCHAR 或 LONGVARCHAR 值
			 * （取决于该参数相对于驱动程序在 VARCHAR 值上的限制的相对大小。
			 * 然后下一步就可以交给数据库执行了）。*/
			ps.setString(1, id);//把sql中第一个？的值替换为id
			rs=ps.executeQuery();//执行这条查询
			while(rs.next()){
				//将来是帖子表，还是回复表，还是其他表。给他自己处理。我们这里就不管了。
				/*本例用类ResultSet对象rm的类方法中的getRow方法-->输出当前行编号。
				 * 我们不知道将来传什么进来，但是将来给你（RM行映射接口）自己去包装实现对象。
				 * 我们在这里就可以不管了。*/
				t=rm.readToObjFromDBRow(rs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			JDBCTools.close(rs,ps,con);
		}
		return t;
	}
	
	/**分页查询的sql认定是这样
	 * select * from tableName where ...?...? limit ?,?;
	 * 注意哦，where后面的2个？是固定的哦！i的动态变化就是利用了此现象。
	 * 	//情况假设：select * from aa where ...?...?（疑问：从这里截断提炼出来i+1个问号？）limit ?,?;
	 * 这里的问号的值组成Object参数数组
	 */
	@Override
	public List<T> findByPage(int pageSize, int page,String sql,
			RowMapper<T> rm,Object...obj) {
		List<T> ts=new ArrayList<T>();//用这个容器来储存数据
		int begin=(page-1)*pageSize+1;
//		int end=page*pageSize;//我的判断能力好低啊。
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			//获取连接对象
			con= JDBCTools.getConn();
			//该连接创建一个 PreparedStatement 对象来将参数化的 SQL 语句发送到数据库。
			ps=con.prepareStatement(sql);
			int i=0;
			for(i=0;i<obj.length;i++){
				/*遍历Object[] 参数para 数组向集合ps 中插入数据
				 * ｛把数组0号位置的对象放到集合ps 的第一个位置ps.setObject(1,para[0]);
				 * 把数组1号位置的对象放到集合ps 的第二个位置ps.setObject(2,para[1]);
				 * 把数组3号位置的对象放到集合ps 的第三个位置ps.setObject(3,para[2]);
				 * ..以此类推｝*/
				ps.setObject(i+1,obj[i] );
			}//从for循环里出来之后，又+1.最后条件不成立出来了。
			//以下2行是操作最后2个通配符
			ps.setInt(++i,begin);
			ps.setInt(++i, pageSize);
			// 在此 PreparedStatement 对象中执行 SQL 查询，并返回该查询生成的 ResultSet 对象。
			rs=ps.executeQuery();
			while(rs.next()){//返回值为true,存在元素的话
				T t=rm.readToObjFromDBRow(rs);//通过映射的对象把得到这个对象。
				ts.add(t);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			JDBCTools.close(rs,ps,con);
			
		}
		return ts;//把这些对象们返回出去。
	}
	
	
	//改变数据库的操作（增改删）
	/*删除不放在里面，删除可能是根据主键来删除*/
	@Override
	public int saveOrUpdate(String sql, Object... obj) {
		Connection con=null;
		PreparedStatement ps=null;
		int n=0;
		try {
			con= JDBCTools.getConn();
			ps=con.prepareStatement(sql);
			for(int i=0;i<obj.length;i++){
				ps.setObject(i+1, obj[i]);
			}
			n=ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			JDBCTools.close(null,ps,con);
		}
		return n;
	}

	@Override
	public int deleteById(String id, String sql) {
		Connection con=null;
		PreparedStatement ps=null;
		int n=0;
		try {
			con= JDBCTools.getConn();
			ps=con.prepareStatement(sql);
			//void setString看源代码
			ps.setString(1, id);//执行一个的动作，所以返回值为void，此处代表第一个?参数为id。
			n=ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			JDBCTools.close(null, ps,con);
			
		}

		return n;
	}


	@Override
	public List<T> findAll(String sql, RowMapper<T> rm, Object... objs) {
		List<T> ts=new ArrayList<T>();
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			con= JDBCTools.getConn();
			ps=con.prepareStatement(sql);
			int i=0;
			for(i=0;i<objs.length;i++)
			{
				ps.setObject(i+1, objs[i]);
			}
			rs=ps.executeQuery();
		
			while(rs.next()){
				T t=rm.readToObjFromDBRow(rs);
				ts.add(t);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			JDBCTools.close(rs,ps,con);
		}
		return ts;
	}


	@Override//为什么写这个通用操作：因为！
	//在forum表中，根据categoryid查询forumid，
	//在topic表中，根据forumId中查询所有的topicId（关联查询），
	//在reply表中，根据topicid查询所有的replyId
	//既然要做如此多相同的事情，我们能不能专门写一个Dao专门来做这样的事情：封装的都是id信息。
	public Collection<String> findForeignId(String id, String sql) {
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		Collection<String> ids=new ArrayList<String>();
		try {
			con= JDBCTools.getConn();
			ps=con.prepareStatement(sql);
			ps.setString(1, id);//第一条信息放id的值。中的1是指这个语句的第一个问号占位符设置为id的值。
			rs=ps.executeQuery();
			//这个sql语句肯定只是一个问号
			while(rs.next()){//向一个集合里面添加元素的时候，切记莫有if,if只做一次，while才会继续判断。
				ids.add(rs.getString(1));//就查一列，把第一列的值取出来添加给ids集合。
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			JDBCTools.close(rs,ps,con);
		}
		return ids;//返回包含的id集
	}

	@Override
	public int getCount(String sql) {
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		int count=0;
		try {
			con= JDBCTools.getConn();
			ps=con.prepareStatement(sql);
			rs=ps.executeQuery();
			//因为rs，是结果集，所有要遍历。
			if(rs.next()){
				//获得结果集的结果
				count=rs.getInt(1);//你可以通过索引或者列名来获得查询结果集中的某一列的值。
			}
		} catch (Exception e) {
		e.printStackTrace();
		}finally{
			JDBCTools.close(rs,ps,con);
		}
		return count;
	}

}
