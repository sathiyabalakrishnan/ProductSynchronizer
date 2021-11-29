package com.mercado.productSynchronizer.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {
    private Long id;

    private String title;

    private String description;

    private String price;

    private String quantity;
}
