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
public class Word extends Term implements Serializable{
    String lema;
    List<Category> categories;
    Category mainCategory;

    public Word(long termID, String label, String lema, List<Category> categories, Category mainCategory) {
        super(termID, label);
        this.lema = lema;
        this.categories = categories;
        this.mainCategory = mainCategory;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public String getLema() {
        return lema;
    }

    public void setLema(String lema) {
        this.lema = lema;
    }

    public Category getMainCategory() {
        return mainCategory;
    }

    public void setMainCategory(Category mainCategory) {
        this.mainCategory = mainCategory;
    }
    
    @Override
    public String toString(){
        return(this.lema+ "[id="+this.getTermID()+"]");
    }



}
