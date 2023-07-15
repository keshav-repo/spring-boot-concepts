package com.example.mongodb.document;

import java.util.List;

public class Category extends CategoryComponent{
    public Category(String name) {
        super(name);
    }
    @Override
    public void addSubcategory(CategoryComponent component) {
        throw new UnsupportedOperationException();
    }
    @Override
    public void removeSubcategory(CategoryComponent component) {
        throw new UnsupportedOperationException();
    }
    @Override
    public List<CategoryComponent> getSubcategories() {
        throw new UnsupportedOperationException();
    }
}
