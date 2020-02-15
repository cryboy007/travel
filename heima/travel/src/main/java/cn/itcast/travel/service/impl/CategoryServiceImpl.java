package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {
    private static CategoryDao dao = new CategoryDaoImpl();
    @Override
    public List<Category> findAll() {
        Jedis jedis = JedisUtil.getJedis();
        //获取数字和值
        Set<Tuple> category = jedis.zrangeByScoreWithScores("category", 0, -1);
        List<Category> list = null;
        //如果没有缓存,就调用dao层拿数据
        if (category == null || category.size() == 0) {
            list = dao.findAll();
            //将数据存储到sorted集合中
            list.forEach(value -> jedis.zadd("category",value.getCid(),value.getCname()));
        }else {
            //如果category不为空,将数据全部添加到list集合中并返回
            list = new ArrayList<>();
            for (Tuple tuple : category) {
                Category c = new Category();
                c.setCid((int) tuple.getScore());
                c.setCname(tuple.getElement());
                list.add(c);
            }
        }
        return list;
    }
}
