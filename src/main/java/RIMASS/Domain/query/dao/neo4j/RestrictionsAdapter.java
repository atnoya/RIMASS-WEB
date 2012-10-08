package RIMASS.Domain.query.dao.neo4j;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import RIMASS.Domain.document.Relation;
import RIMASS.Domain.document.Term;
import RIMASS.Domain.query.base.Restriction;
import es.uvigo.rimass.collection.store.neo4j.entities.DependenceNode;

public class RestrictionsAdapter {

	public RestrictionsAdapter() {
	}

	public List<Restriction> adapt(Iterable<DependenceNode> descriptors) {
		List<Restriction> restrictions = new ArrayList<Restriction>();
		
		Iterator<DependenceNode> iterator = descriptors.iterator();
		while (iterator.hasNext()){
			DependenceNode next = iterator.next();
			restrictions.add(new Restriction(
					new Relation(next.getRelation().getId(), next.getRelation().getLabel()),
					new Term(next.getModifier().getId(), next.getModifier().getLabel())));
		}
		
		return restrictions;
	}

}
