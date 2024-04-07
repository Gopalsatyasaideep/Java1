import java.awt.*;
import java.sql.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.util.List;
import java.util.ArrayList;

class DatabaseConnection {
    public final Connection connection;

    public DatabaseConnection(Connection con) {
        this.connection = con;
    }

    public List<Student> getStudents(String college) {
        List<Student> students = new ArrayList<>();
        try {
            String query;
            if(college.equals("ALL")) {
                query = "SELECT * FROM TOPCODEMIND ORDER BY Codemind DESC LIMIT 10";
            } else {
                query = "SELECT * FROM TOPCODEMIND WHERE College = ? ORDER BY Codemind DESC LIMIT 10";
            }
            PreparedStatement statement = connection.prepareStatement(query);
            if(!college.equals("ALL")) {
                statement.setString(1, college);
            }
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String name = resultSet.getString("Name");
                String branch = resultSet.getString("Branch");
                int age = resultSet.getInt("Age");
                int codemind = resultSet.getInt("Codemind");
                students.add(new Student(id, name, college, branch, age, codemind));
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return students;
    }

    public void insertStudent(Student student) throws SQLException {
        String query = "INSERT INTO TOPCODEMIND (ID, Name, College, Branch, Age, Codemind) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, student.getId());
        statement.setString(2, student.getName());
        statement.setString(3, student.getCollege());
        statement.setString(4, student.getBranch());
        statement.setInt(5, student.getAge());
        statement.setInt(6, student.getCodemind());
        statement.executeUpdate();
        statement.close();
        System.out.println("Values inserted successfully");
    }
}

class Student {
    private final int id;
    private final String name;
    private final String college;
    private final String branch;
    private final int age;
    private final int codemind;

    public Student(int id, String name, String college, String branch, int age, int codemind) {
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

    public String getCollege() {
        return college;
    }

    public String getBranch() {
        return branch;
    }

    public int getId() {
        return id;
    }

    public int getAge() {
        return age;
    }

    public int getCodemind() {
        return codemind;
    }
}

class InsertValues extends JFrame {
    private final DatabaseConnection dbConnection;

    public InsertValues(DatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
        initialize();
    }

    private void initialize() {
        setSize(550, 550);
        setTitle("This is Insertion");
        setResizable(false);
        setLayout(null);

        JLabel idLabel = new JLabel();
        idLabel.setText("Enter ID : ");
        idLabel.setBounds(30, 30, 150,50);

        JTextField idField = new JTextField();
        idField.setBounds(250,42,150,30);

        JLabel nameLabel = new JLabel();
        nameLabel.setText("Enter name : ");
        nameLabel.setBounds(30, 85, 150,50);

        JTextField nameField = new JTextField();
        nameField.setBounds(250,92,150,30);

        JLabel collegeLabel = new JLabel();
        collegeLabel.setText("Enter college : ");
        collegeLabel.setBounds(30, 140, 150,50);

        JTextField collegeField = new JTextField();
        collegeField.setBounds(250,154,150,30);

        JLabel branchLabel = new JLabel();
        branchLabel.setText("Enter branch : ");
        branchLabel.setBounds(30, 195, 150,50);

        JTextField branchField = new JTextField();
        branchField.setBounds(250,205,150,30);

        JLabel ageLabel = new JLabel();
        ageLabel.setText("Enter age : ");
        ageLabel.setBounds(30, 250, 150,50);

        JTextField ageField = new JTextField();
        ageField.setBounds(250,265,150,30);

        JLabel codemindLabel = new JLabel();
        codemindLabel.setText("Enter codemind submissions : ");
        codemindLabel.setBounds(30, 305, 200,50);

        JTextField codemindField = new JTextField();
        codemindField.setBounds(250,316,150,30);

        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(300,450,100,40);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == submitButton){
                    int userId = Integer.parseInt(idField.getText());
                    String userName = nameField.getText();
                    String userCollege = collegeField.getText();
                    String userBranch = branchField.getText();
                    int userAge = Integer.parseInt(ageField.getText());
                    int userCodemind = Integer.parseInt(codemindField.getText());
                    Student student = new Student(userId, userName, userCollege, userBranch, userAge, userCodemind);
                    insertValues(student);
                }
            }
        });

        JButton clearButton = new JButton("Clear");
        clearButton.setBounds(100,450,100,40);

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource()== clearButton){
                    clearFields(idField, nameField, collegeField, branchField, ageField, codemindField);
                }
            }
        });

        add(idLabel);
        add(idField);
        add(nameLabel);
        add(nameField);
        add(branchLabel);
        add(branchField);
        add(ageLabel);
        add(ageField);
        add(collegeLabel);
        add(collegeField);
        add(codemindLabel);
        add(codemindField);
        add(submitButton);
        add(clearButton);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void clearFields(JTextField idField, JTextField nameField, JTextField collegeField, JTextField branchField, JTextField ageField, JTextField codemindField) {
        idField.setText("");
        nameField.setText("");
        collegeField.setText("");
        branchField.setText("");
        ageField.setText("");
        codemindField.setText("");
    }

    private void insertValues(Student student) {
        try {
            dbConnection.insertStudent(student);
        } catch (SQLException e) {
            System.out.println("Error inserting values: " + e.getMessage());
        }
    }
}

public class LeaderBoard extends InsertValues {
    static DatabaseConnection dbConnection;
    static JTextArea outputArea;

    public LeaderBoard(DatabaseConnection dbConnection) {
        super(dbConnection);
    }

    public static void main(String[] args) {
        try {
            String url = "jdbc:mysql://localhost:3306/leaderboard";
            String user = "root";
            String pass = "gopal@2k5";
            Connection con = DriverManager.getConnection(url, user, pass);
            if (con != null)
                System.out.println("Successfully Connected");

            dbConnection = new DatabaseConnection(con);

            ImageIcon titleLogo = new ImageIcon("E:\\Java\\icons8-leaderboard-60.png");

            JFrame mainFrame = new JFrame("LeaderBoard");
            mainFrame.setSize(700, 600);
            mainFrame.setResizable(false);
            mainFrame.setLayout(null);

            JLabel tagLine = new JLabel("Top 10 in Codemind");
            tagLine.setBounds(150, 20, 500, 40);
            tagLine.setFont(new Font("Berlin Sans FB", Font.BOLD, 40));

            JButton aecButton, acetButton, acoeButton, allButton, insertButtton;
            aecButton = new JButton("AEC");
            acetButton = new JButton("ACET");
            acoeButton = new JButton("ACOE");
            allButton = new JButton("ALL");
            insertButtton = new JButton("Insert");

            aecButton.setBounds(70, 100, 100, 40);
            acetButton.setBounds(210, 100, 100, 40);
            acoeButton.setBounds(350, 100, 100, 40);
            allButton.setBounds(490, 100, 100, 40);
            insertButtton.setBounds(270,480,100,40);

            outputArea = new JTextArea("");
            outputArea.setBounds(70, 150, 520, 300);
            outputArea.setBackground(Color.LIGHT_GRAY);

            class ButtonClickListener implements ActionListener {
                public void actionPerformed(ActionEvent button) {
                    if (button.getSource() == aecButton) {
                        displayLeaderboard("AEC");
                    } else if (button.getSource() == acetButton) {
                        displayLeaderboard("ACET");
                    } else if (button.getSource() == acoeButton) {
                        displayLeaderboard("ACOE");
                    } else if (button.getSource() == allButton) {
                        displayLeaderboard("ALL");
                    } else if (button.getSource() == insertButtton) {
                        new InsertValues(dbConnection);
                    }
                }
            }

            aecButton.addActionListener(new ButtonClickListener());
            acetButton.addActionListener(new ButtonClickListener());
            acoeButton.addActionListener(new ButtonClickListener());
            allButton.addActionListener(new ButtonClickListener());
            insertButtton.addActionListener(new ButtonClickListener());

            mainFrame.add(insertButtton);
            mainFrame.add(aecButton);
            mainFrame.add(acetButton);
            mainFrame.add(acoeButton);
            mainFrame.add(allButton);
            mainFrame.add(tagLine);
            mainFrame.add(outputArea);
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainFrame.setVisible(true);
            mainFrame.setIconImage(titleLogo.getImage());
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    private static void displayLeaderboard(String college) {
        String leaderboard = "Top 10 Students from " + college + ":\n";
        if(college.equals("ALL")) {
            leaderboard = "From all three campuses\n";
        }
        List<Student> students = dbConnection.getStudents(college);
        for (int i = 0; i < students.size(); i++) {
            leaderboard += "Id - " + students.get(i).getId() + ", " + "Branch - " + students.get(i).getBranch() + ",   " + "Age - " + students.get(i).getAge() + "--->" + students.get(i).getName() + " - " + students.get(i).getCodemind() + "\n";
        }
        outputArea.setText(leaderboard);
    }
}