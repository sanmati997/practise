package com.scm.kafka.daoservices;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;



import com.scm.kafka.beans.ACATCounsumerRespBean;
//import com.scm.kafka.main.CustomerIncExc;
//import com.scm.kafka.main.BIEXCEPTION;
import com.scm.kafka.servicesprovider.commonUtility;

import joptsimple.internal.Strings;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;

public class dbConnectionimpl {
	static Connection con = null;
	// Connection con1 = null;
	static String content = "PLSQL ERROR: Return Code -1 for exception occured in PLSQL Block";
	static int RETURN_VARIABLE = 0;
	static long totaltimetillnow;
	Long request_id;
	static String RETURN_VARIABLE1 = null;
	static String RETURN_VARIABLE2 = null;
	static String RETURN_VARIABLE3 = null;
	static String RETURN_VARIABLE4 = null;
	static int reqNo = 123;
	static int applicationRequestID = 0;
	static String attribute3QuoteId = null, strQuoteNumber = null,strTransactionType = null, strSubTrxType = null;
	File file1 = new File("SAVAJOBFAIL.txt");
	String DB_URL;
	String DB_USER_ID;
	String DB_PASSWORD;
	String groupID;
	String topicName;
	String carsErrorTableName, carsErrorTableNameColsList, carsOutPutTableName,
	carsOutputTableColsList;

	OracleCallableStatement callStmt1;



	public Connection con() {

		System.out.println(" Connection Methond");
		commonUtility comm = new commonUtility();
		Properties prop = comm.readProp();
		DB_URL = prop.getProperty("dbHost");
		DB_USER_ID = prop.getProperty("dbUserID");
		DB_PASSWORD = prop.getProperty("dbPassword");
		groupID = prop.getProperty("groupId");
		topicName = prop.getProperty("topicName");

		//            System.out.println("DB URL:"+DB_URL);
		//System.out.println("DB_USER_ID:"+DB_USER_ID);
		//System.out.println("DB_PASSWORD:"+DB_PASSWORD);

		try {

			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(DB_URL.trim() + "",DB_USER_ID.trim(), DB_PASSWORD.trim());

			return con;
		} catch (Exception e) {
			System.err.println("Exception in DB connection" + e.getMessage());
			e.getStackTrace();
			return null;
		}

	}

	public Statement dbConn() {

		commonUtility comm = new commonUtility();
		Properties prop = comm.readProp();
		DB_URL = prop.getProperty("dbHost");
		DB_USER_ID = prop.getProperty("dbUserID");
		DB_PASSWORD = prop.getProperty("dbPassword");
		groupID = prop.getProperty("groupId");
		topicName = prop.getProperty("topicName");

		Statement stmt = null;
		try {

			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(DB_URL, DB_USER_ID, DB_PASSWORD);
			stmt = con.createStatement();
			return stmt;

		} catch (Exception e) {
			System.err.println("Exception in dbConn" + e.getMessage());
			e.getStackTrace();
			return null;
		}
	}

	// this method to be used in ACAT Consumer to maintain error log in backend
	public void insertErrorErrData(String ID, Long APPL_REQUEST_ID,String TOPIC, Integer offset, Integer partition, String Json,String ERROR_CODE, String ERROR_MESSAGE, String created_by,String created_date, String updated_by, String updated_date,     String errTable, String errorTableColsList) {                           
		//ID = " xxcss_o.XXCSS_CARS_KAFKA_ERRLOG_SEQ.nextval ";  // send offset instead of ID to maintain unique record
		try {
			Statement stmt = dbConn();
			String query = "insert into " + errTable + " ("	+ errorTableColsList + ")" + " values (" + ID
					+ "," + APPL_REQUEST_ID + " ,'" + TOPIC + "' ," + offset
					+ "," + partition + ",'" + Json + "','" + ERROR_CODE
					+ "','" + ERROR_MESSAGE + "','" + created_by + "',"
					+ created_date + ",'" + updated_by + "'," + updated_date
					+ "" + ")";
			//System.out.println("Err Qruey : " + query);
			if (!Strings.isNullOrEmpty(query)) {
				stmt.execute(query);
				con.commit();
				//con.close();
			}
		} catch (Exception e) {
			System.out
			.println("Error in insert Error data : " + e.getMessage());
		}
	}
	//--------------------------------------------------------------------------------------------------------


	public void executeACATSaveCustomerType(ACATCounsumerRespBean NOTIFICATION_OBJ,long lastOffset ) throws SQLException, ParseException {
		System.out.println("In DB Connection Method");
		
		OracleCallableStatement callStmt1 = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {

			System.out.println("last offset------------>"+lastOffset);

			con = con();
			String proc = "{"+ "call apps.xxcss_acaT_sfdc_int.update_opportunity_id (?,?,?,?)"+"}";// BACKEND API CALL
			callStmt1 = (OracleCallableStatement) con.prepareCall(proc);

				System.out.println("    CorelationId : "+NOTIFICATION_OBJ.getCorelationId());
				System.out.println("	Oppty ID : "+NOTIFICATION_OBJ.getOpptyID());
				System.out.println("	Response : "+NOTIFICATION_OBJ.getResponseMessage());

			
			 callStmt1.setLong(1,NOTIFICATION_OBJ.getCorelationId());
			 callStmt1.setString(2,NOTIFICATION_OBJ.getOpptyID());
			 callStmt1.setString(3,NOTIFICATION_OBJ.getResponseMessage());
			 callStmt1.registerOutParameter(4, OracleTypes.VARCHAR);// Status
			 callStmt1.execute();
			 RETURN_VARIABLE2 = callStmt1.getString(4);
			 System.out.println("return "+RETURN_VARIABLE2);
			 System.out.println("====Data inserted successfully====");
		} catch (NullPointerException npex) {
			System.out.println(" Exception in executeBatchProcedure : ");
			npex.printStackTrace();
		} catch (SQLException buex) {
			buex.printStackTrace();
			System.out.println(" Exception in executeBatchProcedure : "
					+ buex.getMessage());
		} finally {
			callStmt1.close();
			con.close();
		}
		
		/*dealIdEntryDelelegalInclusionEntitiesDeletedted.clear();
		dealIdEntryDeleted.clear();
		legalExclusionEntitiesDeleted.clear();
		exclusionAdditionalParamsRulesDeleted.clear();*/


	}


}


