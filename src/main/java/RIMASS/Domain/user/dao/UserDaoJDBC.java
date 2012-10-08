/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package RIMASS.Domain.user.dao;

import RIMASS.Domain.user.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

/**
 *
 * @author hadriw
 */
@Repository
public class UserDaoJDBC extends JdbcDaoSupport implements UserDao{

    @Autowired
    private DataSource datasource;

    public UserDaoJDBC(){
    }

    @PostConstruct
    public void initialize(){
        this.setDataSource(datasource);
        this.setJdbcTemplate(new JdbcTemplate(datasource));
    }
    
    public User getUserById(int id) {
        return this.getJdbcTemplate().query("SELECT * FROM users WHERE id = ?", new Object[]{id}, new UserExtractor());
    }

    public User getUserByEmail(String email) {
        return this.getJdbcTemplate().query("SELECT * FROM users WHERE email = ?", new Object[]{email}, new UserExtractor());
    }

    public User checkLogin(String email, String password) {
        User u = this.getJdbcTemplate().query("SELECT * FROM users u WHERE u.email = ? AND u.password = ?", new Object[]{email, password}, new UserExtractor());
        if (u == null) return null;
        else{
            //Get the List of Stored Queries
            return u;
        }
    }

    public boolean insertUser(User u) {
        try{
            int res = this.getJdbcTemplate().update("INSERT INTO users (email, password) VALUES(?,?)",
                            new Object[] { u.getEmail(), u.getPassword() });
            if (res == 1) return true;
            else return false;
        }catch(DataAccessException e){
            return false;
        }
    }

    public boolean updatePassword(int id, String password) {
        try{
            int res = this.getJdbcTemplate().update("UPDATE users SET password = ? WHERE id = ?",
                            new Object[] { password, id });
            if (res == 1) return true;
            else return false;
        }catch(DataAccessException e){
            return false;
        }
    }

    public boolean deleteUser(User u) {
        try{
            int res = this.getJdbcTemplate().update("DELETE FROM users WHERE id = ?",
                            new Object[] { u.getId() });
            if (res == 1) return true;
            else return false;
        }catch(DataAccessException e){
            return false;
        }
    }

    private class UserExtractor implements ResultSetExtractor<User>{

        public User extractData(ResultSet rs) throws SQLException, DataAccessException {
            rs.first();
            try{
                User newuser = new User();
                newuser.setId(rs.getInt(1));
                newuser.setEmail(rs.getString(2));
                newuser.setPassword(rs.getString(3));
                return newuser;
            }catch(Exception e){
                return null;
            }
        }

    }

}
