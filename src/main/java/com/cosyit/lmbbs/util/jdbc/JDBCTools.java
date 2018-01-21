package com.cosyit.lmbbs.util.jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JDBCTools {
    /* 数据源信息：
     * 正常的项目中都会有数据源。所以要定义数据源属性。
     * private static DataSource dataSource=null;
     * 常用的有c3p0和DBCP。技术不难，请加入到项目中
     */
    static String driverClass = null;
    static String url = null;
    static String username = null;
    static String password = null;

    //利用静态块给数据源信息初始化，加载了这个类，即有值。不希望每次创建连接就读取一次配置文件，只希望读一次后一直保存在内存。
    static {
        Properties prop = new Properties();
        InputStream in = JDBCTools.class.getClassLoader().getResourceAsStream("jdbc.properties");
        try {
            prop.load(in);
        } catch (IOException e) {
            Logger.getLogger(JDBCTools.class.getName()).log(Level.SEVERE, null, e);
        }
        //System.out.println(prop);

        //以下的参数具体请参照定义的jdbc.properties文件。
        driverClass = prop.getProperty("jdbc.driverClass");
        url = prop.getProperty("jdbc.url");
        username = prop.getProperty("jdbc.username");
        password = prop.getProperty("jdbc.password");
        // System.out.println(url + "," + password + "," + username + "," + driverClass);
    }


    public static Connection getConn() {
        try {
            Class.forName(driverClass);
            Connection con = DriverManager.getConnection(url, username, password);
            return con;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 不支持事务
     * 关闭连接，请注意关闭顺序
     * 编码技巧 ：
     * rs.isClosed在if()中，需要抛异常。rs.close()在 if {}
     * 也要抛出异常，对于这种情况，不如有一个大大的try catch将他们包起来。
     */
    public static void close(ResultSet rs, PreparedStatement ps, Connection con) {

        //首先关闭ResultSet对象。
        try {
            if (rs != null && !rs.isClosed()) rs.close();
        } catch (Exception e) {
            Logger.getLogger(JDBCTools.class.getName()).log(Level.SEVERE, null, e);
        }
        //然后关闭ps
        try {
            if (ps != null && !ps.isClosed()) ps.close();
        } catch (Exception e) {
            Logger.getLogger(JDBCTools.class.getName()).log(Level.SEVERE, null, e);
        }

        //con 最先产生，最后关闭
        try {
            if (con != null && !con.isClosed()) con.close();
        } catch (Exception e) {
            Logger.getLogger(JDBCTools.class.getName()).log(Level.SEVERE, null, e);
        }
    }


    // 以下支持事务，ThreadLocal 里面放的是数据库的连接。保证同一个线程里是同一个对象。
    // http://blog.csdn.net/wdw18668109050/article/details/78999241
    private static ThreadLocal<Connection> connectionThreadLocal = new ThreadLocal<Connection>();

    /**
     * 获取连接 支持事务的
     *
     * @return
     */
    public static Connection getConnection() {
        Connection con = null;
        con = connectionThreadLocal.get(); //从当前线程中去获取这个变量的引用。

        if (con == null) {//如果本地线程变量副本中没有线程。创建连接对象。把这个对象设置进connectionThreadLocal
            try {
                Class.forName(driverClass);
                con = DriverManager.getConnection(url, username, password);
                connectionThreadLocal.set(con);
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        //返回的时候，不直接返回con,而是从本地线程中返回。
        return connectionThreadLocal.get();
    }

    /**
     * 在有事务的时候，单独关闭Connection对象
     * 由于getConnection中的事务代码，
     * 关闭的时候，不能简单的关闭con了，
     * 关闭之前先把con参数从close方法参数中去掉。
     * con的关闭需等业务操作完成再关闭。
     * 在事务的时候需要做这个？
     * closeConnection(ResultSet rs, PreparedStatement ps)
     */
    public static void closeConnection() {
        //con不需要作为参数传入,是同一个线程中的con对象
        Connection con = connectionThreadLocal.get();
        try {
            if (con != null && !con.isClosed()) con.close();
            //关闭后把con从ThreadLocal中移除。
            connectionThreadLocal.remove();
        } catch (Exception e) {
            Logger.getLogger(JDBCTools.class.getName()).log(Level.SEVERE, null, e);
        }
    }

}