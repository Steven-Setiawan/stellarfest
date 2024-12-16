package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

	// Class databse penghubung ke db menggunakan prinsip design pattern singleton
	
	public static Database instance;
	
	public final String USERNAME = "root";
	public final String PASSWORD = "";
	public final String DATABASE = "stellarfest";
	public final String HOST = "localhost:3306";
	public final String CONNCECTION = String.format("jdbc:mysql://%s/%s",HOST, DATABASE);
	
	private Connection con;
	private Statement st;
	private PreparedStatement ps;
	public ResultSet rs;
	private ResultSetMetaData rsm;
	
	public static Database getInstance() {
		if(instance == null) {
			instance =  new Database();
		}
		return instance;
	}
	
	private Database() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(CONNCECTION, USERNAME, PASSWORD);
			st = con.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ResultSet execQuery(String query) {
		try {
			rs = st.executeQuery(query);
			rsm = rs.getMetaData();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return rs;
	}
	
	public void execUpdate(String query) {
		try {
			st.execute(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Connection getConnection() throws SQLException {
	    return DriverManager.getConnection(CONNCECTION, USERNAME, PASSWORD);
	}
	
	public PreparedStatement preparedStatment(String query){
		try {
			ps = con.prepareStatement(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ps;
	}
	
}
