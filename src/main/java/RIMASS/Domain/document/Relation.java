/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package RIMASS.Domain.document;

import java.io.Serializable;

/**
 *
 * 
 */
public class Relation implements Serializable{
	
	public static final Relation EMPTY_RELATION_ID = new Relation(0L, "");

    Long relationID;
    String label;

    public Relation(long relationID, String label) {
        this.relationID = relationID;
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Long getRelationID() {
        return relationID;
    }

    public void setRelationID(Long relationID) {
        this.relationID = relationID;
    }

    @Override
    public String toString(){
        return("<"+this.label+"[id="+this.relationID+"]>");
    }
}
