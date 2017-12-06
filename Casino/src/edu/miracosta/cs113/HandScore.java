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

    //Default constructor
    public HandScore()
    {
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
            System.out.println(c.toString());
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
        System.out.println("NumPairs: " + numPairs);
        System.out.println("NumThreeOfKind: " + numThreeOfKind);
        System.out.println("NumFourOfKind: " + numFourOfKind);
        System.out.println("Straight: " + straight);
        System.out.println("Flush: " + flush);
        score = 0;
        score += numFourOfKind  * FOUR_OF_A_KIND;
        score += flush          * FLUSH;
        score += straight       * STRAIGHT;
        if ((numThreeOfKind > 0) && (numPairs > 0))
        {
        	score += FULL_HOUSE;
        }
        else
        {
        	score += numThreeOfKind * THREE_OF_A_KIND;
            score += numPairs       * PAIR;
        }
        for (int i = 0; (i < cards.length); i++)
        {
            score += cards[i].getValue();
        }
        return score;
    }
    /**
     * Resets all counters to 0
     */
    private void resetValues()
    {
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
                if (cards[i].getValue() > cards[i + 1].getValue())
                {
                    Card tempCard = cards[i + 1];
                    cards[i + 1] = cards[i];
                    cards[i] = tempCard;
                    sorted = false;
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
    /**
     * Helper method for detectDuplicates which checks the value of numDuplicates and increments
     * the counters for pairs, three of a kind, and four of a kind if needed
     */
    private void countDuplicates()
    {
        if (numDuplicates == 4)
        {
            numFourOfKind++;
        }
        else if (numDuplicates == 3)
        {
            numThreeOfKind++;
        }
        else if (numDuplicates == 2)
        {
            numPairs++;
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
            //Checking if the next card is next in value from the previous
            if (previousCard == null)
            {
                numInRow = 1;
            }
            else if (previousCard.getValue() == (c.getValue() + 1))
            {
                numInRow++;
            }
            else if (previousCard.getValue() != (c.getValue() + 1))
            {
                numInRow = 1;
            }
            //Checking for a straight
            if (numInRow >= NUM_FOR_STRAIGHT)
            {
                straight = 1;
            }
            previousCard = c;
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
        for (int i = 0; i < (cards.length - 1); i++)
        {
            int tempFaceCount = 0;
            Card previousCard = null;
            for (Card c : cards)
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
            if (tempFaceCount >= numSameFace)
            {
                numSameFace = tempFaceCount;
            }
            if (numSameFace >= NUM_FOR_FLUSH)
            {
                flush = 1;
            }
        }
        if (flush > 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}

