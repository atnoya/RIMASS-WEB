/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package RIMASS.Domain.user.dao;

import RIMASS.Domain.query.base.GraphicQuery;
import RIMASS.Domain.user.UserStoredQuery;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

/**
 *
 * @author hadriw
 */
@Repository
public class UserStoredQueryDaoJDBC extends JdbcDaoSupport implements UserStoredQueryDao{
    @Autowired
    private DataSource datasource;

    @PostConstruct
    public void initialize() {
        this.setDataSource(datasource);
    }

    public boolean insert(UserStoredQuery query) {
        try{
            int res = this.getJdbcTemplate().update("INSERT INTO search (userid, name, description, numberResults, date, contents) VALUES(?,?,?,?,?,?)",
                            new Object[] { query.getUserid(), query.getName(), query.getDescription(), query.getNumberOfResults(), query.getDate(), query.getQuery() });
            if (res == 1) return true;
            else return false;
        }catch(Exception e){
            return false;
        }
    }

    public boolean update(UserStoredQuery query) {
        try{
            int res = this.getJdbcTemplate().update("UPDATE search SET name = ?, description = ?, numberResults = ?, date = ?, contents = ? WHERE id = ? AND userid = ?",
                            new Object[] { query.getName(), query.getDescription(), query.getNumberOfResults(), query.getDate(), query.getQuery(), query.getId(), query.getUserid() });
            if (res == 1) return true;
            else return false;
        }catch(Exception e){
            return false;
        }
    }

    public boolean delete(UserStoredQuery query) {
        try{
            int res = this.getJdbcTemplate().update("DELETE FROM search WHERE id = ? AND userid = ?",
                            new Object[] { query.getId(), query.getUserid() });
            if (res == 1) return true;
            else return false;
        }catch(Exception e){
            return false;
        }
    }

    public UserStoredQuery get(long id, int userid) {
        return this.getJdbcTemplate().query("SELECT * FROM search WHERE id = ? AND userid = ?", new Object[]{id, userid}, new UserStoredQueryExtractor());
    }

    public List<UserStoredQuery> getAll(int userid) {
        return this.getJdbcTemplate().query("SELECT id, userid, name, description, numberResults, date FROM search WHERE userid = ?", new Object[]{userid}, new UserStoredQueryListExtractor());
    }

    public boolean overwriteByName(UserStoredQuery query) {
        try{
            int res = this.getJdbcTemplate().update("UPDATE search SET name = ?, description = ?, numberResults = ?, date = ?, contents = ? WHERE name = ? AND userid = ?",
                            new Object[] { query.getName(), query.getDescription(), query.getNumberOfResults(), query.getDate(), query.getQuery(), query.getName(), query.getUserid() });
            if (res == 1) return true;
            else return false;
        }catch(Exception e){
            return false;
        }
    }

    public boolean checkName(String name, int userid) {
        try{
            List<UserStoredQuery> lu = this.getJdbcTemplate().query("SELECT id, userid, name, description, numberResults, date FROM search WHERE name= ? AND userid = ?",
                            new Object[] { name, userid}, new UserStoredQueryListExtractor());
            if (lu.size() >= 1) return true;
            else return false;
        }catch(Exception e){
            return false;
        }
    }

    private class UserStoredQueryExtractor implements ResultSetExtractor<UserStoredQuery>{

        public UserStoredQuery extractData(ResultSet rs) throws SQLException, DataAccessException {
            rs.first();
            try{
                UserStoredQuery u = new UserStoredQuery();
                u.setId(rs.getInt(1));
                u.setUserid(rs.getInt(2));
                u.setName(rs.getString(3));
                u.setNumberOfResults(rs.getLong(5));
                u.setDescription(rs.getString(4));
                u.setDate(rs.getDate(6));
                ObjectInputStream ois = null;
                try {
                    ois = new ObjectInputStream(rs.getBlob(7).getBinaryStream());
                    u.setQuery((GraphicQuery)ois.readObject());
                } catch (IOException ex) {
                    Logger.getLogger(UserStoredQueryDaoJDBC.class.getName()).log(Level.SEVERE, null, ex);
                    return null;
                } catch (ClassNotFoundException e){
                    Logger.getLogger(UserStoredQueryDaoJDBC.class.getName()).log(Level.SEVERE, null, e);
                    return null;
                }finally{
                    if (ois != null) ois.close();
                }
                return u;
            }catch (Exception e){
                Logger.getLogger(UserStoredQueryDaoJDBC.class.getName()).log(Level.SEVERE, null, e);
                return null;
            }
        }
    }

     private class UserStoredQueryListExtractor implements ResultSetExtractor<List<UserStoredQuery>>{

        public List<UserStoredQuery> extractData(ResultSet rs) throws SQLException, DataAccessException {
            List<UserStoredQuery> list =  new Vector<UserStoredQuery>();
            rs.first();
            do{
                try{
                    UserStoredQuery u = new UserStoredQuery();
                    u.setId(rs.getInt(1));
                    u.setUserid(rs.getInt(2));
                    u.setName(rs.getString(3));
                    u.setNumberOfResults(rs.getLong(5));
                    u.setDescription(rs.getString(4));
                    u.setDate(rs.getDate(6));
                    list.add(u);
                }catch (Exception e){
                    return null;
                }
            }while(rs.next());
            return list;
        }
    }
}
