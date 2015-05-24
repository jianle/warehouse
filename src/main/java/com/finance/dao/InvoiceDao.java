package com.finance.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.finance.model.Invoice;
import com.wms.model.Pagination;


@Repository
public class InvoiceDao implements BaseDao<Invoice, Long> {

    private Logger logger = LoggerFactory.getLogger(InvoiceDao.class);
    
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat DATET_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static final String TABLE_NAME    = "invoice";
    private static final String INSERT_FIELDS = " inv_id, con_id, valorem_tax, amount, amount_tax, rate_tax,"
            + "inv_date, remark, inc_date, pro_id, verification, is_deleted, update_time";
    private static final String SELECT_FIELDS = INSERT_FIELDS;
    
    @Autowired
    @Qualifier("jdbcTemplateFinance")
    private JdbcTemplate jdbcTemplateFinance;

    @Override
    public Invoice get(Long invId) {
        // TODO Auto-generated method stub
        try {
            String sql = "select " + SELECT_FIELDS + " from " + TABLE_NAME
                    + " where inv_id=?";
            return jdbcTemplateFinance.queryForObject(sql, rowMapper, invId);
        } catch (Exception e) {
            // TODO: handle exception
            logger.debug("get failed." + e);
        }
        return null;
    }

    @Override
    public Boolean update(Invoice obj) {
        // TODO Auto-generated method stub
        try {
            String sql = "update " + TABLE_NAME + " set "
                    + "con_id=?, valorem_tax=?, amount=?, amount_tax=?, rate_tax=?,"
            + "inv_date=?, remark=?, inc_date=?, pro_id=?, verification=?, is_deleted=? "
            + "where inv_id=?" ;
            jdbcTemplateFinance.update(sql,
                    obj.getConId(),
                    obj.getValoremTax(),
                    obj.getAmount(),
                    obj.getAmountTax(),
                    obj.getRateTax(),
                    obj.getInvDate(),
                    obj.getRemark(),
                    obj.getIncDate(),
                    obj.getProId(),
                    obj.getVerification(),
                    obj.getIsDeleted(),
                    obj.getInvId()
                    );
            return true;
        } catch (Exception e) {
            // TODO: handle exception
            logger.debug("save failed." + e);
        }
        return false;
    }

    @Override
    public List<Invoice> findAll() {
        // TODO Auto-generated method stub
        return null;
    }
    
    @SuppressWarnings("deprecation")
    public Pagination<Invoice> findPagination(Long conId, String monthId, int currentPage, int numPerPage) {
        
        String sql = "select " + SELECT_FIELDS + " from " + TABLE_NAME + " where con_id=" + conId + " and "
                + "date_format(inv_date,'%YM%m')='" + monthId + "'";
        
        String sqlCount = sql.replace(SELECT_FIELDS, "COUNT(1)");
        
        int totalRows = 0;
        Pagination<Invoice> pagination = null;
        try {
            totalRows = jdbcTemplateFinance.queryForInt(sqlCount);
            pagination = new Pagination<Invoice>(totalRows, currentPage, numPerPage);
            sql = pagination.getMySQLPageSQL(sql, pagination.getCurrentPage());
            List<Invoice> resultList = jdbcTemplateFinance.query(sql, rowMapper);
            pagination.setResultList(resultList);
            logger.info(sql);
            return pagination;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        
        return pagination;
    }
    
    public List<Invoice> findAllByBrId(Long conId) {
        // TODO Auto-generated method stub
        try {
            String sql = "select " + SELECT_FIELDS + " from " + TABLE_NAME
                    + " where con_id=?";
            return jdbcTemplateFinance.query(sql, rowMapper, conId);
        } catch (Exception e) {
            // TODO: handle exception
            logger.debug("findAllByBrId failed." + e);
        }
        return null;
    }


    @Override
    public Boolean save(Invoice obj) {
        // TODO Auto-generated method stub
     // TODO Auto-generated method stub
        try {
            String sql = "insert into " + TABLE_NAME + " ( " + INSERT_FIELDS
                    + " ) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)" ;
            jdbcTemplateFinance.update(sql,
                    obj.getInvId(),
                    obj.getConId(),
                    obj.getValoremTax(),
                    obj.getAmount(),
                    obj.getAmountTax(),
                    obj.getRateTax(),
                    obj.getInvDate(),
                    obj.getRemark(),
                    obj.getIncDate(),
                    obj.getProId(),
                    obj.getVerification(),
                    obj.getIsDeleted(),
                    obj.getUpdateTime()
                    );
            return true;
        } catch (Exception e) {
            // TODO: handle exception
            logger.debug("save failed." + e);
        }
        return false;
    }
    
    public Boolean saveBatch(final List<Invoice> invoices) {
        // TODO Auto-generated method stub
     // TODO Auto-generated method stub
        try {
            String sql = "insert into " + TABLE_NAME + " ( " + INSERT_FIELDS
                    + " ) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)" ;
            
            jdbcTemplateFinance.batchUpdate(sql, new BatchPreparedStatementSetter(){

                @Override
                public void setValues(PreparedStatement ps, int i)
                        throws SQLException {
                    // TODO Auto-generated method stub
                    Invoice obj = invoices.get(i);
                    ps.setObject(1,obj.getInvId());
                    ps.setObject(2,obj.getConId());
                    ps.setObject(3,obj.getValoremTax());
                    ps.setObject(4,obj.getAmount());
                    ps.setObject(5,obj.getAmountTax());
                    ps.setObject(6,obj.getRateTax());
                    ps.setString(7,obj.getInvDate());
                    ps.setString(8,obj.getRemark());
                    ps.setString(9,obj.getIncDate());
                    ps.setLong(10,obj.getProId());
                    ps.setObject(11,obj.getVerification());
                    ps.setObject(12,obj.getIsDeleted());
                    ps.setObject(13,new Timestamp(System.currentTimeMillis()));
                    
                }

                @Override
                public int getBatchSize() {
                    // TODO Auto-generated method stub
                    return invoices.size();
                }});
            
           return true;
           
        } catch (Exception e) {
            // TODO: handle exception
            logger.debug("save failed." + e);
        }
        return false;
    }
    
    public Boolean updateIsDeleted(Integer isDeleted, Long invId) {
        // TODO Auto-generated method stub
        try {
            String sql = "update " + TABLE_NAME + " set is_deleted=? "
                    + "where inv_id=?";
            jdbcTemplateFinance.update(sql, isDeleted, invId);
            return true;
        } catch (Exception e) {
            // TODO: handle exception
            logger.debug("updateIsDeleted failed." + e);
        }
        return false;
    }

    @Override
    public Boolean delete(Long invId) {
        // TODO Auto-generated method stub
        try {
            String sql = "delete from " + TABLE_NAME + " where inv_id=? ";
            jdbcTemplateFinance.update(sql, invId);
            return true;
        } catch (Exception e) {
            // TODO: handle exception
            logger.debug("delete failed." + e);
        }
        return false;
    }
    
    
    private RowMapper<Invoice> rowMapper = new RowMapper<Invoice>() {

        @Override
        public Invoice mapRow(ResultSet rs, int rowNum) throws SQLException {
            // TODO Auto-generated method stub
            Invoice invoice = new Invoice();
            invoice.setInvId(rs.getLong("inv_id"));
            invoice.setConId(rs.getLong("con_id"));
            invoice.setValoremTax(rs.getDouble("valorem_tax"));
            invoice.setAmount(rs.getDouble("amount"));
            invoice.setAmountTax(rs.getDouble("amount_tax"));
            invoice.setRateTax(rs.getDouble("rate_tax"));
            invoice.setInvDate(DATE_FORMAT.format(rs.getDate("inv_date")));
            invoice.setRemark(rs.getString("remark"));
            invoice.setIncDate(DATE_FORMAT.format(rs.getDate("inc_date")));
            invoice.setProId(rs.getLong("pro_id"));
            invoice.setVerification(rs.getDouble("verification"));
            invoice.setIsDeleted(rs.getInt("is_deleted"));
            invoice.setUpdateTime(DATET_TIME_FORMAT.format(rs.getTimestamp("update_time")));
            return invoice;
        }
    };

    public List<Invoice> findAllByConId(Long conId) {
        // TODO Auto-generated method stub
        try {
            String sql = "select " + SELECT_FIELDS + " from " + TABLE_NAME
                    + " where is_deleted=0 and con_id=?";
            logger.info(sql);
            return jdbcTemplateFinance.query(sql, rowMapper, conId);
        } catch (Exception e) {
            // TODO: handle exception
            logger.debug("findAllByConId failed." + e);
        }
        return null;
    }

    public Boolean updateVerification(Long invId, Double amount) {
        // TODO Auto-generated method stub
        try {
            String sql = null;
            if (amount == null) {
                sql = "update " + TABLE_NAME + " set verification=amount "
                        + "where inv_id=?";
                jdbcTemplateFinance.update(sql, invId);
            }else {
                sql = "update " + TABLE_NAME + " set verification=verification + ? "
                        + "where inv_id=?";
                jdbcTemplateFinance.update(sql, amount, invId);
            }
            return true;
        } catch (Exception e) {
            // TODO: handle exception
            logger.debug("updateVerification failed." + e);
        }
        return false;
    }

}
