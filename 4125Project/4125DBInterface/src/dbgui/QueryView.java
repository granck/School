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
import java.sql.Statement;
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
public class QueryView extends javax.swing.JFrame {
	private JLabel qnLabel;
	private JLabel valueListLabel;
	private JLabel secondaryListLabel;
	private JComboBox queryCombo;
	private JTable table;
	private JButton jButton2; 
	private JButton jButton1;
	private JScrollPane jScrollPane1;
	
	private Vector tabContent; 
	private ResultSet rs;
	private Vector newRow = new Vector();

	private TableInfo ti;
	private TableUpdate tu;
	private java.sql.Connection conn = null;
	private JComboBox valueList = new JComboBox();
	private JComboBox secondaryList = new JComboBox();
	private String per_id = null;
	private String comp_id = null;
	private String pos_code = null;
	private String job_code = null;
	private String queryValue = null;
	private int queryNum = 0;
	private String missingNum = "0";
	
	private String[] queryList = {"Query 1", "Query 2", "Query 3", "Query 4", "Query 5", 
				"Query 6", "Query 7", "Query 8", "Query 9 - a", "Query 9 - b", "Query 10", 
				"Query 11", "Query 12 - a", "Query 12 - b", "Query 13", "Query 15", 
				"Query 16","Query 17", "Query 18", "Query 19", "Query 20", "Query 21", 
				"Query 22", "Query 23", "Query 24", "Query 25 - a", "Query 25 - b", "Query 30"
	};
	/**
	* constructor takes a reference of a db accesser object 
	*/
	public QueryView(TableUpdate tu, java.sql.Connection conn) {
		super();
		this.tu = tu; 
		this.ti = tu.getTableInfo();
		this.conn = conn;
		initGUI();
	}
	
	/**
	* drawing the GUI
	*/
	private void initGUI() {
		try {
			{
				qnLabel = new JLabel();
				getContentPane().add(qnLabel);
				qnLabel.setText("Query");
				qnLabel.setBounds(130, -10, 91, 28);
			}
			{
				valueListLabel = new JLabel();
				getContentPane().add(valueListLabel);
				valueListLabel.setText("Primary");
				valueListLabel.setBounds(50, 20, 125, 28);
				valueListLabel.setVisible(false);
			}	
			{
				secondaryListLabel = new JLabel();
				getContentPane().add(secondaryListLabel);
				secondaryListLabel.setText("Secondary");
				secondaryListLabel.setBounds(460, 20, 125, 28);
				secondaryListLabel.setVisible(false);
			}
			{
				//ComboBoxModel tnJComboModel = new DefaultComboBoxModel(ti.listTableName());
				ComboBoxModel queryComboModel = new DefaultComboBoxModel(queryList);
				queryCombo = new JComboBox();
				getContentPane().add(queryCombo);
				queryCombo.setModel(queryComboModel);
				queryCombo.setBounds(200, 0, 455, 20);
				queryCombo.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						queryComboActionPerformed(evt);
					}
				});
			}
			{
				valueList = new JComboBox();
				getContentPane().add(valueList);
				valueList.setBounds(91, 26, 300, 20);
				valueList.setVisible(false);
				valueList.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent evt){
						valueListComboActionPerformed(evt);
					}
				});
			}
			{
				secondaryList = new JComboBox();
				getContentPane().add(secondaryList);
				secondaryList.setBounds(536, 26, 300, 20);
				secondaryList.setVisible(false);
				secondaryList.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent evt){
						secondaryListComboActionPerformed(evt);
					}
				});
			}
			{
				table = new JTable(new String[][] {{" ", " "}}, 
						new String[] {"Column 1", "Column 2" });
				table.setBounds(45, 200, 826, 357);
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
				jButton1.setVisible(false);
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
				jButton2.setVisible(false);
				jButton2.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						jButton2ActionPerformed(evt);
					}
				});
			}
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			getContentPane().setLayout(null);
			pack();
			this.setSize(900, 550);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	* Combobox's action
	*/
	private void queryComboActionPerformed(ActionEvent evt) {
		//System.out.println("tnJCombo.actionPerformed, event=" + evt);
		//TODO add your code for tnJCombo.actionPerformed

		String chosenTable = (String) queryCombo.getSelectedItem();
		valueList.setVisible(false);
		valueListLabel.setVisible(false);
		secondaryList.setVisible(false);
		secondaryListLabel.setVisible(false);
		try {
			Statement stmt = conn.createStatement();
			String query = queryParser(chosenTable);
			rs = stmt.executeQuery(query);
			//rs = ti.getTable(chosenTable);
			tabContent = ti.resultSet2Vector(rs);
			Vector tabTitles = ti.getTitlesAsVector(rs);
			TableModel tableModel = new DefaultTableModel(tabContent, tabTitles);
			table.setModel(tableModel);
			
			if(chosenTable.equals("Query 25 - b")){
				stmt.executeUpdate("DELETE FROM works WHERE per_id = 9021090210");
				stmt.executeUpdate("DELETE FROM works WHERE per_id = 779966655 AND job_code = 9924573241");
			}
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
			String tableName = (String)queryCombo.getSelectedItem(); 
			numRow = tu.insertRow(newRow, tableName, rs);
			if (numRow == 0) 
				System.out.println(this.getName() + ":jButton2ActionPerformed: no row is insterted.");
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	
	private String queryParser(String chosenQuery){
		//valueList = new JComboBox();
		//getContentPane().add(valueList);
		//valueList.setBounds(91, 31, 455, 28);
		if(chosenQuery.equals("Query 1")){
			queryNum = 1;			
			String[] colValue = null;
			try {
				colValue = ti.getColumn("Company", "comp_id");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			ComboBoxModel companyComboModel = new DefaultComboBoxModel(colValue);
			valueList.setModel(companyComboModel);
			valueList.setVisible(true);
			valueListLabel.setText("company");
			valueListLabel.setVisible(true);
			
			queryOne();
		}
		else if(chosenQuery.equals("Query 2")){
			queryNum = 2;
			String[] colValue = null;
			try {
				colValue = ti.getColumn("Company", "comp_id");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			ComboBoxModel companyComboModel = new DefaultComboBoxModel(colValue);
			valueList.setModel(companyComboModel);
			valueList.setVisible(true);
			valueListLabel.setText("company");
			valueListLabel.setVisible(true);
			
			queryTwo();
			
		}
		else if(chosenQuery.equals("Query 3")){
			queryNum = 3;
			
			queryThree();
			
		}
		else if(chosenQuery.equals("Query 4")){
			queryNum = 4;
			String[] colValue = null;
			try {
				colValue = ti.getColumn("Person", "per_id");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			ComboBoxModel personComboModel = new DefaultComboBoxModel(colValue);
			valueList.setModel(personComboModel);
			valueList.setVisible(true);
			valueListLabel.setText("person");
			valueListLabel.setVisible(true);
			
			queryFour();
			
			
		}
		else if(chosenQuery.equals("Query 5")){
			queryNum = 5;			
			String[] colValue = null;
			try {
				colValue = ti.getColumn("Person", "per_id");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			ComboBoxModel companyComboModel = new DefaultComboBoxModel(colValue);
			valueList.setModel(companyComboModel);
			valueList.setVisible(true);
			valueListLabel.setText("person");
			valueListLabel.setVisible(true);
			
			queryFive();
			
		}
		else if(chosenQuery.equals("Query 6")){
			queryNum = 6;			
			String[] colValue = null;
			try {
				colValue = ti.getColumn("Person", "per_id");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			ComboBoxModel companyComboModel = new DefaultComboBoxModel(colValue);
			valueList.setModel(companyComboModel);
			valueList.setVisible(true);
			valueListLabel.setText("person");
			valueListLabel.setVisible(true);
			
			querySix();
		}
		else if(chosenQuery.equals("Query 7")){
			queryNum = 7;			
			String[] colValue = null;
			try {
				colValue = ti.getColumn("job_profile", "pos_code");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			ComboBoxModel companyComboModel = new DefaultComboBoxModel(colValue);
			valueList.setModel(companyComboModel);
			valueList.setVisible(true);
			valueListLabel.setText("profile");
			valueListLabel.setVisible(true);
			
			querySeven();
			
		}
		else if(chosenQuery.equals("Query 8")){
			queryNum = 8;
			secondaryListLabel.setVisible(true);
			String[] colValue = null;
			String[] secondaryValue = null;
			try {
				colValue = ti.getColumn("job", "job_code");
				secondaryValue = ti.getColumn("person", "per_id");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			ComboBoxModel companyComboModel = new DefaultComboBoxModel(colValue);
			ComboBoxModel secondaryComboModel = new DefaultComboBoxModel(secondaryValue);
			valueList.setModel(companyComboModel);
			valueList.setVisible(true);
			valueListLabel.setText("job");
			valueListLabel.setVisible(true);
			secondaryList.setModel(secondaryComboModel);
			secondaryList.setVisible(true);
			secondaryListLabel.setText("person");
			secondaryListLabel.setVisible(true);
			
			queryEight();

		}
		else if(chosenQuery.equals("Query 9 - a") || chosenQuery.equals("Query 9 - b")){
			queryNum = 9;
			if (chosenQuery.equals("Query 9 - a")) {
				queryNine("temp_skill_set");
			}
			else if (chosenQuery.equals("Query 9 - b")) {
				queryNine("TEMP_SKILL_SET2");
			}
			
		}
		else if(chosenQuery.equals("Query 10")){
			queryNum = 10;
			String[] colValue = null;
			String[] secondaryValue = null;
			try {
				colValue = ti.getColumn("job", "job_code");
				secondaryValue = ti.getColumn("person", "per_id");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			ComboBoxModel companyComboModel = new DefaultComboBoxModel(colValue);
			ComboBoxModel secondaryComboModel = new DefaultComboBoxModel(secondaryValue);
			valueList.setModel(companyComboModel);
			valueList.setVisible(true);
			valueListLabel.setText("job");
			valueListLabel.setVisible(true);;
			secondaryList.setModel(secondaryComboModel);
			secondaryList.setVisible(true);
			secondaryListLabel.setText("person");
			secondaryListLabel.setVisible(true);
			
			
			queryTen();
		}
		else if(chosenQuery.equals("Query 11")){
			queryNum = 11;
			secondaryListLabel.setVisible(true);
			String[] colValue = null;
			String[] secondaryValue = null;
			try {
				colValue = ti.getColumn("job", "job_code");
				secondaryValue = ti.getColumn("person", "per_id");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			ComboBoxModel companyComboModel = new DefaultComboBoxModel(colValue);
			ComboBoxModel secondaryComboModel = new DefaultComboBoxModel(secondaryValue);
			valueList.setModel(companyComboModel);
			valueList.setVisible(true);
			valueListLabel.setText("job");
			valueListLabel.setVisible(true);
			secondaryList.setModel(secondaryComboModel);
			secondaryList.setVisible(true);
			secondaryListLabel.setText("person");
			secondaryListLabel.setVisible(true);
			
			queryEleven();
		}
		else if(chosenQuery.equals("Query 12 - a")|| chosenQuery.equals("Query 12 - b")){
			queryNum = 12;
			if (chosenQuery.equals("Query 12 - a")) {
				queryTwelve("temp_skill_set3");
			} else if (chosenQuery.equals("Query 12 - b")) {
				queryTwelve("temp_skill_set4");
			}
		}
		else if(chosenQuery.equals("Query 13")){
			queryNum = 13;
			secondaryListLabel.setVisible(true);
			String[] colValue = null;
			String[] secondaryValue = null;
			try {
				colValue = ti.getColumn("job", "job_code");
				secondaryValue = ti.getColumn("person", "per_id");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			ComboBoxModel companyComboModel = new DefaultComboBoxModel(colValue);
			ComboBoxModel secondaryComboModel = new DefaultComboBoxModel(secondaryValue);
			valueList.setModel(companyComboModel);
			valueList.setVisible(true);
			valueListLabel.setText("job");
			valueListLabel.setVisible(true);
			secondaryList.setModel(secondaryComboModel);
			secondaryList.setVisible(true);
			secondaryListLabel.setText("person");
			secondaryListLabel.setVisible(true);
			
			queryThirteen();
		}
		else if(chosenQuery.equals("Query 15")){
			queryNum = 15;
			String[] colValue = null;
			try {
				colValue = ti.getColumn("person", "per_id");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			ComboBoxModel companyComboModel = new DefaultComboBoxModel(colValue);
			valueList.setModel(companyComboModel);
			valueList.setVisible(true);
			valueListLabel.setText("person");
			valueListLabel.setVisible(true);
			
			queryFifteen();
		}
		else if(chosenQuery.equals("Query 16")){
			queryNum = 16;
			String[] colValue = null;
			try {
				colValue = ti.getColumn("person", "per_id");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			ComboBoxModel companyComboModel = new DefaultComboBoxModel(colValue);
			valueList.setModel(companyComboModel);
			valueList.setVisible(true);
			valueListLabel.setText("person");
			valueListLabel.setVisible(true);
			
			querySixteen();
		}
		else if(chosenQuery.equals("Query 17")){
			queryNum = 17;
			String[] colValue = null;
			try{
				colValue = ti.getColumn("Job_profile", "pos_code");
			}
			catch(SQLException e){
				e.printStackTrace();
			}
			ComboBoxModel companyComboModel = new DefaultComboBoxModel(colValue);
			valueList.setModel(companyComboModel);
			valueList.setVisible(true);
			valueListLabel.setText("profile");
			valueListLabel.setVisible(true);
			
			querySeventeen();
		}
		else if(chosenQuery.equals("Query 18")){
			queryNum = 18;
			String[] colValue = null;
			missingNum = "1";
			try{
				colValue = ti.getColumn("Job_profile", "pos_code");
			}
			catch(SQLException e){
				e.printStackTrace();
			}
			ComboBoxModel companyComboModel = new DefaultComboBoxModel(colValue);
			valueList.setModel(companyComboModel);
			valueList.setVisible(true);
			valueListLabel.setText("profile");
			valueListLabel.setVisible(true);
			
			queryEighteen();
		}
		else if(chosenQuery.equals("Query 19")){
			queryNum = 19;
			String[] colValue = null;
			missingNum = "1";
			try{
				colValue = ti.getColumn("Job_profile", "pos_code");
			}
			catch(SQLException e){
				e.printStackTrace();
			}
			ComboBoxModel companyComboModel = new DefaultComboBoxModel(colValue);
			valueList.setModel(companyComboModel);
			valueList.setVisible(true);
			valueListLabel.setText("profile");
			valueListLabel.setVisible(true);
			
			queryNineteen();
		}
		else if(chosenQuery.equals("Query 20")){
			queryNum = 20;
			String[] colValue = null;
			try{
				colValue = ti.getColumn("Job_profile", "pos_code");
			}
			catch(SQLException e){
				e.printStackTrace();
			}
			ComboBoxModel companyComboModel = new DefaultComboBoxModel(colValue);
			valueList.setModel(companyComboModel);
			valueList.setVisible(true);
			valueListLabel.setText("profile");
			valueListLabel.setVisible(true);
			
			queryTwenty();
		}
		else if(chosenQuery.equals("Query 21")){
			queryNum = 21;
			String[] colValue = null;
			String[] secondaryValue = {"1", "2", "3", "4", "5", "6", "7" , "8", "9", "10"};
			try{
				colValue = ti.getColumn("Job_profile", "pos_code");
				
			}
			catch(SQLException e){
				e.printStackTrace();
			}
			ComboBoxModel companyComboModel = new DefaultComboBoxModel(colValue);
			ComboBoxModel secondaryComboModel = new DefaultComboBoxModel(secondaryValue);
			valueList.setModel(companyComboModel);
			valueList.setVisible(true);
			valueListLabel.setText("profile");
			valueListLabel.setVisible(true);
			secondaryList.setModel(secondaryComboModel);
			secondaryList.setVisible(true);
			secondaryListLabel.setText("number");
			secondaryListLabel.setVisible(true);
			
			queryEighteen();
		}
		else if(chosenQuery.equals("Query 22")){
			queryNum = 22;
			String[] colValue = null;
			String[] secondaryValue = {"1", "2", "3", "4", "5", "6", "7" , "8", "9", "10"};
			try{
				colValue = ti.getColumn("Job_profile", "pos_code");
				
			}
			catch(SQLException e){
				e.printStackTrace();
			}
			ComboBoxModel companyComboModel = new DefaultComboBoxModel(colValue);
			ComboBoxModel secondaryComboModel = new DefaultComboBoxModel(secondaryValue);
			valueList.setModel(companyComboModel);
			valueList.setVisible(true);
			valueListLabel.setText("profile");
			valueListLabel.setVisible(true);
			secondaryList.setModel(secondaryComboModel);
			secondaryList.setVisible(true);
			secondaryListLabel.setText("number");
			secondaryListLabel.setVisible(true);
			
			queryNineteen();
		}
		else if(chosenQuery.equals("Query 23")){
			queryNum = 23;			
			String[] colValue = null;
			try {
				colValue = ti.getColumn("job_profile", "pos_code");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			ComboBoxModel companyComboModel = new DefaultComboBoxModel(colValue);
			valueList.setModel(companyComboModel);
			valueList.setVisible(true);
			valueListLabel.setText("profile");
			valueListLabel.setVisible(true);
			
			queryTwentyThree();
		}
		else if(chosenQuery.equals("Query 24")){
			queryNum = 24;			
			String[] colValue = null;
			try {
				colValue = ti.getColumn("job_profile", "pos_code");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			ComboBoxModel companyComboModel = new DefaultComboBoxModel(colValue);
			valueList.setModel(companyComboModel);
			valueList.setVisible(true);
			valueListLabel.setText("profile");
			valueListLabel.setVisible(true);
			
			queryTwentyThree();
		}
		else if(chosenQuery.equals("Query 25 - a") || chosenQuery.equals("Query 25 - b")){
			queryNum = 25;
			
			if(chosenQuery.equals("Query 25 - b")){
				String update1 = "INSERT INTO Works VALUES(7497236861, " +
								"9021090210, " +
								"9924573241, " +
								"003, " +
								"85000.00, " +
								"001, " +
								"001)";
				String update2 = "INSERT INTO Works VALUES(7497236865, " +
								"779966655, " +
								"9924573241, " +
								"003, " +
								"80000.00, " +
								"001, " +
								"001)";		
				try{
					Statement stmt = conn.createStatement();
					stmt.executeUpdate(update1);
					stmt.executeUpdate(update2);
				} catch(SQLException sqle){
					sqle.printStackTrace();
				}

			}
			
			queryTwentyFive();
		}
		else if(chosenQuery.equals("Query 30")){
			queryNum = 30;
			String[] colValue = null;
			String[] secondaryValue = null;
			try{
				colValue = ti.getColumn("Job_profile", "pos_code");
				secondaryValue = ti.getColumn("Person", "per_id");
				
			}
			catch(SQLException e){
				e.printStackTrace();
			}
			ComboBoxModel companyComboModel = new DefaultComboBoxModel(colValue);
			ComboBoxModel secondaryComboModel = new DefaultComboBoxModel(secondaryValue);
			valueList.setModel(companyComboModel);
			valueList.setVisible(true);
			valueListLabel.setText("profile");
			valueListLabel.setVisible(true);
			secondaryList.setModel(secondaryComboModel);
			secondaryList.setVisible(true);
			secondaryListLabel.setText("person");
			secondaryListLabel.setVisible(true);
			
			queryThirty();
		}
		return queryValue;
	}
	private void valueListComboActionPerformed(ActionEvent evt){
		if(queryNum == 1){
			comp_id = (String) valueList.getSelectedItem();
			
			queryOne();
		}
		else if(queryNum == 2){
			comp_id = (String) valueList.getSelectedItem();
			
			queryTwo();
		}
		else if(queryNum == 4){
			per_id = (String) valueList.getSelectedItem();
			
			queryFour();
		}
		else if(queryNum == 5){
			per_id = (String) valueList.getSelectedItem();
			
			queryFive();
		}
		else if(queryNum == 6){
			per_id = (String) valueList.getSelectedItem();
			
			querySix();
		}
		else if(queryNum == 7){
			pos_code = (String) valueList.getSelectedItem();
			
			querySeven();
		}
		else if(queryNum == 8){
			job_code = (String) valueList.getSelectedItem();
			per_id = (String) secondaryList.getSelectedItem();
			
			queryEight();
		}
		else if(queryNum == 10){
			job_code = (String) valueList.getSelectedItem();
			per_id = (String) secondaryList.getSelectedItem();
			
			queryTen();
		}
		else if(queryNum == 11){
			job_code = (String) valueList.getSelectedItem();
			per_id = (String) secondaryList.getSelectedItem();
			
			queryEleven();
		}
		else if(queryNum == 12){
			
		}
		else if(queryNum == 13){
			job_code = (String) valueList.getSelectedItem();
			per_id = (String) secondaryList.getSelectedItem();
			queryThirteen();
		}
		else if(queryNum == 14){
			
		}
		else if(queryNum == 15){
			per_id = (String) valueList.getSelectedItem();
			queryFifteen();
		}
		else if(queryNum == 16){
			per_id = (String) valueList.getSelectedItem();
			querySixteen();
		}
		else if(queryNum == 17){
			pos_code = (String) valueList.getSelectedItem();
			querySeventeen();
		}
		else if(queryNum == 18){
			pos_code = (String) valueList.getSelectedItem();
			queryEighteen();
		}
		else if(queryNum == 19){
			pos_code = (String) valueList.getSelectedItem();
			queryNineteen();
		}
		else if(queryNum == 20){
			pos_code = (String) valueList.getSelectedItem();
			queryTwenty();
		}
		else if(queryNum == 21){
			pos_code = (String) valueList.getSelectedItem();
			missingNum = (String) secondaryList.getSelectedItem();
			queryEighteen();
		}
		else if(queryNum == 22){
			pos_code = (String) valueList.getSelectedItem();
			missingNum = (String) secondaryList.getSelectedItem();
			queryNineteen();
		}
		else if(queryNum == 23){
			pos_code = (String) valueList.getSelectedItem();
			queryTwentyThree();
		}
		else if(queryNum == 24){
			pos_code = (String) valueList.getSelectedItem();
			queryTwentyFour();
		}
		else if(queryNum == 30){
			pos_code = (String) valueList.getSelectedItem();
			per_id = (String) secondaryList.getSelectedItem();
			queryThirty();
		}
		try {
			Statement stmt = conn.createStatement();
			System.out.println(queryValue);
			rs = stmt.executeQuery(queryValue);
			//rs = ti.getTable(chosenTable);
			tabContent = ti.resultSet2Vector(rs);
			Vector tabTitles = ti.getTitlesAsVector(rs);
			TableModel tableModel = new DefaultTableModel(tabContent, tabTitles);
			table.setModel(tableModel); 
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	private void secondaryListComboActionPerformed(ActionEvent evt){
		if(queryNum == 8){
			job_code = (String) valueList.getSelectedItem();
			per_id = (String) secondaryList.getSelectedItem();
			
			queryEight();
		}
		else if(queryNum == 10){
			job_code = (String) valueList.getSelectedItem();
			per_id = (String) secondaryList.getSelectedItem();
			
			queryTen();
		}
		else if(queryNum == 11){
			job_code = (String) valueList.getSelectedItem();
			per_id = (String) secondaryList.getSelectedItem();
			
			queryEleven();
		}
		else if(queryNum == 13){
			job_code = (String) valueList.getSelectedItem();
			per_id = (String) secondaryList.getSelectedItem();
			queryThirteen();
		}
		else if(queryNum == 21){
			pos_code = (String) valueList.getSelectedItem();
			missingNum = (String) secondaryList.getSelectedItem();
			queryEighteen();
		}
		else if(queryNum == 22){
			pos_code = (String) valueList.getSelectedItem();
			missingNum = (String) secondaryList.getSelectedItem();
			queryNineteen();
		}
		else if(queryNum == 30){
			pos_code = (String) valueList.getSelectedItem();
			per_id = (String) secondaryList.getSelectedItem();
			queryThirty();
		}
		try {
			Statement stmt = conn.createStatement();
			System.out.println(queryValue);
			rs = stmt.executeQuery(queryValue);
			//rs = ti.getTable(chosenTable);
			tabContent = ti.resultSet2Vector(rs);
			Vector tabTitles = ti.getTitlesAsVector(rs);
			TableModel tableModel = new DefaultTableModel(tabContent, tabTitles);
			table.setModel(tableModel); 
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	
	private void queryOne(){
		queryValue = "SELECT name " + 
				"FROM Person NATURAL JOIN Works JOIN Job USING(job_code) " + 
				"WHERE comp_id=" + comp_id + " AND status=003";
	}
	private void queryTwo(){
		queryValue = "SELECT name, works.pay_rate " + 
				"FROM Person NATURAL JOIN Works JOIN Job USING(job_code) " +
				"WHERE status = 003 AND works.pay_type=001 AND comp_id=" + comp_id + " " +
				"ORDER BY works.pay_rate";
	}
	private void queryThree(){
		queryValue = "WITH company_salary_total AS ( " +
				   "SELECT comp_id, SUM(works.pay_rate) AS total_salary " +
				   "FROM works JOIN Job USING(job_code)" +
				   "WHERE works.pay_type=001 AND status =003 " +
				   "GROUP BY comp_id), " +
				   
				  "company_wage_total AS ( " +
				   "SELECT comp_id, SUM(works.pay_rate * 1920) AS total_wage " +
				   "FROM works JOIN Job USING(job_code) " +
				   "WHERE works.pay_type=002 AND status=003 " +
				   "GROUP BY comp_id), " +
				   
				  "company_salary_all AS ( " +
				    "SELECT company.comp_id, total_salary " +
				    "FROM Company LEFT OUTER JOIN  " +
				      "company_salary_total ON(Company.comp_id = company_salary_total.comp_id)) " +

				"SELECT company_salary_all.comp_id, SUM(NVL(total_salary,0) + NVL(total_wage,0)) AS labor_cost " +
				"FROM company_salary_all LEFT OUTER JOIN company_wage_total ON(company_salary_all.comp_id = company_wage_total.comp_id) " +
				"GROUP BY company_salary_all.comp_id " +
				"ORDER BY SUM(NVL(total_salary,0) + NVL(total_wage,0)) DESC ";
	}
	private void queryFour(){
		queryValue = "SELECT job_code FROM Works " +
				"WHERE status=003 AND per_id=" + per_id;
	}
	private void queryFive(){
		queryValue = "SELECT ks_code, ks_name " +
				"FROM skill_set NATURAL JOIN Knowledge_skill " +
				"WHERE per_id=" + per_id;
	}
	private void querySix(){
		queryValue = " WITH persons_jobs AS ( " +
				   "SELECT per_id, job_code, pos_code " +
				   "FROM Works JOIN Job USING(job_code) " +
				   "WHERE per_id=" + per_id + " and status=003) " +

					"SELECT job_code, ks_code " +
					"FROM persons_jobs NATURAL JOIN skills_required " +
					"NATURAL JOIN ((SELECT ks_code " +
				                      "FROM skills_required) " +
				                      "MINUS " +
				                      "(SELECT ks_code " +
				                      "FROM skill_set " +
				                      "WHERE per_id=" + per_id + ")) " +
				"ORDER BY job_code";
	}
	private void querySeven(){
		queryValue = "SELECT pos_code, ks_code AS job_skills, ks_name " +
				"FROM skills_required NATURAL JOIN Knowledge_skill " +
				"WHERE pos_code=" + pos_code;
	}
	private void queryEight(){
		queryValue = "SELECT DISTINCT ks_code, ks_name " +
				"FROM skills_required NATURAL JOIN knowledge_skill " +
				"NATURAL JOIN ((SELECT ks_code " +
									"FROM skills_required NATURAL JOIN job " +
									"WHERE job_code =" + job_code + ") "+
				                      "MINUS " +
									"(SELECT ks_code " +
				                      "FROM skill_set " + 
				                      "WHERE per_id=" + per_id + "))";
	}
	private void queryNine(String temp_table){
		queryValue = "WITH course_skill_set AS ( " +
						"SELECT c_code, ks_code " +
						"FROM Course NATURAL JOIN Skills_taught) " +

					"SELECT DISTINCT c_code " +
					"FROM course_skill_set c1, " +  temp_table +
					" WHERE ks_code = given_skill " + 
					"AND NOT EXISTS((SELECT given_skill FROM " + temp_table + ") " +
                      				"MINUS " +
                      				"(SELECT ks_code AS given_skill FROM course_skill_set c2  " +
                      				"WHERE c1.c_code = c2.c_code))";
	}
	private void queryTen(){
		queryValue = "WITH missing_skills AS ( " +
			    "SELECT ks_code " +
			    "FROM ( " +
			      "SELECT ks_code " +
			      "FROM skills_required NATURAL JOIN job " +
			      "WHERE job_code=" + job_code + " " +
			      "MINUS " +
			      "SELECT ks_code " +
			      "FROM skill_set " +
			      "WHERE per_id=" + per_id + ")) " +
			  
			  "SELECT c_code, course_title " +
			  "FROM course " +
			  "WHERE c_code NOT IN( " + 
			    "SELECT c_code " +
			    "FROM( " +
			      "SELECT ks_code, c_code " +
			      "FROM missing_skills, course " +
			      "MINUS " + 
			      "SELECT ks_code, c_code " +
			      "FROM skills_taught))";
	}
	private void queryEleven(){
		queryValue = "WITH missing_skills AS ( " +
			    "SELECT ks_code " +
			    "FROM ( " +
			      "SELECT ks_code " +
			      "FROM skills_required NATURAL JOIN job " +
			      "WHERE job_code=" + job_code + " " +
			      "MINUS " +
			      "SELECT ks_code " +
			      "FROM skill_set " +
			      "WHERE per_id=" + per_id + ")), " +
			  
			  "valid_courses AS ( " +
			  "SELECT c_code, sec_no, comp_id, complete_date " +
			  "FROM course NATURAL JOIN section " +
			  "WHERE c_code NOT IN( " + 
			    "SELECT c_code " +
			    "FROM ( " +
			      "SELECT ks_code, c_code " +
			      "FROM missing_skills, course " +
			      "MINUS " + 
			      "SELECT ks_code, c_code " +
			      "FROM skills_taught))), " + 
			      
			   "closest_date AS ( " +
			    "SELECT MIN(complete_date) as complete_date " + 
			    "FROM valid_courses) " +
			    		    
			    "SELECT c_code, sec_no, comp_id, complete_date " +
			    "FROM valid_courses NATURAL JOIN closest_date";
	}
	private void queryTwelve(String temp_table){
		
		queryValue = "WITH CourseSet_Skill(csetID, ks_code) AS ( " +
		    "SELECT csetID, ks_code " +
		    "FROM CourseSet CSet JOIN skills_taught CS ON CSet.c_code1=CS.c_code " +
		    "UNION " +
		    "SELECT csetID, ks_code " +
		    "FROM CourseSet CSet JOIN skills_taught CS ON CSet.c_code2=CS.c_code " +
		    "UNION " +
		    "SELECT csetID, ks_code " +
		    "FROM CourseSet CSet JOIN skills_taught CS ON CSet.c_code3=CS.c_code), " + 

		/* use division to find those course sets that cover missing skills */
		  "Cover_CSet(csetID, num) AS ( " +
		    "SELECT csetID, num " +
		    "FROM CourseSet CSet " +
		    "WHERE NOT EXISTS (SELECT given_skill " +
		                      "FROM " + temp_table +
		                      " MINUS "  +
		                      "SELECT ks_code " +
		                      "FROM CourseSet_Skill CSSk " +
		                      "WHERE CSSk.csetID = Cset.csetID)) " +
		                      
		  /* to find the smallest sets */
		"SELECT c_code1, c_code2, c_code3 " +
		"FROM Cover_CSet NATURAL JOIN CourseSet " +
		"WHERE num =(SELECT MIN(num) " +
		            "FROM Cover_CSet)";
	}
	
	private void queryThirteen(){
		queryValue = "WITH CourseSet_Skill(csetID, ks_code) AS ( " +
		    "SELECT csetID, ks_code " +
		    "FROM CourseSet CSet JOIN skills_taught CS ON CSet.c_code1=CS.c_code " +
		    "UNION " +
		    "SELECT csetID, ks_code " +
		    "FROM CourseSet CSet JOIN skills_taught CS ON CSet.c_code2=CS.c_code " +
		    "UNION " +
		    "SELECT csetID, ks_code " +
		    "FROM CourseSet CSet JOIN skills_taught CS ON CSet.c_code3=CS.c_code), " + 

		  "missing_skills AS ( " +
		    "SELECT ks_code " +
		    "FROM knowledge_skill "+ 
		    "NATURAL JOIN (SELECT ks_code " +
		                  "FROM skills_required NATURAL JOIN job " +
		                  "WHERE job_code= " +job_code +
		                  " MINUS " +
		                  "SELECT ks_code " +
		                  "FROM skill_set " +
		                  "WHERE per_id= " + per_id + ")), " +

		  "Cover_CSet(csetID, num) AS ( " +
		    "SELECT csetID, num " +
		    "FROM CourseSet CSet " +
		    "WHERE NOT EXISTS (SELECT ks_code " +
		                      "FROM missing_skills " +
		                      "MINUS " +
		                      "SELECT ks_code " +
		                      "FROM CourseSet_Skill CSSk " +
		                      "WHERE CSSk.csetID = Cset.csetID)) " +
		                      
		"SELECT c_code1, c_code2, c_code3 " +
		"FROM Cover_CSet NATURAL JOIN CourseSet " +
		"WHERE num =(SELECT MIN(num) " +
		            "FROM Cover_CSet)" ;
	}
	private void queryFifteen(){
		queryValue = "SELECT DISTINCT pos_code " +
					"FROM job_profile c1 " + 
					"WHERE NOT EXISTS((SELECT ks_code " +
						               "FROM skills_required c2 " +
						               "WHERE c1.pos_code = c2.pos_code) " +
						               "MINUS " +
						               "(SELECT ks_code " +
						               "FROM skill_set " +
						               "WHERE per_id =" + per_id + "))";
	}
	
	private void querySixteen(){
        queryValue = "WITH unqualified_positions AS ( " +
                "SELECT DISTINCT pos_code " +
                "FROM skills_required " +
                "NATURAL JOIN ((SELECT ks_code " +
                                   "FROM skills_required) " +
                                      "MINUS " +
                                      "(SELECT ks_code " +
                                      "FROM skill_set " +
                                      "WHERE per_id =" + per_id + "))), " +
                "total_pay AS ( " +
                    "SELECT job_code, " +
                        "case " +
                            "WHEN pay_type=002 THEN pay_rate * 1920  " +
                            "WHEN pay_type=001 THEN pay_rate " +
                        "END AS total_earnings " + 
                    "FROM job " +
                    "NATURAL JOIN((SELECT pos_code " +
                                           "FROM job) " +
                                           "MINUS " +
                                        "(SELECT pos_code " +
                                        "FROM unqualified_positions))) " +
                                                              
                "SELECT job_code, total_earnings " +
                "FROM total_pay " +
                "WHERE total_earnings >= ALL (SELECT total_earnings " +
                                                "FROM total_pay)";
    }
	private void querySeventeen(){
		queryValue = "SELECT name, email " +
					"FROM person " +
					"WHERE NOT EXISTS((SELECT ks_code " +
                           "FROM skills_required " +
                           "WHERE pos_code= " + pos_code + ") " +
                           "MINUS " +
                           "(SELECT ks_code " +
                           "FROM skill_set " +
                           "WHERE skill_set.per_id = person.per_id ))";
	}
	private void queryEighteen(){
		if(queryNum == 21){
			missingNum = (String) secondaryList.getSelectedItem();
		}
		queryValue = "WITH missing_skills AS( " +
				  "SELECT per_id, ks_code " +
				  "FROM Skills_required, person " +
				  "WHERE pos_code =" + pos_code + " " +
				  "MINUS " +
				  "SELECT per_id, ks_code " +
				  "FROM skill_set, job_profile " +
				  "WHERE pos_code =" + pos_code + "), " +
                        
				"missing_skill_count AS( " +
				"SELECT per_id, COUNT(ks_code) AS num_of_missing_skills " +
				"FROM missing_skills " +
				"GROUP BY per_id " +
				"ORDER BY num_of_missing_skills) " +
    
				"SELECT per_id, num_of_missing_skills " +
				"FROM missing_skill_count " +
				"WHERE num_of_missing_skills <= " + missingNum;
	}
	private void queryNineteen(){
		queryValue = "WITH missing_skills AS( " +
				  "SELECT per_id, ks_code " +
				  "FROM Skills_required, person " +
				  "WHERE pos_code =" + pos_code + " " +
				  "MINUS " +
				  "SELECT per_id, ks_code " +
				  "FROM skill_set, job_profile " +
				  "WHERE pos_code =" + pos_code + "), " +
                    
			"missing_skill_count AS( " +
			"SELECT per_id, COUNT(ks_code) AS num_of_missing_skills " +
			"FROM missing_skills " +
			"GROUP BY per_id " +
			"ORDER BY num_of_missing_skills), " +

			"people_missing_skills AS( " +
			"SELECT per_id, num_of_missing_skills " +
			"FROM missing_skill_count " +
			"WHERE num_of_missing_skills <= " + missingNum + ") " +
			
			"SELECT ks_code, COUNT(per_id) AS people_missing " +
			"FROM missing_skills NATURAL JOIN people_missing_skills " +
			"GROUP BY ks_code " +
			"ORDER BY people_missing";
	}
	private void queryTwenty(){
		queryValue = "WITH missing_skills AS( " +
				  "SELECT per_id, ks_code " +
				  "FROM Skills_required, person " +
				  "WHERE pos_code =" + pos_code + " " +
				  "MINUS " +
				  "SELECT per_id, ks_code " +
				  "FROM skill_set, job_profile " +
				  "WHERE pos_code =" + pos_code + "), " +

  		"missing_skill_count AS( " +
  		"SELECT per_id, COUNT(ks_code) AS num_of_missing_skills " +
  		"FROM missing_skills " +
  		"GROUP BY per_id " +
  		"ORDER BY num_of_missing_skills), " +

  		"least_skills_missing AS( " +
  		"SELECT num_of_missing_skills " +
  		"FROM missing_skill_count " +
  		"WHERE ROWNUM = 1) " +

		"SELECT per_id, num_of_missing_skills " +
		"FROM missing_skill_count NATURAL JOIN least_skills_missing ";
	}
	private void queryTwentyThree(){
		queryValue = "SELECT DISTINCT per_id, name, address, zip_code, email, gender, phone " +
					"FROM person NATURAL JOIN works JOIN job USING(job_code) " +
					"WHERE pos_code=" + pos_code;
	}
	private void queryTwentyFour(){
		queryValue = "WITH unemployeed AS( " +
					"SELECT per_id, name, address, zip_code, email, gender, phone, job_code " +
					"FROM person NATURAL JOIN (SELECT per_id, job_code " +
                    			"FROM works " +
                    			"WHERE status=004 " +
                    			"MINUS " + 
                    			"SELECT per_id, job_code " +
                    			"FROM works " + 
                    			"WHERE status=003)) " +
  
	"SELECT per_id, name, address, zip_code, email, gender, phone " +
	"FROM unemployeed NATURAL JOIN job " +
	"WHERE pos_code=" + pos_code;	
	}
	private void queryTwentyFive(){
		queryValue = "WITH company_employees AS( " +
					"SELECT comp_id, COUNT(per_id) AS employee_count " +
					"FROM works JOIN job USING(job_code) " +
					"WHERE status=003 " +
					"GROUP BY comp_id " +
					"ORDER BY employee_count DESC), " +
  
					"company_salary_total AS ( " +
					"SELECT comp_id, SUM(works.pay_rate) AS total_salary " +
					"FROM works JOIN Job USING(job_code)" +
					"WHERE works.pay_type=001 AND status =003 " +
					"GROUP BY comp_id), " +

					"company_wage_total AS ( " +
					"SELECT comp_id, SUM(works.pay_rate * 1920) AS total_wage " +
					"FROM works JOIN Job USING(job_code) " +
					"WHERE works.pay_type=002 AND status=003 " +
					"GROUP BY comp_id), " +
   
					"company_salary_all AS( " +
					"SELECT company.comp_id, total_salary " +
					"FROM company LEFT OUTER JOIN  " +
					"company_salary_total ON(company.comp_id = company_salary_total.comp_id)), " +
      
					"company_wage_salary_all AS( " +
					"SELECT company_salary_all.comp_id, total_salary, total_wage " +
					"FROM company_salary_all LEFT OUTER JOIN " +
					"company_wage_total ON(company_salary_all.comp_id = company_wage_total.comp_id)), " +

					"company_labor_cost AS( " +
					"SELECT comp_id, SUM(NVL(total_salary,0) + NVL(total_wage,0)) AS labor_cost " +
					"FROM company_wage_salary_all " +
					"GROUP BY comp_id " +
					"ORDER BY SUM(NVL(total_salary,0) + NVL(total_wage,0)) DESC) " +

		"SELECT company_employees.comp_id AS largest_employeer, employee_count, " +
		"company_labor_cost.comp_id AS largest_cost, labor_cost " +
		"FROM company_employees, company_labor_cost " +
		"WHERE ROWNUM = 1";
	}
	private void queryThirty(){
		queryValue = "WITH missing_skills AS( " +
				"SELECT DISTINCT ks_code " +
				"FROM ((SELECT ks_code " +
						"FROM skills_required " + 
						"WHERE pos_code=" + pos_code +") " +
						"MINUS " +
						"(SELECT ks_code " +
						"FROM skill_set " +
						"WHERE per_id=" + per_id + "))) " +
                                
	"SELECT DISTINCT c_code " +
	"FROM skills_taught NATURAL JOIN missing_skills";
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
