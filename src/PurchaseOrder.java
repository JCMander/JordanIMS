import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class PurchaseOrder extends JFrame {
	private JTable table;
    private JButton btnSend;
    private DefaultTableModel tableModel;
    private int totalPrice = 0;

    public PurchaseOrder() {
        tableModel = new DefaultTableModel(new Object[]{"Product Name","Order Quantity", "Supplier", "Price"},0);
        createGUI();
    }

    private void createGUI() {
        setLayout(new BorderLayout());
        JScrollPane pane = new JScrollPane();
        table = new JTable();
        pane.setViewportView(table);
        JPanel southPanel = new JPanel();
        btnSend = new JButton("Send");
        southPanel.add(btnSend);
        add(southPanel,BorderLayout.SOUTH);
        add(pane,BorderLayout.CENTER);
        table.setModel(tableModel);
    
    btnSend.addActionListener(new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Du Herro");
            dispose();
            JOptionPane.showMessageDialog(null, "Your purchase order has been sent");
        }
    });
    
    }
    
    public void addProductToOrder(int productID, String productName, int productQuantity){
    	if(productQuantity<=50){
    		totalPrice+=(10*(250-productQuantity));
    	tableModel.addRow(new Object[]{productName, (250 - productQuantity), "NB Gardens", "$" + (10*(250 - productQuantity)) + ".00"});
    	}
    }
    
    public void addTotalPrice(){
        tableModel.addRow(new Object[]{"Total Price", "", "", "$" + totalPrice + ".00"});
    }
    
} 