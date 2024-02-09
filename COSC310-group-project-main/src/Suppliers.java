import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Suppliers {
    JPanel SuppliersScreen;
    private JFormattedTextField ManagerTitle;
    private JButton logOutButton;
    private JButton homeButton;
    private JButton addSupplierButton;
    private JList SupplierList;
    private JTextField newSupplierTextField;
    private JLabel messageOutput;

    private void updateList(){
        DBConnection con = new DBConnection();
        int[] ids = con.getAllSuppliers();
        String[] suppliers = new String[ids.length];
        int i = 0;
        for(int id : ids){
            suppliers[i++] = "#" + id + ": " + con.getSupplierNameById(id);
        }
        SupplierList.setListData(suppliers);
    }

    public Suppliers() {
        messageOutput.setText("");
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

        addSupplierButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = newSupplierTextField.getText();
                if(name.equals("")) {
                    messageOutput.setText("Enter a name");
                    return;
                }
                DBConnection con = new DBConnection();
                con.addNewSupplier(name);
                con.close();
                messageOutput.setText("");
                updateList();
            }
        });
    }

}
