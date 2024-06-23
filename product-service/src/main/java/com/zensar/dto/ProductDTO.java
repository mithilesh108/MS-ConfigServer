package com.zensar.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductDTO {

    private int id;
    private String name;
    private String category;
    private long price;
}
