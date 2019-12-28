package algorithms;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import com.google.gson.Gson;
import dataStructure.DGraph;
import dataStructure.Edge;
import dataStructure.Node;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;
/**
 * This class represents the set of graph-theory algorithms.
 * @author Eli Ruvinov
 */
public class Graph_Algo implements graph_algorithms{
	private DGraph g;
	
	@Override
	public void init(graph g) {
		this.g = new DGraph(g);
	}

	@Override
	public void init(String file_name) {
		try {
			File DGraph_JASON = new File(file_name);
			BufferedReader br = new BufferedReader(new FileReader(DGraph_JASON));
			String st0 = "";
			String st;
			while((st = br.readLine()) != null) {
				st0 += st;
			}
			br.close();
			Gson gson = new Gson();
			this.g = gson.fromJson(st0, DGraph.class);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	@Override
	public void save(String file_name) {
		try {
			File DGraph_JASON = new File(file_name);
			PrintWriter pw = new PrintWriter(new FileWriter(DGraph_JASON));
			pw.println(new Gson().toJson(this.g));
			pw.close();
		}catch (IOException e) {
			e.printStackTrace();
		} 
	}

	@Override
	public boolean isConnected() {
		if(g.nodeHash.values().size() < 2)
			return true;
		Iterator<Node> itr = g.nodeHash.values().iterator();
		Node n = itr.next();
		this.achivedAll(n);
		while (itr.hasNext()) {
			Node temp = itr.next();
			if (!temp.getInfo().equals("Achieved"))
					return false;
		}
		return this.could(n);
	}
	/**
	 * This function is sets all the nodes (that can by reached from n) infos to be "Achieved".
	 * @param n - is the start node_data. 
	 */
	private void achivedAll(node_data n) {
		for (edge_data e : g.getE(n.getKey())) {
			if(!g.getNode(e.getDest()).getInfo().equals("Achieved")) {
				g.getNode(e.getDest()).setInfo("Achieved");
				this.achivedAll(g.getNode(e.getDest()));
			}
		}
	}
	/**
	 * @return true if it possible to reached to n from all the Nodes in the DGraph g, else false.
	 * @param n - the node that we want to check if it possible to reached from all the Nodes in the DGraph g.
	 */
	private boolean could(Node n) {
		n.setInfo("could");
		for (Iterator<Edge> iterator = n.toThis.values().iterator(); iterator.hasNext();) {
			Edge e = iterator.next();
			Node could = g.getRealNode(e.getSrc());
			if(!could.getInfo().equals("could")) {
				could.setInfo("could");
				this.could(could);
			}
		}
		for (Node node : g.nodeHash.values()) {
			if(!node.getInfo().equals("could"))
				return false;
		}
		return true;
	}

	@Override
	public double shortestPathDist(int src, int dest) {
		LinkedList<node_data> path = (LinkedList<node_data>) shortestPath(src, dest);
		double sum = 0;
		Iterator<node_data> iterator = path.iterator();
		node_data lastNode_data; 
		if(iterator.hasNext()) {
			lastNode_data = (node_data) iterator.next();
			sum += lastNode_data.getWeight();
		}
		else
			return 0;
		while ( iterator.hasNext()) {
			node_data node_data = (node_data) iterator.next();
			sum += node_data.getWeight();
			sum += this.g.nodeHash.get(lastNode_data.getKey()).fromThis.get(node_data.getKey()).getWeight();
			lastNode_data =node_data;
		}
		return sum;
	}

	@Override
	public List<node_data> shortestPath(int src, int dest) {
		Node source = g.nodeHash.get(src);
		Node destanation = g.nodeHash.get(dest);
		if(source == null || destanation == null)
			return null;
		HashMap<Integer, Node> hasReached = new HashMap<Integer, Node>();
		HashMap<Integer, LinkedList<node_data>> pathes = new HashMap<Integer, LinkedList<node_data>>();
		ArrayList<Node> onEdge = new ArrayList<Node>();
		onEdge.add(source);
		for (Iterator<Node> iterator = g.nodeHash.values().iterator(); iterator.hasNext();) {
			Node node = iterator.next();
			node.setTag(node.fromThis.size());
		}
		while (!onEdge.isEmpty()) {
			Edge minWeight = null;
			for (Iterator<Node> iterator = onEdge.iterator(); iterator.hasNext();) {
				Node node = iterator.next();
				Iterator<Edge> itr = node.fromThis.values().iterator();
				while (itr.hasNext()) {
					Edge e = itr.next();
					if (hasReached.get(e.getDest()) != null)
						continue;
					if (minWeight == null || minWeight.getWeight() > e.getWeight() )
						minWeight = e;
				}
			}
			
			// TODO Auto-generated method stub
		}
		return null;
	}

	@Override
	public List<node_data> TSP(List<Integer> targets) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public graph copy() {
		return new DGraph(g);
	}

}
