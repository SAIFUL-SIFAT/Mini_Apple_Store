import javax.swing.*;
import java.sql.*;
import java.sql.Connection;
import java.util.function.DoubleFunction;

public class accountFunc
{
	public static int isLoggedin(String username, String password)
    {
        Connection connection = dbFunc.getConnection("db/database.db");
        PreparedStatement preparedStatement;
        ResultSet result;
        String query = "SELECT id FROM users WHERE username = ? AND password = ?";
        int id = 0;

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            result = preparedStatement.executeQuery();  //called if return is needed
            id = result.getInt("id");
            connection.close();
            System.out.println("database closed");
			System.out.println("ID: " + id);

        } catch (SQLException e) {e.printStackTrace();}
        
        return id;
    }   
	public static void signup(String username,String full_name,String email,String phone,String address,String pass)
	{
			Connection connection = dbFunc.getConnection("db/database.db");
			PreparedStatement preparedStatement;
			String query = "INSERT INTO users (username, full_name, email, phone, address, password) VALUES (?,?,?,?,?,?)";
			
			try {

				preparedStatement = connection.prepareStatement(query);
				preparedStatement.setString(1, username);
				preparedStatement.setString(2, full_name);
				preparedStatement.setString(3, email);
				preparedStatement.setString(4, phone);
				preparedStatement.setString(5, address);
				preparedStatement.setString(6, pass);
				preparedStatement.executeUpdate(); 
				connection.close();
				System.out.println("database closed");

			} catch (SQLException e) {e.printStackTrace();}
	}
	
	public static void resetPassword(String username, String password)
	{
			Connection connection = dbFunc.getConnection("db/database.db");
			PreparedStatement preparedStatement;
			String query = "UPDATE users SET password = ? WHERE username = ?";
			
			try {

				preparedStatement = connection.prepareStatement(query);
				preparedStatement.setString(1, password);
				preparedStatement.setString(2, username);
				preparedStatement.executeUpdate(); 
				connection.close();
				System.out.println("database closed");

			} catch (SQLException e) {e.printStackTrace();}
	}
	public static int isUser(String username)
	{
		Connection connection = dbFunc.getConnection("db/database.db");
		PreparedStatement preparedStatement;
		ResultSet result;
		String query = "SELECT id FROM users WHERE username = ?";
		int id = 0;

		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, username);
			result = preparedStatement.executeQuery();  //called if return is needed
			id = result.getInt("id");
			connection.close();
			System.out.println("database closed");
			System.out.println("ID: " + id);

		} catch (SQLException e) {e.printStackTrace();}

		return id;
	}
	
	public static String getUserDetail(int id, String infoColumn)
	{
		Connection connection = dbFunc.getConnection("db/database.db");
		PreparedStatement preparedStatement;
		ResultSet result = null;
		String info = null;
		String query = "SELECT "+infoColumn+" FROM users WHERE id = ?";

		try {
			preparedStatement = connection.prepareStatement(query);
			//preparedStatement.setString(1, infoColumn);
			preparedStatement.setInt(1, id);
			result = preparedStatement.executeQuery();
			info = result.getString(infoColumn);

			result.close();
			connection.close();
			System.out.println("database closed");

		} catch (SQLException e) {e.printStackTrace();}

		return info;
	}
	public static void updateUserDetail(int id, String infoColumn, String info)
	{
		Connection connection = dbFunc.getConnection("db/database.db");
		PreparedStatement preparedStatement;
		String query = "UPDATE users SET " + infoColumn + " = ? WHERE id = ?";

		try {
			preparedStatement = connection.prepareStatement(query);
			//preparedStatement.setString(1, infoColumn);
			preparedStatement.setString(1, info);
			preparedStatement.setInt(2, id);
			preparedStatement.executeUpdate();
			connection.close();
			System.out.println("database closed");

		} catch (SQLException e) {e.printStackTrace();}
	}
	
	public static void updateUserDetails(String full_name, String email, String phone, String address)
	{
		Connection connection = dbFunc.getConnection("db/database.db");
		PreparedStatement preparedStatement;
		String query = "UPDATE users SET full_name = ?, email = ?, phone = ?, address = ? WHERE id = ?";

		try {

			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, full_name);
			preparedStatement.setString(2, email);
			preparedStatement.setString(3, phone);
			preparedStatement.setString(4, address);
			preparedStatement.setInt(5, Signin.id);
			preparedStatement.executeUpdate();
			connection.close();
			System.out.println("database closed");

		} catch (SQLException e) {e.printStackTrace();}
	}
	
	public static double getCredits()
	{
		Connection connection = dbFunc.getConnection("db/database.db");
		PreparedStatement preparedStatement;
		String query = "SELECT credits FROM users WHERE id = ?";
		ResultSet resultSet = null;
		double credits = 0;

		try {

			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, Signin.id);
			resultSet = preparedStatement.executeQuery();
			credits = resultSet.getDouble("credits");

			resultSet.close();
			connection.close();
			System.out.println("database closed");

		} catch (SQLException e) {e.printStackTrace();}
		return credits;
	}
	public static String getDefaultPayment()
	{
		Connection connection = dbFunc.getConnection("db/database.db");
		PreparedStatement preparedStatement;
		String query = "SELECT default_payment FROM users WHERE id = ?";
		ResultSet resultSet = null;
		String payment = "";

		try {

			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, Signin.id);
			resultSet = preparedStatement.executeQuery();
			payment = resultSet.getString("default_payment");

			resultSet.close();
			connection.close();
			System.out.println("database closed");

		} catch (SQLException e) {e.printStackTrace();}
		return payment;
	}
	public static String getLastCardDigits(int id)
	{
		Connection connection = dbFunc.getConnection("db/database.db");
		PreparedStatement preparedStatement;
		String query = "SELECT last_four_digits_of_card FROM users WHERE id = ?";
		ResultSet resultSet = null;
		String digits = "";

		try {

			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			digits = resultSet.getString("last_four_digits_of_card");

			resultSet.close();
			connection.close();
			System.out.println("database closed");

		} catch (SQLException e) {e.printStackTrace();}
		return digits;
	}
	public static void setDefaultPaymentMethod(String paymentMethod)
	{
		Connection connection = dbFunc.getConnection("db/database.db");
		PreparedStatement preparedStatement;
		String query = "UPDATE users SET default_payment = ? WHERE id = ?";

		try {

			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, paymentMethod);
			preparedStatement.setInt(2, Signin.id);
			preparedStatement.executeUpdate();
			connection.close();
			System.out.println("database closed");

		} catch (SQLException e) {e.printStackTrace();}
	}
	public static void setCardInfo(int id, String number, String expiry, String cvv, boolean isUpdate)
	{
		Connection connection = dbFunc.getConnection("db/database.db");
		PreparedStatement preparedStatement;

		String query;
		try {
			if(isUpdate)
			{
				query = "UPDATE cards SET card_number = ?, expiry = ?, cvv = ? WHERE id = ?";
				preparedStatement = connection.prepareStatement(query);
				preparedStatement.setString(1, number);
				preparedStatement.setString(2, expiry);
				preparedStatement.setString(3, cvv);
				preparedStatement.setInt(4, id);
				preparedStatement.executeUpdate();
			}
			else {
				query = "INSERT INTO cards (id, card_number, expiry, cvv) VALUES (?,?,?,?)";
				preparedStatement = connection.prepareStatement(query);
				preparedStatement.setInt(1, id);
				preparedStatement.setString(2, number);
				preparedStatement.setString(3, expiry);
				preparedStatement.setString(4, cvv);
				preparedStatement.executeUpdate();
			}

			connection.close();
			System.out.println("database closed");

		} catch (SQLException e) {e.printStackTrace();}
	}
	public static ResultSet getOrderHistory(int id)
	{
		Connection connection = dbFunc.getConnection("db/database.db");
		PreparedStatement preparedStatement;
		String query = "SELECT * FROM orders WHERE id = ?";
		ResultSet resultSet = null;

		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, Signin.id);
			resultSet = preparedStatement.executeQuery();
			//resultSet.close();
			//connection.close();
			System.out.println("database closed");

		} catch (SQLException e) {e.printStackTrace();}
		return resultSet;
	}
	
	public static void adminSignup(String adminame,String password)
	{
			Connection connection = dbFunc.getConnection("db/database.db");
			PreparedStatement preparedStatement;
			String query = "INSERT INTO admins (adminName, adminPass) VALUES (?,?)";
			
			try {
				preparedStatement = connection.prepareStatement(query);
				preparedStatement.setString(1, adminame);
				preparedStatement.setString(2, password);
				preparedStatement.executeUpdate(); 
				connection.close();
				System.out.println("database closed");

			} catch (SQLException e) {e.printStackTrace();}
	}
	
	public static int isAdmin(String adminame)
	{
		Connection connection = dbFunc.getConnection("db/database.db");
		PreparedStatement preparedStatement;
		ResultSet result;
		String query = "SELECT id FROM admins WHERE adminName = ?";
		int id = 0;

		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, adminame);
			result = preparedStatement.executeQuery();  //called if return is needed
			id = result.getInt("id");
			connection.close();
			System.out.println("database closed");
			System.out.println("ID: " + id);

		} catch (SQLException e) {e.printStackTrace();}

		return id;
	}
	
	public static double redeemCard(int id, String cardNumber)
	{
		Connection connection = dbFunc.getConnection("db/database.db");
		PreparedStatement preparedStatement;

		String query = null;
		ResultSet resultSet = null;

		double total = 0;

		try {
			query = "SELECT * FROM gift_cards WHERE card_number = ?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, cardNumber);
			resultSet = preparedStatement.executeQuery();

			Double amount = resultSet.getDouble("amount");

			if(resultSet.next())
			{
				Double currentBalance = Double.parseDouble(accountFunc.getUserDetail(Signin.id, "credits"));

				query = "UPDATE users SET credits = ? WHERE id = ?";

				preparedStatement = connection.prepareStatement(query);
				//preparedStatement.setString(1, infoColumn);
				total = currentBalance + amount;
				preparedStatement.setString(1, String.valueOf(total));
				preparedStatement.setInt(2, id);
				preparedStatement.executeUpdate();

				query = "INSERT INTO orders (id, product_id, title, price, payment_method) VALUES (?,?,?,?,?)";
				preparedStatement = connection.prepareStatement(query);
				preparedStatement.setInt(1, id);
                preparedStatement.setInt(2, -1);
                preparedStatement.setString(3, (amount + "$ Gift Card"));
                preparedStatement.setDouble(4, amount);
                preparedStatement.setString(5, "gift_card");
				preparedStatement.executeUpdate();

				query = "DELETE FROM gift_cards WHERE gift_card_id = ?";
				preparedStatement = connection.prepareStatement(query);
				preparedStatement.setInt(1, resultSet.getInt("gift_card_id"));
				preparedStatement.executeUpdate();


			}
			resultSet.close();
			connection.close();
			System.out.println("database closed");

		} catch (SQLException e) {e.printStackTrace();}

		return total;
	}
	
	public static void updateUserPassword(String pass)
	{
		Connection connection = dbFunc.getConnection("db/database.db");
		PreparedStatement preparedStatement;
		String query = "UPDATE users SET password = ? WHERE id = ?";

		try {

			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, pass);
			preparedStatement.setInt(2, Signin.id);
			preparedStatement.executeUpdate();
			connection.close();
			System.out.println("database closed");

		} catch (SQLException e) {e.printStackTrace();}
	}
	public static ResultSet getUsers()
	{
		Connection connection = dbFunc.getConnection("db/database.db");
		PreparedStatement preparedStatement;
		String query = "SELECT * FROM users";
		ResultSet resultSet = null;

		try {
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			System.out.println("database closed");

		} catch (SQLException e) {e.printStackTrace();}
		return resultSet;
	}

	public static ResultSet getCards()
	{
		Connection connection = dbFunc.getConnection("db/database.db");
		PreparedStatement preparedStatement;
		String query = "SELECT * FROM gift_cards";
		ResultSet resultSet = null;

		try {
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			System.out.println("database closed");

		} catch (SQLException e) {e.printStackTrace();}
		return resultSet;
	}

}