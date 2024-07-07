package com.product.service;

import com.product.dto.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    @Autowired
    private KafkaTemplate<String, ProductDTO> kafkaTemplate;

    private static final String TOPIC = "new-products";

    public void sendProductMessage(ProductDTO productDTO) {
        kafkaTemplate.send(TOPIC, productDTO);
    }
}

