/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package RIMASS.Model.query.management;

import RIMASS.Domain.document.Document;
import RIMASS.Domain.document.Passage;
import java.util.List;
import RIMASS.Domain.query.base.Query;
import java.io.Serializable;


/**
 *
 * 
 */
public class ResultSet implements Serializable{
    Query query;
    List<Document> documents;
    int currentDocumentCount;
    int currentPassageCount;


    public ResultSet() {
        query = null;
        documents = null;
        currentDocumentCount = 0;
        currentPassageCount = 0;
    }

    public ResultSet(Query query, List<Document> documents) {
        this.query = query;
        this.documents = documents;
        currentDocumentCount = 0;
        currentPassageCount = 0;
    }


    public void setDocuments(List<Document> documents) {
        this.documents = documents;
        this.currentDocumentCount = documents.size();
        this.currentPassageCount = 0; // a corregir
    }

    public void setQuery(Query query) {
        this.query = query;
        this.documents = null;
        currentDocumentCount = 0;
        currentPassageCount = 0;
    }

    public List<Document> getAllDocuments(){
        return (this.documents);
    }

    public List<Document> getDocumentsRange(int start, int range){
        return(this.documents.subList(start, start+range));
    }

    public List<Passage> getAllPassages(){
        return(null);
    }

    public List<Passage> getPassagesRange(int start, int range){
        return(null);
    }

}
