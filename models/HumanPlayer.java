package models;

import java.util.Scanner;

public class HumanPlayer extends Player
{
    private int pendingUndoCount;

    public HumanPlayer(String name, Symbol symbol)
    {
        super(name, symbol);
    }

    @Override
    public Pair makeMove(Board board)
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Its " + this.getName() + "'s turn. Enter row and column");
        int row = scanner.nextInt();
        int column = scanner.nextInt();

        return new Pair(row, column);
    }

    public void decrementUndoCount()
    {
        this.pendingUndoCount--;
    }

    // Getters and Setters

    public int getPendingUndoCount()
    {
        return this.pendingUndoCount;
    }

    public void setPendingUndoCount(int pendingUndoCount)
    {
        this.pendingUndoCount = pendingUndoCount;
    }
}
