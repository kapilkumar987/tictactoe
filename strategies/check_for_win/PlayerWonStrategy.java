package strategies.check_for_win;

import models.Board;
import models.Cell;
import models.Move;

public interface PlayerWonStrategy
{
    public boolean checkForWin(Board board, Cell currentCell);
    public void handleUndo(Move move);
}
