package trivia;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

class QuestionDeck {
   private static final List<String> CATEGORIES = List.of("Pop", "Science", "Sports", "Rock");

   private final Map<String, LinkedList<String>> questionsByCategory = new HashMap<>();

   QuestionDeck(int questionsPerCategory) {
      for (String category : CATEGORIES) {
         addQuestions(category, questionsPerCategory);
      }
   }

   String nextQuestion(String category) {
      return questionsByCategory.get(category).removeFirst();
   }

   String categoryFor(int position) {
      return CATEGORIES.get((position - 1) % CATEGORIES.size());
   }

   private void addQuestions(String category, int count) {
      LinkedList<String> questions = new LinkedList<>();
      for (int index = 0; index < count; index++) {
         questions.addLast(category + " Question " + index);
      }
      questionsByCategory.put(category, questions);
   }
}
