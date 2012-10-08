/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package RIMASS.Model.query.management;

import java.io.Serializable;
import java.util.List;

import RIMASS.Domain.document.Document;
import RIMASS.Domain.document.Passage;
import RIMASS.Domain.query.base.GraphicQuery;
import RIMASS.Domain.query.base.Query;

/**
 *
 * s
 */
public abstract class QueryExecuter implements Serializable{
    GraphicQuery currentQuery;
    ResultSet currentResultSet;

    public QueryExecuter() {
        currentQuery = null;
        currentResultSet = null;
    }

    public GraphicQuery getCurrentQuery() {
        return currentQuery;
    }

    public void setCurrentQuery(GraphicQuery currentQuery) {
        this.currentQuery = currentQuery;
        this.currentResultSet = null;
    }

    public ResultSet getCurrentResultSet() {
        return currentResultSet;
    }

    public void setCurrentResultSet(ResultSet currentResultSet) {
        this.currentResultSet = currentResultSet;
    }


    public void runQuery(){
    }

    public List<Document> getAllDocuments(){
        if (currentResultSet != null) {
            return (currentResultSet.getAllDocuments());
        }
        else {
            return (null);
        }
    }

    public List<Document> getDocumentsRange(int start, int range){
        if (currentResultSet != null) {
            return (currentResultSet.getDocumentsRange(start, range));
        }
        else {
            return (null);
        }
    }

    public List<Passage> getAllPassages(){
        if (currentResultSet != null) {
            return (currentResultSet.getAllPassages());
        }
        else {
            return (null);
        }

    }

    public List<Passage> getPassagesRange(int start, int range){
        if (currentResultSet != null) {
            return (currentResultSet.getPassagesRange(start, range));
        }
        else {
            return (null);
        }
    }

}
