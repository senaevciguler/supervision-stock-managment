package com.kiwi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import java.util.Collection;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StockDto {
    private Long id;

    @NotBlank
    private String name;

    @PositiveOrZero
    private Integer quantity;

    private ProductDto product;

}
