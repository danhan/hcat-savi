package savi.hcat.rest.service.mysql;

import java.io.File;
import java.io.FileInputStream;
import java.sql.*; // All we need for JDBC
import java.util.Properties;

public class XDBConnection {

        Connection connection = null;
        Statement sql = null; // Our statement to run queries with

        private static String driverName = "com.mysql.jdbc.Driver";

        Properties prop = new Properties();
        
        public XDBConnection() throws ClassNotFoundException, SQLException {
        	
        	try{
        		File file = new File("conf/mysql.properties");
        		if(file.exists()){
            		prop.load(new FileInputStream("conf/mysql.properties"));               	            		
                    Class.forName(driverName); // load the driver
                    String hostname = prop.getProperty("hostname");
                    String dbName = prop.getProperty("dbname");
                    String username = prop.getProperty("username");
                    String password = prop.getProperty("password");
                    
                    connection = DriverManager.getConnection("jdbc:mysql://"+hostname+"/"+dbName, username, password); // connect
                    connection.setAutoCommit(true);

                    // create a statement that we can use later
                    sql = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                                    ResultSet.CONCUR_UPDATABLE);     

        		}else{
        			throw new Exception("Load mysql.properties wrong");
        		} 		
        		
        		
        	}catch(Exception e){
        		e.printStackTrace();
        	}

        }

        public void close() {
                try {
                        connection.close();
                } catch (SQLException e) {
                }
        }
        
        
        

        public Connection getConnection() {
			return connection;
		}

		public ResultSet executeSearch(String sqlString) throws SQLException { // SELECT,
                                                                                                                                                        // .executeQuery()
        		
                return sql.executeQuery(sqlString);

                /**
                 * // sqlString for preparedStatement should contain '?' for multiple
                 * executions (passing arguments) // e.g:
                 * "insert into jdbc_demo values (?,?)" PreparedStatement preSQLSelect =
                 * connection.prepareStatement(sqlString); return
                 * preSQLSelect.executeQuery();
                 */
        }

        public int executeModify(String sqlString) throws SQLException { // INSERT,
                                                                                                                                                // DELETE,
                                                                                                                                                // UPDATE.
                                                                                                                                                // .executeUpdate()

                return sql.executeUpdate(sqlString);

                /**
                 * // sqlString for preparedStatement should contain '?' for multiple
                 * executions (passing arguments) // e.g:
                 * "insert into jdbc_demo values (?,?)" PreparedStatement preSQLSelect =
                 * connection.prepareStatement(sqlString); return
                 * preSQLSelect.executeUpdate();
                 */

        }
}