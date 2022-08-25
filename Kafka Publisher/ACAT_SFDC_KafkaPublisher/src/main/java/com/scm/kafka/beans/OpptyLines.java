package com.scm.kafka.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OpptyLines {
	
	@JsonProperty("ServiceProgram")
	private String ServiceProgram;
	@JsonProperty("ServiceLevel")
	private String ServiceLevel ;
	@JsonProperty("BusinessEntityCCWC")
	private String BusinessEntityCCWC;
	@JsonProperty("BusinessSubEntityCCWC")
	private String BusinessSubEntityCCWC ;
	@JsonProperty("ServiceCategory")
	private String ServiceCategory;
	@JsonProperty("AllocatedServiceGroup")
	private String AllocatedServiceGroup;
	@JsonProperty("UniqueSpSlScC")
	private String UniqueSpSlScC;
	@JsonProperty("MixC")
	private double  MixC;
	@JsonProperty("ExpectedServiceNetPriceC")
	private long ExpectedServiceNetPriceC;
	@JsonProperty("Term")
	private long Term ;
	@JsonProperty("ServiceWithServiceLevelC")
	private String ServiceWithServiceLevelC;
	@JsonProperty("ServiceProgram")
	public String getServiceProgram() {
		return ServiceProgram;
	}
	@JsonProperty("ServiceProgram")
	public void setServiceProgram(String ServiceProgram) {
		this.ServiceProgram = ServiceProgram;
	}
	@JsonProperty("ServiceLevel")
	public String getServiceLevel() {
		return ServiceLevel;
	}
	@JsonProperty("ServiceLevel")
	public void setServiceLevel(String ServiceLevel) {
		this.ServiceLevel = ServiceLevel;
	}
	@JsonProperty("BusinessEntityCCWC")
	public String getBusinessEntityCCWC() {
		return BusinessEntityCCWC;
	}
	@JsonProperty("BusinessEntityCCWC")
	public void setBusinessEntityCCWC(String BusinessEntityCCWC) {
		this.BusinessEntityCCWC = BusinessEntityCCWC;
	}
	@JsonProperty("BusinessSubEntityCCWC")
	public String getBusinessSubEntityCCWC() {
		return BusinessSubEntityCCWC;
	}
	@JsonProperty("BusinessSubEntityCCWC")
	public void setBusinessSubEntityCCWC(String BusinessSubEntityCCWC) {
		this.BusinessSubEntityCCWC = BusinessSubEntityCCWC;
	}
	@JsonProperty("ServiceCategory")
	public String getServiceCategory() {
		return ServiceCategory;
	}
	@JsonProperty("ServiceCategory")
	public void setServiceCategory(String ServiceCategory) {
		this.ServiceCategory = ServiceCategory;
	}
	@JsonProperty("AllocatedServiceGroup")
	public String getAllocatedServiceGroup() {
		return AllocatedServiceGroup;
	}
	@JsonProperty("AllocatedServiceGroup")
	public void setAllocatedServiceGroup(String AllocatedServiceGroup) {
		this.AllocatedServiceGroup = AllocatedServiceGroup;
	}
	@JsonProperty("UniqueSpSlScC")
	public String getUniqueSpSlScC() {
		return UniqueSpSlScC;
	}
	@JsonProperty("UniqueSpSlScC")
	public void setUniqueSpSlScC(String UniqueSpSlScC) {
		this.UniqueSpSlScC = UniqueSpSlScC;
	}
	@JsonProperty("MixC")
	public double getMixC() {
		return MixC;
	}
	@JsonProperty("MixC")
	public void setMixC(double MixC) {
		this.MixC = MixC;
	}
	@JsonProperty("ExpectedServiceNetPriceC")
	public long getExpectedServiceNetPriceC() {
		return ExpectedServiceNetPriceC;
	}
	@JsonProperty("ExpectedServiceNetPriceC")
	public void setExpectedServiceNetPriceC(long ExpectedServiceNetPriceC) {
		this.ExpectedServiceNetPriceC = ExpectedServiceNetPriceC;
	}
	@JsonProperty("Term")
	public long getTerm() {
		return Term;
	}
	@JsonProperty("Term")
	public void setTerm(long Term) {
		this.Term = Term;
	}
	@JsonProperty("ServiceWithServiceLevelC")
	public String getServiceWithServiceLevelC() {
		return ServiceWithServiceLevelC;
	}
	@JsonProperty("ServiceWithServiceLevelC")
	public void setServiceWithServiceLevelC(String ServiceWithServiceLevelC) {
		this.ServiceWithServiceLevelC = ServiceWithServiceLevelC;
	}
	@Override
	public String toString() {
		return "OpptyLines [ServiceProgram=" + ServiceProgram + ", ServiceLevel=" + ServiceLevel
				+ ", BusinessEntityCCWC=" + BusinessEntityCCWC + ", BusinessSubEntityCCWC=" + BusinessSubEntityCCWC
				+ ", ServiceCategory=" + ServiceCategory + ", AllocatedServiceGroup=" + AllocatedServiceGroup
				+ ", UniqueSpSlScC=" + UniqueSpSlScC + ", MixC=" + MixC + ", ExpectedServiceNetPriceC="
				+ ExpectedServiceNetPriceC + ", Term=" + Term + ", ServiceWithServiceLevelC=" + ServiceWithServiceLevelC
				+ "]";
	}
	

}
