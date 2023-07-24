package com.example.pagination.controller;

import com.example.pagination.entity.Product;
import com.example.pagination.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductRepo productRepo;
    @GetMapping
    public Page<Product> getProduct( @RequestParam(defaultValue = "0") int pageNo,
                                     @RequestParam(defaultValue = "10") int pageSize){

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Product> products =  productRepo.findAll(pageable);

       return products;
    }
}
