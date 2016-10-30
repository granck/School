/**
 * Garrick Ranck and Kristen Maus
 * Database 4125
 */
package dbgui;
import java.sql.*;

public class Job {
   private String queryValue;
   
   public boolean create(Connection conn, Statement stmt, String[] attributes) {
	   boolean success = true;
	   queryValue = "INSERT INTO Job VALUES(" + attributes[0] + ", " + attributes[1] + ", " + attributes[2] + ", " + attributes[3] + ", " 
	                                             + attributes[4] + ", " + attributes[5] + ")";
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
   
   public void create(Statement stmt, int job_code, int job_type, double pay_rate, int pay_type, int comp_id, int pos_code) {
      queryValue = "INSERT INTO Job VALUES(" + job_code + ", " + job_type + ", " + pay_rate + ", " + pay_type + ", " 
                                             + comp_id + ", " + pos_code + ");";
      try {
         stmt.executeUpdate(queryValue);
      } catch (SQLException sqle) {
         sqle.printStackTrace();
      }
   }

   public boolean inactive(Statement stmt, String job_code) {
	   boolean success = true;
      queryValue = "UPDATE Job SET job_type=003 WHERE job_code =" + job_code; // 003 is inactive

      try {
         stmt.executeUpdate(queryValue);
      } catch (SQLException sqle) {
         sqle.printStackTrace();
         success = false;
      }
      return success;
   }
}