package strategies.check_for_win;

import models.Board;
import models.Cell;
import models.Move;
import models.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrderOneWinningStrategy implements PlayerWonStrategy
{

    private List<HashMap<Character, Integer>> rowsMap;
    private List<HashMap<Character, Integer>> colsMap;
    private HashMap<Character, Integer> diagonalMap;
    private HashMap<Character, Integer> reverseDiagonalMap;

    private int n;
    public OrderOneWinningStrategy(int n)
    {
        rowsMap = new ArrayList<>();
        colsMap = new ArrayList<>();
        diagonalMap = new HashMap<Character, Integer>();
        reverseDiagonalMap = new HashMap<Character, Integer>();
        for(int i = 0; i < n; i++)
        {
            rowsMap.add(new HashMap<Character, Integer>());
            colsMap.add(new HashMap<Character, Integer>());
        }

        this.n = n;
    }

    @Override
    public boolean checkForWin(Board board, Cell currentCell)
    {
        // Update the maps

        int row = currentCell.getRow();
        int col = currentCell.getColumn();
        char symbol = currentCell.getPlayer().getSymbol().getS();

        HashMap<Character, Integer> rowMap = rowsMap.get(row);
        rowMap.put(symbol, rowMap.getOrDefault(symbol, 0) + 1);

        HashMap<Character, Integer> colMap = colsMap.get(col);
        colMap.put(symbol, colMap.getOrDefault(symbol, 0) + 1);

        if(checkIfCellIsOnDiagonal(row, col))
        {
            diagonalMap.put(symbol, diagonalMap.getOrDefault(symbol, 0) + 1);
        }

        if(checkIfCellIsOnReverseDiagonal(row, col, board.getSize()))
        {
            reverseDiagonalMap.put(symbol, reverseDiagonalMap.getOrDefault(symbol, 0) + 1);
        }

        // Check if player has won

        if(rowsMap.get(row).get(symbol) == board.getSize())
        {
            return  true;
        }

        if(colsMap.get(col).get(symbol) == board.getSize())
        {
            return true;
        }

        if (checkIfCellIsOnDiagonal(row,col) && diagonalMap.get(symbol) == board.getSize())
        {
            return true;
        }

        if(checkIfCellIsOnReverseDiagonal(row, col, board.getSize()) && reverseDiagonalMap.get(symbol) == board.getSize())
        {
            return true;
        }

        return false;
    }

    private boolean checkIfCellIsOnDiagonal(int row, int column)
    {
        return row == column;
    }

    public boolean checkIfCellIsOnReverseDiagonal(int row, int column, int n)
    {
        return row + column == n-1;
    }

    @Override
    public void handleUndo(Move move)
    {
        Cell cell = move.getCell();
        int row = cell.getRow();
        int column = cell.getColumn();

        Player player = move.getPlayer();
        char symbol = player.getSymbol().getS();

        HashMap<Character, Integer> rowMap = rowsMap.get(row);
        rowMap.put(symbol, rowMap.get(symbol) -1);

        HashMap<Character, Integer> colmap = colsMap.get(column);
        colmap.put(symbol, colmap.get(symbol) -1);

        if(checkIfCellIsOnDiagonal(row, column))
        {
            diagonalMap.put(symbol, diagonalMap.get(symbol) -1);
        }

        if(checkIfCellIsOnReverseDiagonal(row, column, this.n))
        {
            reverseDiagonalMap.put(symbol, reverseDiagonalMap.get(symbol) -1);
        }

    }
}
