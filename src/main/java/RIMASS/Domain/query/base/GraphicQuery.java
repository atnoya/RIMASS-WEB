package RIMASS.Domain.query.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import RIMASS.Domain.document.Relation;
import RIMASS.Domain.document.Term;

public class GraphicQuery extends Query implements Serializable {	
	
	public static final Long UNKNOWN_TERM_ID = -1L;
	public static final Long UNKNOWN_RELATION_ID = -1L;
	
	private Long nodeId;
	private List<QueryNode> roots;
	private HashMap<Long, QueryNode> nodes;

	public GraphicQuery(long id) {
		this(id, new ArrayList<QueryNode>(), new HashMap<Long, QueryNode>());
	}

	public GraphicQuery(long id, List<QueryNode> roots,
			HashMap<Long, QueryNode> nodes) {
		super(id);
		this.roots = roots;
		this.nodes = nodes;
		
		//ID 0: Reserved
		this.nodeId = 1L;
	}

	public List<QueryNode> getRoots() {
		return roots;
	}

	public void setRoots(List<QueryNode> roots) {
		this.roots = roots;
	}

	public void addRoot(QueryNode root) {
		this.roots.add(root);
	}

	public void deleteRoot(QueryNode root) {
		this.roots.remove(root);
	}

	public HashMap<Long, QueryNode> getNodes() {
		return this.nodes;
	}
	
	public void registerNode(Long id, QueryNode node){
		this.nodes.put(id, node);
	}

	public void unRegisterNode(Long id){
		this.nodes.remove(id);
	}
	
	/**
	 * @param nodes
	 *            the nodes to set
	 */
	public void setNodes(HashMap<Long, QueryNode> nodes) {
		this.nodes = nodes;
	}

	public QueryNode getNode(long id) {
		return this.nodes.get(id);
	}

	@Override
	public String toString() {
		String str;

		str = "queryID=" + this.id + "\n";
		for (QueryNode node : this.roots) {
			str = str + node.toString();
			str += "\n";
		}
		return (str);
	}
	
	public Long getNodeId() {
		return nodeId;
	}
	
	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}
	
	public Long getNextNodeId(){
		return nodeId++;
	}
}

