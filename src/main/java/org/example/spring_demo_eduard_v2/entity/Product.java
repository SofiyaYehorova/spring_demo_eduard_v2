package org.example.spring_demo_eduard_v2.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "product", schema = "public")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "price")
    private Double price;

    @Column(name = "total_count")
    private Integer totalCount;
}


