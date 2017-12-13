package edu.miracosta.cs113.graph;


import org.junit.Assert;
import org.junit.Test;
import edu.miracosta.cs113.Hand;

public class AbstractPokerGraphTest {
/*
	@Test
	public void testListPokerGraphConstructor()
	{
		
		AbstractPokerGraph list = new ListPokerGraph(7,false);
		Assert.assertFalse(list == null);
		int vertices = list.getNumV();
		Assert.assertTrue(vertices == 7);
		Assert.assertFalse(list.isDirected());
		
		
	}
	@Test
	public void test2()
	{
		AbstractPokerGraph list = new ListPokerGraph(7,false);
		HandVertex v1 = new HandVertex(101,0);
		HandVertex v2 = new HandVertex(201,1);
		HandVertex v3 = new HandVertex(301,2);
		HandVertex v4 = new HandVertex(401,3);
		PokerEdge edge1 = new PokerEdge(v1, v2);
		PokerEdge edge2 = new PokerEdge(v1, v3);
		PokerEdge edge3 = new PokerEdge(v3, v4);
		PokerEdge edge4 = new PokerEdge(v2, v4);
		
		
		list.insert(edge1);
		list.insert(edge2);
		list.insert(edge3);
		list.insert(edge4);
		Assert.assertTrue(((ListPokerGraph) list).isEdge(v1,v2));
		Assert.assertTrue(((ListPokerGraph) list).getEdge(v1, v2).equals(edge1));
		Assert.assertTrue(((ListPokerGraph) list).getEdge(v3, v4).equals(edge3));
		Assert.assertTrue(((ListPokerGraph) list).isEdge(v2,v1));//testing if functions
																		// as undirected graph
		// test with edge not in list
		Assert.assertFalse(((ListPokerGraph) list).isEdge(v1, v4));
		Assert.assertFalse(((ListPokerGraph) list).getEdge(v3, v4).equals(edge4));
	}
*/
}
