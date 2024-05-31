import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;


/**
 * The MarioPartyGame class represents the actual game of MarioParty. The game has an amount of players, current map,
 * max amount of turns, who the first player will be, what the current turn is, what maps there are, and the order the players
 * will play by
 */
public class MarioPartyGame {
    private int players;
    private Maps map;
    private int maxTurns;
    private int firstPlayer;
    private int currentTurn;
    private ArrayList<String> maps;
    private ArrayList<Player> playerOrder;

    /**
     * Constructor for the MarioPartyGame class. This creates a new instance of a game with no parameters.
     * This is mainly for smoothness and to fix bugs in the game
     */
    public MarioPartyGame() {
        maps = new ArrayList<>();
        maps.add("lava castle");
    }

    /**
     * Constructor for the MarioPartyGame class. This creates a new instance of a game given the below parameters
     *
     * @param players  represents the number of players playing the game
     * @param map      represents the map that is being played on
     * @param maxTurns represetns the max amount of turns the game will last
     */
    public MarioPartyGame(int players, Maps map, int maxTurns) {
        this.players = players;
        this.map = map;
        this.maxTurns = maxTurns;
    }

    /**
     * The isMap method will determine if the given map by the user is a map in the map pool for the game.
     *
     * @param map a string that represents the map inserted by the user
     * @return returns true if map is in map pool and false if it's not.
     */
    public boolean isMap(String map) {
        boolean isMap = false;
        int mapsIndex = 0;
        while (mapsIndex < maps.size()) {
            if (maps.get(mapsIndex).equals(map.toLowerCase())) {
                isMap = true;
            }
            mapsIndex++;
        }
        return isMap;
    }

    /**
     * The validAmountOfTurns method will determine if the amount of turns inserted by the user follows the
     * range of valid turns set by the game
     *
     * @param turns integer that represents the amount of turns the user inserted
     * @return true or false if it is a valid amount of turns
     */
    public boolean validAmountOfTurns(int turns) {
        boolean valid = true;
        if (turns % 5 != 0) {
            valid = false;
        }
        if (turns > 40) {
            valid = false;
        }
        if (turns < 10) {
            valid = false;
        }
        return valid;
    }

    /**
     * The diceRoll method simulates a roll of a die with 10 sides.
     *
     * @return returns a number from 1-10.
     */
    public int diceRoll() {
        int spacesMove = (int) (Math.random() * 10) + 1;
        return spacesMove;
    }

    /**
     * The gameOver method will determine if the game is over by checking the current turn of the game.
     *
     * @param currentTurn integer that represents the current turn that the game is at
     * @return returns true or false if the game is over
     */
    public boolean gameOver(int currentTurn) {
        if (currentTurn > maxTurns) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * The determineFirst method will simulate every player rolling a die and then making whoever got the highest go first.
     * Then it'll create an order of players for which the game will follow.
     *
     * @param players a list full of Player objects which represents all the players that are playing the game
     * @return a String representing everyone's dice roll number from greatest to least and the player order
     */
    public String determineFirst(ArrayList<Player> players) {
        ArrayList<String> playerDiceRollNums = new ArrayList<>();
        int highestNum = 0;
        ArrayList<String> sortedPlayerDiceRollNums = new ArrayList<>();
        playerOrder = new ArrayList<>();
        String orderOfPlayers = "";
        for (int i = 0; i < players.size(); i++) {
            playerDiceRollNums.add(Integer.toString((int) (Math.random() * 9) + 1));
            orderOfPlayers += players.get(i).getName() + "   ";
        }
        orderOfPlayers = playerDiceRollNums + "\n" + orderOfPlayers;
        for (int i = 0; i < playerDiceRollNums.size(); i++) {
            sortedPlayerDiceRollNums.add(playerDiceRollNums.get(i));
        }
        for (int i = 0; i <= playerDiceRollNums.size(); i++) {
            Collections.sort(sortedPlayerDiceRollNums, Collections.reverseOrder());
        }
        for (int i = 0; i < players.size(); i++) {
            playerOrder.add(players.get(playerDiceRollNums.indexOf(sortedPlayerDiceRollNums.get(i))));
            playerDiceRollNums.set(playerDiceRollNums.indexOf(sortedPlayerDiceRollNums.get(i)), "0");
        }
        firstPlayer = (players.indexOf(playerOrder.get(0))) + 1;
        for (int i = 0; i < players.size(); i++) {
            if (i == 0) {
                orderOfPlayers += "\n" + playerOrder.get(0).getName() + " is first";
            }
            if (i == 1) {
                orderOfPlayers += "\n" + playerOrder.get(1).getName() + " is second";
            }
            if (i == 2) {
                orderOfPlayers += "\n" + playerOrder.get(2).getName() + " is third";
            }
            if (i == 3) {
                orderOfPlayers += "\n" + playerOrder.get(3).getName() + " is fourth";
            }
        }
        return "Alright let's see who goes first!\n" + orderOfPlayers;
    }

    /**
     * The getOrderOfPlayers method will give the order of players in which the game will be played in
     *
     * @return the order of players
     */
    public ArrayList<Player> getOrderOfPlayers() {
        return playerOrder;
    }

    /**
     * The winner method will determine who the winner is after a game is over. It will check who has the most stars and
     * then tells you who it is.
     *
     * @return the player with the most amount of stars (the winner) or a tie if the star amount are the same.
     */
    public String winner() {
        String winner = "";
        ArrayList<Integer> mostStars = new ArrayList<>();
        ArrayList<Integer> sortedMostStars = new ArrayList<>();
        for (int i = 0; i < playerOrder.size(); i++) {
            mostStars.add(playerOrder.get(i).amountOfStars());
        }
        for (int i = 0; i < playerOrder.size(); i++) {
            sortedMostStars.add(mostStars.get(i));
        }
        winner += sortedMostStars + "\n";
        for (int i = 0; i < 4; i++) {
            winner += playerOrder.get(i).getName() + " ";
        }
        for (int i = 0; i < playerOrder.size(); i++) {
            Collections.sort(sortedMostStars, Collections.reverseOrder());
        }
        if (sortedMostStars.get(0) == sortedMostStars.get(1)) {
            winner += "\nIt was a tie";
        } else {
            winner += "\nThe winner is " + playerOrder.get(mostStars.indexOf(sortedMostStars.get(0))).getName();
        }
        return winner;
    }
}
