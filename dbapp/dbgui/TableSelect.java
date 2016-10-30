package dbgui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import java.sql.ResultSet; 
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Date;

import dbaccess.TableInfo;

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
public class TableSelect extends javax.swing.JFrame {
	private TableInfo ti;
	private JLabel tnLabel;
	private JComboBox tnJCombo;
	private JTable table;
	private JLabel sortCommentLab;
	private JCheckBox orderCheckBox;
	private JComboBox valueCombo;
	private JLabel valueLab;
	private JComboBox colNameCombo;
	private JLabel colName;
	private JScrollPane jScrollPane1;
	private ResultSet rs;
	private int[] colTypes; 
	private int chosenColType;
	private TableModel emptyTableModel = new DefaultTableModel(new String[][] {{" ", " "}}, new String[] {""});
	private ComboBoxModel emptyComboModel = new DefaultComboBoxModel(new String[] { "", "" });;

	/**
	* constructor takes a reference of a db accesser object 
	*/
	public TableSelect(TableInfo ti) {
		super();
		this.ti = ti;
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
				tnLabel.setBounds(14, 14, 91, 28);
			}
			{
				ComboBoxModel tnJComboModel = new DefaultComboBoxModel(ti.listTableName());
				tnJCombo = new JComboBox();
				getContentPane().add(tnJCombo);
				tnJCombo.setModel(tnJComboModel);
				tnJCombo.setBounds(126, 14, 266, 28);
				tnJCombo.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						tnJComboActionPerformed(evt);
					}
				});
			}
			{
				TableModel tableModel = emptyTableModel; 
				table = new JTable();
				table.setModel(tableModel);
				table.setBounds(21, 56, 826, 357);
			}
			{
				jScrollPane1 = new JScrollPane(table);
				getContentPane().add(jScrollPane1);
				jScrollPane1.setBounds(7, 98, 861, 329);
			}
			{
				colName = new JLabel();
				getContentPane().add(colName);
				colName.setText("Column Name:");
				colName.setBounds(14, 56, 112, 28);
			}
			{
				ComboBoxModel colNameComboModel = emptyComboModel;
				colNameCombo = new JComboBox();
				getContentPane().add(colNameCombo);
				colNameCombo.setModel(colNameComboModel);
				colNameCombo.setBounds(126, 56, 266, 28);
				colNameCombo.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						colNameComboActionPerformed(evt);
					}
				});
			}
			{
				valueLab = new JLabel();
				getContentPane().add(valueLab);
				valueLab.setText("Choose a value:");
				valueLab.setBounds(434, 14, 147, 28);
			}
			{
				ComboBoxModel valueComboModel = emptyComboModel; 
				valueCombo = new JComboBox();
				getContentPane().add(valueCombo);
				valueCombo.setModel(valueComboModel);
				valueCombo.setBounds(434, 56, 343, 28);
				valueCombo.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						valueComboActionPerformed(evt);
					}
				});
			}
			{
				orderCheckBox = new JCheckBox("sorted ", null, true);
				getContentPane().add(orderCheckBox);
				//orderCheckBox.setText("sorted ");
				orderCheckBox.setBounds(791, 56, 70, 28);
			}
			{
				sortCommentLab = new JLabel();
				getContentPane().add(sortCommentLab);
				sortCommentLab.setText("unchecking \"sorted\" shows null values");
				sortCommentLab.setBounds(623, 14, 245, 28);
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
			TableModel tableModel = new DefaultTableModel(ti
					.resultSet2Vector(rs), ti.getTitlesAsVector(rs));
			table.setModel(tableModel);
			if (rs != null) { 
				colTypes = ti.getColumnTypes(rs);
				DefaultComboBoxModel comboModel = new DefaultComboBoxModel(ti.getTitles(rs));
				colNameCombo.setModel(comboModel); 
				valueCombo.setModel(emptyComboModel);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	
	private void colNameComboActionPerformed(ActionEvent evt) {
		//System.out.println("colNameCombo.actionPerformed, event=" + evt);
		String chosenTable = (String) tnJCombo.getSelectedItem();
		String chosenCol = (String) colNameCombo.getSelectedItem(); 
		int chosenInd = colNameCombo.getSelectedIndex();
		chosenColType = colTypes[chosenInd]; 
		String[] colValue = null; 
		try {
			if (chosenColType == Types.DATE)
				colValue = ti.getDateColumnInShort(chosenTable, chosenCol);
			else if (orderCheckBox.isSelected())
				colValue = ti.resultSet2Array(
								ti.getOrderedColumn(chosenTable, chosenCol, chosenCol));
			else 
				colValue = ti.getColumn(chosenTable, chosenCol);
			DefaultComboBoxModel colValueComboModel = new DefaultComboBoxModel(colValue);
			valueCombo.setModel(colValueComboModel); 
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	
	private void valueComboActionPerformed(ActionEvent evt) {
		//System.out.println("valueCombo.actionPerformed, event=" + evt);
		String chosenTable = (String) tnJCombo.getSelectedItem();
		String chosenCol = (String) colNameCombo.getSelectedItem();
		try {
			String  val = (String)valueCombo.getSelectedItem();
			// System.out.println("sorted checkbox:" + orderCheckBox.isSelected());
			boolean sorted = orderCheckBox.isSelected();
			rs = ti.getSelectedResultSet(chosenTable, chosenCol, 
											chosenColType, val); 
			TableModel tableModel = new DefaultTableModel(ti.resultSet2Vector(rs), ti.getTitlesAsVector(rs));
			table.setModel(tableModel); 
		} catch (Exception e) {
			e.printStackTrace();
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
		TableInfo ti = new TableInfo(args[0], args[1]); 
//		TableInfo ti = new TableInfo("scott", "tiger");
		TableSelect inst = new TableSelect(ti);
		inst.setVisible(true);
	}
}
