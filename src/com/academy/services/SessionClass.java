package com.academy.services;

import java.util.*;
import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.*;
import javax.servlet.http.*;

import com.academy.bean.User;
import com.academy.orm.DBConnection;

import java.security.*;
import java.sql.Connection;
import java.sql.SQLException;

public class SessionClass extends HttpServlet {

	private static final long serialVersionUID = -2128122335811219481L;

	public void doGet(HttpServletRequest request, HttpServletResponse res) throws IOException {
		HttpSession session = request.getSession(false);
		String userLogin =  request.getRemoteUser().split("\\\\")[1];
		
		if(request.getParameter("key")!=null)
		{
			request.getSession().invalidate();
			
		}
		else
		{
			try {
				if(session==null)
				{
					session = request.getSession(true);
					createSessionAndSetValues(session,userLogin,request, res);
				}
				else
				{
					createSessionAndSetValues(session,userLogin,request, res);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void createSessionAndSetValues(HttpSession session, String userLogin, HttpServletRequest request, HttpServletResponse res) throws SQLException, ServletException, IOException {
		// TODO Auto-generated method stub
		User presentUser = new User();
		presentUser = presentUser.getUser(userLogin);
		session.setAttribute("userLogin", userLogin);
		session.setAttribute("isAdmin", presentUser.isAdmin());
		session.setAttribute("type", presentUser.getType());
		session.setAttribute("empCode", presentUser.getEmpCode());
		
		RequestDispatcher disp = request.getRequestDispatcher("index.jsp");
		disp.forward(request, res);
	}
}