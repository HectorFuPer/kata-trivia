import io
import random
import unittest
from contextlib import redirect_stdout

from trivia import Game


class GoldenMasterTest(unittest.TestCase):
    EXPECTED_SEED_1 = """Chet was added
They are player number 1
Pat was added
They are player number 2
Sue was added
They are player number 3
Chet is the current player
They have rolled a 2
Chet's new location is 3
The category is Sports
Sports Question 0
Answer was correct!!!!
Chet now has 1 Gold Coins.
Pat is the current player
They have rolled a 3
Pat's new location is 4
The category is Rock
Rock Question 0
Answer was correct!!!!
Pat now has 1 Gold Coins.
Sue is the current player
They have rolled a 4
Sue's new location is 5
The category is Pop
Pop Question 0
Question was incorrectly answered
Sue was sent to the penalty box
Chet is the current player
They have rolled a 4
Chet's new location is 7
The category is Sports
Sports Question 1
Answer was correct!!!!
Chet now has 2 Gold Coins.
Pat is the current player
They have rolled a 2
Pat's new location is 6
The category is Science
Science Question 0
Answer was correct!!!!
Pat now has 2 Gold Coins.
Sue is the current player
They have rolled a 4
Sue is not getting out of the penalty box
Chet is the current player
They have rolled a 4
Chet's new location is 11
The category is Sports
Sports Question 2
Answer was correct!!!!
Chet now has 3 Gold Coins.
Pat is the current player
They have rolled a 5
Pat's new location is 11
The category is Sports
Sports Question 3
Answer was correct!!!!
Pat now has 3 Gold Coins.
Sue is the current player
They have rolled a 4
Sue is not getting out of the penalty box
Chet is the current player
They have rolled a 2
Chet's new location is 1
The category is Pop
Pop Question 1
Answer was correct!!!!
Chet now has 4 Gold Coins.
Pat is the current player
They have rolled a 3
Pat's new location is 2
The category is Science
Science Question 1
Answer was correct!!!!
Pat now has 4 Gold Coins.
Sue is the current player
They have rolled a 1
Sue is getting out of the penalty box
Sue's new location is 6
The category is Science
Science Question 2
Answer was correct!!!!
Sue now has 1 Gold Coins.
Chet is the current player
They have rolled a 5
Chet's new location is 6
The category is Science
Science Question 3
Answer was correct!!!!
Chet now has 5 Gold Coins.
Pat is the current player
They have rolled a 4
Pat's new location is 6
The category is Science
Science Question 4
Answer was correct!!!!
Pat now has 5 Gold Coins.
Sue is the current player
They have rolled a 4
Sue's new location is 10
The category is Science
Science Question 5
Answer was correct!!!!
Sue now has 2 Gold Coins.
Chet is the current player
They have rolled a 5
Chet's new location is 11
The category is Sports
Sports Question 4
Answer was correct!!!!
Chet now has 6 Gold Coins.
"""

    def test_seed_1_characterization(self):
        self.assertEqual(self.EXPECTED_SEED_1, self._extract_output(1))

    def test_random_games_finish(self):
        for seed in range(1, 100):
            with self.subTest(seed=seed):
                output = self._extract_output(seed)
                self.assertIn("Gold Coins.", output)

    def _extract_output(self, seed):
        rand = random.Random(seed)
        game = Game()
        output = io.StringIO()

        with redirect_stdout(output):
            game.add("Chet")
            game.add("Pat")
            game.add("Sue")

            not_a_winner = True
            while not_a_winner:
                game.roll(rand.randrange(5) + 1)

                if rand.randrange(9) == 7:
                    not_a_winner = game.wrong_answer()
                else:
                    not_a_winner = game.handle_correct_answer()

        return output.getvalue()


if __name__ == "__main__":
    unittest.main()
