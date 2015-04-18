package com.wms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.wms.model.OrderDetail;

@Repository
public class OrderDetailDao implements BaseDao<OrderDetail, Long> {

    private Logger logger = LoggerFactory.getLogger(OrderDetailDao.class);
    
    private static final String TABLE_NAME    = "order_detail";
    private static final String INSERT_FIELDS = "o_id, g_id, g_name, retail_price, unit_price, amount_net, amount"
                                              + ", amount_amt, code, remarks, insert_dt, update_time";
    private static final String SELECT_FIELDS = "od_id, " + INSERT_FIELDS ;
    
    @Autowired
    @Qualifier("jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @Override
    public OrderDetail get(Long id) {
        // TODO Auto-generated method stub
        try {
            String sql = "SELECT " + SELECT_FIELDS + " FROM " + TABLE_NAME + " WHERE od_id=? ";
            return jdbcTemplate.queryForObject(sql, rowMapper, id);
        } catch (Exception e) {
            // TODO: handle exception
            logger.debug("get order detail by odid failed." + e);
        }
        return null;
    }

    @Override
    public Boolean save(OrderDetail object) {
        // TODO Auto-generated method stub
        try {
            
            String sql = "INSERT INTO " + TABLE_NAME + " (" + INSERT_FIELDS 
                    + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            int flag = jdbcTemplate.update(sql,
                    object.getoId(),
                    object.getgId(),
                    object.getgName(),
                    object.getRetailPrice(),
                    object.getUnitPrice(),
                    object.getAmountNet(),
                    object.getAmount(),
                    object.getAmountAmt(),
                    object.getCode(),
                    object.getRemarks(),
                    object.getInsertDt(),
                    object.getUpdateTime()
                    );
            return flag > 0;
        } catch (Exception e) {
            // TODO: handle exception
            logger.debug("save faield." + e);
        }
        return null;
    }
    
    public Boolean update(OrderDetail object) {
        // TODO Auto-generated method stub
        try {
            
            String sql = "UPDATE " + TABLE_NAME 
                    + "SET o_id=?, g_id=?, g_name=?, retail_price=?, unit_price=?, amount_net=?, amount=?"
                    + ", amount_amt=?, code=?, remarks=?"
                    + " WHERE od_id=?";
            int flag = jdbcTemplate.update(sql,
                    object.getoId(),
                    object.getgId(),
                    object.getgName(),
                    object.getRetailPrice(),
                    object.getUnitPrice(),
                    object.getAmountNet(),
                    object.getAmount(),
                    object.getAmountAmt(),
                    object.getCode(),
                    object.getRemarks(),
                    object.getOdId()
                    );
            return flag > 0;
        } catch (Exception e) {
            // TODO: handle exception
            logger.debug("save faield." + e);
        }
        return null;
    }

    @Override
    public List<OrderDetail> findAll() {
        try {
            String sql = "SELECT " + SELECT_FIELDS + " FROM " + TABLE_NAME + " limit 10000 ";
            return jdbcTemplate.query(sql, rowMapper);
        } catch (Exception e) {
            // TODO: handle exception
            logger.debug("findByOId failed." + e);
        }
        return null;
    }
    
    public List<OrderDetail> findByOId(Long oId){
        try {
            String sql = "SELECT " + SELECT_FIELDS + " FROM " + TABLE_NAME + " WHERE o_id=? ";
            return jdbcTemplate.query(sql, rowMapper, oId);
        } catch (Exception e) {
            // TODO: handle exception
            logger.debug("findByOId failed." + e);
        }
        return null;
    }
    
    public Boolean delete(Long odId){
        try {
            String sql = "DELETE FROM " + TABLE_NAME + " WHERE od_id=? ";
            return jdbcTemplate.update(sql, odId) > 0;
        } catch (Exception e) {
            // TODO: handle exception
            logger.info("delete by odId-" + odId + " failed." + e);
        }
        return false;
    }
    
    private RowMapper<OrderDetail> rowMapper = new RowMapper<OrderDetail>() {

        @Override
        public OrderDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
            // TODO Auto-generated method stub
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOdId(rs.getLong("od_id"));
            orderDetail.setoId(rs.getLong("o_id"));
            orderDetail.setgId(rs.getLong("g_id"));
            orderDetail.setgName(rs.getString("g_name"));
            orderDetail.setRetailPrice(rs.getDouble("retail_price"));
            orderDetail.setUnitPrice(rs.getDouble("unit_price"));
            orderDetail.setAmountNet(rs.getDouble("amount_net"));
            orderDetail.setAmount(rs.getInt("amount"));
            orderDetail.setAmountAmt(rs.getDouble("amount_amt"));
            orderDetail.setCode(rs.getString("code"));
            orderDetail.setRemarks(rs.getString("remarks"));
            orderDetail.setInsertDt(rs.getTimestamp("insert_dt"));
            orderDetail.setUpdateTime(rs.getTimestamp("update_time"));
            
            return orderDetail;
        }
    };

}
