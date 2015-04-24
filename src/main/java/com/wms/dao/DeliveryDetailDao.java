package com.wms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.wms.model.DeliveryDetail;

@Repository
public class DeliveryDetailDao implements BaseDao<DeliveryDetail, Long>{
    
    private Logger logger = LoggerFactory.getLogger(DeliveryDetailDao.class);
    
    private static final String TABLE_NAME    = "delivery_detail";
    private static final String INSERT_FIELDS = "content, accept_address, accept_time, remark";
    private static final String SELECT_FIELDS = "dd_id, " + INSERT_FIELDS ;
    
    @Autowired
    @Qualifier("jdbcTemplate")
    private JdbcTemplate jdbcTemplate;
    
    @Override
    public DeliveryDetail get(Long id) {
        // TODO Auto-generated method stub
        try {
            String sql = "SELECT " + SELECT_FIELDS + " FROM " + TABLE_NAME + " WHERE dd_id=? ";
            return jdbcTemplate.queryForObject(sql, rowMapper, id);
        } catch (Exception e) {
            // TODO: handle exception
            logger.debug("get id faield." + e);
        }
        return null;
    }
    
    @Override
    public Boolean save(DeliveryDetail object) {
        // TODO Auto-generated method stub
        try {
            String sql = "INSERT INTO " + TABLE_NAME + " (" + INSERT_FIELDS 
                    + ") VALUES (?, ?, ?, ?)";
            
            jdbcTemplate.update(sql, 
                    object.getContent(),
                    object.getAcceptAddress(),
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(object.getAcceptTime()),
                    object.getRemark()
                    );
            return true;
        } catch (Exception e) {
            // TODO: handle exception
            logger.info("save faield. " + e);
        }
        return false;
    }
    @Override
    public List<DeliveryDetail> findAll() {
        // TODO Auto-generated method stub
        return null;
    }
    
    public List<DeliveryDetail> findByContent(String content){
        try {
            String sql = "select " + SELECT_FIELDS + " from " + TABLE_NAME + " where content=? ";
            return jdbcTemplate.query(sql, rowMapper, content);
        } catch (Exception e) {
            // TODO: handle exception
            logger.debug("delete content:" + content);
        }
        return null;
    }
    
    public Boolean deleteByContent(String content) {
        try {
            String sql = "delete from " + TABLE_NAME + " where content=? ";
            jdbcTemplate.update(sql, content);
        } catch (Exception e) {
            // TODO: handle exception
            logger.debug("delete content:" + content);
        }
        return false;
    }
    
    private RowMapper<DeliveryDetail> rowMapper = new RowMapper<DeliveryDetail>() {

        @Override
        public DeliveryDetail mapRow(ResultSet rs, int rowNum)
                throws SQLException {
            // TODO Auto-generated method stub

            DeliveryDetail deliveryDetail = new DeliveryDetail();
            deliveryDetail.setDdId(rs.getLong("dd_id"));
            deliveryDetail.setContent(rs.getString("content"));
            deliveryDetail.setAcceptAddress(rs.getString("accept_address"));
            deliveryDetail.setAcceptTime(String.valueOf(rs.getTimestamp("accept_time")));
            deliveryDetail.setRemark(rs.getString("remark"));
            
            return deliveryDetail;
        }
    };

}
