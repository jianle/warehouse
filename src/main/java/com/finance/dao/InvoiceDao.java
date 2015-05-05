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

import com.finance.model.Invoice;


@Repository
public class InvoiceDao implements BaseDao<Invoice, Long> {

    private Logger logger = LoggerFactory.getLogger(InvoiceDao.class);
    
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat DATET_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static final String TABLE_NAME    = "invoice";
    private static final String INSERT_FIELDS = "br_id, inv_id, inv_head, valorem_tax, amount, amount_tax, rate_tax,"
            + "inv_date, remark, inc_date, inv_to_company, verification, update_time";
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
    public Boolean save(Invoice object) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Invoice> findAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean update(Invoice object) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean delete(Long id) {
        // TODO Auto-generated method stub
        return null;
    }
    
    
    private RowMapper<Invoice> rowMapper = new RowMapper<Invoice>() {

        @Override
        public Invoice mapRow(ResultSet rs, int rowNum) throws SQLException {
            // TODO Auto-generated method stub
            Invoice invoice = new Invoice();
            invoice.setBrId(rs.getLong("br_id"));
            invoice.setInvId(rs.getLong("inv_id"));
            invoice.setInvHead(rs.getString("inv_head"));
            invoice.setValoremTax(rs.getDouble("valorem_tax"));
            invoice.setAmount(rs.getDouble("amount"));
            invoice.setAmountTax(rs.getDouble("amount_tax"));
            invoice.setRateTax(rs.getDouble("rate_tax"));
            invoice.setInvDate(DATE_FORMAT.format(rs.getDate("inv_date")));
            invoice.setRemark(rs.getString("remark"));
            invoice.setIncDate(DATE_FORMAT.format(rs.getDate("inc_date")));
            invoice.setInvToCompany(rs.getString("inv_to_company"));
            invoice.setVerification(rs.getDouble("verification"));
            invoice.setUpdateTime(DATET_TIME_FORMAT.format(rs.getDate("update_time")));
            return invoice;
        }
    };

}
