package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class Payment {
	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/paf_ca", "root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public String insertPayment(String name, String No, String type, String bank, String tot)

	{
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " insert into payments(`p_id`,`NameOnCard`,`CardNo`,`cardType`,`bank`,`totAmount`)"
					+ " values (?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, name);
			preparedStmt.setDouble(3, Double.parseDouble(No));
			preparedStmt.setString(4, type);
			preparedStmt.setString(5, bank);
			preparedStmt.setDouble(6, Double.parseDouble(tot));

			// execute the statement
			preparedStmt.execute();
			con.close();
			String newPayments = readPayments();
			output = "{\"status\":\"success\", \"data\": \"" + newPayments + "\"}";

		} catch (Exception e) {

			output = "{\"status\":\"error\", \"data\":" + " \"Error while inserting the item.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String readPayments() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border=\"1\"><tr><th>Name on card</th><th>Card No</th><th>Card type</th><th>bank</th><th>Total amount</th><th>Update</th><th>Remove</th></tr>";
			String query = "select * from payments";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String p_id = Integer.toString(rs.getInt("p_id"));
				String NameOnCard = rs.getString("NameOnCard");
				String CardNo = Integer.toString(rs.getInt("CardNo"));
				String cardType = rs.getString("cardType");
				String bank = rs.getString("bank");
				String totAmount = Double.toString(rs.getDouble("totAmount"));
				

				// Add into the html table

				output += "<tr><td><input id='hidItemIDUpdate' name='hidItemIDUpdate' type='hidden' value='" + p_id
						+ "'>" + NameOnCard +"</td>";
				output += "<td>" + CardNo + "</td>";
				output += "<td>" + cardType + "</td>";
				output += "<td>" + bank + "</td>";
				output += "<td>" + totAmount + "</td>";
				

				// buttons
				output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary'></td><td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-p_id='"
						+ p_id + "'></td></tr>";

			}
			con.close();
			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the payments.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String updatePayment(String ID, String name, String No, String type, String bank, String tot)

	{
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
			// create a prepared statement
			String query = "UPDATE payments SET NameOnCard=?,CardNo=?,cardType=?,bank=?,totAmount=? WHERE p_id=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setString(1, name);
			preparedStmt.setDouble(2, Double.parseDouble(No));
			preparedStmt.setString(3, type);
			preparedStmt.setString(4, bank);
			preparedStmt.setDouble(5, Double.parseDouble(tot));
			

			preparedStmt.setInt(6, Integer.parseInt(ID));

			// execute the statement
			preparedStmt.execute();
			con.close();

			String newPayments = readPayments();
			output = "{\"status\":\"success\", \"data\": \"" + newPayments + "\"}";

		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\":  " + " \"Error while updating the Payment.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String deletePayment(String p_id) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}
			// create a prepared statement
			String query = "delete from payments where p_id=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(p_id));
			// execute the statement
			preparedStmt.execute();
			con.close();

			String newPayments = readPayments();
			output = "{\"status\":\"success\", \"data\": \"" + newPayments + "\"}";

		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\":\"Error while deleting the payment.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}

}
