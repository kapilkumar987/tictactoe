package models;

public class Cell
{
    // attributes | member variables of the class
    private int row;
    private int column;
    private CellStatus cellStatus;

    private Player player; // which person is present on the Cell | Cell has player

    public Cell(int row, int column, CellStatus cellStatus) // Parameterized constructor
    {
        this.row = row;
        this.column = column;
        this.cellStatus = cellStatus;
    }


    // Getters and Setters

    public int getRow()
    {
        return this.row;
    }

    public void setRow(int row)
    {
        this.row = row;
    }

    public int getColumn()
    {
        return this.column;
    }

    public void setColumn(int column)
    {
        this.column = column;
    }

    public CellStatus getCellStatus()
    {
        return this.cellStatus;
    }

    public void setCellStatus(CellStatus cellStatus)
    {
        this.cellStatus = cellStatus;
    }

    public Player getPlayer() {
        return this.player;
    }

    public void setPlayer(Player player) {
        this.player = player;
        this.cellStatus = CellStatus.OCCUPIED;
    }

    public void removePlayer()
    {
        // this.player = null;
        this.cellStatus = CellStatus.UNOCCUPIED;
    }

    public boolean isUnoccupied()
    {
        return this.player == null && cellStatus.equals(CellStatus.UNOCCUPIED);
    }

    public void printCell()
    {
        if(cellStatus.equals(CellStatus.UNOCCUPIED))
        {
            System.out.print(" _ ");
        }
        else
        {
            System.out.print(" " + this.player.getSymbol().getS() + " ");
        }
    }

}
