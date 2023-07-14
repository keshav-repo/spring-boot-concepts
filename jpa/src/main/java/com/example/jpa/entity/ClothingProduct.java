package com.example.jpa.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
//@Table(name = "clothing_product")
//@DiscriminatorValue("clothing")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ClothingProduct extends Product{
    private String size;
    private String color;
}
