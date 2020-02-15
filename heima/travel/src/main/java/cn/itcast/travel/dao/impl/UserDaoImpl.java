package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.BaseDao;
import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.domain.User;

public class UserDaoImpl extends BaseDao<User> implements UserDao {
    @Override
    public int regist(User user) {
        String sql = "insert into tab_user (username,password,name,birthday,sex,telephone,email,status,code) " +
                "values (?,?,?,?,?,?,?,?,?)";
        return saveOrDelorUpdate(sql,
                user.getUsername(),
                user.getPassword(),
                user.getName(),
                user.getBirthday(),
                user.getSex(),
                user.getTelephone(),
                user.getEmail(),
                user.getStatus(),
                user.getCode());
    }

    @Override
    public User userExists(User user) {
        String sql = "select * from tab_user where username = ?";
        return exists(sql,user, user.getUsername());
    }

    @Override
    public User findByCode(String code) {
        String sql = "select * from tab_user where code = ? and status = 'N'";
        return exists(sql,new User(),code);
    }

    @Override
    public void userActive(User user) {
        String sql = "update tab_user set status = 'Y' where code = ?";
        saveOrDelorUpdate(sql,user.getUid());
    }

    @Override
    public User findBynameAndPassword(User user) {
        String sql = "select * from tab_user where username = ? and password = ?";
        return exists(sql,user,user.getUsername(),user.getPassword());
    }
}
