package RIMASS.Model.impl;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import RIMASS.Domain.document.Relation;
import RIMASS.Domain.document.RelationGeneric;
import RIMASS.Domain.document.Term;
import RIMASS.Domain.query.base.GraphicQuery;
import RIMASS.Domain.query.base.OperationAddRestriction;
import RIMASS.Domain.query.base.OperationAddRoot;
import RIMASS.Domain.query.base.OperationGeneralize;
import RIMASS.Domain.query.base.OperationMoveNode;
import RIMASS.Domain.query.base.OperationMoveNodeToRoot;
import RIMASS.Domain.query.base.OperationMoveRootToNode;
import RIMASS.Domain.query.base.OperationRemoveNode;
import RIMASS.Domain.query.base.OperationRemoveTree;
import RIMASS.Domain.query.base.OperationSpecialize;
import RIMASS.Domain.query.base.QueryNode;
import RIMASS.Domain.query.base.QueryNodeModifier;
import RIMASS.Domain.query.base.Restriction;
import RIMASS.Domain.query.base.TextualQuery;
import RIMASS.Domain.query.dao.AnalyzerDao;
import RIMASS.Domain.query.dao.QueryDao;
import RIMASS.Model.query.management.GraphicQueryEditor;
import RIMASS.Model.query.management.QueryHistoryManager;
import RIMASS.Model.query.management.TermNotFoundException;
import es.uvigo.rimass.collection.store.neo4j.entities.RelationNode;
import es.uvigo.rimass.collection.store.neo4j.entities.TermNode;
import es.uvigo.rimass.collection.store.neo4j.repositories.DependenceNodeRepository;
import es.uvigo.rimass.collection.store.neo4j.repositories.RelationNodeRepository;
import es.uvigo.rimass.collection.store.neo4j.repositories.TermNodeRepository;

@Component
public class GraphicQueryEditorImpl extends GraphicQueryEditor implements
		Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private QueryDao queryDao;

	@Autowired
	private AnalyzerDao analyzerDao;

	@Autowired
	private DependenceNodeRepository depRepo;

	@Autowired
	private TermNodeRepository termRepo;

	@Autowired
	private RelationNodeRepository relRepo;

	public GraphicQueryEditorImpl() {
		super();
		this.setHistory(new QueryHistoryManager());
	}

	public GraphicQueryEditorImpl(GraphicQuery currentQuery) {
		super();
		this.setCurrentQuery(currentQuery);
		this.setHistory(new QueryHistoryManager());
	}

	@Override
	public GraphicQuery createGraphicQuery(TextualQuery query) {
		this.setCurrentQuery(analyzerDao.parseQuery(query));
		return (this.getCurrentQuery());
	}

	@Override
	public List<Term> getGeneralizations(QueryNode anchor, String keyword) {
		List<Term> terms = queryDao.getGeneralizations(this.getCurrentQuery(),
				anchor.getTerm().getLabel());

		List<Term> toRet = new Vector<Term>();
		for (Term c : terms) {
			if (c.getLabel().toLowerCase().startsWith(keyword))
				toRet.add(c);
		}
		return toRet;
	}

	@Override
	public List<Term> getGeneralizations(long anchorID, String keyword) {
		return getGeneralizations(getCurrentQuery().getNode(anchorID), keyword);
	}

	@Override
	public List<Restriction> getRestrictions(QueryNode anchor) {
		return queryDao.getRestrictions(this.getCurrentQuery(), anchor
				.getTerm().getLabel());
	}

	@Override
	public List<Restriction> getRestrictions(long anchorID, String search) {
		Term termino = this.getCurrentQuery().getNode(anchorID).getTerm();
		List<Restriction> lr = queryDao.getRestrictions(this.getCurrentQuery(),
				termino.getLabel());
		List<Restriction> lrToRet = new Vector<Restriction>();
		if (!search.isEmpty()) {
			for (Restriction r : lr) {
				if (r.getTerm().getLabel().toLowerCase()
						.startsWith(search.toLowerCase()))
					lrToRet.add(r);
			}
		} else
			lrToRet = lr;
		return lrToRet;
	}

	@Override
	public List<Term> getSpecializations(QueryNode anchor, String keyword) {
		List<Term> toRet = new Vector<Term>();
		List<Term> aux = queryDao.getSpecializations(getCurrentQuery(), anchor
				.getTerm().getLabel());
		if (keyword.isEmpty())
			return aux;
		else {
			for (Term t : aux) {
				if (t.getLabel().toLowerCase()
						.startsWith(keyword.toLowerCase()))
					toRet.add(t);
			}

		}
		return toRet;
	}

	@Override
	public List<Term> getSpecializations(long anchorID, String keyword) {
		return getSpecializations(getCurrentQuery().getNode(anchorID), keyword);
	}

	@Override
	public void setCurrentQuery(GraphicQuery currentQuery) {
		super.setCurrentQuery(currentQuery);
	}

	private long siguienteID() {
		for (long i = 1; true; i++) {
			if (getCurrentQuery().getNode(i) == null)
				return i;
		}
	}

	@Override
	public void addRestriction(QueryNode anchor, Restriction restriction) {
		long id = siguienteID();
		QueryNode modificador = new QueryNode(id, restriction.getTerm());
		this.getCurrentQuery().getNodes().put(id, modificador);
		QueryNodeModifier enlace = new QueryNodeModifier(
				restriction.getRelation(), modificador);
		OperationAddRestriction op = new OperationAddRestriction(anchor, enlace);
		op.doOperation();
		this.getHistory().addOperation(op);
	}

	@Override
	public void addRestriction(long anchorID, long idrel, long idterm) {
		QueryNode anchor = getCurrentQuery().getNode(anchorID);

		TermNode termNode = this.termRepo.findOne(idterm);
		Relation newRel;
		if (idrel == -1)
			newRel = new RelationGeneric();
		else {
			RelationNode relNode = this.relRepo.findOne(idrel);
			newRel = new Relation(relNode.getId(),
					relNode.getLabel());
		}
		Restriction restriction = new Restriction(newRel, new Term(
				termNode.getId(), termNode.getLabel()));

		Long nodeId = getCurrentQuery().getNextNodeId();
		QueryNode modificador = new QueryNode(nodeId, restriction.getTerm());
		getCurrentQuery().getNodes().put(nodeId, modificador);
		QueryNodeModifier enlace = new QueryNodeModifier(
				restriction.getRelation(), modificador);
		OperationAddRestriction op = new OperationAddRestriction(anchor, enlace);
		op.doOperation();
		this.getHistory().addOperation(op);
	}

	@Override
	public void removeRestriction(QueryNode anchor, QueryNodeModifier modifier) {
		if (!anchor.isLeaf()) {
			OperationRemoveNode op = new OperationRemoveNode(modifier, anchor);
			op.doOperation();
			this.getHistory().addOperation(op);
		}
	}

	@Override
	public void removeRestriction(long anchorID, long relChildID, long childID) {
		QueryNode anchor = getCurrentQuery().getNode(anchorID);
		if (anchor != null && !anchor.isLeaf()) {
			QueryNodeModifier enlace = anchor.getModifier(relChildID, childID);
			OperationRemoveNode op = new OperationRemoveNode(enlace, anchor);
			op.doOperation();
			this.getHistory().addOperation(op);
		}
	}

	@Override
	public void removeRoot(QueryNode anchor) {
		OperationRemoveTree op = new OperationRemoveTree(getCurrentQuery(),
				anchor);
		op.doOperation();
		this.getHistory().addOperation(op);
	}

	@Override
	public void removeRoot(long anchorID) {
		QueryNode anchor = getCurrentQuery().getNode(anchorID);
		OperationRemoveTree op = new OperationRemoveTree(getCurrentQuery(),
				anchor);
		op.doOperation();
		this.getHistory().addOperation(op);
	}

	@Override
	public void generalizeQueryNode(QueryNode anchor, Term generalTerm) {
		Term old = anchor.getTerm();
		OperationGeneralize op = new OperationGeneralize(old, generalTerm,
				anchor);
		op.doOperation();
		this.getHistory().addOperation(op);
	}

	@Override
	public void generalizeQueryNode(long anchorID, long idcat) {
		QueryNode anchor = getCurrentQuery().getNode(anchorID);
		Term old = anchor.getTerm();
		TermNode termNode = termRepo.findOne(idcat);
		Term generalTerm = new Term(termNode.getId(),
				termNode.getLabel());
		OperationGeneralize op = new OperationGeneralize(old, generalTerm,
				anchor);
		op.doOperation();
		this.getHistory().addOperation(op);
	}

	@Override
	public void specializeQueryNode(QueryNode anchor, Term specializedTerm) {
		Term old = anchor.getTerm();
		OperationSpecialize op = new OperationSpecialize(old, specializedTerm,
				anchor);
		op.doOperation();
		this.getHistory().addOperation(op);
	}

	@Override
	public void specializeQueryNode(long anchorID, long idcat) {
		QueryNode anchor = getCurrentQuery().getNode(anchorID);
		Term old = anchor.getTerm();
		TermNode termNode = termRepo.findOne(idcat);
		if (termNode == null)
			return;
		Term specializedTerm = new Term(termNode.getId(),
				termNode.getLabel());
		OperationSpecialize op = new OperationSpecialize(old, specializedTerm,
				anchor);
		op.doOperation();
		this.getHistory().addOperation(op);
	}

	@Override
	public void moveNode(long originFatherId, long originMod, long origin,
			long to) {
		QueryNode father = getCurrentQuery().getNode(originFatherId);
		QueryNodeModifier originNode = father.getModifier(originMod, origin);
		QueryNode toNode = getCurrentQuery().getNode(to);
		OperationMoveNode op = new OperationMoveNode(father, originNode, toNode);
		op.doOperation();
		this.getHistory().addOperation(op);
	}

	@Override
	public void moveNodeToRoot(long relId, long nodeId, long fatherId) {
		QueryNode father = getCurrentQuery().getNode(fatherId);
		QueryNodeModifier originNode = father.getModifier(relId, nodeId);
		OperationMoveNodeToRoot op = new OperationMoveNodeToRoot(father,
				originNode, getCurrentQuery());
		op.doOperation();
		this.getHistory().addOperation(op);
	}

	@Override
	public void moveRootToNode(long idOrigen, long idTo) {
		GraphicQuery currentQuery = getCurrentQuery();
		QueryNode nodoOrigen = currentQuery.getNode(idOrigen);
		QueryNode nodoTo = currentQuery.getNode(idTo);
		QueryNodeModifier nuevoNodo = new QueryNodeModifier(
				new RelationGeneric(), nodoOrigen);
		OperationMoveRootToNode op = new OperationMoveRootToNode(nodoOrigen,
				nuevoNodo, nodoTo, currentQuery);
		op.doOperation();
		this.getHistory().addOperation(op);
	}

	@Override
	public void addRoot(String term) throws TermNotFoundException {

		Iterable<TermNode> termNodes = termRepo.findByLabel(term);
		Iterator<TermNode> iterator = termNodes.iterator();

		TermNode termnode = null;
		if (iterator.hasNext())
			termnode = iterator.next();
		else
			throw new TermNotFoundException("Term not found");

		Term newTerm = new Term(termnode.getId(), termnode.getLabel());
		QueryNode node = new QueryNode(getCurrentQuery().getNextNodeId(), newTerm);
		GraphicQuery currentQuery = getCurrentQuery();
		currentQuery.getNodes().put(node.getId(), node);
		OperationAddRoot op = new OperationAddRoot(currentQuery, node);
		op.doOperation();
		this.getHistory().addOperation(op);
	}
}
