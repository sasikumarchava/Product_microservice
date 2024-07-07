package com.product.service;////package com.product.service;
////
////import com.product.dao.ProductDAO;
////import com.product.dto.ProductDTO;
////import com.product.entity.Product;
////import com.product.exception.ProductAlreadyExistsException;
////import com.product.exception.ProductNotFoundException;
////import com.product.exception.ProductSaveException;
////import com.product.validation.ProductValidation;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.http.HttpStatus;
////import org.springframework.http.ResponseEntity;
////import org.springframework.stereotype.Service;
////import org.springframework.transaction.annotation.Transactional;
////import com.product.constant.ProductConstants;
////import org.springframework.cache.annotation.CacheEvict;
////import org.springframework.cache.annotation.Cacheable;
////
////import java.sql.Timestamp;
////import java.util.List;
////
////@Service
////public class ProductServiceImpl implements ProductService {
////
////    @Autowired
////    private ProductDAO productDAO;
////
////    @Autowired
////    private ProductValidation productValidation;
////
////    @Override
////    @Cacheable(value="products", key="#page + '-' + #size")
////    public List<Product> getAllProducts(int page, int size) {
////        return productDAO.getAllProducts(page, size);
////    }
////
////    @Override
////    @Cacheable(value = "products", key = "#id")
////    public Product getProductById(int id) {
////
////        productValidation.validateProductExists(id);
////        return productDAO.getProductById(id);
////    }
////
////    @Override
////    public Product getProductByClientIdAndSkuCode(int clientId, String skuCode) {
////        Product product = productDAO.getProductByClientIdAndSkuCode(clientId, skuCode);
////        if (product == null) {
////            throw new ProductNotFoundException(ProductConstants.PRODUCT_NOT_FOUND + clientId + " and skuCode: " + skuCode);
////        }
////        return product;
////    }
////
////    @Override
////    public Product getProductByEmpIdAndSkuCode(int empId, String skuCode) {
////        return productDAO.getProductByEmpIdAndSkuCode(empId, skuCode);
////    }
////
////    @Transactional
////    @CacheEvict(value = "products", allEntries = true)
////    public ResponseEntity<?> addProduct(ProductDTO productDTO) {
////
////
////        try {
////            productValidation.validateUniqueProduct(productDTO);
////
////            Product product = Product.builder()
////                    .clientId(productDTO.getClientId())
////                    .empId(productDTO.getEmpId())
////                    .skuCode(productDTO.getSkuCode())
////                    .name(productDTO.getName())
////                    .enable(productDTO.isEnable())
////                    .lastModifiedDate(new Timestamp(System.currentTimeMillis()))
////                    .build();
////
////            productDAO.saveOrUpdateProduct(product);
////            return ResponseEntity.ok(ProductConstants.PRODUCT_ADDED_SUCCESS);
////
////        } catch (ProductSaveException e) {
////            // Catch specific exceptions related to product saving
////            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving product: " + e.getMessage());
////
////        } catch (Exception e) {
////            // Catch any unexpected exceptions
////            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error while adding product: " + e.getMessage());
////        }
////
////    }
////
////    @Transactional
////    @CacheEvict(value = "products", allEntries = true)
////    public ResponseEntity<?> updateProduct(ProductDTO productDTO) {
////        try {
////
////            productValidation.validateProductDTO(productDTO);
////
////            // Retrieve existing product
////            Product productToUpdate = getProductById(productDTO.getId());
////            if (productToUpdate == null) {
////                throw new ProductNotFoundException("Product not found with id: " + productDTO.getId());
////            }
////
////            // Update fields
////            productToUpdate.setClientId(productDTO.getClientId());
////            productToUpdate.setEmpId(productDTO.getEmpId());
////            productToUpdate.setSkuCode(productDTO.getSkuCode());
////            productToUpdate.setName(productDTO.getName());
////            productToUpdate.setEnable(productDTO.isEnable());
////            productToUpdate.setLastModifiedDate(new Timestamp(System.currentTimeMillis()));
////
////
////            productDAO.saveOrUpdateProduct(productToUpdate);
////
////            return ResponseEntity.ok(ProductConstants.PRODUCT_UPDATED_SUCCESS);
////        } catch (ProductNotFoundException e) {
////            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
////        } catch (Exception e) {
////            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating product: " + e.getMessage());
////        }
////    }
////}
////
//package com.product.service;
//
//import com.product.dao.ProductDAO;
//import com.product.dto.ProductDTO;
//import com.product.entity.Product;
//import com.product.exception.ProductAlreadyExistsException;
//import com.product.exception.ProductNotFoundException;
//import com.product.exception.ProductSaveException;
//import com.product.validation.ProductValidation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import com.product.constant.ProductConstants;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;
//
//import java.sql.Timestamp;
//import java.util.List;
//
//@Service
//public class ProductServiceImpl implements ProductService {
//
//    @Autowired
//    private ProductDAO productDAO;
//
//    @Autowired
//    private KafkaProducerService kafkaProducerService;
//
//    @Autowired
//    private ProductValidation productValidation;
//
//    @Override
//    @Cacheable(value = "products", key = "'all-' + #page + '-' + #size")
//    public List<Product> getAllProducts(int page, int size) {
//        return productDAO.getAllProducts(page, size);
//    }
//
//    @Override
//    @Cacheable(value = "products", key = "#id")
//    public Product getProductById(int id) {
//        Product product = productDAO.getProductById(id);
//        if (product == null) {
//            throw new ProductNotFoundException("Product not found with id: " + id);
//        }
//        return product;
//    }
//
//    @Override
//    public Product getProductByClientIdAndSkuCode(int clientId, String skuCode) {
//        return productDAO.getProductByClientIdAndSkuCode(clientId, skuCode);
//    }
//
//    @Override
//    public Product getProductByEmpIdAndSkuCode(int empId, String skuCode) {
//        return productDAO.getProductByEmpIdAndSkuCode(empId, skuCode);
//    }
//
//    @Transactional
//    @CacheEvict(value = "products", allEntries = true)
//    public ResponseEntity<?> addProduct(ProductDTO productDTO) {
//        try {
//            productValidation.validateUniqueProduct(productDTO);
//
//            Product product = Product.builder()
//                    .clientId(productDTO.getClientId())
//                    .empId(productDTO.getEmpId())
//                    .skuCode(productDTO.getSkuCode())
//                    .name(productDTO.getName())
//                    .enable(productDTO.isEnable())
//                    .lastModifiedDate(new Timestamp(System.currentTimeMillis()))
//                    .build();
//
//            productDAO.saveOrUpdateProduct(product);
//            return ResponseEntity.ok(ProductConstants.PRODUCT_ADDED_SUCCESS);
//
//        } catch (ProductSaveException e) {
//            // Catch specific exceptions related to product saving
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving product: " + e.getMessage());
//
//        } catch (Exception e) {
//            // Catch any unexpected exceptions
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error while adding product: " + e.getMessage());
//        }
//    }
//
//    @Transactional
//    @CacheEvict(value = "products", allEntries = true)
//    public ResponseEntity<?> updateProduct(ProductDTO productDTO) {
//        try {
//            productValidation.validateProductDTO(productDTO);
//
//            // Retrieve existing product
//            Product productToUpdate = getProductById(productDTO.getId());
//
//            // Update fields
//            productToUpdate.setClientId(productDTO.getClientId());
//            productToUpdate.setEmpId(productDTO.getEmpId());
//            productToUpdate.setSkuCode(productDTO.getSkuCode());
//            productToUpdate.setName(productDTO.getName());
//            productToUpdate.setEnable(productDTO.isEnable());
//            productToUpdate.setLastModifiedDate(new Timestamp(System.currentTimeMillis()));
//
//            productDAO.saveOrUpdateProduct(productToUpdate);
//
//            return ResponseEntity.ok(ProductConstants.PRODUCT_UPDATED_SUCCESS);
//        } catch (ProductNotFoundException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating product: " + e.getMessage());
//        }
//    }
//}
import com.product.constant.ProductConstants;
import com.product.dao.ProductDAO;
import com.product.dto.ProductDTO;
import com.product.entity.Product;
import com.product.exception.ProductAlreadyExistsException;
import com.product.exception.ProductNotFoundException;
import com.product.exception.ProductSaveException;
import com.product.validation.ProductValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.sql.Timestamp;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDAO productDAO;

    @Autowired
    private KafkaProducerService kafkaProducerService; // Assuming you have a KafkaProducerService

    @Autowired
    private ProductValidation productValidation;

    @Override
    @Cacheable(value = "products", key = "'all-' + #page + '-' + #size")
    public List<Product> getAllProducts(int page, int size) {
        return productDAO.getAllProducts(page, size);
    }

    @Override
    @Cacheable(value = "products", key = "#id")
    public Product getProductById(int id) {
        Product product = productDAO.getProductById(id);
        if (product == null) {
            throw new ProductNotFoundException("Product not found with id: " + id);
        }
        return product;
    }

    @Override
    public Product getProductByClientIdAndSkuCode(int clientId, String skuCode) {
        return productDAO.getProductByClientIdAndSkuCode(clientId, skuCode);
    }

    @Override
    public Product getProductByEmpIdAndSkuCode(int empId, String skuCode) {
        return productDAO.getProductByEmpIdAndSkuCode(empId, skuCode);
    }

    @Transactional
    @CacheEvict(value = "products", allEntries = true)
    public ResponseEntity<?> addProduct(ProductDTO productDTO) {
        try {
            productValidation.validateUniqueProduct(productDTO);

            Product product = Product.builder()
                    .clientId(productDTO.getClientId())
                    .empId(productDTO.getEmpId())
                    .skuCode(productDTO.getSkuCode())
                    .name(productDTO.getName())
                    .enable(productDTO.isEnable())
                    .lastModifiedDate(new Timestamp(System.currentTimeMillis()))
                    .build();

            // Save product
            productDAO.saveOrUpdateProduct(product);

            // Publish message to Kafka
            kafkaProducerService.sendProductMessage(productDTO);

            return ResponseEntity.ok(ProductConstants.PRODUCT_ADDED_SUCCESS);

        } catch (ProductSaveException e) {
            // Catch specific exceptions related to product saving
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving product: " + e.getMessage());

        } catch (Exception e) {
            // Catch any unexpected exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error while adding product: " + e.getMessage());
        }
    }

    @Transactional
    @CacheEvict(value = "products", allEntries = true)
    public ResponseEntity<?> updateProduct(ProductDTO productDTO) {
        try {
            productValidation.validateProductDTO(productDTO);

            // Retrieve existing product
            Product productToUpdate = getProductById(productDTO.getId());

            // Update fields
            productToUpdate.setClientId(productDTO.getClientId());
            productToUpdate.setEmpId(productDTO.getEmpId());
            productToUpdate.setSkuCode(productDTO.getSkuCode());
            productToUpdate.setName(productDTO.getName());
            productToUpdate.setEnable(productDTO.isEnable());
            productToUpdate.setLastModifiedDate(new Timestamp(System.currentTimeMillis()));

            // Save updated product
            productDAO.saveOrUpdateProduct(productToUpdate);

            // Publish message to Kafka
            kafkaProducerService.sendProductMessage(productDTO); // Example method to send product update event

            return ResponseEntity.ok(ProductConstants.PRODUCT_UPDATED_SUCCESS);
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating product: " + e.getMessage());
        }
    }
}
