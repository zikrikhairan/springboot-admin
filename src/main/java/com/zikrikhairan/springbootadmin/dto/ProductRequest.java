package com.zikrikhairan.springbootadmin.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Getter
@Setter
public class ProductRequest {
    @NotBlank(message = "Name is mandatory")
    String name;
    Integer quantity;
    String barcode;
    BigDecimal price;
}
