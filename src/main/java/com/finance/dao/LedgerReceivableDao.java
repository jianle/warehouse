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

import com.finance.model.LedgerReceivable;

@Repository
public class LedgerReceivableDao implements BaseDao<LedgerReceivable, Long> {
    
    private Logger logger = LoggerFactory.getLogger(LedgerReceivableDao.class);
    
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat DATET_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static final String TABLE_NAME    = "ledger_receivable";
    private static final String INSERT_FIELDS = "pay_company, amount, pay_date, verification, remark, update_time";
    private static final String SELECT_FIELDS = "lr_id, " + INSERT_FIELDS;
    
    @Autowired
    @Qualifier("jdbcTemplateFinance")
    private JdbcTemplate jdbcTemplateFinance;

    @Override
    public LedgerReceivable get(Long lrId) {
        // TODO Auto-generated method stub
        try {
            String sql = "select " + SELECT_FIELDS + " from " + TABLE_NAME + " where lr_id=?";
            return jdbcTemplateFinance.queryForObject(sql, rowMapper, lrId);
        } catch (Exception e) {
            // TODO: handle exception
            logger.debug("get failed." + e);
        }
        return null;
    }

    @Override
    public Boolean save(LedgerReceivable object) {
        // TODO Auto-generated method stub
        try {
            String sql = "insert into " + TABLE_NAME + " ( " + INSERT_FIELDS
                    + " ) values (?, ?, ?, ?, ?, ?)" ;
            jdbcTemplateFinance.update(sql,
                    object.getPayCompany(),
                    object.getAmount(),
                    object.getPayDate(),
                    object.getVerification(),
                    object.getRemark(),
                    object.getUpdateTime()
                    );
            return true;
        } catch (Exception e) {
            // TODO: handle exception
            logger.debug("save failed." + e);
        }
        return false;
    }

    @Override
    public List<LedgerReceivable> findAll() {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public Boolean update(LedgerReceivable object) {
        // TODO Auto-generated method stub
        try {
            String sql = "update " + TABLE_NAME + " set "
                    + "pay_company=?, amount=?, pay_date=?, verification=?, remark=? "
                    + "where lr_id=?" ;
            jdbcTemplateFinance.update(sql,
                    object.getPayCompany(),
                    object.getAmount(),
                    object.getPayDate(),
                    object.getVerification(),
                    object.getRemark(),
                    object.getLr_id()
                    );
            return true;
        } catch (Exception e) {
            // TODO: handle exception
            logger.debug("save failed." + e);
        }
        return false;
    }

    @Override
    public Boolean delete(Long lrId) {
        // TODO Auto-generated method stub
        try {
            String sql = "delete from " + TABLE_NAME + " where lr_id=?";
            jdbcTemplateFinance.update(sql, lrId);
            return true;
        } catch (Exception e) {
            // TODO: handle exception
            logger.debug("delete failed." + e);
        }
        return false;
    }
    
    private RowMapper<LedgerReceivable> rowMapper = new RowMapper<LedgerReceivable>() {

        @Override
        public LedgerReceivable mapRow(ResultSet rs, int rowNum)
                throws SQLException {
            LedgerReceivable ledgerReceivable = new LedgerReceivable();
            ledgerReceivable.setLr_id(rs.getLong("lr_id"));
            ledgerReceivable.setPayCompany(rs.getString("pay_company"));
            ledgerReceivable.setAmount(rs.getDouble("amount"));
            ledgerReceivable.setPayDate(DATE_FORMAT.format(rs.getDate("pay_date")));
            ledgerReceivable.setVerification(rs.getDouble("verification"));
            ledgerReceivable.setRemark(rs.getString("remark"));
            ledgerReceivable.setUpdateTime(DATET_TIME_FORMAT.format(rs.getTime("update_time")));
            // TODO Auto-generated method stub
            return null;
        }
    };

}
