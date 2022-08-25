/* Decompiler 62ms, total 7093ms, lines 169 */
package com.scm.email.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.scm.email.serviceprovider.commonUtility;

import oracle.jdbc.OracleDriver;

public abstract class CarsWeekly implements Connection {
	Statement stmt = null;
	static String DB_URL;
	static String DB_USER_ID;
	static String DB_PASSWORD;
	static Connection con = null;
	static String delaytime;
	int row_count;
	private static String NotrunningStatus;

	public static Connection con() throws SQLException {
		commonUtility comm = new commonUtility();
		Properties prop = comm.readProp();
		DB_URL = prop.getProperty("dbHost");
		DB_USER_ID = prop.getProperty("dbUserID");
		DB_PASSWORD = prop.getProperty("dbPassword");

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(DB_URL.trim(), DB_USER_ID.trim(), DB_PASSWORD.trim());
			return con;
		} catch (Exception var3) {
			System.err.println("Exception in DB connection" + var3.getMessage());
			var3.getStackTrace();
			return null;
		}
	}

	public static void executeApiBatch() throws SQLException, InterruptedException, MessagingException
	{
		String CARS_ID = "";
		try {
			DriverManager.registerDriver(new OracleDriver());
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException var16) {
			var16.printStackTrace();
		}

		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		Date dateobj = new Date();
		System.out.println(df.format(dateobj));
		
		con = con();
		System.out.println("api calling...");

		String proc = "SELECT DECODE (Quote_conversion, 'Y', Appl_name || 'RQ', APPL_NAME)\n"
				+ "SUBSCRIBER,\n"
				+ "transaction_type,\n"
				+ " nvl(Sub_trx_type,' '),\n"
				+ "COUNT (request_id)\n"
				+ "TOTAL_NUMBER_OF_REQUESTS,\n"
				+ "SUM (submitted_rec_cnt)\n"
				+ "Total_lines_submitted,\n"
				+ "SUM (success_rec_cnt)\n"
				+ "Total_success_lines,\n"
				+ "SUM (FAILED_REC_CNT)\n"
				+ "Total_Failed_lines\n"
				+ "FROM apps.xxcss_cars_headers_stg\n"
				+ "WHERE 1 = 1\n"
				+ "--andappl_name in ('XXCSS_MMAT','XXCSS_PS','XXCSS_AMP')\n"
				+ "AND TRUNC (creation_date) >\n"
				+ "TRUNC (TRUNC (TO_DATE ('"+df.format(dateobj)+"', 'mm/dd/rrrr')) - 7)\n"
				+ "AND TRUNC (creation_date) <=\n"
				+ "TRUNC (TRUNC (TO_DATE ('"+df.format(dateobj)+"', 'mm/dd/rrrr')))\n"
				+ "GROUP BY DECODE (Quote_conversion, 'Y', Appl_name || 'RQ', APPL_NAME),\n"
				+ "transaction_type,\n"
				+ "Sub_trx_type\n"
				+ "ORDER BY 1";

//		System.out.println(proc);
		PreparedStatement ps = con.prepareStatement(proc);
		
		ResultSet rs1 = ps.executeQuery();
		Date date1 = new Date();
		int count=1;
		while(rs1.next()) {
			 CARS_ID = CARS_ID + "<tr><td>"+ count+"</td><td>" + rs1.getString(1) + "</td><td>" + rs1.getString(2) + "</td><td>"
						+ rs1.getString(3)+ "</td><td>"+ rs1.getString(4)+ "</td><td>"+rs1.getString(5)+ "</td><td>"
					    +rs1.getString(6)+ "</td><td>"+rs1.getString(7) + "</td></tr>";
			 count++;
		}
		

//		System.out.println("DATAS :" + CARS_ID);
		if (CARS_ID != "") {
			sendMail(CARS_ID);
			CARS_ID = "";
		} else {
			System.out.println(" Issue in getting status!");
			NotrunningStatus = "";
			sendMail(NotrunningStatus);
			
		}

		System.out.println("API CALLED SUCCESFULLY!");
		ps.close();
		new Date();
		long time1 = date1.getTime();
		Timestamp ts1 = new Timestamp(time1);
		System.out.println("Current Time Stamp: " + ts1);
		con.close();
	}

	public static void sendMail(String tableTag) throws MessagingException {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yy");
		String content;
		Date date = new Date();
		
		commonUtility com = new commonUtility();
		Properties prop = com.readProp();
		String fromEmail = prop.getProperty("fromEmail");
		String toEmail = prop.getProperty("toEmail");
		
		Properties props = new Properties();
		props.put("mail.smtp.host", "mail.cisco.com");
		props.put("mail.smtp.port", "25");
		Session session = Session.getInstance(props);
		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(fromEmail, false));
		msg.setRecipients(RecipientType.TO, InternetAddress.parse(toEmail));
		msg.setSubject("CSFPRD -  CARS Weekly Usage Report: " + formatter.format(date));
		msg.setContent("Test email to check mailer Alias", "text/html");
		msg.setSentDate(new Date());
		MimeBodyPart messageBodyPart = new MimeBodyPart();
		
		     String   style =  "style=\"background:#6D8FFF;padding:3.75pt 3.75pt 3.75pt 3.75pt\"";
		     String   classs = "class=\"MsoNormal\"><b><span style=\"mso-fareast-font-family:&quot;Times New Roman&quot;;color:black\"";
		
		if(tableTag.length()!=0) {
		 content = "<p>Hi Team,</p>"
				+"<h3 align=\"center\" style=\"text-align:center\"><u><span style=\"font-family:&quot;Verdana&quot;,sans-serif;\n"
				+ "mso-fareast-font-family:&quot;Times New Roman&quot;;color:#23261fd9\">CARS Usage Report\n"
				+ "- </span></u><u><span style=\"font-family:&quot;Verdana&quot;,sans-serif;\n"
				+ "mso-fareast-font-family:&quot;Times New Roman&quot;;color:red\">Weekly </span></u><span style=\"mso-fareast-font-family:&quot;Times New Roman&quot;\"><o:p></o:p></span></h3>"
				+ "<h2><strong><span style=\"color: #000080; background-color: #99cc00;\"></span></strong></h2>\n"
				+ "<table  align=\"center\" style=\"width: 621px; height: 33px;   border-color: black;\" border=\"2\">"
				+ "<tbody>"
				+ "<tr style=\"mso-yfti-irow:0;mso-yfti-firstrow:yes\" colspan=\"2\">\n"
				+ "  <td "+style+">\n"
				+ "  <p "+classs+">"
				+ "  Serial_No<o:p></o:p></span></b></p>\n"
				+ "  </td>\n"
				+ "  <td "+style+">\n"
				+ "  <p "+classs+">"
				+ "  SUBSCRIBER<o:p></o:p></span></b></p>\n"
				+ "  </td>\n"
				+ "  <td "+style+">\n"
				+ "  <p "+classs+">"
				+ "  TRANSACTION_TYPE<o:p></o:p></span></b></p>\n"
				+ "  </td>\n"
				+ "  <td "+style+">\n"
				+ "  <p "+classs+">"
				+ "  SUB_TRX_TYPE<o:p></o:p></span></b></p>\n"
				+ "  </td>\n"
				+ "  <td "+style+">\n"
				+ "  <p "+classs+">"
				+ "  TOTAL_NUMBER_OF_REQUESTS<o:p></o:p></span></b></p>\n"
				+ "  </td>\n"
				+ "  <td "+style+">\n"
				+ "  <p "+classs+">"
				+ "  TOTAL_LINES_SUBMITTED<o:p></o:p></span></b></p>\n"
				+ "  </td>\n"
				+ "  <td "+style+">\n"
				+ "  <p "+classs+">"
				+ "  TOTAL_SUCCESS_LINES<o:p></o:p></span></b></p>\n"
				+ "  </td>\n"
				+ "  <td "+style+">\n"
				+ "  <p "+classs+">"
				+ "  TOTAL_FAILED_LINES<o:p></o:p></span></b></p>\n"
				+ "  </td>\n"		
				+ "</tr>" + "</tbody>" + "<tbody>" + tableTag + "</tbody>"
				+ "</table><br><br><p>Thank You.</p>";}
		else {
			 content =  "<p>Hi Team,</p>"
					+"<h1 align=\"center\" style=\"text-align:center\"><u><span style=\"font-family:&quot;Verdana&quot;,sans-serif;\n"
					+ "mso-fareast-font-family:&quot;Times New Roman&quot;;color:#23261fd9\">"
					+ "DATA NOT AVAILABLE FOR :-" +formatter.format(date); 
		}
		messageBodyPart.setContent(content, "text/html");
		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);
		new MimeBodyPart();
		msg.setContent(multipart);
		Transport.send(msg);
		System.out.println("Email Sent Successfully !");
		System.out.println("FINAL TABLE TAG------>" );
		
	}


}
