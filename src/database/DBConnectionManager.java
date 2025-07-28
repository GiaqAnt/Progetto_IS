package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class DBConnectionManager {
	
	public static String url = "jdbc:mysql://localhost:3306/";
	public static String dbName = "mydb";
	public static String driver = "com.mysql.cj.jdbc.Driver";
	public static String userName = "root"; 
	public static String password = "password";
	
	
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		
		Connection conn = null;		
		Class.forName(driver); 
		
		conn = DriverManager.getConnection(url+dbName,userName,password);
		
		return conn;
	}
	
	public static void closeConnection(Connection c) throws SQLException {
		
		c.close();
	}
	
	
	
	public static ResultSet selectQuery(String query) throws ClassNotFoundException, SQLException {
		
		
		
		Connection conn = getConnection();
		
		
		Statement statment = conn.createStatement();
		
		ResultSet ret = statment.executeQuery(query); 
		
		
		return ret;
	}
	
	public static int updateQuery(String query) throws ClassNotFoundException, SQLException {
		
		Connection conn = getConnection();
		Statement statement = conn.createStatement();
		int ret = statement.executeUpdate(query);
		conn.close();
		return ret;
	}
	
	public static Integer updateQueryReturnGeneratedKey(String query) throws ClassNotFoundException, SQLException {
		Integer ret = null;
		
		Connection conn = getConnection();
		Statement statement = conn.createStatement();
		statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
		
		ResultSet rs = statement.getGeneratedKeys();
		if (rs.next()){
		    ret = rs.getInt(1);
		}
		
		conn.close();
		
		return ret;
	}
	
	public static int updateQuerySolution(String query, byte[] contenuto,LocalDate dataConsegna, int idTask, String emailStudente)
	        throws ClassNotFoundException, SQLException {
		System.out.println(dataConsegna);
	    int ret = -1; // valore di default se nessuna chiave generata

	    try (Connection conn = getConnection();
	         PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

	        ps.setBytes(1, contenuto);
	        ps.setDate(2, java.sql.Date.valueOf(dataConsegna));
	        ps.setInt(3, idTask);
	        ps.setString(4, emailStudente);

	        ps.executeUpdate();

	        try (ResultSet rs = ps.getGeneratedKeys()) {
	            if (rs.next()) {
	                ret = rs.getInt(1);
	            }
	        }
	    }

	    return ret;
	}

}