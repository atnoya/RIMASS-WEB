/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package RIMASS.Model.Message;

import RIMASS.Domain.user.UserStoredQuery;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author hadriw
 */
public class SaveQueryMessage {
    private String name;
    private String description;
    private List<UserStoredQuery> queries = new LinkedList<UserStoredQuery>();

    public SaveQueryMessage(){}

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the queries
     */
    public List<UserStoredQuery> getQueries() {
        return queries;
    }

    /**
     * @param queries the queries to set
     */
    public void setQueries(List<UserStoredQuery> queries) {
        this.queries = queries;
    }
}
