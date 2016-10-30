/*
 * Created on Jul 4, 2006
 */
package dbaccess;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
// import java.sql.PreparedStatement;
// import java.text.DateFormat;

/**
 * @author Shengru Tu
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class TableInfo {

	private Connection conn;
	final String host = "localhost"; 
	final String port = "1521";
	final String sID = "nbdb";

	/**
	 * @param username
	 *            the user name to access the database.
	 * @param password
	 *            This constructor establishes connection and a java.sql.Types
	 *            checker object.
	 */
	public TableInfo(	String host, 
						String port, 
						String sID, 
						String username, 
						String passwd) throws SQLException { 
		conn = new DBConnection(host, port, sID).getDBConnection(username, passwd); 
	}

	public TableInfo(String username, String passwd) throws SQLException { 
		this.conn = new DBConnection(host, port, sID).getDBConnection(username, passwd); 
 
	}

	public TableInfo(Connection conn) throws SQLException { 
		this.conn = conn; 
	}

	/**
	 * execute a simple SQL statement without any parameter 
	 */
	public int runUpdate(String str) throws SQLException {
		Statement stmt = conn.createStatement();
		return stmt.executeUpdate(str);
	}
	
	/**
	 * Run a fixed query.
	 */
	public ResultSet runSQLQuery(String str) throws SQLException {
		Statement stmt = conn.createStatement();
		return stmt.executeQuery(str);

	}	

	/**
	 * list the user's table names
	 */
	public String[] listTableName() throws SQLException {
		String str = "SELECT Table_Name FROM USER_TABLES";
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(str);
		ArrayList al = new ArrayList();
		while (rs.next()) {
			al.add(rs.getString("Table_Name"));
		}
		String[] tn = new String[1];
		tn = (String[]) al.toArray(tn);
		return tn;
	}

	/**
	 * return the ResultSet object of a table
	 */
	public ResultSet getTable(String tn) throws SQLException {
		String str = "SELECT * FROM " + tn;
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(str);
		return rs;
	}
	
	/**
	 * return the columns' titles of a table as an array of String
	 */
	public String[] getTitles(ResultSet rs) throws SQLException {
		ResultSetMetaData rsmd = rs.getMetaData();
		int col = rsmd.getColumnCount();
		String[] title = new String[col];
		for (int i = 0; i < col; i++) {
			title[i] = rsmd.getColumnLabel(i+1);
		}
		return title;
	}

	/**
	 * return a column's values in String[]
	 */
	public String[] getColumn(String tableName, String colName) throws SQLException {
		ResultSet rs = runSQLQuery("SELECT DISTINCT " + colName + " FROM " + tableName + 
									" ORDER BY " + colName); 
		ArrayList list = new ArrayList(); 
		while (rs.next()) { 
			Object obj = rs.getObject(colName);
			String str = "null";
			if (obj != null) 
				str = obj.toString();
			list.add(str);
		}
		String[] result = new String[1];
		result = (String[])list.toArray(result);
		return result;
	}
	
	/**
	 * Return an ordered, specific ResultSet of a table.
	 */
	public ResultSet getOrderedColumn(String tn, String values, String order) throws SQLException{
		String str = "SELECT " + values + " FROM " + tn + " ORDER BY " + order;
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(str);
		return rs;
	}

	/**
	 * return a date column's dates in a SHORT format
	 */
	public String[] getDateColumnInShort(String tableName, String colName) throws SQLException {
		String[] result = getDateColumn(tableName, colName, DateFormat.SHORT);
		return result;
	}

	/**
	 * return a date column's dates in a chosen format
	 */
	public String[] getDateColumn(String tableName, String colName, int form) throws SQLException {
		ResultSet rs = runSQLQuery("SELECT DISTINCT " + colName + " FROM " + tableName + 
									" ORDER BY " + colName); 
		DateFormat df = DateFormat.getDateInstance(form);
		ArrayList dList = new ArrayList(); 
		while (rs.next()) { 
			String dat = df.format(rs.getDate(colName));
			dList.add(dat);
		}
		String[] result = new String[1];
		result = (String[])dList.toArray(result);
		return result;
	}
	
	/**
	 * return the columns' titles of a table as a Vector
	 */
	public Vector getTitlesAsVector(ResultSet rs) throws SQLException {
		ResultSetMetaData rsmd = rs.getMetaData();
		int col = rsmd.getColumnCount();
		Vector title = new Vector();
		for (int i = 0; i < col; i++) {
			title.add(rsmd.getColumnLabel(i+1));
		}
		return title;
	}
	
	/**
	 * return the columns' types 
	 */
	public int[] getColumnTypes(ResultSet rs) throws SQLException {
		ResultSetMetaData rsmd = rs.getMetaData();
		int col = rsmd.getColumnCount();
		int[] types = new int[col];
		for (int i = 0; i < col; i++) {
			types[i] = rsmd.getColumnType(i+1);
		}
		return types;
	}
	
	/**
	 * return the selected resultset from a table 
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public ResultSet getSelectedResultSet(String tn, String colName, 
							int colType, String val) throws Exception { 
		String whereClause = null;
		if (val.equals("null")) {
			whereClause = colName + " is null"; 
		} else {
			if (colType == Types.DATE) {
				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
				Date dat = sdf.parse(val); 
				sdf.applyPattern("dd-MMM-yyyy");
				val = "'" + sdf.format(dat) + "'";
			} else if (colType == Types.VARCHAR || colType == Types.CHAR || 
				colType == Types.LONGVARCHAR) {
				val = "'" + val + "'";
			} 
			whereClause = colName + " = " + val;
		}
		String sqlStr = "SELECT * FROM " + tn + " WHERE " + whereClause; 
		ResultSet rs = runSQLQuery(sqlStr); 
		return rs;
	}
	
	/**
	 * convert a ResultSet object to a two dimensional array of String 
	 */
	public String[][] resultSet22DArray(ResultSet rs) throws SQLException {
		ResultSetMetaData rsmd = rs.getMetaData();
		int col = rsmd.getColumnCount();
		ArrayList al = new ArrayList(1); 
		String[] row = null; 
		while (rs.next()) { 
			row = new String[col]; 
			for (int i = 0; i < col; i++) {
				Object obj = rs.getObject(i+1); 
				if (obj != null)
					row[i] = obj.toString();
				else 
					row[i] = "";
			}
			al.add(row);
		} 
		String[][] tab = new String[al.size()][col]; 
		for (int i = 0; i < al.size(); i++) {
			tab[i] = (String[])al.get(i);
		}
		return tab;
	} 
	
	/**
	 * convert a ResultSet object to an array of String 
	 */
	public String[] resultSet2Array(ResultSet rs) throws SQLException {
		ArrayList al = new ArrayList(1);
		String row = null;
		while (rs.next()) {
			row = new String("");
			Object obj = rs.getObject(1);
			if (obj != null) {
				row = obj.toString();
				al.add(row);
			}
		}
		String[] tab = new String[al.size()];
		for (int i = 0; i < al.size(); i++) {
			tab[i] = (String) (al.get(i));
		}
		return tab;
	}
	
	/**
	 * convert a ResultSet object to a two dimensional array of String 
	 */
	public Vector resultSet2Vector(ResultSet rs) throws SQLException {
		ResultSetMetaData rsmd = rs.getMetaData();
		int col = rsmd.getColumnCount();
		Vector vec = new Vector(); 
		Vector row = null; 
		while (rs.next()) { 
			row = new Vector(); 
			for (int i = 0; i < col; i++) {
				row.add(rs.getObject(i+1)); 
			}
			vec.add(row);
		} 
		return vec;
	}

	/**
	 * return a String array of the column names 
	 */
	public static String[] getColumn(ResultSet rs) throws SQLException {
		ResultSetMetaData rmd = rs.getMetaData(); 
		int colNum = rmd.getColumnCount();
		String[] col = new String[colNum]; 
		for (int i = 0; i < colNum; i++) {
			col[i] = rmd.getColumnName(i+1); 
		}
		return col;
	}

	/**
	 * return a String array of the column types 
	 */
	public static int[] getColumnType(ResultSet rs) throws SQLException {
		ResultSetMetaData rmd = rs.getMetaData(); 
		int colNum = rmd.getColumnCount();
		int[] colType = new int[colNum]; 
		for (int i = 0; i < colNum; i++) {
			colType[i] = rmd.getColumnType(i+1); 
		}
		return colType;
	}
	
	/**
	 * tester
	 */
	public static void main(String[] args) throws SQLException {
		if (args.length < 2) {
			System.out.println("usage: java TableInfo db-username db-password"); 
			System.exit(1);
		}
		DBConnection tc = new DBConnection("nbdb"); 
		Connection conn = tc.getDBConnection(args[0], args[1]); 
		TableInfo ti = new TableInfo(conn); 
		System.out.println("\nYour tables are listed below.\n"); 
		String[] names = ti.listTableName(); 
		if (names.length == 0) 
			System.out.println("You do not have any table.");
		else {
			for (int i=0; i<names.length; i++) {
				System.out.println(names[i]); 
			}
			System.out.println("\nList your first table contents.\n");
			ResultSet rs = ti.getTable(names[0]);
			String[] titles = ti.getTitles(rs); 
			for (int i=0; i<titles.length; i++) {
				System.out.print(titles[i] + "\t|"); 
			}
			System.out.println("\n"); 
			String[][] table = ti.resultSet22DArray(rs); 
			for (int i=0; i<table.length; i++) {
				for (int j=0; j<table[0].length; j++) {
					System.out.print(table[i][j] + "\t|");
				}
				System.out.println(); 
			}
		}
		String[] dateS = ti.getDateColumnInShort("Emp", "HireDate"); 
		String[] dateL = ti.getDateColumn("Emp", "HireDate", DateFormat.MEDIUM);
	}
}
