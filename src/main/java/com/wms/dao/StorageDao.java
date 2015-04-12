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

import com.wms.model.Enter;
import com.wms.model.Pagination;
import com.wms.model.Storage;

@Repository
public class StorageDao implements BaseDao<Storage, Long> {
    
    private Logger logger = LoggerFactory.getLogger(StorageDao.class);
    
    private static final String TABLE_NAME    = "storage";
    private static final String INSERT_FIELDS = "g_id, g_name, boxes, remarks, insert_dt";
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
                    + ") VALUES (?, ?, ?, ?, ?)";
            
            jdbcTemplate.update(sql, 
                    object.getgId(),
                    object.getgName(),
                    object.getBoxes(),
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
                    + ") VALUES (?, ?, ?, ?, ?)";
            
            jdbcTemplate.update(sql, 
                    object.getgId(),
                    object.getgName(),
                    object.getBoxes(),
                    object.getRemarks(),
                    object.getInsertDt());
            return true;

        } catch (Exception e) {
            logger.debug("save storage&enter failed." + e);
        }
        return false;
    }
    
    public Boolean updateBoxes(Enter enter, String type){
        try {
            String sql = "UPDATE " + TABLE_NAME + " SET "
                    + " boxes=boxes+? "
                    + " WHERE g_id=? ";
            if ("add".equals(type)) {
                return jdbcTemplate.update(sql, enter.getBoxes(), enter.getgId()) > 0;
            } 
            // 判断是否加减
            sql = "UPDATE " + TABLE_NAME + " SET "
                    + " boxes=boxes-? "
                    + " WHERE g_id=? ";
            if ("del".equals(type)) {
                return jdbcTemplate.update(sql, enter.getBoxes(), enter.getgId()) > 0;
            }
            
        } catch (Exception e) {
            logger.debug("update storage boxes failed." + e);
        }
        return false;
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
            return jdbcTemplate.update(sql, rowMapper, gId) > 0;
        } catch (Exception e) {
            // TODO: handle exception
            logger.debug("get storage by gId failed." + e);
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
            storage.setgName(rs.getString("g_name"));
            storage.setBoxes(rs.getInt("boxes"));
            storage.setRemarks(rs.getString("remarks"));
            storage.setInsertDt(rs.getTimestamp("insert_dt"));
            storage.setUpdateTime(rs.getTimestamp("update_time"));
            
            return storage;
        }
    };
}
