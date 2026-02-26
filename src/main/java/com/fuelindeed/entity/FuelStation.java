package com.fuelindeed.entity;

import java.time.LocalDateTime;

import com.fuelindeed.enums.StationStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "fuel_station")
@Getter
@Setter
public class FuelStation {
	
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private String name;
	    private String openTime;
	    private String closeTime;

	    private String address;
	    private String area;
	    private String city;
	    private String pincode;

	    // ⛽ Fuel stock & rates
	    private double petrolQty;
	    private double petrolRate;
	   

		private String role;
	    private double dieselQty;
	    private double dieselRate;

	    // ✅ Fuel availability (NEW)
	    private boolean petrolAvailable;
	    private boolean dieselAvailable;

	    // ⏱️ Last update time (NEW)
	    private LocalDateTime lastUpdated;

	    private String contact;

	    @Column(unique = true)
	    private String email;

	    private String password;

	    @Enumerated(EnumType.STRING)
	    private StationStatus status;
	    
	    
	    private String imageUrl;

	    public String getImageUrl() {
	        return imageUrl;
	    }

	    public void setImageUrl(String imageUrl) {
	        this.imageUrl = imageUrl;
	    }
	    
	    
	    public boolean isApproved() {
			return approved;
		}

		public void setApproved(boolean approved) {
			this.approved = approved;
		}

		private boolean approved;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getOpenTime() {
			return openTime;
		}

		public void setOpenTime(String openTime) {
			this.openTime = openTime;
		}

		public String getCloseTime() {
			return closeTime;
		}

		public void setCloseTime(String closeTime) {
			this.closeTime = closeTime;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public String getArea() {
			return area;
		}

		public void setArea(String area) {
			this.area = area;
		}

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public String getPincode() {
			return pincode;
		}

		public void setPincode(String pincode) {
			this.pincode = pincode;
		}

		public double getPetrolQty() {
			return petrolQty;
		}

		public void setPetrolQty(double petrolQty) {
			this.petrolQty = petrolQty;
		}

		public double getPetrolRate() {
			return petrolRate;
		}

		public void setPetrolRate(double petrolRate) {
			this.petrolRate = petrolRate;
		}

		public double getDieselQty() {
			return dieselQty;
		}

		public void setDieselQty(double dieselQty) {
			this.dieselQty = dieselQty;
		}

		public double getDieselRate() {
			return dieselRate;
		}

		public void setDieselRate(double dieselRate) {
			this.dieselRate = dieselRate;
		}

		public boolean isPetrolAvailable() {
			return petrolAvailable;
		}

		public void setPetrolAvailable(boolean petrolAvailable) {
			this.petrolAvailable = petrolAvailable;
		}

		public boolean isDieselAvailable() {
			return dieselAvailable;
		}

		public void setDieselAvailable(boolean dieselAvailable) {
			this.dieselAvailable = dieselAvailable;
		}

		public LocalDateTime getLastUpdated() {
			return lastUpdated;
		}

		public void setLastUpdated(LocalDateTime lastUpdated) {
			this.lastUpdated = lastUpdated;
		}

		public String getContact() {
			return contact;
		}

		public void setContact(String contact) {
			this.contact = contact;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public StationStatus getStatus() {
			return status;
		}

		public void setStatus(StationStatus status) {
			this.status = status;
		}
	    
		 public String getRole() {
				return role;
			}

			public void setRole(String role) {
				this.role = role;
			}
	    

}
