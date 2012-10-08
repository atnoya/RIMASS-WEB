/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package RIMASS.Model.query.management;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import RIMASS.Domain.document.Document;
import RIMASS.Domain.document.Passage;
import RIMASS.Domain.query.base.GraphicQuery;
import RIMASS.Domain.query.base.TextualQuery;

/**
 *
 * @author ribadas
 */
public abstract class QueryManager implements Serializable{
    protected TextualQuery initialQuery;
    protected GraphicQuery currentQuery;

    @Autowired
    protected QueryExecuter queryExecuter;

    public QueryManager() {
        this.queryExecuter = null;
        this.initialQuery = null;
        this.currentQuery = null;
    }

    public abstract GraphicQueryEditor getGraphicQueryEditor();

    public abstract void setGraphicQueryEditor(GraphicQueryEditor graphicQueryEditor);

    public QueryExecuter getQueryExecuter() {
        return queryExecuter;
    }

    public void setQueryExecuter(QueryExecuter queryExecuter) {
        this.queryExecuter = queryExecuter;
    }

    public void setInitialQuery(TextualQuery query) {
        this.initialQuery = query;
        this.currentQuery = this.getGraphicQueryEditor().createGraphicQuery(query);

        this.queryExecuter.setCurrentQuery(currentQuery);
    }


    public GraphicQuery getCurrentQuery() {
        return currentQuery;
    }

    public TextualQuery getInitialQuery() {
        return initialQuery;
    }

    public void createGraphicQuery(TextualQuery query) {
        this.initialQuery = query;
        this.currentQuery = this.getGraphicQueryEditor().createGraphicQuery(query);
        this.queryExecuter.setCurrentQuery(currentQuery);
    }

    public GraphicQuery getCurrentGraphicQuery() {
        return(this.currentQuery);
    }

    public List<Document> getAllDocuments(){
        return(this.queryExecuter.getAllDocuments());
    }

    public List<Document> getDocumentsRange(int start, int range){
        return(this.queryExecuter.getDocumentsRange(start, range));
    }

    public List<Passage> getAllPassages(){
        return(this.queryExecuter.getAllPassages());
    }

    public List<Passage> getPassagesRange(int start, int range){
        return(this.queryExecuter.getPassagesRange(start, range));
    }

    public abstract boolean loadGraphicQuery(GraphicQuery gq);

}
