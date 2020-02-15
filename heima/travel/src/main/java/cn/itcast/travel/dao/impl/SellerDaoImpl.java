package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.BaseDao;
import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.Seller;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

public class SellerDaoImpl extends BaseDao<Seller> implements SellerDao {
    @Override
    public Seller findSeller(int rid) {
        String sql = "select * from tab_seller where sid = ?";
        return temp.queryForObject(sql,new BeanPropertyRowMapper<Seller>(Seller.class),rid);
    }
}
