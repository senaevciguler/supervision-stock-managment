package com.kiwi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Collection;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StoreDto {
    private Long id;

    @NotBlank
    private String name;

    private AddressDto address;
    private Collection<ProductDto> products;

}
