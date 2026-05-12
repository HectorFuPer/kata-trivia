package trivia;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

class QuestionDeck {
   private final Map<String, LinkedList<String>> questionsByCategory = new HashMap<>();

   QuestionDeck(int questionsPerCategory) {
      addQuestions("Pop", questionsPerCategory);
      addQuestions("Science", questionsPerCategory);
      addQuestions("Sports", questionsPerCategory);
      addQuestions("Rock", questionsPerCategory);
   }

   String nextQuestion(String category) {
      return questionsByCategory.get(category).removeFirst();
   }

   private void addQuestions(String category, int count) {
      LinkedList<String> questions = new LinkedList<>();
      for (int index = 0; index < count; index++) {
         questions.addLast(category + " Question " + index);
      }
      questionsByCategory.put(category, questions);
   }
}
