/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package RIMASS.Domain.query.base;

import RIMASS.Domain.document.Term;
import java.io.Serializable;

/**
 *
 * @author hadriw
 */
public class OperationSpecialize extends Operation implements Serializable{
    private final static int idOp  = 2;
    private final static String nameOp = "Specialize";
    private QueryNode node;
    private Term oldTerm;
    private Term newTerm;

    public OperationSpecialize(Term oldTerm, Term newTerm, QueryNode node){
        this.node = node;
        this.oldTerm = oldTerm;
        this.newTerm = newTerm;
    }

    @Override
    public void undo() {
        this.node.setTerm(oldTerm);
    }

    @Override
    public void redo() {
        this.node.setTerm(newTerm);
    }

    @Override
    public void doOperation() {
        this.node.setTerm(newTerm);
    }
}
