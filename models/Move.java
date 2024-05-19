package models;

public class Move
{
    // Attributes | member variables of the class

    private Player player; // who made the move
    private Cell cell; // where did you make the move

    public Move(Player player, Cell cell) // Parameterized constructor
    {
        this.player = player;
        this.cell = cell;
    }

    // Getters and Setters

    public Player getPlayer() {
        return this.player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Cell getCell() {
        return this.cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }
}
