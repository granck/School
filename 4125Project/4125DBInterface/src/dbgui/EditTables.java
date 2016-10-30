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
import javax.swing.JTextField;
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
public class EditTables extends javax.swing.JFrame {
	private JLabel tnLabel;
	private JComboBox tnJCombo;
	private JComboBox primaryCombo;
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
	
	private String function = null;
	private String[] functionList = {"New Person", "New Job Profile", "New Job", "New Course"};
	private int numOfAttributes = 0;
	
	JTextField[] tableFields = null; //table who's attributes we are adding to
	JLabel[] tableLabels = null; //labels for tableFields
	JTextField message;
	/**
	* constructor takes a reference of a db accesser object 
	*/
	public EditTables(TableUpdate tu, java.sql.Connection conn) {
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
				tnLabel = new JLabel();
				getContentPane().add(tnLabel);
				tnLabel.setText("Function");
				tnLabel.setBounds(15, 14, 91, 28);
			}
			{
				message = new JTextField();
				getContentPane().add(message);
				message.setBounds(400, 100, 200, 100);
				message.setVisible(false);
			}
			{
				numOfAttributes = 8;
				tableFields = new JTextField[numOfAttributes];
				tableLabels = new JLabel[numOfAttributes];
				for(int x = 0; x < numOfAttributes; x++){
					tableFields[x] = new JTextField();
					tableLabels[x] = new JLabel();
					getContentPane().add(tableFields[x]);
					getContentPane().add(tableLabels[x]);
					tableFields[x].setBounds(150, 100 + 35*x, 119, 30);
					tableLabels[x].setBounds(40, 100 + 35*x, 119, 30);
				}
			}
			{
				//ComboBoxModel tnJComboModel = new DefaultComboBoxModel(ti.listTableName());
				ComboBoxModel tnJComboModel = new DefaultComboBoxModel(functionList);
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
				primaryCombo = new JComboBox();
				getContentPane().add(primaryCombo);
				primaryCombo.setBounds(91, 45, 455, 28);
				primaryCombo.setVisible(false);
			}
			/**
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
			**/
			{
				jButton1 = new JButton();
				getContentPane().add(jButton1);
				jButton1.setText("Default");
				jButton1.setBounds(567, 14, 130, 28);
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
				jButton2.setText("Default");
				jButton2.setBounds(567, 45, 130, 28);
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
		message.setVisible(false); //message that lets user know if row added successfully
		jButton1.setVisible(false);
		jButton2.setVisible(false);
		
		for(int x = 0; x < numOfAttributes; x++){
			tableLabels[x].setVisible(false);
			tableFields[x].setVisible(false);
		}
		
		String chosenFunction = (String) tnJCombo.getSelectedItem();
		String chosenTable = null;
		
		//if we are adding a new person
		if(chosenFunction.equals("New Person")){
			function = "person";
			chosenTable = "Person";
			Vector tabTitles = null;
			
			//get attributes of Person table
			try{
			rs = ti.getTable(chosenTable);
			tabTitles = ti.getTitlesAsVector(rs);
			System.out.println(tabTitles);
			}
			catch(SQLException sqle){
				sqle.printStackTrace();
			}
			
			//create labels and textfields for attributes
			numOfAttributes = tabTitles.size();
			
			//cycles through all attributes that chosen function needs
			//and assigns a label and text field
			for(int x = 0; x < numOfAttributes; x++){
				tableLabels[x].setText((String) tabTitles.elementAt(x));
				tableLabels[x].setVisible(true);
				tableFields[x].setVisible(true);
				
			}
			jButton1.setText("Add Person");
			jButton1.setVisible(true);		
		}
		else if(chosenFunction.equals("New Job Profile")){
			function = "job_profile";
			chosenTable = "job_profile";
			Vector tabTitles = null;
			
			//get attributes of job_profile table
			try{
			rs = ti.getTable(chosenTable);
			tabTitles = ti.getTitlesAsVector(rs);
			System.out.println(tabTitles);
			}
			catch(SQLException sqle){
				sqle.printStackTrace();
			}
			
			//create labels and textfields for attributes
			numOfAttributes = tabTitles.size();

			//cycles through all attributes that chosen function needs
			//and assigns a label and text field
			for(int x = 0; x < numOfAttributes; x++){
				tableLabels[x].setText((String) tabTitles.elementAt(x));
				tableLabels[x].setVisible(true);
				tableFields[x].setVisible(true);
				
			}
			jButton1.setText("Add Job Profile");
			jButton1.setVisible(true);

		}
		else if(chosenFunction.equals("New Job")){
			function = "job";
			chosenTable = "job";
			Vector tabTitles = null;
			String[] jobCodes = null;
			
			//get attributes of job_profile table
			try{
			rs = ti.getTable(chosenTable);
			tabTitles = ti.getTitlesAsVector(rs);
			System.out.println(tabTitles);
			jobCodes = ti.getColumn("job", "job_code");
			}
			catch(SQLException sqle){
				sqle.printStackTrace();
			}
			ComboBoxModel jobComboModel = new DefaultComboBoxModel(jobCodes);
			primaryCombo.setModel(jobComboModel);
			primaryCombo.setVisible(true);
			
			//create labels and textfields for attributes
			numOfAttributes = tabTitles.size();
			
			//cycles through all attributes that chosen function needs
			//and assigns a label and text field
			for(int x = 0; x < numOfAttributes; x++){
				tableLabels[x].setText((String) tabTitles.elementAt(x));
				tableLabels[x].setVisible(true);
				tableFields[x].setVisible(true);
			}
			jButton1.setText("Add Job");
			jButton1.setVisible(true);
			jButton2.setText("Set Job inactive");
			jButton2.setVisible(true);
		}
		
		else if(chosenFunction.equals("New Course")){
			function = "course";
			chosenTable = "course";
			Vector tabTitles = null;
			String[] courseCodes = null;
			
			//get attributes of course table
			try{
			rs = ti.getTable(chosenTable);
			tabTitles = ti.getTitlesAsVector(rs);
			System.out.println(tabTitles);
			courseCodes = ti.getColumn("course", "c_code");
			}
			catch(SQLException sqle){
				sqle.printStackTrace();
			}
			
			ComboBoxModel jobComboModel = new DefaultComboBoxModel(courseCodes);
			primaryCombo.setModel(jobComboModel);
			primaryCombo.setVisible(true);
			//create labels and textfields for attributes
			numOfAttributes = tabTitles.size();
			
			//cycles through all attributes that chosen function needs
			//and assigns a label and text field
			for(int x = 0; x < numOfAttributes; x++){
				tableLabels[x].setText((String) tabTitles.elementAt(x));
				tableLabels[x].setVisible(true);
				tableFields[x].setVisible(true);
				
			}
			jButton1.setText("Add Course");
			jButton1.setVisible(true);
			jButton2.setText("Set inactive");
			jButton2.setVisible(true);
		}
		/**
		try {
			Statement stmt = conn.createStatement();
			rs = ti.getTable(chosenTable);
			tabContent = ti.resultSet2Vector(rs);
			Vector tabTitles = ti.getTitlesAsVector(rs);
			TableModel tableModel = new DefaultTableModel(tabContent, tabTitles);
			table.setModel(tableModel); 
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		**/
	}
	private void primaryComboActionPerformed(ActionEvent evt){
		
	}
	private void jButton1ActionPerformed(ActionEvent evt) {
		//System.out.println("jButton1.actionPerformed, event=" + evt);
		// TODO add your code for jButton1.actionPerformed
		if(function.equals("person")){
			String[] tableValues = new String[numOfAttributes];
			for(int x = 0; x < numOfAttributes; x++){
				tableValues[x] = tableFields[x].getText();
			}
			Person newPerson = new Person();
			Statement stmt = null;
			try{
			stmt = conn.createStatement();
			} catch(SQLException sqle){
				sqle.printStackTrace();
			}
			
			//if row failed to add, inform user
			if(!newPerson.create(conn, stmt, tableValues)){
				message.setText("Failed to add new person");
			}
			else{
				message.setText("New person added successfully");
			}
			message.setVisible(true);
		}
		else if(function.equals("job_profile")){
			String[] tableValues = new String[numOfAttributes];
			for(int x = 0; x < numOfAttributes; x++){
				tableValues[x] = tableFields[x].getText();
			}
			Job_profile profile = new Job_profile();
			Statement stmt = null;
			try{
			stmt = conn.createStatement();
			} catch(SQLException sqle){
				sqle.printStackTrace();
			}
			
			//if row failed to add, inform user
			if(!profile.create(conn, stmt, tableValues)){
				message.setText("Failed to add new profile");
			}
			else{
				message.setText("New profile added successfully");
			}
			message.setVisible(true);
		}
		else if(function.equals("job")){
			String[] tableValues = new String[numOfAttributes];
			for(int x = 0; x < numOfAttributes; x++){
				tableValues[x] = tableFields[x].getText();
			}
			Job job = new Job();
			Statement stmt = null;
			try{
			stmt = conn.createStatement();
			} catch(SQLException sqle){
				sqle.printStackTrace();
			}
			
			//if row failed to add, inform user
			if(!job.create(conn, stmt, tableValues)){
				message.setText("Failed to add new job");
			}
			else{
				message.setText("New job added successfully");
			}
			message.setVisible(true);
		}
		else if(function.equals("course")){
			String[] tableValues = new String[numOfAttributes];
			for(int x = 0; x < numOfAttributes; x++){
				tableValues[x] = tableFields[x].getText();
			}
			Course cour = new Course();
			Statement stmt = null;
			try{
			stmt = conn.createStatement();
			} catch(SQLException sqle){
				sqle.printStackTrace();
			}
			
			//if row failed to add, inform user
			if(!cour.create(conn, stmt, tableValues)){
				message.setText("Failed to add new course");
			}
			else{
				message.setText("New course added successfully");
			}
			message.setVisible(true);
		}
		/**
		try {
			Vector titles = ti.getTitlesAsVector(rs);
			newRow = new Vector(table.getColumnCount());
			tabContent.add(newRow);
			TableModel tableModel = new DefaultTableModel(tabContent, titles);
			table.setModel(tableModel);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		**/
	}
	
	private void jButton2ActionPerformed(ActionEvent evt) {
		// System.out.println("jButton2.actionPerformed, event=" + evt);
		// TODO add your code for jButton2.actionPerformed
		// System.out.println(newRow.elementAt(0));
		if(function.equals("job")){
			Job job = new Job();
			String inactiveJob = (String) primaryCombo.getSelectedItem();
			Statement stmt = null;
			try{
			stmt = conn.createStatement();
			} catch(SQLException sqle){
				sqle.printStackTrace();
			}
			if(!job.inactive(stmt, inactiveJob)){
				message.setText("Failed to set\njob " + inactiveJob + " to inactive");
			}
			else{
				message.setText("Added job " + inactiveJob + " to inactive");
			}
			message.setVisible(true);
		}
		else if(function.equals("course")){
			Course course = new Course();
			String inactiveCourse = (String) primaryCombo.getSelectedItem();
			Statement stmt = null;
			try{
			stmt = conn.createStatement();
			} catch(SQLException sqle){
				sqle.printStackTrace();
			}
			if(!course.inactive(stmt, inactiveCourse)){
				message.setText("Failed to set\ncourse " + inactiveCourse + " to inactive");
			}
			else{
				message.setText("Added course " + inactiveCourse + " to inactive");
			}
			message.setVisible(true);
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
		EditTables inst = new EditTables(tu, conn);
		inst.setVisible(true);
	}
}
