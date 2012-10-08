/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package RIMASS.Domain.query.base;

import java.io.Serializable;

/**
 *
 * @author hadriw
 */
public abstract class Operation implements Serializable{
    private static int idOp = -1;
    private static String nameOp = "";

    abstract public void undo();
    abstract public void redo();
    abstract public void doOperation();
    /**
     * @return the nameOp
     */
    public String getNameOp() {
        return nameOp;
    }

    /**
     * @return the idOp
     */
    public int getIdOp() {
        return idOp;
    }
}
