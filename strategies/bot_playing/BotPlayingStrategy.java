package strategies.bot_playing;

import exceptions.InvalidGameStateException;
import models.Board;
import models.Pair;

public interface BotPlayingStrategy
{
    public Pair makeMove(Board board) throws InvalidGameStateException;
}
