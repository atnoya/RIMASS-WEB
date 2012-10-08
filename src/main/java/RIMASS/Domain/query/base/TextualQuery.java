package RIMASS.Domain.query.base;

import java.io.Serializable;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



/**
 *
 * 
 */
public class TextualQuery extends Query  implements Serializable{
    String texto;

    public TextualQuery(long id, String texto) {
        super(id);
        this.texto = texto;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

}
