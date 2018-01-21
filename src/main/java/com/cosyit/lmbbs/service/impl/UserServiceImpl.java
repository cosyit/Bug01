package com.cosyit.lmbbs.service.impl;
//该类定义的是用户独有的一些操作

import com.cosyit.lmbbs.dao.base.DaoSupport;
import com.cosyit.lmbbs.dao.base.RowMapper;
import com.cosyit.lmbbs.dao.interfaces.RoleDao;
import com.cosyit.lmbbs.dao.interfaces.UserDao;
import com.cosyit.lmbbs.entity.Role;
import com.cosyit.lmbbs.entity.User;
import com.cosyit.lmbbs.factory.ServiceFactory;
import com.cosyit.lmbbs.service.interfaces.RoleService;
import com.cosyit.lmbbs.service.interfaces.UserService;
import com.cosyit.lmbbs.util.des.DESUtils;
import com.cosyit.lmbbs.util.query.PageView;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class UserServiceImpl implements UserService {

    private UserDao ud;
    private RoleDao rd;
    private DaoSupport<String> baseDao;
    private RoleService roleService = ServiceFactory.getRoleService();

    public void setUd(UserDao ud) {
        this.ud = ud;
    }

    public void setRd(RoleDao rd) {
        this.rd = rd;
    }

    public void setBaseDao(DaoSupport<String> baseDao) {
        this.baseDao = baseDao;
    }

    @Override
    public boolean checkLoginNameIsAvailable(String loginName) {
        List<User> users = ud.findAll("select * from lm_user where loginName= ? ", new UserRowMapper(), loginName);
        return users.size() == 0;
    }

    @Override
    public boolean checkEmailIsAvailable(String email) {
        List<User> users = ud.findAll("select * from lm_user where email=?", new UserRowMapper(), email);

        return users.size() == 0;
    }

    @Override
    public User findByLoginNameAndPassword(String loginName, String password) {
        //添加加密的操作----》数据库DB的密码是密文
        System.out.println(password+","+password.length());
        String encodePassword = DESUtils.encode(password);
        System.out.println(encodePassword);
        List<User> users = ud.findAll("select * from  lm_user where loginName=? and password=?", new UserRowMapper(),
                loginName, encodePassword);//前台传来的密码，加密了之后和数据库进行匹配。只需要加密，不需要解密。
        if (users.size() > 0) {
            return users.get(0);
        }
        return null;
    }

    @Override
    public User changePassord(User user, String newPassword) {

        int n = ud.saveOrUpdate("update lm_user set password=? where id=?", newPassword, user.getId());
        if (n > 0)
            user.setPassword(newPassword);
        return user;
    }

    /**
     * 注册用户没有这么简单。你注册的用户名已经存在和email已经存在的，
     * 不能再存在了。
     * 如果存在了。就不能让你注册。
     * 不过这些操作我们
     * 将来可以放在控制器中去控制一下。
     */
    @Override
    public int registerUserResult(User user) {
        user.setId(UUID.randomUUID().toString());
        boolean checkLoginNameAvailable = checkLoginNameIsAvailable(user.getLoginName());
        boolean checkMailAvailable = checkEmailIsAvailable(user.getEmail());

        int n = 0;
        if (checkLoginNameAvailable && checkMailAvailable) {
            //必须2个条件都是同时可用，我们才更新。
            n = ud.saveOrUpdate(
                    "insert into lm_user(id,loginName,nickName,password,email,locked,gender)values(?,?,?,?,?,?,?)",
                    user.getId(), user.getLoginName(), user.getNickName(), user.getPassword(), user.getEmail(),
                    user.isLocked(), user.getGender());
            return n;
        } else {
            return -1;// 直接return 不进行更新。操作失败。
        }
    }

    @Override
    public List<Role> getRole(String userId) {
        List<String> roleIds = baseDao.findAll("select roleId from lm_user_role where userId=?",
                (rs) -> {
                    return rs.getString(1);
                }, userId);

        List<Role> roles = new ArrayList<Role>();

        for (String string : roleIds) {
            Role r = rd.findById(string, "select * from lm_role where id=?", new RowMapper<Role>() {
                @Override
                public Role readToObjFromDBRow(ResultSet rs) throws SQLException {
                    Role role = new Role();
                    role.setId(rs.getString("id"));
                    role.setName(rs.getString("name"));

                    return role;
                }
            });
            roles.add(r);
        }
        return roles;
    }

    @Override
    public void lock(String id) {
        // 查找用户
        User user = ud.findById(id, "select * from lm_user where id=?", new UserRowMapper());
        // 判断是否为超级管理=我们提前superman放入数据库里，初始化时候，默认有。
        if (!"superman".equals(user.getLoginName()))
            ud.saveOrUpdate("update lm_user set locked=? where id=?", true, id);
    }

    @Override
    public void unlock(String id) {
        // 查找用户
        User user = ud.findById(id, "select * from lm_user where id=?", new UserRowMapper());
        ud.saveOrUpdate("update lm_user set locked=? where id=?", false, id);
    }

    @Override // 数据中改变AutoLoginAuthKey的值（就是这这个String字符串：user.getId() + "_" +
    // expiretime）
    public void changeAutoLoginAuthKey(User user) {
        int n = ud.saveOrUpdate("update lm_user set autoLoginAuthKey = ? where id=?", user.getAutoLoginAuthKey(),
                user.getId());
    }

    // 根据主键查询用户
    @Override
    public User findById(String id) {
        return ud.findById(id, "select * from lm_user where id=?", new UserRowMapper());
    }

    @Override
    public List<User> findAll(String loginName, String nickName, String locked, String roleId) {
        String sql = "select u.id,u.loginName,u.password,u.email,u.nickName,u.locked,u.topicCount,u.articleCount "
                + "from lm_user u,lm_role r,lm_user_role ur "
                + "where 1=1  and u.id=ur.userId and r.id=ur.roleId ";
        if (loginName != null && !"".equals(loginName)) {
            sql += "and u.loginName like'%" + loginName + "%'";
        }
        if (nickName != null && !"".equals(nickName)) {
            sql += "and u.nickName like'%" + nickName + "%'";
        }
        if (locked != null && !"".equals(locked)) {
            boolean lock = Boolean.parseBoolean(locked);
            sql += "and u.locked=" + lock + "";
        }
        if (roleId != null && !"".equals(roleId)) {
            sql += " and r.id ='" + roleId + "'  ";
        }
        return ud.findAll(sql, new UserRowMapper());


//		//因为现在的列和之前的不一样了。我们现在查询的列数比较少，其他的列数都是没有的，我们需要自定义一个rowMapper类:匿名类部类。
//		return ud.findAll(sql, new RowMapper<User>() {
//
//			@Override
//			public User readToObjFromDBRow(ResultSet rs) throws SQLException {
//				User u = new User();
//				u.setId(rs.getString("id"));
//				u.setNickName(rs.getString("nickName"));
//				u.setLoginName(rs.getString("loginName"));
//				u.setLocked(rs.getBoolean("locked"));
        //没写完。。。。。。不想重写，还是想用原来的UserRowMapper,sql语句查询的列数补全即可。
//				return null;
//			}
//		});
    }

    //全删，再补
    @Override
    public void updateUserRole(String userId, String[] roleIds) {
        //删除这个用户的所有角色
        ud.deleteById(userId, "delete from lm_user_role where userId=?");
        //
        for (String roleId : roleIds) {
            ud.saveOrUpdate("insert into lm_user_role(userId,roleId)values(?,?)", userId, roleId);
        }

    }

    //写一个内部类，作为,我想优化它。
    class UserRowMapper implements RowMapper<User> {
        @Override
        public User readToObjFromDBRow(ResultSet rs) throws SQLException {
            User u = new User();
            u.setId(rs.getString("id"));
            u.setLoginName(rs.getString("loginName"));
            u.setPassword(rs.getString("password"));
            u.setEmail(rs.getString("email"));
            u.setNickName(rs.getString("nickName"));
            u.setGender(rs.getString("gender"));
            u.setAvatar(rs.getString("avatar"));
            u.setSignature(rs.getString("signature"));
            u.setRegistrationTime(rs.getTime("registrationTime"));
            u.setLastVisitTime(rs.getTime("lastVisitTime"));
            u.setLocked(rs.getBoolean("locked"));
            u.setArticleCount(rs.getInt("articleCount"));
            u.setTopicCount(rs.getInt("articleCount"));
            //给用户的角色进行初始化---》查询lm_user_role中roleId，根据userId.该用户有哪些角色。
            Collection<String> roleIds = ud.findForeignId(u.getId(), "select roleId from lm_user_role where userId=?");
            Set<Role> roles = new HashSet<Role>();
            for (String roleId : roleIds) {
                Role role = roleService.findById(roleId);
                roles.add(role);
            }
            u.setRoles(roles);
            return u;
        }

    }

    @Override
    public PageView findAll(String loginName, String nickName, String locked, String roleId, int pageSize,
                            int currentPage) {
        //sql-START
        String sql = "select u.id,u.loginName,u.password,u.email,u.nickName,u.locked,u.topicCount,u.articleCount "
                + "from lm_user u,lm_role r,lm_user_role ur "
                + "where 1=1  and u.id=ur.userId and r.id=ur.roleId ";
        if (loginName != null && !"".equals(loginName)) {
            sql += "and u.loginName like'%" + loginName + "%'";
        }
        if (nickName != null && !"".equals(nickName)) {
            sql += "and u.nickName like'%" + nickName + "%'";
        }
        if (locked != null && !"".equals(locked)) {
            boolean lock = Boolean.parseBoolean(locked);
            sql += "and u.locked=" + lock + "";
        }
        if (roleId != null && !"".equals(roleId)) {
            sql += " and r.id ='" + roleId + "'  ";
        }

        //利用参数来我们定义的一些类来初始化pageView实例。
        PageView pageView = new PageView();
        //总页数怎么求，找总记录数
        String countsql = "select count(*)" + sql.substring(sql.indexOf(" from"));
//		pageView.setEndPageIndex(endPageIndex);
        int recordCount = ud.getCount(countsql);
        //这样我们就得到了总记录数，初始化给对象
        pageView.setRecordCount(recordCount);

        int begin = (currentPage - 1) * pageSize;
        sql += " limit " + begin + "," + pageSize + "";
        //sql-END

        //使用userDao实例对象查询得到所有有的用户集合。
        List<User> users = ud.findAll(sql, new UserRowMapper());
        pageView.setRecordList(users);
        pageView.setCurrentPage(currentPage);
        pageView.setStartPageIndex(1);
        pageView.setPageSize(pageSize);

        //总记录数得到了，下面我们可以计算总页数了。
        int totalPage = recordCount % pageSize == 0 ? recordCount / pageSize : recordCount / pageSize + 1;
        pageView.setTotalPage(totalPage);
        pageView.setEndPageIndex(totalPage);
        //pageView对象初始化完毕

        return pageView;
    }

}
