package cn.itcast.travel.web.servlet;

import cn.itcast.travel.service.FavoriteService;
import cn.itcast.travel.service.impl.FavoriteServiceImpl;
import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
    private static UserService service = new UserServiceImpl();
    /**
     * 激活用户
     * @param request
     * @param response
     * @throws IOException
     */
    public void activeUserServlet(HttpServletRequest request,HttpServletResponse response) throws IOException {
        //获取激活码
        String code = request.getParameter("code");
        //查询是否有状态为N的用户且激活码为N的用户

        boolean active = service.active(code);
        String msg;
        if (active) {
            //激活成功
            msg = "激活成功，<a href='login.html'>点击登录</a>";
        }else {
            //激活失败
            msg = "激活失败,请联系管理员";
        }
        //设置响应消息
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(msg);
    }

    /**
     * 退出登录
     * @param request
     * @param response
     * @throws IOException
     */
    public void exitServlet(HttpServletRequest request,HttpServletResponse response) throws IOException {
        //销毁session
        HttpSession session = request.getSession();
        session.invalidate();
        //跳转到登录页面
        response.sendRedirect(request.getContextPath()+"/login.html");
    }

    /**
     * 获取登录用户名
     * @param request
     * @param response
     * @throws IOException
     */
    public void getNameServlet(HttpServletRequest request,HttpServletResponse response) throws IOException {
        //1.获取登录后的用户
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        ResultInfo res = new ResultInfo();
        if (user != null) {
            res.setFlag(true);
            res.setErrorMsg(user.getName());
        }else {
            res.setFlag(false);
        }
        writeValue(res,response);
    }

    /**
     * 用户注册
     * @param request
     * @param response
     * @throws IOException
     */
    public void registServlet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //校验验证码是否一致
        String check = request.getParameter("check");
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        //获取验证码后就在session中销毁
        session.removeAttribute("CHECKCODE_SERVER");
        ResultInfo result = new ResultInfo();
        if (check == null || !check.equalsIgnoreCase(checkcode_server)) {
            result.setFlag(false);
            result.setErrorMsg("验证码错误");
            //将info对象序列化为json对象
            /*ObjectMapper mapper = new ObjectMapper();
            String s = mapper.writeValueAsString(result);
            //设置content-type为json类型
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(s);*/
            writeValue(result,response);
            return;
        }
        //获取请求数据
        Map<String, String[]> parameterMap = request.getParameterMap();
        User u = new User();
        try {
            BeanUtils.populate(u, parameterMap);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //调用service完成注册
        boolean flag = service.regist(u);
        if (flag) {
            result.setFlag(true);
        } else {
            result.setFlag(false);
            result.setErrorMsg("注册失败,用户名已存在");
        }

        /*//将info对象序列化为json对象
        ObjectMapper mapper = new ObjectMapper();
        String s = mapper.writeValueAsString(result);
        //设置content-type为json类型
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(s);*/
        writeValue(result,response);
    }

    /**
     * 用户登录
     * @param request
     * @param response
     * @throws IOException
     */
    public void loginServlet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String v_code = request.getParameter("check");
        HttpSession session = request.getSession();
        String checkcode_server = (String)session.getAttribute("CHECKCODE_SERVER");
        //获取验证码后就删除
        session.removeAttribute("CHECKCODE_SERVER");
        ResultInfo result = new ResultInfo();
        if (v_code != null && v_code.equalsIgnoreCase(checkcode_server)) {
            Map<String, String[]> parameterMap = request.getParameterMap();
            User u = new User();
            try {
                BeanUtils.populate(u,parameterMap);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            User check = service.findByNameAndPassword(u);
            if (check != null) {
                if ("N".equals(check.getStatus())) {
                    result.setFlag(false);
                    result.setErrorMsg("尚未激活,请先激活在登录");
                }else {
                    result.setFlag(true);
                    session.setAttribute("user",check);
                }
            }else {
                result.setFlag(false);
                result.setErrorMsg("用户或密码错误");
            }
        }else {
            result.setFlag(false);
            result.setErrorMsg("验证码错误");
        }
        /*response.setContentType("application/json;charset=utf-8");
        ObjectMapper mapper = new ObjectMapper();
        String s = mapper.writeValueAsString(result);
        response.getWriter().write(s);*/
        writeValue(result,response);
    }
}
