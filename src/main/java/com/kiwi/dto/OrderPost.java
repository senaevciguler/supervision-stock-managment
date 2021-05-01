package com.kiwi.dto;

import com.kiwi.entities.Basket;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderPost implements Serializable {

    private Date date;
    private Basket basket;
    private Long product;
    private String store;
}
