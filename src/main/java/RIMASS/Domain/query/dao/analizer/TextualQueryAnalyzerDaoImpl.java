package RIMASS.Domain.query.dao.analizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import RIMASS.Domain.query.base.GraphicQuery;
import RIMASS.Domain.query.base.TextualQuery;
import RIMASS.Domain.query.dao.AnalyzerDao;
import es.uvigo.rimass.collection.Analyzer;
import es.uvigo.rimass.collection.Collection;
import es.uvigo.rimass.collection.Configuration;
import es.uvigo.rimass.collection.Language;
import es.uvigo.rimass.collection.analyzers.stanfordnlp.StanfordSingleAnalizer;
import es.uvigo.rimass.collection.store.neo4j.repositories.RelationNodeRepository;
import es.uvigo.rimass.collection.store.neo4j.repositories.TermNodeRepository;
import es.uvigo.rimass.core.Document;
import es.uvigo.rimass.core.Metadata;
import es.uvigo.rimass.core.TreeStringRepresentation;
import es.uvigo.rimass.thesaurus.Thesaurus;

@Repository
public class TextualQueryAnalyzerDaoImpl implements AnalyzerDao {

	private Thesaurus thesaurus;

	private Analyzer analyzer;
	
	@Autowired
	private RelationNodeRepository relRepo;

	@Autowired
	private TermNodeRepository termRepo;
	
	public TextualQueryAnalyzerDaoImpl() {
		Configuration configuration = new Configuration("/home/adrian/tmp");
        Collection collection = new Collection("medline", new Language("english", "en"));

		thesaurus = new Thesaurus(configuration.getProperty("base_dir")
				+ "/thesaurus.MESH.xml");

		analyzer = new StanfordSingleAnalizer(thesaurus);
		analyzer.initialize(collection, configuration);

	}

	public GraphicQuery parseQuery(TextualQuery query) {
		String origText = query.getTexto();

		Document<?> doc = analyzer.analizeDocument(new Document<Metadata>(0,
				null, origText, null));
		TreeStringRepresentation deps = (TreeStringRepresentation) doc
				.representationByType("tree");

		return new GraphicQueryAdapter(termRepo, relRepo).parse(deps.getTrees());
	}

}
