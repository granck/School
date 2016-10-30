/**
 * Garrick Ranck and Kristen Maus
 * Database 4125
 */

package dbgui;
import java.sql.*;

public class Person {
   private String queryValue;
   private boolean success; //returns true if successful
   
   public boolean create(Connection conn, Statement stmt, String[] attributes){
	   success = true;
	   queryValue = "INSERT INTO Person VALUES(" + attributes[0] + ", '" + attributes[1] + "', '" + attributes[2] + "', " + attributes[3] + ", '" 
               + attributes[4] + "', " + attributes[5] + ", " + attributes[6] + ")";
	    try {
	    	System.out.println(queryValue);
	    	PreparedStatement stmt2 = conn.prepareStatement(queryValue);
	    	stmt2.executeUpdate(queryValue);
	    } catch (SQLException sqle) {
	    	sqle.printStackTrace();
	    	success = false;
	    }
	    return success;
   }
   public void create(Statement stmt, int per_id, String name, String address, int zip_code, String email, int gender, int phone) {
      queryValue = "INSERT INTO Person VALUES(" + per_id + ", " + name + ", " + address + ", " + zip_code + ", " 
                                             + email + ", " + gender + ", " + phone + ");";
      try {
         stmt.executeUpdate(queryValue);
      } catch (SQLException sqle) {
         sqle.printStackTrace();
      }
   }
/** Don't know if people should be deleted
 *  public static void delete(int per_id) {
 *     queryValue = "UPDATE Job SET job_type=003 WHERE job_code = " + job_code + ";"; // 003 is inactive
 *     stmt.executeUpdate(queryValue);
 *  }
**/
}