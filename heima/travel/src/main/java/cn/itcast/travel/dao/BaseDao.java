package cn.itcast.travel.dao;

import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class BaseDao<T> {
    protected JdbcTemplate temp = new JdbcTemplate(JDBCUtils.getDataSource());

    public int saveOrDelorUpdate(String sql,Object... obj) {
        return temp.update(sql,obj);
    }

    public List<T> queryAll(String sql, T t, Object... obj) {
       return temp.query(sql,new BeanPropertyRowMapper<T>((Class<T>) t.getClass()),obj);
    }
    public T exists(String sql,T t,Object... obj) {
        try {
            return temp.queryForObject(sql,new BeanPropertyRowMapper<T>((Class<T>) t.getClass()),obj);
        }catch (Exception e) {
            System.out.println("错误");
            return null;
        }
    }
}
