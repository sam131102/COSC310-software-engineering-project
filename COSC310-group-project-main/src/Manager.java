import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

public class Manager {
    public JPanel ManagerScreen;
    private JTabbedPane tabbedPane;
    private JTable InventoryTable; // Display Inventory Data from Database
    private JList SalesList;
    private JList WarningsList;
    private JFormattedTextField SalesTitle;
    private JFormattedTextField RecentSalesTitle;
    private JProgressBar SalesProgress;
    private JFormattedTextField managerFormattedTextField;
    private JButton ManageEmployeesButton;
    private JButton suppliersButton;
    private JButton logOutButton;
    private JFormattedTextField ManagerTitle;
    private JTextPane SaleDetails;
    private JButton saveChagesButton;
    private JButton resetButton;
    private JLabel inventoryMessageOutput;
    private JTextField nameTextField;
    private JTextField priceTextField;
    private JTextField stockTextField;
    private JButton addProductButton;
    private JComboBox supplierDropDown;
    private JTextField totalTextField;

    private JPanel Sales;

    NumberFormat moneyFormat;

    private void updateSales(){
        DBConnection con = new DBConnection();
        SaleDetails.setText("");
        double monthlySales = con.getThisMonthsSales();
        SalesProgress.setString(moneyFormat.format(monthlySales));
        SalesProgress.setValue((int)monthlySales);
        int[] sales = con.getAllSaleIds();
        String[] salesInfo = new String[sales.length];
        int i = 0;
        for(int id : sales){
            salesInfo[i++] = "Sale #" + id + ":  " + con.getSaleInfoById(id);
        }
        SalesList.setListData(salesInfo);
    }

    private void updateInventory(){
        inventoryMessageOutput.setText("");
        DBConnection con = new DBConnection();
        int[] supplierIds = con.getAllSuppliers();
        String[] suppliers = new String[supplierIds.length];
        int i = 0;
        for(int id : supplierIds){
            suppliers[i++] = "#" + id + ": " + con.getSupplierNameById(id);
        }
        supplierDropDown.setModel(new DefaultComboBoxModel(suppliers));
        DefaultTableModel model = new DefaultTableModel() {
            boolean[] canEdit = new boolean[]{
                    false, false, false, true, true
            };
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        };
        InventoryTable.setModel(model);
        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Supplier");
        model.addColumn("Price");
        model.addColumn("Stock");
        int[] ids = con.getAllProductIds();
        for(int id : ids){
            String name = con.getProductName(id);
            String supplier = con.getProductSupplier(id);
            double price = con.getProductPrice(id);
            int stock = con.getProductStock(id);
            model.insertRow(0, new Object[] {"" + id, name, supplier, "" + price, "" + stock});
        }
        con.close();
    }

    private void updateWarnings(){
        DBConnection con = new DBConnection();
        int[] ids = con.getLowStockProductIds();
        String[] warnings = new String[ids.length];
        int i = 0;
        for(int id : ids){
            String name = con.getProductName(id);
            int stock = con.getProductStock(id);
            warnings[i++] = "Low Stock: " + name + ", " + stock + " left";
        }
        WarningsList.setListData(warnings);
        con.close();
    }
    public Manager() {
        moneyFormat = NumberFormat.getCurrencyInstance();
        updateSales();
        tabbedPane.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent changeEvent) {
                JTabbedPane sourceTabbedPane = (JTabbedPane) changeEvent.getSource();
                int index = sourceTabbedPane.getSelectedIndex();
                switch(index){
                    case 0: updateSales();
                    case 1: updateInventory();
                    case 2: updateWarnings();
                }
            }
        });

        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.frame.setContentPane(new Login().LoginPanel);
                Main.frame.pack();
            }
        });

        SalesList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting()) return;
                JList source = (JList)e.getSource();
                if(source.isSelectionEmpty()) return;
                String selected = source.getSelectedValue().toString();
                int purchaseId = Integer.parseInt(selected.substring(selected.indexOf('#') + 1, selected.indexOf(':')));
                DBConnection con = new DBConnection();
                String items = con.getSaleItemsById(purchaseId);
                SaleDetails.setText(items);
                con.close();
            }
        });
        ManageEmployeesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.frame.setContentPane(new ManageEmployees().ManageEmployeeScreen);
                Main.frame.pack();
            }
        });
        suppliersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.frame.setContentPane(new Suppliers().SuppliersScreen);
                Main.frame.pack();
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateInventory();
            }
        });

        saveChagesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(int row = 0; row < InventoryTable.getRowCount(); row++){
                    try{
                        int id = Integer.parseInt((String)InventoryTable.getValueAt(row, 0));
                        double price = Double.parseDouble((String)InventoryTable.getValueAt(row, 3));
                        int stock = Integer.parseInt((String)InventoryTable.getValueAt(row, 4));
                        if(price < 0 || stock < 0) throw new IllegalArgumentException();
                        DBConnection con = new DBConnection();
                        con.setProductPrice(id, price);
                        con.setProductStock(id, stock);
                        con.close();
                        inventoryMessageOutput.setText("");
                    }catch(Exception error){
                        inventoryMessageOutput.setText("Invalid value");
                    }
                }
            }
        });

        addProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String name = nameTextField.getText();
                    if(name.equals("")) throw new IllegalArgumentException();
                    Double price = Double.parseDouble(priceTextField.getText());
                    int stock = Integer.parseInt(stockTextField.getText());
                    String supplierString = (String)supplierDropDown.getSelectedItem();
                    int supplier_id = Integer.parseInt(supplierString.substring(supplierString.indexOf('#') + 1, supplierString.indexOf(':')));
                    if(price < 0 || stock < 0) throw new IllegalArgumentException();
                    DBConnection con = new DBConnection();
                    con.addNewProduct(name, price, supplier_id, stock);
                    con.close();
                    inventoryMessageOutput.setText("");
                    updateInventory();
                }catch(Exception error){
                    inventoryMessageOutput.setText("Invalid value");
                }
            }
        });
    }
}
