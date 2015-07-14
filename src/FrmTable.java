import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

public class FrmTable extends JFrame{
    private JTable table;
    private DefaultTableModel tableModel;
    private int count = 0;
    private String newProductName;
    private static AppLoader al;
    private JLabel simTime;
    private int confirmProductName;
	private int userinput1;
	private int userinput2;

    public FrmTable() {
    	
    	tableModel = new DefaultTableModel(new Object[]{"Product ID","Product Name","Product Quantity", "Product Threshold"},0) {
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
            	  return !(columnIndex > -1);
            	}
        };

        createGUI();
        al = new AppLoader();
    }

    private void createGUI() {
    	
    	try{
    		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	
    	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	this.setPreferredSize(new Dimension(700,600));;
    	
    	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");    
		Date date = new Date();
    	
        setLayout(new BorderLayout());
        JScrollPane pane = new JScrollPane();
        table = new JTable();
        pane.setViewportView(table);
        simTime = new JLabel(dateFormat.format(date));
        JPanel southPanel = new JPanel();
        JMenuBar menubar = new JMenuBar();
        JMenu filemenu = new JMenu("Product");
        JMenu anothermenu = new JMenu("Simulate");
        JMenu saveemenu = new JMenu("Stock Report");
        menubar.add(filemenu);
        menubar.add(anothermenu);
        menubar.add(saveemenu);
        JMenuItem addProduct = new JMenuItem("Add New Product");
        JMenuItem updateQuantity = new JMenuItem("Update Quantity");
        JMenuItem updateThreshold = new JMenuItem("Update Threshold");
        filemenu.add(addProduct);
        filemenu.add(updateQuantity);
        filemenu.add(updateThreshold);
        JMenuItem simulateDays = new JMenuItem("Create Simulation");
        anothermenu.add(simulateDays);
        JMenuItem saveReport = new JMenuItem("Generate Stock Report");
        JMenuItem makeOrder = new JMenuItem("Generate Purchase Order");
        saveemenu.add(saveReport);
        saveemenu.add(makeOrder);
        JMenuItem refreshTable = new JMenuItem("Refresh Table");
        southPanel.add(simTime);
        add(pane,BorderLayout.CENTER);
        add(menubar, BorderLayout.NORTH);
        add(southPanel, BorderLayout.SOUTH);
        
        updateQuantity.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
            	userinput1 = Integer.parseInt((String)JOptionPane.showInputDialog("Please enter the id of the product you wish to change"));
            	userinput2 = Integer.parseInt((String)JOptionPane.showInputDialog("Product ID: " + userinput1 + "   "
            			+ "Product Name: " + tableModel.getValueAt(userinput1 - 1, 1) + "   Product Quantity: "
            			+ "" + tableModel.getValueAt(userinput1 - 1, 2) + "   Product Threshold: "
            			+ "" + tableModel.getValueAt(userinput1 - 1, 3) + "\n\nPlease enter the new quantity"));
            	al.updateProduct(userinput1, userinput2 );
            	tableModel.setValueAt(userinput2, userinput1-1, 2);
            	}
        });
        updateThreshold.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
            	userinput1 = Integer.parseInt((String)JOptionPane.showInputDialog("Please enter the id of the product you wish to change"));
            	userinput2 = Integer.parseInt((String)JOptionPane.showInputDialog("Product ID: " + userinput1 + "   "
            			+ "Product Name: " + tableModel.getValueAt(userinput1 - 1, 1) + "   Product Quantity: "
            			+ "" + tableModel.getValueAt(userinput1 - 1, 2) + "   Product Threshold: "
            			+ "" + tableModel.getValueAt(userinput1 - 1, 3) + "\n\nPlease enter the new threshold"));
            	al.updateThreshold(userinput1, userinput2 );
            	tableModel.setValueAt(userinput2, userinput1-1, 3);
            	}
        });
        addProduct.addActionListener(new ActionListener(){
        	@Override
        	public void actionPerformed(ActionEvent e){
        		newProductName = (String)JOptionPane.showInputDialog("Please enter the name of the new product");
        		confirmProductName = JOptionPane.showConfirmDialog(null,"Would you like to add the product " + newProductName + "?","Confirm", JOptionPane.YES_NO_OPTION);
				if(confirmProductName == JOptionPane.YES_OPTION){
	        		count = tableModel.getRowCount()+1;
	        		al.addProduct(count, newProductName);
	                tableModel.addRow(new Object[]{count,newProductName,0, 500});
				}else{
					JOptionPane.showMessageDialog(null, "Request cancelled");
				}
        	}
        });
        simulateDays.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {   
            	SimulationGUI.launch(SimulationGUI.class);
            }
        });
        saveReport.addActionListener(new ActionListener(){
        	@Override
        	public void actionPerformed(ActionEvent e){
        		al.generateStockReport();
        	}
        });
        makeOrder.addActionListener(new ActionListener(){
        	@Override
        	public void actionPerformed(ActionEvent e){
        		al.generatePurchaseOrder();
        	}
        });
        table.setModel(tableModel);
        table.getTableHeader().setReorderingAllowed(false);
    }
    
    public void addProductToTable(int productID, String productName, int productQuantity, int productThreshold){
    	DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.addRow(new Object[]{productID, productName, productQuantity, productThreshold});
    }
    
} 