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

import com.finance.model.Debtor;

@Repository
public class DebtorDao implements BaseDao<Debtor, Long> {
    
    private Logger logger = LoggerFactory.getLogger(DebtorDao.class);
    
    private static final SimpleDateFormat DATET_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static final String TABLE_NAME    = "debtor";
    private static final String INSERT_FIELDS = "mon_id, consumer, inv_company, amount, remark, update_time";
    private static final String SELECT_FIELDS = "de_id, " + INSERT_FIELDS;
    
    @Autowired
    @Qualifier("jdbcTemplateFinance")
    private JdbcTemplate jdbcTemplateFinance;
    
    @Override
    public Debtor get(Long id) {
        // TODO Auto-generated method stub
        try {
            String sql = "select " + SELECT_FIELDS + " from " + TABLE_NAME + " where de_id=?";
            return jdbcTemplateFinance.queryForObject(sql, rowMapper, id);
        } catch (Exception e) {
            // TODO: handle exception
            logger.debug("get failed." + e);
        }
        return null;
    }
    @Override
    public Boolean save(Debtor object) {
        // TODO Auto-generated method stub
        try {
            String sql = "insert into " + TABLE_NAME + " ( " + INSERT_FIELDS
                    + " ) values (?, ?, ?, ?, ?, ?)" ;
            jdbcTemplateFinance.update(sql, 
                    object.getMonId(),
                    object.getConsumer(),
                    object.getInvCompany(),
                    object.getAmount(),
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
    public List<Debtor> findAll() {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public Boolean update(Debtor object) {
        // TODO Auto-generated method stub
        try {
            String sql = "update " + TABLE_NAME + " set "
                    + " mon_id=?, consumer=?, inv_company=?, amount=?, remark=? "
                    + " where de_id=? ";
            jdbcTemplateFinance.update(sql, 
                    object.getMonId(),
                    object.getConsumer(),
                    object.getInvCompany(),
                    object.getAmount(),
                    object.getRemark(),
                    object.getDeId()
                    );
            return true;
        } catch (Exception e) {
            // TODO: handle exception
            logger.debug("update failed." + e);
        }
        return false;
    }
    @Override
    public Boolean delete(Long deId) {
        // TODO Auto-generated method stub
        try {
            String sql = "delete from " + TABLE_NAME + " where de_id=? ";
            jdbcTemplateFinance.update(sql, deId);
            return true;
        } catch (Exception e) {
            // TODO: handle exception
            logger.debug("delete failed." + e);
        }
        return false;
    }
    
    private RowMapper<Debtor> rowMapper = new RowMapper<Debtor>() {

        @Override
        public Debtor mapRow(ResultSet rs, int rowNum) throws SQLException {
            // TODO Auto-generated method stub
            Debtor debtor = new Debtor(
                    rs.getLong("de_id"),
                    rs.getString("mon_id"),
                    rs.getString("consumer"),
                    rs.getString("inv_company"),
                    rs.getDouble("amount"),
                    rs.getString("remark"),
                    DATET_TIME_FORMAT.format(rs.getTime("update_time"))
                    );
            return debtor;
        }
    };

}
