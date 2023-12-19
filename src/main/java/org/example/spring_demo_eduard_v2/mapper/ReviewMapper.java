package org.example.spring_demo_eduard_v2.mapper;

import org.example.spring_demo_eduard_v2.dto.ReviewDto;
import org.example.spring_demo_eduard_v2.entity.Review;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {
    public ReviewDto mapToDto(Review review){
        return new ReviewDto()
                .setAuthor(review.getAuthor())
                .setMessage(review.getMessage())
                .setTimestamp(review.getTimestamp());
    }
    public Review map(ReviewDto dto){
        return new Review()
                .setAuthor(dto.getAuthor())
                .setMessage(dto.getMessage())
                .setTimestamp(dto.getTimestamp());
    }
}
