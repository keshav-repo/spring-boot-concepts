package com.example.mongodb.repo;

import com.example.mongodb.document.CategoryComponent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryComponentRepo extends MongoRepository<CategoryComponent, Long> {
}
