package controllers;

import exceptions.BotCountExceededException;
import exceptions.InvalidGameStateException;
import models.Game;
import models.GameStatus;
import models.Player;

import java.util.List;

public class GameController
{
    public Game createGame(List<Player> players, int undoLimitPerPlayer) throws BotCountExceededException
    {
        return Game.getBuilder()
                .setPlayers(players)
                .setUndoLimitPerPlayer(undoLimitPerPlayer)
                .build();
    }

    public GameStatus getGameStatus(Game game)
    {
        return game.getGameStatus();
    }

    public void printBoard(Game game)
    {
        game.printBoard();
    }

    public void makeMove(Game game) throws InvalidGameStateException {
        game.makeMove();
    }

    public Player getCurrentPlayer(Game game)
    {
        return game.getCurrentPlayer();
    }

    public void undo(Game game)
    {
        game.undo();
    }

    public void replay(Game game) throws InvalidGameStateException
    {
        game.replay();
    }
}
