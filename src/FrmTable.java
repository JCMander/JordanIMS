import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
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
	private int updateCell;
	private int updateRow;

    public FrmTable() {
    	
    	tableModel = new DefaultTableModel(new Object[]{"Product ID","Product Name","Product Quantity"},0) {
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
            	  return !(columnIndex > -1);
            	}
        };
    	
        createGUI();
        al = new AppLoader();
    }

    private void createGUI() {
    	
    	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	
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
        filemenu.add(updateQuantity);
        filemenu.add(addProduct);
        JMenuItem simulateDays = new JMenuItem("Default Simulation (5 days)");
        JMenuItem simulateCustomDays = new JMenuItem("Custom Simulation");
        anothermenu.add(simulateDays);
        anothermenu.add(simulateCustomDays);
        JMenuItem saveReport = new JMenuItem("Generate Stock Report");
        JMenuItem makeOrder = new JMenuItem("Generate Purchase Order");
        saveemenu.add(saveReport);
        saveemenu.add(makeOrder);
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
            			+ "" + tableModel.getValueAt(userinput1 - 1, 2) + "\n\nPlease enter the new quantity"));
            	al.updateProduct(userinput1, userinput2 );
            	tableModel.setValueAt(userinput2, userinput1-1, 2);
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
	                tableModel.addRow(new Object[]{count,newProductName,0});
				}else{
					JOptionPane.showMessageDialog(null, "Request cancelled");
				}
        	}
        });
        simulateDays.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Goteem");
            }
        });
        simulateCustomDays.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Goteem");
            }
        });
        saveReport.addActionListener(new ActionListener(){
        	@Override
        	public void actionPerformed(ActionEvent e){
        		al.generateStockReport();
        		System.out.println("Fantastic");
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
    
    public void addProductToTable(int productID, String productName, int productQuantity){
    	DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.addRow(new Object[]{productID, productName, productQuantity});
    }
    
    public void simulateDays(){}
    
    public void generatePurchaseOrder(){}
    
    /*public void addTableListener(){
        tableModel.addTableModelListener(new TableModelListener() {
            public void tableChanged(TableModelEvent e) {
            	if(tableModel.getValueAt(table.getSelectedRow(),2) != null){

            	//updateRow = table.getSelectedRow();
            	//updateCell=Integer.parseInt((String) tableModel.getValueAt(updateRow,2));
            	//al.updateProduct(updateRow+1, updateCell);
            	System.out.println(table.getSelectedRow());
            	}
            }
          });
    }*/

    
} 