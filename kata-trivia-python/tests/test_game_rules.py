import unittest
from contextlib import redirect_stdout
from io import StringIO

from trivia import Game


class GameRulesTest(unittest.TestCase):
    def test_cannot_add_more_than_six_players(self):
        game = Game()

        with self._captured_output():
            for player_name in ["A", "B", "C", "D", "E", "F"]:
                game.add(player_name)

        with self.assertRaises(ValueError):
            game.add("G")

    def test_cannot_add_two_players_with_the_same_name(self):
        game = Game()
        with self._captured_output():
            game.add("Chet")

        with self.assertRaises(ValueError):
            game.add("Chet")

    def test_player_leaves_penalty_box_after_odd_roll(self):
        game = Game()
        with self._captured_output():
            game.add("Chet")
            game.add("Pat")
            game.wrong_answer()
            game.handle_correct_answer()
            game.roll(1)

        self.assertFalse(game.players[0].in_penalty_box)

    def _captured_output(self):
        return redirect_stdout(StringIO())


if __name__ == "__main__":
    unittest.main()
