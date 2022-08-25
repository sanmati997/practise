package com.scm.kafka.main;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonProperty;



public class Header
{
	//Header Objects
	
//	Long  transactionId ;	
//	String transactionType ;
//	String subTransactionType ;
//    Long webOrderID ;
//     String subRefId;
//    Long numberOfLines;

	private long transaction_id;
	private String transaction_type;
	private String sub_transaction_type;
	private long web_ord_id;
	private String sub_ref_id;
	private long number_of_lines;
	private long c3_request_id;
	
	public long getTransaction_id() {
		return transaction_id;
	}
	public void setTransaction_id(long transaction_id) {
		this.transaction_id = transaction_id;
	}
	public String getTransaction_type() {
		return transaction_type;
	}
	public void setTransaction_type(String transaction_type) {
		this.transaction_type = transaction_type;
	}
	public String getSub_transaction_type() {
		return sub_transaction_type;
	}
	public void setSub_transaction_type(String sub_transaction_type) {
		this.sub_transaction_type = sub_transaction_type;
	}
	public long getWeb_ord_id() {
		return web_ord_id;
	}
	public void setWeb_ord_id(long web_ord_id) {
		this.web_ord_id = web_ord_id;
	}
	
	public String getSub_ref_id() {
		return sub_ref_id;
	}
	public void setSub_ref_id(String sub_ref_id) {
		this.sub_ref_id = sub_ref_id;
	}
	public long getNumber_of_lines() {
		return number_of_lines;
	}
	public void setNumber_of_lines(long number_of_lines) {
		this.number_of_lines = number_of_lines;
	}
	public long getC3_request_id() {
		return c3_request_id;
	}
	public void setC3_request_id(long c3_request_id) {
		this.c3_request_id = c3_request_id;
	}
	
	
}
    