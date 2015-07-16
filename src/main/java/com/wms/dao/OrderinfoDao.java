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

import com.wms.model.Orderinfo;
import com.wms.model.Pagination;

@Repository
public class OrderinfoDao implements BaseDao<Orderinfo, Long> {
    
    private Logger logger = LoggerFactory.getLogger(OrderinfoDao.class); 
    
    private static final String TABLE_NAME    = "orderinfo";
    private static final String INSERT_FIELDS = "order_code, customer_code, wh_add"
            + ", transp_cost_type, receive_tel, receive_add, user_id, document_date, amount_total"
            + ", amount_discount, amount_payable, status, os_id, operator_id, remark, insert_dt, update_time";
    private static final String SELECT_FIELDS = "o_id, " + INSERT_FIELDS ;
    
    @Autowired
    @Qualifier("jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    
    @SuppressWarnings("deprecation")
    public long getMinId(String userIds) {
        // TODO Auto-generated method stub
        String sql = "";
        try {
            String isWhere = "";
            if (userIds != null && !"".equals(userIds)) {
                isWhere = " where user_id in " + userIds;
            }
            sql = "SELECT min(o_id) FROM " + TABLE_NAME + isWhere;
            logger.info("getMinId:" + sql);
            
            return jdbcTemplate.queryForLong(sql);
        } catch (Exception e) {
            // TODO: handle exception
            logger.debug("getMinId:" + e + "\n" + sql);
        }
        return 0;
    }
    
    @Override
    public Orderinfo get(Long id) {
        // TODO Auto-generated method stub
        try {
            String sql = "SELECT " + SELECT_FIELDS + " FROM " + TABLE_NAME + " WHERE o_id=? ";
            return jdbcTemplate.queryForObject(sql, rowMapper, id);
        } catch (Exception e) {
            // TODO: handle exception
            logger.debug("get order by oid failed." + e);
        }
        return null;
    }
    
    @SuppressWarnings("deprecation")
    public Long getMiniId(){
        try {
            String sql = "SELECT min(o_id) oId FROM " + TABLE_NAME;
            return jdbcTemplate.queryForLong(sql);
        } catch (Exception e) {
            // TODO: handle exception
            logger.debug("get MiniId faield." + e);
            return (long) 0;
        }
    }

    @Override
    public Boolean save(Orderinfo object) {
        // TODO Auto-generated method stub
        try {
            String sql = "INSERT INTO " + TABLE_NAME + " (" + INSERT_FIELDS 
                    + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            int flag = jdbcTemplate.update(sql,
                    object.getOrderCode(),
                    object.getCustomerCode(),
                    object.getWhAdd(),
                    object.getTranspCostType(),
                    object.getReceiveTel(),
                    object.getReceiveAdd(),
                    object.getUserId(),
                    object.getDocumentDate(),
                    object.getAmountTotal(),
                    object.getAmountDiscount(),
                    object.getAmountPayable(),
                    object.getStatus(),
                    object.getOsId(),
                    object.getOperatorId(),
                    object.getRemark(),
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

    @Override
    public List<Orderinfo> findAll() {
        // TODO Auto-generated method stub
        return null;
    }
    
    public Boolean delete(Long oId){
        try {
            String sql = "DELETE FROM " + TABLE_NAME + " WHERE o_id=? ";
            return jdbcTemplate.update(sql, oId) > 0;
        } catch (Exception e) {
            // TODO: handle exception
            logger.info("delete enter by eId-" + oId + " failed." + e);
        }
        return false;
    }
    
    public Boolean update(Orderinfo object) {
        // TODO Auto-generated method stub
        try {
            String sql = "UPDATE " + TABLE_NAME + " SET "
                    + " order_code=?, customer_code=?, wh_add=?"
                    + ", transp_cost_type=?, receive_tel=?, receive_add=?, document_date=?, amount_total=?"
                    + ", amount_discount=?, amount_payable=?, os_id=?, remark=? "
                    + " WHERE o_id=?";
                    ;
            int flag = jdbcTemplate.update(sql,
                    object.getOrderCode(),
                    object.getCustomerCode(),
                    object.getWhAdd(),
                    object.getTranspCostType(),
                    object.getReceiveTel(),
                    object.getReceiveAdd(),
                    object.getDocumentDate(),
                    object.getAmountTotal(),
                    object.getAmountDiscount(),
                    object.getAmountPayable(),
                    object.getOsId(),
                    object.getRemark(),
                    object.getoId()
                    );
            return flag > 0;
        } catch (Exception e) {
            // TODO: handle exception
            logger.debug("update faield." + e);
        }
        return null;
    }
    
    public Boolean update(Integer status, Long oId) {
        // TODO Auto-generated method stub
        try {
            String sql = "UPDATE " + TABLE_NAME + " SET "
                    + " status=? "
                    + " WHERE o_id=?";
                    ;
            jdbcTemplate.update(sql, status, oId);
            return true;
        } catch (Exception e) {
            // TODO: handle exception
            logger.debug("update status faield." + e);
        }
        return null;
    }
    
    public List<Map<String, Object>> findAlloId(String userIds){
        try {
            String isWhere = "";
            if (userIds != null && !"".equals(userIds)) {
                isWhere = " where user_id in " + userIds;
            }
            String sql = "SELECT o_id oId FROM " + TABLE_NAME + isWhere;
            return jdbcTemplate.queryForList(sql);
        } catch (Exception e) {
            // TODO: handle exception
            logger.debug("find all oId failed." + e);
            return null;
        }
    }
    
    @SuppressWarnings("deprecation")
    public Pagination<Orderinfo> findByCurrentPage(int currentPage,int numPerPage, String userIds) {
        //分页显示
        List<Orderinfo> orderinfos;
        
        StringBuffer sqlBuf = new StringBuffer("SELECT " + SELECT_FIELDS + " FROM " + TABLE_NAME);
        if (userIds != null && !"".equals(userIds)) {
            sqlBuf.append(" where user_id in " + userIds);
        }
        sqlBuf.append(" ORDER BY o_id DESC ");
        
        try {
            //定义并执行SQL
            String sqlTotal = sqlBuf.toString().replace(SELECT_FIELDS, "count(1)");
            int totalRows = jdbcTemplate.queryForInt(sqlTotal);
            
            Pagination<Orderinfo> paginations = new Pagination<Orderinfo>(totalRows, currentPage, numPerPage);
            String sql = paginations.getMySQLPageSQL(sqlBuf.toString(),currentPage);
            
            logger.info(sql);
            
            orderinfos = jdbcTemplate.query(sql, rowMapper);
            
            paginations.setResultList(orderinfos);
            
            return paginations;
        } catch (Exception e) {
            // TODO: handle exception
            logger.debug("orderinfos findByCurrentPage failed." + e);
            return null;
        }
    }
    
    private RowMapper<Orderinfo> rowMapper = new RowMapper<Orderinfo>() {

        @Override
        public Orderinfo mapRow(ResultSet rs, int rowNum) throws SQLException {
            // TODO Auto-generated method stub
            Orderinfo orderinfo = new Orderinfo();
            orderinfo.setoId(rs.getLong("o_id"));
            orderinfo.setOrderCode(rs.getString("order_code"));
            orderinfo.setCustomerCode(rs.getLong("customer_code"));
            orderinfo.setWhAdd(rs.getString("wh_add"));
            orderinfo.setTranspCostType(rs.getString("transp_cost_type"));
            orderinfo.setReceiveTel(rs.getString("receive_tel"));
            orderinfo.setReceiveAdd(rs.getString("receive_add"));
            orderinfo.setUserId(rs.getLong("user_id"));
            orderinfo.setDocumentDate(rs.getString("document_date"));
            orderinfo.setAmountTotal(rs.getDouble("amount_total"));
            orderinfo.setAmountDiscount(rs.getDouble("amount_discount"));
            orderinfo.setAmountPayable(rs.getDouble("amount_payable"));
            orderinfo.setStatus(rs.getInt("status"));
            
            orderinfo.setOsId(rs.getLong("os_id"));
            orderinfo.setOperatorId(rs.getLong("operator_id"));
            orderinfo.setRemark(rs.getString("remark"));
            orderinfo.setInsertDt(String.valueOf(rs.getTimestamp("insert_dt")));
            orderinfo.setUpdateTime(String.valueOf(rs.getTimestamp("update_time")));
            
            return orderinfo;
        }
    };

    public List<Map<String, Object>> findCurAlloId(String userIds) {
        try {
            String isWhere = "";
            if (userIds != null && !userIds.equals("")) {
                isWhere = " where user_id in " + userIds;
            } 
            
            String sql = "SELECT o_id oId FROM " + TABLE_NAME + isWhere;
            return jdbcTemplate.queryForList(sql);
        } catch (Exception e) {
            // TODO: handle exception
            logger.debug("findCurAlloId failed." + e);
            return null;
        }
    }
}
