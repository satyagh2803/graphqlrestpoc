package com.infy.poc.ProductServicesSpringBootPoc.controller;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infy.poc.ProductServicesSpringBootPoc.dao.ProductDAO;
import com.infy.poc.ProductAggregatorServiceGraphQLPoc.DTO.Product;
import com.infy.poc.ProductAggregatorServiceGraphQLPoc.DTO.ProductInventory;
import com.infy.poc.ProductAggregatorServiceGraphQLPoc.DTO.ProductInventoryDetail;

@RestController
@RequestMapping(path = "/product")
public class ProductServiceController {

	@Autowired
	private ProductDAO productDao;

	@Autowired
	RestTemplate restTemplate;

	@GetMapping(path = "/productService/{productID}", produces = "application/json")
	public Product getProduct(@PathVariable(value = "productID") int productID) {
		return productDao.getProductDetail(productID);
	}

	@GetMapping(path = "/productInventoryService/{productID}", produces = "application/json")
	public ProductInventory getProductInventory(@PathVariable(value = "productID") int productID) {
		return productDao.getProductInventory(productID);
	}

	@GetMapping(value = "/productAggregatorService/{productID}", produces = "application/json")
	public ProductInventoryDetail getProductList(@PathVariable(value = "productID") int productID) {

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(headers);

		Product product = restTemplate.exchange("http://localhost:8080/product/productService/" + productID,
				HttpMethod.GET, entity, Product.class).getBody();
		System.out.println("outputIs:" + product);

		ProductInventory productInv = restTemplate
				.exchange("http://localhost:8080/product/productInventoryService/" + productID, HttpMethod.GET, entity,
						ProductInventory.class)
				.getBody();
		System.out.println("outputIs:" + productInv);

		ProductInventoryDetail detail = new ProductInventoryDetail();

		detail.setProductID(product.getProductID());
		detail.setProductName(product.getProductName());
		// detail.setProductDescription(product.getProductDescription());
		detail.setProductInventory(productInv.getProductInventory());
		System.out.println("detail :" + detail);

		// java object convert to json

		// Creating Object of ObjectMapper define in Jakson Api
		ObjectMapper Obj = new ObjectMapper();
		String jsonStr = null;

		try {

			// get Oraganisation object as a json string
			jsonStr = Obj.writeValueAsString(detail);

			// Displaying JSON String
			System.out.println(jsonStr);
		}

		catch (IOException e) {
			e.printStackTrace();
		}

		return detail;
	}

}
