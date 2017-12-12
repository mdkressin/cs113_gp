package edu.miracosta.cs113;

import org.junit.Assert;
import org.junit.Test;

import edu.miracosta.cs113.graph.HandVertex;
import edu.miracosta.cs113.graph.PokerEdge;

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
		CardDeck deck = new CardDeck();
		Card[] cards1 = new Card[7];
		Card[] cards2 = new Card[7];
		Card[] cards3 = new Card[7];
		Card[] cards4 = new Card[7];
		Card[] cards5 = new Card[7];
		Card[] cards6 = new Card[7];
		Card[] cards7 = new Card[7];
		for (int i = 0; i < 7; i++)
		{
			cards1[i] = deck.deal();
			cards2[i] = deck.deal();
			cards3[i] = deck.deal();
			cards4[i] = deck.deal();
			cards5[i] = deck.deal();
			cards6[i] = deck.deal();
			cards7[i] = deck.deal();
		}
		Hand hand1 = new Hand(cards1);
		Hand hand2 = new Hand(cards2);
		Hand hand3 = new Hand(cards3);
		Hand hand4 = new Hand(cards4);
		Hand hand5 = new Hand(cards5);
		Hand hand6 = new Hand(cards6);
		Hand hand7 = new Hand(cards7);
		
		AbstractPokerGraph<Hand> list = new ListPokerGraph<Hand>(7, false);
		Vertex<Hand> v0 = new Vertex<Hand>(hand1,0);
		Vertex<Hand> v1 = new Vertex<Hand>(hand2,1);
		Vertex<Hand> v2 = new Vertex<Hand>(hand3,2);
		Vertex<Hand> v3 = new Vertex<Hand>(hand4,3);
		Vertex<Hand> v4 = new Vertex<Hand>(hand5,4);
		Vertex<Hand> v5 = new Vertex<Hand>(hand6,5);
		Vertex<Hand> v6 = new Vertex<Hand>(hand7,6);
		list.insert(new Edge<Hand>(v0,v1));
		list.insert(new Edge<Hand>(v0,v3));
		list.insert(new Edge<Hand>(v0,v4));
		list.insert(new Edge<Hand>(v0,v2));
		list.insert(new Edge<Hand>(v1,v3));
		list.insert(new Edge<Hand>(v1,v4));
		list.insert(new Edge<Hand>(v3,v4));
		list.insert(new Edge<Hand>(v2,v5));
		list.insert(new Edge<Hand>(v2,v6));
		list.insert(new Edge<Hand>(v5,v6));
		
		DepthFirstSearch<Hand> dfs = new DepthFirstSearch<Hand>(list);
		int[] dOrder = dfs.getDiscoveryOrder();
		int[] fOrder = dfs.getFinishOrder();
		
		/* printing dfs traversal
		ListPokerGraph<Hand> l = (ListPokerGraph<Hand>) list;
		for (int i = 0; i < list.getNumV(); i++) 
		{
			System.out.println(dOrder[i] + "\t" + fOrder[i]);
			System.out.println(l.getVertex(dOrder[i]).toString() + "\n" +
								l.getVertex(fOrder[i]).toString());
		}
		*/
	}
	
	@Test
	public void testPossibleHandsGraph()
	{
		System.out.println("\nTesting method possibleHandsGraph()");
		CardDeck deck = new CardDeck();
		deck.shuffle();
		Card[] cards = new Card[6];
		for (int i = 0; i < cards.length; i++)
		{
			cards[i] = deck.deal();
		}
		Hand hand = new Hand(cards);
		Graph<Hand> possibleHands = new ListPokerGraph<Hand>(47,false);
		possibleHands = AbstractPokerGraph.possibleHandsGraph(possibleHands,hand);
		
		DepthFirstSearch<Hand> dfs = new DepthFirstSearch<Hand>(possibleHands);
		int[] dOrder = dfs.getDiscoveryOrder();
		int[] fOrder = dfs.getFinishOrder();
		
		ListPokerGraph<Hand> list = (ListPokerGraph<Hand>) possibleHands;
		System.out.println("discovered\tfinished");
		for (int i = 0; i < list.getNumV(); i++) 
		{
			Vertex<Hand> v1 = list.getVertex(dOrder[i]);
			Vertex<Hand> v2 = list.getVertex(fOrder[i]);
			if (v1 != null && v2 != null)
			{
				System.out.println("\t" + dOrder[i] + "\t" + fOrder[i]);
			//	System.out.println(list.getVertex(dOrder[i]).toString() + "\n" +
				//					list.getVertex(fOrder[i]).toString());
			}
		}
	}
/*	
    @Test
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
