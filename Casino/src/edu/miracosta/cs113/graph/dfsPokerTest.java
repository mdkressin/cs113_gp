package edu.miracosta.cs113.graph;

import org.junit.Assert;
import org.junit.Test;
import edu.miracosta.cs113.Hand;
import edu.miracosta.cs113.Card;
import edu.miracosta.cs113.CardDeck;

public class dfsPokerTest {

	@Test
	public void test() {
		
		CardDeck deck = new CardDeck();
		Card[] cards1 = new Card[7];
		Card[] cards2 = new Card[7];
		Card[] cards3 = new Card[7];
		Card[] cards4 = new Card[7];
		Card[] cards5 = new Card[7];
		Card[] cards6 = new Card[7];
		Card[] cards7 = new Card[7];
		for (int i = 0; i < 6; i++)
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
		
		
		// undirected graph
		AbstractPokerGraph list = new ListPokerGraph(7,false);
		HandVertex v0 = new HandVertex(hand1,0);
		HandVertex v1 = new HandVertex(hand2,1);
		HandVertex v2 = new HandVertex(hand3,2);
		HandVertex v3 = new HandVertex(hand4,3);
		HandVertex v4 = new HandVertex(hand5,4);
		HandVertex v5 = new HandVertex(hand6,5);
		HandVertex v6 = new HandVertex(hand7,6);
		list.insert(new PokerEdge(v0,v1));
		list.insert(new PokerEdge(v0,v3));
		list.insert(new PokerEdge(v0,v4));
		list.insert(new PokerEdge(v0,v2));
		list.insert(new PokerEdge(v1,v3));
		list.insert(new PokerEdge(v1,v4));
		list.insert(new PokerEdge(v3,v4));
		list.insert(new PokerEdge(v2,v5));
		list.insert(new PokerEdge(v2,v6));
		list.insert(new PokerEdge(v5,v6));
		
		dfsPoker dfs = new dfsPoker(list);
		HandVertex[] dOrder = dfs.getDiscoveryOrder();
		HandVertex[] fOrder = dfs.getFinishOrder();
		System.out.println("Discovery and finish order undirected");
		for (int i = 0; i < list.getNumV(); i++) 
		{
			System.out.println(dOrder[i] + " " + fOrder[i]);
		}
		
		// directed graph
		AbstractPokerGraph list2 = new ListPokerGraph(7,true);
		list2.insert(new PokerEdge(v0,v1));
		list2.insert(new PokerEdge(v0,v3));
		list2.insert(new PokerEdge(v0,v4));
		list2.insert(new PokerEdge(v0,v2));
		list2.insert(new PokerEdge(v1,v3));
		list2.insert(new PokerEdge(v1,v4));
		list2.insert(new PokerEdge(v3,v4));
		list2.insert(new PokerEdge(v2,v5));
		list2.insert(new PokerEdge(v2,v6));
		list2.insert(new PokerEdge(v5,v6));
		
		dfsPoker dfs2 = new dfsPoker(list2);
		HandVertex[] dOrder2 = dfs2.getDiscoveryOrder();
		HandVertex[] fOrder2 = dfs2.getFinishOrder();
		System.out.println("Discovery and finish order directed");
		for (int i = 0; i < list2.getNumV(); i++) 
		{
			System.out.println(dOrder2[i] + " " + fOrder2[i]);
		}
		
	}
/*	@Test
	public void test2() {
		// undirected graph
		AbstractPokerGraph list = new ListPokerGraph(10,false);
		HandVertex v0 = new HandVertex(0,0);
		HandVertex v1 = new HandVertex(100,1);
		HandVertex v2 = new HandVertex(200,2);
		HandVertex v3 = new HandVertex(300,3);
		HandVertex v4 = new HandVertex(400,4);
		HandVertex v5 = new HandVertex(500,5);
		HandVertex v6 = new HandVertex(600,6);
		HandVertex v7 = new HandVertex(700,6);
		HandVertex v8 = new HandVertex(800,6);
		HandVertex v9 = new HandVertex(900,6);
		list.insert(new PokerEdge(v0,v3));
		list.insert(new PokerEdge(v0,v1));
		list.insert(new PokerEdge(v3,v2));
		list.insert(new PokerEdge(v1,v2));
		list.insert(new PokerEdge(v2,v9));
		list.insert(new PokerEdge(v2,v8));
		list.insert(new PokerEdge(v1,v4));
		list.insert(new PokerEdge(v1,v6));
		list.insert(new PokerEdge(v1,v7));
		list.insert(new PokerEdge(v4,v5));
		
		dfsPoker dfs = new dfsPoker(list);
		HandVertex[] dOrder = dfs.getDiscoveryOrder();
		HandVertex[] fOrder = dfs.getFinishOrder();
		System.out.println("Discovery and finish order undirected");
		for (int i = 0; i < list.getNumV(); i++) 
		{
			System.out.println(dOrder[i] + " " + fOrder[i]);
		}
		
		// directed graph
		AbstractPokerGraph list2 = new ListPokerGraph(10,true);
		list2.insert(new PokerEdge(v0,v3));
		list2.insert(new PokerEdge(v0,v1));
		list2.insert(new PokerEdge(v3,v2));
		list2.insert(new PokerEdge(v1,v2));
		list2.insert(new PokerEdge(v2,v9));
		list2.insert(new PokerEdge(v2,v8));
		list2.insert(new PokerEdge(v1,v4));
		list2.insert(new PokerEdge(v1,v6));
		list2.insert(new PokerEdge(v1,v7));
		list2.insert(new PokerEdge(v4,v5));
		
		dfsPoker dfs2 = new dfsPoker(list2);
		HandVertex[] dOrder2 = dfs2.getDiscoveryOrder();
		HandVertex[] fOrder2 = dfs2.getFinishOrder();
		System.out.println("Discovery and finish order directed");
		for (int i = 0; i < list2.getNumV(); i++) 
		{
			System.out.println(dOrder2[i] + " " + fOrder2[i]);
		}
		
	}*/

}
