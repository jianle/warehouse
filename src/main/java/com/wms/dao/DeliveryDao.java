package com.wms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.wms.model.Delivery;

@Repository
public class DeliveryDao implements BaseDao<Delivery, Long> {
    
    private Logger logger = LoggerFactory.getLogger(DeliveryDao.class);
    
    private static final String TABLE_NAME    = "delivery";
    private static final String INSERT_FIELDS = "o_id, express_id, express_name, weight, length, wide, high, remarks, status, insert_dt, update_time";
    private static final String SELECT_FIELDS = "d_id, " + INSERT_FIELDS;
    
    @Autowired
    @Qualifier("jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @Override
    public Delivery get(Long dId) {
        // TODO Auto-generated method stub
        try {
            String sql = "SELECT " + SELECT_FIELDS + " FROM " + TABLE_NAME + " WHERE d_id=? ";
            return jdbcTemplate.queryForObject(sql, rowMapper, dId);
        } catch (Exception e) {
            // TODO: handle exception
            logger.debug("get faield ." + e);
        }
        return null;
    }
    
    public List<Delivery> findByoId(Long oId) {
        // TODO Auto-generated method stub
        try {
            String sql = "SELECT " + SELECT_FIELDS + " FROM " + TABLE_NAME + " WHERE o_id=? ";
            return jdbcTemplate.query(sql, rowMapper, oId);
        } catch (Exception e) {
            // TODO: handle exception
            logger.debug("findByoId faield ." + e);
        }
        return null;
    }
    
    @SuppressWarnings("deprecation")
    public Integer findByoIdStatus(Long oId) {
        // TODO Auto-generated method stub
        try {
            String sql = "SELECT sum(status) as status FROM " + TABLE_NAME + " WHERE o_id=? ";
            return jdbcTemplate.queryForInt(sql, oId) > 0 ? 1:0;
        } catch (Exception e) {
            // TODO: handle exception
            logger.debug("findByoIdstatus faield ." + e);
        }
        return 0;
    }
    
    public List<Map<String, Object>> getAllContent() {
        try {
            String sql = "select express_id content from " + TABLE_NAME ;
            logger.info(sql);
            return jdbcTemplate.queryForList(sql);
        } catch (Exception e) {
            // TODO: handle exception
            logger.info("get all conteng failed." + e);
        }
        return null;
    }

    @Override
    public Boolean save(Delivery object) {
        // TODO Auto-generated method stub
        try {
            String sql = "INSERT INTO " + TABLE_NAME + " (" + INSERT_FIELDS 
                    + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql, 
                    object.getoId(),
                    object.getExpressId(),
                    object.getExpressName(),
                    object.getWeight(),
                    object.getLength(),
                    object.getWide(),
                    object.getHigh(),
                    object.getRemarks(),
                    object.getStatus(),
                    object.getInsertDt(),
                    object.getUpdateTime()
                    );
            return true;
        } catch (Exception e) {
            // TODO: handle exception
            logger.info("save failed." + e);
        }
        return false;
    }

    @Override
    public List<Delivery> findAll() {
        // TODO Auto-generated method stub
        return null;
    }
    
    public Boolean delete(Long dId){
        try {
            String sql = "DELETE FROM " + TABLE_NAME + " WHERE d_id=? ";
            return jdbcTemplate.update(sql, dId) > 0;
        } catch (Exception e) {
            // TODO: handle exception
            logger.info("delete by dId-" + dId + " failed." + e);
        }
        return false;
    }
    
    public Boolean updateStatusByOId(Long oId, Integer status){
        try {
            String sql = "UPDATE " + TABLE_NAME + " SET "
                    + " status=? "
                    + " WHERE o_id=?";
            return jdbcTemplate.update(sql, status, oId) > 0;
        } catch (Exception e) {
            // TODO: handle exception
            logger.debug("update status by oId-" + oId + " failed." + e);
        }
        return false;
    }
    
    public Boolean update(Delivery object) {
        
        try {
            String sql = "UPDATE " + TABLE_NAME + " SET "
                    + " express_id=?, express_name=?, weight=?, length=?, wide=?, high=?, remarks=?"
                    + " WHERE d_id=?";
            //express_id, express_name, weight, length, wide, high, remarks, insert_dt, update_time
            jdbcTemplate.update(sql, 
                    object.getExpressId(),
                    object.getExpressName(),
                    object.getWeight(),
                    object.getLength(),
                    object.getWide(),
                    object.getHigh(),
                    object.getRemarks(),
                    object.getdId()
                    );
            return true;
        } catch (Exception e) {
            // TODO: handle exception
            logger.info("update failed." + e);
        }
        
        return false;
    }
    
    
    
    private RowMapper<Delivery> rowMapper = new RowMapper<Delivery>() {

        @Override
        public Delivery mapRow(ResultSet rs, int rowNum) throws SQLException {
            // TODO Auto-generated method 
            Delivery delivery = new Delivery();
            delivery.setdId(rs.getLong("d_id"));
            delivery.setExpressId(rs.getString("express_id"));
            delivery.setExpressName(rs.getString("express_name"));
            delivery.setWeight(rs.getInt("weight"));
            delivery.setLength(rs.getInt("length"));
            delivery.setWide(rs.getInt("wide"));
            delivery.setHigh(rs.getInt("high"));
            delivery.setRemarks(rs.getString("remarks"));
            delivery.setStatus(rs.getInt("status"));
            delivery.setInsertDt(String.valueOf(rs.getTimestamp("insert_dt")));
            delivery.setUpdateTime(String.valueOf(rs.getTimestamp("update_time")));
            
            return delivery;
        }
    };

}
