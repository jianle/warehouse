package com.wms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
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

import com.util.UtilsDes;
import com.wms.model.User;

/*
 * 定义用户操作类
 * 增删改
 */

@Repository
public class UserDao implements BaseDao<User, Long> {

    private Logger logger = LoggerFactory.getLogger(UserDao.class);
    
    private static final String TABLE_NAME = "user";
    private static final String INSERT_FIELDS = "parent_id, username, password, truename, email, role, created";
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
    
    public Map<Long, String> findAllMapIdAndName(Long id) {
        try {
            String sql = "select id, truename from " + TABLE_NAME ;
            if (id != -1) {
                sql = sql + " where id<>" + id;
            }
            return jdbcTemplate.query(sql, resultSetExtractor);
        } catch (Exception e) {
            // TODO: handle exception
            logger.debug("findAllMapIdAndName failed. " + e);
        }
        return null;
    }
    
    public Map<Long, String> findDeniedMapIdAndName(User user) {
        try {
            String sql = "";
            if (user.getRole() == User.ROLE_ADMIN || user.getRole() == User.ROLE_BOSS) {
                sql = "select id, truename from " + TABLE_NAME ;
            }
            if (user.getRole() == User.ROLE_DIRECTOR) {
                sql = "select id, truename from " + TABLE_NAME + " where parent_id=" + user.getId() 
                        + " or id=" + user.getId();
            }
            
            if (sql.equals("")) {
                sql = "select id, truename from " + TABLE_NAME + " where id=" + user.getId();
            }
            
            return jdbcTemplate.query(sql, resultSetExtractor);
        } catch (Exception e) {
            // TODO: handle exception
            logger.debug("findDeniedMapIdAndName failed. " + e);
        }
        return null;
    }

    @Override
    public Boolean save(User user) {
        // TODO Auto-generated method stub
        String sql = "INSERT INTO " + TABLE_NAME + " (" + INSERT_FIELDS + ") VALUES(?, ?, ?, ?, ?, ?, ?)"; 
        
        try {
            jdbcTemplate.update(sql, 
                    user.getParentId(),
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
            String sql = "SELECT " + SELECT_FIELDS + " FROM "+ TABLE_NAME;
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
            user.setParentId(rs.getLong("parent_id"));
            user.setUsername(rs.getString("username"));
            user.setTruename(rs.getString("truename"));
            try {
                user.setPassword(UtilsDes.decrypt(rs.getString("password")));
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            user.setEmail(rs.getString("email"));
            user.setRole(rs.getInt("role"));
            user.setCreated(rs.getTimestamp("created"));

            return user;
        }
    };

    public List<User> findByTruename(String truemane) {
        // TODO Auto-generated method stub
        try {
            String sql = "SELECT " + SELECT_FIELDS + " FROM "+ TABLE_NAME 
                    +" WHERE truename like ? or username like ? ";
            return jdbcTemplate.query(sql, rowMapper, "%" + truemane + "%", "%" + truemane + "%");
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("findByTruename failed ." + e);
            return null;
        }
    }

    public Boolean update(User user) {
     // TODO Auto-generated method stub
        String sql = "update " + TABLE_NAME + " set parent_id=?, username=?, password=?, truename=?, email=?, role=? "
                + "where id=?"; 
        
        try {
            jdbcTemplate.update(sql, 
                    user.getParentId(),
                    user.getUsername(),
                    user.getPassword(),
                    user.getTruename(),
                    user.getEmail(),
                    user.getRole(),
                    user.getId());
            return true;
        } catch (Exception e) {
            // TODO: handle exception
            logger.debug("User save failed ." + e);
            return false;
        }
    }

    public boolean delete(Long id) {
        // TODO Auto-generated method stub
        try {
            String sql = "DELETE FROM " + TABLE_NAME + " WHERE id=? ";
            return jdbcTemplate.update(sql, id) > 0;
        } catch (Exception e) {
            // TODO: handle exception
            logger.info("delete user by id-" + id + " failed." + e);
        }
        return false;
    }

    public List<User> findByParentId(Long parentId) {
        
        try {
            String sql = "SELECT " + SELECT_FIELDS + " FROM "+ TABLE_NAME + " where parent_id=?";
            return jdbcTemplate.query(sql, rowMapper, parentId);
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("findAll User failed ." + e);
            return null;
        }
    }
    
    private ResultSetExtractor<Map<Long, String>> resultSetExtractor = new ResultSetExtractor<Map<Long, String>>() {
        public Map<Long, String> extractData(ResultSet rs) throws SQLException,
                DataAccessException {
            Map<Long, String> map = new HashMap<Long, String>();
            while (rs.next()) {
                Long id = rs.getLong("id");
                String truename = rs.getString("truename");
                map.put(id, truename);
            }
            return map;
        }
        
    };

}
