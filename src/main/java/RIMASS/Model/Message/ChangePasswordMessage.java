/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package RIMASS.Model.Message;

/**
 *
 * @author hadriw
 */
public class ChangePasswordMessage {
    private String realpassword;
    private String oldpassword;
    private String newpassword;
    private String confirmnewpassword;

    public ChangePasswordMessage(){}
    /**
     * @return the oldpassword
     */
    public String getOldpassword() {
        return oldpassword;
    }

    /**
     * @param oldpassword the oldpassword to set
     */
    public void setOldpassword(String oldpassword) {
        this.oldpassword = oldpassword;
    }

    /**
     * @return the newpassword
     */
    public String getNewpassword() {
        return newpassword;
    }

    /**
     * @param newpassword the newpassword to set
     */
    public void setNewpassword(String newpassword) {
        this.newpassword = newpassword;
    }

    /**
     * @return the confirmnewpassword
     */
    public String getConfirmnewpassword() {
        return confirmnewpassword;
    }

    /**
     * @param confirmnewpassword the confirmnewpassword to set
     */
    public void setConfirmnewpassword(String confirmnewpassword) {
        this.confirmnewpassword = confirmnewpassword;
    }

    /**
     * @return the realpassword
     */
    public String getRealpassword() {
        return realpassword;
    }

    /**
     * @param realpassword the realpassword to set
     */
    public void setRealpassword(String realpassword) {
        this.realpassword = realpassword;
    }
}
