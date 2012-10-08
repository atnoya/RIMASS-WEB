package RIMASS.Domain.query.base;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



import RIMASS.Domain.document.Relation;
import RIMASS.Domain.document.Term;
import java.io.Serializable;

/**
 *
 * 
 */
public class Restriction  implements Serializable {
    Relation relation;
    Term term;

    public Restriction(Relation relation, Term term) {
        this.relation = relation;
        this.term = term;
    }

    public Term getTerm() {
        return term;
    }

    public void setTerm(Term term) {
        this.term = term;
    }

    public Relation getRelation() {
        return relation;
    }

    public void setRelation(Relation relation) {
        this.relation = relation;
    }

    
}
