package RIMASS.Domain.query.dao;

import java.util.List;

import RIMASS.Domain.document.Relation;
import RIMASS.Domain.document.Term;
import RIMASS.Domain.query.base.GraphicQuery;
import RIMASS.Domain.query.base.Restriction;

public interface QueryDao {

	List<Restriction> getRestrictions(GraphicQuery query, String term);
	
	List<Term> getGeneralizations(GraphicQuery query, String term);
	
	List<Term> getSpecializations(GraphicQuery query, String term);
}
