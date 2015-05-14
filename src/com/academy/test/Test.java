package com.academy.test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

import org.json.simple.JSONObject;

import com.academy.orm.DBConnection;
public class Test 
{
	public static void main(String[] args) {


		String to = "vijay_r@hcl.com";
		String from = "vijay_r@hcl.com";
		String host = "10.139.11.48";
		String filename = "C:\\Documents and Settings\\eponcede\\Desktop\\controller.java";
		boolean debug = Boolean.valueOf(false).booleanValue();
		String msgText1 = "Sending a file.\n";
		String subject = "Sending a file";

		// create some properties and get the default Session
		Properties props = System.getProperties();
		props.put("mail.smtp.host", host);

		Session session = Session.getInstance(props, null);
		session.setDebug(debug);

		try {
			// create a message
			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(from));
			InternetAddress[] address = {new InternetAddress(to)};
			msg.setRecipients(Message.RecipientType.TO, address);
			msg.setSubject(subject);

			// create and fill the first message part
			MimeBodyPart mbp1 = new MimeBodyPart();
			mbp1.setText(msgText1);

			// create the second message part
			MimeBodyPart mbp2 = new MimeBodyPart();

			// attach the file to the message
			mbp2.attachFile(filename);

			// create the Multipart and add its parts to it
			Multipart mp = new MimeMultipart();
			mp.addBodyPart(mbp1);
			mp.addBodyPart(mbp2);

			// add the Multipart to the message
			msg.setContent(mp);

			// set the Date: header
			msg.setSentDate(new Date());

			// send the message
			Transport.send(msg);

		} catch (MessagingException mex) {
			mex.printStackTrace();
			Exception ex = null;
			if ((ex = mex.getNextException()) != null) {
				ex.printStackTrace();
			}
		} catch (IOException ioex) {
			ioex.printStackTrace();
		}
	}}
