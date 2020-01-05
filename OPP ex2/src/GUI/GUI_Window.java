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
import java.io.FileInputStream;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import algorithms.Graph_Algo;
import dataStructure.DGraph;
import dataStructure.Node;
import dataStructure.edge_data;
import dataStructure.node_data;
import utils.Point3D;
import utils.Range;
import utils.StdDraw;

public class GUI_Window extends JFrame implements ActionListener, MouseListener, MouseMotionListener {
	private Graph_Algo Graph_Algo ;
	private DGraph graph = getGraph();
	private LinkedList<node_data> BoltedPath; int screenHeight = get_Height();
	private int screenWidth = get_Width();
	private static int Height ;
	private static int Width ;
	private static final Color edgeColor = new Color(50, 50, 50);
	private static final Color edgeTextColor = new Color(50, 200, 50);
	private double edgeRadX = get_Height()/10;
	private double edgeRadY = get_Width()/10;
	private static double edgeValsRd;
	private static node_data Moving_node;
	private static int MC;
	private static boolean isDrugde;
	private static final int Yborder = 70;
	private static final int Xborder = 20;
	private static int minY;
	private static int minX;
	private static int maxY;
	private static int maxX;

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
		this.Graph_Algo.graph = g ;
		this.repaint();
	}

	public void paint(Graphics g)  {
		g.setColor(new Color(255, 255, 255));
		g.fillRect(0, 0, get_Width(), get_Height());
//		g.drawLine(x1, y1, x2, y2);
		minX = 0;
		maxX = 0;
		minY = 0;
		maxY = 0;
		boolean b = true;
		if ( graph.nodeSize() != 0 ) {
			for ( Iterator<node_data> iterator = graph.getV().iterator() ; iterator.hasNext();) {
				node_data node = iterator.next();
				if(b) {
					minX = node.getLocation().ix();
					maxX = node.getLocation().ix();
					minY = node.getLocation().iy();
					maxY = node.getLocation().iy();
					b = false;
				} else {
					if (node.getLocation().ix() < minX ) {
						minX = node.getLocation().ix();
					} else if (node.getLocation().ix() > maxX) {
						maxX = node.getLocation().ix();
					}
					if (node.getLocation().iy() < minY ) {
						minY = node.getLocation().iy();
					} else if (node.getLocation().iy() > maxY) {
						maxY = node.getLocation().iy();
					}
				}

			}
		}
		for ( Iterator<node_data> iterator = this.Graph_Algo.graph.getV().iterator() ; iterator.hasNext();) {
			node_data node = iterator.next();
			drowNode(node, g);
			for (edge_data edge : graph.getE(node.getKey())) {
				drowEdge(edge, g);
			}
		}
	}

	private int ScaleY(double y) {
		return (int) (Yborder + (y - minY)*(this.get_Height() - Yborder-10)/maxY) ;
	}
	
	private int ScaleX(double x) {
		return (int) (Xborder + (x - minX)*(this.get_Width() - Xborder-10)/maxX) ;
	}
	
	private void drowEdge(edge_data edge, Graphics g) {
		Point3D sorce = graph.getNode(edge.getSrc()).getLocation();
		Point3D dest = graph.getNode(edge.getDest()).getLocation();
		g.setColor(edgeColor);
		g.drawLine(ScaleX(sorce.ix()), ScaleY(sorce.iy()), ScaleX(dest.ix()), ScaleY(dest.iy()));
		Point3D DirectionPoint = edgeDirectionPoint( sorce, dest);
		g.fillOval(ScaleX(DirectionPoint.x() ), ScaleY(DirectionPoint.y() ), 8, 8);
		g.setColor(edgeTextColor);  
		Point3D TextPoint = edgeTextPoint( sorce, dest);
		g.drawString(String.format("%.2f", edge.getWeight()), ScaleX(TextPoint.ix()), ScaleY(TextPoint.iy()));
	}
	private void drowNode(node_data node, Graphics g) {
		g.setColor(Color.MAGENTA);
		g.fillOval(ScaleX(node.getLocation().x()-1), ScaleY(node.getLocation().y()-1), 10, 10);
		g.setColor(edgeColor);
		g.drawString(""+node.getKey(), ScaleX((int) (node.getLocation().x())), ScaleY((int) (node.getLocation().y())));
	}

	private Point3D edgeDirectionPoint(Point3D srcPoint, Point3D destPoint) {
		return new Point3D((srcPoint.x()+7*destPoint.x())/8, (srcPoint.y()+7*destPoint.y())/8);
	}

	private Point3D edgeTextPoint( Point3D srcPoint, Point3D destPoint) {
		return new Point3D((srcPoint.x()*3+7*destPoint.x())/10, (srcPoint.y()*3+7*destPoint.y())/10);
	}

	

	private int get_Height() {
		return this.getHeight();
	}
	
	private int get_Width() {
		return this.getWidth();
	}

	private void init() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(true);
		this.setBackground(Color.WHITE);
		this.setTitle(" GUI ");

		this.Graph_Algo = new Graph_Algo();
		graph = new DGraph();
		
		Menu menu1 = new Menu("DGraph Algorithems");
		Menu menu2 = new Menu(" DGraph Actions");
		MenuBar menuBar = new MenuBar();
		this.setMenuBar(menuBar);

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

		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		
		isDrugde = false;
		this.setSize(800, 500);
		this.setVisible(true);
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
		DGraph d = DGraph.makeRandomGraph(6, 30);
//		Graph_Algo ga = new Graph_Algo();
		GUI_Window window = new GUI_Window(d);
//		int lastmc = 0;
//		while ( true ) {
//			if (lastmc != window.graph.getMC() ) {
////				System.out.println(frame.getHeight()+" 1 "+frame.getWidth());
//				synchronized (window.graph) {
//					frame.repaint();
//					System.out.println("repint");
//					lastmc = window.graph.getMC();
//				}
//			}
//			else {
////				System.out.println(frame.getHeight()+" 2 "+frame.getWidth());
//				try {
//					Thread.sleep(500);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				} 
//			}
//		}

	}

	@Override
	public void mouseDragged(MouseEvent event) {
		int x = event.getX();
		int y = event.getY();
		while (isDrugde) {
			Moving_node = new Node(Moving_node.getKey(), new Point3D(x, y));
			this.repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent event) {
		if(screenHeight != Height || screenWidth != Width) {
			this.repaint();
			Height = screenHeight;
			Width = screenWidth;
		}
	}

	@Override
	public void mouseClicked(MouseEvent event) {
//		this.BoltedPath = null;
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
		this.repaint();
	}

	@Override
	public void mouseReleased(MouseEvent event) {
		// TODO Auto-generated method stub
		if ( isDrugde = true)
			this.repaint();

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
				this.repaint();
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
			this.repaint();
		} else if (str.equals(" New DGraph ")) {
			graph = new DGraph();
			this.repaint();
		}  else if (str.equals(" Add Node ")) {
			Scanner s = new Scanner(System.in);
			System.out.print("Enter the x Value : ");
			int x = Integer.parseInt(s.next());
			System.out.print("Enter the y Value : ");
			int y = Integer.parseInt(s.next());
			s.close();
			// Z = 0
			graph.addNode( new Node(graph.newId(), new Point3D(x, y)));
			this.repaint();
		}  else if (str.equals(" Remove Node ")) {
			Scanner s = new Scanner(System.in);
			System.out.print("Enter the key Value : ");
			int key = Integer.parseInt(s.next());
			s.close();
			graph.removeNode(key);
			this.repaint();
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
			this.repaint();
		}  else if (str.equals(" Remove Edge ")) {
			Scanner s = new Scanner(System.in);
			System.out.print("Enter the suorce key : ");
			int src = Integer.parseInt(s.next());
			System.out.print("Enter the dest key : ");
			int dest = Integer.parseInt(s.next());
			s.close();
			graph.removeEdge(src, dest);
			this.repaint();
		} 

	}

}