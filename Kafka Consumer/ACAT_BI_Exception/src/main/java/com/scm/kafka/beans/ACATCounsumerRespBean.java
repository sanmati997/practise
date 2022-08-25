package com.scm.kafka.beans;
import java.util.List;
public class ACATCounsumerRespBean {

	
//	{
//		"corelationId":"12345",
//		"opptyID":"0060S000009hv3FQAQ",
//		"responseMessage":"Opportunity created Successfully"
//		}
	Long corelationId;
	String opptyID;
	String responseMessage;
	public Long getCorelationId() {
		return corelationId;
	}
	public void setCorelationId(Long corelationId) {
		this.corelationId = corelationId;
	}
	public String getOpptyID() {
		return opptyID;
	}
	public void setOpptyID(String opptyID) {
		this.opptyID = opptyID;
	}
	public String getResponseMessage() {
		return responseMessage;
	}
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
	
}
