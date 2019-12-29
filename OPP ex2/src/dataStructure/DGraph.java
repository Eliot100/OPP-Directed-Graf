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
	private int lastId;
	private int MC;
	private HashMap<Integer, node_data> nodeHash;
	private HashMap<Integer, HashMap<Integer, edge_data>> edgeHash;
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
	 * @param node_dataCollection
	 * @param edge_dataCollection
	 */
	public DGraph( Collection<node_data> node_dataCollection, Collection<edge_data> edge_dataCollection) {
		int biggestID = -1; 
		MC = 0;
		nodeHash = new HashMap<Integer, node_data>();
		edgeHash = new HashMap<Integer, HashMap<Integer, edge_data>>();
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
		int biggestID = 0; 
		MC = g.getMC();
		nodeHash = new HashMap<Integer, node_data>();
		edgeHash = new HashMap<Integer, HashMap<Integer, edge_data>>();
		Iterator<node_data> nodeIter = g.getV().iterator(); 
		while (nodeIter.hasNext()) {
			node_data Node =  nodeIter.next();
			nodeHash.put(Node.getKey(), Node);
			edgeHash.put(Node.getKey(), new HashMap<Integer, edge_data>());
			Iterator<edge_data> edgeIter = g.getE(Node.getKey()).iterator(); 
			while (edgeIter.hasNext()) {
				edge_data edge = edgeIter.next();
				edgeHash.get(Node.getKey()).put(edge.getDest(), edge);
			}
			if(Node.getKey() > biggestID)
				biggestID = Node.getKey();
		}
		lastId = biggestID;
	}
	/**
	 *  This is a constructor for a new empty DGraph (directed graph).
	 */
	public DGraph() {
		lastId = 0;
		MC = 0;
		nodeHash = new HashMap<Integer, node_data>();
		edgeHash = new HashMap<Integer, HashMap<Integer, edge_data>>();
	}
	
	@Override
	public node_data getNode(int key) {
		return nodeHash.get(key);
	}
//	/**
//	 * return the Node by the node_id,
//	 * @param key - the node_id
//	 * @return the Node by the node_id, null if none.
//	 */
//	public Node getRealNode(int key) {
//		return (Node) this.getNode(key);
//	}

	@Override
	public edge_data getEdge(int src, int dest) {
		return edgeHash.get(src).get(dest);
	}

	@Override
	public void addNode(node_data n) {
		nodeHash.put(n.getKey(), n);
		edgeHash.put(n.getKey(), new HashMap<Integer, edge_data>());
		if(lastId < n.getKey())
			lastId = n.getKey();
		MC++;
	}

	@Override
	public void connect(int src, int dest, double w) {
		Edge e = new Edge(src, dest, w);
		if (nodeHash.get(e.getSrc()) != null && nodeHash.get(e.getDest()) != null ) {
			edgeHash.get(e.getSrc()).put(dest, e);
		}
		MC++;
	}

	@Override
	public Collection<node_data> getV() {
		return nodeHash.values();
	}

	@Override
	public Collection<edge_data> getE(int node_id) {
		return edgeHash.get(node_id).values();
	}

	@Override
	public node_data removeNode(int key) {
		MC++;
		edgeHash.remove(key);
		Iterator<HashMap<Integer, edge_data>> itr = edgeHash.values().iterator();
		while (itr.hasNext()) {
			HashMap<Integer, edge_data> hashMap = itr.next();
			hashMap.remove(key);
		}
		return nodeHash.remove(key);
	}

	@Override
	public edge_data removeEdge(int src, int dest) {
		MC++; 
		return edgeHash.get(src).remove(dest);
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
