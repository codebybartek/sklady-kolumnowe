package pl.kielce.tu.cassandra.mapper;

public class Address {
	private String street;
	private Integer houseNumber;
	private Integer apartmentNumber;
	
	public Address() {	
	}
	
	public Address(String street, Integer houseNumber, Integer apartmentNumber) {
		this.street = street;
		this.houseNumber = houseNumber;
		this.apartmentNumber = apartmentNumber;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public Integer getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(Integer houseNumber) {
		this.houseNumber = houseNumber;
	}

	public Integer getApartmentNumber() {
		return apartmentNumber;
	}

	public void setApartmentNumber(Integer apartmentNumber) {
		this.apartmentNumber = apartmentNumber;
	}	
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder()
			.append("{")
			.append(street + ",")
			.append(houseNumber + ",")
			.append(apartmentNumber + ",")
			.append("}");
		return builder.toString();
	}	
}