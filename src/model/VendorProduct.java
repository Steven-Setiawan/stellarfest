package model;

public class VendorProduct {

	private int productId;
	private String productName;
	private String productDescription;
	private int vendorId;
	
	public VendorProduct(int productId, String productName, String productDescription) {
		this.productId = productId;
		this.productName = productName;
		this.productDescription = productDescription;
	}

	public int getProductId() {
		return productId;
	}

	public String getProductName() {
		return productName;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public int getVendorId() {
		return vendorId;
	}	
}
