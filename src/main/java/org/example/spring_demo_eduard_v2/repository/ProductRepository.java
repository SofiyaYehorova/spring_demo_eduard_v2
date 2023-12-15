package org.example.spring_demo_eduard_v2.repository;

import org.example.spring_demo_eduard_v2.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

        // find by name -> select * from products where name = ?
    Optional<Product> findByName(String name);

    // select * from products where name = ? and total_count = ?
    Optional<Product> findByNameAndTotalCount(String name, Integer totalCount);
}






