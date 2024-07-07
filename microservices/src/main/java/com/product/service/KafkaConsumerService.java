package com.product.service;

import com.product.dto.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @Autowired
    private ProductService productService;

    @KafkaListener(topics = "product_topic", groupId = "product-group")
    public void consumeProductMessage(ProductDTO productDTO) {
        productService.addProduct(productDTO);
    }
}
