import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
public class GruserInterface {
    public static void main(String[] args){
        JFrame myFrame = new JFrame("Insta");
        myFrame.setSize(500, 500);
        myFrame.setResizable(false);
        myFrame.setLayout(null);

        //setting image icon on frame
        ImageIcon image = new ImageIcon("E:/Java/WhatsApp Image 2024-03-16 at 09.44.02_8fb6a7e1.jpg"); // To set image on top of the frame
        myFrame.setIconImage(image.getImage());

        //Combo Box
        String[] boxItems = {"Mustang","Stingray","GTR","Dodge"}; //inserting elements using an array 
        // String[] associations = {"prrrrr","dwefwf","wfefw","ewef"};
        Color[] clrs = {Color.blue, Color.gray, Color.orange, Color.green};
        JComboBox dropBox = new JComboBox(boxItems);
        dropBox.setFont(new Font("MV Boli", Font.BOLD, 15));
        dropBox.setSelectedItem(null);
        dropBox.setBounds(100,100,150,50);

        // dropBox.addItem("Stingray"); ////inserting each element
        // dropBox.addItem("Dodge");
        // dropBox.addItem("GTR");
        // dropBox.addItem("Skyline");
        JLabel labell = new JLabel();
        labell.setOpaque(true);
        labell.setBounds(100, 200, 200, 200);
        labell.setBackground(Color.black);

        dropBox.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                // System.out.println(dropBox.getSelectedItem());
                // System.out.println(dropBox.getSelectedIndex());   
                //int ind = dropBox.getSelectedIndex();
                // JOptionPane.showMessageDialog(dropBox, associations[ind]);
                // if(ind==1){
                //     labell.setBackground(Color.blue); //making decisions through indices
                // }
                // else if(dropBox.getSelectedItem().equals("GTR")){ //making decision through values
                //     labell.setBackground(Color.orange);
                // }
                // else if(ind==3){
                //     labell.setBackground(Color.gray);
                // }
                // else{
                //     labell.setBackground(Color.pink);
                // }
                labell.setBackground(clrs[dropBox.getSelectedIndex()]); // making decisions using indices and assigning colors with an array

            }
        });

        myFrame.add(labell);
        myFrame.add(dropBox);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setVisible(true);
    }
}
