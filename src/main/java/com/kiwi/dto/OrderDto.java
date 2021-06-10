package com.kiwi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private Long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private List<ProductOrderDto> productOrder;
    private BasketDto basket;
    private List<StoreDto> stores;
}
