import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManageEmployees {

    JPanel ManageEmployeeScreen;
    private JFormattedTextField ManagerTitle;
    private JButton logOutButton;
    private JButton homeButton;
    private JList EmployeeList;

    private void updateList(){
        DBConnection con = new DBConnection();
        String[] usernames = con.getAllEmployees();
        String[] employees = new String[usernames.length];
        int i = 0;
        for(String username : usernames){
            employees[i++] = username + "        " + con.getEmployeeNameByUsername(username) + "      " + ((con.isEmployeeManagerByUsername(username) ? "*manager*" : ""));
        }
        EmployeeList.setListData(employees);
        con.close();
    }

    public ManageEmployees() {
        updateList();
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.frame.setContentPane(new Manager().ManagerScreen);
                Main.frame.pack();
            }
        });
        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.frame.setContentPane(new Login().LoginPanel);
                Main.frame.pack();
            }
        });
    }
}
