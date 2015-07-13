package com.wms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.wms.model.Ordersource;

@Repository
public class OrdersourceDao implements BaseDao<Ordersource, Long> {
    
    private Logger logger = LoggerFactory.getLogger(OrdersourceDao.class); 
    
    private static final String TABLE_NAME    = "ordersource";
    private static final String INSERT_FIELDS = "os_name, remark, update_time";
    private static final String SELECT_FIELDS = "os_id, " + INSERT_FIELDS ;
    
    @Autowired
    @Qualifier("jdbcTemplate")
    private JdbcTemplate jdbcTemplate;
    
    @Override
    public Ordersource get(Long id) {
        // TODO Auto-generated method stub
        try {
            String sql = "select " + SELECT_FIELDS + " from " + TABLE_NAME + " where os_id=?";
            return jdbcTemplate.queryForObject(sql, rowMapper, id);
        } catch (Exception e) {
            // TODO: handle exception
            logger.debug("get:" + e);
        }
        return null;
    }

    @Override
    public Boolean save(Ordersource object) {
        // TODO Auto-generated method stub
        return null;
    }
    
    public Map<Long, String> findIdMapName() {
        try {
            String sql = "select os_id, os_name from " + TABLE_NAME;
            return jdbcTemplate.query(sql, new ResultSetExtractor<Map<Long, String>>(){

                @Override
                public Map<Long, String> extractData(ResultSet rs)
                        throws SQLException, DataAccessException {
                    Map<Long, String> map = new HashMap<Long, String>();
                    while (rs.next()) {
                        Long id = rs.getLong("os_id");
                        String truename = rs.getString("os_name");
                        map.put(id, truename);
                    }
                    return map;
                }
                
            });
        } catch (Exception e) {
            // TODO: handle exception
            logger.debug("findIdMapName:" + e);
        }
        return null;
    }
    
    public List<Map<String, Object>> findIdListMapName() {
        try {
            String sql = "select os_id osId, os_name osName from " + TABLE_NAME;
            return jdbcTemplate.queryForList(sql);
        } catch (Exception e) {
            // TODO: handle exception
            logger.debug("findIdListMapName failed ." + e);
            return null;
        }
    }

    @Override
    public List<Ordersource> findAll() {
        // TODO Auto-generated method stub
        try {
            return jdbcTemplate.query("select " + SELECT_FIELDS + " from " + TABLE_NAME, rowMapper);
        } catch (Exception e) {
            // TODO: handle exception
            logger.info("findAll:" + e);
        }
        return null;
    }
    
    private RowMapper<Ordersource> rowMapper = new RowMapper<Ordersource>() {

        @Override
        public Ordersource mapRow(ResultSet rs, int rowNum) throws SQLException {
            // TODO Auto-generated method stub
            Ordersource ordersource = new Ordersource();
            ordersource.setOsId(rs.getLong("os_id"));
            ordersource.setOsName(rs.getString("os_name"));
            ordersource.setRemark(rs.getString("remark"));
            ordersource.setUpdateTime(rs.getTimestamp("update_time"));
            return ordersource;
        }
    };

}
