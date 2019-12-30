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
import java.util.Scanner;

import com.google.gson.Gson;
import dataStructure.DGraph;
import dataStructure.Edge;
import dataStructure.Node;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;
import utils.Point3D;
/**
 * This class represents the set of graph-theory algorithms.
 * @author Eli Ruvinov
 */
public class Graph_Algo implements graph_algorithms{
	public DGraph graph;
	
	public static void main(String[] arr) {
		Graph_Algo ga = new Graph_Algo();
		Graph_Algo ga2 = new Graph_Algo();
		DGraph d = new DGraph();
		int action = 11;
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
		ga.init(d);
		ga.save("2.txt");
		ga2.init("2.txt");
		System.out.println(""+ga.isConnected());
	}
	
	@Override
	public void init(graph g) {
		this.graph = new DGraph(g);
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
			DG_params params = gson.fromJson(st0, DG_params.class);
//			for (int i = 0; i < 10; i++) {
//				System.out.println(params.nodeHash.get(i));
//			}
			this.graph = new DGraph(params.lastId, params.MC, params.nodeHash, params.edgeHash, params.edgeHashSize);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	private class DG_params {
		private int lastId;
		private int MC;
		private HashMap<Integer, Node> nodeHash;
		private HashMap<Integer, HashMap<Integer, Edge>> edgeHash;
		private int edgeHashSize;
		
		public DG_params(int lastId, int MC, HashMap<Integer, Node> nodeHash, 
				HashMap<Integer, HashMap<Integer, Edge>> edgeHash, int edgeHashSize) {
			this.lastId = lastId;
			this.MC = MC;
			this.nodeHash = nodeHash;
			this.edgeHash = edgeHash;
			this.edgeHashSize = edgeHashSize;
		}
	}
	
	private DGraph graphFromParams(DG_params p) {
		DGraph d = new DGraph(p.lastId, p.MC, p.nodeHash, p.edgeHash, p.edgeHashSize);
		return d;
	}

	@Override
	public void save(String file_name) {
		try {
			File DGraph_JASON = new File(file_name);
			PrintWriter pw = new PrintWriter(new FileWriter(DGraph_JASON));
			pw.println(new Gson().toJson(this.graph));
			pw.close();
		}catch (IOException e) {
			e.printStackTrace();
		} 
	}

	@Override
	public boolean isConnected() {
		if( graph.nodeSize() < 2)
			return true;
		Iterator<node_data> itr = graph.getV().iterator();
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
		for (edge_data e : graph.getE(n.getKey())) {
			if(!graph.getNode(e.getDest()).getInfo().equals("Achieved")) {
				graph.getNode(e.getDest()).setInfo("Achieved");
				this.achivedAll(graph.getNode(e.getDest()));
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
			sum += graph.getEdge(lastNode_data.getKey(), node_data.getKey()).getWeight();
			lastNode_data =node_data;
		}
		return sum;
	}

	@Override
	public List<node_data> shortestPath(int src, int dest) {
		node_data source = graph.getNode(src);
		node_data destanation = graph.getNode(dest);
		if(source == null || destanation == null)
			return null;
//		HashMap<Integer, node_data> hasReached = new HashMap<Integer, node_data>();
		HashMap<Integer, LinkedList<node_data>> pathes = new HashMap<Integer, LinkedList<node_data>>();
		ArrayList<Integer> onEdge = new ArrayList<Integer>();
		onEdge.add(source.getKey());
		pathes.put(source.getKey(), new LinkedList<node_data>());
		pathes.get(source.getKey()).add(source);
		for (Iterator<node_data> iterator = graph.getV().iterator(); iterator.hasNext();) {
			node_data node = iterator.next();
			node.setInfo("notReached");
			node.setTag(graph.getE(node.getKey()).size());
		}
		while (!onEdge.isEmpty()) {
			edge_data minWeight = null;
			for (Iterator<Integer> iterator = onEdge.iterator(); iterator.hasNext();) {
				node_data node = graph.getNode(iterator.next());
				Iterator<edge_data> itr = graph.getE(node.getKey()).iterator();
				while (itr.hasNext()) {
					edge_data e = itr.next();
					if (graph.getNode(e.getDest()).getInfo() == "Reached")
						continue;
					if (minWeight == null || minWeight.getWeight() > e.getWeight() )
						minWeight = e;
				}
			}
			if (minWeight == null)
				return null;
			pathes.put(minWeight.getDest(), copyAndAdd(pathes.get(minWeight.getSrc()), graph.getNode(minWeight.getDest()) ));
			if(minWeight.getDest() == dest)
				return pathes.get(minWeight.getDest());
			graph.getNode(minWeight.getDest()).setInfo("Reached");
			graph.getNode(minWeight.getSrc()).setTag(graph.getNode(minWeight.getSrc()).getTag()-1);
			if (graph.getNode(minWeight.getSrc()).getTag() == 0) {
				boolean flag = false;
				for (int i = 0; i < onEdge.size() && !flag ; i++) {
					if (onEdge.get(i).equals(minWeight.getSrc())) {
						onEdge.remove(i);
						flag = true;
					}
				}
				
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
		return new DGraph(graph);
	}

}
