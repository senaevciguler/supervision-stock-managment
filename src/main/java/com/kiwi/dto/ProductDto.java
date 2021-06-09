package com.kiwi.dto;

import com.kiwi.entities.Stock;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private Long id;
    @NotBlank
    @Size(min = 1, max = 200, message = "Name must be between 1 and 200 characters")
    private String name;
    @NotNull
    @Positive
    private BigDecimal price;
    private MeasurementDto measurement;
    private List<IngredientDto> ingredients;
    private CategoryDto category;
    private BasketDto basket;
    private byte[] photo;
    private Stock stock;


}
