import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class SwingAppGUI extends JFrame {

	private Object[] columnNames = {"Product ID", "Product Name", "Product Quantity"};
	private DefaultTableModel tableModel;
	JTable GUItable;
	JPanel GUIPanel;
		
	public SwingAppGUI(){ // Creates the table and places the headers in their respective places
			
		tableModel = new DefaultTableModel(columnNames, 0){
			public boolean isCellEditable(int row, int column){
				return false;
			}
		};
			
		GUItable = new JTable(tableModel);
		GUIPanel = new JPanel();
		GUIPanel.add(new JScrollPane(GUItable));
		this.add(GUIPanel);
		this.pack();
		this.setVisible(true);
		this.setTitle("Product List");
		this.setSize(500, 500);
		this.setJMenuBar(new MenuBarGUI());
		}
		
	public void addProductToTable(int productID, String productName, int productQuantity){ // Method to add the products into the table
        
        DefaultTableModel model = (DefaultTableModel) GUItable.getModel();
        model.addRow(new Object[]{productID, productName, productQuantity});
	
	}
	
	public class MenuBarGUI extends JMenuBar {
		
		private JMenu file;
		private JMenuItem update;
		
		public MenuBarGUI(){
			file = new JMenu("File");
			update = new JMenuItem("Update Quantity");
			file.add(update);
			this.add(file);
		}
		
	}

}
