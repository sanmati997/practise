package com.scm.kafka.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OpptyDetails {
	
	 @JsonProperty("CorelationId")
	private long CorelationId;
	 @JsonProperty("EventType")
	private String EventType;
	 @JsonProperty("OpportunityName")
	private String OpportunityName;
	 @JsonProperty("OpportunityOwner")
	private String OpportunityOwner;
	 @JsonProperty("CRPartyID")
	private long CRPartyID ;
	 @JsonProperty("ExpectedBookDate")
	private String ExpectedBookDate;
	 @JsonProperty("ExpectedService")
	private long ExpectedService;
	 @JsonProperty("ForecastingPosition")
	private String ForecastingPosition;
	 @JsonProperty("Currency")
	private String Currency;
	 @JsonProperty("BEGEOID")
	private long BEGEOID;
	 @JsonProperty("OpportunityId")
	private String OpportunityId;
	 @JsonProperty("OpportunityType")
	private String OpportunityType;
	 @JsonProperty("ForecastCategory")
	private String ForecastCategory;
	 @JsonProperty("OpportunityFlag")
	private String OpportunityFlag;
	 @JsonProperty("TrueUpStatus")
	private String TrueUpStatus;
	
	 @JsonProperty("CorelationId")
	public long getCorelationId() {
		return CorelationId;
	}
	 @JsonProperty("CorelationId")
	public void setCorelationId(long CorelationId) {
		this.CorelationId = CorelationId;
	}
	 @JsonProperty("EventType")
	public String getEventType() {
		return EventType;
	}
	 @JsonProperty("EventType")
	public void setEventType(String EventType) {
		this.EventType = EventType;
	}
	 @JsonProperty("OpportunityName")
	public String getOpportunityName() {
		return OpportunityName;
	}
	 @JsonProperty("OpportunityName")
	public void setOpportunityName(String OpportunityName) {
		this.OpportunityName = OpportunityName;
	}
	 @JsonProperty("OpportunityOwner")
	public String getOpportunityOwner() {
		return OpportunityOwner;
	}
	 @JsonProperty("OpportunityOwner")
	public void setOpportunityOwner(String OpportunityOwner) {
		this.OpportunityOwner = OpportunityOwner;
	}
	 @JsonProperty("CRPartyID")
	public long getCRPartyID() {
		return CRPartyID;
	}
	 @JsonProperty("CRPartyID")
	public void setCRPartyID(long CRPartyID) {
		this.CRPartyID = CRPartyID;
	}
	 @JsonProperty("ExpectedBookDate")
	public String getExpectedBookDate() {
		return ExpectedBookDate;
	}
	 @JsonProperty("ExpectedBookDate")
	public void setExpectedBookDate(String ExpectedBookDate) {
		this.ExpectedBookDate = ExpectedBookDate;
	}
	 @JsonProperty("ExpectedService")
	public long getExpectedService() {
		return ExpectedService;
	}
	 @JsonProperty("ExpectedService")
	public void setExpectedService(long ExpectedService) {
		this.ExpectedService = ExpectedService;
	}
	 @JsonProperty("ForecastingPosition")
	public String getForecastingPosition() {
		return ForecastingPosition;
	}
	 @JsonProperty("ForecastingPosition")
	public void setForecastingPosition(String ForecastingPosition) {
		this.ForecastingPosition = ForecastingPosition;
	}
	 @JsonProperty("Currency")
	public String getCurrency() {
		return Currency;
	}
	 @JsonProperty("Currency")
	public void setCurrency(String Currency) {
		this.Currency = Currency;
	}
	 @JsonProperty("BEGEOID")
	public long getBEGEOID() {
		return BEGEOID;
	}
	 @JsonProperty("BEGEOID")
	public void setBEGEOID(long BEGEOID) {
		this.BEGEOID = BEGEOID;
	}
	 @JsonProperty("OpportunityId")
	public String getOpportunityId() {
		return OpportunityId;
	}
	 @JsonProperty("OpportunityId")
	public void setOpportunityId(String OpportunityId) {
		this.OpportunityId = OpportunityId;
	}
	 @JsonProperty("OpportunityType")
	public String getOpportunityType() {
		return OpportunityType;
	}
	 @JsonProperty("OpportunityType")
	public void setOpportunityType(String OpportunityType) {
		this.OpportunityType = OpportunityType;
	}
	 @JsonProperty("ForecastCategory")
	public String getForecastCategory() {
		return ForecastCategory;
	}
	 @JsonProperty("ForecastCategory")
	public void setForecastCategory(String ForecastCategory) {
		this.ForecastCategory = ForecastCategory;
	}
	 @JsonProperty("OpportunityFlag")
	public String getOpportunityFlag() {
		return OpportunityFlag;
	}
	@JsonProperty("OpportunityFlag")
	public void setOpportunityFlag(String OpportunityFlag) {
		this.OpportunityFlag = OpportunityFlag;
	}
	@JsonProperty("TrueUpStatus")
	public String getTrueUpStatus() {
		return TrueUpStatus;
	}
	@JsonProperty("TrueUpStatus")
	public void setTrueUpStatus(String TrueUpStatus) {
		this.TrueUpStatus = TrueUpStatus;
	}
	@Override
	public String toString() {
		return "OpptyDetails [CorelationId=" + CorelationId + ", EventType=" + EventType + ", OpportunityName="
				+ OpportunityName + ", OpportunityOwner=" + OpportunityOwner + ", CRPartyID=" + CRPartyID
				+ ", ExpectedBookDate=" + ExpectedBookDate + ", ExpectedService=" + ExpectedService
				+ ", ForecastingPosition=" + ForecastingPosition + ", Currency=" + Currency + ", BEGEOID=" + BEGEOID
				+ ", OpportunityId=" + OpportunityId + ", OpportunityType=" + OpportunityType + ", ForecastCategory="
				+ ForecastCategory + ", OpportunityFlag=" + OpportunityFlag + ", TrueUpStatus=" + TrueUpStatus + "]";
	}
	
}
