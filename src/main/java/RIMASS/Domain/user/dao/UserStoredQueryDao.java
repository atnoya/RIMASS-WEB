/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package RIMASS.Domain.user.dao;


import RIMASS.Domain.user.UserStoredQuery;
import java.util.List;

/**
 *
 * @author hadriw
 */
public interface UserStoredQueryDao {
    public boolean insert(UserStoredQuery query);
    public boolean update(UserStoredQuery query);
    public boolean delete(UserStoredQuery query);
    public boolean overwriteByName(UserStoredQuery query);
    public boolean checkName(String name, int userid);
    public UserStoredQuery get(long id, int userid);
    public List<UserStoredQuery> getAll(int userid);
}
