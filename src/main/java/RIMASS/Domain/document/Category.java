/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package RIMASS.Domain.document;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;

/**
 *
 * 
 */
public class Category extends Term implements Serializable{
    List<Category> subCategories;
    Category superCategory=null;
    
    List<Word> words;

    public Category(long categoryID, String categoryLabel) {
        super(categoryID, categoryLabel);
        this.subCategories = new Vector<Category>();
        this.words = new Vector<Word>();
    }

    public List<Category> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<Category> subCategories) {
        this.subCategories = subCategories;
    }

    public Category getSuperCategory() {
        return superCategory;
    }

    public void setSuperCategory(Category superCategory) {
        this.superCategory = superCategory;
    }

    public List<Word> getWords() {
        return words;
    }

    public void setWords(List<Word> words) {
        this.words = words;
    }


    @Override
    public String toString(){
        String str = this.getLabel()+" [id="+this.getTermID();
        if (this.subCategories != null) {
            str += ", #subcats="+this.subCategories.size();

        }

        if (this.words != null) {
            str += ", #words="+this.words.size();

        }

        str +="]";

        return(str);
    }

}
