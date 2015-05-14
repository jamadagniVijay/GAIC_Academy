package com.academy.services;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.RestoreAction;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.academy.orm.DBConnection;
import com.academy.orm.ResultSetConverter;

import org.apache.cxf.jaxrs.ext.MessageContext;


@Path ("/myServices")
public class AcademyServices {


	/*@Context 
	private MessageContext context;*/

	@GET
	@Path("/getMyRequests/{empCode}")
	public String getMyRequests (@PathParam(value = "empCode") int empCode) throws SQLException, JSONException
	{
		System.out.println("<!-------------my requested trainings------------->");
		JSONArray arr = new JSONArray();

		Connection connToComdB = new DBConnection().dbConnect( "com","com123");
		CallableStatement proc = connToComdB.prepareCall("{ call dbo.upMyRequestedTraining(?) }");
		proc.setInt(1, empCode);
		proc.execute();

		arr = new ResultSetConverter().convert(proc.getResultSet());
		System.out.println(arr.toJSONString());

		connToComdB.close();
		return arr.toJSONString();
	}
	
	@GET
	@Path("/getMyActive/{empCode}")
	public String getMyActive (@PathParam(value = "empCode") int empCode) throws SQLException, JSONException
	{

		JSONArray arr = new JSONArray();

		Connection connToComdB = new DBConnection().dbConnect( "com","com123");
		CallableStatement proc = connToComdB.prepareCall("{ call dbo.upGetMyActiveTrainings(?) }");
		proc.setInt(1, empCode);
		proc.execute();

		arr = new ResultSetConverter().convert(proc.getResultSet());
		System.out.println(arr.toJSONString());

		connToComdB.close();
		return arr.toJSONString();
	}

	@GET
	@Path("/getAllActiveTrainings")
	public String getAllActiveTrainings() throws SQLException
	{
		/*select trngs.trainid,trngs.name, trngs.coordinators, trainHist.startDt,trainHist.endDt, trainHist.trainers
		from dbo.trainings as trngs, dbo.trainingHist as trainHist
		where trngs.trainid = trainHist.trainrefid*/
		JSONArray arr = new JSONArray();
		JSONObject obj = null;

		Connection connToComdB = new DBConnection().dbConnect( "com","com123");
		CallableStatement proc = connToComdB.prepareCall("{ call dbo.upGetAllActiveTrainings() }");
		proc.execute();

		ResultSet rs = proc.getResultSet();
		SimpleDateFormat ft = 
				new SimpleDateFormat ("dd-MM-yyyy hh:mm:ss");
		while(rs.next())
		{
			obj =  new JSONObject();
			obj.put("trainId", rs.getInt("trainid"));
			obj.put("name", rs.getString("name"));
			obj.put("coordinators", rs.getString("coordinators"));
			obj.put("startDt", ft.format(rs.getTimestamp("startDt")).toString());
			obj.put("endDt", ft.format(rs.getTimestamp("endDt")).toString());
			obj.put("trainers", rs.getString("trainers"));
			obj.put("isCancelled", rs.getString("isCancelled"));
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
		connToComdB.close();
		return arr.toJSONString();
	}

	@GET
	@Path("/getRelaventTrainings/{empCode}")
	public String getRelaventTrainings (@PathParam(value = "empCode") int empCode) throws SQLException, JSONException
	{
		JSONArray arr = new JSONArray();

		Connection connToComdB = new DBConnection().dbConnect( "com","com123");
		CallableStatement proc = connToComdB.prepareCall("{ call dbo.upSelTrainings(?) }");
		proc.setInt(1, empCode);
		proc.execute();

		arr = new ResultSetConverter().convert(proc.getResultSet());
		System.out.println(arr.toJSONString());

		connToComdB.close();
		return arr.toJSONString();
	}
	
	@GET
	@Path("/getRelaventRequests/{trainId}")
	public String getRelaventRequests (@PathParam(value = "trainId") int trainId, @QueryParam(value="_") String pathParam) throws SQLException, JSONException
	{
		JSONArray arr = new JSONArray();
		
		System.out.println(trainId);
		
		Connection connToComdB = new DBConnection().dbConnect( "com","com123");
		CallableStatement proc = connToComdB.prepareCall("{ call dbo.upGetRequests(?) }");
		proc.setInt(1, trainId);
		proc.execute();

		arr = new ResultSetConverter().convert(proc.getResultSet());
		System.out.println(arr.toJSONString());

		connToComdB.close();
		return arr.toJSONString();
	}

	@POST
	@Path("/requestTrainId/{trainingId}")
	@Produces("text/plain")
	public String requestTainId (@Context HttpServletRequest servletRequest, @PathParam(value = "trainingId") int trainingId) throws SQLException, JSONException
	{	
		if(servletRequest.getSession().getAttribute("userLogin")!=null)
		{
			int empCode = (Integer) servletRequest.getSession(false).getAttribute("empCode");
			Connection connToComdB = new DBConnection().dbConnect( "com","com123");
			CallableStatement proc = connToComdB.prepareCall("{ call dbo.upInsRequest(?,?,?) }");
			proc.setInt(1,trainingId);
			proc.setInt(2, empCode);
			proc.setString(3, (String)servletRequest.getSession().getAttribute("userLogin"));
			proc.execute();
	
			System.out.println("submitted for the empCode "+trainingId+" "+empCode);
			connToComdB.close();
		//return arr.toJSONString();
		}
		else
		{
			return  "sesssionTimeOut";
		}
		/*System.out.println();
		return "submitted for the empCode "+empCode+" training id : "+trainingId;*/
		return  "success";
	}

	@GET
	@Path("/createThisTraining")
	public String createThisTraining (@Context HttpServletRequest servletRequest,@QueryParam("txtCategory") String txtCategory, @QueryParam("txtTraining") String txtTraining,@QueryParam("txtCoordinators")String txtCoordinators) throws SQLException, JSONException
	{	
		System.out.println("inserted "+txtCategory+" "+txtTraining+" "+txtCoordinators);
		String result =null;
		if(servletRequest.getSession(false).getAttribute("userLogin")!=null)
		{
			Connection connToComdB = new DBConnection().dbConnect( "com","com123");
			CallableStatement proc = connToComdB.prepareCall("{ call dbo.upCreateThisTraining(?,?,?) }");
			proc.setString(1, txtCategory);
			proc.setString(2, txtTraining);
			proc.setString(3, txtCoordinators);
			proc.execute();
	
			connToComdB.close();
	
			result =  "insertedTraining";
		}
		else
		{
			result = "sesssionTimeOut";
		}
		return result;
	}
	
	
	
	
	@GET
	@Path("/getAllTrainings")
	public String getAllTrainings () throws SQLException, JSONException
	{	

		JSONArray arr = new JSONArray();

		Connection connToComdB = new DBConnection().dbConnect( "com","com123");
		CallableStatement proc = connToComdB.prepareCall("{ call dbo.upGetAllTrainings }");

		proc.execute();

		arr = new ResultSetConverter().convert(proc.getResultSet());
		System.out.println(arr.toJSONString());

		connToComdB.close();
		return arr.toJSONString();
	}



	@POST
	@Path("/editThisTraining")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces("text/plain")
	public String editThisTraining (@Context HttpServletRequest servletRequest,@FormParam("sentObj") String sentObj) throws SQLException, JSONException, ServletException, IOException
	{	
		String currentEmployee = (String) servletRequest.getSession(false).getAttribute("userLogin");

		String result=null;
		org.json.JSONObject jsonObj = new org.json.JSONObject(sentObj);
		if(currentEmployee!=null )
		{
			if(!(jsonObj.getString("changedValue").equals(jsonObj.getString("initalCellValue"))))
			{
				//call user procedure to alter table.
				String ColumnName = jsonObj.getString("sColumnTitle");
				if(jsonObj.getString("sColumnTitle").equals("Training"))
				{
					ColumnName = "Name";
				}
				if(jsonObj.getString("sColumnTitle").equals("Coordinator"))
				{
					ColumnName+='s';
				}
				Connection connToComdB = new DBConnection().dbConnect( "com","com123");
				CallableStatement proc = connToComdB.prepareCall("{ call dbo.upUpdateThisField(?,?,?) }");
				proc.setString(1,ColumnName);
				proc.setString(2, jsonObj.getString("changedValue"));
				proc.setString(3,  String.valueOf((jsonObj.getInt("TrainId"))));
				proc.execute();

				connToComdB.close();

				System.out.println("edted the column name "+jsonObj.getString("sColumnTitle")+" with the TrainId "+jsonObj.getInt("TrainId")+" by "+currentEmployee +" from "+jsonObj.getString("initalCellValue")+" to "+jsonObj.getString("changedValue"));
			}
			return "success";
		}
		else
		{
			result = "sesssionTimeOut";
		}
		return result;
	}

	@POST
	@Path("/deleteThisTraining")
	public String deleteThisTraining (@Context HttpServletRequest servletRequest,@FormParam("sentObj") String sentObj) throws SQLException, JSONException
	{	
		String currentEmployee = (String) servletRequest.getSession(false).getAttribute("userLogin");
		org.json.JSONObject jsonObj = new org.json.JSONObject(sentObj);


		//call user procedure to alter table.

		String result=null;

		if(currentEmployee!=null && (jsonObj.getInt("TrainId")!=0))
		{
			Connection connToComdB = new DBConnection().dbConnect( "com","com123");
			CallableStatement proc = connToComdB.prepareCall("{ call dbo.upDeleteTraining(?) }");
			proc.setString(1,String.valueOf(jsonObj.getInt("TrainId")));
			proc.execute();

			connToComdB.close();
			System.out.println("DELETED training with id "+jsonObj.getInt("TrainId"));
			return "success";
		}
		else
		{
			result = "sesssionTimeOut";
		}
		return result;
	}
	@POST
	@Path("/manageTrainingSchedule")
	public String cancelTraining (@Context HttpServletRequest servletRequest,@FormParam("sentObj") String sentObj) throws SQLException, JSONException, ParseException
	{
		
		String currentEmployee = (String) servletRequest.getSession(false).getAttribute("userLogin");
		org.json.JSONObject jsonObj = new org.json.JSONObject(sentObj);


		//call user procedure to alter table.

		String result=null;
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
		if(currentEmployee!=null && (jsonObj.getInt("TrainId")!=0))
		{
				System.out.println(jsonObj.length());
				Connection connToComdB = new DBConnection().dbConnect( "com","com123");
				CallableStatement proc = connToComdB.prepareCall("{ call dbo.upManageSchedule(?,?,?,?) }");
				
				proc.setInt("trainingRefId",Integer.parseInt(String.valueOf(jsonObj.getInt("TrainId"))));
				
				proc.setString ("isCancelled", String.valueOf(jsonObj.getString("isCancelled")));
				
				Date parsed = formatter.parse(String.valueOf(jsonObj.getString("StartDt")));
		        java.sql.Date sqlDate = new java.sql.Date(parsed.getTime());
				proc.setDate("startDt", sqlDate);
				
				parsed = formatter.parse(String.valueOf(jsonObj.getString("EndDt")));
				sqlDate = new java.sql.Date(parsed.getTime());
				proc.setDate("endDt",sqlDate);
				
				proc.execute();

				connToComdB.close();
				System.out.println("Modified training with id "+jsonObj.getInt("TrainId"));

			
			return "success";
		}
		else
		{
			result = "sesssionTimeOut";
			
		}
		return result;
	}
	
	
	@GET
	@Path("/cancelMyRequestedTraining/{trainId}")
	public String cancelMyRequests (@Context HttpServletRequest servletRequest, @PathParam(value="trainId") int trainId) throws SQLException, JSONException
	{
		String result = "";
		System.out.println("started cancelling request");
		if(servletRequest.getSession().getAttribute("userLogin")!=null)
		{
			int empCode = (Integer) servletRequest.getSession(false).getAttribute("empCode");
			Connection connToComdB = new DBConnection().dbConnect( "com","com123");
			CallableStatement proc = connToComdB.prepareCall("{ call dbo.upCancelMyRequestedTraining(?,?) }");
			proc.setInt(1,empCode);
			proc.setInt(2,trainId);
			proc.execute();
	
			System.out.println("cancelled request "+trainId+" by"+empCode);
			connToComdB.close();
			return  "success";
		//return arr.toJSONString();
		}
		else
		{
			return  "sesssionTimeOut";
		}
		/*System.out.println();
		return "submitted for the empCode "+empCode+" training id : "+trainingId;*/
	}
}