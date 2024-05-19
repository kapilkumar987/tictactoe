package models;

import java.util.ArrayList;
import java.util.List;

public class Board
{
    private List<List<Cell>> cells; // 2D array of cells

    public Board(int n) // Parameterized constructor
    {
        this.cells = new ArrayList<>();

        for(int i = 0; i < n; i++)
        {
            List<Cell> row = new ArrayList<>(n);
            for(int j = 0; j < n; j++)
            {
                row.add(new Cell(i, j, CellStatus.UNOCCUPIED));
            }

            this.cells.add(row);
        }
    }

    public void printBoard()
    {
        int n = this.cells.size();

        for(List<Cell> row: this.cells)
        {
            for(int j = 0; j < n; j++)
            {
                Cell cell = row.get(j);
                cell.printCell();
            }

            System.out.println();
        }
    }

    public boolean checkIfCellIsUnoccupied(int row, int column)
    {
        Cell cell = this.cells.get(row).get(column);
        return cell.isUnoccupied();
    }

    public void setPlayer(int row, int column, Player player)
    {
        Cell cell = this.cells.get(row).get(column);
        cell.setPlayer(player);
    }

    public Cell getCell(int row, int column)
    {
        return this.cells.get(row).get(column);
    }

    public int getSize()
    {
        return this.cells.size();
    }

    public void resetBoard()
    {
        for(List<Cell> row: cells)
        {
            for(Cell cell: row)
            {
                cell.removePlayer();
            }
        }
    }

    // Getters and Setters

    public List<List<Cell>> getCells()
    {
        return this.cells;
    }

    public void setCells(List<List<Cell>> cells)
    {
        this.cells = cells;
    }
}
