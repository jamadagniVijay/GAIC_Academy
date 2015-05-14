package com.academy.bean;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.academy.orm.DBConnection;

public class User {
	private String UserLogin, Type; 
	private int EmpCode;
	private boolean  IsAdmin;
	public String getUserLogin() {
		return UserLogin;
	}
	public void setUserLogin(String userLogin) {
		UserLogin = userLogin;
	}
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}
	public int getEmpCode() {
		return EmpCode;
	}
	public void setEmpCode(int empCode) {
		EmpCode = empCode;
	}
	public boolean isAdmin() {
		return IsAdmin;
	}
	public void setIsAdmin(boolean isAdmin) {
		IsAdmin = isAdmin;
	}
	
	public User() {
		super();
	}
	public User(String userLogin, String type, int empCode, boolean isAdmin) {
		super();
		UserLogin = userLogin;
		Type = type;
		EmpCode = empCode;
		IsAdmin = isAdmin;
	}
	
	public User getUser (String userLogin) throws SQLException
	{
		User presentUser = null;
		Connection connToComdB = new DBConnection().dbConnect( "com","com123");
		CallableStatement proc = connToComdB.prepareCall("{ call dbo.upSelValidateUser(?) }");
		proc.setString(1, userLogin);
		proc.execute();
		
		ResultSet rs = proc.getResultSet();
		
		while (rs.next()) {
			 String UserLogin = rs.getString("UserLogin"); 
			 String Type = rs.getString("Type"); 
			 int EmpCode = rs.getInt("EmpCode");
			 boolean  IsAdmin = rs.getBoolean("isAdmin");
			 presentUser = new User(UserLogin, Type, EmpCode, IsAdmin);
		}
		connToComdB.close();
		return presentUser;
	}
	
	
	
}
