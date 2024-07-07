package com.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class ProductDTO {

    private int id;
    private int clientId;
    private int empId;
    private String skuCode;
    private String name;
    private boolean enable;

}

