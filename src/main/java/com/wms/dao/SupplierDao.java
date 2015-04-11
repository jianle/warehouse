package com.wms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.wms.form.model.SupplierSearchForm;
import com.wms.model.Pagination;
import com.wms.model.Supplier;


/*
 * 供应商操作实体
 * 增删查改操作
 */

@Repository
public class SupplierDao implements BaseDao<Supplier, Long> {
    
    private Logger logger = LoggerFactory.getLogger(SupplierDao.class);
    
    private static final String TABLE_NAME    = "supplier";
    private static final String INSERT_FIELDS = "name, shortname, address, contact_name, contact_tel, is_disabled, mbcode, insert_dt";
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
                    + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql, supplier.getName(),
                    supplier.getShortname(),
                    supplier.getAddress(),
                    supplier.getContactName(),
                    supplier.getContactTel(),
                    supplier.getIsDisabled(),
                    supplier.getMbcode(),
                    supplier.getInsertDt()
                    );
            return true;
        } catch (Exception e) {
            // TODO: handle exception
            logger.debug("Supplier save failed ." + e);
        }
        return null;
    }
    
    public List<Supplier> findSuggestAll() {
        // 通过Id获取Supplier
        try {
            String sql = "SELECT s_id, name, shortname FROM " + TABLE_NAME ;
            return jdbcTemplate.query(sql, rowMapperSuggest);
        } catch (Exception e) {
            // TODO: handle exception
            logger.debug("Supplier get id failed ." + e);
            return null;
        }
    }

    @Override
    public List<Supplier> findAll() {
        // 查找所有
        try {
            String sql = "SELECT " + SELECT_FIELDS + " FROM " + TABLE_NAME;
            return jdbcTemplate.query(sql, rowMapper);
        } catch (Exception e) {
            // TODO: handle exception
            logger.debug("Supplier findAll failed ." + e);
            return null;
        }
    }
    
    // 通过传入List 返回Map<id, name>
    public Map<Long, String> findBySIdList(Set<Long> sIdsSet){
        String sql = "SELECT s_id, name FROM " + TABLE_NAME;
        StringBuffer whereIs = new StringBuffer("");
        Map<Long, String> maps = new HashMap<Long, String>();
        List<Long> sIds = new ArrayList<Long>(sIdsSet);
        logger.info("findBySIdList:" + sIds.toString());
        
        if (sIds == null || sIds.size() == 0) {
            return null;
        }else {
            for (int i = 0; i < sIds.size(); i++) {
                if (i==0) {
                    whereIs.append(" WHERE s_id in( ").append(sIds.get(i));
                }else {
                    whereIs.append(", ").append(sIds.get(i));
                }
            }
        }
        whereIs.append(")");
        
        try {
            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql + whereIs.toString());
            for (int i = 0; i < resultList.size(); i++) {
                Map<String, Object> tmpMap = resultList.get(i);
                maps.put(Long.valueOf(tmpMap.get("s_id").toString()), String.valueOf(tmpMap.get("name")));
            }
            return maps;
        } catch (Exception e) {
            // TODO: handle exception
            logger.debug("findBySIdList suppliers fialed ." + e);
        }
        
        return null;
    }
    
    
    
    @SuppressWarnings("deprecation")
    public Pagination<Supplier> findByColumnValue(SupplierSearchForm supplierSearchForm) {
        /*
         *  通过列的名称和类型查找数据
         *  分页操作也在其中处理，代码开始乱了
         */
        List<Supplier> resultSuppliers;
        int totalRows = 1;
        Pagination<Supplier> supplierPagination;
        
        StringBuffer sqlBuf = new StringBuffer("SELECT " + SELECT_FIELDS + " FROM " + TABLE_NAME);
        String isWhere = "";
        String column = supplierSearchForm.getColumn().trim();
        //判断是否有效
        if (! supplierSearchForm.getIsDisable().equals("A")) {
            isWhere = " WHERE is_disabled='" + supplierSearchForm.getIsDisable().trim() + "'";
        }
        if (supplierSearchForm.getValue() != null && !"".equals(supplierSearchForm.getValue())) {
            String colValue = supplierSearchForm.getValue().trim();
            if (! isWhere.isEmpty()) {
                isWhere += " AND " + column + " LIKE '%" + colValue + "%'";
            } else {
                isWhere = " WHERE " + column + " LIKE '%" + colValue + "%'";
            }
        }
        
        String sql = sqlBuf.append(isWhere).toString();
        String sqlTotal = sql.replace(SELECT_FIELDS, "COUNT(1)");
        
        try {
            totalRows = jdbcTemplate.queryForInt(sqlTotal);
            supplierPagination = new Pagination<Supplier>(totalRows, supplierSearchForm.getCurrentPage(), supplierSearchForm.getNumPerPage());
            
            sql = supplierPagination.getMySQLPageSQL(sql, supplierSearchForm.getCurrentPage());
            
            resultSuppliers = jdbcTemplate.query(sql, rowMapper);
            supplierPagination.setResultList(resultSuppliers);
            logger.info(sql);
            return supplierPagination;
        } catch (Exception e) {
            // TODO: handle exception
            logger.debug("Supplier findbycolumnvalue failed ." + e);
        }
        
        return null;
        
    }
    
    public Boolean update(Supplier supplier) {
        // 通过sId来跟新一条记录
        try {
            String sql = "UPDATE "+ TABLE_NAME + " SET "
                    + " name=?, shortname=?, address=?, contact_name=?, contact_tel=?, is_disabled=?, mbcode=? "
                    + " WHERE s_id=? ";
            jdbcTemplate.update(sql, supplier.getName(),
                    supplier.getShortname(),
                    supplier.getAddress(),
                    supplier.getContactName(),
                    supplier.getContactTel(),
                    supplier.getIsDisabled(),
                    supplier.getMbcode(),
                    supplier.getsId());
            return true;
        } catch (Exception e) {
            // TODO: handle exception
            logger.debug("update supplier failed ." + e);
        }
        
        return false;
    }
    
    public Boolean delete(Long sId) {
        try {
            String sql = "DELETE FROM " + TABLE_NAME + " WHERE s_id=? ";
            jdbcTemplate.update(sql, sId);
            return true;
        } catch (Exception e) {
            // TODO: handle exception
            logger.debug("delete supplier failed ." + e);
        }
        return false;
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
            supplier.setMbcode(rs.getString("mbcode"));
            
            supplier.setInsertDt(rs.getTimestamp("insert_dt"));
            supplier.setUpdateTime(rs.getTimestamp("update_time"));
            
            return supplier;
        }
    };
    
    private RowMapper<Supplier> rowMapperSuggest = new RowMapper<Supplier>() {
        
        @Override
        public Supplier mapRow(ResultSet rs, int rowNum) throws SQLException {
            // TODO Auto-generated method stub
            
            Supplier supplier = new Supplier();
            supplier.setsId(rs.getLong("s_id"));
            supplier.setName(rs.getString("name"));
            supplier.setShortname(rs.getString("shortname"));
            return supplier;
        }
    };
    
}
