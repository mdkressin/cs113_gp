package edu.miracosta.cs113;

public class HandScore
{
    //Score values
    private int score;
    private final int PAIR            = 12;
    private final int THREE_OF_A_KIND = 47;
    private final int STRAIGHT        = 59;
    private final int FLUSH           = 109;
    private final int FULL_HOUSE      = 159;
    private final int FOUR_OF_A_KIND  = 182;

    //Amounts needed for a type of hand
    private final int NUM_FOR_STRAIGHT = 5;
    private final int NUM_FOR_FLUSH    = 5;

    //Keeps track of the highest card value for these types of hands
    private int straightHighCardValue;
    private int flushHighCardValue;

    //Counters for detecting types of hands
    private int numDuplicates;
    private int numInRow;
    private int numSameFace;

    //Counters for types of hands
    private int numFourOfKind;
    private int flush;
    private int straight;
    private int numThreeOfKind;
    private int numPairs;

    private Card cardToAddToScore;

    //Default constructor
    public HandScore()
    {
        this.straightHighCardValue = 0;
        this.flushHighCardValue    = 0;

        this.numFourOfKind  = 0;
        this.flush          = 0;
        this.straight       = 0;
        this.numThreeOfKind = 0;
        this.numPairs       = 0;

        this.numDuplicates  = 0;
        this.numInRow       = 0;
        this.numSameFace    = 0;
    }
    /**
     * Calls methods to organize the cards, and detect any valuable hands
     * @param cards Cards to be checked
     * @return      The total score
     */
    public int calculateScore(Card[] cards)
    {
        resetValues();
        Card[] tempHand;
        tempHand = sortHand(cards);
        for (Card c : tempHand)
        {
        	if (c != null)
        	{
        		System.out.println(c.toString());
        	}
        }

        detectDuplicates(tempHand);
        detectStraight(tempHand);
        detectFlush(tempHand);
        return getScore(cards);
    }
    /**
     * Adds up the total score based on all counters and the cards' values
     * @param cards cards to cycle through for adding values up
     * @return The total score
     */
    private int getScore(Card[] cards)
    {
        System.out.println("NumPairs: "       + numPairs);
        System.out.println("NumThreeOfKind: " + numThreeOfKind);
        System.out.println("NumFourOfKind: "  + numFourOfKind);
        System.out.println("Straight: "       + straight);
        System.out.println("Flush: "          + flush);
        score += numFourOfKind   * FOUR_OF_A_KIND;
        score += (flush          * FLUSH)    + flushHighCardValue;
        score += (straight       * STRAIGHT) + straightHighCardValue;
        if ((numThreeOfKind > 0) && (numPairs > 0))
        {
            score += FULL_HOUSE;
        }
        else
        {
            score += numThreeOfKind  * THREE_OF_A_KIND;
            score += numPairs        * PAIR;
        }
        if (score <= 0)
        {
        	int tempScore = 0;
        	for (Card c : cards)
        	{
        		if (c != null)
        		{
        			tempScore = c.getValue();
        		}
        	}
            score += tempScore;
        }
        return score;
    }
    /**
     * Resets all counters to 0
     */
    private void resetValues()
    {
        this.straightHighCardValue = 0;
        this.flushHighCardValue    = 0;

        this.score          = 0;
        this.numFourOfKind  = 0;
        this.flush          = 0;
        this.straight       = 0;
        this.numThreeOfKind = 0;
        this.numPairs       = 0;

        this.numDuplicates  = 0;
        this.numInRow       = 0;
        this.numSameFace    = 0;
    }
    /**
     * Uses a bubble sort to put an array of cards in order
     * @param cards Cards to organize
     * @return      Organized array
     */
    public Card[] sortHand(Card[] cards)
    {
        Card[] tempCards;
        int endIndex = (cards.length - 1);
        boolean sorted = false;
        while (!sorted)
        {
            sorted = true;
            for (int i = 0; i < endIndex; i++)
            {
            	if (cards[i] != null  && cards[i + 1] != null)
            	{
            		if (cards[i].getValue() > cards[i + 1].getValue())
                    {
                        Card tempCard = cards[i + 1];
                        cards[i + 1] = cards[i];
                        cards[i] = tempCard;
                        sorted = false;
                    }
            	}
            }
            endIndex--;
        }
        tempCards = cards;
        return tempCards;
    }
    /**
     * Counts the amount of equal cards and then determines if its a pair, three of a kind, or four
     * of a kind
     * @param cards The array of cards to check
     */
    private void detectDuplicates(Card[] cards)
    {
        Card previousCard = null;
        for (int i = 0; i < (cards.length); i++)
        {
        	if (cards[i] != null)
        	{
        		cardToAddToScore = cards[i];
                //Checking if the previous and current cards are equal
                if (previousCard == null)
                {
                    numDuplicates = 1;
                }
                else if (previousCard.getValue() == cards[i].getValue())
                {
                    numDuplicates++;
                    if (i == cards.length - 1) //Last card in array
                    {
                        countDuplicates();
                    }
                }
                else if (previousCard.getValue() != cards[i].getValue())
                {
                    countDuplicates();
                    numDuplicates = 1;
                }
                //Checking numInRow for any pairs
                previousCard = cards[i];
        	}
        }
    }
    /**
     * Helper method for detectDuplicates which checks the value of numDuplicates and increments
     * the counters for pairs, three of a kind, and four of a kind if needed
     */
    private void countDuplicates()
    {
        if (numDuplicates == 4)
        {
            numFourOfKind++;
            score += cardToAddToScore.getValue();
        }
        else if (numDuplicates == 3)
        {
            numThreeOfKind++;
            score += cardToAddToScore.getValue();
        }
        else if (numDuplicates == 2)
        {
            numPairs++;
            score += cardToAddToScore.getValue();
        }
    }
    /**
     * Searched for 5 cards in a row, increments the count for straight if it finds one
     * @param cards Cards to check
     * @return      True if a straight was detected, false otherwise
     */
    private boolean detectStraight(Card[] cards)
    {
        Card previousCard = null;
        for (Card c : cards)
        {
        	if (c != null)
        	{
        		//Checking if the next card is next in value from the previous
                if (previousCard == null)
                {
                    numInRow = 1;
                }
                else if (previousCard.getValue() == (c.getValue() - 1))
                {
                    numInRow++;
                }
                else if ((previousCard.getValue() != (c.getValue() - 1)) && (previousCard.getValue() != c.getValue()))
                {
                    numInRow = 1;
                }
                //Checking for a straight
                if (numInRow >= NUM_FOR_STRAIGHT)
                {
                    straight = 1;
                    straightHighCardValue = c.getValue();
                }
                previousCard = c;
        	}
        }
        if (straight > 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    /**
     * Checks for 5 cards of the same suit
     * @param cards The cards to check
     * @return      True if a flush is found, false if not
     */
    private boolean detectFlush(Card[] cards)
    {
    	/*
        for (int i = 0; i < (cards.length - 1); i++)
        {
        	if  (cards[i] != null)
        	{
        		int tempFaceCount = 0;
                Card previousCard = null;
                for (Card c : cards)
                {
                	if (c != null)
                	{
                		if (previousCard == null)
                        {
                            tempFaceCount = 1;
                        }
                        else if ((cards[i].getSuit() == c.getSuit()) && (cards[i] != c))
                        {
                            tempFaceCount++;
                        }
                        previousCard = c;
                	}
                }
                if (tempFaceCount >= numSameFace)
                {
                    numSameFace = tempFaceCount;
                }
                if (numSameFace >= NUM_FOR_FLUSH)
                {
                    flush = 1;
                    flushHighCardValue = cards[i].getValue();
                }
        	}
        }
        if (flush > 0)
        {
            return true;
        }
        else
        {
            return false;
        } */
    	for (int i = 1; i < cards.length; i++)
    	{
    		if (cards[i].getSuit() != cards[i-1].getSuit())
    		{ // check for matching suits
    			return false; // suits don't match, return false
    		}
    	}
    	return true; // matching suits. is a flush, return true
    }
}

