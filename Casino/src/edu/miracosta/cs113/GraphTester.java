package edu.miracosta.cs113;

import org.junit.Assert;
import org.junit.Test;

/**
 * Algorithm to test graph implementation
 * 1.	test constructors
 * 2.		test constructor of ListPokerGraph
 * 3.		test constructor of MatrixPokerGraph
 * 4.	test method insert
 * 5.		test method insert in ListPokerGraph
 *
 */
public class GraphTester {

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
	public void testMatrixPokerGraphConstructor()
	{
		AbstractPokerGraph matrix = new MatrixPokerGraph(7,false);
		Assert.assertFalse(matrix == null);
		int vertices = matrix.getNumV();
		Assert.assertTrue(vertices == 7);
		Assert.assertFalse(matrix.isDirected());
	}
	@Test
	public void testCreateGraph() {
		
	}

}
