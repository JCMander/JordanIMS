import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class FrmTable extends JFrame{
    private JTable table;
    private JButton btnAdd;
    private JButton btnUpdate;
    private DefaultTableModel tableModel;
    private JTextField txtField1;
    private JTextField txtField2;
    private JTextField txtField3;
    private String[] gnomearray = {"Gnome1","Gnome2","Gnome3"};
    private int[] gnomequantity = {23,42,234};
    private int count = 0;
    private int updateTableID;
    private int updateTableQuantity;
    private String newProductName;

    private FrmTable() {
        createGUI();
    }

    private void createGUI() {
        setLayout(new BorderLayout());
        JScrollPane pane = new JScrollPane();
        table = new JTable();
        pane.setViewportView(table);
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
        		
        	}
        });
        saveemenu.add(saveReport);
        saveemenu.add(makeOrder);
        JPanel eastPanel = new JPanel();
        btnAdd = new JButton("Add");
        eastPanel.add(btnAdd);
        btnUpdate = new JButton("Update");
        eastPanel.add(btnUpdate);
        JPanel northPanel = new JPanel();
        txtField1 = new JTextField();
        txtField2 = new JTextField();
        txtField3 = new JTextField();
        JLabel lblField1 = new JLabel("Column1   ");
        JLabel lblField2 = new JLabel("Column2   ");
        JLabel lblField3 = new JLabel("Column3   ");
        for(int i=0; i<gnomearray.length; i++){
        	eastPanel.add(lblField1);
            eastPanel.add(txtField1);
        }
        northPanel.add(lblField2);
        northPanel.add(txtField2);
        northPanel.add(lblField3);
        northPanel.add(txtField3);
        txtField1.setPreferredSize(lblField1.getPreferredSize());
        txtField2.setPreferredSize(lblField2.getPreferredSize());
        txtField3.setPreferredSize(lblField3.getPreferredSize());
        //add(northPanel, BorderLayout.NORTH);
        add(eastPanel, BorderLayout.EAST);
        add(pane,BorderLayout.CENTER);
        add(menubar, BorderLayout.NORTH);
        tableModel = new DefaultTableModel(new Object[]{"Product ID","Product Name","Product Quantity"},0);
        for(int i=0; i<gnomearray.length; i++){
        	tableModel.addRow(new Object[]{i+1,gnomearray[i], gnomequantity[i]});
        }
        table.setModel(tableModel);
        btnAdd.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                count = tableModel.getRowCount()+1;
                tableModel.addRow(new Object[]{count,txtField2.getText(),txtField3.getText()});
            }
        });

    }

    public static void main(String[] args) {
        /*SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                FrmTable frm = new FrmTable();
                frm.pack();
                frm.setDefaultCloseOperation(EXIT_ON_CLOSE);
                frm.setLocationRelativeTo(null);
                frm.setVisible(true);   
            }

        });*/
    }
} 