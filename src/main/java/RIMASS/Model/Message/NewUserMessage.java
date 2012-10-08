/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package RIMASS.Model.Message;

/**
 *
 * @author hadriw
 */
public class NewUserMessage {
    private String email;
    private String confirmemail;
    private String password;
    private String confirmpassword;

    public NewUserMessage(){
        
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
     * @return the confirmEmail
     */
    public String getConfirmemail() {
        return confirmemail;
    }

    /**
     * @param confirmEmail the confirmEmail to set
     */
    public void setConfirmemail(String confirmemail) {
        this.confirmemail = confirmemail;
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
     * @return the confirmpassword
     */
    public String getConfirmpassword() {
        return confirmpassword;
    }

    /**
     * @param confirmpassword the confirmpassword to set
     */
    public void setConfirmpassword(String confirmpassword) {
        this.confirmpassword = confirmpassword;
    }
}
