package dataStructure;

import static org.junit.jupiter.api.Assertions.*;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;

import org.junit.jupiter.api.Test;

import utils.Point3D;

class DGraphTest {

	@Test
	void testDGraphInLessThen10Sec() {
		System.out.println("***testDGraphInLessThen10Sec***");
		long first = ZonedDateTime.now().toInstant().toEpochMilli();
		DGraph g = new DGraph();
		for (int i = 1; i < 1000001 ; i++) {
			g.addNode(new Node(g.newId(), new Point3D(i, i)));
			if (i >= 10) {
				for (int j = 0; j < 10; j++) {
					g.connect(i, j, i+j);
				}
			}
		}
		for (int i = 1; i < 11; i++) {
			g.connect(i, i+10, 10*Math.sqrt(2));
		}
		long last = ZonedDateTime.now().toInstant().toEpochMilli();
		System.out.println("Time in secends : "+((double)(last - first)/1000));
		if ((last - first)/1000 > 10 ) {
			fail("Should have happened in les then 10 secends ");
		}
	}
	
	@Test
	void testDGraphCollectionOfNodeCollectionOfEdge() {
		DGraph g = new DGraph();
		HashMap<Integer, node_data> nodeHash = new HashMap<Integer, node_data>();
		Hashtable<Integer, edge_data> edgeHash = new Hashtable<Integer, edge_data>();
		for (int i = -10; i < 10; i++) {
			nodeHash.put(i, new Node(i, new Point3D(i, i*(1/2))));
		}  
		Iterator<node_data> it = nodeHash.values().iterator();
		while (it.hasNext()) {
			Node n = (Node) it.next();
			if (n.getKey() != 0) {
				edgeHash.put(n.getKey(), new Edge(n.getKey(), 0, Math.abs(n.getKey())));
			}
		}
		g = new DGraph(nodeHash.values(), edgeHash.values());
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testDGraphDGraph() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testDGraphGraph() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testDGraph() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testGetNodeAndAddNode() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testGetRealNode() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testGetEdge() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testConnect() {
//		DGraph g = new DGraph();
//		g.addNode(new Node(g.newId(), new Point3D(0, 0), 1, "", 0));
//		g.addNode(new Node(g.newId(), new Point3D(1, 1), 1, "", 0));
//		g.connect(1, 2, 1*Math.sqrt(2));
		DGraph g = new DGraph();
		for (int i = 0; i < 10 ; i++) {
			g.addNode(new Node(g.newId(), new Point3D(i, i)));
		}
		for (int i = 0; i < 10; i++) {
			g.connect(i, i+10, 10*Math.sqrt(2));
		}
	}

	@Test
	void testGetV() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testGetE() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testRemoveNodeAndEdge() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testNodeSizeAndEdgeSize() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testGetMC() {
		fail("Not yet implemented"); // TODO
	}

}
