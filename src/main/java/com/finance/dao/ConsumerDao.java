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

import com.finance.model.Consumer;

@Repository
public class ConsumerDao implements BaseDao<Consumer, Long> {
	
	private Logger logger = LoggerFactory.getLogger(ConsumerDao.class);
	private static final SimpleDateFormat DATET_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private static final String TABLE_NAME    = "consumer";
	private static final String INSERT_FIELDS = "con_name, remark, update_time";
    private static final String SELECT_FIELDS = "con_id, " + INSERT_FIELDS;
    
    @Autowired
    @Qualifier("jdbcTemplateFinance")
    private JdbcTemplate jdbcTemplateFinance;
    
    @Override
	public Consumer get(Long id) {
		// TODO Auto-generated method stub
		try{
			String sql = "select " + SELECT_FIELDS + " from " + TABLE_NAME + " where con_id=? ";
			return jdbcTemplateFinance.queryForObject(sql, rowMapper, id);
		} catch(Exception e) {
			logger.debug("get failed. " + e);
		}
		return null;
	}

	@Override
	public Boolean save(Consumer object) {
		// TODO Auto-generated method stub
		try {
			String sql = "insert into " + TABLE_NAME + " ( " + INSERT_FIELDS 
					+ " ) values (?, ?, ?)" ;
			jdbcTemplateFinance.update(sql, 
					object.getConName(),
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
	public List<Consumer> findAll() {
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

	public List<Consumer> findByName(String conName) {
		try {
			String sql = "select " + SELECT_FIELDS + " from " + TABLE_NAME + " where con_name like ?" ;
			return jdbcTemplateFinance.query(sql, rowMapper, "%" + conName + "%");
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug("findByName failed. " + e);
		}
		return null;
	}
	
	public List<Map<String, Object>> findAllIdAndName() {
		try {
			String sql = "select con_id conId, con_name conName from " + TABLE_NAME ;
			return jdbcTemplateFinance.queryForList(sql);
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug("findAllIdAndName failed. " + e);
		}
		return null;
	}
	
	public Map<Long, String> findAllMapIdAndName(String conName) {
		try {
			String sql = "select con_id, con_name from " + TABLE_NAME ;
			if (conName != null && !"".equals(conName)) {
				sql = sql + " where con_name like '%" + conName + "%'";
			}
			return jdbcTemplateFinance.query(sql, new ResultSetExtractor<Map<Long, String>>(){
				@Override
				public Map<Long, String> extractData(ResultSet rs) throws SQLException,
						DataAccessException {
					Map<Long, String> map = new HashMap<Long, String>();
					while (rs.next()) {
						Long conId = rs.getLong("con_id");
						String conName = rs.getString("con_name");
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
	public Boolean update(Consumer object) {
		// TODO Auto-generated method stub
		try {
			String sql = "update " + TABLE_NAME + " set con_name=?, remark=? where con_id=?" ;
			jdbcTemplateFinance.update(sql, 
					object.getConName(),
					object.getRemark(),
					object.getConId()
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
			String sql = "delete from " + TABLE_NAME + " where con_id=?";
			jdbcTemplateFinance.update(sql, id);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug("delete failed. " + e);
		}
		return false;
	}

	
	 private RowMapper<Consumer> rowMapper = new RowMapper<Consumer>() {

		@Override
		public Consumer mapRow(ResultSet rs, int rowNum) throws SQLException {
			// TODO Auto-generated method stub
			Consumer Consumer = new Consumer();
			
			Consumer.setConId(rs.getLong("con_id"));
			Consumer.setConName(rs.getString("con_name"));
			Consumer.setRemark(rs.getString("remark"));
			Consumer.setUpdateTime(DATET_TIME_FORMAT.format(rs.getTimestamp("update_time")));
			
			return Consumer;
		}
		 
	 };

}
