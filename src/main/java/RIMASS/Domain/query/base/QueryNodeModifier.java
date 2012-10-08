package RIMASS.Domain.query.base;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



import RIMASS.Domain.document.Relation;
import java.io.Serializable;

/**
 *
 * 
 */
public class QueryNodeModifier implements Serializable{
    Relation relation;
    QueryNode modifier;

    public QueryNodeModifier(Relation relation, QueryNode modifier) {
        this.relation = relation;
        this.modifier = modifier;
    }

    public QueryNode getModifier() {
        return modifier;
    }

    public void setModifier(QueryNode modifier) {
        this.modifier = modifier;
    }

    public Relation getRelation() {
        return relation;
    }

    public void setRelation(Relation relation) {
        this.relation = relation;
    }


    @Override
    public String toString(){
        return(this.toString(0));
    }

    public String toString(int saltos) {
        String str = "";

        String str_saltos = "";
        for (int i=0; i< saltos; i++){
            str_saltos = str_saltos + " ";
        }

        str = str_saltos + this.relation.toString()+"\n"+this.modifier.toString(saltos);
        return(str);
    }

}
