package cn.itcast.travel.service.impl;


import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.FavoriteService;

public class FavoriteServiceImpl implements FavoriteService {
    private FavoriteDao dao = new FavoriteDaoImpl();
    @Override
    public boolean findOne(String rid, User user) {
        int uid = user.getUid();
        Favorite favorite = dao.findOne(rid,uid);
        return favorite != null;
    }

    @Override
    public void addFavorite(String rid, User user) {
        dao.FavoriteAdd(rid,user.getUid());
    }
}
