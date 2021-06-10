package com.kiwi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {
    private Long id;

    @NotBlank
    private String addressLine;

    @NotBlank
    private String city;

    @NotBlank
    private String country;
    private Integer postalCode;

    @NotBlank
    private String phone;
}
