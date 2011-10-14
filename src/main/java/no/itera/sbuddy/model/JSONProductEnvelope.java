package no.itera.sbuddy.model;

import com.google.gson.annotations.SerializedName;

public class JSONProductEnvelope {

	@SerializedName("p.Product")
	private Product product;

	public JSONProductEnvelope(Product product) {
		super();
		this.product = product;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
}
