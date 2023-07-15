package com.example.CriteriaQuery;

import com.example.CriteriaQuery.entity.Address;
import com.example.CriteriaQuery.entity.Customer;
import com.example.CriteriaQuery.entity.Employee;
import com.example.CriteriaQuery.entity.Order;
import com.example.CriteriaQuery.repo.CustomerRepo;
import com.example.CriteriaQuery.repo.EmployeeRepo;
import com.example.CriteriaQuery.repo.OrderRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SpringBootApplication
@RestController
@RequestMapping
@Slf4j
public class CriteriaQueryApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(CriteriaQueryApplication.class, args);
    }

    @Autowired
    private EntityManager entityManager;

    @GetMapping("/employee")
    public String getEmployeeAddressData2() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);

        Root<Employee> employeeRoot = criteriaQuery.from(Employee.class);
        Root<Address> addressRoot = criteriaQuery.from(Address.class);

        Predicate join = criteriaBuilder.equal(employeeRoot.get("employeeId"), addressRoot.get("employeeId"));

        criteriaQuery.multiselect(employeeRoot, addressRoot);
        criteriaQuery.where(join);

        List<Object[]> res = entityManager.createQuery(criteriaQuery).getResultList();

        return "done";
    }

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private EmployeeRepo employeeRepo;

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        List<Customer> customerList = customerRepo.findAllById(List.of(1, 2));

        System.out.println(customerList);
    }


}
