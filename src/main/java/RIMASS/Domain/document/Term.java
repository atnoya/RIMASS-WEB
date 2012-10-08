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
public class Term implements Serializable{
    Long termID;
    String label;

    public Term(Long termID, String label) {
        this.termID = termID;
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Long getTermID() {
        return termID;
    }

    public void setTermID(Long termID) {
        this.termID = termID;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Term other = (Term) obj;
        if (this.termID != other.termID) {
            return false;
        }
        if ((this.label == null) ? (other.label != null) : !this.label.equals(other.label)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + (int) (this.termID ^ (this.termID >>> 32));
        hash = 97 * hash + (this.label != null ? this.label.hashCode() : 0);
        return hash;
    }
}
