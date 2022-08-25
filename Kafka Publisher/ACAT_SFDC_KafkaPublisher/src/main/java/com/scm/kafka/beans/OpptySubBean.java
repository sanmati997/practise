package com.scm.kafka.beans;

import java.util.List;

public class OpptySubBean {
	 //private List<OplHeader> headers;
	 OpptyDetails opptyDetails;
	 List<OpptyLines> opptyLines ;
	 
	public OpptyDetails getOpptyDetails() {
		return opptyDetails;
	}
	public void setOpptyDetails(OpptyDetails opptyDetails) {
		this.opptyDetails = opptyDetails;
	}
	public List<OpptyLines> getOpptyLines() {
		return opptyLines;
	}
	public void setOpptyLines(List<OpptyLines> opptyLines) {
		this.opptyLines = opptyLines;
	}
	 

  
}
