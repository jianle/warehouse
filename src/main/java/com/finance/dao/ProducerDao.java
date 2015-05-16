package com.finance.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.finance.model.Producer;


@Repository
public class ProducerDao implements BaseDao<Producer, Long> {
	
	private Logger logger = LoggerFactory.getLogger(ProducerDao.class);
	private static final SimpleDateFormat DATET_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private static final String TABLE_NAME    = "producer";
    private static final String INSERT_FIELDS = "pro_name, remark, update_time";
    private static final String SELECT_FIELDS = "pro_id, " + INSERT_FIELDS;
    
    @Autowired
    @Qualifier("jdbcTemplateFinance")
    private JdbcTemplate jdbcTemplateFinance;

	@Override
	public Producer get(Long id) {
		// TODO Auto-generated method stub
		try{
			String sql = "select " + SELECT_FIELDS + " from " + TABLE_NAME + " where pro_id=? ";
			return jdbcTemplateFinance.queryForObject(sql, rowMapper, id);
		} catch(Exception e) {
			logger.debug("get failed. " + e);
		}
		return null;
	}

	@Override
	public Boolean save(Producer object) {
		// TODO Auto-generated method stub
		try {
			String sql = "insert into " + TABLE_NAME + " ( " + INSERT_FIELDS 
					+ " ) values (?, ?, ?)" ;
			jdbcTemplateFinance.update(sql, 
					object.getProName(),
					object.getRemark(),
					new Timestamp(System.currentTimeMillis())
					);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug("save failed. " + e);
		}
		return false;
	}



	@Override
	public List<Producer> findAll() {
		// TODO Auto-generated method stub
		try {
			String sql = "select " + SELECT_FIELDS + " from " + TABLE_NAME ;
			return jdbcTemplateFinance.query(sql, rowMapper);
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug("findAll failed. " + e);
		}
		return null;
	}

	public List<Producer> findByName(String proName) {
		try {
			String sql = "select " + SELECT_FIELDS + " from " + TABLE_NAME + " where pro_name like ?" ;
			return jdbcTemplateFinance.query(sql, rowMapper, "%" + proName + "%");
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug("findByName failed. " + e);
		}
		return null;
	}
	
	public List<Map<String, Object>> findAllIdAndName() {
		try {
			String sql = "select pro_id proId, pro_name proName from " + TABLE_NAME ;
			return jdbcTemplateFinance.queryForList(sql);
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug("findAllIdAndName failed. " + e);
		}
		return null;
	}
	
	public Map<Long, String> findAllMapIdAndName(String proName) {
		try {
			String sql = "select pro_id, pro_name from " + TABLE_NAME ;
			if (proName != null && !"".equals(proName)) {
				sql = sql + " where pro_name like '%" + proName + "%'";
			}
			return jdbcTemplateFinance.query(sql, new ResultSetExtractor<Map<Long, String>>(){
				@Override
				public Map<Long, String> extractData(ResultSet rs) throws SQLException,
						DataAccessException {
					Map<Long, String> map = new HashMap<Long, String>();
					while (rs.next()) {
						Long conId = rs.getLong("pro_id");
						String conName = rs.getString("pro_name");
						map.put(conId, conName);
					}
					return map;
				}
				
			});
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug("findAllMapIdAndName failed. " + e);
		}
		return null;
	}


	@Override
	public Boolean update(Producer object) {
		// TODO Auto-generated method stub
		try {
			String sql = "update " + TABLE_NAME + " set pro_name=?, remark=? where pro_id=?" ;
			jdbcTemplateFinance.update(sql, 
					object.getProName(),
					object.getRemark(),
					object.getProId()
					);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug("save failed. " + e);
		}
		return false;
	}



	@Override
	public Boolean delete(Long id) {
		// TODO Auto-generated method stub
		try {
			String sql = "delete from " + TABLE_NAME + " where pro_id=?";
			jdbcTemplateFinance.update(sql, id);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug("delete failed. " + e);
		}
		return false;
	}

	
	 private RowMapper<Producer> rowMapper = new RowMapper<Producer>() {

		@Override
		public Producer mapRow(ResultSet rs, int rowNum) throws SQLException {
			// TODO Auto-generated method stub
			Producer producer = new Producer();
			
			producer.setProId(rs.getLong("pro_id"));
			producer.setProName(rs.getString("pro_name"));
			producer.setRemark(rs.getString("remark"));
			producer.setUpdateTime(DATET_TIME_FORMAT.format(rs.getTimestamp("update_time")));
			
			return producer;
		}
		 
	 };


}
