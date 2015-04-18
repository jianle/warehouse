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
    private static final String INSERT_FIELDS = "document_code, order_code, order_type, customer_type, customer_code, customer_name, wh_add"
            + ", transp_cost_type, receive_tel, receive_add, user_id, user_name, document_date, sale_date, company, amount_total, mt_mch"
            + ", account_balance, retail_price, amount_discount, amount_payable, amount_net, status, insert_dt, update_time";
    private static final String SELECT_FIELDS = "o_id, " + INSERT_FIELDS ;
    
    @Autowired
    @Qualifier("jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

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

    @Override
    public Boolean save(Orderinfo object) {
        // TODO Auto-generated method stub
        try {
            String sql = "INSERT INTO " + TABLE_NAME + " (" + INSERT_FIELDS 
                    + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            int flag = jdbcTemplate.update(sql,
                    object.getDocumentCode(),
                    object.getOrderCode(),
                    object.getOrderType(),
                    object.getCustomerCode(),
                    object.getCustomerType(),
                    object.getCustomerName(),
                    object.getWhAdd(),
                    object.getTranspCostType(),
                    object.getReceiveTel(),
                    object.getReceiveAdd(),
                    object.getUserId(),
                    object.getUserName(),
                    object.getDocumentDate(),
                    object.getSaleDate(),
                    object.getCompany(),
                    object.getAmountTotal(),
                    object.getMtMch(),
                    object.getAccountBalance(),
                    object.getRetailPrice(),
                    object.getAmountDiscount(),
                    object.getAmountPayable(),
                    object.getAmountNet(),
                    object.getStatus(),
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
                    + "document_code=?, order_code=?, order_type=?, customer_type=?, customer_code=?, customer_name=?, wh_add=?"
                    + ", transp_cost_type=?, receive_tel=?, receive_add=?, user_name=?, document_date=?, sale_date=?, company=?, amount_total=?, mt_mch=?"
                    + ", account_balance=?, retail_price=?, amount_discount=?, amount_payable=?, amount_net=? "
                    + " WHERE o_id=?";
                    ;
            int flag = jdbcTemplate.update(sql,
                    object.getDocumentCode(),
                    object.getOrderCode(),
                    object.getOrderType(),
                    object.getCustomerCode(),
                    object.getCustomerType(),
                    object.getCustomerName(),
                    object.getWhAdd(),
                    object.getTranspCostType(),
                    object.getReceiveTel(),
                    object.getReceiveAdd(),
                    object.getUserName(),
                    object.getDocumentDate(),
                    object.getSaleDate(),
                    object.getCompany(),
                    object.getAmountTotal(),
                    object.getMtMch(),
                    object.getAccountBalance(),
                    object.getRetailPrice(),
                    object.getAmountDiscount(),
                    object.getAmountPayable(),
                    object.getAmountNet(),
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
    
    public List<Map<String, Object>> findAlloId(){
        try {
            String sql = "SELECT o_id oId FROM " + TABLE_NAME;
            return jdbcTemplate.queryForList(sql);
        } catch (Exception e) {
            // TODO: handle exception
            logger.debug("find all oId failed." + e);
            return null;
        }
    }
    
    @SuppressWarnings("deprecation")
    public Pagination<Orderinfo> findByCurrentPage(int currentPage,int numPerPage) {
        //分页显示
        List<Orderinfo> orderinfos;
        
        StringBuffer sqlBuf = new StringBuffer("SELECT " + SELECT_FIELDS + " FROM " + TABLE_NAME);
        sqlBuf.append(" ORDER BY o_id DESC ");
        
        try {
            //定义并执行SQL
            String sqlTotal = "SELECT count(1) FROM " + TABLE_NAME;
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
            orderinfo.setDocumentCode(rs.getString("document_code"));
            orderinfo.setOrderCode(rs.getString("order_code"));
            orderinfo.setOrderType(rs.getString("order_type"));
            orderinfo.setCustomerCode(rs.getString("customer_code"));
            orderinfo.setCustomerType(rs.getString("customer_type"));
            orderinfo.setCustomerName(rs.getString("customer_name"));
            orderinfo.setWhAdd(rs.getString("wh_add"));
            orderinfo.setTranspCostType(rs.getString("transp_cost_type"));
            orderinfo.setReceiveTel(rs.getString("receive_tel"));
            orderinfo.setReceiveAdd(rs.getString("receive_add"));
            orderinfo.setUserId(rs.getLong("user_id"));
            orderinfo.setUserName(rs.getString("user_name"));
            orderinfo.setDocumentDate(rs.getString("document_date"));
            orderinfo.setSaleDate(rs.getString("sale_date"));
            orderinfo.setCompany(rs.getString("company"));
            orderinfo.setAmountTotal(rs.getDouble("amount_total"));
            orderinfo.setMtMch(rs.getString("mt_mch"));
            orderinfo.setAccountBalance(rs.getDouble("account_balance"));
            orderinfo.setRetailPrice(rs.getDouble("retail_price"));
            orderinfo.setAmountDiscount(rs.getDouble("amount_discount"));
            orderinfo.setAmountPayable(rs.getDouble("amount_payable"));
            orderinfo.setAmountNet(rs.getDouble("amount_net"));
            orderinfo.setStatus(rs.getInt("status"));
            orderinfo.setInsertDt(String.valueOf(rs.getTimestamp("insert_dt")));
            orderinfo.setUpdateTime(String.valueOf(rs.getTimestamp("update_time")));
            
            return orderinfo;
        }
    };
}
