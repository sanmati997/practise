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

public abstract class CSFMDaily implements Connection {
	static int p_current_thread = 1;
	static int p_total_threads_i = 1;
	static String p_return_variable_o;
	static String p_return_msg_o;
	Statement stmt = null;
	static String DB_URL;
	static String DB_USER_ID;
	static String DB_PASSWORD;
	static String FROM_USER;
	static String TO_USER;
	static Connection con = null;
	static String delaytime;
	int row_count;
	static String RMA_ID = "";
	static String RMA_ID1 = "";
	//static String CSFM_ID = "";
	private static String NotrunningStatus;

	public static Connection con() throws SQLException {
		commonUtility comm = new commonUtility();
		Properties prop = comm.readProp();
		DB_URL = prop.getProperty("dbHost");
		DB_USER_ID = prop.getProperty("dbUserID");
		DB_PASSWORD = prop.getProperty("dbPassword");
		FROM_USER = prop.getProperty("FromUser");
		TO_USER = prop.getProperty("ToUser");

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

	public static void executeApiBatch() throws SQLException, InterruptedException, MessagingException {
		
		String CSFM_ID = "";
		Date date2 = new Date();
		long time2 = date2.getTime();
		Timestamp ts2 = new Timestamp(time2);
		System.out.println("Current Time Stamp DAILY: " + ts2);
		
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
		System.out.println("CSFM DAILY mail api calling...");
		String proc = "SELECT ERROR_CODE,"
			      		+ "DECODE (b.ERROR_CODE,"
			      		+ "'XXCSS_SFM_QUANTITY_MISMATCH', SUBSTR (error_message, 1, 80),"
			      		+ "'XXCSS_SFM_SERIAL_QTY_MISMATCH', SUBSTR (error_message, 1, 70),"
			      		+ "'XXCSS_SFM_PARENT_NOT_PROCESSED', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_QTY_MIS_PAR_CHLD', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_SERIL_NO_MISMATCH', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_PAK_NOT_FOUND', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_POLLER_SYS_ERROR', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_PAK_KEY_MISMATCH', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_TRANSFORM_SYS_ERR', SUBSTR (error_message, 1, 34),"
			      		+ "'XXCSS_SFM_SITE_USE_MISSING', SUBSTR (error_message, 12, 120),"
			      		+ "'XXCSS_SFM_INV_SITE_USE', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_TRANS_DER_ATTR_MAIN', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_SITE_USE_NULL', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_TRY_BUY_NOT_NULL', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_DTCHG_CNT_NOT_FOUND', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_TRY_BUY_SRV_CON_DT', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_M_LINE_IB_MISSING', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_CUST_ACCP_DT_NULL', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_SRV_SDT_EDT_NULL', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_END_DATE_ALIGN_ISSUE', SUBSTR (error_message, 1, 45),"
			      		+ "'XXCSS_SFM_SRV_SDT_EDT_NOT_NULL', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_TRANSFORM_FAILD', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_SHP_CONF_DT_NOT_NULL', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_TRANSFORM_DATE_CALC', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_TRANSFORM_INFO_REQ', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_TRANSFORM_INS_HDR', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_TRANSFORM_SVE_CALL', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_TRANSF_SPLIT_INST_ID', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_TRANSF_SPLIT_SERL_ID', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_Q_LINE_IB_MISSING', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_NON_TRACKABLE_ITEM', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_CRUD_SYS_ERROR', SUBSTR (error_message, 1, 147),"
			      		+ "'XXCSS_SFM_CRUD_FAILD', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_CRUD_OOB_ERROR', SUBSTR (error_message, 1,125),"
			      		+ "'XXCSS_SFM_CRUD_INFO_REQ', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_CRUD_ITM_REL_NOT_EXT', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_CRUD_ITM_REL_EXPIRE', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_CRUD_INFO_NOT_UPD', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_SRV_BILL_SITE_ID_VAL', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_COUNT_CODE_MISSING', SUBSTR (error_message, 1, 47),"
			      		+ "'XXCSS_SFM_CON_SVC_BILLTO_DIFF', SUBSTR (error_message, 1, 30),"
			      		+ "'XXCSS_SFM_COV_PROD_NOT_FOUND', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_LIST_LINE_NOT_IN', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_COV_PROD_UPD_SRV_LIN', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_COV_PROD_UPD_AUTH', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_COV_PROD_UPD_ERR_AUT', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_CONT_NOT_INFO_FOUND', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_ADD_CON_ALREADY_EXIS', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_CRUD_SPLIT_QUANT_ERR', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_CRUD_INFO_NOT_REQ', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_SERVICE_NOT_FND', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_QA_CHECK_ERROR', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_IB_QTY_MISMATCH', SUBSTR (error_message, 1, 22),"
			      		+ "'XXCSS_SFM_INVALID_SITE_W', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_INVLD_RES_BILL_TO', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_INVLD_BILL_TO', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_RES_BILL_TO_NULL', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_BILL_TO_NULL', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_INVL_INSTALL_SITE', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_START_DATE_NULL', SUBSTR (error_message, 1, 52),"
			      		+ "'XXCSS_SFM_END_DATE_NULL', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_SHIPMENT_NOT_FOUND', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_INVALID_SERVICE_LVL', SUBSTR (error_message, 1, 51),"
			      		+ "'XXCSS_SFM_SERVICE_LVL_NULL', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_SN_ALRDY_CVRD', SUBSTR (error_message, 1, 42),"
			      		+ "'XXCSS_SFM_PRD_ST_DT_GT_ED_DT', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_CONT_NOT_CREATE', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_CRUD_FAILED', SUBSTR (error_message, 1, 40),                 "
			      		+ " SUBSTR (error_message, 1, 70))"
			      		+ "            error_message,"
			      		+ "         COUNT (1) COUNT"
			      		+ "  FROM APPS.XXCSS_SFM_ERROR_LOG B"
			      		+ "  WHERE application_name='XXCSS_SFM' "
			      		+ "  and error_type<>'INFO'"
			      		+ "  and error_message not like '%OOB Error : Update IB failed.Instance End Date is needed for Terminable Statuses%' "
			      		+ "  and trunc(created_date)>=to_date('"+df.format(dateobj)+"','MM/DD/YYYY')-1"
			      		+ "  and biz_process <> 'SCC Update contract' "
			      		+ "   GROUP BY ERROR_CODE,"
			      		+ "         DECODE ("
			      		+ "            b.ERROR_CODE,"
			      		+ "'XXCSS_SFM_QUANTITY_MISMATCH', SUBSTR (error_message, 1, 80),"
			      		+ "'XXCSS_SFM_SERIAL_QTY_MISMATCH', SUBSTR (error_message, 1, 70),"
			      		+ "'XXCSS_SFM_PARENT_NOT_PROCESSED', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_QTY_MIS_PAR_CHLD', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_SERIL_NO_MISMATCH', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_PAK_NOT_FOUND', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_POLLER_SYS_ERROR', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_PAK_KEY_MISMATCH', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_TRANSFORM_SYS_ERR', SUBSTR (error_message, 1, 34),"
			      		+ "'XXCSS_SFM_SITE_USE_MISSING', SUBSTR (error_message, 12, 120),"
			      		+ "'XXCSS_SFM_INV_SITE_USE', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_TRANS_DER_ATTR_MAIN', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_SITE_USE_NULL', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_TRY_BUY_NOT_NULL', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_DTCHG_CNT_NOT_FOUND', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_TRY_BUY_SRV_CON_DT', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_M_LINE_IB_MISSING', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_CUST_ACCP_DT_NULL', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_SRV_SDT_EDT_NULL', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_END_DATE_ALIGN_ISSUE', SUBSTR (error_message, 1, 45),"
			      		+ "'XXCSS_SFM_SRV_SDT_EDT_NOT_NULL', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_TRANSFORM_FAILD', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_SHP_CONF_DT_NOT_NULL', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_TRANSFORM_DATE_CALC', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_TRANSFORM_INFO_REQ', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_TRANSFORM_INS_HDR', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_TRANSFORM_SVE_CALL', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_TRANSF_SPLIT_INST_ID', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_TRANSF_SPLIT_SERL_ID', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_Q_LINE_IB_MISSING', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_NON_TRACKABLE_ITEM', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_CRUD_SYS_ERROR', SUBSTR (error_message, 1, 147),"
			      		+ "'XXCSS_SFM_CRUD_FAILD', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_CRUD_OOB_ERROR', SUBSTR (error_message, 1, 125),"
			      		+ "'XXCSS_SFM_CRUD_INFO_REQ', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_CRUD_ITM_REL_NOT_EXT', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_CRUD_ITM_REL_EXPIRE', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_CRUD_INFO_NOT_UPD', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_SRV_BILL_SITE_ID_VAL', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_COUNT_CODE_MISSING', SUBSTR (error_message, 1, 47),"
			      		+ "'XXCSS_SFM_CON_SVC_BILLTO_DIFF', SUBSTR (error_message, 1, 30),"
			      		+ "'XXCSS_SFM_COV_PROD_NOT_FOUND', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_LIST_LINE_NOT_IN', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_COV_PROD_UPD_SRV_LIN', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_COV_PROD_UPD_AUTH', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_COV_PROD_UPD_ERR_AUT', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_CONT_NOT_INFO_FOUND', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_ADD_CON_ALREADY_EXIS', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_CRUD_SPLIT_QUANT_ERR', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_CRUD_INFO_NOT_REQ', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_SERVICE_NOT_FND', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_QA_CHECK_ERROR', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_IB_QTY_MISMATCH', SUBSTR (error_message, 1, 22),"
			      		+ "'XXCSS_SFM_INVALID_SITE_W', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_INVLD_RES_BILL_TO', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_INVLD_BILL_TO', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_RES_BILL_TO_NULL', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_BILL_TO_NULL', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_INVL_INSTALL_SITE', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_START_DATE_NULL', SUBSTR (error_message, 1, 52),"
			      		+ "'XXCSS_SFM_END_DATE_NULL', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_SHIPMENT_NOT_FOUND', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_INVALID_SERVICE_LVL', SUBSTR (error_message, 1, 51),"
			      		+ "'XXCSS_SFM_SERVICE_LVL_NULL', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_SN_ALRDY_CVRD', SUBSTR (error_message, 1, 42),"
			      		+ "'XXCSS_SFM_PRD_ST_DT_GT_ED_DT', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_CONT_NOT_CREATE', SUBSTR (error_message, 1, 40),"
			      		+ "'XXCSS_SFM_CRUD_FAILED', SUBSTR (error_message, 1, 40),"
			      		+ "SUBSTR (error_message, 1, 70))"
			      		+ "order by count(1) desc";

		PreparedStatement ps = con.prepareStatement(proc);
		
		ResultSet rs1 = ps.executeQuery();
		Date date1 = new Date();
		int count=1;
		while(rs1.next()) {			
			 CSFM_ID = CSFM_ID + "<tr><td>"+ count+"</td><td>" + rs1.getString(1) + "</td><td>" + rs1.getString(2) + "</td><td>"
						+ rs1.getString(3)+ "</td></tr>";
			 count++;
		}
		
		System.out.println("DATAS :" + CSFM_ID);
		if (CSFM_ID != "") {
			sendMail(CSFM_ID);
			CSFM_ID = "";
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
	
	static String style = "style=\"background:#82A6B1;padding:3.75pt 3.75pt 3.75pt 3.75pt\\\">\\n\"";

	static String table = "style=\"width: 621px; height: 33px; border-color: black;\" border=\"2\">\"";

	public static void sendMail(String tableTag) throws MessagingException {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yy");
		String content;
		Date date = new Date();
		Properties props = new Properties();
		props.put("mail.smtp.host", "mail.cisco.com");
		props.put("mail.smtp.port", "25");
		Session session = Session.getInstance(props);
		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(FROM_USER, false));
		msg.setRecipients(RecipientType.TO, InternetAddress.parse(TO_USER));
		msg.setSubject("CSFPRD : CSFM Daily Production Failure Report: " + formatter.format(date));
		msg.setContent("Test email to check mailer Alias", "text/html");
		msg.setSentDate(new Date());
		//String currentDate = "<strong>" + formatter.format(date) + "</strong>";
		MimeBodyPart messageBodyPart = new MimeBodyPart();
		if(tableTag.length()!=0) {
			
			content= "<p>Hi Team,</p>\n"
					+ "<h3 align=\"center\" style=\"text-align:center\">\n"
					+ "   <u><span style=\"color:#565c60\">CSFM Production\n"
					+ "   Failure Report - </span></u><u><span style=\"color:#ff1414\">Daily </span></u><span style=\"mso-fareast-font-family:&quot;Times New Roman&quot;\"><o:p></o:p></span></h3>\n"
					+ "<h2><strong><span style=\"color: #000080; background-color: #99cc00;\"></span></strong></h2>\n"
					+ "<table align=\"center\" style=\"width: 621px; height: 33px; border-color: black;\" border=\"2\">\n"
					+ "<tbody>\n"
					+ "   <tr style=\"background-color: lightgrey;\">\n"
					+ "   <td style=\"background:#6D8FFF;padding:3.75pt 3.75pt 3.75pt 3.75pt\"><p><b>SR.NO.</p><o:p></o:p></td>\n"
					+ "   <td style=\"background:#6D8FFF;padding:3.75pt 3.75pt 3.75pt 3.75pt\"><p><b>ERROR_CODE</b></p><o:p></o:p></td>\n"
					+ "   <td style=\"background:#6D8FFF;padding:3.75pt 3.75pt 3.75pt 3.75pt\"><p><b>ERROR_MESSAGE  </b></p><o:p></o:p></td>\n"
					+ "   <td style=\"background:#6D8FFF;padding:3.75pt 3.75pt 3.75pt 3.75pt\"><p><b>COUNT</b></p><o:p></o:p></td> </tr> \n"
					+ "</tbody>\n"
					+ "<tbody>"+tableTag +"</tbody>\n"
					+ "</table><br><br>\n"
					+ "<p>Thank You.</p>";
		 }else {
			 content = "<p>Hi Team,</p><h3 style=\"text-align:center\"><u><span style=\"font-family:&quot;Verdana&quot;,sans-serif;mso-fareast-font-family:&quot;Times New Roman&quot; color:#64B5F6\">\n"
				 		+ "	DATA NOT AVAILABLE FOR CSFM Production Failure Report :- <span style=\"color:#ff1414\">"+formatter.format(date)+"</span></h3>";  
		}
		messageBodyPart.setContent(content, "text/html");
		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);
		new MimeBodyPart();
		msg.setContent(multipart);
		Transport.send(msg);
		System.out.println("Email Sent Successfully !");
		System.out.println("FINAL TABLE TAG------>" + tableTag);
		tableTag = "";
	}
}
