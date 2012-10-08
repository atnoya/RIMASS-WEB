package RIMASS.Domain.query.dao.neo4j;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import RIMASS.Domain.document.Term;
import RIMASS.Domain.query.base.GraphicQuery;
import RIMASS.Domain.query.base.Restriction;
import RIMASS.Domain.query.dao.QueryDao;
import es.uvigo.rimass.collection.store.neo4j.entities.DependenceNode;
import es.uvigo.rimass.collection.store.neo4j.entities.TermNode;
import es.uvigo.rimass.collection.store.neo4j.repositories.DependenceNodeRepository;
import es.uvigo.rimass.collection.store.neo4j.repositories.TermNodeRepository;

@Repository
public class QueryDaoNeo4jImpl implements QueryDao {

	@Autowired
	private DependenceNodeRepository depRepo;
	
	@Autowired
	private TermNodeRepository termRepo;
	
	public List<Restriction> getRestrictions(GraphicQuery query, String term) {
		Iterable<DependenceNode> nodes = depRepo.findDependencesStartWith(term);
		return new RestrictionsAdapter().adapt(nodes);
	}

	public List<Term> getGeneralizations(GraphicQuery query, String term) {
		Iterable<TermNode> nodes = termRepo.findAscendantNodes(term);
		return new TermAdapter().adapt(nodes);
	}

	public List<Term> getSpecializations(GraphicQuery query, String term) {
		Iterable<TermNode> nodes = termRepo.findDescendantNodes(term);
		return new TermAdapter().adapt(nodes);
	}

}
