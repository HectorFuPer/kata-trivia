package trivia;

import java.util.ArrayList;
import java.util.LinkedList;

// REFACTOR ME
public class Game implements IGame {
   private static final int MAX_PLAYERS = 6;
   private static final int BOARD_SIZE = 12;
   private static final int WINNING_COINS = 6;
   private static final int QUESTIONS_PER_CATEGORY = 50;

   ArrayList<Player> players = new ArrayList<>();
   int[] places = new int[MAX_PLAYERS];
   int[] purses = new int[MAX_PLAYERS];
   boolean[] inPenaltyBox = new boolean[MAX_PLAYERS];

   LinkedList<String> popQuestions = new LinkedList<>();
   LinkedList<String> scienceQuestions = new LinkedList<>();
   LinkedList<String> sportsQuestions = new LinkedList<>();
   LinkedList<String> rockQuestions = new LinkedList<>();

   int currentPlayer = 0;
   boolean isGettingOutOfPenaltyBox;

   public Game() {
      for (int i = 0; i < QUESTIONS_PER_CATEGORY; i++) {
         popQuestions.addLast("Pop Question " + i);
         scienceQuestions.addLast(("Science Question " + i));
         sportsQuestions.addLast(("Sports Question " + i));
         rockQuestions.addLast(createRockQuestion(i));
      }
   }

   public String createRockQuestion(int index) {
      return "Rock Question " + index;
   }

   public boolean isPlayable() {
      return (howManyPlayers() >= 2);
   }

   public boolean add(String playerName) {
      places[howManyPlayers()] = 1;
      purses[howManyPlayers()] = 0;
      inPenaltyBox[howManyPlayers()] = false;
      players.add(new Player(playerName));

      System.out.println(playerName + " was added");
      System.out.println("They are player number " + players.size());
      return true;
   }

   public int howManyPlayers() {
      return players.size();
   }

   public void roll(int roll) {
      System.out.println(currentPlayerName() + " is the current player");
      System.out.println("They have rolled a " + roll);

      if (inPenaltyBox[currentPlayer]) {
         handlePenaltyBoxRoll(roll);
      } else {
         takeTurn(roll);
      }
   }

   private void handlePenaltyBoxRoll(int roll) {
      if (roll % 2 != 0) {
         isGettingOutOfPenaltyBox = true;
         System.out.println(currentPlayerName() + " is getting out of the penalty box");
         takeTurn(roll);
      } else {
         System.out.println(currentPlayerName() + " is not getting out of the penalty box");
         isGettingOutOfPenaltyBox = false;
      }
   }

   private void takeTurn(int roll) {
      moveCurrentPlayer(roll);
      System.out.println(currentPlayerName() + "'s new location is " + places[currentPlayer]);
      System.out.println("The category is " + currentCategory());
      askQuestion();
   }

   private void moveCurrentPlayer(int roll) {
      places[currentPlayer] = places[currentPlayer] + roll;
      if (places[currentPlayer] > BOARD_SIZE) places[currentPlayer] = places[currentPlayer] - BOARD_SIZE;
   }

   private String currentPlayerName() {
      return players.get(currentPlayer).name;
   }

   private void askQuestion() {
      if (currentCategory() == "Pop")
         System.out.println(popQuestions.removeFirst());
      if (currentCategory() == "Science")
         System.out.println(scienceQuestions.removeFirst());
      if (currentCategory() == "Sports")
         System.out.println(sportsQuestions.removeFirst());
      if (currentCategory() == "Rock")
         System.out.println(rockQuestions.removeFirst());
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
      if (inPenaltyBox[currentPlayer]) {
         if (isGettingOutOfPenaltyBox) {
            System.out.println("Answer was correct!!!!");
            purses[currentPlayer]++;
            System.out.println(currentPlayerName()
                               + " now has "
                               + purses[currentPlayer]
                               + " Gold Coins.");

            boolean winner = didPlayerWin();
            advanceToNextPlayer();

            return winner;
         } else {
            advanceToNextPlayer();
            return true;
         }


      } else {

         System.out.println("Answer was corrent!!!!");
         purses[currentPlayer]++;
         System.out.println(currentPlayerName()
                            + " now has "
                            + purses[currentPlayer]
                            + " Gold Coins.");

         boolean winner = didPlayerWin();
         advanceToNextPlayer();

         return winner;
      }
   }

   public boolean wrongAnswer() {
      System.out.println("Question was incorrectly answered");
      System.out.println(currentPlayerName() + " was sent to the penalty box");
      inPenaltyBox[currentPlayer] = true;

      advanceToNextPlayer();
      return true;
   }

   private void advanceToNextPlayer() {
      currentPlayer++;
      if (currentPlayer == players.size()) currentPlayer = 0;
   }


   private boolean didPlayerWin() {
      return !(purses[currentPlayer] == WINNING_COINS);
   }
}
