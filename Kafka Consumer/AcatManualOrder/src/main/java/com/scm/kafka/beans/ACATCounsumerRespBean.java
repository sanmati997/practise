package com.scm.kafka.beans;

public class ACATCounsumerRespBean {

	
	String type ;
	long customer_id;
	long request_id;
	String cec_user_id;
	String action;
	long true_up_request_id;
	long true_up_elastic_id;
	String true_up_schedule;
	String elastic_id ;
	String order_type;
	long sales_order_number;
	String comments;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public long getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(long customer_id) {
		this.customer_id = customer_id;
	}
	public long getRequest_id() {
		return request_id;
	}
	public void setRequest_id(long request_id) {
		this.request_id = request_id;
	}
	public String getCec_user_id() {
		return cec_user_id;
	}
	public void setCec_user_id(String cec_user_id) {
		this.cec_user_id = cec_user_id;
	}
	public long getTrue_up_elastic_id() {
		return true_up_elastic_id;
	}
	public void setTrue_up_elastic_id(long true_up_elastic_id) {
		this.true_up_elastic_id = true_up_elastic_id;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public long getTrue_up_request_id() {
		return true_up_request_id;
	}
	public void setTrue_up_request_id(long true_up_request_id) {
		this.true_up_request_id = true_up_request_id;
	}

	public String getTrue_up_schedule() {
		return true_up_schedule;
	}
	public void setTrue_up_schedule(String true_up_schedule) {
		this.true_up_schedule = true_up_schedule;
	}
	public String getElastic_id() {
		return elastic_id;
	}
	public void setElastic_id(String elastic_id) {
		this.elastic_id = elastic_id;
	}
	public String getOrder_type() {
		return order_type;
	}
	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}
	public long getSales_order_number() {
		return sales_order_number;
	}
	public void setSales_order_number(long sales_order_number) {
		this.sales_order_number = sales_order_number;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	
}
