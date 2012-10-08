/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package RIMASS.Domain.user.dao;

import RIMASS.Domain.user.*;

/**
 *
 * @author hadriw
 */

public interface UserDao {
    public User getUserById(int id);
    public User getUserByEmail(String email);
    public User checkLogin(String email, String password);
    public boolean insertUser(User u);
    public boolean updatePassword(int id, String password);
    public boolean deleteUser(User u);
}
