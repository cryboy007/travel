package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Favorite;

public interface FavoriteDao {

    Favorite findOne(String rid, int uid);

    void FavoriteAdd(String rid, int uid);
}
