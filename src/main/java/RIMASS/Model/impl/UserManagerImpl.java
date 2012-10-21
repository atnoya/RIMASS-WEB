/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package RIMASS.Model.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Component;

import RIMASS.Domain.query.base.GraphicQuery;
import RIMASS.Domain.user.User;
import RIMASS.Domain.user.UserStoredQuery;
import RIMASS.Model.query.management.UserManager;

/**
 *
 * @author hadriw
 */
@Component
public class UserManagerImpl extends UserManager implements Serializable {
    public UserManagerImpl(){
    }
    
    public boolean createNewUser(User user){
        if (this.userDao.insertUser(user)){
            this.user = user;
            return true;
        }else return false;
    }

    public boolean changePassword(String newpass){
        if (userDao.updatePassword(user.getId(), newpass)) return false;
        this.user.setPassword(newpass);
        return true;
    }

    public User getUserData(){
        return user;
    }

    public void logout(){
        user =null;
    }

    public List<UserStoredQuery> getStoredQueries(){
        if (user == null) return null;
        return this.userStoredQueryDao.getAll(user.getId());
    }

    public boolean logInUser(String username, String password) {
        this.user = this.userDao.checkLogin(username, password);
        if (user == null) return false;
        else return true;
    }

    public boolean storeQuery(String name, String description, long results, GraphicQuery graphicQuery ) {
        if (user == null) return false;
        UserStoredQuery usq = new UserStoredQuery();
        usq.setUserid(user.getId());
        usq.setName(name);
        usq.setDescription(description);
        usq.setDate(new java.util.Date());
        usq.setNumberOfResults(results);
        usq.setQuery(graphicQuery);
        if (this.userStoredQueryDao.insert(usq)) return true;
        else return false;
    }

    public UserStoredQuery loadQuery(long id) {
        return this.userStoredQueryDao.get(id, this.user.getId());
    }

    @Override
    public boolean checkQueryNameExists(String name) {
        return this.userStoredQueryDao.checkName(name, this.user.getId());
    }

    @Override
    public boolean overwriteQuery(String name, String description, long results, GraphicQuery graphicQuery) {
        if (user == null) return false;
        UserStoredQuery usq = new UserStoredQuery();
        usq.setUserid(user.getId());
        usq.setName(name);
        usq.setDescription(description);
        usq.setDate(new java.util.Date());
        usq.setNumberOfResults(results);
        usq.setQuery(graphicQuery);
        if (this.userStoredQueryDao.overwriteByName(usq)) return true;
        else return false;
    }
}
