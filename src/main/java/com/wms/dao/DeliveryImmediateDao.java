package com.wms.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.wms.model.DeliveryImmediate;

@Repository
public class DeliveryImmediateDao implements BaseDao<DeliveryImmediate, Long> {
    
    private Logger logger = LoggerFactory.getLogger(GoodsDao.class);
    
    private static final String TABLE_NAME    = "delivery_immediate";
    private static final String INSERT_FIELDS = "g_id, name, model, scode, amount, damount, operator_id, insert_dt, update_time";
    private static final String SELECT_FIELDS = INSERT_FIELDS;
    
    @Autowired
    @Qualifier("jdbcTemplate")
    private JdbcTemplate jdbcTemplate;
    
    @Override
    public DeliveryImmediate get(Long id) {
        // 通过Id获取对象
        try {
            String sql = "SELECT " + SELECT_FIELDS + " FROM " + TABLE_NAME + " WHERE g_id= ?";
            return jdbcTemplate.queryForObject(sql, rowMapper, id);
        } catch (Exception e) {
            logger.debug("get by id failed ." + e);
            return null;
        }
    }

    @Override
    public Boolean save(DeliveryImmediate object) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<DeliveryImmediate> findAll() {
        // TODO Auto-generated method stub
        return null;
    }
    
    public Boolean saveBatch(final JSONArray tuple, final Long userId) {
        try {
            
            final Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            
            String sql = "insert into " + TABLE_NAME + " ( " + INSERT_FIELDS
                    + " ) values (?, ?, ?, ?, ?, ?, ?, ?, ?)" ;
            jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter(){

                @Override
                public void setValues(PreparedStatement ps, int i)
                        throws SQLException {
                    // TODO Auto-generated method stub
                    JSONObject obj = JSONObject.fromObject(tuple.get(i));
                    ps.setObject(1, obj.get("gId"));
                    ps.setObject(2, obj.get("name"));
                    ps.setObject(3, obj.get("model"));
                    ps.setObject(4, obj.get("scode"));
                    ps.setObject(5, obj.get("amount"));
                    ps.setObject(6, obj.get("damount"));
                    ps.setObject(7, userId);
                    ps.setObject(8, timestamp);
                    ps.setObject(9, timestamp);
                    
                }

                @Override
                public int getBatchSize() {
                    // TODO Auto-generated method stub
                    return tuple.size();
                }});
            
           return true;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    
    public List<DeliveryImmediate> findByOperatorId(Long operatorId) {
     // 通过Id获取对象
        try {
            String sql = "SELECT " + SELECT_FIELDS + " FROM " + TABLE_NAME + " WHERE operator_id= ?";
            return jdbcTemplate.query(sql, rowMapper, operatorId);
        } catch (Exception e) {
            logger.debug("findByOperatorId failed ." + e);
            return null;
        }
    }
    
    
    public List<DeliveryImmediate> getGoodsAndStorge(String scode) {
        
        StringBuilder sql = new StringBuilder("SELECT ");
        sql.append("    a.g_id, a.name, a.model, a.scode ")
           .append("    ,coalesce(b.amount, 0) as amount, 1 as damount, a.operator_id, a.insert_dt, a.update_time ")
           .append("from goods a ")
           .append("left outer join storage b on a.g_id=b.g_id ")
           .append("where a.scode=?") ;
        
        logger.info(sql.toString());
        
        try {
            return jdbcTemplate.query(sql.toString(), rowMapper, scode);
        } catch (Exception e) {
            // TODO: handle exception
            logger.debug("ERROR ." + e);
        }
        return null;
        
    }
    
    private RowMapper<DeliveryImmediate> rowMapper = new RowMapper<DeliveryImmediate>() {

        @Override
        public DeliveryImmediate mapRow(ResultSet rs, int numRow) throws SQLException {
            DeliveryImmediate dImmediate = new DeliveryImmediate();
            dImmediate.setgId(rs.getLong("g_id"));
            dImmediate.setName(rs.getString("name"));
            dImmediate.setModel(rs.getString("model"));
            dImmediate.setScode(rs.getString("scode"));
            dImmediate.setDamount(rs.getInt("damount"));
            dImmediate.setAmount(rs.getInt("amount"));
            dImmediate.setOperatorId(rs.getLong("operator_id"));
            dImmediate.setInsertDt(rs.getString("insert_dt"));
            dImmediate.setUpdateTime(rs.getString("update_time"));

            return dImmediate;
        }
    };

}
