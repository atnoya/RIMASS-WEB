/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package RIMASS.Domain.document;

import java.io.Serializable;
import java.util.List;
/**
 * ribadas
 *
 */
public class Document implements Serializable {
    long docID;
    Location location;

    List<Passage> passages;

    public Document(long docID, Location location) {
        this.docID = docID;
        this.location = location;
    }

    public long getDocID() {
        return docID;
    }

    public void setDocID(long docID) {
        this.docID = docID;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Passage> getPassages() {
        return passages;
    }

    public void setPassages(List<Passage> passages) {
        this.passages = passages;
    }


    @Override
    public String toString(){
        return("[docID:"+this.docID+", location:"+this.location.toString()+"]");
    }

}
