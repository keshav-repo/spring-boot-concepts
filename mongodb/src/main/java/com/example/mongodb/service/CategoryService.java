package com.example.mongodb.service;

import com.example.mongodb.document.CategoryComponent;
import com.example.mongodb.document.CategoryGroup;
import com.example.mongodb.repo.CategoryComponentRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CategoryService {

    @Autowired
    private CategoryComponentRepo categoryComponentRepo;

    /**
     * @param parent
     * @param child category need to be appended to parent category
     * @return the parent object
     */
    public CategoryComponent addToCategory(CategoryComponent parent, CategoryComponent child) {
        if (parent instanceof CategoryGroup) {
            parent.addSubcategory(child);
            categoryComponentRepo.save(parent);
        }
        return parent;
    }

    public CategoryComponent addToCategory(CategoryComponent parent, List<CategoryComponent> childs) {
        if (parent instanceof CategoryGroup) {
            for(CategoryComponent child: childs){
                parent.addSubcategory(child);
            }
            categoryComponentRepo.save(parent);
        }
        return parent;
    }

    public CategoryComponent saveCategory(CategoryComponent categoryComponent){
        try {
            categoryComponentRepo.save(categoryComponent);
            return categoryComponent;
        }catch (Exception exception){
            log.error(exception.getMessage());
            exception.printStackTrace();
            throw new RuntimeException("Some error");
        }
    }

}
