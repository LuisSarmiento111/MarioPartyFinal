import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class MarioPartyGameRunner {
    public static void main(String[] args) throws InterruptedException {
        final String resetColor = "\u001B[0m";
        Scanner userInput = new Scanner(System.in);
        MarioPartyGame setup = new MarioPartyGame();
        System.out.println("Welcome to Mario Party!");
        System.out.println("The goal is to get the most stars by the end of the game in order to win." +
                "\033[1m\033[4m\nHere are some main components about this game:\033[0m");
        System.out.println("\u001B[34mLanding on a blue space makes you gain 3 coins." + resetColor);
        System.out.println("\u001B[31mLanding on a red space makes you lose 3 coins." + resetColor);
        System.out.println("\u001B[32mLanding on a lucky space gives you a random reward." + resetColor);
        System.out.println("\u001B[35mLanding on a bowser space gives you a random penalty" + resetColor);
        System.out.println("\u001B[36mLanding on a item space gives you a random item" + resetColor);
        System.out.println("\u001B[33mAnd finally if you land on a star space. You can buy a star for 20 coins." + resetColor);
        System.out.println("There are items you can get along the way to boost your chances of winning.");
        System.out.println("Some maps have unique hazards that can change where you are on the map.");
        System.out.println("The maps you can play are Lava Castle.(There is only one)"); // work on maps I guess
        System.out.println("Which map would you like to play?");
        String userMap = userInput.nextLine();
        while (setup.isMap(userMap) == false) {
            System.out.println("That is not an option");
            System.out.println("The maps you can play are Lava Castle..."); // work on maps I guess
            System.out.println("Which map would you like to play?");
            userMap = userInput.nextLine();
        }
        Maps currentMap = new Maps(userMap);
        System.out.println("How many people are playing? (Max of 4 players)");
        int playerAmount = Integer.parseInt(userInput.nextLine());
        while (playerAmount > 4 || playerAmount < 0) {
            System.out.println("That is not a valid amount of players");
            System.out.println("How many people are playing? (Max of 4 players)");
            playerAmount = Integer.parseInt(userInput.nextLine());
        }
        ArrayList<Player> players = new ArrayList<Player>();
        ArrayList<String> playernames = new ArrayList<String>();
        for (int i = 0; i <= playerAmount - 1; i++) {
            players.add(new Player(currentMap, false));
            System.out.println("What is player " + (i + 1) + "'s name?");
            players.get(i).setName(userInput.nextLine());
            playernames.add(players.get(i).getName());
        }
        int botsAdded = 0;
        for (int i = 0; i < 4 - playerAmount; i++) {
            botsAdded++;
            players.add(new Player(currentMap, true));
            players.get(i + playerAmount).setName("Bot" + (i + 1));
            playernames.add(players.get(i + playerAmount).getName());
        }
        playerAmount = 4;
        System.out.println(botsAdded + " bots have been added to the player list.\nThese are the people on the player list: " + playernames);
        System.out.println("How many turns do you guys want to play up to? (Min is 10, Max is 40, must be divisible by 5)"); // check to ensure max/min
        int maxTurns = Integer.parseInt(userInput.nextLine());
        while (setup.validAmountOfTurns(maxTurns) == false) {
            System.out.println("That is not a valid amount of turns");
            System.out.println("How many turns do you guys want to play up to? (Min is 10, Max is 40, must be divisible by 5)");
            maxTurns = Integer.parseInt(userInput.nextLine());
        }
        System.out.println("Alright lets get this game started!");
        MarioPartyGame MarioPartyGame = new MarioPartyGame(playerAmount, currentMap, maxTurns);
        Items itemLogic = new Items(players);
        currentMap.setUpMap();
        System.out.println(currentMap.mapDetails());
        TimeUnit.SECONDS.sleep(2);
        System.out.println(MarioPartyGame.determineFirst(players));
        TimeUnit.SECONDS.sleep(2);
        System.out.println("Alright now that the turn order has been decided, here are 10 coins to get you all started!\n");
        TimeUnit.SECONDS.sleep(3);
        int currentTurn = 1;
        int userAnswer = 0;
        while (MarioPartyGame.gameOver(currentTurn)) {
            System.out.println("\033[1mTurn " + currentTurn + "\033[0m\n");
            for (int j = 0; j < playerAmount; j++) {
                Player currentPlayerTurn = MarioPartyGame.getOrderOfPlayers().get(j);
                while (currentPlayerTurn.isTurnOver() == false) {
                    if (currentPlayerTurn.isBot == false) {
                        currentPlayerTurn.setPlayers(players);
                        System.out.println("\033[1mIt's " + currentPlayerTurn.getName() + "'s turn.\033[0m");
                        System.out.println("You are on space: " + currentPlayerTurn.getSpace());
                        System.out.println("What would you like to do " + currentPlayerTurn.getName() + "? (Answer with the corresponding number)");
                        System.out.println("1. DiceRoll\n2. Use an item");
                        userAnswer = Integer.parseInt(userInput.nextLine());
                        if (userAnswer <= 0 || userAnswer > 2) {
                            System.out.println("That is not a valid answer");
                            System.out.println("What would you like to do " + currentPlayerTurn.getName() + "? (Answer with the corresponding number)");
                            System.out.println("1. DiceRoll\n2. Use an item");
                            userAnswer = Integer.parseInt(userInput.nextLine());
                        }
                        if (userAnswer == 2) {
                            if (currentPlayerTurn.getPlayerInventory().size() != 0) {
                                System.out.println("Which item do you want to use?(Case Sensitive)(Write \"Cancel\" to cancel.)" +
                                        "\nThis is your current inventory: " + currentPlayerTurn.getPlayerInventory());
                                String item = userInput.nextLine();
                                if (item.equals("Cancel") == false) {
                                    while (currentPlayerTurn.haveItem(item) == false) {
                                        System.out.println("You do not have that item in your inventory");
                                        System.out.println("Which item do you want to use?Case Sensitive)(Write \"Cancel\" to cancel.)" +
                                                "\nThis is your current inventory: " + currentPlayerTurn.getPlayerInventory());
                                        item = userInput.nextLine();
                                    }
                                    System.out.println(currentPlayerTurn.useItem(item, currentPlayerTurn, players));
                                }
                            } else {
                                System.out.print("You do not have any items in your inventory");
                            }
                        }
                        System.out.println(currentPlayerTurn.actionSelected(userAnswer, currentPlayerTurn));
                        if (currentPlayerTurn.passedStar() == true && currentPlayerTurn.amountOfCoins() >= 20) {
                            System.out.println(currentPlayerTurn.getName() + " passed by a star space.\nWould you like to buy a star? (yes or no)");
                            String answer = userInput.nextLine().toLowerCase();
                            while (answer.equals("yes") == false && answer.equals("no") == false) {
                                System.out.println("That is not a valid answer.\nWould you like to buy a star? (yes or no)");
                                answer = userInput.nextLine().toLowerCase();
                            }
                            System.out.println(currentPlayerTurn.buyStar(answer));
                        } else if (currentPlayerTurn.passedStar() == true) {
                            System.out.println(currentPlayerTurn.getName() + " passed by a star space.\nUnfortunately you do not have enough coins to " +
                                    "buy a star.");
                        }
                        System.out.println();
                    } else {
                        System.out.println(currentPlayerTurn.botAction(currentPlayerTurn, players));
                    }
                }
                currentPlayerTurn.endOfTurn();
                TimeUnit.SECONDS.sleep(2);
            }
            currentTurn++;
        }
        System.out.println("That is the end of all the turns.\nLets see how many stars everyone got!");
        System.out.println(MarioPartyGame.winner());
    }
}
