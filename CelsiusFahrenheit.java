import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;
//import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Font;
public class CelsiusFahrenheit {
    public static void main(String[] args){
        JFrame celFahren = new JFrame("My frame");
        celFahren.setVisible(true);
        celFahren.setSize(750, 600);
        celFahren.getContentPane().setBackground(Color.DARK_GRAY);

        JTextField celTextField = new JTextField();
        celFahren.add(celTextField);
        celTextField.setFont(new Font("MV Boli", Font.BOLD, 20));
        celTextField.setBounds(400, 200, 200, 50);

        JTextField farTextField = new JTextField();
        celFahren.add(farTextField);
        farTextField.setFont(new Font("MV Boli", Font.BOLD, 20));
        farTextField.setBounds(700, 200, 200, 50);

        JButton celsiButton = new JButton("Celsius");
        celsiButton.setBounds(400, 270, 200, 50);
        celFahren.add(celsiButton);

        JButton farButton = new JButton("Fahrenheit");
        farButton.setBounds(700, 270, 200, 50);
        celFahren.add(farButton);
        celFahren.setLayout(null);

        celsiButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String celsiusInput = celTextField.getText();
                if(celsiusInput.isEmpty()){
                    JOptionPane.showMessageDialog(celFahren, "Enter valid number","An exception", JOptionPane.WARNING_MESSAGE);
                    celTextField.setText("");
                }
                else if(!celsiusInput.matches("\\d+")){
                    JOptionPane.showMessageDialog(celFahren, "Only integers were allowed","An String exception", JOptionPane.WARNING_MESSAGE);
                    celTextField.setText("");
                }
                else{
                    int chnged = Integer.parseInt(celsiusInput);
                    float answer = (float)(chnged*(1.8))+32;
                    farTextField.setText(String.valueOf(answer));
                }
            } 
            
        });
    }
}
