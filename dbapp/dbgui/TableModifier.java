package dbgui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import java.sql.ResultSet; 
import java.sql.SQLException;
import java.util.Vector;

import dbaccess.TableInfo;
import dbaccess.DBConnection;
import dbaccess.TableUpdate;

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
public class TableModifier extends javax.swing.JFrame {
	private JLabel tnLabel;
	private JComboBox tnJCombo;
	private JTable table;
	private JButton jButton2; 
	private JButton jButton1;
	private JScrollPane jScrollPane1;
	
	private Vector tabContent; 
	private ResultSet rs;
	private Vector newRow = new Vector();

	private TableInfo ti;
	private TableUpdate tu; 
	/**
	* constructor takes a reference of a db accesser object 
	*/
	public TableModifier(TableUpdate tu) {
		super();
		this.tu = tu; 
		this.ti = tu.getTableInfo(); 
		initGUI();
	}
	
	/**
	* drawing the GUI
	*/
	private void initGUI() {
		try {
			{
				tnLabel = new JLabel();
				getContentPane().add(tnLabel);
				tnLabel.setText("Table Name: ");
				tnLabel.setBounds(21, 14, 91, 28);
			}
			{
				ComboBoxModel tnJComboModel = new DefaultComboBoxModel(ti.listTableName());
				tnJCombo = new JComboBox();
				getContentPane().add(tnJCombo);
				tnJCombo.setModel(tnJComboModel);
				tnJCombo.setBounds(91, 14, 455, 28);
				tnJCombo.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						tnJComboActionPerformed(evt);
					}
				});
			}
			{
				table = new JTable(new String[][] {{" ", " "}}, 
						new String[] {"Column 1", "Column 2" });
				table.setBounds(21, 56, 826, 357);
			}
			{
				jScrollPane1 = new JScrollPane(table);
				getContentPane().add(jScrollPane1);
				jScrollPane1.setBounds(7, 49, 861, 378);
			}
			{
				jButton1 = new JButton();
				getContentPane().add(jButton1);
				jButton1.setText("Add Row");
				jButton1.setBounds(567, 14, 98, 28);
				jButton1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						jButton1ActionPerformed(evt);
					}
				});
			}
			{
				jButton2 = new JButton();
				getContentPane().add(jButton2);
				jButton2.setText("Insert");
				jButton2.setBounds(686, 14, 112, 28);
				jButton2.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						jButton2ActionPerformed(evt);
					}
				});
			}
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			getContentPane().setLayout(null);
			pack();
			this.setSize(883, 485);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	* Combobox's action
	*/
	private void tnJComboActionPerformed(ActionEvent evt) {
		//System.out.println("tnJCombo.actionPerformed, event=" + evt);
		//TODO add your code for tnJCombo.actionPerformed
		String chosenTable = (String) tnJCombo.getSelectedItem();
		try {
			rs = ti.getTable(chosenTable);
			tabContent = ti.resultSet2Vector(rs);
			Vector tabTitles = ti.getTitlesAsVector(rs);
			TableModel tableModel = new DefaultTableModel(tabContent, tabTitles);
			table.setModel(tableModel); 
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	
	private void jButton1ActionPerformed(ActionEvent evt) {
		//System.out.println("jButton1.actionPerformed, event=" + evt);
		// TODO add your code for jButton1.actionPerformed
		try {
			Vector titles = ti.getTitlesAsVector(rs);
			newRow = new Vector(table.getColumnCount());
			tabContent.add(newRow);
			TableModel tableModel = new DefaultTableModel(tabContent, titles);
			table.setModel(tableModel);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	
	private void jButton2ActionPerformed(ActionEvent evt) {
		// System.out.println("jButton2.actionPerformed, event=" + evt);
		// TODO add your code for jButton2.actionPerformed
		// System.out.println(newRow.elementAt(0));
		int numRow = 0;
		try {
			String tableName = (String)tnJCombo.getSelectedItem(); 
			numRow = tu.insertRow(newRow, tableName, rs);
			if (numRow == 0) 
				System.out.println(this.getName() + ":jButton2ActionPerformed: no row is insterted.");
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}

	/**
	* activater
	*/
	public static void main(String[] args) throws Exception { 
		if (args.length < 2) {
			System.out.println("usage: java TableInfo db-username db-password"); 
			System.exit(1);
		}
		DBConnection tc = new DBConnection("localhost", "1521", "nbdb");
		java.sql.Connection conn = tc.getDBConnection(args[0], args[1]);
		TableUpdate tu = new TableUpdate(conn);
		TableModifier inst = new TableModifier(tu);
		inst.setVisible(true);
	}
}
