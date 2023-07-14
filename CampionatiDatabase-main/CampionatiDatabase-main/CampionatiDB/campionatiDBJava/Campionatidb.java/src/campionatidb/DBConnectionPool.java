package campionatidb;
import java.sql.*;
import java.util.*;

public class DBConnectionPool {
	
		private static List<Connection> freeDbConnections;
		
		static {
			freeDbConnections=new LinkedList<Connection>();
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch(ClassNotFoundException e) {
				System.out.println("DB driver not found\n");
				System.out.println(e);
			}
		}
		
		private static Connection createDBConnection() throws SQLException{
			Connection newConnection=null;
			String ip="localhost";
			String port="3306";
			String db="campionati";
			String username="root";
			String password="Database2022";
			
			newConnection = DriverManager.getConnection("jdbc:mysql://"+ip+":"+port+"/"+db+"?serverTimezone=Europe/Rome", username, password);
			newConnection.setAutoCommit(false);
			return newConnection;
		}
		
		public static synchronized Connection getConnection() throws SQLException{
			Connection connection;
			
			if(!freeDbConnections.isEmpty()) {
				connection = (Connection) freeDbConnections.get(0);
				DBConnectionPool.freeDbConnections.remove(0);
				
				try {
					if(connection.isClosed())
						connection=DBConnectionPool.getConnection();
				}catch(SQLException e){
					;
				}
			}else connection = DBConnectionPool.createDBConnection();
			return connection;
		}
		public static synchronized void releaseConnection(Connection connection) {
			DBConnectionPool.freeDbConnections.add(connection);
		}
		
}
