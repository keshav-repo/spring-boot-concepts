package com.example.mongodb.document;

import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.ArrayList;
import java.util.List;

public class CategoryGroup extends CategoryComponent {

    @DBRef
    private List<CategoryComponent> subcategories;
    public CategoryGroup(String name) {
        super(name);
        this.subcategories = new ArrayList<>();
    }
    @Override
    public void addSubcategory(CategoryComponent component) {
        this.subcategories.add(component);
    }
    @Override
    public void removeSubcategory(CategoryComponent component) {
        subcategories.remove(component);
    }
    @Override
    public List<CategoryComponent> getSubcategories() {
        return subcategories;
    }
}
