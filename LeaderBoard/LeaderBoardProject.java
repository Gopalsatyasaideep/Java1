import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

class StudentInfo {
    private final int id;
    private final String name;
    private final String college;
    private final String branch;
    private final int age;
    private final int codemind;

    public StudentInfo(int id, String name, String college, String branch, int age, int codemind) {
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

class InsertNewData extends JFrame{
    public InsertNewData(){
            setSize(550, 550);
            setTitle("Insert");
            setResizable(false);
            setLayout(null);

            ImageIcon insertLogo = new ImageIcon("E:\\JavaDatabase\\pngwing.com.png");

            JLabel idLabel = new JLabel();
            idLabel.setText("Enter ID : ");
            idLabel.setFont(new Font( "", Font.BOLD, 15));
            idLabel.setBounds(30, 30, 150,50);

            JTextField idField = new JTextField();
            idField.setFont(new Font( "", Font.BOLD, 15));
            idField.setBounds(250,42,150,30);

            JLabel nameLabel = new JLabel();
            nameLabel.setText("Enter name : ");
            nameLabel.setFont(new Font( "", Font.BOLD, 15));
            nameLabel.setBounds(30, 85, 150,50);

            JTextField nameField = new JTextField();
            nameField.setBounds(250,92,150,30);
            nameField.setFont(new Font( "", Font.BOLD, 15));

            JLabel collegeLabel = new JLabel();
            collegeLabel.setText("Enter college : ");
            collegeLabel.setBounds(30, 140, 150,50);
            collegeLabel.setFont(new Font( "", Font.BOLD, 15));

            JTextField collegeField = new JTextField();
            collegeField.setBounds(250,154,150,30);
            collegeField.setFont(new Font( "", Font.BOLD, 15));

            JLabel branchLabel = new JLabel();
            branchLabel.setText("Enter branch : ");
            branchLabel.setBounds(30, 195, 150,50);
            branchLabel.setFont(new Font( "", Font.BOLD, 15));

            JTextField branchField = new JTextField();
            branchField.setBounds(250,205,150,30);
            branchField.setFont(new Font( "", Font.BOLD, 15));

            JLabel ageLabel = new JLabel();
            ageLabel.setText("Enter age : ");
            ageLabel.setBounds(30, 250, 150,50);
            ageLabel.setFont(new Font( "", Font.BOLD, 15));

            JTextField ageField = new JTextField();
            ageField.setBounds(250,265,150,30);
            ageField.setFont(new Font( "", Font.BOLD, 15));

            JLabel codemindLabel = new JLabel();
            codemindLabel.setText("Enter codemind submissions: ");
            codemindLabel.setBounds(28, 305, 250,50);
            codemindLabel.setFont(new Font( "", Font.BOLD, 15));

            JTextField codemindField = new JTextField();
            codemindField.setBounds(251,316,150,30);
            codemindField.setFont(new Font( "", Font.BOLD, 15));

            JButton submitButton = new JButton("Submit");
            submitButton.setBounds(300,450,100,40);

            submitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == submitButton) {
                        String userIdText = idField.getText();
                        String userName = nameField.getText();
                        String userCollege = collegeField.getText();
                        String userBranch = branchField.getText();
                        String userAgeText = ageField.getText();
                        String userCodemindText = codemindField.getText();

                        if (userIdText.isEmpty() || userName.isEmpty() || userCollege.isEmpty() || userBranch.isEmpty() || userAgeText.isEmpty() || userCodemindText.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        else {
                            try {
                                int userId = Integer.parseInt(userIdText);
                                int userAge = Integer.parseInt(userAgeText);
                                int userCodemind = Integer.parseInt(userCodemindText);
                                if (!userName.matches("[a-zA-Z]+") || !userCollege.matches("[a-zA-Z]+") || !userBranch.matches("[a-zA-Z]+")) {
                                    JOptionPane.showMessageDialog(null, "Please enter only letters for Name, College, and Branch", "Error", JOptionPane.ERROR_MESSAGE);
                                }
                                else {
                                    StudentInfo student = new StudentInfo(userId, userName, userCollege, userBranch, userAge, userCodemind);
                                    insertStudent(student);
                                    clearFields(idField, nameField, collegeField, branchField, ageField, codemindField);
                                }
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(null, "Please enter valid integer values for ID, Age and Codemind", "Error", JOptionPane.ERROR_MESSAGE);
                            } catch (SQLException ex) {
                                JOptionPane.showMessageDialog(null, "Error inserting data into the database: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                }
            });


            JButton clearButton = new JButton("Clear");
            clearButton.setBounds(100,450,100,40);
            clearButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if(e.getSource() == clearButton){
                        clearFields(idField, nameField, collegeField, branchField, ageField, codemindField);
                    }
                }
            });


            setResizable(false);
            setIconImage(insertLogo.getImage());
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

         private void insertStudent(StudentInfo student) throws SQLException {
             String URL = "jdbc:mysql://localhost:3306/leaderboard";
             String User = "root";
             String Password = "gopal@2k5";
             try (Connection connection = DriverManager.getConnection(URL, User, Password)) {
                 String query = "INSERT INTO TOPCODEMIND (ID, Name, College, Branch, Age, Codemind) VALUES (?, ?, ?, ?, ?, ?)";
                 PreparedStatement statement = connection.prepareStatement(query);
                 statement.setInt(1, student.getId());
                 statement.setString(2, student.getName());
                 statement.setString(3, student.getCollege());
                 statement.setString(4, student.getBranch());
                 statement.setInt(5, student.getAge());
                 statement.setInt(6, student.getCodemind());
                 statement.executeUpdate();
             }
         }
         private void clearFields(JTextField idField, JTextField nameField, JTextField collegeField, JTextField branchField, JTextField ageField, JTextField codemindField) {
             idField.setText("");
             nameField.setText("");
             collegeField.setText("");
             branchField.setText("");
             ageField.setText("");
             codemindField.setText("");
         }

    }
    public class Lead extends JFrame {
        private JTextArea textArea;
        public Lead() {

            setTitle("LeaderBoard");
            setSize(700, 600);
            setResizable(false);
            setLayout(null);

            ImageIcon titleLogo = new ImageIcon("E:\\Java\\icons8-leaderboard-60.png");
            JLabel tagLine = new JLabel("Top 10 in Codemind");
            tagLine.setBounds(150, 20, 500, 40);
            tagLine.setFont(new Font("Berlin Sans FB", Font.BOLD, 40));

            JButton aec = new JButton("AEC");
            JButton acet = new JButton("ACET");
            JButton acoe = new JButton("ACOE");
            JButton all = new JButton("ALL");
            JButton insert = new JButton("Insert");

            aec.setBounds(70, 100, 100, 40);
            acet.setBounds(210, 100, 100, 40);
            acoe.setBounds(350, 100, 100, 40);
            all.setBounds(490, 100, 100, 40);
            insert.setBounds(270,480,100,40);

            textArea = new JTextArea("");
            textArea.setBounds(70, 150, 520, 300);
            textArea.setBorder(BorderFactory.createLineBorder(Color.black, 2, true));
            textArea.setBackground(Color.LIGHT_GRAY);

            add(tagLine);
            setIconImage(titleLogo.getImage());
            add(aec);
            add(acet);
            add(acoe);
            add(all);
            add(insert);
            add(textArea);
            textArea.setEditable(false);
            // Add action listener to the button
            aec.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String str = "aec";
                    fetchDataFromDatabase(str,textArea);
                }
            });
            acet.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String str = "acet";
                    fetchDataFromDatabase(str,textArea);
                }
            });
            acoe.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String str = "acoe";
                    fetchDataFromDatabase(str,textArea);
                }
            });
            all.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String str = "all";
                    fetchDataFromDatabase(str,textArea);
                }
            });

            insert.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new InsertNewData();
                }
            });
        }
        private void fetchDataFromDatabase(String str ,JTextArea textArea) {
            String URL = "jdbc:mysql://localhost:3306/leaderboard";
            String User = "root";
            String Password = "gopal@2k5";

            try {
                Connection connection = DriverManager.getConnection(URL, User, Password);
                Statement statement1 = connection.createStatement();
                ResultSet resultSet = switch (str) {
                    case "aec" -> statement1.executeQuery("SELECT * FROM topcodemind WHERE COLLEGE='aec' ORDER BY CODEMIND DESC limit 10");
                    case "acet" -> statement1.executeQuery("SELECT * FROM topcodemind  WHERE COLLEGE='acet' ORDER BY CODEMIND DESC limit 10");
                    case "acoe" -> statement1.executeQuery("SELECT * FROM topcodemind  WHERE COLLEGE='acoe' ORDER BY CODEMIND DESC limit 10");
                    case "all" -> statement1.executeQuery("SELECT * FROM topcodemind  ORDER BY CODEMIND DESC limit 10 ");
                    default -> null;
                };
                String leaderboard = "";
                while (resultSet.next()) {
                    int n=1;
                    int id = resultSet.getInt("ID");
                    String name = resultSet.getString("NAME");
                    String college = resultSet.getString("COLLEGE");
                    String branch = resultSet.getString("BRANCH");
                    int age = resultSet.getInt("AGE");
                    String codemind = resultSet.getString("CODEMIND");
                    if(n==1) leaderboard+=("ID\t" + "Name\t"+"College\t" + "Branch\t" + "Age\t" + "Codemind\t"+"\n");
                    leaderboard+=(id+"\t"+name+"\t"+college+"\t"+branch+"\t"+age+"\t"+codemind+"\t");
                    n=1;
                }
                textArea.setText(leaderboard);
                textArea.setFont(new Font("",Font.BOLD,13));
            } catch (SQLException e) {
                //System.out.println("ERROR OCCURED"+e.getMessage());
                JOptionPane.showMessageDialog(null,"Error in showing Data","Error",JOptionPane.ERROR_MESSAGE);
            }
        }
        public static void main(String[] args) {
            Lead app = new Lead();
            app.setVisible(true);
        }
}

