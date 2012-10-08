/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package RIMASS.Model.query.management;

import RIMASS.Domain.query.base.GraphicQuery;
import RIMASS.Domain.user.User;
import RIMASS.Domain.user.dao.UserDao;
import RIMASS.Domain.user.UserStoredQuery;
import RIMASS.Domain.user.dao.UserStoredQueryDao;
import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author hadriw
 */

@Service
public abstract class UserManager implements Serializable {
    protected User user;
    
    @Autowired
    protected UserDao userDao;

    @Autowired
    protected UserStoredQueryDao userStoredQueryDao;

    public abstract boolean createNewUser(User user);

    public abstract boolean changePassword(String newpass);

    public abstract User getUserData();

    public abstract boolean logInUser(String username, String password);

    public abstract void logout();

    public abstract List<UserStoredQuery> getStoredQueries();

    public abstract boolean storeQuery(String name, String description, long results, GraphicQuery graphicQuery);

    public abstract UserStoredQuery loadQuery(long id);

    public abstract boolean checkQueryNameExists(String name);

    public abstract boolean overwriteQuery(String name, String description, long results, GraphicQuery graphicQuery);
    /**
     * @param userDatabase the userDatabase to set
     */
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * @param queriesDatabase the queriesDatabase to set
     */
    public void setQueriesDatabase(UserStoredQueryDao queriesDatabase) {
        this.userStoredQueryDao = queriesDatabase;
    }
    //DEFAULT SETTINGS????
}