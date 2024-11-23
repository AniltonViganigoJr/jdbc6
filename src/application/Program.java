package application;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import db.DB;
import db.DbException;

public class Program {

	public static void main(String[] args) {

		Connection conn = null;
		Statement statement = null;
		
		try {
			conn = DB.getConnection();
			conn.setAutoCommit(false);
			
			statement = conn.createStatement();
			
			int rows1 = statement.executeUpdate("UPDATE seller SET BaseSalary = 4000 WHERE DepartmentId = 1");
			
			if(true) {
				throw new SQLException("Fake Error!");
			}
			
			int rows2 = statement.executeUpdate("UPDATE seller SET BaseSalary = 7500 WHERE DepartmentId = 4");
			
			conn.commit();
			
			System.out.println("Rows1: " + rows1);
			System.out.println("Rows2: " + rows2);
			
		} catch (SQLException e) {
			try {
				conn.rollback();
				throw new DbException("Transaction rolled back! Caused by: " + e.getMessage());
			} catch (SQLException e1) {
				throw new DbException("Error trying to rollback! Caused by: " + e1.getMessage());
			}
		} finally {
			DB.closeStatement(statement);
			DB.closeConnection();
		}
	}
}