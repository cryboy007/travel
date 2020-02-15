package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.BaseDao;
import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.util.JDBCUtils;
import cn.itcast.travel.util.JedisUtil;
import org.springframework.jdbc.core.JdbcTemplate;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryDaoImpl extends BaseDao<Category> implements CategoryDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public List<Category> findAll() {
            String sql = "select * from tab_category order by cid";
            return queryAll(sql,new Category());
    }
}
