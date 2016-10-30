/**
 * Garrick Ranck and Kristen Maus
 * Database 4125
 */

package dbgui;
import java.sql.*;

public class Job_profile {
   private String queryValue;
   
   public boolean create(Connection conn, Statement stmt, String[] attributes){
	   boolean success = true;
	   queryValue = "INSERT INTO Job_profile VALUES(" + attributes[0] + ", '" + attributes[1] + "', '" + attributes[2] + "', " + attributes[3] + ")";
	   
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
   public void create(Statement stmt, int pos_code, String title, String description, double avg_pay) {
      queryValue = "INSERT INTO Job_profile VALUES(" + pos_code + ", " + title + ", " + description + ", " + avg_pay + ");";
      try {
         stmt.executeUpdate(queryValue);
      } catch (SQLException sqle) {
         sqle.printStackTrace();
      }
   }

/**   public static void delete(int pos_code) {
 *     queryValue = "DELETE FROM Job WHERE pos_code = " + pos_code + ";";
 *     stmt.executeUpdate(queryValue);
 *     queryValue = "DELETE FROM Skills_required WHERE pos_code = " + pos_code + ";";
 *     stmt.executeUpdate(queryValue);
 *     queryValue = "DELETE FROM Job_profile WHERE pos_code = " + pos_code + ";";
 *     stmt.executeUpdate(queryValue);
 * }
 */
}