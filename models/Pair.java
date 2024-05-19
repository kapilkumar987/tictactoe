package models;

public class Pair
{
    // Attributes | member variables of the class

    private int x;
    private int y;

    public Pair(int x, int y) // Parameterized constructor
    {
        this.x = x;
        this.y = y;
    }

    // Getters and Setters 

    public int getX()
    {
        return this.x;
    }

    public int getY()
    {
        return this.y;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public void setY(int y)
    {
        this.y = y;
    }
}
