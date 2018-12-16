package com.edu.pojo;

public class FiveStars {
	String marketplace;
	String customer_id;
	String review_id;
	String product_id;
	String product_parent;
	String product_title;
	String product_category;
	String total_votes;
	String review_headline;
	public FiveStars(String marketplace, String customer_id, String review_id, String product_id, String product_parent,
			String product_title, String product_category, String total_votes, String review_headline) {
		super();
		this.marketplace = marketplace;
		this.customer_id = customer_id;
		this.review_id = review_id;
		this.product_id = product_id;
		this.product_parent = product_parent;
		this.product_title = product_title;
		this.product_category = product_category;
		this.total_votes = total_votes;
		this.review_headline = review_headline;
	}
	public String getMarketplace() {
		return marketplace;
	}
	public void setMarketplace(String marketplace) {
		this.marketplace = marketplace;
	}
	public String getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}
	public String getReview_id() {
		return review_id;
	}
	public void setReview_id(String review_id) {
		this.review_id = review_id;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public String getProduct_parent() {
		return product_parent;
	}
	public void setProduct_parent(String product_parent) {
		this.product_parent = product_parent;
	}
	public String getProduct_title() {
		return product_title;
	}
	public void setProduct_title(String product_title) {
		this.product_title = product_title;
	}
	public String getProduct_category() {
		return product_category;
	}
	public void setProduct_category(String product_category) {
		this.product_category = product_category;
	}
	public String getTotal_votes() {
		return total_votes;
	}
	public void setTotal_votes(String total_votes) {
		this.total_votes = total_votes;
	}
	public String getReview_headline() {
		return review_headline;
	}
	public void setReview_headline(String review_headline) {
		this.review_headline = review_headline;
	}
	
	
}
