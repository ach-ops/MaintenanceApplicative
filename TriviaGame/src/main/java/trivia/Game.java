package trivia;

import java.util.*;

// REFACTORED CODE
public class Game implements IGame {
   private List<String> players = new ArrayList<>();
   private int[] places = new int[6];
   private int[] purses = new int[6];
   private boolean[] inPenaltyBox = new boolean[6];

   private Deque<String> popQuestions = new LinkedList<>();
   private Deque<String> scienceQuestions = new LinkedList<>();
   private Deque<String> sportsQuestions = new LinkedList<>();
   private Deque<String> rockQuestions = new LinkedList<>();

   private int currentPlayer = 0;
   private boolean isGettingOutOfPenaltyBox;

   public Game() {
      initializeQuestions();
   }

   private void initializeQuestions() {
      for (int i = 0; i < 50; i++) {
         popQuestions.addLast("Pop Question " + i);
         scienceQuestions.addLast("Science Question " + i);
         sportsQuestions.addLast("Sports Question " + i);
         rockQuestions.addLast(createRockQuestion(i));
      }
   }

   public String createRockQuestion(int index) {
      return "Rock Question " + index;
   }

   public boolean isPlayable() {
      return players.size() >= 2;
   }

   public boolean add(String playerName) {
      players.add(playerName);
      places[players.size() - 1] = 1;
      purses[players.size() - 1] = 0;
      inPenaltyBox[players.size() - 1] = false;

      System.out.println(playerName + " was added");
      System.out.println("They are player number " + players.size());
      return true;
   }

   public int howManyPlayers() {
      return players.size();
   }

   public void roll(int roll) {
      System.out.println(players.get(currentPlayer) + " is the current player");
      System.out.println("They have rolled a " + roll);

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
         System.out.println(players.get(currentPlayer) + " is getting out of the penalty box");
         movePlayer(roll);
         askQuestion();
      } else {
         System.out.println(players.get(currentPlayer) + " is not getting out of the penalty box");
         isGettingOutOfPenaltyBox = false;
      }
   }

   private void movePlayer(int roll) {
      places[currentPlayer] = (places[currentPlayer] + roll) % 12;
      System.out.println(players.get(currentPlayer) + "'s new location is " + places[currentPlayer]);
      System.out.println("The category is " + currentCategory());
   }

   private void askQuestion() {
      switch (currentCategory()) {
         case "Pop":
            System.out.println(popQuestions.removeFirst());
            break;
         case "Science":
            System.out.println(scienceQuestions.removeFirst());
            break;
         case "Sports":
            System.out.println(sportsQuestions.removeFirst());
            break;
         case "Rock":
            System.out.println(rockQuestions.removeFirst());
            break;
      }
   }

   private String currentCategory() {
      if (places[currentPlayer] - 1 == 0) return "Pop";
      if (places[currentPlayer] - 1 == 4) return "Pop";
      if (places[currentPlayer] - 1 == 8) return "Pop";
      if (places[currentPlayer] - 1 == 1) return "Science";
      if (places[currentPlayer] - 1 == 5) return "Science";
      if (places[currentPlayer] - 1 == 9) return "Science";
      if (places[currentPlayer] - 1 == 2) return "Sports";
      if (places[currentPlayer] - 1 == 6) return "Sports";
      if (places[currentPlayer] - 1 == 10) return "Sports";
      return "Rock";
   }

   public boolean handleCorrectAnswer() {
      if (inPenaltyBox[currentPlayer] && !isGettingOutOfPenaltyBox) {
         advancePlayer();
         return true;
      }

      System.out.println("Answer was correct!!!!");
      purses[currentPlayer]++;
      System.out.println(players.get(currentPlayer) + " now has " + purses[currentPlayer] + " Gold Coins.");

      boolean winner = !didPlayerWin();
      advancePlayer();
      return winner;
   }

   public boolean wrongAnswer() {
      System.out.println("Question was incorrectly answered");
      System.out.println(players.get(currentPlayer) + " was sent to the penalty box");
      inPenaltyBox[currentPlayer] = true;

      advancePlayer();
      return true;
   }

   private void advancePlayer() {
      currentPlayer = (currentPlayer + 1) % players.size();
   }

   private boolean didPlayerWin() {
      return purses[currentPlayer] == 6;
   }
}
