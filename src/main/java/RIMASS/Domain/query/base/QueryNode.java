package RIMASS.Domain.query.base;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



import RIMASS.Domain.document.Term;
import java.io.Serializable;
import java.util.List;
import java.util.Vector;

/**
 *
 * 
 */
public class QueryNode implements Cloneable,Serializable {
    long id;
    Term term;
    List<QueryNodeModifier> modifiers;

    public QueryNode(long id, Term term) {
        this.id = id;
        this.term = term;
        this.modifiers = new Vector<QueryNodeModifier>();
    }

    public QueryNode(long id, Term term, Vector<QueryNodeModifier> modifiers){
        this.id = id;
        this.term = term;
        this.modifiers = modifiers;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Term getTerm() {
        return term;
    }

    public void setTerm(Term term) {
        this.term = term;
    }

    @Override
    public String toString(){
        return(this.toString(0));
    }

    public String toString(int saltos){
        return(this.id+":"+this.term.toString()+this.getModifiers());
    }

    public List<QueryNodeModifier> getModifiers() {
        return modifiers;
    }

    public void setModifiers(List<QueryNodeModifier> modifiers) {
        this.modifiers = modifiers;
    }

    public void addModifier(QueryNodeModifier modifier){
        this.modifiers.add(modifier);
    }


    public QueryNodeModifier getModifier(long relID, long termID){
        int idx=0;
        for (QueryNodeModifier m:this.modifiers) {
            if (m.getRelation().getRelationID() == relID) {
                if (m.getModifier().getId() == termID) {
                    return this.modifiers.get(idx);
                }
            }
            idx++;
        }
        return null;
    }

    public QueryNodeModifier removeModifier(QueryNodeModifier modifier){
        //this.modifiers.remove(modifier);   // No funcionará

        int idx=0;
        for (QueryNodeModifier m:this.modifiers) {
            if (m.getRelation().getRelationID() == modifier.getRelation().getRelationID()) {
                if (m.getModifier().getTerm().equals(modifier.getModifier().getTerm())) {
                    return this.modifiers.remove(idx);
                }
            }
            idx++;
        }
        return null;

    }

    public QueryNodeModifier removeModifier(long relID, long termID){
        //this.modifiers.remove(modifier);   // No funcionará

        int idx=0;
        for (QueryNodeModifier m:this.modifiers) {
            if (m.getRelation().getRelationID() == relID) {
                if (m.getModifier().getId() == termID) {
                    return this.modifiers.remove(idx);
                }
            }
            idx++;
        }
        return null;
    }

    public boolean isLeaf(){
        return modifiers.isEmpty();
    }

    @Override
    public Object clone(){
        Object obj=null;
        try{
            obj=super.clone();
        }catch(CloneNotSupportedException ex){
            System.out.println(" no se puede duplicar");
        }
        return obj;
    }

}
