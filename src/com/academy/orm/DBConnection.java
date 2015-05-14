package com.academy.orm;

import java.sql.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;	
public class DBConnection
{
	public static Connection dbConnect(String db_userid,String db_password)
	{
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver" );
			String connectionUrl = "jdbc:sqlserver://10.99.42.100:1433;databaseName=Com;user="+db_userid+";password="+db_password+";";
			Connection conn = DriverManager.getConnection(connectionUrl);
			/*System.out.println("connected");
			Statement statement = conn.createStatement();
			String queryString = "select * from sysobjects where type='u'";
			ResultSet rs = statement.executeQuery(queryString);
			while (rs.next()) {
				System.out.println(rs.getString(1));
			}*/
			return  conn;
		} catch (Exception e) {
			return null;
			//e.printStackTrace();
		}
	}



	public static void main(String[] args) throws SQLException
	{
		//new DBConnection().dbConnect( "com","com123");

		JSONArray arr = new JSONArray();
		JSONObject obj = null;
		
		Connection connToComdB = new DBConnection().dbConnect( "com","com123");
		Statement stmt = connToComdB.createStatement();
		ResultSet rs=  stmt.executeQuery("select trngs.trainid,trngs.name, trngs.coordinators, trainHist.startDt,trainHist.endDt, trainHist.trainers "
						+ "from dbo.trainings as trngs, dbo.trainingHist as trainHist "
						+ "where trngs.trainid = trainHist.trainrefid");
		while(rs.next())
		{
			obj =  new JSONObject();
			obj.put("name", rs.getString("name"));
			obj.put("coordinators", rs.getString("coordinators"));
			obj.put("startDt", rs.getDate("startDt").toString());
			obj.put("endDt", rs.getDate("endDt").toString());
			obj.put("trainers", rs.getString("trainers"));
			PreparedStatement pstmt = connToComdB.prepareStatement("select count(case when dbo.requests.isactive=0 then 1 end)  as approved,  count(case when dbo.requests.isactive=1 then 1 end) as pending from dbo.requests where dbo.requests.trainrefid=?"); 
			pstmt.setInt(1, rs.getInt("trainid"));
			ResultSet countSet = pstmt.executeQuery();
			while(countSet.next())
			{
				obj.put("enroll_pending", countSet.getInt("approved")+"/"+countSet.getInt("pending"));
			}
			
			//ResultSet countRes = stmt.executeQuery("select count(case when dbo.requests.isactive=0 then 1 end),  count(case when dbo.requests.isactive=1 then 1 end) from dbo.requests where dbo.requests.trainrefid=");
			arr.add(obj);
		}
		System.out.println(arr.toJSONString());
		
	}
}
