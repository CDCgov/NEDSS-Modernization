/* ---------------------------------------------------------------------------------------------------
 * Emergint
 * 501 East Broadway
 * Suite 250, Louisville, KY 40202
 * Copyright © 2002
 *  -----------------------------------------------------------------------------------------------------
 *  Author:
 *  Description :
 *-------------------------------------------------------------------------------------------------------
 */
package nndm;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


/**
 * This represents the connection to the database server that houses the message tables.
 * This class enforces the establishment of only one connection to the database.  When
 * using this connection, be sure to releaseConnection(), allowing for the connection to be reused by
 * another process.
 *
 */
public class nndmDatabaseConnection {


   /**
    * Creates the JDBC Connection.
    * @exception SQLException if unable to connect to the database server.
    */
   static public Connection createConnection() throws Exception {
   	Connection con = null;
    try  { 
        Context initCtx = new InitialContext();
       // Context envCtx = (Context)
       //   initCtx.lookup("java:comp/env");
        DataSource ds = (DataSource)initCtx.lookup("java:jboss/datasources/MsgOutDataSource");
        con =  ds.getConnection();
      } catch (SQLException ex) {
    	  ex.printStackTrace();
      	  throw new Exception("Unable to open MSG_OUT connection: " + ex.getMessage());
      } catch(NamingException n){
    	  n.printStackTrace();
      	  throw new Exception("Invalid JNDI name: /datasources/MsgOutDataSource" + n.getMessage());
      }
      return con;
   }

}