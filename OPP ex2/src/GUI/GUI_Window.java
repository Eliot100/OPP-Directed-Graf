package GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

import javax.swing.JFrame;

import algorithms.Graph_Algo;
import dataStructure.DGraph;
import dataStructure.Node;
import dataStructure.edge_data;
import dataStructure.node_data;
import utils.Point3D;
import utils.Range;
import utils.StdDraw;

public class GUI_Window implements ActionListener, MouseListener, MouseMotionListener {
	private Graph_Algo Graph_Algo ;
	private DGraph graph = getGraph();
	private LinkedList<node_data> BoltedPath;

	private static Graphics2D screen;
	private static int screenHeight = get_Height();
	private static int screenWidth = get_Width();
	private static int Height ;
	private static int Width ;
	private static double nodeRd;
	private static double nodeTextWi;
	private static double edgeWi;
	private static final Color edgeColor = new Color(50, 50, 50);
	private static final Color edgeTextColor = new Color(50, 200, 50);
//	private static final Color edgePointColor = Color.BLUE;//new Color(edgeTextColor.getRed() + 20, edgeTextColor.getGreen() + 20, edgeTextColor.getBlue() + 20);
	private static double edgeRadX = get_Height()/100;
	private static double edgeRadY = get_Width()/100;
	private static double edgeValsRd;
	private static JFrame frame;
	private static node_data Moving_node;
	private static int MC;
	private static boolean isDrugde;

	public GUI_Window() {
		init();
	}
	
	private DGraph getGraph() {
		if(Graph_Algo != null)
			return Graph_Algo.graph;
		else 
			return null;
	}

	public GUI_Window( DGraph g) {
		init();
		this.graph = g ;
		frame.repaint();
	}

	private void paint(Graphics g)  {
		for ( Iterator<node_data> iterator = this.Graph_Algo.graph.getV().iterator() ; iterator.hasNext();) {
			node_data node = iterator.next();
			drowNode(node, g);
			for (edge_data edge : graph.getE(node.getKey())) {
				drowEdge(edge, g);
			}
		}
		frame.repaint();
	}
	
	private void drowEdge(edge_data edge, Graphics g) {
		Point3D sorce = graph.getNode(edge.getSrc()).getLocation();
		Point3D dest = graph.getNode(edge.getDest()).getLocation();
		g.setColor(edgeColor);
		g.drawLine(sorce.ix(), sorce.iy(), dest.ix(), dest.iy());
		Point3D DirectionPoint = edgeDirectionPoint( sorce, dest);
		g.fillOval((int) (DirectionPoint.x() - edgeRadX), (int) (DirectionPoint.y() - edgeRadY), (int) (2 * edgeRadX), (int) (2 * edgeRadY));
		g.setColor(edgeTextColor);  
		Point3D TextPoint = edgeTextPoint( sorce, dest);
		g.drawString(String.format("%.2f", edge.getWeight()), TextPoint.ix(), TextPoint.iy());
	}

	private Point3D edgeDirectionPoint(Point3D srcPoint, Point3D destPoint) {
		return new Point3D((srcPoint.x()+7*destPoint.x())/8, (srcPoint.y()+7*destPoint.y())/8);
	}

	private Point3D edgeTextPoint( Point3D srcPoint, Point3D destPoint) {
		return new Point3D((srcPoint.x()*3+7*destPoint.x())/10, (srcPoint.y()*3+7*destPoint.y())/10);
	}

	private void drowNode(node_data node, Graphics g) {
		g.setColor(Color.MAGENTA);
		g.fillOval((int) (node.getLocation().x()), (int) (node.getLocation().y()), (int) (2 * edgeRadX), (int) (2 * edgeRadY));
		g.setColor(edgeColor);
		g.drawString(""+node.getKey(), (int) (node.getLocation().x()), (int) (node.getLocation().y()+(get_Height()/250)));
	}

	private static int get_Height() {
		if(frame != null)
			return frame.getHeight();
		else 
			return 0;
	}
	
	private static int get_Width() {
		if(frame != null)
			return frame.getWidth();
		else 
			return 0;
	}

	private void init() {
		if (frame != null) frame.setVisible(false);
		frame = new JFrame();
		frame.setTitle(" GUI ");
		frame.setSize(1000, 500);
		frame.setBackground(new Color(255, 255, 255));
		frame.setResizable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.Graph_Algo = new Graph_Algo();
		graph = new DGraph();
		
		Menu menu1 = new Menu("DGraph Algorithems");
		Menu menu2 = new Menu(" DGraph Actions");
		MenuBar menuBar = new MenuBar();
		frame.setMenuBar(menuBar);

		menuBar.add(menu2);
		menuBar.add(menu1);
		String[] algoFunctions = {" Save DGraph to file ", " init DGraph from file ", " shortestPath ", " TSP "};
		String[] DGraphFunctions = {" New DGraph ", " Add Node ", " Remove Node ", " Add Edge ", " Remove Edge "};
		for (int i = 0; i < algoFunctions.length; i++) {
			MenuItem Item = new MenuItem(algoFunctions[i]);
			Item.addActionListener(this);
			menu1.add(Item);
		}
		for (int i = 0; i < DGraphFunctions.length; i++) {
			MenuItem Item = new MenuItem(DGraphFunctions[i]);
			Item.addActionListener(this);
			menu2.add(Item);
		}

		frame.addMouseListener(this);
		frame.addMouseMotionListener(this);
		
		isDrugde = false;
		frame.setVisible(true);
	}
	
//	public void paint(Graphics g) {
//		g.setColor(new Color(255, 255, 255));
//		g.fillRect(0, 0, get_Width(), get_Height());
////		g.drawLine(x1, y1, x2, y2);
//		double XMin = 0;
//		double XMax = 0;
//		double YMin = 0;
//		double YMax = 0;
//		boolean b = true;
//		if ( graph.nodeSize() != 0 ) {
//			for ( Iterator<node_data> iterator = graph.getV().iterator() ; iterator.hasNext();) {
//				node_data node = iterator.next();
//				if(b) {
//					XMin = node.getLocation().ix();
//					XMax = node.getLocation().ix();
//					YMin = node.getLocation().iy();
//					YMax = node.getLocation().iy();
//					b = false;
//				} else {
//					if (node.getLocation().ix() < XMin ) {
//						XMin = node.getLocation().ix();
//					} else if (node.getLocation().ix() > XMax) {
//						XMax = node.getLocation().ix();
//					}
//					if (node.getLocation().iy() < YMin ) {
//						YMin = node.getLocation().iy();
//					} else if (node.getLocation().iy() > YMax) {
//						YMax = node.getLocation().iy();
//					}
//				}
//
//			}
//
//			double dx = XMax-XMin;
//			double dy = YMax-YMin;
//			Range XRange = new Range(XMin-(dx/20), XMax+(dx/20));
//			Range YRange = new Range(YMin-(dy/10), YMax+(dy/10));
//			if (XRange.get_length() == 0) {
//				XRange = new Range(XRange.get_min()-1, XRange.get_max()+1);
//			} 
//			if (YRange.get_length() == 0) {
//				YRange = new Range(YRange.get_min()-0.5, YRange.get_max()+0.5);
//			} 
//
//			for (Iterator<node_data> iterator = graph.getV().iterator(); iterator.hasNext();) {
//				node_data node = iterator.next();
//				StdDraw.setPenRadius(nodeRd);
//				g.setColor(StdDraw.MAGENTA);
//				StdDraw.setPenColor(StdDraw.MAGENTA);
//				StdDraw.point(node.getLocation().x(), node.getLocation().y());
//				StdDraw.setPenRadius(nodeTextWi);
//				StdDraw.setPenColor(new Color(200, 30, 30));
//				StdDraw.text(node.getLocation().x(), node.getLocation().y()+(dy/25), ""+node.getKey());
//				for (edge_data edge : graph.getE(node.getKey())) {
//					Point3D destP = graph.getNode(edge.getDest()).getLocation();
//					StdDraw.setPenRadius(edgeWi);
//					StdDraw.setPenColor(new Color(50, 50, 50));
//					
////					StdDraw.line(node.getLocation().x(), node.getLocation().y(), destP.x(), destP.y());
//
//					g.drawLine((int) node.getLocation().x(), (int) node.getLocation().y(), (int) destP.x(), (int) destP.y());
//					
//					StdDraw.setPenRadius(edgeValsRd);
//					StdDraw.setPenColor(StdDraw.BLUE);
//					StdDraw.point((node.getLocation().x()+7*destP.x())/8, (node.getLocation().y()+7*destP.y())/8);
//					StdDraw.text((node.getLocation().x()*3+7*destP.x())/10, (node.getLocation().y()*3+7*destP.y())/10, String.format("%.2f", edge.getWeight()));
//				}
//			}
//		}
//	}

	public static void main(String[] args) {
		DGraph d = DGraph.makeRandomGraph(20, 7);
//		Graph_Algo ga = new Graph_Algo();
		GUI_Window window = new GUI_Window();
		window.graph = new DGraph(d);
		frame.repaint();
		int lastmc = 0;
		while ( true ) {
			if (lastmc != window.graph.getMC() ) {
				synchronized (window.graph) {

					lastmc = window.graph.getMC();
				}
			}
			else {
				try {
//					System.out.println(frame.getHeight()+"  "+frame.getWidth());
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} 
			}
		}

	}

	@Override
	public void mouseDragged(MouseEvent event) {
		int x = event.getX();
		int y = event.getY();
		while (isDrugde) {
			Moving_node = new Node(Moving_node.getKey(), new Point3D(x, y));
			frame.repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent event) {
		if(screenHeight != Height || screenWidth != Width) {
			frame.repaint();
			Height = screenHeight;
			Width = screenWidth;
		}
	}

	@Override
	public void mouseClicked(MouseEvent event) {
		this.BoltedPath = null;
		//		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent event) {
		int x = event.getX();
		int y = event.getY();
		Point3D tmp = new Point3D(x, y);
		int min_dist = (int) (5 * 1.);
		double best_dist = 1000000;
		for (node_data node : graph.getV()) {
			double dist = tmp.distance3D(node.getLocation());
			if (dist < min_dist && dist < best_dist) {
				best_dist = dist;
				Moving_node = new Node(node.getKey(), new Point3D(x, y));
				node = Moving_node;
			}
		}
		if (Moving_node == null) {
			Moving_node = new Node(graph.newId(), tmp);
			graph.addNode(Moving_node);
		}
		else {
			// TODO new node
			graph.addNode(new Node(graph.newId(), new Point3D(x, y)));
		}
		isDrugde = true;
		frame.repaint();
	}

	@Override
	public void mouseReleased(MouseEvent event) {
		// TODO Auto-generated method stub
		if ( isDrugde = true)
			frame.repaint();

		isDrugde = false;
		Moving_node = null;
	}

	@Override
	public void actionPerformed(ActionEvent event) {

		String str = event.getActionCommand();

		if (str.equals(" Save DGraph to file ")) {
			Scanner s = new Scanner(System.in);
			System.out.print("Enter the output file name : ");
			String fileName = s.next();
			this.Graph_Algo.save(fileName );
			s.close();
		} else if (str.equals(" init DGraph from file ")) {
			try {
				Scanner s = new Scanner(System.in);
				System.out.print("Enter the input file name : ");
				String fileName = s.next();
				this.Graph_Algo.init(fileName );
				frame.repaint();
				s.close();
			} catch (Exception e) {
				System.out.print("file not found.");
			}

		} else if (str.equals(" shortestPath ")) {
			Scanner s = new Scanner(System.in);
			System.out.print("Plase insert the key of the sorce node : ");
			int srcKey = Integer.parseInt(s.next());
			System.out.print("Plase insert the key of the destination node : ");
			int destKey = Integer.parseInt(s.next());
			if (this.Graph_Algo.shortestPath(srcKey, destKey) != null) {
				double pathWight = this.Graph_Algo.shortestPathDist(srcKey, destKey);
				System.out.println("Shortest path Destination is : "+pathWight);
			} else { //exist
				System.out.println("I am sorry. Such path dosen't exist.");
			}
			s.close();
			frame.repaint();
		} else if (str.equals(" New DGraph ")) {
			graph = new DGraph();
			frame.repaint();
		}  else if (str.equals(" Add Node ")) {
			Scanner s = new Scanner(System.in);
			System.out.print("Enter the x Value : ");
			int x = Integer.parseInt(s.next());
			System.out.print("Enter the y Value : ");
			int y = Integer.parseInt(s.next());
			s.close();
			// Z = 0
			graph.addNode( new Node(graph.newId(), new Point3D(x, y)));
			frame.repaint();
		}  else if (str.equals(" Remove Node ")) {
			Scanner s = new Scanner(System.in);
			System.out.print("Enter the key Value : ");
			int key = Integer.parseInt(s.next());
			s.close();
			graph.removeNode(key);
			frame.repaint();
		}  else if (str.equals(" Add Edge ")) {
			Scanner s = new Scanner(System.in);
			System.out.print("Enter the suorce key : ");
			int src = Integer.parseInt(s.next());
			System.out.print("Enter the dest key : ");
			int dest = Integer.parseInt(s.next());
			System.out.print("Enter the edge wight : ");
			double w = Double.parseDouble(s.next());
			s.close();
			graph.connect(src, dest, w);
			frame.repaint();
		}  else if (str.equals(" Remove Edge ")) {
			Scanner s = new Scanner(System.in);
			System.out.print("Enter the suorce key : ");
			int src = Integer.parseInt(s.next());
			System.out.print("Enter the dest key : ");
			int dest = Integer.parseInt(s.next());
			s.close();
			graph.removeEdge(src, dest);
			frame.repaint();
		} 

	}

}
