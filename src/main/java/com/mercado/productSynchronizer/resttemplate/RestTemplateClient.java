package com.mercado.productSynchronizer.resttemplate;

import com.mercado.productSynchronizer.dto.Product;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestTemplateClient {

    @Autowired
    RestTemplate restTemplate;

    public Product createProduct(Product product) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Product> entity = new HttpEntity<Product>(product, headers);
        return restTemplate
            .exchange(
                "http://localhost:8000/create", HttpMethod.POST, entity, Product.class)
            .getBody();
    }
}
