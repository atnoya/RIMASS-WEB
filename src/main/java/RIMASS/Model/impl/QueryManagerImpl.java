package RIMASS.Model.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import RIMASS.Domain.query.base.GraphicQuery;
import RIMASS.Model.query.management.GraphicQueryEditor;
import RIMASS.Model.query.management.QueryExecuter;
import RIMASS.Model.query.management.QueryManager;

@Component
public class QueryManagerImpl extends QueryManager{

	private static final long serialVersionUID = 1599003873603794277L;

	@Autowired
	private GraphicQueryEditor editor;
	
	public QueryManagerImpl() {
		super();
	}

	@Override
	public boolean loadGraphicQuery(GraphicQuery gq) {
		return false;
	}

	@Override
	public GraphicQueryEditor getGraphicQueryEditor() {
		return editor;
	}

	@Override
	public void setGraphicQueryEditor(GraphicQueryEditor graphicQueryEditor) {
		this.editor = graphicQueryEditor;
	}
	
}
