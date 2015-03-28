package com.wms.dao;

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

import com.wms.model.Goods;

/*
 * 商品操作实体
 * 增删改操作
 * 
 */

@Repository
public class GoodsDao implements BaseDao<Goods, Long> {
    
    private Logger logger = LoggerFactory.getLogger(GoodsDao.class);
    
    private static final String TABLE_NAME    = "goods";
    private static final String INSERT_FIELDS = "s_id, name, length, width, height, weight, g_id_supplier"
                                            + ", amount, is_disabled, insert_dt";
    private static final String SELECT_FIELDS = "g_id, " + INSERT_FIELDS + ", update_time";
    
    @Autowired
    @Qualifier("jdbcTemplate")
    private JdbcTemplate jdbcTemplate;


    @Override
    public Goods get(Long id) {
        // 通过Id获取对象
        try {
            String sql = "SELECT " + SELECT_FIELDS + " FROM " + TABLE_NAME + " WHERE id= ?";
            return jdbcTemplate.queryForObject(sql, rowMapper, id);
        } catch (Exception e) {
            logger.debug("Goods get by id failed ." + e);
            return null;
        }
        
    }

    @Override
    public Boolean save(Goods goods) {
        // 传入对象保存
        try {
            String sql = "INSERT INTO " + TABLE_NAME + " (" + INSERT_FIELDS 
                    + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            jdbcTemplate.update(sql, goods.getsId(),
                    goods.getName(),
                    goods.getLength(),
                    goods.getWidth(),
                    goods.getHeight(),
                    goods.getWeight(),
                    goods.getgIdSupplier(),
                    goods.getAmount(),
                    goods.getIsDisabled(),
                    goods.getInsertDt()
                    );
            return true;
        } catch (Exception e) {
            logger.debug("Goods save failed ." + e);
            return false;
        }
        
    }

    @Override
    public List<Goods> findAll() {
        // 查询左右对象限定一定条数
        try {
            String sql = "SELECT " + SELECT_FIELDS + " FROM " + TABLE_NAME + "LIMIT 10000";
            return jdbcTemplate.query(sql, rowMapper);
        } catch (Exception e) {
            logger.debug("Goods findAll failed ." + e);
            return null;
        }
        
    }
    
    private RowMapper<Goods> rowMapper = new RowMapper<Goods>() {

        @Override
        public Goods mapRow(ResultSet rs, int numRow) throws SQLException {
            Goods goods = new Goods();
            goods.setgId(rs.getLong("g_id"));
            goods.setsId(rs.getLong("s_id"));
            goods.setName(rs.getString("name"));
            goods.setLength(rs.getInt("length"));
            goods.setWidth(rs.getInt("width"));
            goods.setHeight(rs.getInt("height"));
            
            goods.setWeight(rs.getInt("weight"));
            goods.setgIdSupplier(rs.getString("g_id_supplier"));
            goods.setAmount(rs.getInt("amount"));
            try {
                goods.setIsDisabled(rs.getString("is_disabled").toCharArray()[0]);
            } catch (Exception e) {
                logger.info("Goods is_disable error ." + e);
            }
            
            goods.setInsertDt(rs.getTimestamp("insert_dt"));
            goods.setUpdateTime(rs.getTimestamp("update_time"));

            return goods;
        }
    };

}
