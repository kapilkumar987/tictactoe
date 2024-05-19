package models;

// Liskov's Substitution Principle - Inheritance should be done in a correct manner.

import exceptions.InvalidGameStateException;
import factories.BotPlayingStrategyFactory;
import strategies.bot_playing.BotPlayingStrategy;

public class BotPlayer extends Player
{
    private BotLevel botLevel;

    private BotPlayingStrategy botPlayingStrategy;

    public BotPlayer(String name, Symbol symbol, BotLevel botLevel)
    {
        super(name, symbol);
        this.botLevel = botLevel;
        this.botPlayingStrategy = BotPlayingStrategyFactory.getBotPlayingStrategy(botLevel);
    }

    @Override
    public Pair makeMove(Board board) throws InvalidGameStateException
    {
        return botPlayingStrategy.makeMove(board);
    }
}
