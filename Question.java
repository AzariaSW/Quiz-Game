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
 public void adminPanel() {
        Admin admin = (Admin) currentUser;
        admin.startGame();
        admin.play();

        int choice = -1;

        while (choice != 5) {
            System.out.println("\n--- ADMIN PANEL ---");
            System.out.println("1. Add Question");
            System.out.println("2. View Questions");
            System.out.println("3. View Leaderboard");
            System.out.println("4. Clear Leaderboard");
            System.out.println("5. Logout");
            System.out.print("Choose: ");
            choice = Integer.parseInt(input.nextLine());

            switch (choice) {
                case 1 -> addQuestion();
                case 2 -> viewQuestions();
                case 3 -> showLeaderboard();
                case 4 -> clearLeaderboard();
                case 5 -> admin.endGame();
                default -> System.out.println("Invalid option!");
            }
        }
    }

    private void addQuestion() {
        System.out.println("\nChoose type:");
        System.out.println("1. Multiple Choice");
        System.out.println("2. True/False");
        int type = Integer.parseInt(input.nextLine());

        System.out.print("Question text: ");
        String text = input.nextLine();

        if (type == 1) {
            System.out.print("How many options? ");
            int n = Integer.parseInt(input.nextLine());
            String[] opts = new String[n];

            for (int i = 0; i < n; i++) {
                System.out.print("Option " + (i + 1) + ": ");
                opts[i] = input.nextLine();
            }

            System.out.print("Correct option number: ");
            String correct = input.nextLine();

            questions.add(new MultipleChoiceQuestion(text, opts, correct));
            System.out.println("MCQ added!");

        } else {
            System.out.println("Correct answer:");
            System.out.println("1 = True");
            System.out.println("2 = False");
            String correct = input.nextLine();

            questions.add(new TrueFalseQuestion(text, correct));
            System.out.println("True/False added!");
        }
    }
