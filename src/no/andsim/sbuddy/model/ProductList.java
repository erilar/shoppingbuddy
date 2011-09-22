package no.andsim.sbuddy.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class ProductList {

	@SerializedName("p.Product")
	private List<Product> product = new ArrayList<Product>();

	public List<Product> getProduct() {
		if (product == null) {
			product = new ArrayList<Product>();
		}
		return this.product;
	}

	public void setProduct(List<Product> product) {
		this.product = product;
	}

}
