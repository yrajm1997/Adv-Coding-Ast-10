package com.mystore.orders.service;

import com.mystore.orders.dto.OrderRequest;
import com.mystore.orders.dto.OrderResponse;
import com.mystore.orders.dto.Product;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Random;

@RefreshScope
@Service
public class OrderService {

    String GET_PROD_URL  = "http://product-service/products/{id}";

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient ;

    public OrderResponse placeOrder(OrderRequest orderRequest) {

        //List<ServiceInstance> instances = discoveryClient.getInstances("product-service");

/*        System.out.println("Instances running....");
        for (ServiceInstance s : instances){
            System.out.println(s.getHost()+":"+s.getPort());
        }

        ServiceInstance serviceInstance = instances.get(0);

        String hostName = serviceInstance.getHost();
        int port = serviceInstance.getPort();

        String GET_PROD_URL = "http://"+hostName + ":" + port + "/products/{id}";

        System.out.println("Get product Endpoint URL = " + GET_PROD_URL);*/


        // 1. retrieve the product details from the product service
        Integer prodId = orderRequest.getProductId();
        String url = GET_PROD_URL.replace("{id}", prodId.toString());
        System.out.println("URL  " + url);


        ResponseEntity<Product> productResponseEntity = restTemplate.getForEntity(url, Product.class);
        Product product = productResponseEntity.getBody();

        // 2. process the order

        Long orderId = new Random().nextLong();
        Double totalPrice = orderRequest.getQty() * product.getPrice();

        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setOrderId(orderId);
        orderResponse.setProductId(prodId);
        orderResponse.setTotalPrice(totalPrice);
        orderResponse.setQty(orderRequest.getQty());
        orderResponse.setProductName(product.getName());

        // 3. return the response
        return orderResponse;
    }

}
