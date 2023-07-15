### @Inheritance
The `@Inheritance` annotation in JPA (Java Persistence API) is used to specify the inheritance strategy for an entity class hierarchy. It is used in conjunction with the `@Entity` annotation to define how the persistence provider should map the inheritance relationships to the database schema.

The `@Inheritance` annotation can be applied at the root entity class of the hierarchy, and it has the following options for the `strategy` attribute:

1. `InheritanceType.SINGLE_TABLE` (default): This strategy maps all entities in the hierarchy to a single table in the database. The discriminator column is used to differentiate between different entity types.

2. `InheritanceType.TABLE_PER_CLASS`: This strategy maps each concrete entity class to its own table in the database. There is no discriminator column, and each table has only the columns specific to that entity class.

3. `InheritanceType.JOINED`: This strategy maps each entity class to its own table in the database, and there is a separate table for the common attributes inherited from the superclass. The relationship between the tables is established using foreign key constraints.

Here's an example usage of `@Inheritance` annotation with the `strategy` attribute:
```java
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Product {
    // Common attributes
}

@Entity
public class ClothingProduct extends Product {
    // Additional attributes specific to clothing products
}

@Entity
public class ElectronicsProduct extends Product {
    // Additional attributes specific to electronics products
}
```

In this example, the `Product` class is the root entity with the `@Inheritance` annotation specifying the `SINGLE_TABLE` strategy. The `ClothingProduct` and `ElectronicsProduct` classes inherit from `Product` and have their specific attributes.

Note that the choice of inheritance strategy depends on your application requirements and the structure of the data model.

```java
@Entity
@Table(name = "product")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
}
@Entity
public class ElectronicsProduct extends Product{
    private String model;
    private String specifications;
}
```

### @MappedSuperclass 

The `@MappedSuperclass` annotation in JPA (Java Persistence API) is used to designate a superclass whose mappings are to be inherited by its subclasses. It allows you to define common mappings and attributes in a superclass and have them shared across multiple entity subclasses.

Here are some key points about `@MappedSuperclass`:

- `@MappedSuperclass` is applied to a class to indicate that it is a superclass whose mappings should be inherited by its subclasses.
- The `@MappedSuperclass`-annotated class itself is not mapped to a database table. It serves as a blueprint for the mappings that will be applied to its subclasses.
- Subclasses of a `@MappedSuperclass` inherit the attributes and mappings defined in the superclass, including any fields, associations, and mappings specified in the superclass.
- `@MappedSuperclass` is useful when you have a set of common attributes or mappings that you want to reuse across multiple entity classes, avoiding code duplication.
- Subclasses of a `@MappedSuperclass` can have additional attributes and mappings specific to their own requirements.

Here's an example usage of `@MappedSuperclass`:

```java
@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Common attributes and mappings shared across subclasses

    // Getters and setters
}

@Entity
public class Employee extends BaseEntity {
    private String firstName;
    private String lastName;

    // Additional attributes and mappings specific to Employee entity

    // Getters and setters
}

@Entity
public class Customer extends BaseEntity {
    private String name;
    private String email;

    // Additional attributes and mappings specific to Customer entity

    // Getters and setters
}
```

In this example, the `BaseEntity` class is annotated with `@MappedSuperclass`, indicating that it provides common attributes and mappings to be inherited by its subclasses (`Employee` and `Customer`). The subclasses can have their specific attributes while inheriting the common attributes and mappings from the `BaseEntity` class.

Note that `@MappedSuperclass` is not used for table inheritance, but rather for reusing common mappings across entity classes.

### Embedded annotation 
The `@Embedded` annotation in JPA (Java Persistence API) is used to mark a field or property in an entity class as an embedded object. An embedded object is a value type that is mapped to multiple columns in a database table.

When you annotate a field or property with `@Embedded`, it signifies that the corresponding attribute is composed of one or more fields from another class (embedded class) that are mapped to the columns of the entity's table. It allows you to encapsulate related fields into a separate class for reusability and better organization of your code.

Here's an example to illustrate the usage of `@Embedded`:

```java
@Entity
public class Employee {
    @Id
    private Long id;
    private String name;

    @Embedded
    private Address address;

    // Getters and setters
}

@Embeddable
public class Address {
    private String street;
    private String city;
    private String state;
    private String zipCode;

    // Getters and setters
}
```

In this example, the `Employee` entity has an `Address` object embedded within it. The `Address` class is annotated with `@Embeddable` to indicate that it represents an embeddable object. The `address` field in the `Employee` class is annotated with `@Embedded` to specify that it is an embedded object.

With this configuration, the attributes of the `Address` class (`street`, `city`, `state`, `zipCode`) will be mapped to columns in the same table as the `Employee` entity. The embedded object's fields will be persisted and retrieved along with the `Employee` entity.

Using `@Embedded`, you can encapsulate complex or reusable components into separate classes and include them as embedded objects within your JPA entities, improving code organization and reusability.

### using @OneToOne, @OneToMany causes hitting db many time. is there any solution for same ( N+1 issue)
When using `@OneToOne` or `@OneToMany` relationships in an object-relational mapping (ORM) framework like Hibernate, it can lead to the "N+1" problem, where additional database queries are executed to fetch associated entities. This can result in performance issues and increased database hits.

To mitigate the N+1 problem and minimize database hits, you can consider the following solutions:

1. **Eager Fetching**: By default, Hibernate performs lazy fetching, where associated entities are loaded on-demand when accessed. However, you can use eager fetching to load the associated entities upfront using the `fetch` attribute in the relationship annotation. For example, `@OneToMany(fetch = FetchType.EAGER)`.

2. **Join Fetching**: Utilize join fetching to fetch associated entities in a single query, reducing the number of database hits. This can be achieved using the `join fetch` keyword in JPQL or HQL queries or by specifying the `fetch = FetchType.JOIN` attribute in the relationship annotation.

3. **Batch Fetching**: Enable batch fetching to retrieve associated entities in batches rather than individually. This can be achieved by configuring batch fetching settings in Hibernate, such as the `@BatchSize` annotation or the `hibernate.batch_fetch_size` configuration property.

4. **DTO Projections**: Consider using DTO (Data Transfer Object) projections to retrieve only the required data from the database instead of full entity objects. This allows you to fetch specific fields or a subset of data, reducing the amount of data transferred from the database and improving performance.

5. **Caching**: Implement caching mechanisms, such as the second-level cache provided by Hibernate, to cache frequently accessed entities. This can help reduce database hits by serving the data directly from the cache when available.

6. **Custom Queries**: Instead of relying solely on ORM mappings, write custom queries using JPQL or SQL to fetch the required data in a more optimized manner. This gives you more control over the fetching strategy and can help reduce unnecessary database hits.

It's important to note that each solution has its trade-offs, and the most suitable approach depends on your specific use case and performance requirements. Analyze the performance impact of each solution and consider factors such as data size, access patterns, and the overall system architecture to choose the most appropriate approach for minimizing database hits in your application.

### N+1 problem solution 
- [appmake](https://appmap.io/blog/2021/10/04/detecting_n_plus_one_for_spring_applications/#:~:text=3%3A00%20The%20easiest%20way,EAGER)%20%40Fetch(FetchMode.)
- 






