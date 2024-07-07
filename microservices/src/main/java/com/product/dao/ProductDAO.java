////package com.product.dao;
////
////import com.product.entity.Product;
////import com.product.exception.ProductNotFoundException;
////import jakarta.persistence.EntityManager;
////import jakarta.persistence.NoResultException;
////import jakarta.persistence.PersistenceContext;
////import jakarta.persistence.TypedQuery;
////import jakarta.persistence.criteria.CriteriaBuilder;
////import jakarta.persistence.criteria.CriteriaQuery;
////import jakarta.persistence.criteria.Root;
////import jakarta.transaction.Transactional;
////import org.hibernate.Session;
////import org.hibernate.SessionFactory;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.data.redis.core.RedisTemplate;
////import org.springframework.stereotype.Repository;
////
////import java.util.List;
////
////@Repository
////@Transactional
////public class ProductDAO {
////
////    @Autowired
////    private SessionFactory sessionFactory;
////
////    @PersistenceContext
////    private EntityManager entityManager;
////
////    public static final String HASH_KEY = "product";
////    private RedisTemplate redisTemplate;
////
////    @Transactional
////    public List<Product> getAllProducts(int page, int size) {
////        Session session = sessionFactory.getCurrentSession();
////        CriteriaBuilder cb = session.getCriteriaBuilder();
////        CriteriaQuery<Product> cq = cb.createQuery(Product.class);
////        Root<Product> root = cq.from(Product.class);
////        cq.select(root);
////
////        return session.createQuery(cq)
////                .setFirstResult(page * size)
////                .setMaxResults(size)
////                .getResultList();
////
////    }
////
////    @Transactional
////    public Product getProductById(int id) {
////        Session session = sessionFactory.getCurrentSession();
////        return session.get(Product.class, id);
////
////    }
////
////    @Transactional
////    public Product getProductByClientIdAndSkuCode(int clientId, String skuCode) {
////        try {
////            return entityManager.createQuery("SELECT p FROM Product p WHERE p.clientId = :clientId AND p.skuCode = :skuCode", Product.class)
////                    .setParameter("clientId", clientId)
////                    .setParameter("skuCode", skuCode)
////                    .getSingleResult();
////        } catch (NoResultException ex) {
////            throw new ProductNotFoundException("Product not found with clientId: " + clientId + " and skuCode: " + skuCode);
////        }
////    }
////
////    @Transactional
////    public Product getProductByEmpIdAndSkuCode(int empId, String skuCode) {
////        try {
////            Session session = sessionFactory.getCurrentSession();
////            String jpql = "SELECT p FROM Product p WHERE p.empId = :empId AND p.skuCode = :skuCode";
////            TypedQuery<Product> query = session.createQuery(jpql, Product.class);
////
////            query.setParameter("empId", empId);
////            query.setParameter("skuCode", skuCode);
////            List<Product> results = query.getResultList();
////            if (results.isEmpty()) {
////                throw new ProductNotFoundException("Product not found with empId: " + empId + " and skuCode: " + skuCode);
////            }
////            return results.get(0);
////        } catch (NoResultException ex) {
////            throw new ProductNotFoundException("Product not found with empId: " + empId + " and skuCode: " + skuCode);
////        }
////    }
////
////    @Transactional
////    public boolean existsByClientIdOrEmpIdOrSkuCode(int clientId, int empId, String skuCode) {
////        Session session = sessionFactory.getCurrentSession();
////        String jpql = "SELECT COUNT(p) FROM Product p WHERE p.clientId = :clientId OR p.empId = :empId OR p.skuCode = :skuCode";
////        TypedQuery<Long> query = session.createQuery(jpql, Long.class);
////        query.setParameter("clientId", clientId);
////        query.setParameter("empId", empId);
////        query.setParameter("skuCode", skuCode);
////        return query.getSingleResult() > 0;
////    }
////
////    @Transactional
////    public void saveOrUpdateProduct(Product product) {
////        Session session = sessionFactory.getCurrentSession();
////        session.saveOrUpdate(product);
////    }
////
////
////}
//
//package com.product.dao;
//
//import com.product.entity.Product;
//import com.product.exception.ProductNotFoundException;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.NoResultException;
//import jakarta.persistence.PersistenceContext;
//import jakarta.persistence.TypedQuery;
//import jakarta.persistence.criteria.CriteriaBuilder;
//import jakarta.persistence.criteria.CriteriaQuery;
//import jakarta.persistence.criteria.Root;
//import java.util.List;
//
//
//@Repository
//@Transactional
//public class ProductDAO {
//
//    @Autowired
//    private SessionFactory sessionFactory;
//
//    @PersistenceContext
//    private EntityManager entityManager;
//
//    @Autowired
//    private RedisTemplate<String, Object> redisTemplate;
//
//    public static final String HASH_KEY = "product";
//
//    @Transactional(readOnly = true)
//    public List<Product> getAllProducts(int page, int size) {
//        List<Product> products;
//        // Check Redis first
//        List<Object> productListFromRedis = redisTemplate.opsForHash().values(HASH_KEY);
//        if (!productListFromRedis.isEmpty()) {
//            products = (List<Product>) (List<?>) productListFromRedis; // Cast to List<Product>
//        } else {
//            // Fetch from database
//            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//            CriteriaQuery<Product> cq = cb.createQuery(Product.class);
//            Root<Product> root = cq.from(Product.class);
//            cq.select(root);
//
//            TypedQuery<Product> query = entityManager.createQuery(cq)
//                    .setFirstResult(page * size)
//                    .setMaxResults(size);
//
//            products = query.getResultList();
//            // Store in Redis
//            redisTemplate.opsForHash().put(HASH_KEY, "allProducts", products);
//        }
//
//        return products;
//    }
//
//    @Transactional(readOnly = true)
//    public Product getProductById(int id) {
//        Product product;
//        // Check Redis first
//        product = (Product) redisTemplate.opsForHash().get(HASH_KEY, id);
//        if (product == null) {
//            // Fetch from database
//            product = entityManager.find(Product.class, id);
//            // Store in Redis
//            if (product != null) {
//                redisTemplate.opsForHash().put(HASH_KEY, id, product);
//            }
//        }
//        return product;
//    }
//
//    @Transactional(readOnly = true)
//    public Product getProductByClientIdAndSkuCode(int clientId, String skuCode) {
//        try {
//            return entityManager.createQuery("SELECT p FROM Product p WHERE p.clientId = :clientId AND p.skuCode = :skuCode", Product.class)
//                    .setParameter("clientId", clientId)
//                    .setParameter("skuCode", skuCode)
//                    .getSingleResult();
//        } catch (NoResultException ex) {
//            throw new ProductNotFoundException("Product not found with clientId: " + clientId + " and skuCode: " + skuCode);
//        }
//    }
//
//    @Transactional(readOnly = true)
//    public Product getProductByEmpIdAndSkuCode(int empId, String skuCode) {
//        try {
//            return entityManager.createQuery("SELECT p FROM Product p WHERE p.empId = :empId AND p.skuCode = :skuCode", Product.class)
//                    .setParameter("empId", empId)
//                    .setParameter("skuCode", skuCode)
//                    .getSingleResult();
//        } catch (NoResultException ex) {
//            throw new ProductNotFoundException("Product not found with empId: " + empId + " and skuCode: " + skuCode);
//        }
//    }
//
//    @Transactional(readOnly = true)
//    public boolean existsByClientIdOrEmpIdOrSkuCode(int clientId, int empId, String skuCode) {
//        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
//        Root<Product> root = cq.from(Product.class);
//        cq.select(cb.count(root));
//        cq.where(cb.or(
//                cb.equal(root.get("clientId"), clientId),
//                cb.equal(root.get("empId"), empId),
//                cb.equal(root.get("skuCode"), skuCode)
//        ));
//
//        Long count = entityManager.createQuery(cq).getSingleResult();
//        return count > 0;
//    }
//
//    @Transactional
//
//    public void saveOrUpdateProduct(Product product) {
//        Session session = sessionFactory.getCurrentSession();
//        session.saveOrUpdate(product);
//
//        // Update or add to Redis
//        redisTemplate.opsForHash().put(HASH_KEY, product.getId(), product);
//    }
//}

package com.product.dao;

import com.product.entity.Product;
import com.product.exception.ProductNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;

@Repository
@Transactional
public class ProductDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public static final String HASH_KEY = "product";


    @Transactional(readOnly = true)
    public List<Product> getAllProducts(int page, int size) {
        List<Product> products;
        // Check Redis first
        List<Object> productListFromRedis = redisTemplate.opsForHash().values(HASH_KEY);
        if (!productListFromRedis.isEmpty()) {
            products = (List<Product>) (List<?>) productListFromRedis; // Cast to List<Product>
        } else {
            // Fetch from database
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Product> cq = cb.createQuery(Product.class);
            Root<Product> root = cq.from(Product.class);
            cq.select(root);

            TypedQuery<Product> query = entityManager.createQuery(cq)
                    .setFirstResult(page * size)
                    .setMaxResults(size);

            products = query.getResultList();
            // Store in Redis
            redisTemplate.opsForHash().put(HASH_KEY, "allProducts", products);
        }

        return products;
    }

    @Transactional(readOnly = true)
    public Product getProductById(int id) {
        Product product;
        // Check Redis first
        product = (Product) redisTemplate.opsForHash().get(HASH_KEY, id);
        if (product == null) {
            // Fetch from database
            product = entityManager.find(Product.class, id);
            // Store in Redis
            if (product != null) {
                redisTemplate.opsForHash().put(HASH_KEY, id, product);
            }
        }
        return product;
    }

    @Transactional(readOnly = true)
    public Product getProductByClientIdAndSkuCode(int clientId, String skuCode) {
        try {
            return entityManager.createQuery("SELECT p FROM Product p WHERE p.clientId = :clientId AND p.skuCode = :skuCode", Product.class)
                    .setParameter("clientId", clientId)
                    .setParameter("skuCode", skuCode)
                    .getSingleResult();
        } catch (NoResultException ex) {
            throw new ProductNotFoundException("Product not found with clientId: " + clientId + " and skuCode: " + skuCode);
        }
    }

    @Transactional(readOnly = true)
    public Product getProductByEmpIdAndSkuCode(int empId, String skuCode) {
        try {
            return entityManager.createQuery("SELECT p FROM Product p WHERE p.empId = :empId AND p.skuCode = :skuCode", Product.class)
                    .setParameter("empId", empId)
                    .setParameter("skuCode", skuCode)
                    .getSingleResult();
        } catch (NoResultException ex) {
            throw new ProductNotFoundException("Product not found with empId: " + empId + " and skuCode: " + skuCode);
        }
    }

    @Transactional(readOnly = true)
    public boolean existsByClientIdOrEmpIdOrSkuCode(int clientId, int empId, String skuCode) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Product> root = cq.from(Product.class);
        cq.select(cb.count(root));
        cq.where(cb.or(
                cb.equal(root.get("clientId"), clientId),
                cb.equal(root.get("empId"), empId),
                cb.equal(root.get("skuCode"), skuCode)
        ));

        Long count = entityManager.createQuery(cq).getSingleResult();
        return count > 0;
    }

    @Transactional
    public void saveOrUpdateProduct(Product product) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(product);

        // Update or add to Redis
        redisTemplate.opsForHash().put(HASH_KEY, product.getId(), product);
    }
}

