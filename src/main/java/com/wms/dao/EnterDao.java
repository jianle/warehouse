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

@Repository
public class EnterDao implements BaseDao<Enter, Long> {
    
    private Logger logger = LoggerFactory.getLogger(EnterDao.class);
    
    private static final String TABLE_NAME    = "enter";
    private static final String INSERT_FIELDS = "g_id, g_name, chests, boxes, amount, user_id, user_name, remarks, insert_dt";
    private static final String SELECT_FIELDS = "e_id, " + INSERT_FIELDS + ", update_time";
    
    @Autowired
    @Qualifier("jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @Override
    public Enter get(Long id) {
        // TODO Auto-generated method stub
        try {
            String sql = "SELECT " + SELECT_FIELDS + " FROM " + TABLE_NAME + " WHERE e_id=? ";
            return jdbcTemplate.queryForObject(sql, rowMapper, id);
        } catch (Exception e) {
            // TODO: handle exception
            logger.info("get enter by id failed." + e);
        }
        return null;
    }
    
    public Boolean delete(Long eId){
        try {
            String sql = "DELETE FROM " + TABLE_NAME + " WHERE e_id=? ";
            return jdbcTemplate.update(sql, eId) > 0;
        } catch (Exception e) {
            // TODO: handle exception
            logger.info("delete enter by eId-" + eId + " failed." + e);
        }
        return false;
    }

    @Override
    public Boolean save(Enter object) {
        try {
            
            String sql = "INSERT INTO " + TABLE_NAME + " (" + INSERT_FIELDS 
                    + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            jdbcTemplate.update(sql, 
                    object.getgId(),
                    object.getgName(),
                    object.getChests(),
                    object.getBoxes(),
                    object.getAmount(),
                    object.getUserId(),
                    object.getUserName(),
                    object.getRemarks(),
                    object.getInsertDt());
            return true;

        } catch (Exception e) {
            logger.debug("save enter failed." + e);
        }
        return false;
    }
    
    public Boolean update(Enter object) {
        //g_id, g_name, chests, boxes, amount, user_id, user_name, remarks
        try {
            String sql = "UPDATE " + TABLE_NAME + " SET "
                    + " g_id=?, g_name=?, chests=?, boxes=?, amount=?, user_id=?, user_name=?, remarks=?"
                    + " WHERE e_id=? ";
            jdbcTemplate.update(sql, 
                    object.getgId(),
                    object.getgName(),
                    object.getChests(),
                    object.getBoxes(),
                    object.getAmount(),
                    object.getUserId(),
                    object.getUserName(),
                    object.getRemarks(),
                    object.geteId()
                    );
            
        } catch (Exception e) {
            // TODO: handle exception
            logger.debug("update enter failed." + e);
        }
        return false;
    }

    @Override
    public List<Enter> findAll() {
        try {
            String sql = "SELECT " + SELECT_FIELDS + " FROM " + TABLE_NAME ;
            return jdbcTemplate.query(sql, rowMapper);
        } catch (Exception e) {
            // TODO: handle exception
            logger.debug("findAll enter failed." + e);
        }
        return null;
    }
    
    @SuppressWarnings("deprecation")
    public Pagination<Enter> findByCurrentPage(int currentPage,int numPerPage) {
        //分页显示
        List<Enter> enters;
        
        StringBuffer sqlBuf = new StringBuffer("SELECT " + SELECT_FIELDS + " FROM " + TABLE_NAME);
        sqlBuf.append(" ORDER BY e_id DESC ");
        
        try {
            //定义并执行SQL
            String sqlTotal = "SELECT count(1) FROM " + TABLE_NAME;
            int totalRows = jdbcTemplate.queryForInt(sqlTotal);
            
            Pagination<Enter> entersPagination = new Pagination<Enter>(totalRows, currentPage, numPerPage);
            String sql = entersPagination.getMySQLPageSQL(sqlBuf.toString(),currentPage);
            
            logger.info(sql);
            
            enters = jdbcTemplate.query(sql, rowMapper);
            
            entersPagination.setResultList(enters);
            
            return entersPagination;
        } catch (Exception e) {
            // TODO: handle exception
            logger.debug("enter findByCurrentPage failed." + e);
            return null;
        }
    }
    
    private RowMapper<Enter> rowMapper =new RowMapper<Enter>(){
        @Override
        public Enter mapRow(ResultSet rs, int rowNum) throws SQLException {
            Enter enter = new Enter();
            
            enter.seteId(rs.getLong("e_id"));
            enter.setgId(rs.getLong("g_id"));
            enter.setgName(rs.getString("g_name"));
            enter.setChests(rs.getInt("chests"));
            enter.setBoxes(rs.getInt("boxes"));
            enter.setAmount(rs.getInt("amount"));
            
            enter.setUserId(rs.getLong("user_id"));
            enter.setUserName(rs.getString("user_name"));
            enter.setRemarks(rs.getString("remarks"));
            enter.setInsertDt(rs.getTimestamp("insert_dt"));
            enter.setUpdateTime(rs.getTimestamp("update_time"));
            
            return enter;
        }
        
    };

}
