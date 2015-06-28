import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

public class FrmTable extends JFrame{
    private JTable table;
    private DefaultTableModel tableModel;
    private String[] gnomearray = {"Gnome1","Gnome2","Gnome3"};
    private int[] gnomequantity = {23,42,234};
    private int count = 0;
    private int updateTableID;
    private int updateTableQuantity;
    private String newProductName;
    private static PurchaseOrder po;
    private JLabel simTime;
    private static String stockListMessage ="";

    private FrmTable() {
        createGUI();
    }

    private void createGUI() {
        setLayout(new BorderLayout());
        JScrollPane pane = new JScrollPane();
        table = new JTable();
        pane.setViewportView(table);
        simTime = new JLabel("23:46:00 28/06/2015");
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
        updateQuantity.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
            	updateTableID = Integer.parseInt((String)JOptionPane.showInputDialog("Please enter the id of the product you wish to change"));
            	updateTableQuantity = Integer.parseInt((String)JOptionPane.showInputDialog("Product ID: " + updateTableID + "   Product Name: " + gnomearray[updateTableID-1] + "   Product Quantity: " + gnomequantity[updateTableID-1] + "\n\nPlease enter the new quantity"));
            	gnomequantity[updateTableID-1]= updateTableQuantity;
                for(int i = tableModel.getRowCount() - 1; i > -1; i--){
                	tableModel.removeRow(i);
                }
                for(int i=0; i<gnomearray.length; i++){
                	tableModel.addRow(new Object[]{i+1,gnomearray[i], gnomequantity[i]});
                }
                count=0;
                System.out.println("Hello");
            }
        });
        addProduct.addActionListener(new ActionListener(){
        	@Override
        	public void actionPerformed(ActionEvent e){
        		newProductName = (String)JOptionPane.showInputDialog("Please enter the name of the new product");
        		count = tableModel.getRowCount()+1;
                tableModel.addRow(new Object[]{count,newProductName,0});
        	}
        });
        filemenu.add(updateQuantity);
        filemenu.add(addProduct);
        JMenuItem simulateDays = new JMenuItem("Default Simulation (5 days)");
        JMenuItem simulateCustomDays = new JMenuItem("Custom Simulation");
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
        anothermenu.add(simulateDays);
        anothermenu.add(simulateCustomDays);
        JMenuItem saveReport = new JMenuItem("Generate Stock Report");
        JMenuItem makeOrder = new JMenuItem("Generate Purchase Order");
        saveReport.addActionListener(new ActionListener(){
        	@Override
        	public void actionPerformed(ActionEvent e){
        		
        	}
        });
        makeOrder.addActionListener(new ActionListener(){
        	@Override
        	public void actionPerformed(ActionEvent e){
        		po.pack();
                po.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                po.setLocationRelativeTo(null);
                po.setVisible(true); 
        	}
        });
        saveemenu.add(saveReport);
        saveemenu.add(makeOrder);
        southPanel.add(simTime);
        add(southPanel, BorderLayout.SOUTH);
        add(pane,BorderLayout.CENTER);
        add(menubar, BorderLayout.NORTH);
        tableModel = new DefaultTableModel(new Object[]{"Product ID","Product Name","Product Quantity"},0) {
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
            	  return !(columnIndex < 2);
            	}
        };
        for(int i=0; i<gnomearray.length; i++){
        	tableModel.addRow(new Object[]{i+1,gnomearray[i], gnomequantity[i]});
        }
        table.setModel(tableModel);
        for(int i =0; i<gnomearray.length;i++){
        	if(gnomequantity[i]<50){
        	stockListMessage = stockListMessage + "Name: " + gnomearray[i] + "            Quantity: " + gnomequantity[i] + "\n";
        	}
        }
        
        
        
        tableModel.addTableModelListener(new TableModelListener() {
            public void tableChanged(TableModelEvent e) {
               System.out.println("That's amazing that");
            }
          });
    }

    private static void stockListMsg(){
        JOptionPane.showMessageDialog(null, "The following products are low in quantity\n\n" + stockListMessage);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                FrmTable frm = new FrmTable();
                frm.pack();
                frm.setDefaultCloseOperation(EXIT_ON_CLOSE);
                frm.setLocationRelativeTo(null);
                frm.setVisible(true);
                
                po = new PurchaseOrder();
                stockListMsg();
               
            }

        });
    }
} 