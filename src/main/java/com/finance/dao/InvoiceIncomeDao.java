package com.finance.dao;

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

import com.finance.model.InvoiceIncome;


@Repository
public class InvoiceIncomeDao implements BaseDao<InvoiceIncome, Long> {
    
    private Logger logger = LoggerFactory.getLogger(InvoiceIncomeDao.class);
    
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat DATET_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static final String TABLE_NAME    = "invoice_income";
    private static final String INSERT_FIELDS = "inv_id, inv_head, valorem_tax, amount, amount_tax, rate_tax,"
            + "inv_date, inv_type, inv_to_company, remark, rate_rebate, is_deleted, update_time";
    private static final String SELECT_FIELDS = INSERT_FIELDS;
    
    @Autowired
    @Qualifier("jdbcTemplateFinance")
    private JdbcTemplate jdbcTemplateFinance;

    @Override
    public InvoiceIncome get(Long invId) {
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
    public Boolean save(InvoiceIncome obj) {
        // TODO Auto-generated method stub
        try {
            String sql = "insert into " + TABLE_NAME + " ( " + INSERT_FIELDS
                    + " ) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)" ;
            jdbcTemplateFinance.update(sql,
                    obj.getInvId(),
                    obj.getInvHead(),
                    obj.getValoremTax(),
                    obj.getAmount(),
                    obj.getAmountTax(),
                    obj.getRateTax(),
                    obj.getInvDate(),
                    obj.getInvType(),
                    obj.getInvToCompany(),
                    obj.getRemark(),
                    obj.getRateRebate(),
                    obj.getIsDeleted(),
                    obj.getUpdateTime()
                    );
            
        } catch (Exception e) {
            // TODO: handle exception
            logger.debug("save failed." + e);
        }
        return false;
    }

    @Override
    public List<InvoiceIncome> findAll() {
        // TODO Auto-generated method stub
        return null;
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
    public Boolean update(InvoiceIncome object) {
        // TODO Auto-generated method stub
        return null;
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
    
    
    private RowMapper<InvoiceIncome> rowMapper = new RowMapper<InvoiceIncome>() {

        @Override
        public InvoiceIncome mapRow(ResultSet rs, int rowNum) throws SQLException {
            // TODO Auto-generated method stub
            InvoiceIncome invoiceIncome = new InvoiceIncome();
            invoiceIncome.setInvId(rs.getLong("inv_id"));
            invoiceIncome.setInvHead(rs.getString("inv_head"));
            invoiceIncome.setValoremTax(rs.getDouble("valorem_tax"));
            invoiceIncome.setAmount(rs.getDouble("amount"));
            invoiceIncome.setAmountTax(rs.getDouble("amount_tax"));
            invoiceIncome.setRateTax(rs.getDouble("rate_tax"));
            invoiceIncome.setInvDate(DATE_FORMAT.format(rs.getDate("inv_date")));
            invoiceIncome.setInvType(rs.getInt("inv_type"));
            invoiceIncome.setInvToCompany(rs.getString("inv_to_company"));
            invoiceIncome.setRemark(rs.getString("remark"));
            invoiceIncome.setRateRebate(rs.getDouble("rate_rebate"));
            invoiceIncome.setIsDeleted(rs.getInt("is_deleted"));
            invoiceIncome.setUpdateTime(DATET_TIME_FORMAT.format(rs.getTime("update_time")));
            return invoiceIncome;
        }
    };
    
    
}
