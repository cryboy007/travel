package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.BaseDao;
import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.Route;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.util.ArrayList;
import java.util.List;

public class RouteDaoImpl extends BaseDao<Route> implements RouteDao {
    @Override
    public int findTotal(int cid,String rname) {
        String sql = "select count(1) from tab_route where 1 = 1 ";
        StringBuffer sb = new StringBuffer(sql);
        List list = new ArrayList();
        if (cid != 0) {
            sb.append("and cid = ? ");
            list.add(cid);
        }
        if (rname != null && !"null".equals(rname)) {
            sb.append("and rname like ?");
            list.add("%"+rname+"%");
        }
        try {
            return temp.queryForObject(sb.toString(),Integer.class,list.toArray());
        }catch (Exception e){
            return 0;
        }
    }

    @Override
    public List<Route> findDate(int cid,int pageSize,int row,String rname) {
        String sql = "select * from tab_route where 1 = 1 ";
        StringBuffer sb = new StringBuffer(sql);
        List list = new ArrayList();
        if (cid != 0) {
            sb.append("and cid = ? ");
            list.add(cid);
        }
        if (rname != null && !"null".equals(rname)) {
            sb.append("and rname like ? ");
            list.add("%"+rname+"%");
        }
        sb.append("limit ?,?");
        list.add(pageSize);
        list.add(row);
        return queryAll(sb.toString(),new Route(),list.toArray());
    }
    @Override
    public Route findOne(String rid) {
        String sql = "select * from tab_route where rid = ?";
        return temp.queryForObject(sql,new BeanPropertyRowMapper<Route>(Route.class),rid);
    }

    @Override
    public int findFavoriteCount(String rid) {
        String sql = "SELECT COUNT(1) FROM tab_favorite WHERE rid = ?";
        return temp.queryForObject(sql,Integer.class,rid);
    }
}
