package RIMASS.Domain.query.dao.neo4j;

import java.util.ArrayList;
import java.util.List;

import RIMASS.Domain.document.Term;
import es.uvigo.rimass.collection.store.neo4j.entities.TermNode;

public class TermAdapter {

	public TermAdapter() {
	}

	public List<Term> adapt(Iterable<TermNode> nodes) {
		List<Term> terms = new ArrayList<Term>();

		for (TermNode node : nodes) {
			terms.add(new Term(node.getId(), node.getLabel()));
		}

		return terms;
	}
}
