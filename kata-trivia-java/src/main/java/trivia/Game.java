package trivia;

import java.util.ArrayList;
import java.util.List;

// REFACTOR ME
public class Game implements IGame {
   private static final int MAX_PLAYERS = 6;
   private static final int BOARD_SIZE = 12;
   private static final int WINNING_COINS = 6;
   private static final int QUESTIONS_PER_CATEGORY = 50;

   private final List<Player> players = new ArrayList<>();
   private final QuestionDeck questions = new QuestionDeck(QUESTIONS_PER_CATEGORY);

   private int currentPlayer = 0;
   private boolean isGettingOutOfPenaltyBox;

   public boolean isPlayable() {
      return (howManyPlayers() >= 2);
   }

   public boolean add(String playerName) {
      validateNewPlayer(playerName);
      players.add(new Player(playerName));

      System.out.println(playerName + " was added");
      System.out.println("They are player number " + players.size());
      return true;
   }

   private void validateNewPlayer(String playerName) {
      if (players.size() == MAX_PLAYERS) {
         throw new IllegalStateException("The game cannot have more than " + MAX_PLAYERS + " players");
      }
      if (players.stream().anyMatch(player -> player.hasName(playerName))) {
         throw new IllegalArgumentException("Player names must be unique");
      }
   }

   public int howManyPlayers() {
      return players.size();
   }

   public void roll(int roll) {
      System.out.println(currentPlayerName() + " is the current player");
      System.out.println("They have rolled a " + roll);

      if (currentPlayer().inPenaltyBox) {
         handlePenaltyBoxRoll(roll);
      } else {
         takeTurn(roll);
      }
   }

   private void handlePenaltyBoxRoll(int roll) {
      if (roll % 2 != 0) {
         isGettingOutOfPenaltyBox = true;
         currentPlayer().releaseFromPenaltyBox();
         System.out.println(currentPlayerName() + " is getting out of the penalty box");
         takeTurn(roll);
      } else {
         System.out.println(currentPlayerName() + " is not getting out of the penalty box");
         isGettingOutOfPenaltyBox = false;
      }
   }

   private void takeTurn(int roll) {
      moveCurrentPlayer(roll);
      System.out.println(currentPlayerName() + "'s new location is " + currentPlayer().position);
      System.out.println("The category is " + currentCategory());
      askQuestion();
   }

   private void moveCurrentPlayer(int roll) {
      currentPlayer().move(roll, BOARD_SIZE);
   }

   private String currentPlayerName() {
      return currentPlayer().name;
   }

   private Player currentPlayer() {
      return players.get(currentPlayer);
   }

   private void askQuestion() {
      System.out.println(questions.nextQuestion(currentCategory()));
   }


   private String currentCategory() {
      return questions.categoryFor(currentPlayer().position);
   }

   public boolean handleCorrectAnswer() {
      if (currentPlayer().inPenaltyBox && !isGettingOutOfPenaltyBox) {
         advanceToNextPlayer();
         return true;
      }

      awardCorrectAnswer();
      boolean winner = didPlayerWin();
      advanceToNextPlayer();
      return winner;
   }

   private void awardCorrectAnswer() {
      System.out.println("Answer was correct!!!!");
      currentPlayer().addCoin();
      System.out.println(currentPlayerName()
                         + " now has "
                         + currentPlayer().coins
                         + " Gold Coins.");
   }

   public boolean wrongAnswer() {
      System.out.println("Question was incorrectly answered");
      System.out.println(currentPlayerName() + " was sent to the penalty box");
      currentPlayer().sendToPenaltyBox();

      advanceToNextPlayer();
      return true;
   }

   private void advanceToNextPlayer() {
      currentPlayer++;
      if (currentPlayer == players.size()) currentPlayer = 0;
   }


   private boolean didPlayerWin() {
      return !currentPlayer().hasWon(WINNING_COINS);
   }
}
