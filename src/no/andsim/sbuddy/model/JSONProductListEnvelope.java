package no.andsim.sbuddy.model;

import com.google.gson.annotations.SerializedName;

public class JSONProductListEnvelope {

	@SerializedName("l.ProductList")
	private ProductList productList = new ProductList();

	public ProductList getProductList() {
		return productList;
	}

	public void setProductList(ProductList productList) {
		this.productList = productList;
	}
	
}
