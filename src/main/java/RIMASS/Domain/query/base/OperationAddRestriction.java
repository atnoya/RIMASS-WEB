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
public class OperationAddRestriction extends Operation implements Serializable{
    private final static int idOp  = 0;
    private final static String nameOp = "AddRestriction";
    private QueryNodeModifier target;
    private QueryNode father;

    public OperationAddRestriction(QueryNode father, QueryNodeModifier res){
        this.father = father;
        this.target = res;
    }
    
    @Override
    public void undo() {
        this.father.removeModifier(target);
    }

    @Override
    public void redo() {
        this.father.addModifier(target);
    }

    @Override
    public void doOperation() {
        this.father.addModifier(target);
    }

}
