package dataStructure;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Iterator;
import org.junit.jupiter.api.Test;
import utils.Point3D;

class NodeTest {

	@Test
	void testFromIterator() {
		System.out.println("***testFromIterator***");
		Node n = new Node(0, new Point3D(0, 0, 0));
		for (int i = 1; i < 11; i++) {
			n.addSource(new Edge(0, i, i/2));
			n.addDest(new Edge(-i, 0, i/2));
		}
		Iterator<Edge> itr = n.fromIterator();
		int i = 1;
		while (itr.hasNext()) {
			Edge edge = itr.next();
			System.out.println(edge);
			assertEquals(edge, new Edge(0, i, i/2));
			i++;
		}
		Iterator<Edge> itr2 = n.fromIterator();
		while (itr2.hasNext()) {
			Edge edge = itr.next();
			assertNotEquals(edge, new Edge(0, (int) (Math.random()*100), Math.random()*10));
			i++;
		}
	}

	@Test
	void testToIterator() {
		System.out.println("***testToIterator***");
		Node n = new Node(0, new Point3D(0, 0, 0));
		for (int i = 1; i < 11; i++) {
			n.addSource(new Edge(-i, 0, i/2));
			n.addDest(new Edge(0, i, i/2));
		}
		Iterator<Edge> itr = n.toIterator();
		int i = 1;
		while (itr.hasNext()) {
			Edge edge = itr.next();
			System.out.println(edge);
			assertEquals(edge, new Edge(-i, 0, (i/2)));
			i++;
		}
		Iterator<Edge> itr2 = n.toIterator();
		while (itr2.hasNext()) {
			Edge edge = itr2.next();
			assertNotEquals(edge, new Edge(0, (int) (Math.random()*100), Math.random()*10));
			i++;
		}
	}

	@Test
	void testNodeKeyLocation() {
		System.out.println("***testNodeKeyLocation***");
		Node[] NodeArr = {new Node(0, new Point3D(0, 0, 0)), new Node(2, new Point3D(0, 2)), new Node(3, new Point3D(3, 2)),
				  new Node(4, new Point3D(4, 1)), new Node(5, new Point3D(6, 6)), new Node(6, new Point3D(7, 4))};
		Node[] NodeArr2 = {new Node(0, new Point3D(0, 0 ,0)), new Node(2, new Point3D(0, 2)), new Node(3, new Point3D(3, 2)),
				  new Node(4, new Point3D(4, 1)), new Node(5, new Point3D(6, 6)), new Node(6, new Point3D(7, 4))};
		Node[] NotNodeArr = {new Node(1, new Point3D(0, 0)), new Node(2, new Point3D(5, 2)), new Node(0, new Point3D(3, 6)),
				  new Node(4, new Point3D(4, 4)), new Node(6, new Point3D(6, 6)), new Node(6, new Point3D(7, 2))};
		for (int i = 0; i < NodeArr.length; i++) {
			assertEquals(NodeArr[i], NodeArr2[i]);
			assertNotEquals(NodeArr[i], NotNodeArr[i]);
		}
	}

	@Test
	void testAddDest() {
		System.out.println("***testAddDest***");
		Node n = new Node(100, new Point3D(0, 0, 0));
		n.addDest(new Edge(100, 1,  95));
		n.addDest(new Edge(100, 50, 50.9));
		assertEquals(n.getDestEdge(1), new Edge(100, 1,  95));
		assertNotEquals(n.getDestEdge(50), new Edge(100, 50, 50));
	}

	@Test
	void testRemoveFromThis() {
		System.out.println("***testRemoveFromThis***");
		Node n = new Node(0, new Point3D(0, 0, 0));  
		Edge[] e = {new Edge(5, 0, 4), new Edge(2, 0, 1.5), new Edge(3, 0, 2), new Edge(4, 0, 3.5)};
		for (int i = 0; i < e.length; i++) {
			n.addDest(e[i]);
			System.out.println("Edge before removing : "+e[i]);
			n.removeFromThis(e[i]);
			if (n.fromThis.get(e[i].getSrc()) != null)
				fail("didn't remove : "+n.fromThis.get(e[i].getSrc()));
		}
	}

	@Test
	void testAddSource() {
		System.out.println("***testAddSource***");
		Node n = new Node(0, new Point3D(0, 0, 0));  
		Edge[] e = {new Edge(5, 0, 4), new Edge(2, 0, 1.5), new Edge(3, 0, 2), new Edge(4, 0, 3.5)};
		for (int i = 0; i < e.length; i++) {
			n.addSource(e[i]);
			assertEquals(n.toThis.get(e[i].getSrc()), e[i]);
			assertNotEquals(n.toThis.get(e[i].getSrc()), e[(i+1)%e.length]);
		}
	}

	@Test
	void testRemoveToThis() {
		System.out.println("***testRemoveToThis***");
		Node n = new Node(0, new Point3D(0, 0, 0));  
		Edge[] e = {new Edge(5, 0, 4), new Edge(2, 0, 1.5), new Edge(3, 0, 2), new Edge(4, 0, 3.5)};
		for (int i = 0; i < e.length; i++) {
			n.addSource(e[i]);
			System.out.println("Edge before removing : "+e[i]);
			n.removeToThis(e[i]);
			if (n.toThis.get(e[i].getSrc()) != null)
				fail("didn't remove : "+n.toThis.get(e[i].getSrc()));
		}
	}

	@Test
	void testGetDestEdge() {
		System.out.println("***testGetDestEdge***");
		Node n = new Node(0, new Point3D(0, 0, 0)); 
		Edge e = new Edge(0, 5, 4);
		n.addDest(e);
		assertEquals(n.getDestEdge(5), e);
		assertNotEquals(n.getDestEdge(3), e);
		assertNotEquals(n.getDestEdge(5), new Edge(0, 5, 2));
	}

	@Test
	void testGetKey() {
		System.out.println("***testGetKey***");
		Node n = new Node(0, new Point3D(0, 0, 0));
		System.out.println(n.getKey()+" = "+0); 
		assertEquals(n.getKey(), 0);
		System.out.print(n.getKey()+" != ");
		for (int i = 0; i < 25; i++) {
			int r = (int) (Math.random()*1000);
			if (r == 0)
				continue;
			System.out.print( r+", ");
			assertNotEquals(n.getKey(), r);
		}
		System.out.println();
	}

	@Test
	void testGetLocation() {
		System.out.println("***testGetLocation***");
		Node[] n = {new Node(0, new Point3D(0, 0, 0)), new Node(1, new Point3D(2, 3)), new Node(0, new Point3D(1, 2, 3))};
		Point3D[] Loc = {new Point3D(0, 0, 0), new Point3D(2, 3), new Point3D(1, 2, 3)};
		for (int i = 0; i < 3; i++) {
			System.out.println(n[i].getLocation()+" = "+Loc[i] );
			assertEquals(n[i].getLocation(), Loc[i]);
			int x = (int) (Math.random()*10);
			int y = (int) (Math.random()*10);
			int z = (int) (Math.random()*10);
			Loc[i].add(x, y, z);
			System.out.println(n[i].getLocation()+" != "+Loc[i] );
			assertNotEquals(n[i].getLocation(), Loc[i]);
		}
	}

	@Test
	void testSetLocation() {
		System.out.println("***testSetLocation***");
		Node n = new Node(0, new Point3D(0, 0, 0));
		for (int i = 0; i < 10; i++) {
			int r = (int) (Math.random()*1000);
			int r2 = (int) (Math.random()*1000);
			n.setLocation(new Point3D(r, r2));
			assertEquals(n.getLocation(), new Point3D(r, r2));
			assertNotEquals(n.getLocation(), new Point3D(0, 0, 0));
		}
		
	}

	@Test
	void testGetWeight() {
		System.out.println("***testGetWeight***");
		Node n = new Node(0, new Point3D(0, 0, 0));
		for (int i = 0; i < 100; i++) {
			double r = Math.random()*1000;
			double r2 = Math.random()*1000;
			n.setWeight(r);
			assertEquals(n.getWeight(), r);
			assertNotEquals(n.getWeight(), r2);
		}
	}

	@Test
	void testSetWeight() {
		System.out.println("***testSetWeight***");
		Node n = new Node(0, new Point3D(0, 0, 0));
		for (int i = 0; i < 100; i++) {
			double r = Math.random()*1000;
			double r2 = Math.random()*1000;
			n.setWeight(r);
			assertEquals(n.getWeight(), r);
			assertNotEquals(n.getWeight(), r2);
		}
	}

	@Test
	void testGetInfo() {
		System.out.println("***testGetInfo***");
		Node n = new Node(0, new Point3D(0, 0, 0));
		for (int i = 0; i < 100; i++) {
			int r = (int) (Math.random()*1000);
			int r2 = (int) (Math.random()*1000);
			String s = ""+r;
			String s2 = ""+r2;
			n.setInfo(s);
			assertEquals(n.getInfo(), s);
			assertNotEquals(n.getInfo(), s2);
		}
	}

	@Test
	void testSetInfo() {
		System.out.println("***testSetInfo***");
		Node n = new Node(0, new Point3D(0, 0, 0));
		for (int i = 0; i < 100; i++) {
			int r = (int) (Math.random()*10000);
			int r2 = (int) (Math.random()*10000);
			String s = ""+r;
			String s2 = ""+r2;
			n.setInfo(s);
			assertEquals(n.getInfo(), s);
			assertNotEquals(n.getInfo(), s2);
		}
	}

	@Test
	void testGetTag() {
		System.out.println("***testGetTag***");
		Node n = new Node(0, new Point3D(0, 0, 0));
		for (int i = 0; i < 100; i++) {
			int r = (int) (Math.random()*10000);
			int r2 = (int) (Math.random()*10000);
			n.setTag(r);
			assertEquals(n.getTag(), r);
			assertNotEquals(n.getTag(), r2);
		}
	}

	@Test
	void testSetTag() {
		System.out.println("***testSetTag***");
		Node n = new Node(0, new Point3D(0, 0, 0));
		for (int i = 0; i < 100; i++) {
			int r = (int) (Math.random()*10000);
			int r2 = (int) (Math.random()*10000);
//			if(r == 0 || )
			n.setTag(r);
			assertEquals(n.getTag(), r);
			assertNotEquals(n.getTag(), r2);
		}
	}

}
