import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SwingExample {
    public static void main(String[] args) {
        // Create the main frame
        JFrame frame = new JFrame("Swing Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLayout(new FlowLayout());

        // Create components
        JLabel label = new JLabel("Click the button!");
        JButton button = new JButton("Click Me");

        // Add action listener to button
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                label.setText("Button was clicked!");
            }
        });

        // Add components to frame
        frame.add(button);
        frame.add(label);

        // Display the frame
        frame.setVisible(true);
    }
}
