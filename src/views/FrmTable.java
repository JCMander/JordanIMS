package views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import controllers.AppLoader;

/** Class used to create the GUI **/

public class FrmTable extends JFrame{

	private static final long serialVersionUID = 1L;
	private JTable table;
    private DefaultTableModel tableModel;
    private int count = 0;
    private String newProductName;
    private static AppLoader al;
    private int confirmProductName;
	private int userinput1;
	private int userinput2;
	private int viewReport;
    private static String[] options = new String[2];

    public FrmTable() {
    	
    	tableModel = new DefaultTableModel(new Object[]{"Product ID","Product Name","Product Quantity", "Product Threshold"},0) {

			private static final long serialVersionUID = 1L;

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
    	
        setLayout(new BorderLayout());
        JScrollPane pane = new JScrollPane();
        table = new JTable();
        pane.setViewportView(table);

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Product");
        JMenu saveMenu = new JMenu("Reports");
        menuBar.add(fileMenu);
        menuBar.add(saveMenu);
        JMenuItem addProduct = new JMenuItem("Add New Product");
        JMenuItem updateThreshold = new JMenuItem("Update Threshold");
        fileMenu.add(addProduct);
        fileMenu.add(updateThreshold);
        JMenuItem saveReport = new JMenuItem("Generate Stock Report");
        saveMenu.add(saveReport);
        add(pane,BorderLayout.CENTER);
        add(menuBar, BorderLayout.NORTH);

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

        saveReport.addActionListener(new ActionListener(){
        	@Override
        	public void actionPerformed(ActionEvent e){
        	    options[0] = new String("OK");
        	    options[1] = new String("View Report");
    		    viewReport = JOptionPane.showOptionDialog(null, "Stock report saved","Low Stock Levels", 0,JOptionPane.INFORMATION_MESSAGE,null,options,null);
        		new StockReport();
        		if(viewReport == 1){
					try {
						 
						if ((new File(StockReport.FILE)).exists()) {
				 
							Process p = Runtime
							   .getRuntime()
							   .exec("rundll32 url.dll,FileProtocolHandler " + StockReport.FILE);
							p.waitFor();
				 
						} else {
				 
						    JOptionPane.showMessageDialog(null, "This file does not exist");				 
						}

				 
				  	  } catch (Exception ex) {
						ex.printStackTrace();
					  }
        		}
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