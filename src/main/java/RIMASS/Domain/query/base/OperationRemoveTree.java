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
public class OperationRemoveTree extends Operation implements Serializable{
    private final static int idOp  = 10;
    private final static String nameOp = "RemoveTree";
    private QueryNode node;
    private GraphicQuery query;

    public OperationRemoveTree(GraphicQuery query, QueryNode node){
        this.node = node;
        this.query = query;
    }

    @Override
    public void undo() {
        this.query.addRoot(node);
    }

    @Override
    public void redo() {
        this.query.deleteRoot(node);
    }

    @Override
    public void doOperation() {
        this.query.deleteRoot(node);
    }
}


