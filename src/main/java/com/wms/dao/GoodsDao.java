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
import com.wms.model.Pagination;

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
    
    public Boolean update(Goods goods) {
        // 通过商品Id修改商品信息
        try {
            String sql = "UPDATE " + TABLE_NAME + " SET "
                    + " s_id=?, name=?, length=?, width=?, height=?, weight=?, g_id_supplier=?,"
                    + " amount=?, is_disabled=? "
                    + " WHERE g_id=? ";
            jdbcTemplate.update(sql, goods.getsId(),
                    goods.getName(),
                    goods.getLength(),
                    goods.getWidth(),
                    goods.getHeight(),
                    goods.getWeight(),
                    goods.getgIdSupplier(),
                    goods.getAmount(),
                    goods.getIsDisabled(),
                    goods.getgId()
                    );
            return true;
        } catch (Exception e) {
            logger.debug("update goods failed ." + e);
        }
        return false;
    }
    
    public Boolean delete(Long gId) {
        // 通过产品Id删除
        try {
            String sql = "DELETE FROM " + TABLE_NAME + " WHERE g_id=? ";
            jdbcTemplate.update(sql, gId);
            return true;
        } catch (Exception e) {
            logger.debug("delete goods failed ." + e);
        }
        return false;
        
    }
    
    @SuppressWarnings("deprecation")
    public Pagination<Goods> findByNameAndIsDisabled(String name, String is_disabled, int currentPage) {
        // 通过商品名称、是否有效搜索并分页
        List<Goods> goods;
        
        StringBuffer sqlBuf = new StringBuffer("SELECT " + SELECT_FIELDS + " FROM " + TABLE_NAME);
        String isWhere = "";
        
        if (name != null && !"".equals(name) ) {
            isWhere = " WHERE name like '%" + name + "%' ";
        }
        
        if (is_disabled != null && ! "A".equals(is_disabled)) {
            if (isWhere.isEmpty()) {
                isWhere = " WHERE is_disabled='" + is_disabled + "' ";
            }else {
                isWhere = isWhere + " AND is_disabled='" + is_disabled + "' ";
            }
        }
        
        sqlBuf.append(isWhere);
        
        try {
            //定义并执行SQL
            String sqlTotal = sqlBuf.toString().replace(SELECT_FIELDS, "COUNT(1)");
            int totalRows = jdbcTemplate.queryForInt(sqlTotal);
            
            Pagination<Goods> goodsPagination = new Pagination<Goods>(totalRows, currentPage);
            String sql = goodsPagination.getMySQLPageSQL(sqlBuf.toString(),currentPage);
            
            logger.info(sql);
            
            goods = jdbcTemplate.query(sql, rowMapper);
            
            goodsPagination.setResultList(goods);
            
            return goodsPagination;
        } catch (Exception e) {
            // TODO: handle exception
            logger.debug("Goods findByNameAndIsDisabled ." + e);
            return null;
        }
    }

    @Override
    public List<Goods> findAll() {
        // 查询左右对象限定一定条数
        try {
            String sql = "SELECT " + SELECT_FIELDS + " FROM " + TABLE_NAME ;
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
            goods.setIsDisabled(rs.getString("is_disabled"));
            
            goods.setInsertDt(rs.getTimestamp("insert_dt"));
            goods.setUpdateTime(rs.getTimestamp("update_time"));

            return goods;
        }
    };

}
