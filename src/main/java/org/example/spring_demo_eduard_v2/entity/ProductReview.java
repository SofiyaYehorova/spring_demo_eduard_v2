package org.example.spring_demo_eduard_v2.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;
@Data
@Accessors(chain = true)
@Document("reviews")
public class ProductReview {
    @MongoId
    private ObjectId id;
    private Integer productId;
    private List<Review> reviews;
}
