from collections import deque


class QuestionDeck:
    CATEGORIES = ("Pop", "Science", "Sports", "Rock")

    def __init__(self, questions_per_category):
        self.questions_by_category = {
            category: deque(
                f"{category} Question {index}"
                for index in range(questions_per_category)
            )
            for category in self.CATEGORIES
        }

    def next_question(self, category):
        return self.questions_by_category[category].popleft()

    def category_for(self, position):
        return self.CATEGORIES[(position - 1) % len(self.CATEGORIES)]
