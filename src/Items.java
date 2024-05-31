import java.util.ArrayList;

/**
 * The Items class represents an Item list. An item list is a list of items that are currently in the game, and the
 * list of players that are going to use them
 */
public class Items {

    private ArrayList<String> gameItems;

    /**
     * Constructor for the Items class. This creates a new instance of a Items given the below parameters
     *
     * @param players an ArrayList<Player> representing the list of players that will be using these items
     */
    public Items(ArrayList<Player> players) {
        gameItems = new ArrayList<>();
        gameItems.add("Mushroom");
        gameItems.add("Double Dice");
        gameItems.add("Triple Dice");
        gameItems.add("Warp Block");
        gameItems.add("Cursed Dice Block");
        gameItems.add("Halfway Dice");
        players = new ArrayList<>();
    }

    /**
     * Constructor for the Items class. This creates a new instance of a Items with no parameters.
     * This is mainly used to fix bugs and keep smoothness
     */
    public Items() {
        gameItems = new ArrayList<>();
        gameItems.add("Mushroom");
        gameItems.add("Double Dice");
        gameItems.add("Triple Dice");
        gameItems.add("Warp Block");
        gameItems.add("Cursed Dice Block");
        gameItems.add("Halfway Dice");
    }


    /**
     * The getGameItems method will give you the list of items that are in the game
     *
     * @return an ArrayList<String> representing the items in the game
     */
    public ArrayList<String> getGameItems() {
        return gameItems;
    }

    /**
     * The getAmountOfItems method will give you how many items that are in the game
     *
     * @return an integer representing how many items are in the game
     */
    public int getAmountOfItems() {
        return gameItems.size();
    }

    /**
     * The itemEffects method will determine the effect of a given item will do to the player that is using it and to
     * the other players in the game
     *
     * @param item    a String representing the item that was used
     * @param player  a Player representing the player that used the item
     * @param players an arraylist of player objects representing the players playing the game
     * @return a String representing the effect of the item that was used
     */
    public String itemEffects(String item, Player player, ArrayList<Player> players) {
        if (item.equals("Mushroom")) {
            player.changeSpacesMoved(5);
            return player.getName() + " has used a mushroom. They have added +5 to their next turn.\n";
        }
        if (item.equals("Double Dice")) {
            player.changeSpacesMoved((int) (Math.random() * 10) + 1);
            return player.getName() + " has used a Double Dice. They will roll two dice on their next turn.\n";
        }
        if (item.equals("Triple Dice")) {
            player.changeSpacesMoved(((int) (Math.random() * 10) + 1) + ((int) (Math.random() * 10) + 1));
            return player.getName() + " has used a Triple Dice. They will roll three dice on their next turn.\n";
        }
        if (item.equals("Cursed Dice Block")) {
            Player selectedPlayer = players.get((int) (Math.random() * 3));
            while (selectedPlayer.equals(player)) {
                selectedPlayer = players.get((int) (Math.random() * 3));
            }
            selectedPlayer.cursedBlockActive();
            return player.getName() + " has used a Cursed Dice. " + selectedPlayer.getName() + " was randomly chosen and " +
                    "will only roll a 1-3 their next turn.\n";
        }
        if (item.equals("Warp Block")) {
            Player selectedPlayer = players.get((int) (Math.random() * 3));
            while (selectedPlayer.equals(player)) {
                selectedPlayer = players.get((int) (Math.random() * 3));
            }
            int selectedPlayerSpace = selectedPlayer.currentPlayerSpace();
            selectedPlayer.setPlayerSpace(player.currentPlayerSpace());
            player.setPlayerSpace(selectedPlayerSpace);
            return player.getName() + " has used a Warp Block. They have randomly swapped places with "
                    + selectedPlayer.getName() + "\n";
        }
        if (item.equals("Halfway Dice")) {
            player.halfwayDiceActive();
            return player.getName() + " has used a Halfway Dice. Their next roll was randomly chosen and " +
                    "will only roll a 1-3 their next turn.\n";
        } else {
            return "";
        }
    }
}
