package com.example.mongodb.document;

import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


import java.util.ArrayList;
import java.util.List;

@Document(collection = "categories")
@EqualsAndHashCode
public abstract class CategoryComponent {
    @Id
    private String id;
    @Indexed
    private String name;

    public CategoryComponent(String name) {
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
    public String getId(){
        return this.id;
    }
    public abstract void addSubcategory(CategoryComponent component);
    public abstract void removeSubcategory(CategoryComponent component);
    public abstract List<CategoryComponent> getSubcategories();
}
