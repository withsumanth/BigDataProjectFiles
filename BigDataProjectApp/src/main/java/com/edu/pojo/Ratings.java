package com.edu.pojo;

public class Ratings {
	
	String productName;
	double productRating;
	
	
	
	public Ratings() {
		
	}

	public Ratings(String productName, double productRating) {
		this.productName = productName;
		this.productRating = productRating;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public double getProductRating() {
		return productRating;
	}

	public void setProductRating(double productRating) {
		this.productRating = productRating;
	}
	
}
