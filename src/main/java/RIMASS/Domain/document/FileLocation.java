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
public class FileLocation extends Location implements Serializable {
    String path;
    String filename;

    public FileLocation(String path, String filename) {
        this.path = path;
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString(){
        return(this.path+":"+this.filename);
    }

}
