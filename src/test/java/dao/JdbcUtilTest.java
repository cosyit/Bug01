package dao;

import java.sql.Connection;

import com.cosyit.lmbbs.util.jdbc.JDBCTools;

public class JdbcUtilTest {
	public static void main(String[] args) {
		Connection con= JDBCTools.getConn();
		System.out.println(con);
	}
}
