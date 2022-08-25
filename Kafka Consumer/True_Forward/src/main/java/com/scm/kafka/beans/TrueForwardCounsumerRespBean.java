package com.scm.kafka.beans;
import java.util.List;
import com.scm.kafka.main.Lines;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.scm.kafka.main.Header;

//@JsonIgnoreProperties(ignoreUnknown=true)
public class TrueForwardCounsumerRespBean {

//    @JsonProperty("header")
	public List<Header>header;
//    @JsonProperty("lines")
    public List<Lines>lines;
	
	

	public List<Header> getheader() {
		return header;
	}
	public void setheader(List<Header> header) {
		this.header = header;
	}
	public List<Lines> getlines() {
		return lines;
	}
	public void setLines(List<Lines> lines) {
		this.lines = lines;
	}

	
}
