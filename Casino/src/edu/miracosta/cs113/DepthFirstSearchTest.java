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

	public static void main(String[] args)
	{
		AbstractPokerGraph<Integer> list = new ListPokerGraph<Integer>(7,false);
		Vertex<Integer> v0 = new Vertex<Integer>(100,0);
		Vertex<Integer> v1 = new Vertex<Integer>(200,1);
		Vertex<Integer> v2 = new Vertex<Integer>(300,2);
		Vertex<Integer> v3 = new Vertex<Integer>(400,3);
		Vertex<Integer> v4 = new Vertex<Integer>(500,4);
		Vertex<Integer> v5 = new Vertex<Integer>(600,5);
		Vertex<Integer> v6 = new Vertex<Integer>(700,6);
		list.insert(new Edge<Integer>(v0,v1));
		list.insert(new Edge<Integer>(v0,v3));
		list.insert(new Edge<Integer>(v0,v4));
		list.insert(new Edge<Integer>(v0,v2));
		list.insert(new Edge<Integer>(v1,v3));
		list.insert(new Edge<Integer>(v1,v4));
		list.insert(new Edge<Integer>(v3,v4));
		list.insert(new Edge<Integer>(v2,v5));
		list.insert(new Edge<Integer>(v2,v6));
		list.insert(new Edge<Integer>(v5,v6));

		DepthFirstSearch<Integer> dfs = new DepthFirstSearch<Integer>(list);
		int[] dOrder = dfs.getDiscoveryOrder();
		int[] fOrder = dfs.getFinishOrder();
		System.out.println("Discovery and finish order undirected");
		for (int i = 0; i < list.getNumV(); i++) 
		{
			System.out.println(dOrder[i] + "\t" + fOrder[i]);
			ListPokerGraph<Integer> l = (ListPokerGraph<Integer>) list;
			System.out.println(l.getVertex(dOrder[i]).toString() + "\t" +
								l.getVertex(fOrder[i]).toString());
		}

	}
	/*
	@Test
	public void test() {
		// undirected graph
		AbstractPokerGraph<Vertex<Integer>,Integer> list = 
									new ListPokerGraph<Vertex<Integer>,Integer>(7,false);
		Vertex<Integer> v0 = new Vertex<Integer>(100,0);
		Vertex<Integer> v1 = new Vertex<Integer>(200,1);
		Vertex<Integer> v2 = new Vertex<Integer>(300,2);
		Vertex<Integer> v3 = new Vertex<Integer>(400,3);
		Vertex<Integer> v4 = new Vertex<Integer>(500,4);
		Vertex<Integer> v5 = new Vertex<Integer>(600,5);
		Vertex<Integer> v6 = new Vertex<Integer>(700,6);
		list.insert(new Edge<Vertex<Integer>,Integer>(v0,v1));
		list.insert(new Edge<Vertex<Integer>,Integer>(v0,v3));
		list.insert(new Edge<Vertex<Integer>,Integer>(v0,v4));
		list.insert(new Edge<Vertex<Integer>,Integer>(v0,v2));
		list.insert(new Edge<Vertex<Integer>,Integer>(v1,v3));
		list.insert(new Edge<Vertex<Integer>,Integer>(v1,v4));
		list.insert(new Edge<Vertex<Integer>,Integer>(v3,v4));
		list.insert(new Edge<Vertex<Integer>,Integer>(v2,v5));
		list.insert(new Edge<Vertex<Integer>,Integer>(v2,v6));
		list.insert(new Edge<Vertex<Integer>,Integer>(v5,v6));
		
		DepthFirstSearch<Vertex<Integer>,Integer> dfs = 
									new DepthFirstSearch<Vertex<Integer>,Integer>(list);
		Vertex<Integer>[] dOrder = dfs.getDiscoveryOrder();
		Vertex<Integer>[] fOrder = dfs.getFinishOrder();
		System.out.println("Discovery and finish order undirected");
		for (int i = 0; i < list.getNumV(); i++) 
		{
			System.out.println(dOrder[i] + " " + fOrder[i]);
		}
		
		// directed graph
		AbstractPokerGraph<Vertex<Integer>,Integer> list2 = 
									new ListPokerGraph<Vertex<Integer>,Integer>(7,true);
		list2.insert(new Edge<Vertex<Integer>,Integer>(v0,v1));
		list2.insert(new Edge<Vertex<Integer>,Integer>(v0,v3));
		list2.insert(new Edge<Vertex<Integer>,Integer>(v0,v4));
		list2.insert(new Edge<Vertex<Integer>,Integer>(v0,v2));
		list2.insert(new Edge<Vertex<Integer>,Integer>(v1,v3));
		list2.insert(new Edge<Vertex<Integer>,Integer>(v1,v4));
		list2.insert(new Edge<Vertex<Integer>,Integer>(v3,v4));
		list2.insert(new Edge<Vertex<Integer>,Integer>(v2,v5));
		list2.insert(new Edge<Vertex<Integer>,Integer>(v2,v6));
		list2.insert(new Edge<Vertex<Integer>,Integer>(v5,v6));
		
		DepthFirstSearch<Vertex<Integer>,Integer> dfs2 = 
									new DepthFirstSearch<Vertex<Integer>,Integer>(list2);
		Vertex<Integer>[] dOrder2 = dfs2.getDiscoveryOrder();
		Vertex<Integer>[] fOrder2 = dfs2.getFinishOrder();
		System.out.println("Discovery and finish order directed");
		for (int i = 0; i < list2.getNumV(); i++) 
		{
			System.out.println(dOrder2[i] + " " + fOrder2[i]);
		}
		
	}
	@Test
	public void test2() {
		// undirected graph
		AbstractPokerGraph<Vertex<Integer>,Integer> list = 
									new ListPokerGraph<Vertex<Integer>,Integer>(10,false);
		Vertex<Integer> v0 = new Vertex<Integer>(0,0);
		Vertex<Integer> v1 = new Vertex<Integer>(100,1);
		Vertex<Integer> v2 = new Vertex<Integer>(200,2);
		Vertex<Integer> v3 = new Vertex<Integer>(300,3);
		Vertex<Integer> v4 = new Vertex<Integer>(400,4);
		Vertex<Integer> v5 = new Vertex<Integer>(500,5);
		Vertex<Integer> v6 = new Vertex<Integer>(600,6);
		Vertex<Integer> v7 = new Vertex<Integer>(700,6);
		Vertex<Integer> v8 = new Vertex<Integer>(800,6);
		Vertex<Integer> v9 = new Vertex<Integer>(900,6);
		list.insert(new Edge<Vertex<Integer>,Integer>(v0,v3));
		list.insert(new Edge<Vertex<Integer>,Integer>(v0,v1));
		list.insert(new Edge<Vertex<Integer>,Integer>(v3,v2));
		list.insert(new Edge<Vertex<Integer>,Integer>(v1,v2));
		list.insert(new Edge<Vertex<Integer>,Integer>(v2,v9));
		list.insert(new Edge<Vertex<Integer>,Integer>(v2,v8));
		list.insert(new Edge<Vertex<Integer>,Integer>(v1,v4));
		list.insert(new Edge<Vertex<Integer>,Integer>(v1,v6));
		list.insert(new Edge<Vertex<Integer>,Integer>(v1,v7));
		list.insert(new Edge<Vertex<Integer>,Integer>(v4,v5));
		
		DepthFirstSearch<Vertex<Integer>,Integer> dfs = 
									new DepthFirstSearch<Vertex<Integer>,Integer>(list);
		Vertex<Integer>[] dOrder = dfs.getDiscoveryOrder();
		Vertex<Integer>[] fOrder = dfs.getFinishOrder();
		System.out.println("Discovery and finish order undirected");
		for (int i = 0; i < list.getNumV(); i++) 
		{
			System.out.println(dOrder[i] + " " + fOrder[i]);
		}
		
		// directed graph
		AbstractPokerGraph<Vertex<Integer>,Integer> list2 = 
									new ListPokerGraph<Vertex<Integer>,Integer>(10,true);
		list2.insert(new Edge<Vertex<Integer>,Integer>(v0,v3));
		list2.insert(new Edge<Vertex<Integer>,Integer>(v0,v1));
		list2.insert(new Edge<Vertex<Integer>,Integer>(v3,v2));
		list2.insert(new Edge<Vertex<Integer>,Integer>(v1,v2));
		list2.insert(new Edge<Vertex<Integer>,Integer>(v2,v9));
		list2.insert(new Edge<Vertex<Integer>,Integer>(v2,v8));
		list2.insert(new Edge<Vertex<Integer>,Integer>(v1,v4));
		list2.insert(new Edge<Vertex<Integer>,Integer>(v1,v6));
		list2.insert(new Edge<Vertex<Integer>,Integer>(v1,v7));
		list2.insert(new Edge<Vertex<Integer>,Integer>(v4,v5));
		
		DepthFirstSearch<Vertex<Integer>,Integer> dfs2 = 
									new DepthFirstSearch<Vertex<Integer>,Integer>(list2);
		Vertex<Integer>[] dOrder2 = dfs2.getDiscoveryOrder();
		Vertex<Integer>[] fOrder2 = dfs2.getFinishOrder();
		System.out.println("Discovery and finish order directed");
		for (int i = 0; i < list2.getNumV(); i++) 
		{
			System.out.println(dOrder2[i] + " " + fOrder2[i]);
		}
		
	}
	*/

}
