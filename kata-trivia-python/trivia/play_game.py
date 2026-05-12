import random

from .game import Game


def main():
    print("*** Welcome to Trivia Game ***\n")
    print("Enter number of players: 1-6")
    player_count = int(input())
    if player_count < 1 or player_count > 6:
        raise ValueError("No player 1..6")

    print(f"Reading names for {player_count} players:")
    game = Game()

    for index in range(1, player_count + 1):
        player_name = input(f"Player {index} name: ")
        game.add(player_name)

    print("\n\n--Starting game--")
    not_a_winner = True
    while not_a_winner:
        roll = read_roll()
        game.roll(roll)

        correct = read_yes_no()
        if correct:
            not_a_winner = game.handle_correct_answer()
        else:
            not_a_winner = game.wrong_answer()

    print(">> Game won!")


def read_yes_no():
    answer = input(">> Was the answer correct? [y/n] ").strip().upper()
    if answer not in {"Y", "N"}:
        print("y or n please")
        return read_yes_no()
    return answer == "Y"


def read_roll():
    roll_text = input(">> Throw a die and input roll, or [ENTER] to generate a random roll: ").strip()
    if not roll_text:
        roll = random.randint(1, 6)
        print(f">> Random roll: {roll}")
        return roll
    if not roll_text.isdigit():
        print(f"Not a number: '{roll_text}'")
        return read_roll()

    roll = int(roll_text)
    if roll < 1 or roll > 6:
        print("Invalid roll")
        return read_roll()
    return roll


if __name__ == "__main__":
    main()
