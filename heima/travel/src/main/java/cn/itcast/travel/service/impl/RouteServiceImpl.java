package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.ImageDao;
import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.dao.impl.ImageDaoImpl;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.dao.impl.SellerDaoImpl;
import cn.itcast.travel.domain.*;
import cn.itcast.travel.service.RouteService;

import java.util.List;

public class RouteServiceImpl implements RouteService {
    private RouteDao dao = new RouteDaoImpl();
    private ImageDao imageDao = new ImageDaoImpl();
    private SellerDao sellerDao = new SellerDaoImpl();
    @Override
    public PageInfo<Route> findPage(int cid, int currentPage, int row,String rname) {
        PageInfo<Route> page = new PageInfo<>();
        page.setCurrentPage(currentPage);
        page.setRows(row);
        //拿到总页数
        Integer total = dao.findTotal(cid,rname);
        int totalPage = total % row == 0 ? total / row : total / row + 1;
        page.setTotalCount(total);
        page.setTotalPage(totalPage);
        int pageSize = (currentPage-1) * row;
        //拿到数据
        List<Route> list = dao.findDate(cid,pageSize,row,rname);
        page.setList(list);
        return page;
    }

    @Override
    public Route findOne(String rid) {
        //拿路线信息
        Route r = dao.findOne(rid);
        //拿图片信息
        List<RouteImg> imgs = imageDao.findImage(rid);
        //拿商家信息和路线信息
        Seller seller = sellerDao.findSeller(r.getSid());
        r.setSeller(seller);
        r.setRouteImgList(imgs);
        //查询收藏次数
        int count = dao.findFavoriteCount(rid);
        r.setCount(count);
        return r;
    }
}
