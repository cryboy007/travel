package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

public interface UserDao {
    int regist(User user);

    User userExists(User user);

    /**
     * 找对应的激活码用户
     * @param code
     * @return
     */
    User findByCode(String code);

    /**
     * 改变用户状态
     * @param user
     */
    void userActive(User user);

    /**
     * 校验登录信息
     * @param user
     * @return
     */
    User findBynameAndPassword(User user);
}
