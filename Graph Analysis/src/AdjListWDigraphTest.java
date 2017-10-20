import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.PriorityQueue;

import org.junit.Before;
import org.junit.Test;


public class AdjListWDigraphTest {

	int n1 = 2, n2 = 5, n3 = 10, n4 = 6, n5 = 5, n6 = 4, n7 = 7;

	int[] left2  = { 0, 1, 1, 2, 4, 4 };
	int[] right2 = { 1, 2, 3, 0, 2, 3 };
	
	int[] left3  = { 0, 0, 3, 3, 4, 5, 6, 7, 9, 9};
	int[] right3 = { 1, 2, 1, 2, 5, 6, 4, 8, 7, 8};

	int[] left4  = { 0, 0, 0, 1, 1, 2, 2, 4, 5 };
	int[] right4 = { 1, 2, 5, 2, 3, 3, 5, 3, 4 };
	double[] w4  = { 7, 9,14,10,15,12, 2, 6, 9 };
	
	int[] left5  = { 0, 0, 1, 1, 2, 2, 3, 3 };
	int[] right5 = { 1, 2, 3, 4, 3, 4, 2, 4 };
	double[] w5  = { 1, 5, 2, 8, 2, 2, 1, 4 };
	
	int[] left6  = { 0, 0, 1, 2 };
	int[] right6 = { 1, 2, 2, 3 };
	double[] w6  = { 1, 2, 1, 1 };
	
	int[] left7  = { 0, 0, 1, 1, 1, 2, 3, 3, 4, 4, 5 };
	int[] right7 = { 1, 3, 2, 3, 4, 4, 4, 5, 5, 6, 6 };
	double[] w7  = { 7, 5, 8, 9, 7, 5,15, 6, 8, 9, 11};
	
	AdjListWDigraph g1, g2, g3, g4, g5, g6, g7;
	
	@Before
	public void setUp() {
		g1 = new AdjListWDigraph(n1);
		g1.addEdge(0,1);
		
		g2 = new AdjListWDigraph(n2);
		for (int i=0; i<left2.length; i++) {
			g2.addEdge(left2[i],right2[i]);
		}

		g3 = new AdjListWDigraph(n3);
		for (int i=0; i<left3.length; i++) {
			g3.addEdge(left3[i],right3[i]);
		}

		g4 = new AdjListWDigraph(n4);
		for (int i=0; i<left4.length; i++) {
			g4.addEdge(left4[i],right4[i],w4[i]);
		}

		g5 = new AdjListWDigraph(n5);
		for (int i=0; i<left5.length; i++) {
			g5.addEdge(left5[i],right5[i],w5[i]);
		}

		// Undirected
		g6 = new AdjListWDigraph(n6);
		for (int i=0; i<left6.length; i++) {
			g6.addEdge(left6[i],right6[i],w6[i]);
			g6.addEdge(right6[i],left6[i],w6[i]);
		}

		// Undirected
		g7 = new AdjListWDigraph(n7);
		for (int i=0; i<left7.length; i++) {
			g7.addEdge(left7[i],right7[i],w7[i]);
			g7.addEdge(right7[i],left7[i],w7[i]);
		}
	}
	
//	@Test
//	public void testSimpleClone() {
//		AdjListWDigraph g2clone = g2.simpleClone();
//		System.out.println(g2clone.toString());
//		assertEquals(true, g2clone.isSymmetric());
//		AdjListWDigraph g3clone = g3.simpleClone();
//		System.out.println(g3clone.toString());
//		assertEquals(true, g3clone.isSymmetric());
//		
//	}
	
//	@Test
//	public void testAveragedClone() {
//		AdjListWDigraph g5clone = g5.averagedClone();
//		System.out.println(g5clone.toString());
//		assertEquals(true, g5clone.isSymmetric());
//		AdjListWDigraph g7clone = g7.averagedClone();
//		System.out.println(g7clone.toString());
//		assertEquals(true, g7clone.isSymmetric());
//	}
	
//	@Test
//	public void testGetNumVertices() {
//		assertEquals(n1,g1.getNumVertices());
//		assertEquals(n2,g2.getNumVertices());
//		assertEquals(n3,g3.getNumVertices());
//		assertEquals(n4,g4.getNumVertices());
//		assertEquals(n5,g5.getNumVertices());
//		assertEquals(n6,g6.getNumVertices());
//		assertEquals(n7,g7.getNumVertices());
//	}
	
//	@Test
//	public void testIsSymmetric() {
//		assertEquals(false,g1.isSymmetric());
//		assertEquals(false,g2.isSymmetric());
//		assertEquals(false,g3.isSymmetric());
//		assertEquals(false,g4.isSymmetric());
//		assertEquals(false,g5.isSymmetric());
//		assertEquals(true,g6.isSymmetric());
//		assertEquals(true,g7.isSymmetric());
//	}

//	@Test
//	public void testIsThereEdge() {
//		assertTrue(g1.isThereEdge(0,1));
//		assertFalse(g1.isThereEdge(1,0));
//		
//		for (int i=0; i<left2.length; i++) {
//			assertTrue(g2.isThereEdge(left2[i],right2[i]));
//		}
//
//		for (int i=0; i<left3.length; i++) {
//			assertTrue(g3.isThereEdge(left3[i],right3[i]));
//		}
//
//		for (int i=0; i<left4.length; i++) {
//			assertTrue(g4.isThereEdge(left4[i],right4[i]));
//		}
//
//		for (int i=0; i<left5.length; i++) {
//			assertTrue(g5.isThereEdge(left5[i],right5[i]));
//		}
//
//		for (int i=0; i<left6.length; i++) {
//			assertTrue(g6.isThereEdge(left6[i],right6[i]));
//			assertTrue(g6.isThereEdge(right6[i],left6[i]));
//		}
//
//		for (int i=0; i<left7.length; i++) {
//			assertTrue(g7.isThereEdge(left7[i],right7[i]));
//			assertTrue(g7.isThereEdge(right7[i],left7[i]));
//		}
//	}

//	@Test
//	public void testReachable() {
//		//System.out.println(g1);
//		boolean[] reached = g1.reachable(0);
//		System.out.println("Reachable output:");
//		System.out.println(Arrays.toString(reached));
//		boolean[] reachedCorrect = new boolean[]{ true, true };
//		System.out.println("Expected output:");
//		System.out.println(Arrays.toString(reachedCorrect));
//		for (int i=0; i<n1; i++)
//			assertEquals(reachedCorrect[i],reached[i]);
//
//		System.out.println();
//		
//		System.out.println(g2);
//		reached = g2.reachable(0);
//		System.out.println("Reachable output:");
//		System.out.println(Arrays.toString(reached));
//		reachedCorrect = new boolean[]{ true, true, true, true, false };
//		System.out.println("Expected output:");
//		System.out.println(Arrays.toString(reachedCorrect));
//		for (int i=0; i<n2; i++)
//			assertEquals(reachedCorrect[i],reached[i]);
//
//		System.out.println();
//		
//		System.out.println(g3);
//		reached = g3.reachable(0);
//		System.out.println("Reachable output:");
//		System.out.println(Arrays.toString(reached));
//		reachedCorrect = new boolean[n3];
//		reachedCorrect[0] = true;
//		reachedCorrect[1] = true;
//		reachedCorrect[2] = true;
//		System.out.println("Expected output:");
//		System.out.println(Arrays.toString(reachedCorrect));
//		for (int i=0; i<n3; i++)
//			assertEquals(reachedCorrect[i],reached[i]);
//
//		System.out.println();
//		
//		System.out.println(g4);
//		reached = g4.reachable(0);
//		System.out.println("Reachable output:");
//		System.out.println(Arrays.toString(reached));
//		reachedCorrect = new boolean[n4];
//		for (int i=0; i<n4; i++) reachedCorrect[i] = true;
//		System.out.println("Expected output:");
//		System.out.println(Arrays.toString(reachedCorrect));
//		for (int i=0; i<n4; i++)
//			assertEquals(reachedCorrect[i],reached[i]);
//
//		System.out.println();
//		
//		System.out.println(g5);
//		reached = g5.reachable(0);
//		System.out.println("Reachable output:");
//		System.out.println(Arrays.toString(reached));
//		reachedCorrect = new boolean[n5];
//		for (int i=0; i<n5; i++) reachedCorrect[i] = true;
//		System.out.println("Expected output:");
//		System.out.println(Arrays.toString(reachedCorrect));
//		for (int i=0; i<n5; i++)
//			assertEquals(reachedCorrect[i],reached[i]);
//	}

//	@Test
//	public void testShortestPath() {
//		System.out.println("\n----- Shortest Path -----\n");
//		
//		double[] expDist = new double[]{0, 7, 9, 21, 20, 11 };
//		double[] calcDist = new double[6];
//		for(int i=0; i<6;i++){
//			calcDist[i] = g4.shortestPath(0,i);
//		}
//		
//		System.out.println("Expected outputs\n");
//		System.out.println(Arrays.toString(expDist));
//		System.out.println("Calculated outputs\n");
//		System.out.println(Arrays.toString(calcDist));
//		
//		for(int i=0; i<6;i++){
//			assertEquals(expDist[i],calcDist[i],0.01);
//		}
//	}

//	@Test
//	public void testMST() {
//		System.out.println("\n----- Minimum Spanning Tree -----\n");
//		
//		AdjListWDigraph mst = g6.MST(0);
//		System.out.println(mst);
//
//		System.out.println();
//		
//		mst = g7.MST(0);
//		System.out.println(mst);
//	}
	
//	@Test
//	public void testMST2() {
//		System.out.println(g1.MST(0));
//		System.out.println(g2.MST(0));
//		System.out.println(g3.MST(0));
//		System.out.println(g4.MST(0));
//		System.out.println(g5.MST(0));
//		System.out.println(g6.MST(0));
//		System.out.println(g7.MST(0));
//	}
	
//	@Test
//	public void testconnectedComponents() {
//		System.out.println("\n----- Weakly Connected Components -----\n");
//		
//		int numComp = g1.connectedComponents();
//		System.out.println("# components = " + numComp);
//		assertEquals(1,numComp);
//		
//		numComp = g2.connectedComponents();
//		assertEquals(1,numComp);
//		System.out.println("# components = " + numComp);
//		
//		numComp = g3.connectedComponents();
//		assertEquals(3,numComp);
//		System.out.println("# components = " + numComp);
//		
//		numComp = g4.connectedComponents();
//		assertEquals(1,numComp);
//		System.out.println("# components = " + numComp);
//	}

}
