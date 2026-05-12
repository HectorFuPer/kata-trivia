package trivia;

import org.junit.Test;

public class GameRulesTest {
   @Test(expected = IllegalStateException.class)
   public void cannotAddMoreThanSixPlayers() {
      Game game = new Game();

      game.add("A");
      game.add("B");
      game.add("C");
      game.add("D");
      game.add("E");
      game.add("F");

      game.add("G");
   }

   @Test(expected = IllegalArgumentException.class)
   public void cannotAddTwoPlayersWithTheSameName() {
      Game game = new Game();

      game.add("Chet");
      game.add("Chet");
   }
}
