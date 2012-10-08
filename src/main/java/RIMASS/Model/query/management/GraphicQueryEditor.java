/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package RIMASS.Model.query.management;

import RIMASS.Domain.document.Category;
import RIMASS.Domain.document.Relation;
import RIMASS.Domain.document.Term;
import RIMASS.Domain.document.Word;
import java.util.List;
import RIMASS.Domain.query.base.GraphicQuery;
import RIMASS.Domain.query.base.QueryNode;
import RIMASS.Domain.query.base.QueryNodeModifier;
import RIMASS.Domain.query.base.Restriction;
import RIMASS.Domain.query.base.TextualQuery;
import java.io.Serializable;

/**
 *
 * 
 */
public abstract class GraphicQueryEditor implements Serializable{
	private static final long serialVersionUID = 1L;

	private GraphicQuery currentQuery;
    private QueryHistoryManager history;


    public GraphicQueryEditor() {
        this.currentQuery = null;
        this.history = null;
    }

    public GraphicQueryEditor(GraphicQuery currentQuery) {
        this.currentQuery = currentQuery;
        this.history = null;
    }

    public GraphicQuery getCurrentQuery() {
        return currentQuery;
    }

    public void setCurrentQuery(GraphicQuery currentQuery) {
        this.currentQuery = currentQuery;
    }

    /**
     * @return the history
     */
    public QueryHistoryManager getHistory() {
        return history;
    }
    
    public void setHistory(QueryHistoryManager history){
    	this.history =history;
    }

    public abstract GraphicQuery createGraphicQuery(TextualQuery query);

    public abstract List<Restriction> getRestrictions(QueryNode anchor);
    
    public abstract List<Restriction> getRestrictions(long anchorID, String search);

    public abstract List<Term> getGeneralizations(QueryNode anchor, String keyword);

    public abstract List<Term> getGeneralizations(long anchorID, String keyword);

    public abstract List<Term> getSpecializations(QueryNode anchor, String keyword);

    public abstract List<Term> getSpecializations(long anchorID, String keyword);

    public abstract void addRestriction(QueryNode anchor, Restriction restriction);

    public abstract void addRestriction(long anchorID, long idrel, long idterm);

    public abstract void removeRestriction(QueryNode anchor, QueryNodeModifier modifier);

    public abstract void removeRestriction(long anchorID, long relChildID, long childID);

    public abstract void generalizeQueryNode(QueryNode anchor, Term generalTerm);

    public abstract void generalizeQueryNode(long anchorID, long idcat);

    public abstract void specializeQueryNode(QueryNode anchor, Term specializedTerm);

    public abstract void specializeQueryNode(long anchorID, long idcat);

    public abstract void removeRoot(QueryNode anchor);

    public abstract void removeRoot(long anchorID);

    public abstract void moveNode(long originFatherId, long originMod, long origin, long to);

    public abstract void moveNodeToRoot(long relId, long nodeId, long fatherId);
    
    public abstract void moveRootToNode(long idOrigen, long idTo);

    public abstract void addRoot(String term)  throws TermNotFoundException;
}
