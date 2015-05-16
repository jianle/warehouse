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
import com.wms.model.Pagination;

@Repository
public class BillReceivableDao implements BaseDao<BillReceivable, Long> {
    
    private Logger logger = LoggerFactory.getLogger(BillReceivableDao.class);
    
    private static final String TABLE_NAME    = "bill_receivable";
    private static final String INSERT_FIELDS = "con_id, br_date, amount, remark";
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
                    object.getConId(),
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
    
    @SuppressWarnings("deprecation")
    public  Pagination<BillReceivable> findPagination(String startDate, String endDate
            , String conIds, int currentPage, int numPerPage) {
        // TODO Auto-generated method stub
        
        StringBuilder sqlBuilder = new StringBuilder("select ");
        sqlBuilder.append(SELECT_FIELDS)
                  .append(" from ")
                  .append(TABLE_NAME);
        
        StringBuilder isWhere = new StringBuilder();
        Boolean flagIsWhere = false;
        if (startDate.compareTo(endDate)<0) {
            isWhere.append(" where br_date between '").append(startDate).append("' and '").append(endDate).append("' ");
            flagIsWhere = true;
        }
        
        if (conIds != null && !conIds.equals("")) {
        	
            if (flagIsWhere) {
                isWhere.append(" and con_id in ").append(conIds);
            } else {
                isWhere.append(" where con_id in ").append(conIds);
            }
            flagIsWhere = true;
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
            Pagination<BillReceivable> pagination = new Pagination<BillReceivable>(totalRows, currentPage, numPerPage);
            sql = pagination.getMySQLPageSQL(sql, pagination.getCurrentPage());
            List<BillReceivable> resultList = jdbcTemplateFinance.query(sql, rowMapper);
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
    public Boolean update(BillReceivable object) {
        // TODO Auto-generated method stub
        try {
            String sql = "update " + TABLE_NAME + " set "
                    + "con_id=?, br_date=?, amount=?, remark=? "
                    + "where br_id=?";
            jdbcTemplateFinance.update(sql, 
                    object.getConId(),
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
            billReceivable.setConId((rs.getLong("con_id")));
            billReceivable.setBrDate(new SimpleDateFormat("yyyy-MM-dd").format(rs.getDate("br_date")));
            billReceivable.setAmount(rs.getDouble("amount"));
            billReceivable.setRemark(rs.getString("remark"));
            return billReceivable;
        }
    };

}
