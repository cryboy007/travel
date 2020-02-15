package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.BaseDao;
import cn.itcast.travel.dao.ImageDao;
import cn.itcast.travel.domain.RouteImg;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.util.List;

public class ImageDaoImpl extends BaseDao<RouteImg> implements ImageDao {

    @Override
    public List<RouteImg> findImage(String rid) {
        String sql = "select * from tab_route_img where rid = ?";
        return temp.query(sql,new BeanPropertyRowMapper<RouteImg>(RouteImg.class),rid);
    }
}
