package exceptions;

public class InvalidGameStateException extends Exception
{
    public InvalidGameStateException(String message)
    {
        super(message);
    }
}
