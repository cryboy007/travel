package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageInfo;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;

public interface RouteService {
    /**
     * 分页查询旅游数据
     * @param cid
     * @param currentPage
     * @param row
     * @return
     */
    PageInfo<Route> findPage(int cid, int currentPage, int row,String rname);

    /**
     * 查看详细
     * @param rid
     * @return
     */
    Route findOne(String rid);
}
