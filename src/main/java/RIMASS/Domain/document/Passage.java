/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package RIMASS.Domain.document;

import java.io.Serializable;
import java.util.List;

/**
 *
 * 
 */
public class Passage implements Serializable{
    long passageID;
    Document document;

    List<Sentence> sentences;

    public Passage(long passageID, Document document) {
        this.passageID = passageID;
        this.document = document;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public long getPassageID() {
        return passageID;
    }

    public void setPassageID(long passageID) {
        this.passageID = passageID;
    }

    public List<Sentence> getSentences() {
        return sentences;
    }

    public void setSentences(List<Sentence> sentences) {
        this.sentences = sentences;
    }


}
