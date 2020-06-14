package main.java;

public class Ticket {
	private String type;
	private String place;
	private String dateOfCreation;
	
	public Ticket() {
	}
	
	public Ticket(String type, String place, String dateOfCreation) {
		this.type = type;
		this.place = place;
		this.dateOfCreation = dateOfCreation;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getDate() {
		return dateOfCreation;
	}

	public void setDate(String dateOfCreation) {
		this.dateOfCreation = dateOfCreation;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder()
				.append("{")
				.append("\"creation_date\":\"" + dateOfCreation + "\",")
				.append("\"place\":\"" + place + "\",")
				.append("\"type\":\"" + type + "\"")
				.append("}");
		return builder.toString();
	}

}