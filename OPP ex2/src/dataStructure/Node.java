package dataStructure;

import java.util.HashMap;
import java.util.Iterator;
import utils.Point3D;
/**
 * This class is represent the Node in the directed graph.
 * @author Eli Ruvinov
 */
public class Node implements node_data {
	private int key;
	private Point3D location;
	private double weight;
	private String info;
	private int tag;
	HashMap <Integer, Edge> fromThis;
	HashMap <Integer , Edge> toThis;
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
	public Node(int key, Point3D location, double weight, String info, int tag) {
		this.key = key;
		this.location = location;
		this.weight = weight;
		this.info = info;
		this.tag = tag;
		this.fromThis = new HashMap <Integer, Edge>();
		this.toThis = new HashMap <Integer, Edge>();
	}
	/**
	 * This function is adding an edge that enter this Node. 
	 * @param edge - Edge which you add.
	 */
	public void addDest(Edge edge) {
		fromThis.put(edge.getDest(), edge);
	}
	/**
	 * This function is removing an edge that exit this Node. 
	 * @param edge - Edge which you remove.
	 */
	public void removeFromThis(Edge edge) {
		fromThis.remove(edge.getDest());
	}
	/**
	 * This function is adding an edge that enter this Node. 
	 * @param edge - Edge which you add.
	 */
	public void addSource(Edge edge) {
		toThis.put(edge.getSrc(), edge);
	}
	/**
	 * This function is removing an edge that enter this Node.
	 * @param edge - Edge which you remove.
	 */
	public void removeToThis(Edge edge) {
		toThis.remove(edge.getSrc());
	}
	/**
	 * This returning the edge between this Node and the destination
	 * @param dest - Integer that represent the destination.
	 * @return an edge from this Node to the destination. 
	 */
	public Edge getDestEdge(int dest) {
		return this.fromThis.get(dest);
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
