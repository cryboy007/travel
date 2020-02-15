package cn.itcast.travel.service;

import cn.itcast.travel.domain.User;

/**
 * @author Administrator
 */
public interface UserService {
    /**
     * 注册
     * @param user
     */
    boolean regist(User user);

    /**
     * 激活
     * @param code
     */
    boolean active(String code);

    /**
     * 查询用户是否存在
     * @param u
     * @return
     */
    User findByNameAndPassword(User u);
}
