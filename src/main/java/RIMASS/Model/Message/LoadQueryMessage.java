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
public class LoadQueryMessage {
    private List<UserStoredQuery> queries = new LinkedList<UserStoredQuery>();
    private long selected;
    public LoadQueryMessage(){
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

    /**
     * @return the selected
     */
    public long getSelected() {
        return selected;
    }

    /**
     * @param selected the selected to set
     */
    public void setSelected(long selected) {
        this.selected = selected;
    }
}
