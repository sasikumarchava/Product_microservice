
package com.product.entity;

//import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.redis.core.RedisHash;

//import java.time.LocalDateTime;
import java.sql.Timestamp;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product")
@RedisHash("product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "client_id")
    private int clientId;

    @Column(name = "emp_id")
    private int empId;

    @Column(name = "skuCode", unique = true)
    private String skuCode;

    @Column(name = "name")
    private String name;

    @Column(name = "last_modified_date")
    @UpdateTimestamp
    private Timestamp lastModifiedDate;

    @Column(name = "enable")
    private boolean enable;

}



