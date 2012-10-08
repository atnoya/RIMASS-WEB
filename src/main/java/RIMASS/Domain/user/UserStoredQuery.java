/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package RIMASS.Domain.user;

import RIMASS.Domain.query.base.GraphicQuery;
import java.util.Date;

/**
 *
 * @author hadriw
 */
public class UserStoredQuery {
    private int id;
    private String name;
    private int userid;
    private String description;
    private Long numberOfResults;
    private Date date;
    private GraphicQuery query;

    public UserStoredQuery(){}
    public UserStoredQuery(int id, String name, int userid, String description, Long numberOfResults, Date date, GraphicQuery query){
        this.id = id;
        this.name = name;
        this.userid = userid;
        this.description = description;
        this.numberOfResults = numberOfResults;
        this.date = date;
        this.query = query;

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
     * @return the numberOfResults
     */
    public Long getNumberOfResults() {
        return numberOfResults;
    }

    /**
     * @param numberOfResults the numberOfResults to set
     */
    public void setNumberOfResults(Long numberOfResults) {
        this.numberOfResults = numberOfResults;
    }

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the userid
     */
    public int getUserid() {
        return userid;
    }

    /**
     * @param userid the userid to set
     */
    public void setUserid(int userid) {
        this.userid = userid;
    }

    /**
     * @return the query
     */
    public GraphicQuery getQuery() {
        return query;
    }

    /**
     * @param query the query to set
     */
    public void setQuery(GraphicQuery query) {
        this.query = query;
    }
}
