package factories;

import models.BotLevel;
import strategies.bot_playing.BotPlayingStrategy;
import strategies.bot_playing.EasyBotPlayingStrategy;

public class BotPlayingStrategyFactory
{
    public static BotPlayingStrategy getBotPlayingStrategy(BotLevel botLevel)
    {
        return switch (botLevel)
        {
            case EASY, MEDIUM, DIFFICULT -> new EasyBotPlayingStrategy();
            default -> throw new UnsupportedOperationException("The given bot level is not supported");
        };
    }
}
