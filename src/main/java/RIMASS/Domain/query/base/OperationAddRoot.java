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
public class OperationAddRoot extends Operation implements Serializable{
    private final static int idOp  = 1;
    private final static String nameOp = "AddRoot";
    private QueryNode node;
    private GraphicQuery roots;

    public OperationAddRoot(GraphicQuery roots, QueryNode node){
        this.node = node;
        this.roots = roots;
    }

    @Override
    public void undo() {
        this.roots.deleteRoot(node);
    }

    @Override
    public void redo() {
        this.roots.addRoot(node);
    }

    @Override
    public void doOperation() {
        this.roots.addRoot(node);
    }
}
