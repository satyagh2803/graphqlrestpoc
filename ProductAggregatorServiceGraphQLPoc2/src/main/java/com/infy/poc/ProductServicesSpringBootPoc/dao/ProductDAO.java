package com.infy.poc.ProductServicesSpringBootPoc.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.infy.poc.ProductAggregatorServiceGraphQLPoc.DTO.Product;
import com.infy.poc.ProductAggregatorServiceGraphQLPoc.DTO.ProductInventory;



@Repository
public class ProductDAO {

	private static List<Product> products = new ArrayList<Product>();
	private static List<ProductInventory> productInventoryDetails = new ArrayList<ProductInventory>();
	static {
		products.add(new Product(1, "iPhone", "iPhone is a line of smartphones designed and marketed by Apple Inc"));
		products.add(new Product(2, "Samsung",
				"Samsung was founded as a grocery trading store on March 1, 1938, by Lee Byung-Chull."));
		products.add(new Product(3, "Xiaomi",
				"Xiaomi Corporation is a Chinese electronics company headquartered in Beijing. Xiaomi makes and invests in smartphones, mobile apps, laptops"));

		productInventoryDetails.add(new ProductInventory(1, 150));
		productInventoryDetails.add(new ProductInventory(2, 250));
		productInventoryDetails.add(new ProductInventory(3, 550));
	}

	public List<Product> getProducts() {
		return products;
	}

	public List<ProductInventory> getProductsInventory() {
		return productInventoryDetails;
	}

	public Product getProductDetail(int productID) {
		Product p = new Product();
		for (Product product : products) {
			if (product.getProductID() == productID)
				p = product;

		}
		return p;
	}

	public ProductInventory getProductInventory(int productID) {
		ProductInventory pInv = new ProductInventory();
		for (ProductInventory pInventory : productInventoryDetails) {
			if (pInventory.getProductID() == productID)
				pInv = pInventory;
		}
		return pInv;
	}

}
