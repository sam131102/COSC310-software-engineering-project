import javax.swing.*;

public class Main {
    static JFrame frame = new JFrame("Group 4 Inventory Management System");
    public static void main(String[] args) {
        frame.setContentPane(new Login().LoginPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
