package dataStructure;

import static org.junit.jupiter.api.Assertions.*;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.junit.jupiter.api.Test;
import utils.Point3D;

class DGraphTest {

	@Test
	void testDGraphInLessThen10Sec() {
//		System.out.println("***testDGraphInLessThen10Sec***");
//		long first = ZonedDateTime.now().toInstant().toEpochMilli();
//		DGraph g = new DGraph();
//		for (int i = 1; i < 1000000 ; i++) {
//			g.addNode(new Node(g.newId(), new Point3D(i, i)));
//			if (i >= 10) {
//				for (int j = 0; j < 10; j++) {
//					g.connect(i, j, i+j);
//				}
//			}
//		}
//		for (int i = 1; i < 11; i++) {
//			g.connect(i, i+10, 10*Math.sqrt(2));
//		}
//		long last = ZonedDateTime.now().toInstant().toEpochMilli();
//		System.out.println("Time in secends : "+((double)(last - first)/1000));
//		if ((last - first)/1000 > 10 ) {
//			fail("Should have happened in les then 10 secends ");
//		}
		//run in 2.48 secends  
	}
	
	@Test
	void testDGraphCollectionOfNodeCollectionOfEdge() {
		System.out.println("***testDGraphCollectionOfNodeCollectionOfEdge***");
		ArrayList<node_data> nodes = new ArrayList<node_data>();
		ArrayList<edge_data> edges = new ArrayList< edge_data>();
		for (int i = -10; i < 11; i++) {
			nodes.add(new Node(i, new Point3D(i, i*(1/2))));
		}  
		Iterator<node_data> it = nodes.iterator();
		while (it.hasNext()) {
			node_data n = it.next();
			if (n.getKey() != 0) {
				edges.add(new Edge(n.getKey(), 0, Math.abs(n.getKey()) ) );
			}
		}
		DGraph g = new DGraph(nodes, edges);
		System.out.println(g.edgeSize()+" = "+20);
		assertEquals(g.edgeSize() , 20);
		g.removeNode(0);
		System.out.println(g.edgeSize()+" = "+0);
		assertEquals(g.edgeSize() , 0);
		System.out.println(g.nodeSize()+" = "+19);
		assertEquals(g.nodeSize() , 19);
		assertNotEquals(g.edgeSize(), edges.size());
	}

	@Test
	void testCopyDGraph() {
		System.out.println("***testCopyDGraph***");
		DGraph g = new DGraph();
		for (int i = 0; i < 10 ; i++) {
			g.addNode(new Node(i, new Point3D(i, i)));
		}
		for (int i = 0; i < 10; i++) {
			g.connect(i, (i+1)%10, 1);
		}
		System.out.println("g.edgeSize = "+g.edgeSize());
		DGraph d = g.copy();
		System.out.println(d.edgeSize()+" = "+g.edgeSize());
		assertEquals(d.edgeSize() , g.edgeSize());
		System.out.println("g.edgeSize = "+g.edgeSize());
		System.out.println("d.edgeSize = "+d.edgeSize());
		d.removeEdge(0, 1);
		System.out.println(d.edgeSize()+" != "+g.edgeSize());
		assertNotEquals(d.edgeSize() , g.edgeSize());
	
	}

	@Test
	void testDGraphGraph() {
		System.out.println("***testDGraphGraph***");
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testGetNodeAndAddNode() {
		System.out.println("***testGetNodeAndAddNode***");
		DGraph g = new DGraph();
		for (int i = 0; i < 100; i+=5) {
			Node n = new Node(i, new Point3D(i, i+5));
			g.addNode(n);
			assertEquals(g.getNode(i), n);
			assertNotEquals(g.getNode(i), new Node(i, new Point3D(i, i)));
		}
		assertEquals(g.getV().size(), 20);
	}

	@Test
	void testGetEdge() {
		System.out.println("***testGetEdge***");
		DGraph g = new DGraph();
		for (int i = 0; i < 10 ; i++) {
			g.addNode(new Node(i, new Point3D(i, i)));
		}
		for (int i = 0; i < 10; i++) {
			g.connect(i, (i+1)%10, 1);
			assertEquals(g.getEdge(i, (i+1)%10), new Edge(i, (i+1)%10, 1));
			assertNotEquals(g.getEdge(i, (i+2)%10), new Edge(i, (i+1)%10, 1));
		}
	}

	@Test
	void testConnect() {
		System.out.println("***testConnect***");
		DGraph g = new DGraph();
		for (int i = 0; i < 10 ; i++) {
			g.addNode(new Node(i, new Point3D(i, i)));
		}
		for (int i = 0; i < 10; i++) {
			g.connect(i, (i+1)%10, 1);
		}
		assertEquals(g.getEdge(0, 1) ,new Edge(0, 1, 1));
		assertNotEquals(g.getEdge(0, 2) , new Edge(0, 1, 1));
	}

	@Test
	void testGetV() {
		System.out.println("***testGetV***");
		DGraph g = new DGraph();
		for (int i = 0; i < 10; i++) {
			g.addNode(new Node(i, new Point3D(i, i)));
		}
		assertEquals(g.getV().size(), 10);
		for (node_data n : g.getV()) {
			System.out.println(n.getKey()+"  "+n.getLocation());
			assertEquals(n, new Node(n.getKey(), new Point3D(n.getKey(), n.getKey())));
		}
	}

	@Test
	void testGetE() {
		System.out.println("***testGetE***");
		this.testCopyDGraph();
	}

	@Test
	void testRemoveNodeAndEdge() {
		System.out.println("***testRemoveNodeAndEdge***");
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testNodeSizeAndEdgeSize() {
		System.out.println("***testNodeSizeAndEdgeSize***");
		DGraph g = new DGraph();
		for (int i = 0; i < 10 ; i++) {
			g.addNode(new Node(i, new Point3D(i, i)));
		}
		for (int i = 0; i < 10; i++) {
			g.connect(i, (i+1)%10, 1);
		}
		System.out.println("g.edgeSize = "+g.edgeSize());
		DGraph d = g.copy();
		System.out.println(d.edgeSize()+" = "+g.edgeSize());
		assertEquals(d.edgeSize() , g.edgeSize());
		System.out.println("g.edgeSize = "+g.edgeSize());
		System.out.println("d.edgeSize = "+d.edgeSize());
		d.removeEdge(0, 1);
		System.out.println(d.edgeSize()+" != "+g.edgeSize());
		assertNotEquals(d.edgeSize() , g.edgeSize());
	}

	@Test
	void testGetMC() {
		System.out.println("***testGetMC***");
		DGraph g = new DGraph();
		int MC0 = g.getMC();
		g.addNode(new Node(0, new Point3D(0, 0)));
		int MC1 = g.getMC();
		g.addNode(new Node(1, new Point3D(1, 1)));
		int MC2 = g.getMC();
		int MC3 = g.getMC();
		assertNotEquals(MC0,MC1);
		assertEquals(MC2,MC3);
	}

}
