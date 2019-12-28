package dataStructure;

import java.util.HashMap;
import java.util.Iterator;
import utils.Point3D;
/**
 * This class is represent the Node in the directed graph.
 * @author Eli Ruvinov
 */
public class Node implements node_data  {
	private int key;
	private Point3D location;
	private double weight;
	private String info;
	private int tag;
	public HashMap <Integer, Edge> fromThis;
	public HashMap <Integer, Edge> toThis;
	/**
	 * 
	 */
	public boolean equals(Object n) {
		if(!(n instanceof Node))
			return false;
		if(((Node) n).key != key)
			return false;
		if(!((Node) n).location.equals(location))
			return false;
		if(((Node) n).weight != weight)
			return false;
		if(((Node) n).tag != tag)
			return false;
		if(((Node) n).info != info)
			return false;
		Iterator<Edge> itr = ((Node) n).fromIterator();
		while (itr.hasNext()) {
			Edge edge = itr.next();
			if(!edge.equals(this.fromThis.get(edge.getDest())))
				return false;
		}
		Iterator<Edge> itr2 = ((Node) n).toIterator();
		while (itr2.hasNext()) {
			Edge edge = itr2.next();
			if(!edge.equals(this.fromThis.get(edge.getSrc())))
				return false;
		}
		return true;
	}
	/**
	 * @return Iterator on the edges that exit from this Node.
	 */
	Iterator<Edge> fromIterator(){
		return fromThis.values().iterator();
	}
	/**
	 * @return Iterator on the edges that enter to this Node.
	 */
	Iterator<Edge> toIterator(){
		return toThis.values().iterator();
	}
	/**
	 * This is a constructor for a Node.
	 */
	public Node(int key, Point3D location) {
		this.key = key;
		this.location = location;
		this.info = "";
		this.tag = new Integer(0);
		this.weight = new Double(0.0);
	}
	/**
	 * This is a constructor for a Node.
	 */
	public Node(int key, Point3D location, double weight, String info, int tag) {
		this.key = key;
		this.location = location;
		this.weight = weight;
		this.info = info;
		this.tag = tag;
	}
	public Node(node_data n) {
		this.info = n.getInfo();
		this.key = n.getKey();
		this.location = n.getLocation();
		this.tag = n.getTag();
		this.weight = n.getWeight();
		this.fromThis = new HashMap<Integer,Edge>();
		this.toThis = new HashMap<Integer,Edge>();
	}
	/**
	 * This function is adding an edge that enter this Node. 
	 * @param edge - Edge which you want to add.
	 */
	public void addDest(Edge edge) {
		if(edge.getSrc() == this.getKey())
			fromThis.put(edge.getDest(), edge);
	}
	/**
	 * This returning the edge between this Node and the destination
	 * @param dest - Integer that represent the destination.
	 * @return an edge from this Node to the destination. 
	 */
	public Edge getDestEdge(int dest) {
		return this.fromThis.get(dest);
	}
	/**
	 * This function is adding an edge that enter this Node. 
	 * @param edge - Edge which you want to add.
	 */
	public void addSource(Edge edge) {
		if(edge.getDest() == this.getKey())
			this.toThis.put(edge.getSrc(), edge);
	}
	/**
	 * This function is removing an edge that exit this Node. 
	 * @param edge - Edge which you want to remove.
	 */
	public void removeFromThis(Edge edge) {
		if(edge.getDest() == this.getKey())
			this.fromThis.remove(edge.getDest());
	}
	/**
	 * This function is removing an edge that enter this Node.
	 * @param edge - Edge which you want to remove.
	 */
	public void removeToThis(Edge edge) {
		if(edge.getDest() == this.getKey())
			this.toThis.remove(edge.getSrc());
	}
	
	@Override
	public int getKey() {
		return this.key;
	}

	@Override
	public Point3D getLocation() {
		return this.location;
	}

	@Override
	public void setLocation(Point3D p) {
		this.location = p;
	}

	@Override
	public double getWeight() {
		return this.weight;
	}

	@Override
	public void setWeight(double w) {
		this.weight = w;
	}

	@Override
	public String getInfo() {
		return this.info;
	}

	@Override
	public void setInfo(String s) {
		this.info = s;
	}

	@Override
	public int getTag() {
		return this.tag;
	}

	@Override
	public void setTag(int t) {
		this.tag = t;
	}
}
