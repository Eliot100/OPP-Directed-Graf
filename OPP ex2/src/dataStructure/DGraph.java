package dataStructure;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import utils.Point3D;
/**
 * This class is implementation of directed graph.
 * @author Eli Ruvinov
 */
public class DGraph implements graph {
	private int lastId;
	private int MC;
	private HashMap<Integer, node_data> nodeHash;
	private HashMap<Integer, HashMap<Integer, edge_data>> edgeHash;
	private int edgeHashSize;
	
//	public DGraph(int lastId, int MC, HashMap<Integer, node_data> nodeHash, 
//			HashMap<Integer, HashMap<Integer, edge_data>> edgeHash, int edgeHashSize) {
//		this.lastId = lastId;
//		this.MC = MC;
//		this.nodeHash = nodeHash;
//		this.edgeHash = edgeHash;
//		this.edgeHashSize = edgeHashSize;
//	}
	/**
	 * TODO
	 * @return
	 */
	public int newId() {
		lastId++;
		return lastId;
	}
	
	public static DGraph main(int action) {
		DGraph d = new DGraph();
		if(action == 1) {
			for (int i = 0; i < 1000; i++) {
				if(i%2 == 1)
					d.addNode(new Node(d.newId(), new Point3D(0, i)));
				else
					d.addNode(new Node(d.newId(), new Point3D(i, 0)));
			}
			for (int i = 1; i < 1000; i++) {
				d.connect(i, i+1, 1);
			}
		} else {
			for (int i = 0; i < 100; i++) {
				d.addNode(new Node( i, new Point3D(i, i)));
			}
			for (int i = 0; i < 100; i++) {
				d.connect(i, i+1, i/2);
			}
		}
		return d;
	}
//	/**
//	 * 
//	 * @param node_dataCollection
//	 * @param edge_dataCollection
//	 */
//	public DGraph( Collection<node_data> node_dataCollection, Collection<edge_data> edge_dataCollection) {
//		int biggestID = -1; 
//		MC = 0;
//		nodeHash = new HashMap<Integer, node_data>();
//		edgeHash = new HashMap<Integer, HashMap<Integer, edge_data>>();
//		Iterator<node_data> nodeIter = node_dataCollection.iterator();
//		while (nodeIter.hasNext()) {
//			Node NodeData = new Node(nodeIter.next());
//			nodeHash.put(NodeData.getKey(), NodeData);
//			if(NodeData.getKey() > biggestID)
//				biggestID = NodeData.getKey();
//		}
//		lastId = biggestID;
//		Iterator<edge_data> edgeIter = edge_dataCollection.iterator();
//		while (edgeIter.hasNext()) {
//			edge_data e = edgeIter.next();
//			this.connect(e.getSrc(), e.getDest(), e.getWeight());
//		}
//	}
//	/**
//	 *  This is a constructor for a DGraph from a DGraph.
//	 * @param g - the DGraph which 
//	 */
//	public DGraph copy() {
//		DGraph d = new DGraph();
//		d.lastId = this.lastId;
//		d.MC = this.MC;
//		Iterator<node_data> nodeIter = this.nodeHash.values().iterator();
//		while (nodeIter.hasNext()) {
//			Node Node = (Node) nodeIter.next();
//			d.nodeHash.put(Node.getKey(), Node);
//		}
//		Iterator<node_data> nodeIter2 = this.getV().iterator(); 
//		while (nodeIter2.hasNext()) {
//			Node Node = new Node( nodeIter2.next());
//			Iterator<edge_data> edgeIter = this.getE(Node.getKey()).iterator();
//			while (edgeIter.hasNext()) {
//				edge_data edge = edgeIter.next();
//				d.connect(edge.getSrc(), edge.getDest(), edge.getWeight());
//			}
//		}
//		return d;
//	}
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
			if(Node.getKey() > biggestID)
				biggestID = Node.getKey();
		}
		lastId = biggestID;
		Iterator<node_data> nodeIter2 = g.getV().iterator(); 
		while (nodeIter2.hasNext()) {
			node_data Node =  nodeIter2.next();
			Iterator<edge_data> edgeIter = g.getE(Node.getKey()).iterator(); 
			while (edgeIter.hasNext()) {
				edge_data edge = edgeIter.next();
				this.connect(edge.getSrc(), edge.getDest(), edge.getWeight());
				
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
		edgeHash = new HashMap<Integer, HashMap<Integer, edge_data>>();
	}
	
	public DGraph(int lastId, int MC, HashMap<Integer, Node> nodeHash, HashMap<Integer, HashMap<Integer, Edge>> edgeHash, int edgeHashSize) {
		this.lastId = lastId;
		this.MC = MC;
		this.edgeHashSize = edgeHashSize;
		this.nodeHash = new HashMap<Integer, node_data>();
		this.edgeHash = new HashMap<Integer, HashMap<Integer, edge_data>> ();
		for (node_data node : nodeHash.values()) {
			System.out.println(node.getLocation());
			this.nodeHash.put(node.getKey(), node);
			System.out.println(nodeHash.get(node.getKey()).getLocation());
			this.edgeHash.put(node.getKey(), new HashMap<Integer, edge_data>());
			edgeHash.get(node.getKey());
			for (edge_data edge : edgeHash.get(node.getKey()).values()) {
				System.out.println(edge);
				this.edgeHash.get(edge.getSrc()).put(edge.getDest(), edge);

				System.out.println(this.getEdge(edge.getSrc(), edge.getDest()));
			}
		}
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
	public synchronized void addNode(node_data n) {
		nodeHash.put(n.getKey(), n);
		edgeHash.put(n.getKey(), new HashMap<Integer, edge_data>());
		if(lastId < n.getKey())
			lastId = n.getKey();
		MC++;
	}

	@Override
	public synchronized void connect(int src, int dest, double w) {
		if (nodeHash.get(src) != null && nodeHash.get(dest) != null ) {
			Edge e = new Edge(src, dest, w);
			edgeHash.get(src).put(dest, e);
		}
		MC++;
		edgeHashSize++;
	}

	@Override
	public synchronized Collection<node_data> getV() {
		return nodeHash.values();
	}

	@Override
	public synchronized Collection<edge_data> getE(int node_id) {
		return edgeHash.get(node_id).values();
	}

	@Override
	public synchronized node_data removeNode(int key) {
		MC++;
		edgeHashSize -= edgeHash.get(key).size();
		edgeHash.remove(key);
		Iterator<HashMap<Integer, edge_data>> itr = edgeHash.values().iterator();
		while (itr.hasNext()) {
			HashMap<Integer, edge_data> hashMap = itr.next();
			hashMap.remove(key);
			edge_data edge = hashMap.remove(key);
			if (edge != null)
				edgeHashSize--;
		}
		return nodeHash.remove(key);
	}

	@Override
	public synchronized edge_data removeEdge(int src, int dest) {
		MC++; 
		edge_data edge = edgeHash.get(src).remove(dest);
		if (edge != null)
			edgeHashSize--;
		return edge;
	}

	@Override
	public int nodeSize() {
		return nodeHash.size();
	}

	@Override
	public int edgeSize() {
		return edgeHashSize;
	}

	@Override
	public int getMC() {
		return MC;
	}

}
