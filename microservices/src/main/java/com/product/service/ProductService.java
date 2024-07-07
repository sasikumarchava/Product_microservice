////package com.product.service;
////
////import com.product.dto.ProductDTO;
////import com.product.entity.Product;
////import org.springframework.http.ResponseEntity;
////
////import java.util.List;
////
////public interface ProductService {
////
////    List<Product> getAllProducts(int page, int size);
////
////    Product getProductById(int id);
////
////    Product getProductByClientIdAndSkuCode(int clientId, String skuCode);
////
////    Product getProductByEmpIdAndSkuCode(int empId, String skuCode);
////
////    void addProduct(ProductDTO productDTO);
////
////    ResponseEntity<?> updateProduct(ProductDTO productDTO);
////
////    ResponseEntity<?> addOrUpdateProduct(ProductDTO productDTO);
////}
//
//package com.product.service;
//
//import com.product.dto.ProductDTO;
//import com.product.entity.Product;
//import org.springframework.http.ResponseEntity;
//
//import java.util.List;
//
//public interface ProductService {
//
//    List<Product> getAllProducts(int page, int size);
//
//    Product getProductById(int id);
//
//    Product getProductByClientIdAndSkuCode(int clientId, String skuCode);
//
//    Product getProductByEmpIdAndSkuCode(int empId, String skuCode);
//
//    ResponseEntity<?> addOrUpdateProduct(ProductDTO productDTO);
//}
//

package com.product.service;

import com.product.dto.ProductDTO;
import com.product.entity.Product;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {

    List<Product> getAllProducts(int page, int size);

    Product getProductById(int id);

    Product getProductByClientIdAndSkuCode(int clientId, String skuCode);

    Product getProductByEmpIdAndSkuCode(int empId, String skuCode);

    ResponseEntity<?> addProduct(ProductDTO productDTO);

    ResponseEntity<?> updateProduct(ProductDTO productDTO);
}

