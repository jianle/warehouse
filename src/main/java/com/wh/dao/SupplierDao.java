package com.wh.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;

import com.wh.model.Supplier;

/*
 * 供应商操作实体
 * 增删查改操作
 */

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
        // TODO Auto-generated method stub
        try {
            String sql = "SELECT " + SELECT_FIELDS + " FROM " + TABLE_NAME + " WHERE id=? ";
        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;
    }

    @Override
    public Boolean save(Supplier object) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Supplier> findAll() {
        // TODO Auto-generated method stub
        return null;
    }
    
    

}
