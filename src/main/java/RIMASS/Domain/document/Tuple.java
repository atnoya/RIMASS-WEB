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
public class Tuple implements Serializable{
    long tupleID;
    Relation relation;
    Term head;
    Term modifier;

    public Tuple(long tupleID, Relation relation, Term head, Term modifier) {
        this.tupleID = tupleID;
        this.relation = relation;
        this.head = head;
        this.modifier = modifier;
    }

    public Term getHead() {
        return head;
    }

    public void setHead(Term head) {
        this.head = head;
    }

    public Term getModifier() {
        return modifier;
    }

    public void setModifier(Term modifier) {
        this.modifier = modifier;
    }

    public Relation getRelation() {
        return relation;
    }

    public void setRelation(Relation relation) {
        this.relation = relation;
    }

    public long getTupleID() {
        return tupleID;
    }

    public void setTupleID(long tupleID) {
        this.tupleID = tupleID;
    }



}
