//aneni
import java.util.*;

abstract class Question {
    protected String questionText;
    protected String[] options;
    protected String correctAnswer;

    public Question(String questionText, String[] options, String correctAnswer) {
        this.questionText = questionText;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    public abstract boolean checkAnswer(String userAnswer);

    public void display() {
        System.out.println(questionText);
    }
}

class MultipleChoiceQuestion extends Question {

    public MultipleChoiceQuestion(String questionText, String[] options, String correctAnswer) {
        super(questionText, options, correctAnswer);
    }

    @Override
    public boolean checkAnswer(String userAnswer) {
        return correctAnswer.equals(userAnswer);
    }

    @Override
    public void display() {
        super.display();
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ". " + options[i]);
        }
    }
}

class TrueFalseQuestion extends Question {
    public TrueFalseQuestion(String questionText, String correctAnswer) {
        super(questionText, new String[]{"1. True", "2. False"}, correctAnswer);
    }

    @Override
    public boolean checkAnswer(String userAnswer) {
        return correctAnswer.equals(userAnswer);
    }

    @Override
    public void display() {
        super.display();
        System.out.println("1. True");
        System.out.println("2. False");
    }
}
//aneni
