class Player:
    def __init__(self, name):
        self.name = name
        self.position = 1
        self.coins = 0
        self.in_penalty_box = False

    def has_name(self, other_name):
        return self.name == other_name

    def move(self, roll, board_size):
        self.position += roll
        if self.position > board_size:
            self.position -= board_size

    def add_coin(self):
        self.coins += 1

    def send_to_penalty_box(self):
        self.in_penalty_box = True

    def release_from_penalty_box(self):
        self.in_penalty_box = False

    def has_won(self, winning_coins):
        return self.coins == winning_coins
