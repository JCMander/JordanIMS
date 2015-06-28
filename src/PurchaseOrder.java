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

public class PurchaseOrder extends JFrame {
	private JTable table;
    private JButton btnSend;
    private DefaultTableModel tableModel;
    private String[] gnomearray = {"Gnome1","Gnome2","Gnome3"};
    private int[] gnomequantity = {23,42,234};
    private String[] supplierarray = {"Gringotts", "Hogwarts" ,"Shreaking Shack"};

    private PurchaseOrder() {
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
        tableModel = new DefaultTableModel(new Object[]{"Product Name","Order Quantity", "Supplier", "Price"},0);
        for(int i=0; i<gnomearray.length; i++){
        	if(gnomequantity[i]<50){
        	tableModel.addRow(new Object[]{gnomearray[i], (250 - gnomequantity[i]), supplierarray[i], "$" + (10*(250 - gnomequantity[i])) + ".00"});
        	}
        }
        table.setModel(tableModel);
    
    btnSend.addActionListener(new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Du Herro");
        }
    });
    
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                PurchaseOrder po = new PurchaseOrder();
                po.pack();
                po.setDefaultCloseOperation(EXIT_ON_CLOSE);
                po.setLocationRelativeTo(null);
                po.setVisible(true);   
            }

        });
    }
} 