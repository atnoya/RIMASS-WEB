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
public class OperationMoveRootToNode extends Operation  implements Serializable{
    private final static int idOp  = 21;
    private final static String nameOp = "MoveNodeToRoot";
    private QueryNode origen;
    private QueryNodeModifier node;
    private GraphicQuery query;
    private QueryNode to;

    public OperationMoveRootToNode(QueryNode origen, QueryNodeModifier node, QueryNode to, GraphicQuery query){
        this.node = node;
        this.origen = origen;
        this.query = query;
        this.to= to;
    }

    @Override
    public void undo() {
        this.query.addRoot(node.getModifier());
        this.to.removeModifier(node);
    }

    @Override
    public void redo() {
        this.query.deleteRoot(this.origen);
        this.to.addModifier(node);
    }

    @Override
    public void doOperation() {
        this.query.deleteRoot(this.origen);
        this.to.addModifier(node);
    }
}

