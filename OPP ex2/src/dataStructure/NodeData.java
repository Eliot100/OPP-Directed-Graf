package dataStructure;
import java.util.HashMap;
import java.util.Iterator;

import utils.Point3D;

public class NodeData implements node_data {
	private int key;
	private Point3D location;
	private double weight;
	private String info;
	private int tag;
	HashMap <Integer, EdgeData> fromThis;
	HashMap <Integer , EdgeData> toThis;
	
	Iterator<EdgeData> fromIterator(){
		return fromThis.values().iterator();
	}
	
	Iterator<EdgeData> toIterator(){
		return toThis.values().iterator();
	}
	
	public NodeData(int key, Point3D location, double weight, String info, int tag) {
		this.key = key;
		this.location = location;
		this.weight = weight;
		this.info = info;
		this.tag = tag;
		this.fromThis = new HashMap <Integer, EdgeData>();
		this.toThis = new HashMap <Integer, EdgeData>();
	}
	
	public void addDest(int dest, EdgeData edge) {
		fromThis.put(dest, edge);
	}
	
	public void removeDest(int dest, EdgeData edge) {
		fromThis.remove(dest);
	}
	
	public void addSource(int source , EdgeData edge) {
		toThis.put(source, edge);
	}
	
	public void removeSource(int source, EdgeData edge) {
		toThis.remove(source);
	}
	
	public EdgeData getDestEdge(int dest) {
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
