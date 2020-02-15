package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;

import java.util.List;

public interface RouteDao {
    /**
     * 查总数
     * @param cid
     * @return
     */
    int findTotal(int cid,String rname);

    /**
     * 查数据
     * @return
     */
    List<Route> findDate(int cid,int pageSize,int row,String rname);

    /**
     * 查单个信息
     * @return
     */
    Route findOne(String rid);

    /**
     * 查询收藏次数
     * @param rid
     * @return
     */
    int findFavoriteCount(String rid);
}
