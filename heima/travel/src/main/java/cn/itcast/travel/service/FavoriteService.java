package cn.itcast.travel.service;

import cn.itcast.travel.domain.User;

public interface FavoriteService {
    /**
     * 查询是否收藏
     * @param rid
     * @param user
     * @return
     */
    boolean findOne(String rid, User user);

    /**
     * 添加收藏
     * @param rid
     * @param user
     */
    void addFavorite(String rid, User user);
}
