/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package RIMASS.Model.query.management;

import RIMASS.Domain.query.base.Operation;
import java.io.Serializable;
import java.util.Stack;

/**
 *
 * @author hadriw
 */
public class QueryHistoryManager implements Serializable{
    private Stack<Operation> operations;
    private Stack<Operation> operationsUndo;

    public QueryHistoryManager(){
        operations = new Stack<Operation>();
        operationsUndo = new Stack<Operation>();
    }

    public void addOperation(Operation op){
        operations.push(op);
        setState();
    }

    public void setState(){
        operationsUndo.clear();
    }

    public void undoOperation(){
        if (operations.isEmpty()) return;
        Operation op = operations.pop();
        op.undo();
        operationsUndo.push(op);
    }

    public void redoOperation(){
        if (operationsUndo.isEmpty()) return;
        Operation op = operationsUndo.pop();
        op.redo();
        operations.push(op);
    }
}
