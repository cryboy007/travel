package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageInfo;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.FavoriteService;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.FavoriteServiceImpl;
import cn.itcast.travel.service.impl.RouteServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {
    private RouteService service = new RouteServiceImpl();
    private static FavoriteService favorite = new FavoriteServiceImpl();
    /**
     * 分页查询旅游线路
     * @param request
     * @param response
     */
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //接收参数
        String cidStr = request.getParameter("cid");
        String currentPageStr = request.getParameter("currentPage");
        String rowStr = request.getParameter("rows");
        String rname = request.getParameter("rname");
        //类别id
        int cid = 0;
        if (cidStr != null && cidStr.length() > 0 && !"null".equals(cidStr)) {
            cid = Integer.valueOf(cidStr);
        }
        //当前页码
        int currentPage = 1;
        if (currentPageStr != null && currentPageStr.length() > 0) {
            currentPage = Integer.valueOf(currentPageStr);
        }

        //每页显示的条数
        int row = 5;
        if (rowStr != null && rowStr.length() > 0 ) {
            row = Integer.valueOf(rowStr);
        }
        //得到数据
        PageInfo<Route> page = service.findPage(cid,currentPage,row,rname);
        //格式化成json
        writeValue(page,response);
    }

    /**
     * 查询单个
     * @param req
     * @param resp
     */
    public void findOne(HttpServletRequest req,HttpServletResponse resp) throws IOException {
        String rid = req.getParameter("rid");
        //查看详细
        User user = (User)req.getSession().getAttribute("user");
        Route route = service.findOne(rid);
        writeValue(route,resp);
    }
    public void isFavorite(HttpServletRequest req,HttpServletResponse resp) throws IOException {
        String rid = req.getParameter("rid");
        User user = (User) req.getSession().getAttribute("user");
        if (user != null) {
            //判断该用户是否收藏该路线
            boolean flag = favorite.findOne(rid,user);
            writeValue(flag,resp);
        }else {

        }
    }
    public void isLogin(HttpServletRequest req,HttpServletResponse resp) throws IOException {
        //判断用户是否已经登录
        Object user = req.getSession().getAttribute("user");
        if (user == null) {
            return;
        }else {
            //调用service进行收藏添加
            String rid = req.getParameter("rid");
            favorite.addFavorite(rid,(User)user);
       }
    }
}
