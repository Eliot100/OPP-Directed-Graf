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
	int lastId;
	int MC;
	HashMap<Integer, node_data> nodeHash;
	Hashtable<Integer, edge_data> edgeHash;
	/**
	 * TODO
	 * @return
	 */
	public int newId() {
		lastId++;
		return lastId;
	}
	/**
	 * 
	 * @param c1
	 * @param c2
	 */
	public DGraph( Collection<node_data> node_dataCollection, Collection<edge_data> edge_dataCollection) {
		int biggestID = -1; 
		MC = 0;
		nodeHash = new HashMap<Integer, node_data>();
		edgeHash = new Hashtable<Integer, edge_data>();
		Iterator<node_data> nodeIter = node_dataCollection.iterator();
		while (nodeIter.hasNext()) {
			Node NodeData = new Node(nodeIter.next());
			nodeHash.put(NodeData.getKey(), NodeData);
			if(NodeData.getKey() > biggestID)
				biggestID = NodeData.getKey();
		}
		lastId = biggestID;
		Iterator<edge_data> edgeIter = edge_dataCollection.iterator();
		while (edgeIter.hasNext()) {
			edge_data e = edgeIter.next();
			this.connect(e.getSrc(), e.getDest(), e.getWeight());
		}
	}
	/**
	 *  This is a constructor for a DGraph from a DGraph.
	 * @param g - the DGraph which 
	 */
	public DGraph copy() {
		DGraph d = new DGraph();
		d.lastId = this.lastId;
		d.MC = this.MC;
		Iterator<node_data> nodeIter = this.nodeHash.values().iterator();
		while (nodeIter.hasNext()) {
			Node Node = (Node) nodeIter.next();
			d.nodeHash.put(Node.getKey(), Node);
		}
		Iterator<node_data> nodeIter2 = this.getV().iterator(); 
		while (nodeIter2.hasNext()) {
			Node Node = new Node( nodeIter2.next());
			Iterator<edge_data> edgeIter = this.getE(Node.getKey()).iterator();
			while (edgeIter.hasNext()) {
				edge_data edge = edgeIter.next();
				d.connect(edge.getSrc(), edge.getDest(), edge.getWeight());
			}
		}
		return d;
	}
	/**
	 *  This is a constructor for a DGraph from a graph.
	 * @param g
	 */
	public DGraph(graph g) {
		int biggestID = -1; 
		MC = g.getMC();
		Iterator<node_data> nodeIter = g.getV().iterator(); 
		while (nodeIter.hasNext()) {
			node_data Node =  nodeIter.next();
			nodeHash.put(Node.getKey(), Node);
			if(Node.getKey() > biggestID)
				biggestID = Node.getKey();
		}
		lastId = biggestID;
		Iterator<node_data> nodeIter2 = g.getV().iterator(); 
		while (nodeIter2.hasNext()) {
			node_data Node = nodeIter2.next();
			Iterator<edge_data> edgeIter = g.getE(Node.getKey()).iterator();
			while (edgeIter.hasNext()) {
				edge_data edge = edgeIter.next();
				this.connect(edge.getSrc(), edge.getDest(), edge.getWeight());
				edgeHash.put(edge.getSrc(), edge);
			}
		}
		
	}
	/**
	 *  This is a constructor for a new empty DGraph (directed graph).
	 */
	public DGraph() {
		lastId = 0;
		MC = 0;
		nodeHash = new HashMap<Integer, node_data>();
		edgeHash = new Hashtable<Integer, edge_data>();
	}
	
	@Override
	public node_data getNode(int key) {
		return nodeHash.get(key);
	}
	/**
	 * return the Node by the node_id,
	 * @param key - the node_id
	 * @return the Node by the node_id, null if none.
	 */
	public Node getRealNode(int key) {
		return (Node) this.getNode(key);
	}

	@Override
	public edge_data getEdge(int src, int dest) {
		return this.getRealNode(src).getDestEdge(dest);
	}

	@Override
	public void addNode(node_data n) {
		nodeHash.put(n.getKey(), new Node(n));
		if(lastId < n.getKey())
			lastId = n.getKey();
		MC++;
	}

	@Override
	public void connect(int src, int dest, double w) {
		Edge e = new Edge(src, dest, w);
		if (nodeHash.get(e.getSrc()) != null && nodeHash.get(e.getDest()) != null ) {
			this.getRealNode(src).addDest(e);
			this.getRealNode(dest).addSource(e);
			edgeHash.put(e.getSrc(), e);
		}
		MC++;
	}

	@Override
	public Collection<node_data> getV() {
		return nodeHash.values();
	}

	@Override
	public Collection<edge_data> getE(int node_id) {
		return this.getRealNode(node_id).getV();
	}

	@Override
	public node_data removeNode(int key) {
		MC++;
		Iterator<edge_data> fromItr = this.getRealNode(key).fromIterator();
		while(fromItr.hasNext()) {
			Edge edge = (Edge) fromItr.next(); 
			this.getRealNode(edge.getDest()).toThis.remove(edge.getSrc());
		}
		Iterator<edge_data> toItr = this.getRealNode(key).toIterator();
		while(toItr.hasNext()) {
			Edge edge = (Edge) toItr.next(); 
			this.getRealNode(edge.getSrc()).fromThis.remove(edge.getDest());
		}
		return nodeHash.remove(key);
	}

	@Override
	public edge_data removeEdge(int src, int dest) {
		MC++;
		Edge edge = (Edge) this.getRealNode(src).getDestEdge(dest);
		this.getRealNode(src).removeFromThis(edge);
		this.getRealNode(dest).removeToThis(edge);
		return this.edgeHash.remove(dest);
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
