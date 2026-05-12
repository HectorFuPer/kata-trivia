from .player import Player
from .question_deck import QuestionDeck


class Game:
    MAX_PLAYERS = 6
    BOARD_SIZE = 12
    WINNING_COINS = 6
    QUESTIONS_PER_CATEGORY = 50

    def __init__(self):
        self.players = []
        self.questions = QuestionDeck(self.QUESTIONS_PER_CATEGORY)
        self.current_player_index = 0
        self.is_getting_out_of_penalty_box = False

    def is_playable(self):
        return self.how_many_players() >= 2

    def add(self, player_name):
        self._validate_new_player(player_name)
        self.players.append(Player(player_name))

        print(f"{player_name} was added")
        print(f"They are player number {len(self.players)}")
        return True

    def how_many_players(self):
        return len(self.players)

    def roll(self, roll):
        print(f"{self._current_player_name()} is the current player")
        print(f"They have rolled a {roll}")

        if self._current_player().in_penalty_box:
            self._handle_penalty_box_roll(roll)
        else:
            self._take_turn(roll)

    def handle_correct_answer(self):
        if self._current_player().in_penalty_box and not self.is_getting_out_of_penalty_box:
            self._advance_to_next_player()
            return True

        self._award_correct_answer()
        winner = self._did_player_win()
        self._advance_to_next_player()
        return winner

    def wrong_answer(self):
        print("Question was incorrectly answered")
        print(f"{self._current_player_name()} was sent to the penalty box")
        self._current_player().send_to_penalty_box()

        self._advance_to_next_player()
        return True

    def _validate_new_player(self, player_name):
        if len(self.players) == self.MAX_PLAYERS:
            raise ValueError(f"The game cannot have more than {self.MAX_PLAYERS} players")
        if any(player.has_name(player_name) for player in self.players):
            raise ValueError("Player names must be unique")

    def _handle_penalty_box_roll(self, roll):
        if roll % 2 != 0:
            self.is_getting_out_of_penalty_box = True
            self._current_player().release_from_penalty_box()
            print(f"{self._current_player_name()} is getting out of the penalty box")
            self._take_turn(roll)
        else:
            print(f"{self._current_player_name()} is not getting out of the penalty box")
            self.is_getting_out_of_penalty_box = False

    def _take_turn(self, roll):
        self._move_current_player(roll)
        print(f"{self._current_player_name()}'s new location is {self._current_player().position}")
        print(f"The category is {self._current_category()}")
        self._ask_question()

    def _move_current_player(self, roll):
        self._current_player().move(roll, self.BOARD_SIZE)

    def _current_player_name(self):
        return self._current_player().name

    def _current_player(self):
        return self.players[self.current_player_index]

    def _ask_question(self):
        print(self.questions.next_question(self._current_category()))

    def _current_category(self):
        return self.questions.category_for(self._current_player().position)

    def _award_correct_answer(self):
        print("Answer was correct!!!!")
        self._current_player().add_coin()
        print(f"{self._current_player_name()} now has {self._current_player().coins} Gold Coins.")

    def _advance_to_next_player(self):
        self.current_player_index += 1
        if self.current_player_index == len(self.players):
            self.current_player_index = 0

    def _did_player_win(self):
        return not self._current_player().has_won(self.WINNING_COINS)
