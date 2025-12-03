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
interface Playable {
    void startGame();
    void endGame();
}

abstract class User {
    protected String username;
    protected String password;
    protected int score;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public abstract void play();
}

class Student extends User implements Playable {

    public Student(String username, String password) {
        super(username, password);
    }

    @Override
    public void play() {
        System.out.println(username + " is playing...");
    }

    @Override
    public void startGame() {
        System.out.println("Quiz starting for " + username + "...");
    }

    @Override
    public void endGame() {
        System.out.println("Quiz ended. Your score: " + score);
    }

    public void addScore(int points) {
        score += points;
    }

    public int getScore() { return score; }

    public String getName() { return username; }
}

class Admin extends User implements Playable {

    public Admin(String username, String password) {
        super(username, password);
    }

    @Override
    public void play() {
        System.out.println(username + " opened admin panel...");
    }

    @Override
    public void startGame() {
        System.out.println("Admin logged in!");
    }

    @Override
    public void endGame() {
        System.out.println("Admin logged out!");
    }
}

 class QuizGame {
    private static Scanner input = new Scanner(System.in);

    private ArrayList<Question> questions = new ArrayList<>();
    private static ArrayList<Student> leaderboard = new ArrayList<>();
    private User currentUser;

    public QuizGame(User user) {
        this.currentUser = user;
    }

    public void loadDefaultQuestions() {
        questions.add(new MultipleChoiceQuestion(
                "What is 5 + 5?",
                new String[]{"7", "10", "12"},
                "2"
        ));
        questions.add(new TrueFalseQuestion("Java is OOP", "1"));
    }

    public void startQuiz() {
        Student stu = (Student) currentUser;
        stu.startGame();
        stu.play();

        for (Question q : questions) {
            q.display();
            System.out.print("Enter your answer: ");
            String ans = input.nextLine();

            if (q.checkAnswer(ans)) {
                stu.addScore(10);
                System.out.println("Correct!\n");
            } else {
                System.out.println("Wrong!\n");
            }
        }

        leaderboard.add(stu);
        stu.endGame();
    }

    // ----------- ADMIN FEATURES -----------
        

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

    private void viewQuestions() {
        System.out.println("\n--- QUESTIONS ---");
        int i = 1;
        for (Question q : questions) {
            System.out.println(i++ + ". " + q.questionText);
        }
    }

    private void showLeaderboard() {
        System.out.println("\n--- LEADERBOARD ---");

        if (leaderboard.isEmpty()) {
            System.out.println("No scores yet.");
            return;
        }

        leaderboard.sort((a, b) -> b.getScore() - a.getScore());

        for (Student s : leaderboard) {
            System.out.println(s.getName() + " : " + s.getScore());
        }
    }

    private void clearLeaderboard() {
        leaderboard.clear();
        System.out.println("Leaderboard cleared!");
    }

    // STATIC ACCESS
    public static ArrayList<Student> getLeaderboard() { return leaderboard; }
}

 class MainPlay {
    static Scanner input = new Scanner(System.in);

    static HashMap<String, Student> studentAccounts = new HashMap<>();
    static HashMap<String, Admin> adminAccounts = new HashMap<>();

    public static void main(String[] args) {

        adminAccounts.put("admin", new Admin("admin", "1234"));

        System.out.println("=== QUIZ SYSTEM ===");

        while (true) {
            System.out.println("\n1. Login");
            System.out.println("2. Signup (Student only)");
            System.out.println("3. Exit");
            System.out.print("Choose: ");
            String choice = input.nextLine();
            switch (choice) {
                case "1" -> login();
                case "2" -> signupStudent();
                case "3" -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }
static void signupStudent() {
        System.out.print("Choose username: ");
        String user = input.nextLine();
        System.out.print("Choose password: ");
        String pass = input.nextLine();

        if (studentAccounts.containsKey(user)) {
            System.out.println("Username already exists.");
            return;
        }

        studentAccounts.put(user, new Student(user, pass));
        System.out.println("Signup successful!");
    }

    static void login() {
        System.out.print("Username: ");
        String user = input.nextLine();
        System.out.print("Password: ");
        String pass = input.nextLine();

        if (adminAccounts.containsKey(user)) {
            Admin ad = adminAccounts.get(user);
            if (ad.password.equals(pass)) {
                QuizGame q = new QuizGame(ad);
                q.adminPanel();
                return;
            }
        }

        if (studentAccounts.containsKey(user)) {
            Student st = studentAccounts.get(user);
            if (st.password.equals(pass)) {
                QuizGame q = new QuizGame(st);
                q.loadDefaultQuestions();
                q.startQuiz();
                return;
            }
        }

        System.out.println("Invalid login.");
    }
}   
    
