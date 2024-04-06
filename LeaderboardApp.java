import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LeaderboardApp extends JFrame {
    private JTextArea leaderboardTextArea;
    private JButton aecButton, acetButton, acoeButton, allButton;
    private DatabaseConnection dbConnection;

    public LeaderboardApp() {
        setTitle("Leaderboard");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        leaderboardTextArea = new JTextArea();
        leaderboardTextArea.setEditable(false);

        aecButton = new JButton("AEC");
        acetButton = new JButton("ACET");
        acoeButton = new JButton("ACOE");
        allButton = new JButton("ALL");

        aecButton.addActionListener(new ButtonClickListener());
        acetButton.addActionListener(new ButtonClickListener());
        acoeButton.addActionListener(new ButtonClickListener());
        allButton.addActionListener(new ButtonClickListener());

        JPanel buttonPanel = new JPanel(new GridLayout(1, 4));
        buttonPanel.add(aecButton);
        buttonPanel.add(acetButton);
        buttonPanel.add(acoeButton);
        buttonPanel.add(allButton);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(buttonPanel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(leaderboardTextArea), BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);

        // Initialize database connection
        dbConnection = new DatabaseConnection();
    }

    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String college = "";
            if (e.getSource() == aecButton) {
                college = "AEC";
            } else if (e.getSource() == acetButton) {
                college = "ACET";
            } else if (e.getSource() == acoeButton) {
                college = "ACOE";
            }
            displayLeaderboard(college);
        }
    }

    private void displayLeaderboard(String college) {
        String leaderboard = "Top 10 Students for " + college + ":\n";
        List<Student> students = dbConnection.getTopStudents(college);
        for (int i = 0; i < students.size(); i++) {
            leaderboard += (i + 1) + ". " + students.get(i).getName() + " - " + students.get(i).getCodemind() + "\n";
        }
        leaderboardTextArea.setText(leaderboard);
    }

    public static void main(String[] args) {
        new LeaderboardApp();
    }
}

class DatabaseConnection {
    private final String url = "jdbc:mysql://localhost:3306/leaderboard";
    private final String username = "root";
    private final String password = "gopal@2k5";

    public List<Student> getTopStudents(String college) {
        List<Student> students = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String query = "SELECT * FROM TOPCODEMIND WHERE College = ? ORDER BY Codemind DESC LIMIT 10";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, college);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String id = resultSet.getString("ID");
                String name = resultSet.getString("Name");
                String branch = resultSet.getString("Branch");
                int age = resultSet.getInt("Age");
                int codemind = resultSet.getInt("Codemind");
                students.add(new Student(id, name, college, branch, age, codemind));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }
}

class Student {
    private String id;
    private String name;
    private String college;
    private String branch;
    private int age;
    private int codemind;

    public Student(String id, String name, String college, String branch, int age, int codemind) {
        this.id = id;
        this.name = name;
        this.college = college;
        this.branch = branch;
        this.age = age;
        this.codemind = codemind;
    }

    public String getName() {
        return name;
    }
    public int getCodemind(){
        return codemind;
    }
}