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
public class OperationMoveNode extends Operation implements Serializable{
    private final static int idOp  = 20;
    private final static String nameOp = "MoveNode";
    private QueryNode origen;
    private QueryNodeModifier node;
    private QueryNode destiny;

    public OperationMoveNode(QueryNode origen, QueryNodeModifier node, QueryNode destiny){
        this.node = node;
        this.origen = origen;
        this.destiny = destiny;
    }

    @Override
    public void undo() {
        this.destiny.removeModifier(node);
        this.origen.addModifier(node);
    }

    @Override
    public void redo() {
        this.origen.removeModifier(node);
        this.destiny.addModifier(node);
    }

    @Override
    public void doOperation() {
        this.origen.removeModifier(node);
        this.destiny.addModifier(node);
    }
}
