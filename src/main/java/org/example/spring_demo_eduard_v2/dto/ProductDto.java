package org.example.spring_demo_eduard_v2.dto;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;
import org.example.spring_demo_eduard_v2.view.View;


@Data
@Builder
public class ProductDto {
    @JsonView(View.Internal.class)
    private Integer id;


    @JsonView(View.Public.class)
    @NotBlank(message = "name must not be blanc")
    @Size(min = 2, max = 16, message = "name must be between {min} and {max} characters long")
    private String name;



    @JsonView(View.Public.class)
    @Positive(message = "price  must be positive")
    @DecimalMin(value = "0.99", message = "price must be greater that {value}, but it was {validateValue}")
    private Double price;

    @JsonView(View.Public.class)
    @Positive(message = "totalCount  must be positive")
    @Max(value = 1_000_000, message = "totalCount should be less than {value}")
    private Integer totalCount;
}
