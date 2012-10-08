package RIMASS.Model.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import RIMASS.Domain.document.Document;
import RIMASS.Domain.document.URILocation;
import RIMASS.Domain.query.base.GraphicQuery;
import RIMASS.Domain.query.base.QueryNode;
import RIMASS.Domain.query.base.QueryNodeModifier;
import RIMASS.Model.query.management.QueryExecuter;
import RIMASS.Model.query.management.ResultSet;
import es.uvigo.rimass.collection.store.neo4j.entities.DocumentNode;
import es.uvigo.rimass.collection.store.neo4j.repositories.DependenceNodeRepository;
import es.uvigo.rimass.collection.store.neo4j.repositories.DocumentNodeRepository;

@Service
public class QueryExecuterImpl extends QueryExecuter {

	private static final long serialVersionUID = -2926647372224801805L;

	@Autowired
	private DependenceNodeRepository depRepo;

	@Autowired
	private DocumentNodeRepository docRepo;
	
	public QueryExecuterImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void runQuery() {
		List<Long> depIds = extractDepIds();
		Iterator<DocumentNode> iterator = docRepo.findDocsByRelIds(depIds).iterator();
		
		List<Document> docs = new ArrayList<Document>();
		while(iterator.hasNext()){
			DocumentNode nodeDoc = iterator.next();
			docs.add(new Document(nodeDoc.getId(), new URILocation(nodeDoc.getTitle())));
		}
		this.setCurrentResultSet(new ResultSet(getCurrentQuery(),docs));
	}

	private List<Long> extractDepIds() {
		GraphicQuery query = getCurrentQuery();
		List<Long> ids = new ArrayList<Long>();

		for (QueryNode node : query.getRoots()){
			ids.addAll(extractNodeRelIds(node));
		}
		return ids;
	}

	private List<Long> extractNodeRelIds(QueryNode node) {
		List<Long> ids = new ArrayList<Long>();
		for (QueryNodeModifier mod : node.getModifiers()) {
			if (node.getTerm().getTermID() != GraphicQuery.UNKNOWN_TERM_ID
					&& mod.getRelation().getRelationID() != GraphicQuery.UNKNOWN_RELATION_ID
					&& mod.getModifier().getTerm().getTermID() != GraphicQuery.UNKNOWN_TERM_ID) {

				ids.addAll(depRepo.findDependenceByIds(node.getTerm().getTermID(), mod.getModifier().getTerm().getTermID(), mod.getRelation().getRelationID()));
			}
			ids.addAll(extractNodeRelIds(mod.getModifier()));
		}
		return ids;
	}
}
