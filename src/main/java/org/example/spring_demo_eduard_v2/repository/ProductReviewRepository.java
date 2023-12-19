package org.example.spring_demo_eduard_v2.repository;

import org.bson.types.ObjectId;
import org.example.spring_demo_eduard_v2.entity.ProductReview;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductReviewRepository extends MongoRepository<ProductReview, ObjectId> {
    Optional<ProductReview> findByProductId(Integer productId);

    @Query("{productId: ?0}")
    Optional<ProductReview> get(Integer productId);

}
