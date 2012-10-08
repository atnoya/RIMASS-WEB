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
public class OperationRemoveNode extends Operation implements Serializable{
    private final static int idOp  = 11;
    private final static String nameOp = "RemoveNode";
    private QueryNode father;
    private QueryNodeModifier deleted;

    public OperationRemoveNode(QueryNodeModifier deleted, QueryNode father){
        this.father = father;
        this.deleted = deleted;
    }

    @Override
    public void undo() {
        this.father.addModifier(deleted);
    }

    @Override
    public void redo() {
        this.father.removeModifier(deleted);
    }

    @Override
    public void doOperation() {
        this.father.removeModifier(deleted);
    }
}
