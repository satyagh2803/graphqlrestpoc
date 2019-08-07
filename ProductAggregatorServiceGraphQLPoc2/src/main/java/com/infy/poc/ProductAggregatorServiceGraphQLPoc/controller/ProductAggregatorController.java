package com.infy.poc.ProductAggregatorServiceGraphQLPoc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infy.poc.ProductAggregatorServiceGraphQLPoc.DTO.Product;
import com.infy.poc.ProductAggregatorServiceGraphQLPoc.DTO.ProductInventory;
import com.infy.poc.ProductAggregatorServiceGraphQLPoc.graphql.GraphQLService;
import com.infy.poc.ProductServicesSpringBootPoc.dao.ProductDAO;

import graphql.ExecutionResult;

@RestController
public class ProductAggregatorController {
	
	@Autowired
	GraphQLService graphQLService;
	
	@Autowired
	private ProductDAO productDao;

	@PostMapping(path = "/productAggregator")
	public ResponseEntity<Object> getProductDetail(@RequestBody String query) {
		ExecutionResult execute = graphQLService.getGraphQL().execute(query);

		return new ResponseEntity<>(execute, HttpStatus.OK);
	}
	
	@GetMapping(path = "/product/productService/{productID}", produces = "application/json")
	public Product getProduct(@PathVariable(value = "productID") int productID) {
		return productDao.getProductDetail(productID);
	}

	@GetMapping(path = "/product/productInventoryService/{productID}", produces = "application/json")
	public ProductInventory getProductInventory(@PathVariable(value = "productID") int productID) {
		return productDao.getProductInventory(productID);
	}
}
