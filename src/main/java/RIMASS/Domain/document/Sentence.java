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
public class Sentence implements Serializable{
    long sentenceID;
    Passage passage;

    List<Term> terms;
    List<Tuple> tuples;

    public Sentence(long sentenceID, Passage passage) {
        this.sentenceID = sentenceID;
        this.passage = passage;
    }

    public Passage getPassage() {
        return passage;
    }

    public void setPassage(Passage passage) {
        this.passage = passage;
    }

    public long getSentenceID() {
        return sentenceID;
    }

    public void setSentenceID(long sentenceID) {
        this.sentenceID = sentenceID;
    }

    public List<Term> getTerms() {
        return terms;
    }

    public void setTerms(List<Term> terms) {
        this.terms = terms;
    }

    public List<Tuple> getTuples() {
        return tuples;
    }

    public void setTuples(List<Tuple> tuples) {
        this.tuples = tuples;
    }



}
