package com.infy.poc.ProductAggregatorServiceGraphQLPoc.graphql.datafetcher;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.infy.poc.ProductAggregatorServiceGraphQLPoc.DTO.Product;
import com.infy.poc.ProductAggregatorServiceGraphQLPoc.DTO.ProductInventory;
import com.infy.poc.ProductAggregatorServiceGraphQLPoc.DTO.ProductInventoryDetail;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

@Component
public class ProductInventoryDataFatcher implements DataFetcher<ProductInventoryDetail> {

	@Autowired
	public RestTemplate restTemplate;
	//@Autowired
	//OAuthUser oAuthUser;
	
	public static final String token = "Bearer ya29.GlxcBzlH6zUNGlWDdKrSa5a4-DYKIkDJcCF-o7NJGBLLNEA0nw4SkBrgyChMgLLJkysPKjTRA0nST313nQPsv19PGbF0iJ-fEmbo7A8VeFJUbyoIDpBs_CLgkRZrdg";

	

	@Override
	public ProductInventoryDetail get(DataFetchingEnvironment dataFetchingEnvironment) {

		int productID = dataFetchingEnvironment.getArgument("id");

	//	MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
	   // headers.add("Authorization", String.format("%s %s", oAuthUser.getTokenType(), oAuthUser.getAccessToken()));
		
		HttpHeaders headers = new HttpHeaders();
		//headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		//headers.add("Authorization", String.format("%s %s", oAuthUser.getTokenType(), oAuthUser.getAccessToken()));
		headers.add("Authorization", token.trim());
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		//System.out.println("Access Token "+oAuthUser.getAccessToken());
		Product product = restTemplate.exchange("https://cpspocgraphql.appspot.com/product/productService/".trim() + productID,
		HttpMethod.GET, entity, Product.class).getBody();
		System.out.println("outputIs:" + product);

		ProductInventory productInv = restTemplate
				.exchange("https://cpspocgraphql.appspot.com/product/productInventoryService/".trim() + productID, HttpMethod.GET, entity,
						ProductInventory.class)
				.getBody();
		System.out.println("outputIs:" + productInv);

		ProductInventoryDetail pIDetail = new ProductInventoryDetail();
		pIDetail.setProductID(product.getProductID());
		pIDetail.setProductName(product.getProductName());
		pIDetail.setProductInventory(productInv.getProductInventory());

		return pIDetail;
	}

}
