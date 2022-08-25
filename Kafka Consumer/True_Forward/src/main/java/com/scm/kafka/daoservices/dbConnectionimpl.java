package com.scm.kafka.daoservices;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.List;
import java.util.Properties;

import com.scm.kafka.main.Header;
//import com.scm.kafka.main.CustomerIncExc;
import com.scm.kafka.main.Lines;
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
	static long applicationRequestID = 0;
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


	public void executeTrueFWDSave(List<Header> HEADER_OBJ,
			List<Lines> LINES_OBJ,String TOPIC, Integer offset, Integer partition, 
			String errTable, String errorTableColsList) throws SQLException, ParseException {
	System.out.println("In DB Connection Method");
		
		OracleCallableStatement callStmt1 = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			
			con = con();

			Header[] HEADER_OBJ1 = HEADER_OBJ.toArray(new Header[HEADER_OBJ.size()]);
			Lines[] LINES_OBJ1 = LINES_OBJ.toArray(new Lines[LINES_OBJ.size()]);
			
			applicationRequestID=HEADER_OBJ.get(0).getTransaction_id();
			System.out.println("Transaction Id: "+applicationRequestID);
			System.out.println("SubrefId :"+HEADER_OBJ.get(0).getSub_ref_id());
			
			String proc ="{call APPS.XXCSS_ACAT_KAFKA_INT.populate_cxea_tf_webord_price (?,?)}";// BACKEND API CALL
			StructDescriptor oracleLineObjectCollection = StructDescriptor.createDescriptor("APPS.XXCSS_ACAT_TF_WBRDPRC_TYPE", con);
			callStmt1 = (OracleCallableStatement) con.prepareCall(proc);
			STRUCT linesStruct = null;
			int l_count = 0;
			Lines lines1;
			Header header1;
			STRUCT[] structArrayLines = new STRUCT[LINES_OBJ.size()];
			for (int i = 0; i < (LINES_OBJ1.length); i++) 
			{
				header1=HEADER_OBJ1[0];
				lines1 = LINES_OBJ1[i];

				try {
					Object[] trueObject = new Object[] 
							{
							header1.getTransaction_id(),		
							header1.getSub_ref_id(),
							lines1.getTrue_forward_date(),
							lines1.getInstance_id(),
							lines1.getCpl_id(),
							lines1.getInstall_site_loc(),
							lines1.getWeb_ord_id(),
							lines1.getWeb_order_line_id(),
							lines1.getOriginal_list_price(),
							lines1.getDefault_term(),
							lines1.getEa_Factor(),
							lines1.getUnit_list_price(),
							lines1.getService_list_price(),
							lines1.getProtected_discount(),
							lines1.getUnit_net_price_before_credit(),
							lines1.getExtended_net_price_bfr_credit(),
							lines1.getCredit_amount(),
							lines1.getCredit_code(),
							lines1.getService_net_price(),
							lines1.getMonthly_credit(),
							lines1.getMonthly_ea_list_price(),
							lines1.getResidual_monthly_list_price(),
							lines1.getMonthly_ea_credit(),
							lines1.getResidual_monthly_credit(),
							header1.getC3_request_id(),
							null,
							
				
					};
					linesStruct = new STRUCT(oracleLineObjectCollection, con,trueObject);
				} /*catch (NullPointerException e) {
//                                                                                System.out.println("Null Pointer Exception");
//                                                                                continue;
                                                          } */catch (StringIndexOutOfBoundsException e) {
                                                        	  System.out.println("StringIndexOutOfBoundsException Exception");
                                                        	  l_count = l_count + 1;
                                                        	  continue;
                                                          }
				structArrayLines[i] = linesStruct;
//
			}
			ArrayDescriptor LinesTypeArrayDesc = ArrayDescriptor.createDescriptor("APPS.XXCSS_ACAT_TF_WBRDPRC_TAB", con);
			ARRAY LinesArrayOfProjects = new ARRAY(LinesTypeArrayDesc, con,structArrayLines);
			
			 callStmt1.setObject(1,LinesArrayOfProjects );
			 callStmt1.registerOutParameter(2, OracleTypes.VARCHAR);// Status
			 callStmt1.execute();
			 RETURN_VARIABLE2 = callStmt1.getString(2);
			// If Error then post directly in output table
			if (RETURN_VARIABLE2.equalsIgnoreCase("E")) {
					// Set value of Error
				RETURN_VARIABLE2 = "ERROR";

					
					String ErrorCode = RETURN_VARIABLE2;
					String json_data = "JASON-Refer JSON in Kafka Topic";

					// Insert in CARS Kafka Error Log
					insertErrorErrData("ID", applicationRequestID, TOPIC, offset, 
							partition, json_data, ErrorCode,"Getting Error while Inserting Data", "-1", "sysdate", "-1", "sysdate", errTable, errorTableColsList);
					System.out.println("====Getting Error while Inserting Data======");
				}
			else
			 System.out.println("====Data inserted successfully====");
			
		} catch (NullPointerException npex) {
			System.out.println(" Exception in executeBatchProcedure : ");
			npex.printStackTrace();
		} 
		catch (SQLException buex) {
			buex.printStackTrace();
			System.out.println(" Exception in executeBatchProcedure : "
					+ buex.getMessage());
		} finally {
			callStmt1.close();
			con.close();
		}

	}


}


