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
    private JButton btnAdd;
    private JButton btnRemove;
    private DefaultTableModel tableModel;
    private int totalPrice = 0;
    private int userinput1;
    private int idConvert;
    private int priceConvert;

    public PurchaseOrder() {
        tableModel = new DefaultTableModel(new Object[]{"ProductID", "Product Name","Order Quantity", "Supplier", "Price"},0){
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
            	  return !(columnIndex != 2);
            	}
        };;
        createGUI();
    }

    private void createGUI() {
    	
        setLayout(new BorderLayout());
        JScrollPane pane = new JScrollPane();
        table = new JTable();
        pane.setViewportView(table);
        JPanel southPanel = new JPanel();
        btnAdd = new JButton("Add");
        southPanel.add(btnAdd);
        btnRemove = new JButton("Remove");
        southPanel.add(btnRemove);
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
            for (int i = tableModel.getRowCount() - 1; i >= 0; i--) {
                tableModel.removeRow(i);
            }
            totalPrice=0;
        }
    });
    
    btnAdd.addActionListener(new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent e) {
        	tableModel.removeRow(tableModel.getRowCount()-1);
        	
        	totalPrice+=1;
        	
        	tableModel.addRow(new Object[]{"", "Total Price", "", "", "$" + totalPrice + ".00"});
        }
    });
    
    btnRemove.addActionListener(new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent e) {
        	if(table.getSelectedRow() == -1){
            	userinput1 = Integer.parseInt((String)JOptionPane.showInputDialog("Please enter the ID of the product you wish to remove"));
            	tableModel.removeRow(tableModel.getRowCount()-1);
            	for(int i =0; i<table.getRowCount()-1;i++){
            		idConvert=(Integer)table.getValueAt(i, 0);
            		priceConvert=(Integer)table.getValueAt(i, 4);
            		System.out.println(priceConvert);
            		if(userinput1 == idConvert){
            			tableModel.removeRow(i);
            			totalPrice-=priceConvert;
            		}
            	}
        	}else{
        		tableModel.removeRow(tableModel.getRowCount()-1);
        		priceConvert=(Integer)table.getValueAt(table.getSelectedRow(), 4);
        		tableModel.removeRow(table.getSelectedRow());
        		totalPrice-=priceConvert;
        	}
        	tableModel.addRow(new Object[]{"", "Total Price", "", "", "$" + totalPrice + ".00"});
        }
    });
    
    }
    
    public void addProductToOrder(int productID, String productName, int productQuantity){

    	if(productQuantity<=50){
    		totalPrice+=(10*(250-productQuantity));
    	tableModel.addRow(new Object[]{productID, productName, (250 - productQuantity), "NB Gardens",(10*(250 - productQuantity))});
    	}
    }
    
    public void addTotalPrice(){
        tableModel.addRow(new Object[]{"", "Total Price", "", "", "$" + totalPrice + ".00"});
    }
    
} 