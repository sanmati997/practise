package com.scm.kafka.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.kafka.clients.CommonClientConfigs;
//COMMIT
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.config.SslConfigs;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.scm.kafka.beans.ACATCounsumerRespBean;
import com.scm.kafka.daoservices.dbConnectionimpl;
import com.scm.kafka.servicesprovider.commonUtility;

import joptsimple.internal.Strings;

public class ACATSfdcMain {
	static KafkaConsumer<String, String> consumer = null;
	static Map<Integer, Integer> hm = null;
	static dbConnectionimpl dbCon = null;
	static String aibCommitTable = null;
	static String aibErrorTable = null;
	static Integer intOffset = null;
	static Integer intPartition_Number = null;
	static String JsongetString = null;
	static List<Long> OFFSET = new ArrayList<Long>();
	static List<Long> PARTITION_NUMBER = new ArrayList<Long>();
	static List<String> JASON_DATA = new ArrayList<String>();

	static ACATCounsumerRespBean  acatCounsumerRespBean= new ACATCounsumerRespBean();//customerIncExcBeans

	static List<String> uniDataBatch = new ArrayList<String>();
	static Set<String> uniErrDataBatch = new HashSet<String>();
	static Set<Long> uniqDataBatch = new HashSet<Long>();
	static List<String> dupDataBatch = new ArrayList<String>();
	static Set<String> uniDataBatchMain = new HashSet<String>();
	static List<String> dupDataBatchMain = new ArrayList<String>();
	static List<Integer> setOFFSET_dup = new ArrayList<Integer>();
	static List<String> setdate = new ArrayList<String>();
	static List<String> setjson_string = new ArrayList<String>();
	static List<String> seterr_msg = new ArrayList<String>();
	static List<String> seterr_code = new ArrayList<String>();
	static List<Integer> setPARTITION_dup = new ArrayList<Integer>();
	static int batchSize = 0;
	static int Poll_ms = 0;
	static int size = 0;
	static int counter = 0;
	static int dupcounter = 0;
	static String HDRTableColsList = null;
	static String LINESTableColsList = null;
	static String errorTableColsList = null;
	static String HDRTableName = null;
	static String LINESTableName = null;
	static int initFlag = 0, initFlag0 = 0, initFlag1 = 0, initFlag2 = 0,initFlag3 = 0;
	static String topicName = "";
	static String groupId = "";
	static String error_msg = "";
	static String err_msg_req_id = "";
	static String err_msgs = "";
	//adding as a part of april release
	//static List<String>   customerId= new ArrayList<String>();
	static List<String> dealIdEntryDeleted= new ArrayList<String>();
	static List<String> dealIdEntryDelelegalInclusionEntitiesDeletedted= new ArrayList<String>();
	static List<String> inclusionAdditionalParamsRulesDeleted= new ArrayList<String>();
	static List<String> legalExclusionEntitiesDeleted= new ArrayList<String>();
	static List<String> exclusionAdditionalParamsRulesDeleted= new ArrayList<String>();



	public static void main(String[] argv) {
		commonUtility comm = new commonUtility();
		Properties prop = comm.readProp();
		topicName = prop.getProperty("topicName");//prop.getProperty("topicName");
		groupId = prop.getProperty("groupId");
		String strServerName = prop.getProperty("bootstrapServer");

		batchSize = Integer.parseInt(prop.getProperty("BatchSize").toString());
		Poll_ms = Integer.parseInt(prop.getProperty("Poll_Ms").toString());

		aibErrorTable = prop.getProperty("AIBErrorTable");
		errorTableColsList = prop.getProperty("AIBErrorTableColsList");


		System.out.println("================================================");
		System.out.println("topic Name : " + topicName);
		System.out.println("group Id : " + groupId);
		System.out.println("Server : " + strServerName);
		System.out.println("ERROR  Table : " + aibErrorTable);
		System.out.println("Batch Size : " + batchSize);
		System.out.println("================================================");

		Properties configProperties = new Properties();
		configProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,strServerName);
		configProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringDeserializer");
		configProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringDeserializer");
		configProperties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		configProperties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);//autocommit true
		configProperties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
		configProperties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, 100);
		configProperties.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 300000);
		configProperties.put(ConsumerConfig.REQUEST_TIMEOUT_MS_CONFIG, 350000);
		configProperties.put(ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG,15728640);

//		configProperties.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SSL");
//		configProperties.put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, "src//main//resources//client.truststore.jks");
//		configProperties.put(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG,  "CiscoCIBtc123stg");
//		
//		// configure the following three settings for SSL Authentication
//		configProperties.put(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG, "src//main//resources//con_stg//client.keystore.jks");
//		configProperties.put(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, "ciscoCIB1234stg");
//		configProperties.put(SslConfigs.SSL_KEY_PASSWORD_CONFIG, "ciscoCIB1234stg");
		
		configProperties.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SSL");
		configProperties.put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, "C:\\KafkaSSL\\client.truststore.jks");
		configProperties.put(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG,  "CiscoCIBtc123stg");
		
		// configure the following three settings for SSL Authentication
		configProperties.put(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG, "C:\\KafkaSSL\\client.keystore.jks");
		configProperties.put(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, "ciscoCIB1234stg");
		configProperties.put(SslConfigs.SSL_KEY_PASSWORD_CONFIG, "ciscoCIB1234stg");
		
		
//		configProperties.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SSL");
//		configProperties.put(SslConfigs.SSL_TRUSTSTORE_LOCATION_CONFIG, "src//main//resources//prod//client.truststore.jks");
//		configProperties.put(SslConfigs.SSL_TRUSTSTORE_PASSWORD_CONFIG,  "CiscoCIBtc123prod");
//		
//		// configure the following three settings for SSL Authentication
//		configProperties.put(SslConfigs.SSL_KEYSTORE_LOCATION_CONFIG, "src//main//resources//prod//client.keystore.jks");
//		configProperties.put(SslConfigs.SSL_KEYSTORE_PASSWORD_CONFIG, "ciscoCIB1234prod");
//		configProperties.put(SslConfigs.SSL_KEY_PASSWORD_CONFIG, "ciscoCIB1234prod");
		
		
		
		dbCon = new dbConnectionimpl();//DBConnection Object

		System.out.println("Subscribed to topic " + topicName);

		ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);//case sensitive mapping 
		objectMapper.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);		
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);		

		System.out.println("Consumer start ");
		consumer = new KafkaConsumer<String, String>(configProperties);
//		System.out.println("Consumer end ");

		int i = 0;
		boolean insertFlag = false;
		boolean recCheck   = false;
		try {
			consumer.subscribe(Arrays.asList(topicName));
			System.out.println("Consumer Subscribe");
			while (true) {
				ConsumerRecords<String, String> records = consumer.poll(Poll_ms);
				recCheck = false;
				for (TopicPartition partition : records.partitions()) {
					System.out.println(" ----partition()  ---- :" + partition);
					List<ConsumerRecord<String, String>> partitionRecords = records.records(partition);
					for (ConsumerRecord<String, String> record : partitionRecords) {
						String message = null;
						recCheck = true;
						JsongetString = new String(record.value());
						intPartition_Number = record.partition();
						intOffset = (int) record.offset();			
						//added offset 
						//System.out.println("JsongetString " + JsongetString);
						/*JsongetString = JsongetString.replaceAll("\\\\\"", "");
						JsongetString = JsongetString.replace("\\", "");*/
						ACATCounsumerRespBean responseObj = null;                        
						try {
							objectMapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
							responseObj = objectMapper.readValue(JsongetString,ACATCounsumerRespBean.class);
							objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
							uniDataBatch.add(JsongetString);

						} catch (JsonParseException e) {
							System.out.println("1. Json Parse Exception exception- hence inserting into error table");
							e.printStackTrace();
							Long Appl_Req_ID = 0L;
							message = e.getMessage();
							message = message.substring(0, 70);
							if (!Strings.isNullOrEmpty(JsongetString.toString())) {
								String err_code = "JAVA - 0003";
								String error_message = "JSON Parsing Exception - Wrong JSON Format";

								String jsonErr = JsongetString.toString();
								String arr1[] = jsonErr.split("\"");

								if (JsongetString.length() > 2000)
									JsongetString = JsongetString.substring(0,2000);
								else
									JsongetString = JsongetString.substring(0,JsongetString.length());
								if (arr1.length > 1) {
									String re_Id = arr1[4].substring(arr1[4].indexOf(":") + 2,arr1[4].indexOf(",")).trim();
									Appl_Req_ID = Long.parseLong(re_Id);

									//call error table method to maintain logs in backend
									dbCon.insertErrorErrData("ID", Appl_Req_ID,topicName, intOffset,intPartition_Number, JsongetString,	err_code, message, "-1", "sysdate","-1", "sysdate", aibErrorTable,errorTableColsList);

								}
								dupcounter++;
								System.out.println("Partition :"
										+ intPartition_Number
										+ " Offset going to updated :"
										+ intOffset);
								continue;
							}
						} catch (JsonMappingException e) {
							System.out
							.println("2. JSON Mapping Exception Exception - hence error data inserting into error table ");
							e.printStackTrace();
							String err_code = "JAVA - MAPPING EXCEPTION";
							String error_message = "JSON Mapping Exception - Attributes Not Able to Match with Java Object";
							Long Appl_Req_ID = 0L;
							message = e.getMessage();
							message = message.substring(0, 70);

							if (!Strings.isNullOrEmpty(JsongetString.toString())) {

								String jsonErr = JsongetString.toString();
								String arr1[] = jsonErr.split("\"");

								/*if (JsongetString.length() > 2000)
									JsongetString = JsongetString.substring(0,
											2000);
								else
									JsongetString = JsongetString.substring(0,
											JsongetString.length());

								if (arr1.length > 1) {
									String re_Id = arr1[4].substring(
											arr1[4].indexOf(":") + 2,
											arr1[4].indexOf(",")).trim();
									Appl_Req_ID = Long.parseLong(re_Id);

									//call error table method to maintain logs in backend
									dbCon.insertErrorErrData("ID", Appl_Req_ID,topicName, intOffset,intPartition_Number, JsongetString,err_code, message, "-1", "sysdate","-1", "sysdate", aibErrorTable,errorTableColsList);
								}*/
								dupcounter++;
								System.out.println("Partition :"
										+ intPartition_Number
										+ " Offset going to updated :"
										+ intOffset);
								continue;
							}

						} catch (IOException e) {
							System.out
							.println("3. IO Exception Exception , hence error data  inserting into error table");
							String err_code = "JAVA - 0002";
							String error_message = "IO Exception  , Please Contact Support Administrator";
							Long Appl_Req_ID = 0L;

							message = e.getMessage();
							message = message.substring(0, 70);
							if (!Strings.isNullOrEmpty(JsongetString.toString())) {
								String jsonErr = JsongetString.toString();
								System.out.println("jsonErr" + jsonErr);
								String arr1[] = jsonErr.split("\"");

								if (JsongetString.length() > 2000)
									JsongetString = JsongetString.substring(0,
											2000);
								else
									JsongetString = JsongetString.substring(0,
											JsongetString.length());

								if (arr1.length > 1) {

									System.out.println(arr1[2]);
									String re_Id = arr1[4].substring(
											arr1[4].indexOf(":") + 2,
											arr1[4].indexOf(",")).trim();

									Appl_Req_ID = Long.parseLong(re_Id);

									//call error table method to maintain logs in backend
									dbCon.insertErrorErrData("ID", Appl_Req_ID,topicName, intOffset,intPartition_Number, JsongetString,err_code, message, "-1", "sysdate","-1", "sysdate", aibErrorTable,errorTableColsList);

								}
								dupcounter++;
								System.out.println("Partition :"
										+ intPartition_Number
										+ " Offset going to updated :"
										+ intOffset);

								continue;
							}
							continue;
						}
						// hm.put(intPartition_Number, intOffset);

						if (size == uniDataBatch.size()) {
							err_msgs = "another error";
							dupcounter++;
						} else {
							prepareBatchQuery(comm, responseObj, intOffset,	JsongetString, intPartition_Number, counter);
							counter++;
						}
						size = uniDataBatch.size();

						System.out.println("----- Size : " + size);

						if (i % 5 == 0) {
							long lastOffset = partitionRecords.get(
									partitionRecords.size() - 1).offset();
							System.out.println("----- lastOffset : "
									+ lastOffset);
						}
						if (++i % batchSize == 0) {
							System.out.println("----- Firt batchsize : ");
							if (uniDataBatch.size() > 0
									|| dupDataBatchMain.size() > 0) {
								if (uniDataBatch.size() > 0) {
									//dbCon.executeACATSaveCustomerType(p_customer_inc_exc_tab_i);
									dbCon.executeACATSaveCustomerType(acatCounsumerRespBean,intOffset);//, dealIdEntryDeleted, dealIdEntryDelelegalInclusionEntitiesDeletedted, inclusionAdditionalParamsRulesDeleted, legalExclusionEntitiesDeleted, exclusionAdditionalParamsRulesDeleted);

								}
								if (dupDataBatchMain.size() > 0) {

								}
								insertFlag = true;
							}
							insertFlag = true;

							uniDataBatch.clear();
							uniqDataBatch.clear();
							uniDataBatchMain.clear();
							dupDataBatchMain.clear();
							size = 0;
							counter = 0;
							dupcounter = 0;
						}
					}
					long lastOffset = partitionRecords.get(partitionRecords.size() - 1).offset();
				}

				if (uniDataBatch.size() > 0 || dupDataBatchMain.size() > 0) {
					if (uniDataBatch.size() > 0) {
						System.out.println("2. ========== Insert into Unique table =======");
						//dbCon.executeACATSaveCustomerType(p_customer_inc_exc_tab_i);
						dbCon.executeACATSaveCustomerType(acatCounsumerRespBean, intOffset);//, dealIdEntryDeleted, dealIdEntryDelelegalInclusionEntitiesDeletedted, inclusionAdditionalParamsRulesDeleted, legalExclusionEntitiesDeleted, exclusionAdditionalParamsRulesDeleted);

					}
					if (dupDataBatchMain.size() > 0) {
						System.out.println("2. ========= Insert into Duplicate table =========");

					}
					insertFlag = true;
					uniDataBatch.clear();
					uniqDataBatch.clear();
					uniDataBatchMain.clear();
					dupDataBatchMain.clear();
					size = 0;
					counter = 0;
					dupcounter = 0;
					System.out.println("Insert flag : " + insertFlag+ ", Unique batch Size : "+ uniDataBatch.size()+ ", Duplicate batch Size : "+ dupDataBatch.size());
				}
				insertFlag = false;
			}
		} catch (Exception e) {
			System.out.println("Exception in Consumer class : "
					+ e.getMessage());
			e.printStackTrace();
			String errMsg = e.getMessage().toString();
			if (errMsg.contains("poll()")) {
				System.out.println("True Contain");
			} else {
				System.out.println("Please check the other Ex exception");
			}
		} finally {
			consumer.close();
			System.out.println("===========we are in finally ===========");
		}
	}

	public static void prepareBatchQuery(commonUtility commObj,ACATCounsumerRespBean acatCounsumerRespBean1, Integer offset, String JsongetString,int Partition_Number,  int counter) {

		try {
			System.out.println("********* Counter : " + counter);

			//System.out.println("customerId: "+acatCounsumerRespBean.getSub_ref_id());
			
			acatCounsumerRespBean=acatCounsumerRespBean1;
			
			//customerId.add(acatCounsumerRespBean.getSub_ref_id());
			/*dealIdEntryDeleted.add(acatCounsumerRespBean.getDealIdEntryDeleted());
			dealIdEntryDelelegalInclusionEntitiesDeletedted.add(acatCounsumerRespBean.getDealIdEntryDelelegalInclusionEntitiesDeletedted());
			inclusionAdditionalParamsRulesDeleted.add(acatCounsumerRespBean.getInclusionAdditionalParamsRulesDeleted());
			legalExclusionEntitiesDeleted.add(acatCounsumerRespBean.getLegalExclusionEntitiesDeleted());
			exclusionAdditionalParamsRulesDeleted.add(acatCounsumerRespBean.getExclusionAdditionalParamsRulesDeleted());*/
		} catch (Exception e) {
			System.out.println("Exception in prepareBatchQuery - 1:");
			e.printStackTrace();
			System.out.println("Exception in prepareBatchQuery : "+ e.getMessage());
		}
	}


}

//	static List<CustomerIncExc>p_customer_inc_exc_tab_i= new ArrayList<CustomerIncExc>();//customerIncExcBeans
