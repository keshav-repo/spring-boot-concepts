package com.example.mongodb.service;

import com.example.mongodb.document.CategoryComponent;
import com.example.mongodb.document.CategoryGroup;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@ComponentScan("com.example.mongodb.service")
public class CategoryServiceTest {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void testSaveCategory() {

        CategoryComponent categoryComponent = new CategoryGroup("Dummy 1");
        categoryService.saveCategory(categoryComponent);

        assertFalse(categoryComponent.getId().isBlank());

        CategoryComponent returnedComponent = mongoTemplate.findById(categoryComponent.getId(), CategoryComponent.class);
        assertEquals(categoryComponent, returnedComponent);
    }

    @Test
    public void testAddToCategory(){
        CategoryComponent parentCat = new CategoryGroup("parent cat");
        categoryService.saveCategory(parentCat);

        CategoryComponent child_cat = new CategoryGroup("child cat");
        categoryService.saveCategory(child_cat);

        CategoryComponent child_cat2 = new CategoryGroup("child cat2");
        categoryService.saveCategory(child_cat2);

        categoryService.addToCategory(parentCat, List.of(child_cat, child_cat2));

        CategoryComponent parentCatRet = mongoTemplate.findById(parentCat.getId(), CategoryComponent.class);
        assertTrue(parentCatRet.getSubcategories().size()==2);
        assertEquals(child_cat, parentCatRet.getSubcategories().get(0));
        assertEquals(child_cat2, parentCatRet.getSubcategories().get(1));
    }

}
