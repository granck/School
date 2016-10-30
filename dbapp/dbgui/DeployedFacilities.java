package dbgui;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class DeployedFacilities extends javax.swing.JFrame {
	private JLabel jLabel1;
	private JLabel jLabel2;
	private JTable jTable1;
	private JScrollPane jScrollPane1;
	private JComboBox jComboBox2;
	private JComboBox jComboBox1;
	private JButton jButton1;
	private JPasswordField jPasswordField1;
	private JTextField jTextField1;

	/**
	* Auto-generated main method to display this JFrame
	*/
	public static void main(String[] args) {
		DeployedFacilities inst = new DeployedFacilities();
		inst.setVisible(true);
	}
	
	public DeployedFacilities() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			getContentPane().setLayout(null);
			{
				jLabel1 = new JLabel();
				getContentPane().add(jLabel1);
				jLabel1.setText("username");
				jLabel1.setBounds(7, 7, 63, 28);
			}
			{
				jLabel2 = new JLabel();
				getContentPane().add(jLabel2);
				jLabel2.setText("password");
				jLabel2.setBounds(7, 56, 63, 28);
			}
			{
				jTextField1 = new JTextField();
				getContentPane().add(jTextField1);
				jTextField1.setBounds(70, 7, 154, 28);
			}
			{
				jPasswordField1 = new JPasswordField();
				getContentPane().add(jPasswordField1);
				jPasswordField1.setBounds(70, 56, 154, 28);
			}
			{
				jButton1 = new JButton();
				getContentPane().add(jButton1);
				jButton1.setText("Make Connection");
				jButton1.setBounds(294, 28, 196, 28);
			}
			{
				ComboBoxModel jComboBox1Model = new DefaultComboBoxModel(
					new String[] { "Item One", "Item Two" });
				jComboBox1 = new JComboBox();
				getContentPane().add(jComboBox1);
				jComboBox1.setModel(jComboBox1Model);
				jComboBox1.setBounds(70, 119, 154, 28);
			}
			{
				ComboBoxModel jComboBox2Model = new DefaultComboBoxModel(
					new String[] { "Item One", "Item Two" });
				jComboBox2 = new JComboBox();
				getContentPane().add(jComboBox2);
				jComboBox2.setModel(jComboBox2Model);
				jComboBox2.setBounds(294, 119, 154, 28);
			}
			{
				jScrollPane1 = new JScrollPane();
				getContentPane().add(jScrollPane1);
				jScrollPane1.setBounds(70, 175, 574, 273);
				{
					TableModel jTable1Model = new DefaultTableModel(
						new String[][] { { "One", "Two" }, { "Three", "Four" } },
						new String[] { "Column 1", "Column 2" });
					jTable1 = new JTable();
					jScrollPane1.setViewportView(jTable1);
					jTable1.setModel(jTable1Model);
					jTable1.setPreferredSize(new java.awt.Dimension(574, 252));
				}
			}
			pack();
			this.setSize(712, 535);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
