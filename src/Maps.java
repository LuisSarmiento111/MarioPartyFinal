import java.util.ArrayList;
import java.util.Arrays;

/**
 * The Maps class represents a Map. A map has its max amount of spaces, a starting space, their blue spaces, their red
 * spaces, their star space, their bowser spaces, their lucky spaces, and their item spaces
 */
public class Maps {

    private String map;
    private int maxSpaces;
    private int startingSpace;
    private ArrayList<Integer> blueSpaces;
    private ArrayList<Integer> redSpaces;
    private int starSpace;
    private ArrayList<Integer> bowserSpaces;
    private ArrayList<Integer> luckySpaces;
    private ArrayList<Integer> itemSpaces;


    /**
     * Constructor for the Maps class. This creates a new instance of a Map given the below parameters
     *
     * @param map a String representing the map that the game is being played on
     */
    public Maps(String map) {
        this.map = map.toLowerCase();
    }

    /**
     * The setUpMap method will set up the map with all its unique spaces and attributes and star location
     */
    public void setUpMap() {
        startingSpace = 0;
        if (map.equals("lava castle")) {
            maxSpaces = 92;
            blueSpaces = new ArrayList<Integer>(Arrays.asList(2, 3, 4, 7, 9, 10, 13, 14, 16, 17, 19, 21, 23, 25, 26,
                    28, 30, 32, 34, 36, 37, 40, 41, 43, 47, 48, 49, 51, 52, 53, 54, 56, 58, 59, 61, 63, 64, 67, 69, 71,
                    72, 74, 76, 77, 78, 79, 83, 84, 86, 89, 91, 92));
            redSpaces = new ArrayList<Integer>(Arrays.asList(1, 8, 18, 29, 35, 45, 62, 80));
            bowserSpaces = new ArrayList<Integer>(Arrays.asList(12, 31, 55, 68, 88));
            luckySpaces = new ArrayList<Integer>(Arrays.asList(5, 6, 11, 20, 24, 27, 33, 38, 39, 44, 46, 50, 57, 60, 66, 70, 73,
                    75, 81, 82, 85, 90));
            starSpace = (int) (Math.random() * (maxSpaces - 30)) + 15;
            itemSpaces = new ArrayList<Integer>(Arrays.asList(15, 22, 42, 65, 87));
            if (blueSpaces.contains(starSpace)) {
                blueSpaces.remove(blueSpaces.indexOf(starSpace));
            }
            if (redSpaces.contains(starSpace)) {
                redSpaces.remove(redSpaces.indexOf(starSpace));
            }
            if (bowserSpaces.contains(starSpace)) {
                bowserSpaces.remove(bowserSpaces.indexOf(starSpace));
            }
            if (luckySpaces.contains(starSpace)) {
                luckySpaces.remove(luckySpaces.indexOf(starSpace));
            }
            if (itemSpaces.contains(starSpace)) {
                itemSpaces.remove(itemSpaces.indexOf(starSpace));
            }
        }
    }

    /**
     * The getBlueSpaces method will give you all the blue spaces
     *
     * @return an ArrayList<Integer> representing all the blue spaces on the map
     */
    public ArrayList<Integer> getBlueSpaces() {
        return blueSpaces;
    }

    /**
     * The newStarSpace essentially creates a new star space on the map once someone buys a star
     *
     * @return a String representing where the star has moved on the map
     */
    public String newStarSpace() {
        String starMovedTo = "The star moved to space ";
        if (map.equals("lava castle")) {
            maxSpaces = 92;
            blueSpaces = new ArrayList<Integer>(Arrays.asList(2, 3, 4, 7, 9, 10, 13, 14, 16, 17, 19, 21, 23, 25, 26,
                    28, 30, 32, 34, 36, 37, 40, 41, 43, 47, 48, 49, 51, 52, 53, 54, 56, 58, 59, 61, 63, 64, 67, 69,
                    71, 72, 74, 76, 77, 78, 79, 83, 84, 86, 89, 91, 92));
            redSpaces = new ArrayList<Integer>(Arrays.asList(1, 8, 18, 29, 35, 45, 62, 80));
            bowserSpaces = new ArrayList<Integer>(Arrays.asList(12, 31, 55, 68, 88));
            luckySpaces = new ArrayList<Integer>(Arrays.asList(5, 6, 11, 20, 24, 27, 33, 38, 39, 44, 46, 50, 57, 60, 66, 70, 73,
                    75, 81, 82, 85, 90));
            starSpace = (int) (Math.random() * (maxSpaces - 30)) + 15;
            itemSpaces = new ArrayList<Integer>(Arrays.asList(15,22, 42, 65, 87));
            if (blueSpaces.contains(starSpace)) {
                blueSpaces.remove(blueSpaces.indexOf(starSpace));
            }
            if (redSpaces.contains(starSpace)) {
                redSpaces.remove(redSpaces.indexOf(starSpace));
            }
            if (bowserSpaces.contains(starSpace)) {
                bowserSpaces.remove(bowserSpaces.indexOf(starSpace));
            }
            if (luckySpaces.contains(starSpace)) {
                luckySpaces.remove(luckySpaces.indexOf(starSpace));
            }
            if (itemSpaces.contains(starSpace)) {
                itemSpaces.remove(itemSpaces.indexOf(starSpace));
            }
            starMovedTo += starSpace;
        }
        return starMovedTo;
    }

    /**
     * The getBlueSpaces method will give you all the red spaces
     *
     * @return an ArrayList<Integer> representing all the red spaces on the map
     */
    public ArrayList<Integer> getRedSpaces() {
        return redSpaces;
    }

    /**
     * The getStarSpace method will give you where the star space is on the map
     *
     * @return an integer representing where the star is on the map
     */
    public int getStarSpace() {
        return starSpace;
    }

    /**
     * The getMaxSpaces method will give you the amount of spaces there are on the map
     *
     * @return an integer representing the max amount of spaces on the current map
     */
    public int getMaxSpaces() {
        return maxSpaces;
    }

    /**
     * The getBowserSpaces method will give you all the bowser spaces
     *
     * @return an ArrayList<Integer> representing all the bowser spaces on the map
     */
    public ArrayList<Integer> getBowserSpaces() {
        return bowserSpaces;
    }

    /**
     * The getLuckySpaces method will give you all the lucky spaces
     *
     * @return an ArrayList<Integer> representing all the lucky spaces on the map
     */
    public ArrayList<Integer> getLuckySpaces() {
        return luckySpaces;
    }

    /**
     * The getItemSpaces method will give you all the item spaces
     *
     * @return an ArrayList<Integer> representing all the item spaces on the map
     */
    public ArrayList<Integer> getItemSpaces() {
        return itemSpaces;
    }

    /**
     * The mapDetails method will tell you what map you are on and state where all the unique spaces are on the current map
     *
     * @return a String representing the current map and it's spaces
     */
    public String mapDetails() {
        return "Welcome to " + map + ".\n\u001B[34mThere are blue spaces at: "
                + getBlueSpaces() + "\033[0m\n\u001B[31mThere are red spaces at: "
                + getRedSpaces() + "\033[0m\n\u001B[32mThere are lucky spaces at: " + getLuckySpaces() +
                "\033[0m\n\u001B[35mThere are bowser spaces at: " + getBowserSpaces() +
                "\033[0m\n\u001B[36mThere are item spaces at: " + getItemSpaces() +
                "\033[0m\n\u001B[33mThe star space is currently located at: " + getStarSpace() + "\033[0m";
    }
}
