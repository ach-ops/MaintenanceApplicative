package trivia;

import java.util.*;

// REFACTOR ME
public class Game implements IGame {
   private final List<String> players = new ArrayList<>();
   private final int[] places = new int[6];
   private final int[] purses = new int[6];
   private final boolean[] inPenaltyBox = new boolean[6];

   private final Map<String, LinkedList<String>> questions = new HashMap<>();
   private static final String[] CATEGORIES = {"Pop", "Science", "Sports", "Rock", "Geography"};

   private int currentPlayer = 0;
   private boolean isGettingOutOfPenaltyBox;

   public Game() {
      for (String category : CATEGORIES) {
         questions.put(category, new LinkedList<>());
      }
      for (int i = 0; i < 50; i++) {
         questions.get("Pop").add("Pop Question " + i);
         questions.get("Science").add("Science Question " + i);
         questions.get("Sports").add("Sports Question " + i);
         questions.get("Rock").add("Rock Question " + i);
         questions.get("Geography").add("Geography Question " + i);
      }
   }

   public boolean add(String playerName) {
      if (players.size() >= 6) {
         System.out.println("Maximum number of players (6) reached.");
         return false;
      }
      players.add(playerName);
      int playerIndex = players.size() - 1;
      places[playerIndex] = 1;
      purses[playerIndex] = 0;
      inPenaltyBox[playerIndex] = false;

      log(playerName + " was added");
      log("They are player number " + players.size());
      return true;
   }

   public boolean canStartGame() {
      return players.size() >= 2;
   }

   public void roll(int roll) {
      if (!canStartGame()) {
         System.out.println("At least 2 players are required to start the game.");
         return;
      }

      log(players.get(currentPlayer) + " is the current player");
      log("They have rolled a " + roll);

      if (inPenaltyBox[currentPlayer]) {
         handlePenaltyBox(roll);
      } else {
         movePlayer(roll);
         askQuestion();
      }
   }

   private void handlePenaltyBox(int roll) {
      if (roll % 2 != 0) {
         isGettingOutOfPenaltyBox = true;
         log(players.get(currentPlayer) + " is getting out of the penalty box");
         movePlayer(roll);
         askQuestion();
      } else {
         log(players.get(currentPlayer) + " is not getting out of the penalty box");
         isGettingOutOfPenaltyBox = false;
      }
   }

   private void movePlayer(int roll) {
      places[currentPlayer] = (places[currentPlayer] + roll - 1) % 12 + 1;
      log(players.get(currentPlayer) + "'s new location is " + places[currentPlayer]);
      log("The category is " + currentCategory());
   }

   private void askQuestion() {
      log(questions.get(currentCategory()).removeFirst());
   }

   private String currentCategory() {
      return CATEGORIES[(places[currentPlayer] - 1) % 4];
   }

   public boolean handleCorrectAnswer() {
      if (!inPenaltyBox[currentPlayer] || isGettingOutOfPenaltyBox) {
         rewardPlayer();
         boolean winner = didPlayerWin();
         nextPlayer();
         return winner;
      } else {
         nextPlayer();
         return true;
      }
   }

   private void rewardPlayer() {
      log("Answer was corrent!!!!");
      purses[currentPlayer]++;
      log(players.get(currentPlayer) + " now has " + purses[currentPlayer] + " Gold Coins.");
   }

   private void nextPlayer() {
      currentPlayer = (currentPlayer + 1) % players.size();
   }

   public boolean wrongAnswer() {
      log("Question was incorrectly answered");
      log(players.get(currentPlayer) + " was sent to the penalty box");
      inPenaltyBox[currentPlayer] = true;
      nextPlayer();
      return true;
   }

   private boolean didPlayerWin() {
      return purses[currentPlayer] != 6;
   }

   private void log(String message) {
      System.out.println(message);
   }
}
