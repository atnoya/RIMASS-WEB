/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package RIMASS.Domain.user;

import java.io.Serializable;
import java.util.Vector;

/**
 *
 * @author hadriw
 */

public class User implements Serializable{
    private int id;
    private String email;
    private String password;

    //User sin parametros: para borrar
    public User(){}
    public User(String email, String password){
        this.password = password;
        this.email= email;
    }

    public User(String email, String password, Vector<UserStoredQuery> queries){
        this.password = password;
        this.email= email;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }
}
