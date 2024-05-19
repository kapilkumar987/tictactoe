package strategies.bot_playing;

import exceptions.InvalidGameStateException;
import models.Board;
import models.Cell;
import models.CellStatus;
import models.Pair;

import java.util.List;

public class EasyBotPlayingStrategy implements BotPlayingStrategy
{

    @Override
    public Pair makeMove(Board board) throws InvalidGameStateException {
        for(List<Cell> row: board.getCells())
        {
            for (Cell cell: row)
            {
                if(cell.getCellStatus().equals(CellStatus.UNOCCUPIED))
                {
                    return new Pair(cell.getRow(), cell.getColumn());
                }
            }
        }

        throw new InvalidGameStateException("No place for bot to make a move");
    }
}
