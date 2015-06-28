import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class PurchaseOrder extends JFrame {
	private JTable table;
    private JButton btnSend;
    private DefaultTableModel tableModel;
    private String[] gnomearray = {"Gnome1","Gnome2","Gnome3"};
    private int[] gnomequantity = {23,42,234};
    private String[] supplierarray = {"Gringotts", "Hogwarts" ,"Shreaking Shack"};

    public PurchaseOrder() {
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
            dispose();
            JOptionPane.showMessageDialog(null, "Your purchase order has been sent");
        }
    });
    
    }
} 