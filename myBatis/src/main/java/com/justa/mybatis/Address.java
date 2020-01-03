package com.justa.mybatis;

public class Address {
	
	private int addressId;
	private String streetAddress; 
	private int personId;
	
	public int getAddressId() {
		return addressId;
	}
	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}
	public String getStreetAddress() {
		return streetAddress;
	}
	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}
	public int getPersonId() {
		return personId;
	}
	public void setPersonId(int personId) {
		this.personId = personId;
	}
	
	@Override
	public String toString() {
		return "id=" +addressId 
				+", streetAddress:" + streetAddress; 

	}

}
