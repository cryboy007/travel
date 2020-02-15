package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

public class UserServiceImpl implements UserService {
    private UserDao dao = new UserDaoImpl();

    @Override
    public boolean regist(User user) {
        //校验用户是否存在
        User u = dao.userExists(user);
        if (u == null) {
            //并且设置用户的code值
            String uuid = UuidUtil.getUuid();
            user.setCode(uuid);

            //改变用户状态
            user.setStatus("N");
            //存在添加用户
            dao.regist(user);
            //添加成功后,发送邮件
            String email = user.getEmail();
            String content = "<a href='http://localhost:8111/travel/activeUserServlet?code="+uuid+"'>点击激活</a>";
            MailUtils.sendMail("1347411463@qq.com",
                    content,
                    "黑马旅游网站注册激活");
            return true;
        }
        return false;
    }

    @Override
    public boolean active(String code) {
        User user = dao.findByCode(code);
        if (user != null) {
            dao.userActive(user);
            return true;
        }
        return false;
    }

    @Override
    public User findByNameAndPassword(User u) {
        return dao.findBynameAndPassword(u);
    }
}
