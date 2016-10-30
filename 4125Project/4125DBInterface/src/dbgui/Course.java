/**
 * Garrick Ranck and Kristen Maus
 * Database 4125
 */

package dbgui;
import java.sql.*;

public class Course {
   private String queryValue;

   public boolean create(Connection conn, Statement stmt, String[] attributes) {
	   boolean success = true;
	   queryValue = "INSERT INTO Course VALUES(" + attributes[0] + ", '" + attributes[1] + "', " + attributes[2] + ", '" + attributes[3] + "', " 
	                                             + attributes[4] + ")";
	   try {
		   System.out.println(queryValue);
		  PreparedStatement stmt2 = conn.prepareStatement(queryValue);
		  stmt2.executeUpdate(queryValue);
	   } catch (SQLException sqle) {
		  success = false;
		  sqle.printStackTrace();
	   }
	   return success;
   }
   
   public void create(Statement stmt, int c_code, String course_title, int difficulty, String description, int status) {
      queryValue = "INSERT INTO Course VALUES(" + c_code + ", " + course_title + ", " + difficulty + ", " + description + ", " + status + ");";
      try {
         stmt.executeUpdate(queryValue);
      } catch (SQLException sqle) {
         sqle.printStackTrace();
      }
   }

   public boolean inactive(Statement stmt, String c_code) {
	  boolean success = true;
      queryValue = "UPDATE Course SET status=002 WHERE c_code = " + c_code; //002 is expired
      try {
         stmt.executeUpdate(queryValue);
      } catch (SQLException sqle) {
         sqle.printStackTrace();
         success = false;
      }
      return success;
   }
}