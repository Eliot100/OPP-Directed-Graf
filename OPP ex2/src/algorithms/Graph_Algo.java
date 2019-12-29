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
		if( g.nodeSize() < 2)
			return true;
		Iterator<node_data> itr = g.getV().iterator();
		node_data n = itr.next();
		this.achivedAll(n);
		while (itr.hasNext()) {
			node_data temp = itr.next();
			if (!temp.getInfo().equals("Achieved"))
					return false;
			if (this.shortestPath(temp.getKey(), n.getKey()) == null) {
				return false;
			}
		}
		return true;
	}
	/**
	 * This function is sets all the nodes, that can by reached from n, infos to be "Achieved".
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

	@Override
	public double shortestPathDist(int src, int dest) {
		LinkedList<node_data> path = (LinkedList<node_data>) this.shortestPath(src, dest);
		if (path == null) {
			throw new RuntimeException("It isn't posibol to Reache dest node from src node");
		}
		Iterator<node_data> iterator = path.iterator();
		node_data lastNode_data; 
		if(iterator.hasNext()) {
			lastNode_data = (node_data) iterator.next();
		}
		else
			return 0;
		double sum = 0;
		while ( iterator.hasNext() ) {
			node_data node_data = (node_data) iterator.next();
			sum += g.getEdge(lastNode_data.getKey(), node_data.getKey()).getWeight();
			lastNode_data =node_data;
		}
		return sum;
	}

	@Override
	public List<node_data> shortestPath(int src, int dest) {
		node_data source = g.getNode(src);
		node_data destanation = g.getNode(dest);
		if(source == null || destanation == null)
			return null;
//		HashMap<Integer, node_data> hasReached = new HashMap<Integer, node_data>();
		HashMap<Integer, LinkedList<node_data>> pathes = new HashMap<Integer, LinkedList<node_data>>();
		ArrayList<Integer> onEdge = new ArrayList<Integer>();
		onEdge.add(source.getKey());
		pathes.put(source.getKey(), new LinkedList<node_data>());
		pathes.get(source.getKey()).add(source);
		for (Iterator<node_data> iterator = g.getV().iterator(); iterator.hasNext();) {
			node_data node = iterator.next();
			node.setInfo("notReached");
			node.setTag(g.getE(node.getKey()).size());
		}
		while (!onEdge.isEmpty()) {
			edge_data minWeight = null;
			for (Iterator<Integer> iterator = onEdge.iterator(); iterator.hasNext();) {
				node_data node = g.getNode(iterator.next());
				Iterator<edge_data> itr = g.getE(node.getKey()).iterator();
				while (itr.hasNext()) {
					edge_data e = itr.next();
					if (g.getNode(e.getDest()).getInfo() == "Reached")
						continue;
					if (minWeight == null || minWeight.getWeight() > e.getWeight() )
						minWeight = e;
				}
			}
			if (minWeight == null)
				return null;
			pathes.put(minWeight.getDest(), copyAndAdd(pathes.get(minWeight.getSrc()), g.getNode(minWeight.getDest()) ));
			if(minWeight.getDest() == dest)
				return pathes.get(minWeight.getDest());
			g.getNode(minWeight.getDest()).setInfo("Reached");
			g.getNode(minWeight.getSrc()).setTag(g.getNode(minWeight.getSrc()).getTag()-1);
			if (g.getNode(minWeight.getSrc()).getTag() == 0) {
				onEdge.remove(minWeight.getSrc());
			}
		}
		return null;
	}
	/** TODO
	 * @param path 
	 * @param n
	 * @return
	 */
	private static LinkedList<node_data> copyAndAdd(LinkedList<node_data> path, node_data n){
		LinkedList<node_data> newPath = new LinkedList<node_data>();
		for (Iterator<node_data> iter = path.iterator(); iter.hasNext();) {
			newPath.add(iter.next()); 
		}
		newPath.add(n); 
		return newPath;
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
