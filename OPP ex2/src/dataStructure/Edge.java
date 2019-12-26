package dataStructure;
/**
 * This class represent a directed path 
 * between 2 Nodes in the directed graph.
 * @author Eli Ruvinov
 */
public class Edge implements edge_data {
	private int source;
	private int destination;
	private double weight;
	private String info;
	private int tag;
	/**
	 * This is a constructor for an Edge.
	 */
	public Edge(int src, int dest, double w ) {
		source = src;
		destination = dest;
		weight = w;
	}
	
	@Override
	public int getSrc() {
		return source;
	}

	@Override
	public int getDest() {
		return destination;
	}

	@Override
	public double getWeight() {
		return weight;
	}

	@Override
	public String getInfo() {
		return info;
	}

	@Override
	public void setInfo(String s) {
		this.info = s;
	}

	@Override
	public int getTag() {
		return tag;
	}

	@Override
	public void setTag(int t) {
		this.tag = t;
	}

}
