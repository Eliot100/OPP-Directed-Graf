package dataStructure;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
/**
 * This class is implementation of directed graph.
 * @author Eli Ruvinov
 */
@SuppressWarnings("serial")
public class DGraph implements graph,Serializable{
	public int lastId;
	public int MC;
	public HashMap<Integer, Node> nodeHash;
	public HashMap<Integer, node_data> data_nodeHash;
	public Hashtable<Integer, edge_data> edgeHash;

	public DGraph( Collection<Node> c1, Collection<Edge> c2) {
		int biggestID = -1; 
		MC = 0;
		Iterator<Node> nodeIter = c1.iterator();
		while (nodeIter.hasNext()) {
			Node NodeData = nodeIter.next();
			nodeHash.put(NodeData.getKey(), NodeData);
			data_nodeHash.put(NodeData.getKey(), NodeData);
			if(NodeData.getKey() > biggestID)
				biggestID = NodeData.getKey();
		}
		lastId = biggestID;
		Iterator<Edge> edgeIter = c2.iterator();
		while (edgeIter.hasNext()) {
			Edge e = edgeIter.next();
			this.connect(e.getSrc(), e.getDest(), e.getWeight());
		}
	}
	
	public DGraph(DGraph g) {
		lastId = g.lastId;
		MC = g.MC;
		nodeHash = g.nodeHash;
		data_nodeHash = g.data_nodeHash;
		edgeHash = g.edgeHash;
	}
	
	public DGraph(graph g) {
		int biggestID = -1; 
		MC = g.getMC();
		Iterator<node_data> nodeIter = data_nodeHash.values().iterator();
		while (nodeIter.hasNext()) {
			node_data node = nodeIter.next();
			Node NodeData = (Node) node;
			nodeHash.put(NodeData.getKey(), NodeData);
			if(NodeData.getKey() > biggestID)
				biggestID = NodeData.getKey();
		}
		lastId = biggestID;
		Iterator<edge_data> edgeIter = edgeHash.values().iterator();
		while (edgeIter.hasNext()) {
			edge_data edge = edgeIter.next();
			this.connect(edge.getSrc(), edge.getDest(), edge.getWeight());
			edgeHash.put(edge.getSrc(), edge);
		}
	}
//	private Iterator<Edge> edgeIterator(){
//		return edgeHash.values().iterator();
//	}
//	private Iterator<Node> nodeIterator(){
//		return nodeHash.values().iterator();
//	}
//	public int getNewId() {
//		lastId++;
//		return lastId;
//	}
	/**
	 *  This is a constructor for a graph.
	 */
	public DGraph() {
		lastId = 0;
		nodeHash = new HashMap<Integer, Node>();
		data_nodeHash = new HashMap<Integer, node_data>();
		MC = 0;
	}
	
	@Override
	public node_data getNode(int key) {
		return nodeHash.get(key);
	}
	public Node getRealNode(int key) {
		return nodeHash.get(key);
	}

	@Override
	public edge_data getEdge(int src, int dest) {
		return nodeHash.get(src).getDestEdge(dest);
	}

	@Override
	public void addNode(node_data n) {
		nodeHash.put(lastId, new Node( n.getKey(), n.getLocation(),  n.getWeight(), n.getInfo(), n.getTag() ));
		data_nodeHash.put(n.getKey(), n);
		lastId++;
		MC++;
	}

	@Override
	public void connect(int src, int dest, double w) {
		Edge e = new Edge(src, dest, w);
		if (nodeHash.get(e.getSrc()) != null && nodeHash.get(e.getDest()) != null ) {
			nodeHash.get(src).addDest(e);
			nodeHash.get(dest).addSource(e);
			edgeHash.put(e.getSrc(), e);
			MC++;
		}
	}

	@Override
	public Collection<node_data> getV() {
		return data_nodeHash.values();
	}

	@Override
	public Collection<edge_data> getE(int node_id) {
		return edgeHash.values();
	}

	@Override
	public node_data removeNode(int key) {
		MC++;
		Iterator<Edge> fromItr = nodeHash.get(key).fromIterator();
		while(fromItr.hasNext()) {
			Edge edge = fromItr.next(); 
			nodeHash.get(edge.getDest()).toThis.remove(edge.getSrc());
		}
		Iterator<Edge> toItr = nodeHash.get(key).toIterator();
		while(toItr.hasNext()) {
			Edge edge = toItr.next(); 
			nodeHash.get(edge.getSrc()).fromThis.remove(edge.getDest());
		}
		data_nodeHash.remove(key);
		return nodeHash.remove(key);
	}

	@Override
	public edge_data removeEdge(int src, int dest) {
		MC++;
		Edge edge = nodeHash.get(src).getDestEdge(dest);
		nodeHash.get(src).removeFromThis(edge);
		nodeHash.get(dest).removeToThis(edge);
		return edge;
	}

	@Override
	public int nodeSize() {
		return nodeHash.size();
	}

	@Override
	public int edgeSize() {
		return edgeHash.size();
	}

	@Override
	public int getMC() {
		return MC;
	}

}
