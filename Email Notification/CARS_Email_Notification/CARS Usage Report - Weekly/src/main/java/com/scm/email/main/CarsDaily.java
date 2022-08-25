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

public abstract class CarsDaily implements Connection {
	Statement stmt = null;
	static String DB_URL;
	static String DB_USER_ID;
	static String DB_PASSWORD;
	static Connection con = null;
	static String delaytime;
	int row_count;
	static String RMA_ID = "";
	static String RMA_ID1 = "";

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
		 String CARS_ID    = "";
		 String REQUEST_ID = "";
		 String ERROR_CODE = "";
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
		String proc = "  SELECT transaction_type,\n"
				+ "         nvl(sub_trx_type,' '),\n"
				+ "         COUNT (1)                   Number_of_requests,\n"
				+ "         SUM (SUBMITTED_REC_CNT)     submitted_lines,\n"
				+ "         SUM (success_rec_cnt)       success_lines,\n"
				+ "         SUM (failed_rec_cnt)        failed_lines\n"
				+ "    FROM apps.xxcss_cars_headers_stg H\n"
				+ "   WHERE h.creation_date >=\n"
				+ "         TRUNC (TO_DATE ('"+df.format(dateobj)+"', 'mm/dd/rrrr'))\n"
				+ "GROUP BY transaction_type, sub_trx_type\n"
				+ "ORDER BY Number_of_requests DESC";

//		System.out.println(proc);
		PreparedStatement ps = con.prepareStatement(proc);
		
		ResultSet rs = ps.executeQuery();
		Date date1 = new Date();
		int count=1;
		while(rs.next()) {
			 CARS_ID = CARS_ID + "<tr><td>"+ count+"</td><td>" + rs.getString(1) + "</td><td>" + rs.getString(2) + "</td><td>"
						+ rs.getString(3)+ "</td><td>"+ rs.getString(4)+ "</td><td>"+rs.getString(5)+ "</td><td>"
					    +rs.getString(6) + "</td></tr>";
			 count++;
		}
		
//		System.out.println("DATAS :" + CARS_ID);
		
		String proc1 ="SELECT appl_name,request_id,submitted_Rec_cnt, cars_processing_time\n"
				+ "FROM\n"
				+ "(\n"
				+ "SELECT appl_name,hstg.request_id,submitted_Rec_cnt,\n"
				+ "ROUND((SYSDATE-hstg.creation_date) * 24 * 60,2) cars_processing_time\n"
				+ "FROM apps.xxcss_cars_headers_stg      hstg\n"
				+ "WHERE 1 = 1\n"
				+ "  and hstg.creation_date >= trunc(to_date('"+df.format(dateobj)+"','mm/dd/rrrr'))-7\n"
				+ "  AND hstg.status IN ('INPROGRESS')\n"
				+ ")  \n"
				+ "WHERE cars_processing_time>=120";
		

			PreparedStatement ps1 = con.prepareStatement(proc1);
		
			ResultSet rs1 = ps1.executeQuery();
			int count1=1;
			while(rs1.next()) {
				REQUEST_ID = REQUEST_ID + "<tr><td>"+ count1+"</td><td>" + rs1.getString(1) + "</td><td>" + rs1.getString(2) + "</td><td>"
							+ rs1.getString(3)+ "</td><td>"+ rs1.getString(4)+ "</td></tr>";
				 count1++;
			}
			
//			System.out.println("DATAS 1 :" + REQUEST_ID);
			
			String proc2 = "select /*+ PARALLEL(e 25) */ \n"
							+ "appl_name, error_code,SUBSTR(e.error_message,1,100) ERROR_MESSAGE ,COUNT(1) \n"
							+ "from apps.xxcss_cars_error_log e\n"
							+ "where e.last_update_DATE >= TO_dATE('"+df.format(dateobj)+"','MM/DD/YYYY')\n"
							+ "AND e.error_type='ERROR'\n"
							+ "and (error_code = 'XXCSS_CARS_API_ERROR'\n"
							+ " OR\n"
							+ " error_code like 'XXCSS_SFM%'\n"
							+ " OR\n"
							+ " ERROR_CODE IS NULL\n"
							+ " OR\n"
							+ " ERROR_MESSAGE IS NULL\n"
							+ " )\n"
							+ "and error_message not like '%XXCSS_SFM_json%Scanner%'\n"
							+ "GROUP BY e.appl_name, e.error_code,SUBSTR(e.error_message,1,100)\n"
							+ "order by count(1) desc";
			
			PreparedStatement ps2 = con.prepareStatement(proc2);
			
			ResultSet rs2 = ps2.executeQuery();
			int count2=1;
			while(rs2.next()) {
				ERROR_CODE = ERROR_CODE + "<tr><td>"+ count2+"</td><td>" + rs2.getString(1) + "</td><td>" + rs2.getString(2) + "</td><td>"
							+ rs2.getString(3)+ "</td><td>"+ rs2.getString(4)+ "</td></tr>";
				 count2++;
			}
			
//			System.out.println("DATAS 2 :" + ERROR_CODE);
			
			sendMail(CARS_ID ,REQUEST_ID,ERROR_CODE);


		System.out.println("API CALLED SUCCESFULLY!");
		ps.close();
		new Date();
		long time1 = date1.getTime();
		Timestamp ts1 = new Timestamp(time1);
		System.out.println("Current Time Stamp: " + ts1);
		con.close();
	}

	public static void sendMail(String tableTag ,String tableTag1 ,String tableTag2 ) throws MessagingException {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yy");
		StringBuffer content = new StringBuffer() ;
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
		msg.setSubject(" CSFPRD -  CARS Production Failure Report: " + formatter.format(date));
		msg.setContent("Test email to check mailer Alias", "text/html");
		msg.setSentDate(new Date());
		MimeBodyPart messageBodyPart = new MimeBodyPart();
		
		 String   style =  "style=\"background:#6D8FFF;padding:3.75pt 3.75pt 3.75pt 3.75pt\"";
	     String   classs = "class=\"MsoNormal\"><b><span style=\"mso-fareast-font-family:&quot;Times New Roman&quot;;color:black\"";

		if(tableTag.length()!=0) {
		 content .append( "<p>Hi Team,</p>"
				+"<h3 align=\"center\" style=\"text-align:center\"><u><span style=\"font-family:&quot;Verdana&quot;,sans-serif;"
				+ "mso-fareast-font-family:&quot;Times New Roman&quot;;color:#23261fd9\">CARS Production Failure Report "
				+ "- </span></u><u><span style=\"font-family:&quot;Verdana&quot;,sans-serif;"
				+ "mso-fareast-font-family:&quot;Times New Roman&quot;;color:red\">Daily </span></u><span style=\"mso-fareast-font-family:&quot;Times New Roman&quot;\"><o:p></o:p></span></h3>"
				+ "<h2><strong><span style=\"color: #000080; background-color: #99cc00;\"></span></strong></h2>"
				+ "<table  align=\"center\" style=\"width: 621px; height: 33px;   border-color: black;\" border=\"2\">"
				+ "<tbody>"
				+ "<tr style=\"mso-yfti-irow:0;mso-yfti-firstrow:yes\" colspan=\"2\">"
				+ "  <td "+ style+">"
				+ "  <p "+ classs+">"
				+ "  Sl No<o:p></o:p></span></b></p>"
				+ "  </td>"
				+ "  <td "+ style+">"
				+ "  <p "+ classs+">"
				+ "  TRANSACTION_TYPE<o:p></o:p></span></b></p>"
				+ "  </td>"
				+ "  <td "+ style+">"
				+ "  <p "+ classs+">"
				+ "  SUB_TRX_TYPE<o:p></o:p></span></b></p>"
				+ "  </td>"
				+ "  <td "+ style+">"
				+ "  <p "+ classs+">"
				+ "  NUMBER_OF_REQUESTS<o:p></o:p></span></b></p>"
				+ "  </td>"
				+ "  <td "+ style+">"
				+ "  <p "+ classs+">"
				+ "  SUBMITTED_LINES<o:p></o:p></span></b></p>"
				+ "  </td>"
				+ "  <td "+ style+">"
				+ "  <p "+ classs+">"
				+ "  SUCCESS_LINES<o:p></o:p></span></b></p>"
				+ "  </td>"
				+ "  <td "+ style+">"
				+ "  <p "+ classs+">"
				+ "  FAILED_LINES<o:p></o:p></span></b></p>"
				+ "  </td>"		
				+ "</tr>" 
				+ "</tbody>" 
				+ "<tbody>"
				+ tableTag +"</tbody>"
				+ "</table><br><br>");}
		else {
			 content.append( "<p>Hi Team,</p>"
					+"<h3 align=\"center\" style=\"text-align:center\"><u><span style=\"font-family:&quot;Verdana&quot;,sans-serif;"
					+ "mso-fareast-font-family:&quot;Times New Roman&quot;;color:#23261fd9\">"
					+ "DATA NOT AVAILABLE FOR CARS PRODUCTION FAILURE REPORT:-" 
					+ "</u>"
					+  "<u><span style=\"font-family:&quot;Verdana&quot;,sans-serif;"
					+ "mso-fareast-font-family:&quot;Times New Roman&quot;;color:red\">"+formatter.format(date)+" </span>"
					+ "</h3>"); 
		}
		
		
		if(tableTag1.length()!=0) 
		{
		 content.append("<h3 align=\"center\" style=\"text-align:center\"><u><span style=\"font-family:&quot;Verdana&quot;,sans-serif;"
					+ "mso-fareast-font-family:&quot;Times New Roman&quot;;color:#23261fd9\">Long Running Requests at CARS(selected date - 7Days) "
					+ "- </span></u>"
					+ "<span style=\"mso-fareast-font-family:&quot;Times New Roman&quot;\"><o:p></o:p></span></h3>"
					+ "<h2><strong><span style=\"color: #000080; background-color: #99cc00;\"></span></strong></h2>"
					+ "<table  align=\"center\" style=\"width: 621px; height: 33px;   border-color: black;\" border=\"2\">"
					+ "<tbody>" 	
			        + "<tr style=\"mso-yfti-irow:0;mso-yfti-firstrow:yes\">"
			        + "  <td "+ style+">"
			        + "  <p "+ classs+">"
			        + "  Sl No<o:p></o:p></span></b></p>"
			        + "  </td>"
			        + "  <td "+ style+">"
			        + "  <p "+ classs+">"
			        + "  APPL_NAME<o:p></o:p></span></b></p>"
			        + "  </td>"
			        + "  <td "+ style+">"
			        + "  <p "+ classs+">"
			        + "  REQUEST_ID<o:p></o:p></span></b></p>"
			        + "  </td>"
			        + "  <td "+ style+">"
			        + "  <p "+ classs+">"
			        + "  SUBMITTED_REC_CNT<o:p></o:p></span></b></p>"
			        + "  </td>"
			        + "  <td "+ style+">"
			        + "  <p "+ classs+">"
			        + "  CARS_PROCESSING_TIME<o:p></o:p></span></b></p>"
			        + "  </td>"
			        + " </tr>"
			    	+ "</tbody>" 
					+ "<tbody>"
			    	+ tableTag1 +"</tbody>"
					+ "</table><br><br>");
		}
		else {
			 content.append("<h3 align=\"center\" style=\"text-align:center\">"
		 	          	+ "<u><span style=\"font-family:&quot;Verdana&quot;,sans-serif;"
					    + "mso-fareast-font-family:&quot;Times New Roman&quot;;color:#23261fd9\">"
					    + "DATA NOT AVAILABLE FOR LONG RUNNING REQUESTS AT CARS:-" 
					    + "</u>"
						+  "<u><span style=\"font-family:&quot;Verdana&quot;,sans-serif;"
						+ "mso-fareast-font-family:&quot;Times New Roman&quot;;color:red\">"+formatter.format(date)+" </span>"
						+ "</h3>"); 
		}
	
		
		if(tableTag2.length()!=0) {
			 content.append("<h3 align=\"center\" style=\"text-align:center\"><span lang=\"EN-IN\" style=\"mso-fareast-font-family:"
			 		+ "&quot;Times New Roman&quot;;color:#23261fd9;mso-ansi-language:EN-IN\">CARS Errors Report<o:p></o:p></span>"
			 		+ "<span style=\"mso-fareast-font-family:&quot;Times New Roman&quot;\"><o:p></o:p></span></h3>"
					+ "<h2><strong><span style=\"color: #000080; background-color: #99cc00;\"></span></strong></h2>\n"
					+ "<table align=\"center\" style=\"width: 621px; height: 33px;   border-color: black;\" border=\"2\">"
			 		+ "  <tbody>"
			 		+ "  <tr style=\"mso-yfti-irow:0;mso-yfti-firstrow:yes\">"
			 		+ "  <td "+ style+">"
			 	    + "  <p "+ classs+">"
			 		+ "  Sl No<o:p></o:p></span></b></p>"
			 		+ "  </td> "
			 		+ "  <td "+ style+">"
			 		+ "  <p "+ classs+">"
			 		+ "  APPL_NAME<o:p></o:p></span></b></p>"
			 		+ "  </td>"
			 		+ "  <td "+ style+">"
			 		+ "  <p "+ classs+">"
			 		+ "  ERROR_CODE<o:p></o:p></span></b></p>"
			 		+ "  </td>"
			 		+ "  <td "+ style+">"
			 		+ "  <p "+ classs+">"
			 		+ "  ERROR_MESSAGE<o:p></o:p></span></b></p>"
			 		+ "  </td>"
			 		+ "  <td "+ style+">"
			 		+ "  <p "+ classs+">"
			 		+ "  COUNT<o:p></o:p></span></b></p>"
			 		+ "  </td>"
					+ " </tr>" 
			 		+ "</tbody>" 
					+ "<tbody>"
			 		+ tableTag2 + "</tbody>" 
			 		+ "</table><br><br>"
			 		+ "<p>Thank You.</p>");
		}
			 else {
				 content.append("<h3 align=\"center\" style=\"text-align:center\">"
				 	          	+ "<u><span style=\"font-family:&quot;Verdana&quot;,sans-serif;"
							    + "mso-fareast-font-family:&quot;Times New Roman&quot;;color:#23261fd9\">"
							    + "DATA NOT AVAILABLE FOR CARS ERRORS REPORT:-"
							    + "</u>"
								+  "<u><span style=\"font-family:&quot;Verdana&quot;,sans-serif;"
								+ "mso-fareast-font-family:&quot;Times New Roman&quot;;color:red\">"+formatter.format(date)+" </span>"
								+ "</h3>"); 
			 }
		
		messageBodyPart.setContent(content.toString(), "text/html");
		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);
		new MimeBodyPart();
		msg.setContent(multipart);
		Transport.send(msg);
		System.out.println("Email Sent Successfully !");
		System.out.println("FINAL TABLE TAG------>" );
		tableTag="";
		tableTag1="";
		tableTag2="";
	}


}
