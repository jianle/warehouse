package com.wh.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.wh.form.model.SupplierSearchForm;
import com.wh.model.Pagination;
import com.wh.model.Supplier;

/*
 * 供应商操作实体
 * 增删查改操作
 */

@Repository
public class SupplierDao implements BaseDao<Supplier, Long> {
    
    private Logger logger = LoggerFactory.getLogger(SupplierDao.class);
    
    private static final String TABLE_NAME    = "supplier";
    private static final String INSERT_FIELDS = "name, shortname, address, contact_name, contact_tel, is_disabled, insert_dt";
    private static final String SELECT_FIELDS = "s_id, " + INSERT_FIELDS + ", update_time";
    
    @Autowired
    @Qualifier("jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @Override
    public Supplier get(Long id) {
        // 通过Id获取Supplier
        try {
            String sql = "SELECT " + SELECT_FIELDS + " FROM " + TABLE_NAME + " WHERE id=? ";
            return jdbcTemplate.queryForObject(sql, rowMapper, id);
        } catch (Exception e) {
            // TODO: handle exception
            logger.debug("Supplier get id failed ." + e);
            return null;
        }
    }

    @Override
    public Boolean save(Supplier supplier) {
        // 保存Supplier
        try {
            String sql = "INSERT INTO " + TABLE_NAME + " (" + INSERT_FIELDS 
                    + ") VALUES (?, ?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql, supplier.getName(),
                    supplier.getShortname(),
                    supplier.getAddress(),
                    supplier.getContactName(),
                    supplier.getContactTel(),
                    supplier.getIsDisabled(),
                    supplier.getInsertDt()
                    );
            return true;
        } catch (Exception e) {
            // TODO: handle exception
            logger.debug("Supplier save failed ." + e);
        }
        return null;
    }

    @Override
    public List<Supplier> findAll() {
        // 查找所有
        try {
            String sql = "SELECT " + SELECT_FIELDS + " FROM " + TABLE_NAME + " LIMIT 10000 ";
            return jdbcTemplate.query(sql, rowMapper);
        } catch (Exception e) {
            // TODO: handle exception
            logger.debug("Supplier findAll failed ." + e);
            return null;
        }
    }
    
    @SuppressWarnings("deprecation")
    public Pagination<Supplier> findByColumnValue(SupplierSearchForm supplierSearchForm) {
        // 通过列的名称和类型查找数据
        List<Supplier> resultSuppliers;
        int totalRows = 1;
        Pagination<Supplier> supplierPagination;
        try {
            String sql = "";
            String sqlTotal = "";
            if ( !supplierSearchForm.getIsDisable().equals("A") ) {
                sql = "SELECT " + SELECT_FIELDS + " FROM " + TABLE_NAME + " WHERE is_disabled=? ";
                if (supplierSearchForm.getValue() != null && !"".equals(supplierSearchForm.getValue())) {
                    
                    sql = sql + " and " + supplierSearchForm.getColumn() + " like ? ";
                    sqlTotal = sql.replace(SELECT_FIELDS, "COUNT(1)");
                    totalRows = jdbcTemplate.queryForInt(sqlTotal, supplierSearchForm.getIsDisable(),
                            "%" + supplierSearchForm.getValue() + "%");
                    
                    supplierPagination = new Pagination<Supplier>(totalRows, supplierSearchForm.getCurrentPage());
                    sql = supplierPagination.getMySQLPageSQL(sql, supplierSearchForm.getCurrentPage());
                    
                    resultSuppliers = jdbcTemplate.query(sql, rowMapper, supplierSearchForm.getIsDisable(),
                            "%" + supplierSearchForm.getValue() + "%");
                    
                    supplierPagination.setResultList(resultSuppliers);
                    logger.info(sql);
                    return supplierPagination;
                }
                
                sqlTotal = sql.replace(SELECT_FIELDS, "COUNT(1)");
                totalRows = jdbcTemplate.queryForInt(sqlTotal, supplierSearchForm.getIsDisable());
                
                supplierPagination = new Pagination<Supplier>(totalRows, supplierSearchForm.getCurrentPage());
                sql = supplierPagination.getMySQLPageSQL(sql, supplierSearchForm.getCurrentPage());
                
                resultSuppliers = jdbcTemplate.query(sql, rowMapper, supplierSearchForm.getIsDisable());
                supplierPagination.setResultList(resultSuppliers);
                logger.info(sql);
                return supplierPagination;
            }else {
                if (supplierSearchForm.getValue() != null && !"".equals(supplierSearchForm.getValue())) {
                    sql = "SELECT " + SELECT_FIELDS + " FROM " + TABLE_NAME + " WHERE " + supplierSearchForm.getColumn() + " like ? ";
                    sqlTotal = sql.replace(SELECT_FIELDS, "COUNT(1)");
                    totalRows = jdbcTemplate.queryForInt(sqlTotal,
                            "%" + supplierSearchForm.getValue() + "%");
                    
                    supplierPagination = new Pagination<Supplier>(totalRows, supplierSearchForm.getCurrentPage());
                    sql = supplierPagination.getMySQLPageSQL(sql, supplierSearchForm.getCurrentPage());
                    
                    resultSuppliers = jdbcTemplate.query(sql, rowMapper, 
                            "%" + supplierSearchForm.getValue() + "%");
                    supplierPagination.setResultList(resultSuppliers);
                    logger.info(sql);
                    return supplierPagination;
                    
                }
                sql = "SELECT " + SELECT_FIELDS + " FROM " + TABLE_NAME ;
                sqlTotal = sql.replace(SELECT_FIELDS, "COUNT(1)");
                totalRows = jdbcTemplate.queryForInt(sqlTotal);
                
                supplierPagination = new Pagination<Supplier>(totalRows, supplierSearchForm.getCurrentPage());
                sql = supplierPagination.getMySQLPageSQL(sql, supplierSearchForm.getCurrentPage());
                
                resultSuppliers = jdbcTemplate.query(sql, rowMapper);
                supplierPagination.setResultList(resultSuppliers);
                logger.info(sql);
                
                return supplierPagination;
            }
            
        } catch (Exception e) {
            // TODO: handle exception
            logger.debug("Supplier findbycolumnvalue failed ." + e);
            return null;
        }
    }
    
    
    
    private RowMapper<Supplier> rowMapper = new RowMapper<Supplier>() {
        
        @Override
        public Supplier mapRow(ResultSet rs, int rowNum) throws SQLException {
            // TODO Auto-generated method stub
            
            Supplier supplier = new Supplier();
            supplier.setsId(rs.getLong("s_id"));
            supplier.setName(rs.getString("name"));
            supplier.setShortname(rs.getString("shortname"));
            supplier.setAddress(rs.getString("address"));
            supplier.setContactName(rs.getString("contact_name"));
            supplier.setContactTel(rs.getString("contact_tel"));
            supplier.setIsDisabled(rs.getString("is_disabled"));
            
            supplier.setInsertDt(rs.getTimestamp("insert_dt"));
            supplier.setUpdateTime(rs.getTimestamp("update_time"));
            
            return supplier;
        }
    };

}
