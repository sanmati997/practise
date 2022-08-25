package com.scm.kafka.main;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.config.SslConfigs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scm.kafka.beans.OpptyDetails;
import com.scm.kafka.beans.OpptyLines;
import com.scm.kafka.beans.OpptySubBean;
import com.scm.kafka.beans.Response;
import com.scm.kafka.servicesprovider.commonUtility;

public class SFDCPublisher {

	// public static void main(String[] argv) throws IOException, SQLException {
	public void sfdcRequest() throws IOException, SQLException {

		System.out.println("in side");
		String DB_URL;
		String DB_USER_ID;
		String DB_PASSWORD;
		String groupID;
		String topicName;
		Connection con = null;
		//String topic = "TEST_TOPICn";

		Producer<String, String> producer = null;

		ObjectMapper mapper = new ObjectMapper();
		commonUtility comm = new commonUtility();
		Properties props = new Properties();
		Properties prop = comm.readProp();

		props.put("bootstrap.servers",prop.getProperty("bootstrapServer"));
				
		props.put("acks", "all");
		props.put("retries", 0);
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

		props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SSL");
		String truststore = new File(prop.getProperty("truststore")).getAbsolutePath();
		String keystore = new File(prop.getProperty("keystore")).getAbsolutePath();

		props.put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, truststore);
		props.put(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG, prop.getProperty("trust_pwd")); 
		// configure the following three settings for SSL Authentication
		props.put(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG, keystore);
		props.put(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, prop.getProperty("key_pwd"));
		props.put(SslConfigs.SSL_KEY_PASSWORD_CONFIG, prop.getProperty("key_pwd"));

		producer = new KafkaProducer<String, String>(props);
		
		

		DB_URL = prop.getProperty("dbHost");
		DB_USER_ID = prop.getProperty("dbUserID");
		DB_PASSWORD = prop.getProperty("dbPassword");
		groupID = prop.getProperty("groupId");
		topicName = prop.getProperty("topicName");

		System.out.println("topicName "+topicName);
		System.out.println("groupID "+ groupID);

		try {

			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(DB_URL.trim() + "", DB_USER_ID.trim(), DB_PASSWORD.trim());

		} catch (Exception e) {
			System.err.println("Exception in DB connection" + e.getMessage());
			e.getStackTrace();
		}

		Long correlationId;
		Statement stmt = null;
		ResultSet rsmtuser = null;
		Statement stmt1 = null;
		ResultSet rsmtuser1 = null;
		// String jsonString = null;
		int headCount = 0;
		OpptyDetails head = null;
		OpptyLines lines = null;
		List<OpptyDetails> headlist = new ArrayList<OpptyDetails>();
		List<OpptyLines> list = new ArrayList<OpptyLines>();
		System.out.println("Before loop");

		// while (true) {

		try {
			/*String sql1 = "SELECT CORELATION_ID ,EVENT_TYPE,OPPORTUNITY_NAME,OPPORTUNITY_OWNER,CR_PARTY_ID,EXPECTED_BOOK_DATE,EXPECTED_SERVICE,\r\n"
			+ "FORECASTING_POSITION,CURRENCY,BE_GEO_ID,OPPORTUNITY_ID,OPPORTUNITY_TYPE,FORECAST_CATEGORY,OPPORTUNITY_FLAG,TRUE_UP_STATUS \r\n"
			+ "from apps.xxcss_acat_sfdc_header  where KAFKA_STATUS='NEW'";*/
			
			String sql = "SELECT CORELATION_ID ,EVENT_TYPE,OPPORTUNITY_NAME,OPPORTUNITY_OWNER,CR_PARTY_ID,to_char(EXPECTED_BOOK_DATE,'DD-MON-YYYY') \r\n"
					+ "EXPECTED_BOOK_DATE,EXPECTED_SERVICE,FORECASTING_POSITION,CURRENCY,BE_GEO_ID,OPPORTUNITY_ID,\r\n"
					+ "OPPORTUNITY_TYPE,FORECAST_CATEGORY,OPPORTUNITY_FLAG,TRUE_UP_STATUS\r\n"
					+ "from apps.xxcss_acat_sfdc_header where KAFKA_STATUS='NEW'";

			//System.out.println("sql : " + sql);
			stmt1 = con.createStatement();
			rsmtuser1 = stmt1.executeQuery(sql);
			System.out.println("Before result");

			while (rsmtuser1.next()) {
				correlationId = rsmtuser1.getLong(1);
				OpptySubBean oplbean = null;
				String jsonString = null;

				head = new OpptyDetails();
				head.setCorelationId(rsmtuser1.getLong(1));
				head.setEventType(rsmtuser1.getString(2));
				head.setOpportunityName(rsmtuser1.getString(3));
				head.setOpportunityOwner(rsmtuser1.getString(4));
				head.setCRPartyID(rsmtuser1.getLong(5));
				head.setExpectedBookDate(rsmtuser1.getString(6));
				head.setExpectedService(rsmtuser1.getLong(7));
				head.setForecastingPosition(rsmtuser1.getString(8));
				head.setCurrency(rsmtuser1.getString(9));
				head.setBEGEOID(rsmtuser1.getLong(10));
				head.setOpportunityId(rsmtuser1.getString(11));
				head.setOpportunityType(rsmtuser1.getString(12));
				head.setForecastCategory(rsmtuser1.getString(13));
				head.setOpportunityFlag(rsmtuser1.getString(14));
				head.setTrueUpStatus(rsmtuser1.getString(15));

				System.out.println("correlationId: " + correlationId);

				try {
					String sql1 = "SELECT l.SERVICE_PROGRAM,l.SERVICE_LEVEL,l.BUSINESS_ENTITY_CCW,l.BUSINESS_SUB_ENTITY_CCW,l.SERVICE_CATEGORY,l.ALLOCATED_SERVICE_GROUP, l.MIX_C, l.EXPECTED_SERVICE_NET_PRICE,l.TERM,\r\n"
							+ "l.SERVICE_CATEGORY||'-'|| l.ALLOCATED_SERVICE_GROUP ||'-'||h.CURRENCY || '-Service' as \"UniqueSpSlScC\",\r\n"
							+ "l.SERVICE_CATEGORY||'-'|| l.ALLOCATED_SERVICE_GROUP ||'-'||l.SERVICE_LEVEL \"ServiceWithServiceLevelC\"\r\n"
							+ "FROM apps.xxcss_acat_sfdc_detail l,apps.xxcss_acat_sfdc_header h\r\n"
							+ "where h.corelation_id=l.corelation_id\r\n"
							+ "AND l.CORELATION_ID=" + correlationId;
					
					
					//System.out.println("sql1 : " + sql1);
					stmt = con.createStatement();
					rsmtuser = stmt.executeQuery(sql1);
					while (rsmtuser.next()) {

						lines = new OpptyLines();

						lines.setServiceProgram(rsmtuser.getString(1));
						lines.setServiceLevel(rsmtuser.getString(2));
						lines.setBusinessEntityCCWC(rsmtuser.getString(3));
						lines.setBusinessSubEntityCCWC(rsmtuser.getString(4));
						lines.setServiceCategory(rsmtuser.getString(5));
						lines.setAllocatedServiceGroup(rsmtuser.getString(6));
						lines.setMixC(rsmtuser.getDouble(7));
						lines.setExpectedServiceNetPriceC(rsmtuser.getLong(8));
						lines.setTerm(rsmtuser.getLong(9));
						lines.setUniqueSpSlScC(rsmtuser.getString(10));
						lines.setServiceWithServiceLevelC(rsmtuser.getString(11));

						list.add(lines);

						headCount++;
					}
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					rsmtuser.close();
					stmt.close();
				}

				oplbean = new OpptySubBean();

				oplbean.setOpptyDetails(head);
				oplbean.setOpptyLines(list);

				jsonString = mapper.writeValueAsString(oplbean);

				Response response = new Response();
				System.out.println("jsonString : "+jsonString);
				try {

					RecordMetadata metadata;
					metadata = producer.send(new ProducerRecord<String, String>(topicName, jsonString)).get();
					System.out.println("metadata:" + metadata);
					long offset = metadata.offset();
					int partition = metadata.partition();
					topicName = metadata.topic();
					response.setMessage("success");
					response.setOffset(offset);
					response.setPartition(partition);
					response.setTopic(topicName);

				} catch (Exception e) {
					e.printStackTrace();
				}
				producer.close();
				headlist.clear();
				list.clear();

				if (response.getMessage() == "success") {

					try {

						/*String update_sql = "update xxcss_o.xxcss_acat_sfdc_header h set kafka_status ='PUSHED',last_update_date=sysdate where h.corelation_id ="
								+ correlationId + "";*/
						
						String update_sql = "update xxcss_o.xxcss_acat_sfdc_header h set kafka_status ='PUSHED',last_update_date=sysdate,offset="+response.getOffset()+" where h.corelation_id ="
								+ correlationId + "";
						
						System.out.println("update_sql : " + update_sql);
						stmt = con.createStatement();
						rsmtuser = stmt.executeQuery(update_sql);
						System.out.println("Data pushed on topic...");

					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						rsmtuser.close();
						stmt.close();

					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			stmt1.close();
			rsmtuser1.close();
			//stmt.close();
		}
		
		System.out.println("Lines count " + headCount);
		// rsmtuser.close();
		// mapper.writeValue(System.out, oplbean);
		// mapper.writeValue(new File("oplbean.json"), oplbean);
	}

	// }

}
