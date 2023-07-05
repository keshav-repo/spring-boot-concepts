package com.javatechie.redis.respository;

import com.javatechie.redis.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductDao {

    public static final String HASH_KEY = "Product";

    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate template;

    public Product save(Product product){
        template.opsForHash().put(HASH_KEY,product.getId(),product);
        return product;
    }

    public List<Product> findAll(){
        System.out.println("find all is called");
        List<Product> res = null;
        try{
            res =  template.opsForHash().values(HASH_KEY);
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return res;
    }

    public Product findProductById(int id){
        System.out.println("called findProductById() from DB");
        return (Product) template.opsForHash().get(HASH_KEY,id);
    }

    public String deleteProduct(int id){
         template.opsForHash().delete(HASH_KEY,id);
        return "product removed !!";
    }

    public Product updateProduct(Product product) {
        template.opsForHash().put(HASH_KEY, product.getId(), product);
        return product;
    }
}
