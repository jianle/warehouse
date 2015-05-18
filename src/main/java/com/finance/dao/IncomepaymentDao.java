package com.finance.dao;

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

import com.finance.model.Incomepayment;

@Repository
public class IncomepaymentDao implements BaseDao<Incomepayment, Integer> {
	
	private Logger logger = LoggerFactory.getLogger(IncomepaymentDao.class);
    
    private static final String TABLE_NAME    = "incomepayment";
    private static final String INSERT_FIELDS = "parent_id, type";
    private static final String SELECT_FIELDS = "id, " + INSERT_FIELDS;
    
    @Autowired
    @Qualifier("jdbcTemplateFinance")
    private JdbcTemplate jdbcTemplateFinance;

	@Override
	public Incomepayment get(Integer id) {
		// TODO Auto-generated method stub
		try {
			String sql = "select " + SELECT_FIELDS + " from " + TABLE_NAME + " where id=?";
			return jdbcTemplateFinance.queryForObject(sql, rowMapper, id);
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug("get failed." + e);
		}
		return null;
	}

	@Override
	public Boolean save(Incomepayment object) {
		// TODO Auto-generated method stub
		try {
			String sql = "insert into " + TABLE_NAME + " ( " + INSERT_FIELDS 
					+ " ) values (?, ?) ";
			jdbcTemplateFinance.update(sql, 
					object.getParentId(),
					object.getType());
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug("save failed. " + e);
		}
		return false;
	}

	@Override
	public List<Incomepayment> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<Incomepayment> findByParentId(Integer parentId) {
		// TODO Auto-generated method stub
		try {
			String sql = "select " + SELECT_FIELDS + " from " + TABLE_NAME + " where parent_id=?";
			return jdbcTemplateFinance.query(sql, rowMapper, parentId);
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug("findByParentId failed. " + e);
		}
		return null;
	}

	@Override
	public Boolean update(Incomepayment object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean delete(Integer id) {
		// TODO Auto-generated method stub
		try {
			String sql = "delete from " + TABLE_NAME + " where id=?";
			jdbcTemplateFinance.update(sql, id);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug("delete failed. " + e);
		}
		return false;
	}
	
	private RowMapper<Incomepayment> rowMapper = new RowMapper<Incomepayment>() {

		@Override
		public Incomepayment mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			// TODO Auto-generated method stub
			Incomepayment incomepayment = new Incomepayment();
			incomepayment.setId(rs.getInt("id"));
			incomepayment.setParentId(rs.getInt("parent_id"));
			incomepayment.setType(rs.getString("type"));
			return incomepayment;
		}
	};

}
