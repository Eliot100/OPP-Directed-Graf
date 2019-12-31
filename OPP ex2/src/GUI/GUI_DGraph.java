package GUI;

import java.util.Iterator;

import dataStructure.DGraph;
import dataStructure.edge_data;
import dataStructure.node_data;
import utils.Point3D;
import utils.Range;
import utils.StdDraw;

public class GUI_DGraph implements Runnable {
	
	public static void newCanvas(DGraph graph) {
		StdDraw.setCanvasSize(1000, 500);
		double XMin = 0;
		double XMax = 0;
		double YMin = 0;
		double YMax = 0;
		for (Iterator<node_data> iterator = graph.getV().iterator(); iterator.hasNext();) {
			node_data node = iterator.next();
			if (node.getLocation().ix() < XMin ) {
				XMin = node.getLocation().ix();
			} else if (node.getLocation().ix() > XMax) {
				XMax = node.getLocation().ix();
			}
			if (node.getLocation().iy() < YMin ) {
				YMin = node.getLocation().iy();
			} else if (node.getLocation().iy() > YMax) {
				YMax = node.getLocation().iy();
			}
		}
		double dx = XMax-XMin;
		double dy = YMax-YMin;
		Range XRange = new Range(XMin-(dx/80), XMax+(dx/80));
		Range YRange = new Range(YMin-(dy/80), YMax+(dy/80));
		if (XRange.get_length() == 0) {
			XRange = new Range(XRange.get_min()-1, XRange.get_max()+1);
		} 
		if (YRange.get_length() == 0) {
			YRange = new Range(YRange.get_min()-0.5, YRange.get_max()+0.5);
		} 
		StdDraw.setXscale(XRange.get_min(), XRange.get_max());
		StdDraw.setYscale(YRange.get_min(), YRange.get_max());
		int MC = graph.getMC();
		for (Iterator<node_data> iterator = graph.getV().iterator(); iterator.hasNext();) {
			node_data node = iterator.next();
			StdDraw.setPenRadius(0.05);
			StdDraw.point(node.getLocation().x(), node.getLocation().y());
			for (edge_data edge : graph.getE(node.getKey())) {
				Point3D destP = graph.getNode(edge.getDest()).getLocation();
				StdDraw.line(node.getLocation().x(), node.getLocation().y(), destP.x(), destP.y());
			}
		}
	}
	
	@Override
	public void run() {
		
	}

}
