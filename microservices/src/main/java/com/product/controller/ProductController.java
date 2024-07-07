//
//package com.product.controller;
//
//import com.product.dto.ProductDTO;
//import com.product.entity.Product;
//import com.product.exception.ProductAlreadyExistsException;
//import com.product.exception.ProductNotFoundException;
//import com.product.service.ProductService;
//import com.product.dto.ProductSearchDTO;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/products")
//public class ProductController {
//
//    @Autowired
//    private ProductService productService;
//
//    @GetMapping
//    public ResponseEntity<List<Product>> getAllProducts(
//            @RequestParam(defaultValue = "1") int page,
//            @RequestParam(defaultValue = "5") int size) {
//
//        List<Product> products = productService.getAllProducts(page, size);
//        return ResponseEntity.ok(products);
//    }
//
//    @GetMapping("/search")
//    public ResponseEntity<Product> searchProduct(ProductSearchDTO searchDTO) {
//
//        Product product = null;
//
//        if (searchDTO.getId() != 0) {
//            product = productService.getProductById(searchDTO.getId());
//        } else if (searchDTO.getClientId() != 0 && searchDTO.getSkuCode() != null) {
//            product = productService.getProductByClientIdAndSkuCode(searchDTO.getClientId(), searchDTO.getSkuCode());
//        } else if (searchDTO.getEmpId() != 0 && searchDTO.getSkuCode() != null) {
//            product = productService.getProductByEmpIdAndSkuCode(searchDTO.getEmpId(), searchDTO.getSkuCode());
//        }
//
//        if (product != null) {
//            return ResponseEntity.ok(product);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    @PostMapping("/add-or-update")
//    public ResponseEntity<?> addOrUpdateProduct(@RequestBody ProductDTO productDTO) {
//        try {
//            ResponseEntity<?> response = productService.addOrUpdateProduct(productDTO);
//            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
//        } catch (ProductAlreadyExistsException e) {
//            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
//        } catch (ProductNotFoundException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing product: " + e.getMessage());
//        }
//    }
//}
package com.product.controller;

import com.product.dto.ProductDTO;
import com.product.entity.Product;
import com.product.exception.ProductAlreadyExistsException;
import com.product.exception.ProductNotFoundException;
import com.product.service.ProductService;
import com.product.dto.ProductSearchDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size) {

        List<Product> products = productService.getAllProducts(page, size);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/search")
    public ResponseEntity<Product> searchProduct(ProductSearchDTO searchDTO) {

        Product product = null;

        if (searchDTO.getId() != 0) {
            product = productService.getProductById(searchDTO.getId());
        } else if (searchDTO.getClientId() != 0 && searchDTO.getSkuCode() != null) {
            product = productService.getProductByClientIdAndSkuCode(searchDTO.getClientId(), searchDTO.getSkuCode());
        } else if (searchDTO.getEmpId() != 0 && searchDTO.getSkuCode() != null) {
            product = productService.getProductByEmpIdAndSkuCode(searchDTO.getEmpId(), searchDTO.getSkuCode());
        }

        if (product != null) {
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@RequestBody ProductDTO productDTO) {
        try {
            ResponseEntity<?> response = productService.addProduct(productDTO);
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        } catch (ProductAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing product: " + e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateProduct(@RequestBody ProductDTO productDTO) {
        try {
            ResponseEntity<?> response = productService.updateProduct(productDTO);
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (ProductAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing product: " + e.getMessage());
        }
    }
}

