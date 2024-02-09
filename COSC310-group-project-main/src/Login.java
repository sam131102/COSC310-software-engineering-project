import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login {
    private JPasswordField passwordInput;
    private JFormattedTextField COSC310LoginFormattedTextField;
    private JButton cashierLoginButton;
    private JButton managerLoginButton;
    private JFormattedTextField userNameInput;
    private JFormattedTextField passwordFormattedTextField;
    public JPanel LoginPanel;
    private JTextPane textPane1;
    private JFormattedTextField usernameFormattedTextField1;
    private JLabel messageOutput;

    public Login() {
        managerLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DBConnection con = new DBConnection();
                try{
                    if(con.checkLogin(userNameInput.getText(), new String(passwordInput.getPassword()), true)){
                        con.close();
                        Main.frame.setContentPane(new Manager().ManagerScreen);
                        Main.frame.pack();
                    }
                }catch(IllegalArgumentException invalid){
                    con.close();
                    messageOutput.setText(invalid.getMessage());
                }
            }
        });
        cashierLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DBConnection con = new DBConnection();
                try{
                    if(con.checkLogin(userNameInput.getText(), new String(passwordInput.getPassword()), false)){
                        con.close();
                        Main.frame.setContentPane(new Cashier().CashierScreen);
                        Main.frame.pack();
                    }
                }catch(IllegalArgumentException invalid){
                    con.close();
                    messageOutput.setText(invalid.getMessage());
                }
            }
        });
    }
}
