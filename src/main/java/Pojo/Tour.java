package Pojo;

import java.sql.Date;
public class Tour {
	
	int tourId;
	int companyId;
    private String title;
    private String description;
    private String imageUrl;
    private int price;
    private int numberOfPersons;
    private String location;
    private int numberOfDays;
    private Date departureDate;
    

    // Constructor
    public Tour(int tid,int cid,String title, String description, String imageUrl, int price, int numberOfPersons, String location, int numberOfDays, Date departureDate) {
        this.tourId=tid;
        this.companyId=cid;
    	this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.price = price;
        this.numberOfPersons = numberOfPersons;
        this.location = location;
        this.numberOfDays = numberOfDays;
        this.departureDate = departureDate;
    }
    
    public int getTourId() {
		return tourId;
	}

	public void setTourId(int tourId) {
		this.tourId = tourId;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

    // Getter and setter methods for each attribute
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getNumberOfPersons() {
        return numberOfPersons;
    }

    public void setNumberOfPersons(int numberOfPersons) {
        this.numberOfPersons = numberOfPersons;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(int numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }
    public String toString() {
        return "Tour{" +
                "tourId=" + tourId +
                ", companyId=" + companyId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", price=" + price +
                ", numberOfPersons=" + numberOfPersons +
                ", location='" + location + '\'' +
                ", numberOfDays=" + numberOfDays +
                ", departureDate='" + departureDate + '\'' +
                '}';
    }

}
