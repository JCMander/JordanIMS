import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class SwingAppGUI extends JFrame {
	private JFrame mainFrame;
	private JLabel headerLabel;
	private JLabel statusLabel;
	private JPanel controlPanel;
	private Object[] columnNames = {"Product ID", "Product Name", "Product Quantity"};
	private DefaultTableModel tableModel;
    private Object[][] data = {{0, "Hi", 30}, {1, "Hello", 30}, {2, "Yo", 30}, {3, "Bruh", 30}};
	JTable GUItable;
	JPanel GUIPanel;
	
	public SwingAppGUI(){
		
		tableModel = new DefaultTableModel(columnNames, 0){
			public boolean isCellEditable(int row, int column){
				return false;
			}
		};
		
		/*        
       public void addProductToTable(String productID, String productName, int productQuantity){
              
              DefaultTableModel model = (DefaultTableModel) stockListTable.getModel();
              model.addRow(new Object[]{productID, productName, productQuantity});
              
       }
       
 */
		
		GUItable = new JTable(tableModel);
		GUIPanel = new JPanel();
		GUIPanel.add(new JScrollPane(GUItable));
		this.add(GUIPanel);
		
		this.pack();
		this.setVisible(true);
		//prepareGUI();
		}

    
	private void prepareGUI() {
		mainFrame = new JFrame("Java SWING Examples");
		mainFrame.setSize(400, 400);
		mainFrame.setLayout(new GridLayout(3, 1));
		headerLabel = new JLabel("", JLabel.CENTER);
		statusLabel = new JLabel("", JLabel.CENTER);
		statusLabel.setSize(350, 100);
		mainFrame.addWindowListener(new WindowAdapter() {
	public void windowClosing(WindowEvent windowEvent) {
		System.exit(0);
		}
		});
		controlPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout());
		mainFrame.add(headerLabel);
		mainFrame.add(controlPanel);
		mainFrame.add(statusLabel);
		mainFrame.setVisible(true);
	
	}
	void showEvent(){
		headerLabel.setText("Press Button");
		JButton okButton = new JButton("OK");
		JButton submitButton = new JButton("Submit");
		JButton cancelButton = new JButton("Cancel");
		okButton.setActionCommand("OK");
		submitButton.setActionCommand( "Submit");
		cancelButton.setActionCommand( "Cancel");
		okButton.addActionListener( new BCL());
		submitButton.addActionListener( new BCL());
		cancelButton.addActionListener( new BCL());
		controlPanel.add(okButton);
		controlPanel.add(submitButton);
		controlPanel.add(cancelButton);
		mainFrame.setVisible(true);
	
	}
	private class BCL implements ActionListener {
		@Override
		public void actionPerformed ( ActionEvent ae) {
			String command = ae.getActionCommand();
			switch (command) {
				case "OK": statusLabel.setText("OK!");
				break;
				case "Submit": statusLabel.setText("Submitted!");
				break;
				case "Cancel": statusLabel.setText("Cancel not possible");
				break;
				}}}

}