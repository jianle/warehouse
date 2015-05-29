package com.finance.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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
import com.wms.model.Pagination;

@Repository
public class LedgerReceivableDao implements BaseDao<LedgerReceivable, Long> {
    
    private Logger logger = LoggerFactory.getLogger(LedgerReceivableDao.class);
    
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat DATET_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static final String TABLE_NAME    = "ledger_receivable";
    private static final String INSERT_FIELDS = "con_id, pro_id, month_id, amount, pay_date, verification, remark, update_time";
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
                    object.getConId(),
                    object.getAmount(),
                    object.getPayDate(),
                    object.getVerification(),
                    object.getRemark(),
                    new Timestamp(System.currentTimeMillis())
                    );
            return true;
        } catch (Exception e) {
            // TODO: handle exception
            logger.debug("save failed." + e);
        }
        return false;
    }
    
    public Boolean saveByInvoice(Long conId, Long proId, String monthId) {
        try {
            
            String sql = "insert into ledger_receivable(con_id, pro_id, month_id, amount, verification,update_time) "
                    + "select con_id, pro_id,date_format(inv_date,'%YM%m') month_id,sum(amount) amount "
                    + ",sum(verification) verification,current_timestamp() from invoice "
                    + "where con_id=? and pro_id=? and date_format(inv_date,'%YM%m')=? and is_deleted=0 "
                    + "group by 1,2,3";
            logger.info(sql);
            jdbcTemplateFinance.update(sql, conId, proId, monthId);
            return true;
        } catch (Exception e) {
            // TODO: handle exception
        }
        
        return false;
    }
    
    public Boolean deleteByInvIdAndMonthId(Long conId, Long proId, String monthId) {
        try {
            String sql = "delete from " + TABLE_NAME + " where con_id=? and pro_id=? and month_id=?";
            jdbcTemplateFinance.update(sql, conId, proId, monthId);
            return true;
        } catch (Exception e) {
            // TODO: handle exception
            logger.debug("deleteByInvIdAndMonthId failed. " + e);
        }
        return false;
    }

    @Override
    public List<LedgerReceivable> findAll() {
        // TODO Auto-generated method stub
        return null;
    }
    
    @SuppressWarnings("deprecation")
    public Pagination<LedgerReceivable> findPagination(String startDate,
            String endDate, String conIds, Long proId, Integer currentPage,
            Integer numPerPage) {
        // TODO Auto-generated method stub
        StringBuilder sqlBuilder = new StringBuilder("select ");
        sqlBuilder.append(SELECT_FIELDS)
                  .append(" from ")
                  .append(TABLE_NAME);
        
        StringBuilder isWhere = new StringBuilder();
        Boolean flagIsWhere = true;
        isWhere.append(" where month_id between '").append(startDate).append("' and '").append(endDate).append("' ");
        
        if (conIds != null && !conIds.equals("")) {
            
            if (flagIsWhere) {
                isWhere.append(" and con_id in ").append(conIds);
            } else {
                isWhere.append(" where con_id in ").append(conIds);
            }
            flagIsWhere = true;
        }
        
        if (proId != null && proId != 0) {
            isWhere.append(" and pro_id=").append(proId);
        }
        
        String sql = null;
        if (flagIsWhere) {
            sql = sqlBuilder.append(isWhere.toString()).toString();
        } else {
            sql = sqlBuilder.toString();
        }
        
        String sqlTotal = sql.replace(SELECT_FIELDS, "COUNT(1)");
        int totalRows = 0;
        
        try {
            totalRows = jdbcTemplateFinance.queryForInt(sqlTotal);
            Pagination<LedgerReceivable> pagination = new Pagination<LedgerReceivable>(totalRows, currentPage, numPerPage);
            sql = pagination.getMySQLPageSQL(sql, pagination.getCurrentPage());
            List<LedgerReceivable> resultList = jdbcTemplateFinance.query(sql, rowMapper);
            logger.debug("resultList:"+resultList.toString());
            pagination.setResultList(resultList);
            logger.info(sql);
            return pagination;
        } catch (Exception e) {
            // TODO: handle exception
            logger.debug("findPagination failed." + e);
        }
        return null;
    }
    
    @Override
    public Boolean update(LedgerReceivable object) {
        // TODO Auto-generated method stub
        try {
            String sql = "update " + TABLE_NAME + " set "
                    + "con_id=?, amount=?, pay_date=?, verification=?, remark=? "
                    + "where lr_id=?" ;
            jdbcTemplateFinance.update(sql,
                    object.getConId(),
                    object.getAmount(),
                    object.getPayDate(),
                    object.getVerification(),
                    object.getRemark(),
                    object.getLrId()
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
            ledgerReceivable.setLrId(rs.getLong("lr_id"));
            ledgerReceivable.setConId(rs.getLong("con_id"));
            ledgerReceivable.setProId(rs.getLong("pro_id"));
            ledgerReceivable.setMonthId(rs.getString("month_id"));
            ledgerReceivable.setAmount(rs.getDouble("amount"));
            ledgerReceivable.setPayDate(DATE_FORMAT.format(rs.getDate("pay_date")));
            ledgerReceivable.setVerification(rs.getDouble("verification"));
            ledgerReceivable.setRemark(rs.getString("remark"));
            ledgerReceivable.setUpdateTime(DATET_TIME_FORMAT.format(rs.getTime("update_time")));
            // TODO Auto-generated method stub
            return ledgerReceivable;
        }
    };

    public Boolean updateVerification(Long lrId, Double verifi) {
        try {
            String sql = "update " + TABLE_NAME + " set verification=verification + ? "
                    + "where lr_id=?";
            jdbcTemplateFinance.update(sql, verifi, lrId);
            return true;
        } catch (Exception e) {
            // TODO: handle exception
            logger.debug("updateVerification failed." + e);
        }
        return false;
    }

    public List<LedgerReceivable> findToDebtor(String startMonth,
            String endMonth, String conIds) {
        StringBuilder sqlBuilder = new StringBuilder("select ");
        sqlBuilder.append(SELECT_FIELDS)
                  .append(" from ")
                  .append(TABLE_NAME);
        
        sqlBuilder.append(" where month_id between '").append(startMonth).append("' and '").append(endMonth).append("' ");
        
        if (conIds != null && !conIds.equals("()")) {
            sqlBuilder.append(" and con_id in ").append(conIds);
        }
        
        logger.info(sqlBuilder.toString());
        try {
            return jdbcTemplateFinance.query(sqlBuilder.toString(), rowMapper);
        } catch (Exception e) {
            // TODO Auto-generated method stub
            logger.debug("findToDebtor failed." + e);
            return null;
        }
        
    }

}
