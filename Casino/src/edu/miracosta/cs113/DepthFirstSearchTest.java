package edu.miracosta.cs113;

import org.junit.Assert;
import org.junit.Test;

/**
 * Algorithm for testing search traversal methods of the graphs
 * 1.	test depth-first search
 * 2.		instantiate ListPokerGraph object
 * 3.			insert edges to create graph
 * 4.			use depth-first search
 * 5.			compare result with expected result
 * 6.			test differences between directed and undirected graphs
 *
 */
public class DepthFirstSearchTest {

	@Test
	public void test() {
		// undirected graph
		AbstractPokerGraph list = new ListPokerGraph(7,false);
		list.insert(new Edge(0,1));
		list.insert(new Edge(0,3));
		list.insert(new Edge(0,4));
		list.insert(new Edge(0,2));
		list.insert(new Edge(1,3));
		list.insert(new Edge(1,4));
		list.insert(new Edge(3,4));
		list.insert(new Edge(2,5));
		list.insert(new Edge(2,6));
		list.insert(new Edge(5,6));
		
		DepthFirstSearch dfs = new DepthFirstSearch(list);
		int[] dOrder = dfs.getDiscoveryOrder();
		int[] fOrder = dfs.getFinishOrder();
		System.out.println("Discovery and finish order undirected");
		for (int i = 0; i < list.getNumV(); i++) 
		{
			System.out.println(dOrder[i] + " " + fOrder[i]);
		}
		
		// directed graph
		AbstractPokerGraph list2 = new ListPokerGraph(7,true);
		list2.insert(new Edge(0,1));
		list2.insert(new Edge(0,3));
		list2.insert(new Edge(0,4));
		list2.insert(new Edge(0,2));
		list2.insert(new Edge(1,3));
		list2.insert(new Edge(1,4));
		list2.insert(new Edge(3,4));
		list2.insert(new Edge(2,5));
		list2.insert(new Edge(2,6));
		list2.insert(new Edge(5,6));
		
		DepthFirstSearch dfs2 = new DepthFirstSearch(list2);
		int[] dOrder2 = dfs2.getDiscoveryOrder();
		int[] fOrder2 = dfs2.getFinishOrder();
		System.out.println("Discovery and finish order directed");
		for (int i = 0; i < list2.getNumV(); i++) 
		{
			System.out.println(dOrder2[i] + " " + fOrder2[i]);
		}
		
	}
	@Test
	public void test2() {
		// undirected graph
		AbstractPokerGraph list = new ListPokerGraph(10,false);
		list.insert(new Edge(0,3));
		list.insert(new Edge(0,1));
		list.insert(new Edge(3,2));
		list.insert(new Edge(1,2));
		list.insert(new Edge(2,9));
		list.insert(new Edge(2,8));
		list.insert(new Edge(1,4));
		list.insert(new Edge(1,6));
		list.insert(new Edge(1,7));
		list.insert(new Edge(4,5));
		
		DepthFirstSearch dfs = new DepthFirstSearch(list);
		int[] dOrder = dfs.getDiscoveryOrder();
		int[] fOrder = dfs.getFinishOrder();
		System.out.println("Discovery and finish order undirected");
		for (int i = 0; i < list.getNumV(); i++) 
		{
			System.out.println(dOrder[i] + " " + fOrder[i]);
		}
		
		// directed graph
		AbstractPokerGraph list2 = new ListPokerGraph(10,true);
		list.insert(new Edge(0,3));
		list.insert(new Edge(0,1));
		list.insert(new Edge(3,2));
		list.insert(new Edge(1,2));
		list.insert(new Edge(2,9));
		list.insert(new Edge(2,8));
		list.insert(new Edge(1,4));
		list.insert(new Edge(1,6));
		list.insert(new Edge(1,7));
		list.insert(new Edge(4,5));
		
		DepthFirstSearch dfs2 = new DepthFirstSearch(list2);
		int[] dOrder2 = dfs2.getDiscoveryOrder();
		int[] fOrder2 = dfs2.getFinishOrder();
		System.out.println("Discovery and finish order directed");
		for (int i = 0; i < list2.getNumV(); i++) 
		{
			System.out.println(dOrder2[i] + " " + fOrder2[i]);
		}
		
	}

}
