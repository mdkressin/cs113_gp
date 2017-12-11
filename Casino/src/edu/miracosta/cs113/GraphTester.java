package edu.miracosta.cs113;

import org.junit.Assert;
import org.junit.Test;
import java.util.Iterator;

/**
 * Algorithm to test graph implementation
 * 1.	test constructors
 * 2.		test constructor of ListPokerGraph
 * 3.		test constructor of MatrixPokerGraph
 * 4.	test method insert
 * 5.		test method insert in ListPokerGraph
 * 6.			instantiate ListPokerGraph object
 * 7.			instantiate Edge objects
 * 8.			insert Edge objects into the ListPokerGraph
 * 9.		test method insert in MatrixPokerGraph
 * 10.			repeat steps 6-8 for MatrixPokerGraph
 * 11.	test method getEdge
 * 12.		test method getEdge in ListPokerGraph
 * 13.			instantiate ListPokerGraph object
 * 14.			insert Edges into graph
 * 15.			getEdges from graph and check to see if they're correct
 * 16.		test method getEdge in MatrixPokerGraph
 * 17.			repeat steps 13-15 for MatrixPokerGraph
 * 18.	test method isEdge
 * 19.		test method isEdge in ListPokerGraph
 * 20.			instantiate ListPokerGraph object
 * 21.			insert Edges into graph
 * 22.			check with edges in graph and edges not in the graph
 * 23.		test method isEdge in MatrixPokerGraph
 * 24.			repeat steps 20-22 for MatrixPokerGraph
 * 25.	test method edgeIterator
 * 26.		test method edgeIterator in ListPokerGraph
 * 27.			instantiate ListPokerGraph object
 * 28.			insert edges into graph
 * 29.			test to see if iterator iterates through graph
 * 30.		test method edgeIterator in MatrixPokerGraph
 * 31.			repeat steps 27-29 for MatrixPokerGraph
 *
 */
public class GraphTester {

	@Test
	public void testListPokerGraphConstructor()
	{
		
		AbstractPokerGraph<Integer> list = new ListPokerGraph<Integer>(7,false);
		Assert.assertFalse(list == null);
		int vertices = list.getNumV();
		Assert.assertTrue(vertices == 7);
		Assert.assertFalse(list.isDirected());
		
		
	}
	@Test
	public void test2()
	{
		AbstractPokerGraph<Integer> list = new ListPokerGraph<Integer>(7,false);
		Vertex<Integer> v1 = new Vertex<Integer>(101,0);
		Vertex<Integer> v2 = new Vertex<Integer>(201,1);
		Vertex<Integer> v3 = new Vertex<Integer>(301,2);
		Vertex<Integer> v4 = new Vertex<Integer>(401,3);
		Edge<Integer> edge1 = new Edge<Integer>(v1, v2);
		Edge<Integer> edge2 = new Edge<Integer>(v1, v3);
		Edge<Integer> edge3 = new Edge<Integer>(v3, v4);
		Edge<Integer> edge4 = new Edge<Integer>(v2, v4);
		
		
		list.insert(edge1);
		list.insert(edge2);
		list.insert(edge3);
		list.insert(edge4);
		Assert.assertTrue(((ListPokerGraph<Integer>) list).isEdge(v1,v2));
		Assert.assertTrue(((ListPokerGraph<Integer>) list).getEdge
														(v1, v2).equals(edge1));
		Assert.assertTrue(((ListPokerGraph<Integer>) list).getEdge
														(v3, v4).equals(edge3));
		Assert.assertTrue(((ListPokerGraph<Integer>) list).isEdge(v2,v1));//testing if functions
																		// as undirected graph
		// test with edge not in list
		Assert.assertFalse(((ListPokerGraph<Integer>) list).isEdge(v1, v4));
		Assert.assertFalse(((ListPokerGraph<Integer>) list).getEdge
													(v3, v4).equals(edge4));
		
	}
	@Test
	public void testInsertHand()
	{
		AbstractPokerGraph<Hand> list = new ListPokerGraph<Hand>(10, false);
	}
/*	@Test
	public void testMatrixPokerGraphConstructor()
	{
		AbstractPokerGraph matrix = new MatrixPokerGraph(7,false);
		Assert.assertFalse(matrix == null);
		int vertices = matrix.getNumV();
		Assert.assertTrue(vertices == 7);
		Assert.assertFalse(matrix.isDirected());
	}
	@Test
	public void testListPokerGraphInsert()
	{
		AbstractPokerGraph list = new ListPokerGraph(7,false);
		Edge edge1 = new Edge(0,2);
		Edge edge2 = new Edge(2,4);
		Edge edge3 = new Edge(4,6);
		
		list.insert(edge1);
		Assert.assertTrue(((ListPokerGraph) list).isEdge(0,2));
		Assert.assertTrue(((ListPokerGraph) list).getEdge(0, 2).equals(edge1));
		// test with edge not in list
		Assert.assertFalse(((ListPokerGraph) list).isEdge(2, 4));
		
		list.insert(edge2);
		list.insert(edge3);
		Assert.assertTrue(((ListPokerGraph) list).isEdge(2,4));
		Assert.assertTrue(((ListPokerGraph) list).getEdge(2, 4).equals(edge2));
		Assert.assertTrue(((ListPokerGraph) list).isEdge(4,6));
		Assert.assertTrue(((ListPokerGraph) list).getEdge(4, 6).equals(edge3));	
	}
	@Test
	public void testListPokerGraphGetEdge()
	{
		AbstractPokerGraph list = new ListPokerGraph(7,false);
		Edge edge1 = new Edge(0,2);
		Edge edge2 = new Edge(2,4);
		Edge edge3 = new Edge(4,6);
		
		list.insert(edge1);
		list.insert(edge2);
		list.insert(edge3);
		
		Edge e1 = list.getEdge(edge1.getSource(), edge1.getDest());
		Assert.assertTrue(edge1.equals(e1));
		Edge e2 = list.getEdge(edge2.getSource(), edge2.getDest());
		Edge e3 = list.getEdge(edge3.getSource(), edge3.getDest());
		Assert.assertTrue(edge2.equals(e2) && edge3.equals(e3));
	}
	@Test
	public void testListPokerGraphIsEdge()
	{
		AbstractPokerGraph list = new ListPokerGraph(7,false);
		Edge edge1 = new Edge(0,2);
		Edge edge2 = new Edge(2,4);
		Edge edge3 = new Edge(4,6);
		
		list.insert(edge1);
		list.insert(edge2);
		list.insert(edge3);
		
		Assert.assertTrue(((ListPokerGraph) list).isEdge(0, 2));
		Assert.assertTrue(((ListPokerGraph) list).isEdge(2, 4));
		Assert.assertTrue(((ListPokerGraph) list).isEdge(4, 6));
		Assert.assertTrue(((ListPokerGraph) list).isEdge(6, 4));//undirected graph
		
		// test for edges that are not in the graph
		Assert.assertFalse(((ListPokerGraph) list).isEdge(0, 6));
		Assert.assertFalse(((ListPokerGraph) list).isEdge(2, 6));
		Assert.assertFalse(((ListPokerGraph) list).isEdge(0, 4));
	}
	@Test 			//will come back to later
	public void testListPokerGraphEdgeIterator()
	{
		AbstractPokerGraph list = new ListPokerGraph(7,false);
		Edge edge1 = new Edge(0,2);
		Edge edge2 = new Edge(2,4);
		Edge edge3 = new Edge(4,6);
		Edge edge4 = new Edge(2,8);
		Edge edge5 = new Edge(4,10);
		Edge edge6 = new Edge(0,12);
		Edge edge7 = new Edge(6,10);
		
		list.insert(edge1);
		list.insert(edge2);
		list.insert(edge3);
		
		Iterator<Edge> iter = list.edgeIterator(0);
		Edge[] edges = new Edge[7];
		int i = 0;
		while (iter.hasNext())
		{
			edges[i] = iter.next();
			System.out.println(edges[i].toString());
			System.out.println(i);
			i++;
		}
		
	}
	@Test
	public void testCreateGraph() {
		
	}*/

}
