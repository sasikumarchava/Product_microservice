package com.product.dto;


import lombok.Data;

@Data
public class ProductSearchDTO {
    private int id;
    private int clientId;
    private int empId;
    private String skuCode;
}

