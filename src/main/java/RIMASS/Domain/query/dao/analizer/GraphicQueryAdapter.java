package RIMASS.Domain.query.dao.analizer;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import RIMASS.Domain.document.Relation;
import RIMASS.Domain.document.Term;
import RIMASS.Domain.query.base.GraphicQuery;
import RIMASS.Domain.query.base.QueryNode;
import RIMASS.Domain.query.base.QueryNodeModifier;
import es.uvigo.rimass.collection.store.neo4j.entities.RelationNode;
import es.uvigo.rimass.collection.store.neo4j.entities.TermNode;
import es.uvigo.rimass.collection.store.neo4j.repositories.RelationNodeRepository;
import es.uvigo.rimass.collection.store.neo4j.repositories.TermNodeRepository;
import es.uvigo.rimass.core.tree.TreeNodeRelation;
import es.uvigo.rimass.core.tree.TreeRepresentation;

public class GraphicQueryAdapter {

	private Long nodeId;
	
	private TermNodeRepository termRepo;
	private RelationNodeRepository relRepo;
	
	public GraphicQueryAdapter(TermNodeRepository termRepo, RelationNodeRepository relRepo) {
		this.termRepo = termRepo;
		this.relRepo = relRepo;
		nodeId = 0L;
	}
	
	public GraphicQuery parse(Collection<TreeRepresentation> trees) {
		GraphicQuery gq = new GraphicQuery(0);
		for (TreeRepresentation tree : trees) {
			TermNode termNode = findOneTermNode(tree.getRoot().getName());
			QueryNode newNode = null;
			if (termNode != null){
				 newNode = new QueryNode(nodeId++, new Term(termNode.getId(), termNode.getLabel()));
			}else {
				newNode = new QueryNode(nodeId++, new Term(GraphicQuery.UNKNOWN_TERM_ID, tree.getRoot().getName()));
			}
			gq.addRoot(newNode);
			gq.registerNode(newNode.getId(), newNode);
			buildTreeChildren(gq, tree.getRoot().getChildren(), newNode);
		}
		
		gq.setNodeId(nodeId);
		
		return gq;
	}

	private void buildTreeChildren(GraphicQuery query, List<TreeNodeRelation> children,
			QueryNode node) {
		for (TreeNodeRelation relation : children){
			TermNode termNode = findOneTermNode(relation.getChild().getName());
			QueryNode newNode = null;
			if (termNode != null){
				 newNode = new QueryNode(nodeId++, new Term(termNode.getId(), termNode.getLabel()));
			}else {
				newNode = new QueryNode(nodeId++, new Term(GraphicQuery.UNKNOWN_TERM_ID, relation.getChild().getName()));
			}
			
			Relation rel = null;
			RelationNode relNode = findOneRelationNode(relation.getRelation());
			if (relNode != null){
				rel = new Relation(relNode.getId(), relNode.getLabel());
			}else{
				rel = new Relation(GraphicQuery.UNKNOWN_RELATION_ID, relation.getRelation());
			}
			node.addModifier(new QueryNodeModifier(rel, newNode));

			query.registerNode(newNode.getId(), newNode);
			buildTreeChildren(query, relation.getChild().getChildren(), newNode);
		}
	}
	
	private TermNode findOneTermNode(String label){
		Iterator<TermNode> termIterator = termRepo.findByLabel(label).iterator();
		if (termIterator.hasNext()){
			return termIterator.next();
		}
		return null;
	}
	
	private RelationNode findOneRelationNode(String label){
		Iterator<RelationNode> relIterator = relRepo.findByLabel(label).iterator();
		if (relIterator.hasNext()){
			return relIterator.next();
		}
		return null;
	}
}
