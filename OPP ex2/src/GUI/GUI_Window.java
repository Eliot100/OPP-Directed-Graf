package GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import algorithms.Graph_Algo;
import dataStructure.DGraph;
import dataStructure.Node;
import dataStructure.edge_data;
import dataStructure.node_data;
import utils.Point3D;
import utils.Range;
import utils.StdDraw;

public class GUI_Window extends JFrame implements ActionListener, MouseListener, MouseMotionListener{
	private final static double nodeRd = 0.03;
	private final static double nodeTextWi = 0.036;
	private final static double edgeWi = 0.003;
	private final static double edgeValsRd = 0.02;
	private static node_data Moving_node;
	private static int MC;
	private Graph_Algo Graph_Algo ;
	private static boolean isDrugde = false;
	private LinkedList<node_data> BoltedPath;
	
	public GUI_Window() {
		this.Graph_Algo = new Graph_Algo();
		init();
	}
	
	private void init() {
//		this.setSize(1000, 500);
		StdDraw.setCanvasSizeGUIDGraph(1000, 500);
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        MenuBar menuBar = new MenuBar();
        this.setMenuBar(menuBar);
        
//        Menu menu = new Menu("Menu");
//        menuBar.add(menu);
//        MenuItem item1 = new MenuItem("simpleTriangle");
//        item1.addActionListener(this);
//
//        MenuItem item2 = new MenuItem("clean-up");
//        item2.addActionListener(this);
//        
//        menu.add(item1);
//        menu.add(item2);
        // ***************************************************
        Menu menu2 = new Menu("DGraph_Algo");
		menuBar.add(menu2);
		MenuItem Item1 = new MenuItem(" Save DGraph to file ");
		Item1.addActionListener(this);
		menu2.add(Item1);
		MenuItem Item2 = new MenuItem(" init DGraph from file ");
		Item2.addActionListener(this);
		menu2.add(Item2);
		MenuItem Item3 = new MenuItem(" Cheack isConnected ");
		Item3.addActionListener(this);
		menu2.add(Item3);
		MenuItem Item4 = new MenuItem(" shortestPathDist ");
		Item4.addActionListener(this);
		menu2.add(Item4);
		MenuItem Item5 = new MenuItem(" shortestPath ");
		Item5.addActionListener(this);
		menu2.add(Item5);
		MenuItem Item6 = new MenuItem(" TSP ");
		Item6.addActionListener(this);
		menu2.add(Item6);

		menu2.add(Item1);
		menu2.add(Item2);
		menu2.add(Item3);
		menu2.add(Item4);
		menu2.add(Item5);
		menu2.add(Item6);

        this.addMouseListener(this);
        this.addMouseMotionListener(this);
//		if (frame != null) frame.setVisible(false);
//		frame = new JFrame();
//		offscreenImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
//		onscreenImage  = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
//		offscreen = offscreenImage.createGraphics();
//		onscreen  = onscreenImage.createGraphics();
//		setXscale();
//		setYscale();
//		offscreen.setColor(DEFAULT_CLEAR_COLOR);
//		offscreen.fillRect(0, 0, width, height);
//		setPenColor();
//		setPenRadius();
//		setFont();
//		clear();
//
//		// add antialiasing
//		RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
//				RenderingHints.VALUE_ANTIALIAS_ON);
//		hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
//		offscreen.addRenderingHints(hints);
//
//		// frame stuff
//		ImageIcon icon = new ImageIcon(onscreenImage);
//		JLabel draw = new JLabel(icon);
//
//		draw.addMouseListener(std);
//		draw.addMouseMotionListener(std);
//
//		frame.setContentPane(draw);
//		frame.addKeyListener(std);    // JLabel cannot get keyboard focus
//		frame.setResizable(false);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);            // closes all windows
//		// frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);      // closes only current window
//		frame.setTitle("Standard Draw");
//		frame.setJMenuBar(createGUIDGraphMenuBar());
//		frame.pack();
//		frame.requestFocusInWindow();
//		this.setVisible(true);
	}
	private void paint() {
        
		this.init();
		double XMin = 0;
		double XMax = 0;
		double YMin = 0;
		double YMax = 0;
		boolean b = true;
		for ( Iterator<node_data> iterator = this.Graph_Algo.graph.getV().iterator() ; iterator.hasNext();) {
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
		StdDraw.setXscale(XRange.get_min(), XRange.get_max());
		StdDraw.setYscale(YRange.get_min(), YRange.get_max());
		
		for (Iterator<node_data> iterator = this.Graph_Algo.graph.getV().iterator(); iterator.hasNext();) {
			node_data node = iterator.next();
			StdDraw.setPenRadius(nodeRd);
			StdDraw.setPenColor(StdDraw.MAGENTA);
			StdDraw.point(node.getLocation().x(), node.getLocation().y());
			StdDraw.setPenRadius(nodeTextWi);
			StdDraw.setPenColor(new Color(200, 30, 30));
			StdDraw.text(node.getLocation().x(), node.getLocation().y()+(dy/25), ""+node.getKey());
			for (edge_data edge : this.Graph_Algo.graph.getE(node.getKey())) {
				Point3D destP = this.Graph_Algo.graph.getNode(edge.getDest()).getLocation();
				StdDraw.setPenRadius(edgeWi);
				StdDraw.setPenColor(new Color(50, 50, 50));
				StdDraw.line(node.getLocation().x(), node.getLocation().y(), destP.x(), destP.y());
				StdDraw.setPenRadius(edgeValsRd);
				StdDraw.setPenColor(StdDraw.BLUE);
				StdDraw.point((node.getLocation().x()+7*destP.x())/8, (node.getLocation().y()+7*destP.y())/8);
				StdDraw.text((node.getLocation().x()*3+7*destP.x())/10, (node.getLocation().y()*3+7*destP.y())/10, String.format("%.2f", edge.getWeight()));
			}
		}
	}
	
	public static void main(String[] args) {
		DGraph d = DGraph.makeRandomGraph(20, 7);
		Graph_Algo ga = new Graph_Algo();
		GUI_Window window = new GUI_Window();
		window.Graph_Algo.graph = new DGraph(d);
		window.paint();
//		int lastmc = 0;
//		while ( true ) {
//			if (lastmc != window.Graph_Algo.graph.getMC() ) {
//				synchronized (window.Graph_Algo.graph) {
//			
//					lastmc = window.Graph_Algo.graph.getMC();
//				}
//			}
//			else {
//				try {
//					Thread.sleep(1000);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				} 
//			}
//		}
		
	}
	
	private class makeCanvas implements Runnable {

		@Override
		public void run() {
			
			
		}
	}

	@Override
	public void mouseDragged(MouseEvent event) {
		int x = event.getX();
        int y = event.getY();
        while (isDrugde) {
        	Moving_node = new Node(Moving_node.getKey(), new Point3D(x, y));
            repaint();
        }
	}

	@Override
	public void mouseMoved(MouseEvent event) {
		// do nothing
	}

	@Override
	public void mouseClicked(MouseEvent event) {
		this.BoltedPath = null;
		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent event) {
		// TODO Auto-generated method stub

		repaint();
	}

	@Override
	public void mouseExited(MouseEvent event) {
		// TODO Auto-generated method stub

		repaint();
	}

	@Override
	public void mousePressed(MouseEvent event) {
		int x = event.getX();
        int y = event.getY();
        Point3D tmp = new Point3D(x, y);
        int min_dist = (int) (5 * 1.);
        double best_dist = 1000000;
        for (node_data node : this.Graph_Algo.graph.getV()) {
        	double dist = tmp.distance3D(node.getLocation());
            if (dist < min_dist && dist < best_dist) {
                best_dist = dist;
                Moving_node = new Node(node.getKey(), new Point3D(x, y));
                node = Moving_node;
            }
		}
        if (Moving_node == null) {
        	Moving_node = new Node(Moving_node.getKey(), tmp);
        }
        else {
        	// TODO new node
        	this.Graph_Algo.graph.addNode(new Node(this.Graph_Algo.graph.newId(), new Point3D(x, y)));
        }
        isDrugde = true;
        repaint();
	}

	@Override
	public void mouseReleased(MouseEvent event) {
		// TODO Auto-generated method stub
        isDrugde = false;
        Moving_node = null;
		repaint();
	}

	@SuppressWarnings("unused")// for Eclipse warning
	@Override
	public void actionPerformed(ActionEvent event) {
		try {
		 String str = event.getActionCommand();

	        if (str.equals(" Save DGraph to file ")) {
	        	Scanner s = new Scanner(System.in);
	        	System.out.print("Enter the output file name : ");
	        	String fileName = s.next();
	        	this.Graph_Algo.save(fileName );
	            s.close();
	        } else if (str.equals(" init DGraph from file ")) {
	        	Scanner s = new Scanner(System.in);
	        	System.out.print("Enter the input file name : ");
	        	String fileName = s.next();
	        	this.Graph_Algo.init(fileName );
	            repaint();
	            s.close();
	        } else if (str.equals(" Cheack isConnected ")) {
	        	System.out.print("The directed graph that in the GUI ");
	        	if (this.Graph_Algo.isConnected()) {
	        		System.out.println("is Connected.");
	        	} else {
	        		System.out.println("isn't Connected.");
	        	}
	            repaint();
	        } else if (str.equals(" shortestPathDist ")) {
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
	            repaint();
	        } else if (str.equals(" TSP ")) {
	        	
	            repaint();
	        } 
		} catch (Exception e) {
			System.out.println("What you enterd wasn't suitabl to the qwastion \\ action.");
		}
	}
}
