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

public class GUI_Window extends JFrame implements ActionListener, MouseListener, MouseMotionListener {
	private Graph_Algo Graph_Algo ;
	private DGraph graph = getGraph();
	private LinkedList<node_data> BoltedPath;

//	private static Graphics2D screen;
	private int screenHeight = this.get_Height();
	private int screenWidth = this.get_Width();
	private static int Height ;
	private static int Width ;
//	private static double nodeRd;
//	private static double nodeTextWi;
//	private static double edgeWi;
	private static final Color edgeColor = new Color(50,50,50);
	private static final Color edgeTextColor = new Color(50,50,200);
//	private static final Color edgePointColor = Color.BLUE;//new Color(edgeTextColor.getRed() + 20, edgeTextColor.getGreen() + 20, edgeTextColor.getBlue() + 20);
	private double edgeRadX = this.get_Height()/100;
	private double edgeRadY = this.get_Width()/100;
//	private static double edgeValsRd;
//	private static JFrame frame;
	private static node_data Moving_node;
//	private static int MC;
	private static boolean isDrugde;

	private int XMin = 0;
	private int XMax = 0;
	private int YMin = 0;
	private int YMax = 0;
	private Range XRange;
	private Range YRange;
	private int YBorder = 50;

	private int scaledX(int x) {
		return (int) ((x-XRange.get_min())*get_Width()/XRange.get_length()) ;
	}
	
	private int scaledY(int y) {
		return (int) (YBorder+(y-YRange.get_min())*(get_Height()-YBorder)/YRange.get_length()) ;
	}
	
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
		this.setScale(g);
		this.Graph_Algo = new Graph_Algo();
		Graph_Algo.graph = g ;
		graph = Graph_Algo.graph;
		init();
//		this.repaint();
	}

	private void setScale(DGraph g) {
		boolean b = true;
		for ( Iterator<node_data> iterator = g.getV().iterator() ; iterator.hasNext();) {
			node_data node = iterator.next();
			if(b) {
				XMin = node.getLocation().ix();
				XMax = node.getLocation().ix();
				YMin = node.getLocation().iy();
				YMax = node.getLocation().iy();
				b = false;
			} else {
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
		}
		
		double dx = XMax-XMin;
		double dy = YMax-YMin;
		Range XRange = new Range(XMin-(dx/20), XMax+(dx/20));
		Range YRange = new Range(YMin-(dy/10), YMax+(dy/10));
		if (XRange.get_length() == 0) {
			XRange = new Range(XRange.get_min()-1, XRange.get_max()+1);
		} 
		if (YRange.get_length() == 0) {
			YRange = new Range(YRange.get_min()-0.5, YRange.get_max()+0.5);
		} 
		this.XRange = XRange;
		this.YRange = YRange;
	}

	public void paint(Graphics g)  {
		super.paint(g);
		g.clearRect(0, 0, this.get_Width(), this.get_Height());
		for ( Iterator<node_data> iterator = this.Graph_Algo.graph.getV().iterator() ; iterator.hasNext();) {
			node_data node = iterator.next();
			for (edge_data edge : graph.getE(node.getKey())) {
				drowEdge(edge, g);
			}
			drowNode(node, g);
		}
	}
	
	private void drowEdge(edge_data edge, Graphics g) {
		Point3D sorce = graph.getNode(edge.getSrc()).getLocation();
		Point3D dest = graph.getNode(edge.getDest()).getLocation();
		g.setColor(edgeColor);
		g.drawLine(scaledX(sorce.ix()), scaledY(sorce.iy()), scaledX(dest.ix()), scaledY(dest.iy()));
		Point3D DirectionPoint = edgeDirectionPoint( sorce, dest);
		g.fillOval(scaledX((int) (DirectionPoint.x()-1 )), scaledY((int) (DirectionPoint.y())-1), (int) ( get_Width()/100), (int) ( get_Height()/50));
		g.setColor(edgeTextColor);  
		Point3D TextPoint = edgeTextPoint( sorce, dest);
		g.drawString(String.format("%.2f", edge.getWeight()), scaledX(TextPoint.ix()), scaledY(TextPoint.iy()));
	}

	private Point3D edgeDirectionPoint(Point3D srcPoint, Point3D destPoint) {
		return new Point3D((srcPoint.x()+7*destPoint.x())/8, (srcPoint.y()+7*destPoint.y())/8);
	}

	private Point3D edgeTextPoint( Point3D srcPoint, Point3D destPoint) {
		return new Point3D((srcPoint.x()*3+7*destPoint.x())/10, (srcPoint.y()*3+7*destPoint.y())/10);
	}

	private void drowNode(node_data node, Graphics g) {
		g.setColor(Color.orange);
		g.fillOval(scaledX((int) (node.getLocation().x())-1), scaledY((int) (node.getLocation().y())-1), (int)  get_Width()/60, (int) get_Height()/30);
		g.setColor(edgeColor);
		g.drawString(""+node.getKey(), scaledX((int) (node.getLocation().x())), scaledY((int) (node.getLocation().y()+(get_Height()/250))));
	}

	private int get_Height() {
		return this.getHeight();
	}
	
	private int get_Width() {
		return this.getWidth();
	}

	private void init() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle(" GUI ");
		this.setBackground(Color.WHITE);
		this.setSize(600, 700);
		this.setResizable(true);
		
		Menu menu1 = new Menu(" DGraph Algorithems ");
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
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		DGraph d = DGraph.makeRandomGraph(7, 30);
		GUI_Window window = new GUI_Window(d);
		int lastmc = 0;
		while ( true ) {
			if (lastmc != window.graph.getMC() ) {
				synchronized (window.graph) {
					window.repaint();
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
//		while (isDrugde) {
//			int x = event.getX();
//			int y = event.getY();
//			Moving_node.setLocation(new Point3D(x, y));
//			this.repaint();
//		}
	}

	@Override
	public void mouseMoved(MouseEvent event) {
//		if(screenHeight != Height || screenWidth != Width) {
//			this.repaint();
//			Height = screenHeight;
//			Width = screenWidth;
//		}
	}

	@Override
	public void mouseClicked(MouseEvent event) {
//		this.BoltedPath = null;
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
//		int x = event.getX();
//		int y = event.getY();
//		Point3D tmp = new Point3D(x, y);
//		int min_dist = 20;
//		boolean b = true;
//		for (Iterator<node_data> itr = graph.getV().iterator(); itr.hasNext() && b;) {
//			node_data node = itr.next();
//			double dist = tmp.distance3D(node.getLocation());
//			if (dist < min_dist ) {
//				Moving_node = node;
//				b = false;
//			}
//		}
//		if (Moving_node == null) {
//			Moving_node = new Node(graph.newId(), tmp);
//			graph.addNode(Moving_node);
//		}
//		isDrugde = true;
//		this.repaint();
	}

	@Override
	public void mouseReleased(MouseEvent event) {
		// TODO Auto-generated method stub
//		if ( isDrugde == true)
//			this.repaint();
//
//		isDrugde = false;
//		Moving_node = null;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		try {
		String str = event.getActionCommand();

		if (str.equals(" Save DGraph to file ")) {
			Scanner s = new Scanner(System.in);
			System.out.print("Enter the output file name : ");
			String fileName = s.next();
			this.Graph_Algo.save(fileName);
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
			setScale(graph);
			this.repaint();
		}  else if (str.equals(" Remove Node ")) {
			Scanner s = new Scanner(System.in);
			System.out.print("Enter the key Value : ");
			int key = Integer.parseInt(s.next());
			s.close();
			graph.removeNode(key);
			setScale(graph);
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
		}catch (Exception e) {
			// TODO: handle exception
			System.out.print(e.toString()+"\n");
		}

	}

}
