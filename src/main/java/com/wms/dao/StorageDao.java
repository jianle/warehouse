package com.wms.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.wms.model.Enter;
import com.wms.model.Pagination;
import com.wms.model.Storage;

@Repository
public class StorageDao implements BaseDao<Storage, Long> {
    
    private Logger logger = LoggerFactory.getLogger(StorageDao.class);
    
    private static final String TABLE_NAME    = "storage";
    private static final String INSERT_FIELDS = "g_id, s_id, g_name, chests, boxes, amount, remarks, insert_dt";
    private static final String SELECT_FIELDS = INSERT_FIELDS + ", update_time";
    
    @Autowired
    @Qualifier("jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @Override
    public Storage get(Long id) {
        // TODO Auto-generated method stub
        try {
            String sql = "SELECT " + SELECT_FIELDS + " FROM " + TABLE_NAME + " WHERE g_id=? ";
            return jdbcTemplate.queryForObject(sql, rowMapper, id);
        } catch (Exception e) {
            // TODO: handle exception
            logger.debug("get storage by gId failed." + e);
        }
        return null;
    }

    @Override
    public Boolean save(Storage object) {
        try {
            
            String sql = "INSERT INTO " + TABLE_NAME + " (" + INSERT_FIELDS 
                    + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            
            jdbcTemplate.update(sql, 
                    object.getgId(),
                    object.getsId(),
                    object.getgName(),
                    object.getChests(),
                    object.getBoxes(),
                    object.getAmount(),
                    object.getRemarks(),
                    object.getInsertDt());
            return true;

        } catch (Exception e) {
            logger.debug("save storage failed." + e);
        }
        return false;
    }
    
    public Boolean save(Enter object) {
        try {
            
            String sql = "INSERT INTO " + TABLE_NAME + " (" + INSERT_FIELDS 
                    + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            
            jdbcTemplate.update(sql, 
                    object.getgId(),
                    object.getsId(),
                    object.getgName(),
                    object.getChests(),
                    object.getBoxes(),
                    object.getAmount(),
                    object.getRemarks(),
                    object.getInsertDt());
            return true;

        } catch (Exception e) {
            logger.debug("save storage&enter failed." + e);
        }
        return false;
    }
    
    public Boolean update(Storage object) {
        try {
            
            String sql = "UPDATE " + TABLE_NAME + " SET "
                    + " s_id=?, g_name=?, amount=?, remarks=? "
                    + " WHERE g_id=? ";
            
            jdbcTemplate.update(sql,
                    object.getsId(),
                    object.getgName(),
                    object.getAmount(),
                    object.getRemarks(),
                    object.getgId());
            return true;

        } catch (Exception e) {
            logger.debug("save storage failed." + e);
        }
        return false;
    }
    
    public Boolean updateBoxes(Enter enter, String type){
        try {
            String sql = "UPDATE " + TABLE_NAME + " SET "
                    + " chests=chests+?, boxes=boxes+?, amount=amount+? "
                    + " WHERE g_id=? ";
            if ("add".equals(type)) {
                return jdbcTemplate.update(sql, 
                        enter.getChests(),
                        enter.getBoxes(), 
                        enter.getAmount(),
                        enter.getgId()) > 0;
            } 
            // 判断是否加减
            sql = "UPDATE " + TABLE_NAME + " SET "
                    + " chests=chests-?, boxes=boxes-?, amount=amount-? "
                    + " WHERE g_id=? ";
            if ("del".equals(type)) {
                return jdbcTemplate.update(sql, enter.getChests(),
                        enter.getBoxes(), 
                        enter.getAmount(),
                        enter.getgId()) > 0;
            }
            
        } catch (Exception e) {
            logger.debug("update storage boxes failed." + e);
        }
        return false;
    }
    
    public Boolean updateAmount(Long gId, Long amount, String type){
        try {
            String sql = "UPDATE " + TABLE_NAME + " SET "
                    + " amount=amount+? "
                    + " WHERE g_id=? ";
            if ("add".equals(type)) {
                return jdbcTemplate.update(sql, 
                        amount, gId) > 0;
            } 
            // 判断是否加减
            sql = "UPDATE " + TABLE_NAME + " SET "
                    + " amount=amount-? "
                    + " WHERE g_id=? ";
            if ("del".equals(type)) {
                return jdbcTemplate.update(sql, amount, gId) > 0;
            }
            
        } catch (Exception e) {
            logger.debug("update storage amount failed." + e);
        }
        return false;
    }
    
    public Boolean updateSubAmountList(final List<Map<String, Object>> list){
        try {
            String sql = "UPDATE " + TABLE_NAME + " SET "
                    + " amount=amount-? "
                    + " WHERE g_id=? ";
            jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
                
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    // TODO Auto-generated method stub
                    Map<String, Object> map = list.get(i);
                    ps.setLong(1, Long.valueOf(map.get("amount").toString()));
                    ps.setLong(2, Long.valueOf(map.get("g_id").toString()));
                }
                
                @Override
                public int getBatchSize() {
                    // TODO Auto-generated method stub
                    return list.size();
                }
            });
            return true;
        } catch (Exception e) {
            // TODO: handle exception
            logger.debug("updateSubAmountList faield." + e);
        }
        return false;
    }
    
    @SuppressWarnings("deprecation")
    public Long findByGId(Long gId) {
        Long amount = new Long(0);
        try {
            String sql = "select amount from " + TABLE_NAME + " where g_id=? ";
            return jdbcTemplate.queryForLong(sql, gId);
        } catch (Exception e) {
            // TODO: handle exception
            logger.debug("find amount by gid faield." + e);
        }
        return amount;
    }

    @Override
    public List<Storage> findAll() {
        // TODO Auto-generated method stub
        try {
            String sql = "SELECT " + SELECT_FIELDS + " FROM " + TABLE_NAME ;
            return jdbcTemplate.query(sql, rowMapper);
        } catch (Exception e) {
            // TODO: handle exception
            logger.debug("findAll storage failed." + e);
        }
        return null;
    }
    
    public Boolean delete(Long gId){
        try {
            String sql = "DELETE FROM " + TABLE_NAME + " WHERE g_id=? ";
            return jdbcTemplate.update(sql, gId) > 0;
        } catch (Exception e) {
            // TODO: handle exception
            logger.debug("delete storage by gId failed." + e);
        }
        return false;
    }
    
    
    
    @SuppressWarnings("deprecation")
    public Pagination<Storage> findByCurrentPage(String gName, int currentPage,int numPerPage) {
        //分页显示
        List<Storage> storages;
        
        StringBuffer sqlBuf = new StringBuffer("SELECT " + SELECT_FIELDS + " FROM " + TABLE_NAME);
        String isWhere = "";
        if (gName != null && !"".equals(gName.trim())) {
            isWhere = " WHERE g_name LIKE '%" + gName + "%'";
        }
        
        sqlBuf.append(isWhere);
        
        try {
            //定义并执行SQL
            String sqlTotal = "SELECT count(1) FROM " + TABLE_NAME;
            int totalRows = jdbcTemplate.queryForInt(sqlTotal);
            
            Pagination<Storage> entersPagination = new Pagination<Storage>(totalRows, currentPage, numPerPage);
            String sql = entersPagination.getMySQLPageSQL(sqlBuf.toString(),currentPage);
            
            logger.info(sql);
            
            storages = jdbcTemplate.query(sql, rowMapper);
            
            entersPagination.setResultList(storages);
            
            return entersPagination;
        } catch (Exception e) {
            // TODO: handle exception
            logger.debug("enter findByCurrentPage failed." + e);
            return null;
        }
    }
    
    
    private RowMapper<Storage> rowMapper = new RowMapper<Storage>() {

        @Override
        public Storage mapRow(ResultSet rs, int rowNum) throws SQLException {
            // TODO Auto-generated method stub
            Storage storage = new Storage();
            
            storage.setgId(rs.getLong("g_id"));
            storage.setsId(rs.getLong("s_id"));
            storage.setgName(rs.getString("g_name"));
            storage.setChests(rs.getLong("chests"));
            storage.setBoxes(rs.getLong("boxes"));
            storage.setAmount(rs.getLong("amount"));
            storage.setRemarks(rs.getString("remarks"));
            storage.setInsertDt(rs.getTimestamp("insert_dt"));
            storage.setUpdateTime(rs.getTimestamp("update_time"));
            
            return storage;
        }
    };
}
