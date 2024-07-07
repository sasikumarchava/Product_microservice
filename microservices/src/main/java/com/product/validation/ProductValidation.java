package com.product.validation;

import com.product.dto.ProductDTO;
import com.product.exception.ProductAlreadyExistsException;
import com.product.exception.ProductNotFoundException;
import com.product.dao.ProductDAO;
import com.product.constant.ProductConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductValidation {

    @Autowired
    private ProductDAO productDAO;

    public void validateProductDTO(ProductDTO productDTO) {

        if (productDTO == null || productDTO.getId() <= 0) {
            throw new IllegalArgumentException("Invalid product DTO");
        }
    }
    public void validateProductExists(int id) {
        if (productDAO.getProductById(id) == null) {
            throw new ProductNotFoundException(ProductConstants.PRODUCT_NOT_FOUND + id);
        }
    }

    public void validateUniqueProduct(ProductDTO productDTO) {
        if (productDAO.existsByClientIdOrEmpIdOrSkuCode(productDTO.getClientId(), productDTO.getEmpId(), productDTO.getSkuCode())) {
            throw new ProductAlreadyExistsException(ProductConstants.PRODUCT_ALREADY_EXISTS);
        }
    }
}
