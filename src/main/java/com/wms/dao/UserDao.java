package com.wms.dao;

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

import com.wms.model.User;

/*
 * 定义用户操作类
 * 增删改
 */

@Repository
public class UserDao implements BaseDao<User, Long> {

    private Logger logger = LoggerFactory.getLogger(UserDao.class);
    
    private static final String TABLE_NAME = "user";
    private static final String INSERT_FIELDS = "username, password, truename, email, role, created";
    private static final String SELECT_FIELDS = "id, " + INSERT_FIELDS ;

    @Autowired
    @Qualifier("jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @Override
    public User get(Long id) {
        // TODO Auto-generated method stub
        try {
            String sql = "SELECT " + SELECT_FIELDS + " FROM " + TABLE_NAME + " WHERE id=? ";
            return jdbcTemplate.queryForObject(sql, rowMapper, id);
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("get User failed ." + e);
            return null;
        }
    }

    @Override
    public Boolean save(User user) {
        // TODO Auto-generated method stub
        String sql = "INSERT INTO " + TABLE_NAME + " (" + INSERT_FIELDS + ") VALUES(?, ?, ?, ?, ?, ?)"; 
        
        try {
            jdbcTemplate.update(sql, 
                    user.getUsername(),
                    user.getPassword(),
                    user.getTruename(),
                    user.getEmail(),
                    user.getRole(),
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(user.getCreated()));
            return true;
        } catch (Exception e) {
            // TODO: handle exception
            logger.debug("User save failed ." + e);
            return false;
        }
        
    }

    @Override
    public List<User> findAll() {
        // TODO Auto-generated method stub
        try {
            String sql = "SELECT " + SELECT_FIELDS + " FROM "+ TABLE_NAME +" LIMIT 10000";
            return jdbcTemplate.query(sql, rowMapper);
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("findAll User failed ." + e);
            return null;
        }
    }

    public User findByUsername(String username) {
        try {
            String sql = "SELECT " + SELECT_FIELDS + " FROM "+ TABLE_NAME 
                    +" WHERE username=? ";
            return jdbcTemplate.queryForObject(sql, rowMapper, username);
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("findByUsername failed ." + e);
            return null;
        }
    }
    
    public User findByUsernameAndPassword(String username, String password) {
        try {
            String sql = "SELECT " + SELECT_FIELDS + " FROM "+ TABLE_NAME 
                    +" WHERE username=? AND password=? ";
            return jdbcTemplate.queryForObject(sql, rowMapper, username, password);
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("findByUsernameAndPassword failed ." + e);
            return null;
        }
    }
    
    public List<User> findByRole(Integer role){
        try {
            String sql = "SELECT " + SELECT_FIELDS + " FROM "+ TABLE_NAME 
                    +" WHERE role=? ";
            return jdbcTemplate.query(sql, rowMapper, role);
        } catch (Exception e) {
            // TODO: handle exception
            logger.debug("findByRole failed ." + e);
            return null;
        }
    }

    private RowMapper<User> rowMapper = new RowMapper<User>() {

        @Override
        public User mapRow(ResultSet rs, int numRow) throws SQLException {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setUsername(rs.getString("username"));
            user.setTruename(rs.getString("truename"));
            user.setPassword(rs.getString("password"));
            user.setEmail(rs.getString("email"));
            user.setRole(rs.getInt("role"));
            user.setCreated(rs.getTimestamp("created"));

            return user;
        }
    };

}
