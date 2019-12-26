package dataStructure;

import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
public class DGraph implements graph{
	private static int lastId;
	private int MC;
	private HashMap<Integer, NodeData> nodeHash;
	private Hashtable<Integer, EdgeData> edgeHash;
	
//	private Iterator<EdgeData> edgeIterator(){
//		return edgeHash.values().iterator();
//	}
//	private Iterator<NodeData> nodeIterator(){
//		return nodeHash.values().iterator();
//	}
	
//	private static int newId() {
//		lastId++;
//		return lastId;
//	}
	
	public DGraph() {
		lastId = 0;
		nodeHash = new HashMap<Integer, NodeData>();
		MC = 0;
	}
	
	@Override
	public node_data getNode(int key) {
		return (node_data) nodeHash.get(key);
	}

	@Override
	public edge_data getEdge(int src, int dest) {
		return nodeHash.get(src).getDestEdge(dest);
	}

	@Override
	public void addNode(node_data n) {
		nodeHash.put(lastId, new NodeData( n.getKey(), n.getLocation(), 
				n.getWeight(), n.getInfo(), n.getTag() ));
		lastId++;
		MC++;
	}

	@Override
	public void connect(int src, int dest, double w) {
		EdgeData edge = new EdgeData(src, dest, w);
		nodeHash.get(src).addDest(dest, edge);
		nodeHash.get(dest).addSource(src, edge);
		MC++;
	}

	@Override
	public Collection<node_data> getV() {
		// TODO Auto-generated method stub
		return null;
//		return nodeHash.values();
	}

	@Override
	public Collection<edge_data> getE(int node_id) {
		// TODO Auto-generated method stub
		return null;
//		return edgeHash.values();
	}

	@Override
	public node_data removeNode(int key) {
		MC++;
		Iterator<EdgeData> fromItr = nodeHash.get(key).fromIterator();
		while(fromItr.hasNext()) {
			EdgeData edge = fromItr.next(); 
			nodeHash.get(edge.getDest()).toThis.remove(edge.getSrc());
		}
		Iterator<EdgeData> toItr = nodeHash.get(key).toIterator();
		while(toItr.hasNext()) {
			EdgeData edge = toItr.next(); 
			nodeHash.get(edge.getSrc()).fromThis.remove(edge.getDest());
		}
		return nodeHash.remove(key);
	}

	@Override
	public edge_data removeEdge(int src, int dest) {
		MC++;
		EdgeData edge = nodeHash.get(src).getDestEdge(dest);
		nodeHash.get(src).removeDest(dest, edge);
		nodeHash.get(dest).removeSource(src, edge);
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
