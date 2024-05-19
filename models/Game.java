package models;

import exceptions.BotCountExceededException;
import exceptions.InvalidGameStateException;
import strategies.check_for_win.OrderOneWinningStrategy;
import strategies.check_for_win.PlayerWonStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game // Game class has lots of attributes | Builder Design Pattern
{
    // Game has Board | Aggregation

    private Board board;
    private List<Player> players;
    private GameStatus gameStatus;
    private int currentPlayerIdx; // needs to know who is the current player that is making the move

    private List<Move> moves; // keep tracking of moves

    private PlayerWonStrategy winningStrategy;

    private Game(Board board, List<Player> players, GameStatus gameStatus, int currentPlayerIdx, List<Move> moves, PlayerWonStrategy winningStrategy, int undoLimitPerPlayer)
    {
        this.board = board;
        this.players = players;
        this.gameStatus = gameStatus;
        this.currentPlayerIdx = currentPlayerIdx;
        this.moves = moves;
        this.winningStrategy = winningStrategy;

        for(Player player: players)
        {
            if(player instanceof HumanPlayer)
            {
                HumanPlayer humanPlayer = (HumanPlayer) player;
                humanPlayer.setPendingUndoCount(undoLimitPerPlayer);
             }
        }
    }

    public static GameBuilder getBuilder()
    {
        return new GameBuilder();
    }

    // Getters and Setters


    public Board getBoard() {
        return this.board;
    }

    public List<Player> getPlayers()
    {
        return this.players;
    }

    public GameStatus getGameStatus()
    {
        return this.gameStatus;
    }

    public int getCurrentPlayerIdx()
    {
        return this.currentPlayerIdx;
    }

    public List<Move> getMoves()
    {
        return this.moves;
    }

    public void printBoard()
    {
        this.board.printBoard();
    }

    public void makeMove() throws InvalidGameStateException {
        Player player = this.players.get(currentPlayerIdx);
        Pair rowColumn = player.makeMove(board);

        while(!this.board.checkIfCellIsUnoccupied(rowColumn.getX(), rowColumn.getY()))
        {
            if(player instanceof HumanPlayer)
            {
                System.out.println("Please make a move on a different cell");
            }

            rowColumn = player.makeMove(this.board);
        }

        this.board.setPlayer(rowColumn.getX(), rowColumn.getY(), player);
        Cell cell = this.board.getCell(rowColumn.getX(), rowColumn.getY());
        Move move = new Move(player, cell);
        this.moves.add(move);

        if(this.winningStrategy.checkForWin(this.board, cell))
        {
            this.gameStatus = GameStatus.ENDED;
            return;
        }
        else if (checkForDraw())
        {
            this.gameStatus = GameStatus.DRAWN;
            return;
        }

        this.currentPlayerIdx = (this.currentPlayerIdx + 1) % (this.board.getSize() - 1);

    }

    public void undo()
    {
        if(gameStatus.equals(GameStatus.IN_PROGRESS))
        {
            int previousPlayerIndex = this.currentPlayerIdx - 1;
            if(previousPlayerIndex < 0)
            {
                previousPlayerIndex = players.size() - 1;
            }

            Player player = this.players.get(previousPlayerIndex);
            if(player instanceof HumanPlayer)
            {
                HumanPlayer humanPlayer = (HumanPlayer) player;
                if(humanPlayer.getPendingUndoCount() > 0)
                {
                    System.out.println("Do you want to undo: (y/n)");
                    Scanner scanner = new Scanner(System.in);
                    String input = scanner.next();

                    if(input.charAt(0) == 'y' || input.charAt(0) == 'Y')
                    {
                        Move move = moves.remove(moves.size()-1);
                        Cell cell = move.getCell();
                        cell.removePlayer();

                        this.currentPlayerIdx = previousPlayerIndex;
                        this.winningStrategy.handleUndo(move);
                        System.out.println("We have successfully undid player: " + player.getName() + "'s last move");
                        humanPlayer.decrementUndoCount();

                        if(humanPlayer.getPendingUndoCount() == 0)
                        {
                            System.out.println("This was your last undo, no more undo attempts left");
                        }
                    }
                }
            }
            else
            {
                // Do nothing, it is a bot, bot cannot undo
            }
        }
    }


    public void replay() throws InvalidGameStateException
    {
        if(gameStatus == GameStatus.IN_PROGRESS)
        {
            throw new InvalidGameStateException("Game is still in progress");
        }

        board.resetBoard();
        int count = 1;
        for(Move move: this.moves)
        {
            Cell cell = move.getCell();
            Player player = cell.getPlayer();

            board.setPlayer(cell.getRow(), cell.getColumn(), player);
            System.out.println("Turn #" + count++ + ", player " + player.getName() + " makes a move");
            printBoard();
        }
    }

    public boolean checkForDraw()
    {
        int n = this.board.getSize();
        return n*n == this.moves.size();
    }


    public Player getCurrentPlayer()
    {
        return this.players.get(this.currentPlayerIdx);
    }


    public static class GameBuilder
    {
        private Board board;
        private List<Player> players;
        private GameStatus gameStatus;
        private int currentPlayerIdx;
        private List<Move> moves;
        private PlayerWonStrategy winningStrategy;
        private int undoLimitPerPlayer;

        public GameBuilder setPlayers(List<Player> players)
        {
            this.players = players;
            int n = players.size();
            this.board = new Board(n + 1);
            return this;
        }

        public GameBuilder setUndoLimitPerPlayer(int undoLimitPerPlayer)
        {
            this.undoLimitPerPlayer = undoLimitPerPlayer;
            return this;
        }

        public Game build() throws BotCountExceededException
        {
            int botCount = 0;

            for(Player player: players)
            {
                if(player instanceof BotPlayer)
                {
                    botCount++;
                }

                if(botCount > 1)
                {
                    throw new BotCountExceededException("Found more than 1 bots, maximum only 1 bot is alloed");
                }
            }

            return new Game(this.board, this.players, GameStatus.IN_PROGRESS, 0, new ArrayList<>(), new OrderOneWinningStrategy(board.getSize()), this.undoLimitPerPlayer);
        }
    }


}
