package RIMASS.Domain.query.dao;

import RIMASS.Domain.query.base.GraphicQuery;
import RIMASS.Domain.query.base.TextualQuery;

public interface AnalyzerDao {

	GraphicQuery parseQuery(TextualQuery query);
	
}
