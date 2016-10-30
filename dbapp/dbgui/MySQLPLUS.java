package dbgui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

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
public class MySQLPLUS extends javax.swing.JFrame {
	private JTextArea sQLstmt;
	private JTextArea message;
	private JScrollPane messagePane;
	private JScrollPane resultPane;
	private JScrollPane sqlstmtPane;
	private JLabel sqlstmtLab;
	private JButton execute;
	private JTable resultTable;

	private TableInfo ti;			// the persistent manager 

	/**
	* Auto-generated main method to display this JFrame
	*/
	public static void main(String[] args) throws Exception {
		if (args.length < 2) {
			System.out.println("usage: java MySQLPLUS db-username db-password"); 
			System.exit(1);
		}
		TableInfo ti = new TableInfo(args[0], args[1]); 
		MySQLPLUS ms = new MySQLPLUS(ti);
		ms.setVisible(true);
	}
	
	public MySQLPLUS(TableInfo ti) {
		super();
		this.ti = ti;
		initGUI();
	}
	
	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			getContentPane().setLayout(null);
			{
				message = new JTextArea();
				getContentPane().add(message);
				message.setText("database message");
				message.setBounds(42, 154, 623, 84);
			}
			{
				messagePane = new JScrollPane();
				getContentPane().add(messagePane);
				messagePane.setBounds(35, 147, 658, 98);
				messagePane.setViewportView(message);
				message.setPreferredSize(new java.awt.Dimension(655, 95));
			}
			{
				execute = new JButton();
				getContentPane().add(execute);
				execute.setText("executeBut");
				execute.setBounds(35, 595, 161, 35);
				execute.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						executeActionPerformed(evt);
					}
				});
			}
			{
				resultPane = new JScrollPane();
				getContentPane().add(resultPane);
				resultPane.setBounds(35, 252, 658, 329);
				{
					TableModel resultTableModel = new DefaultTableModel(
						new String[][] { { "", "" }, { "", "" } },
						new String[] { "", "" });
					resultTable = new JTable();
					resultPane.setViewportView(resultTable);
					resultTable.setModel(resultTableModel);
					resultTable.setPreferredSize(new java.awt.Dimension(658, 308));
				}
			}
			{
				sqlstmtLab = new JLabel();
				getContentPane().add(sqlstmtLab);
				sqlstmtLab.setText("Your SQL statement");
				sqlstmtLab.setBounds(35, 7, 154, 28);
			}
			{
				sqlstmtPane = new JScrollPane();
				getContentPane().add(sqlstmtPane);
				sqlstmtPane.setBounds(35, 35, 658, 105);
				{
					sQLstmt = new JTextArea();
					sqlstmtPane.setViewportView(sQLstmt);
					sQLstmt.setText("");
					sQLstmt.setBounds(35, 112, 651, 28);
					sQLstmt.setPreferredSize(new java.awt.Dimension(654, 102));
				}
			}
			pack();
			this.setSize(722, 671);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void executeActionPerformed(ActionEvent evt) {
		//System.out.println("execute.actionPerformed, event=" + evt);
		String stmt = sQLstmt.getText(); 
		String command = stmt.trim().split(" ")[0];
		int numRow = 0; 
		try {
			if (command.toUpperCase().equals("SELECT")) {
				java.sql.ResultSet rs = ti.runSQLQuery(sQLstmt.getText());
				TableModel tableModel = new DefaultTableModel(ti.resultSet2Vector(rs), ti.getTitlesAsVector(rs));
				resultTable.setModel(tableModel);
			} else {
				numRow = ti.runUpdate(sQLstmt.getText());
				if (numRow > 0) {
					message.setText(numRow + " row(s) " + command + "ed.");
				} else
					message.setText("No row was " + command + "ed.");
			}
		} catch (SQLException sqle) {
			message.setText(sqle.toString());
		}
	}
}
