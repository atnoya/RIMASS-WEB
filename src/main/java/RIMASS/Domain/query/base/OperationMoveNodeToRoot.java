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
public class OperationMoveNodeToRoot extends Operation implements Serializable{
    private final static int idOp  = 21;
    private final static String nameOp = "MoveNodeToRoot";
    private QueryNode origen;
    private QueryNodeModifier node;
    private GraphicQuery query;

    public OperationMoveNodeToRoot(QueryNode origen, QueryNodeModifier node, GraphicQuery query){
        this.node = node;
        this.origen = origen;
        this.query = query;
    }

    @Override
    public void undo() {
        this.query.deleteRoot(node.getModifier());
        this.origen.addModifier(node);
    }

    @Override
    public void redo() {
        this.origen.removeModifier(node);
        this.query.addRoot(node.getModifier());
    }

    @Override
    public void doOperation() {
        this.origen.removeModifier(node);
        this.query.addRoot(node.getModifier());
    }
}
