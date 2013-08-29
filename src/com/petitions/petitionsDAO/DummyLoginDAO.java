package com.petitions.petitionsDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.petitions.Login_Field;
import com.petitions.util.SQLConnection;

public class DummyLoginDAO {
	public Login_Field validateUser(String username, String password) throws Exception {
		Login_Field loginField = new Login_Field();
		Connection connection = null;
		try {
			connection = SQLConnection.getConnection();
			
			String url = "select loginName, loginPassword, role, netId from login_fields where loginName = ? and loginPassword = ? ";
			PreparedStatement statement = connection.prepareStatement(url); 
			statement.setString(1, username);
			statement.setString(2, password);
			ResultSet result = statement.executeQuery();
		
			while(result.next()) {
				loginField.setLoginName(result.getString("loginName")) ;
				loginField.setRole(result.getString("role")) ;
				loginField.setNetId(result.getInt("netId"));
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new Exception();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception();
		}
		
		return loginField;
	}
}
