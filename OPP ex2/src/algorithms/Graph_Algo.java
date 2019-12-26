package algorithms;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.google.gson.Gson;
import dataStructure.DGraph;
import dataStructure.Node;
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
		if(g.nodeHash.values().size() <= 1)
			return true;
		Iterator<Node> itr = g.nodeHash.values().iterator();
		ArrayList<Node> achivd = new ArrayList<Node> ();
		ArrayList<Node> notAchivd = new ArrayList<Node> ();
		achivd.add(itr.next());
		while(itr.hasNext()) {
			notAchivd.add(itr.next());
		}
		// TODO Auto-generated method stub
		
		
		
		
		return false;
	}

	@Override
	public double shortestPathDist(int src, int dest) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<node_data> shortestPath(int src, int dest) {
		// TODO Auto-generated method stub
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
