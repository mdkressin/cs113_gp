package edu.miracosta.cs113;

public class Card
{
    //Data
    private String face;
    private int value;

    //Constructor
    public Card(String face, int value)
    {
        setFace(face);
        setValue(value);
    }

    //Setters
    private void setFace(String face)
    {
        this.face = face;
    }
    private void setValue(int value)
    {
        this.value = value;
    }

    //Getters
    public String getFace()
    {
        return face;
    }
    public int getValue()
    {
        return value;
    }
}
