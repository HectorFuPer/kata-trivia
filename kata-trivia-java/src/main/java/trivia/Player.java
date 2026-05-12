package trivia;

class Player {
   final String name;
   int position = 1;
   int coins = 0;
   boolean inPenaltyBox = false;

   Player(String name) {
      this.name = name;
   }

   boolean hasName(String otherName) {
      return name.equals(otherName);
   }

   void move(int roll, int boardSize) {
      position = position + roll;
      if (position > boardSize) position = position - boardSize;
   }

   void addCoin() {
      coins++;
   }

   void sendToPenaltyBox() {
      inPenaltyBox = true;
   }

   void releaseFromPenaltyBox() {
      inPenaltyBox = false;
   }

   boolean hasWon(int winningCoins) {
      return coins == winningCoins;
   }
}
