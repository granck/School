package dbgui;
/**
 * Garrick Ranck and Kristen Maus
 * Database 4125
 */

import java.sql.*;

public class Hire {
   private String queryValue;
   private ResultSet rs;
   private Array ar;
   private int[] ks_codes;
   private int work_id = 1000000000; // need to generate unique work_ids

   public void Hire(Statement stmt, int job_code, int per_id, int status, double pay_rate, int pay_type, int job_type) {
      ar = personMissingSkills(stmt, job_code, per_id);
      ks_codes = ar.getArray(); 
      if (isQualified(ks_codes)) {
         queryValue = "INSERT INTO Works VALUES (" + work_id + ", " + per_id + ", " + job_code + ", " + status + ", " 
                                             + pay_rate + ", " + pay_type + ", " + job_type + ");";
      } else {
         System.out.println("Why you hire?! (╯°□°）╯︵ ┻━┻ ");
      }
  }

   /**
    * Currently not working at all, need to figure out how to handle ResultSet
    * returning an Array(interface) instead of a useable Array. This issue is also
    * happening in the Hire constructor, and since we don't have an acutal array to compare
    * to, this is continuing in the isQualified() method.
    */
   public Array personMissingSkills(Statement stmt, int job_code, int per_id) {
      queryValue = "SELECT ks_code " + 
                     "FROM skills_required, person " +
                     "NATURAL JOIN ((SELECT ks_code " + 
                                   "FROM skills_required " +
                                   "WHERE job_code = " + job_code + ") " +
                                    "MINUS " + 
                                    "(SELECT ks_code " + 
                                    "FROM skill_set " +
                                    "WHERE per_id = " + per_id + ")); ";
      try {
         stmt.executeQuery(queryValue);
      } catch (SQLException sqle) {
         sqle.printStackTrace();
      }

      rs = stmt.getResultSet();
      return rs.getArray(0);
   }

   public boolean isQualified(int[] array) {
      boolean qualified = false;
      if (ks_codes == null) {
         qualified = true;
      }
      return qualified;
   } 

}