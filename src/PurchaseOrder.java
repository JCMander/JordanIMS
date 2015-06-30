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
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

public class PurchaseOrder extends JFrame {
	private static AppLoader al;
	private JTable table;
    private JButton btnSend;
    private JButton btnAdd;
    private JButton btnRemove;
    private DefaultTableModel tableModel;
    private int totalPrice = 0;
    private int userinput1;
    private int userinput2;
    private int idConvert;
    private int priceConvert;
    private String productName;
    private int productQuantity;
    private boolean exists;
    private int[] removeRows;
	private int updateCell;
	private int updateRow;
	private int[] updateRows;

    public PurchaseOrder() {
    	al = new AppLoader();
        tableModel = new DefaultTableModel(new Object[]{"ProductID", "Product Name","Order Quantity", "Supplier", "Price"},0){
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
            	  return !(columnIndex > -1);
            	}
        };;
        createGUI();
    }

    private void createGUI() {
    	
    	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
        	userinput1 = Integer.parseInt((String)JOptionPane.showInputDialog("Please enter the ID of the product you wish to add"));
        	productName = al.getProductName(userinput1);
        	productQuantity = al.getProductQuantity(userinput1); 
        	userinput2 = Integer.parseInt((String)JOptionPane.showInputDialog("Product ID: " + userinput1 + "   "
        			+ "Product Name: " + productName + "   Current Product Quantity: "
        			+ productQuantity + "\n\nPlease enter the quantity you would like to order"));
        	
        	for(int i =0; i<table.getRowCount()-1;i++){
        		idConvert=(Integer)table.getValueAt(i, 0);
        		priceConvert=(Integer)table.getValueAt(i, 4);
        		if(userinput1 == idConvert){
        			exists = true;
        			table.setValueAt(priceConvert + (userinput2*10), i, 4);
        			table.setValueAt((Integer)table.getValueAt(i, 2 ) + userinput2, i, 2);
        		}
        	}
        	
        	tableModel.removeRow(tableModel.getRowCount()-1);
        	if(!exists){
        		tableModel.addRow(new Object[]{userinput1, productName, userinput2, "NB Gardens",(10*userinput2)});
        	}
        	totalPrice+=(10*userinput2);
        	tableModel.addRow(new Object[]{"", "Total Price", "", "", "$" + totalPrice + ".00"});
        }
    });
    
    btnRemove.addActionListener(new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent e) {
        	if(table.getSelectedRow() == -1){
            	userinput1 = Integer.parseInt((String)JOptionPane.showInputDialog("Please enter the ID of the product you wish to remove"));
            	tableModel.removeRow(tableModel.getRowCount()-1);
            	for(int i =0; i<table.getRowCount();i++){
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
        		removeRows = table.getSelectedRows();
        		for(int i=0; i<removeRows.length; i++){
            		priceConvert=(Integer)table.getValueAt(removeRows[i]-i, 4);
            		tableModel.removeRow(removeRows[i]-i);
            		totalPrice-=priceConvert;
        		}
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

    /*public void addTableListener(){
        tableModel.addTableModelListener(new TableModelListener() {
            public void tableChanged(TableModelEvent e) {
            	updateRows = table.getSelectedRows();
            	if(updateRows.length>1){
            		System.out.println("Fantastic");
            	}else if(exists){
	            	updateRow = table.getSelectedRow();
	            	System.out.println(updateRow);
	        		priceConvert=(Integer)table.getValueAt(updateRow, 4);
	        		System.out.println(priceConvert);
	            	updateCell=Integer.parseInt((String) tableModel.getValueAt(updateRow,2));
	            	System.out.println(updateCell);
	            	//table.setValueAt((updateCell*10), updateRow, 4);
            	}
            }
          });
    }*/
    
} 