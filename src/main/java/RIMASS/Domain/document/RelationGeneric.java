/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package RIMASS.Domain.document;

import java.io.Serializable;

/**
 *
 * @author hadriw
 */
public class RelationGeneric extends Relation implements Serializable{
    public RelationGeneric(){
        super(-1, "(*)");
    }

    @Override
    public void setLabel(String label) {
        throw new UnsupportedOperationException("Can't change label on a Generic Relation.");
    }

    @Override
    public void setRelationID(Long relationID) {
        throw new UnsupportedOperationException("Can't change ID on a Generic Relation.");
    }
}
