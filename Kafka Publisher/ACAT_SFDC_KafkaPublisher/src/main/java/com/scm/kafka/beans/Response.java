package com.scm.kafka.beans;

public class Response {

	private String message;
	private long offset;
	private int partition;
	private String topic;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public long getOffset() {
		return offset;
	}

	public void setOffset(long offset) {
		this.offset = offset;
	}

	public int getPartition() {
		return partition;
	}

	public void setPartition(int partition) {
		this.partition = partition;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	@Override
	public String toString() {
		return "Response [message=" + message + ", offset=" + offset + ", partition=" + partition + ", topic=" + topic
				+ "]";
	}

}
