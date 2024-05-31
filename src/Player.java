import java.util.ArrayList;

/**
 * The Player class represents a Player. A player is a human player with a name, amount of coins, an inventory,
 * amount of stars, a space their on, mops they can play on, if they are receiving double coins, if they passed a star,
 * if their turn is over, follows the logic of the game, if they're a bot, the players they're playing withhow many
 * spaces they will move, they have to use a cursed dice block and if they have to use a halfway dice block
 */
public class Player {

    private ArrayList<String> playerInventory;
    private int playerCoins;
    private int playerSpace;
    private String name;
    private Maps map;
    private boolean doubled;
    private boolean passedStar;
    private int stars;
    private boolean turnOver;
    private MarioPartyGame gameLogic;
    boolean isBot;
    private Items itemLogic;
    private int currentSpacesMoved;
    private boolean cursedBlockActive;
    private boolean halfwayDiceActive;
    private ArrayList<Player> players;

    /**
     * Constructor for the Player class. This creates a new instance of a Player given the below parameters and with
     * set attributes.
     *
     * @param currentMap represents the current map the player is playing on
     * @param isBot      represents if the player is an added bot or not
     */
    public Player(Maps currentMap, boolean isBot) {
        playerInventory = new ArrayList<>();
        playerSpace = 0;
        playerCoins = 0;
        map = currentMap;
        passedStar = false;
        stars = 0;
        turnOver = false;
        gameLogic = new MarioPartyGame();
        this.isBot = isBot;
        currentSpacesMoved = 0;
    }

    /**
     * The getPlayerInventory method will give the player's inventory
     *
     * @return a list representing a player's inventory
     */
    public ArrayList<String> getPlayerInventory() {
        return playerInventory;
    }

    /**
     * The addToInventory method will add a given item to the player's inventory
     *
     * @param item a String representing the item that will be added to the player's inventory
     */
    public void addToInventory(String item) {
        playerInventory.add(item);
    }

    /**
     * The setName parameter sets the player's name to a given String
     *
     * @param name a String representing the name the player's name will be set to
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * The getName parameter will return a String which is the player's name
     *
     * @return a String representing the player's name
     */
    public String getName() {
        return name;
    }

    /**
     * The updatePlayerSpace method will update a player's space according to how many spaces they will move. It will
     * the return a String showing the space they landed on and the effects of that space on the player
     *
     * @param spacesMoved an integer representing the amount of spaces the player will move
     * @param player      represents the player the change is being made to
     * @return a String representing all the changes/effects that occurred when the player moved
     */
    public String updatePlayerSpace(int spacesMoved, Player player) {
        String returnString = "";
        int previousSpace = playerSpace;
        final String resetColor = "\u001B[0m";
        String textColor = "";
        spacesMoved += currentSpacesMoved;
        if (cursedBlockActive) {
            spacesMoved = (int) (Math.random() * 3) + 1;
            cursedBlockActive = false;
        }
        if (halfwayDiceActive) {
            spacesMoved = (int) (Math.random() * 5) + 1;
            halfwayDiceActive = false;
        }
        playerSpace += spacesMoved;
        returnString += name + " has moved " + spacesMoved + " spaces.\n";
        if (playerSpace > map.getMaxSpaces()) {
            playerSpace = playerSpace - map.getMaxSpaces();
        }
        if (map.getBlueSpaces().contains(playerSpace)) {
            textColor = "\u001B[34m";
            if (doubled) {
                playerCoins += 6;
                returnString += textColor + name + " landed on a blue space.\nYou get 6 coins." + resetColor;
            } else {
                playerCoins += 3;
                returnString += textColor + name + " landed on a blue space.\nYou get 3 coins." + resetColor;
            }
        }
        if (map.getRedSpaces().contains(playerSpace)) {
            textColor = "\u001B[31m";
            if (doubled) {
                playerCoins -= 6;
                returnString += textColor + name + " landed on a red space.\nYou lose 6 coins." + resetColor;
            } else {
                playerCoins -= 3;
                returnString += textColor + name + " landed on a red space.\nYou lose 3 coins." + resetColor;
            }
        }
        if (map.getBowserSpaces().contains(playerSpace)) {
            textColor = "\u001B[35m";
            returnString += textColor + name + " landed on a bowser space. You have a chance to: \nLose a random amount of coins" +
                    "\nLose half your coins\nLose a star\nGet 100 stars!!!!!!!\n\nA wheel will be spun to determine what you get.\n"
                    + bowserSpaceResults() + resetColor;
        }
        if (map.getLuckySpaces().contains(playerSpace)) {
            textColor = "\u001B[32m";
            returnString += textColor + name + " landed on a lucky space. You have a chance to: \nGain a random amount of coins" +
                    "\nGet a mushroom\nGet a Double Dice\nTeleport 10-20 spaces behind the star space." +
                    "\nA wheel will be spun to determine what you get.\n" + luckySpaceResults() + resetColor;
            previousSpace = playerSpace;
        }
        if (map.getItemSpaces().contains(playerSpace)) {
            textColor = "\u001B[36m";
            returnString += textColor + name + " landed on a item space. You will randomly receive an item from the item pool.\n" +
                    itemSpaceResults() + resetColor;
        }
        if (playerCoins < 0) {
            playerCoins = 0;
        }
        if (map.getStarSpace() > previousSpace && map.getStarSpace() <= playerSpace) {
            passedStar = true;
        } else {
            passedStar = false;
        }
        returnString += "\n" + name + " currently has " + playerCoins + " coins.\n" + name +
                " is currently on space " + playerSpace;
        turnOver = true;
        currentSpacesMoved = 0;
        return returnString;
    }

    /**
     * The currentPlayerSpace method will return an integer representing the player's current space
     *
     * @return an integer representing the player's current space
     */
    public int currentPlayerSpace() {
        return playerSpace;
    }

    /**
     * The passedStar method will return a boolean representing if the player passed by a star or not
     *
     * @return a boolean representing if the player passed by a star or not
     */
    public boolean passedStar() {
        return passedStar;
    }

    /**
     * The updatePlayerCoins method will update the amount of coins that the player has
     *
     * @param coins an integer representing the amount of coins the user will gain/lose
     */
    public void updatePlayerCoins(int coins) {
        playerCoins += coins;
    }

    /**
     * The amountOfCoins method will give how many coins the player has
     *
     * @return an integer representing the amount of coins the player has
     */
    public int amountOfCoins() {
        return playerCoins;
    }

    /**
     * The haveItem method will determine if the user has the item that they inserted in their inventory
     *
     * @param item a String representing the item the user inserted
     * @return a boolean representing if the user has that item in their inventory or not
     */
    public boolean haveItem(String item) {
        boolean haveItem = false;
        if (playerInventory.indexOf(item) != -1) {
            haveItem = true;
        }
        return haveItem;
    }

    /**
     * The amountOfStars method will give how many stars the player has
     *
     * @return an integer representing the amount of stars the player has
     */
    public int amountOfStars() {
        return stars;
    }

    /**
     * The updatePlayerStars method will update the amount of stars that the player has by one
     */
    public void updateStars() {
        stars++;
    }

    /**
     * The bowserSpaceResults will determine the event that occurs when landing on a bowser space
     *
     * @return a String representing the event that occurred and how it affected the player
     */
    public String bowserSpaceResults() {
        String results = "";
        int probabilityNum = (int) (Math.random() * 20) + 1;
        if (probabilityNum <= 10) {
            int coinsLost = (int) (Math.random() * 13) + 3;
            playerCoins -= coinsLost;
            if (playerCoins < 0) {
                playerCoins = 0;
            }
            results = "You got a call from an unknown number. You answer and they ask to send a picture of your workplace ID for confirmation.\nYou send it over but it turns out it was Bowser with a voice changer!\nHe sneaks into your workplace and steals some of your money.\nYou lost " + coinsLost + " coins!";
        } else if (probabilityNum > 11 && probabilityNum <= 17) {
            int coinsLost = playerCoins / 2;
            playerCoins -= coinsLost;
            results = "There was a data breach at Google.\nSince you use the same password for every account, someone was able to log in to your bank account and steal half of your coins.\nYou lost " + (coinsLost) + " coins.";
        } else if (probabilityNum > 17 && probabilityNum <= 19 ) {
            stars--;
            results = "While you weren't looking.\nA boo ghost snuck a picture of your credit card.\nThey took one your stars.";
        } else {
            results = "Bowser says nothing and runs away.";
        }
        return results;
    }

    /**
     * The luckySpaceResults will determine the event that occurs when landing on a lucky space
     *
     * @return a String representing the event that occurred and how it affected the player
     */
    public String luckySpaceResults() {
        String results = "";
        int probabilityNum = (int) (Math.random() * 20) + 1;
        if (probabilityNum <= 9) {
            int coinsGained = (int) (Math.random() * 18) + 3;
            playerCoins += coinsGained;
            results = name + " gained " + coinsGained + " coins!";
        } else if (probabilityNum > 9 && probabilityNum <= 14) {
            playerInventory.add("Mushroom");
            results = name + " got a mushroom!";
        } else if (probabilityNum > 14 && probabilityNum <= 17) {
            playerInventory.add("Double Dice");
            results = name + " got a double dice!";
        } else if (probabilityNum == 18) {
            playerInventory.add("Triple Dice");
            results = name + " got a triple dice!";
        } else {
            playerSpace = map.getStarSpace() - ((int) (Math.random() * 10) + 10);
            results = name + " got teleported closer to the star!";
        }
        return results;
    }

    /**
     * The itemSpaceResults will determine the event that occurs when landing on an item space
     *
     * @return a String representing the event that occurred and how it affected the player
     */
    public String itemSpaceResults() {
        itemLogic = new Items();
        String results = "As a result of keeping an active firewall, you got a gift from a security program for your good work.\n";
        String itemAdded = itemLogic.getGameItems().get((int) (Math.random() * (itemLogic.getAmountOfItems() - 1)));
        addToInventory(itemAdded);
        results += name + " randomly got a " + itemAdded;
        return results;
    }

    /**
     * The buyStar method simulates a player buying a star when they pass one.
     *
     * @param answer a String representing the user's choice of buying a star (Either yes or no)
     * @return a String representing the effect of the user's choice
     */
    public String buyStar(String answer) {
        if (answer.equals("yes")) {
            updatePlayerCoins(-20);
            updateStars();
            return "You have bought a star for 20 coins!" + map.newStarSpace();
        } else {
            return "You have decided not to buy a star";
        }
    }

    /**
     * The isTurnOver method returns if the user's turn is over or not
     *
     * @return a boolean representing if the turn of a user is over or not
     */
    public boolean isTurnOver() {
        return turnOver;
    }

    /**
     * The actionSelected method simulates the user's choice of action in the game. It will give the effect of that action
     *
     * @param actionNum an integer representing what number choice the player made
     * @param player    a Player representing who this choice is being made by
     * @return a String representing the effects of the choice made
     */
    public String actionSelected(int actionNum, Player player) {
        String actionDoneByPlayer = "";
        if (actionNum == 1) {
            actionDoneByPlayer = updatePlayerSpace(gameLogic.diceRoll(), player);
        }
        if (actionNum == 2) {

        }
        return actionDoneByPlayer;
    }

    /**
     * The endOfTurn method will make it so the player's turn is no longer over
     */
    public void endOfTurn() {
        turnOver = false;
    }

    /**
     * The botAction method will simulate a bot and their choice of actions when it is their turn along with its effect
     * in the game
     *
     * @param bot     a Player representing the bot who is making the choices
     * @param players a ArrayList<Player> representing the list of players that are in the game
     * @return a String representing what the bot decided to do and its effects
     */
    public String botAction(Player bot, ArrayList<Player> players) {
        itemLogic = new Items(players);
        String botActionResults = "";
        int previousSpace = playerSpace;
        if (playerInventory.size() > 0) {
            int probabilityOfDoing = (int) (Math.random() * 4) + 1;
            if (probabilityOfDoing > 1) {
                botActionResults += useItem(playerInventory.get((int) (Math.random() * playerInventory.size())), bot, players);
                botActionResults += updatePlayerSpace((int) (Math.random() * 10) + 1, bot) + "\n";
            } else {
                botActionResults += updatePlayerSpace((int) (Math.random() * 10) + 1, bot) + "\n";
            }
        } else {
            botActionResults += updatePlayerSpace((int) (Math.random() * 10) + 1, bot) + "\n";
        }
        if (map.getStarSpace() > previousSpace && map.getStarSpace() <= playerSpace) {
            passedStar = true;
        } else {
            passedStar = false;
        }
        if (passedStar == true && playerCoins >= 20) {
            botActionResults += name + " passed by a star space. They bought it for 20 coins.";
            stars++;
        } else if (passedStar == true) {
            botActionResults += name + " passed by a star space.\nUnfortunately they do not have enough coins to buy a star.";
        }
        isBot = true;
        return botActionResults + "\n";
    }

    /**
     * The useItem method simulates the user using an item of their choice and how it'll affect their or other players'
     * next turn
     *
     * @param item    a String representing the item being used
     * @param player  a Player representing the player who is using an item
     * @param players a ArrayList<Player> representing a list of players in the game
     * @return a String representing the effects of the item that was used
     */
    public String useItem(String item, Player player, ArrayList<Player> players) {
        itemLogic = new Items();
        String itemResults = "";
        itemResults = itemLogic.itemEffects(item, player, players);
        playerInventory.remove(playerInventory.indexOf(item));
        return itemResults;
    }

    /**
     * The changeSpacesMoved method will change amount spaces the player will move along with their dice roll.
     *
     * @param change an integer representing the amount of spaces that will be added
     */
    public void changeSpacesMoved(int change) {
        currentSpacesMoved += change;
    }

    /**
     * The cursedBlockActive method will make it so the player will use a cursedBlock on their next turn
     */
    public void cursedBlockActive() {
        cursedBlockActive = true;
    }

    /**
     * The halfwayDiceActive method will make it so the player will use a halfwayDice on their next turn
     */
    public void halfwayDiceActive() {
        halfwayDiceActive = true;
    }

    /**
     * The setPlayerSpace method will set the player's current space to the inserted space
     *
     * @param space an integer representing the space that the player's space will be set to
     */
    public void setPlayerSpace(int space) {
        playerSpace = space;
    }

    /**
     * The setPlayers method will set the players that are playing into the player's data
     *
     * @param players an ArrayList<Player> representing the players playing the game
     */
    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public int getSpace(){
    return playerSpace;}
}