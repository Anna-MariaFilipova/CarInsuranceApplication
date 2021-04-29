package company.carsProject.model;

import java.util.Date;

public class Message {

	private Date timestamp;
	private String message;

	public Message(Date timestamp, String message) {
		super();
		this.timestamp = timestamp;
		this.message = message;
	}
	public Message(String message) {
		this.message=message;
	}

	public Message() {
		super();
	}
	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	

}
