package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.BaseDao;
import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.domain.Favorite;
import org.springframework.jdbc.core.BeanPropertyRowMapper;


public class FavoriteDaoImpl extends BaseDao<Favorite> implements FavoriteDao {
    @Override
    public Favorite findOne(String rid, int uid) {
        String sql = "SELECT * FROM tab_favorite WHERE rid = ? and  uid = ?";
        try {
            return temp.queryForObject(sql,new BeanPropertyRowMapper<Favorite>(Favorite.class),rid,uid);
        }catch (Exception e) {
            return null;
        }
    }

    @Override
    public void FavoriteAdd(String rid, int uid) {
        String sql = "insert into tab_favorite values (?,?,?)";
        saveOrDelorUpdate(sql,rid,new java.util.Date(),uid);
    }
}
