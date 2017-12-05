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
 *
 */
public class DepthFirstSearchTest {

	@Test
	public void test() {
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
		System.out.println("Discovery and finish order");
		for (int i = 0; i < list.getNumV(); i++) 
		{
			System.out.println(dOrder[i] + " " + fOrder[i]);
		}
		
	}

}
