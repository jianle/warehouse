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

import com.finance.model.BillReceivable;

@Repository
public class BillReceivableDao implements BaseDao<BillReceivable, Long> {
    
    private Logger logger = LoggerFactory.getLogger(BillReceivableDao.class);
    
    private static final String TABLE_NAME    = "bill_receivable";
    private static final String INSERT_FIELDS = "customer_company, br_date, amount, remark";
    private static final String SELECT_FIELDS = "br_id, " + INSERT_FIELDS;
    
    @Autowired
    @Qualifier("jdbcTemplateFinance")
    private JdbcTemplate jdbcTemplateFinance;

    @Override
    public BillReceivable get(Long id) {
        // TODO Auto-generated method stub
        try {
            String sql = "select " + SELECT_FIELDS + " from " + TABLE_NAME + " where br_id=? ";
            return jdbcTemplateFinance.queryForObject(sql, rowMapper, id);
        } catch (Exception e) {
            // TODO: handle exception
            logger.debug("get faield." + e);
        }
        return null;
    }

    @Override
    public Boolean save(BillReceivable object) {
        // TODO Auto-generated method stub
        try {
            String sql = "insert into " + TABLE_NAME + " ("
                    + INSERT_FIELDS + " ) values (?, ?, ?, ?)";
            jdbcTemplateFinance.update(sql, 
                    object.getCustomerCompany(),
                    object.getBrDate(),
                    object.getAmount(),
                    object.getRemark()
                    );
            return true;
        } catch (Exception e) {
            // TODO: handle exception
            logger.debug("save failed." + e);
        }
        return false;
    }

    @Override
    public List<BillReceivable> findAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean update(BillReceivable object) {
        // TODO Auto-generated method stub
        try {
            String sql = "update " + TABLE_NAME + " set "
                    + "customer_company=?, br_date=?, amount=?, remark=? "
                    + "where br_id=?";
            jdbcTemplateFinance.update(sql, 
                    object.getCustomerCompany(),
                    object.getBrDate(),
                    object.getAmount(),
                    object.getRemark(),
                    object.getBrId()
                    );
            return true;
        } catch (Exception e) {
            // TODO: handle exception
            logger.debug("save failed." + e);
        }
        return false;
    }

    @Override
    public Boolean delete(Long id) {
        // TODO Auto-generated method stub
        try {
            String sql = "delete from " + TABLE_NAME + " where br_id=? ";
            jdbcTemplateFinance.update(sql, id);
            return true;
        } catch (Exception e) {
            // TODO: handle exception
            logger.debug("delete failed." + e);
        }
        return false;
    }
    
    private RowMapper<BillReceivable> rowMapper = new RowMapper<BillReceivable>() {

        @Override
        public BillReceivable mapRow(ResultSet rs, int rowNum)
                throws SQLException {
            // TODO Auto-generated method stub
            BillReceivable billReceivable = new BillReceivable();
            billReceivable.setBrId(rs.getLong("br_id"));
            billReceivable.setCustomerCompany(rs.getString("customer_company"));
            billReceivable.setBrDate(new SimpleDateFormat("yyyy-MM-dd").format(rs.getDate("br_date")));
            billReceivable.setAmount(rs.getDouble("amount"));
            billReceivable.setRemark(rs.getString("remark"));
            return billReceivable;
        }
    };

}
